package com.linecorp.kotlinjdsl.test.reactive

import org.hibernate.cfg.AvailableSettings
import org.hibernate.jpa.HibernatePersistenceProvider
import org.hibernate.jpa.boot.internal.PersistenceXmlParser
import org.hibernate.jpa.boot.spi.Bootstrap
import org.hibernate.reactive.stage.Stage.SessionFactory
import org.junit.jupiter.api.extension.AfterAllCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.api.extension.TestInstancePostProcessor
import org.slf4j.LoggerFactory
import javax.persistence.EntityManagerFactory
import javax.persistence.Persistence
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.KProperty1
import kotlin.reflect.KType
import kotlin.reflect.full.createType
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible

class StageSessionFactoryExtension : TestInstancePostProcessor, AfterAllCallback {
    private lateinit var sessionFactory: SessionFactory
    private lateinit var entityManagerFactory: EntityManagerFactory
    private val sessionFactoryType = SessionFactory::class.createType()
    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun postProcessTestInstance(testInstance: Any, context: ExtensionContext) {
        // hibernate-reactive does not support h2 db officially.
        // So, we initialize the table by using the schema generation function of h2 db in synchronous method existing in hibernate-core.
        // The reason is that the create-drop function is not a core function of our library.
        val persistenceUnitName = "order"
        val unit = PersistenceXmlParser.locatePersistenceUnits(emptyMap<String, String>()).first { it.name == persistenceUnitName }
        unit.providerClassName = HibernatePersistenceProvider::class.qualifiedName

        entityManagerFactory =
            Bootstrap.getEntityManagerFactoryBuilder(
                unit,
                mapOf<Any, Any>(AvailableSettings.HBM2DDL_AUTO to "create"),
                this.javaClass.classLoader
            ).build()
        sessionFactory = Persistence.createEntityManagerFactory(persistenceUnitName)
            .unwrap(SessionFactory::class.java).apply {
                // warm up
                logger.info("warmup order count: ${withSession {
                    it.createQuery<Long>("SELECT COUNT(o) FROM Order o").singleResult
                }.toCompletableFuture().get()}")
            }

        injectProperty(testInstance, sessionFactory, sessionFactoryType)
    }

    override fun afterAll(context: ExtensionContext) {
        logger.info("closing after all")
        sessionFactory.close()
        entityManagerFactory.close()
    }

    @Suppress("UNCHECKED_CAST")
    private fun injectProperty(testInstance: Any, setProperty: Any, type: KType) {
        testInstance::class.memberProperties
            .filter { it.returnType == type }
            .forEach { property ->
                property as KProperty1<Any, Any>

                if (property.isAlreadyInitialized(testInstance)) return

                property.apply {
                    this.isAccessible = true

                    if (this !is KMutableProperty1<Any, Any>) {
                        throw IllegalStateException(
                            "${property.name} in ${testInstance::class.simpleName} is read-only property"
                        )
                    }
                    this.set(testInstance, setProperty)
                }
            }

    }

    private fun KProperty1<Any, Any?>.isAlreadyInitialized(target: Any): Boolean {
        return try {
            get(target) != null
        } catch (ex: Exception) {
            false
        }
    }
}
