package com.linecorp.kotlinjdsl.test.reactive

import com.linecorp.kotlinjdsl.test.reactive.query.initializeDbQueries
import org.hibernate.reactive.stage.Stage.SessionFactory
import org.junit.jupiter.api.extension.*
import javax.persistence.Persistence
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.KProperty1
import kotlin.reflect.full.createType
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible

class StageSessionFactoryExtension :
    TestInstancePostProcessor, ParameterResolver,
    BeforeEachCallback, AfterEachCallback {

    private lateinit var sessionFactory: SessionFactory

    private val type = SessionFactory::class.createType()

    override fun supportsParameter(parameterContext: ParameterContext, extensionContext: ExtensionContext): Boolean {
        return parameterContext.parameter.type == SessionFactory::class.java
    }

    override fun resolveParameter(parameterContext: ParameterContext, extensionContext: ExtensionContext): Any {
        return sessionFactory
    }

    override fun postProcessTestInstance(testInstance: Any, context: ExtensionContext) {
        sessionFactory = Persistence.createEntityManagerFactory("order")
            .unwrap(SessionFactory::class.java)

        injectProperty(testInstance)
    }

    override fun beforeEach(context: ExtensionContext) {
        initializeDbQueries("/drop-table.sql", sessionFactory)
        initializeDbQueries("/init-table.sql", sessionFactory)
    }

    override fun afterEach(context: ExtensionContext) {
        sessionFactory.close()
    }

    @Suppress("UNCHECKED_CAST")
    private fun injectProperty(testInstance: Any) = testInstance::class.memberProperties
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
                this.set(testInstance, sessionFactory)
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
