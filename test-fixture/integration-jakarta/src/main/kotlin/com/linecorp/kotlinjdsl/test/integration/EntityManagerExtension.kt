package com.linecorp.kotlinjdsl.test.integration

import org.junit.jupiter.api.extension.*
import jakarta.persistence.EntityManager
import jakarta.persistence.Persistence
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.KProperty1
import kotlin.reflect.full.createType
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible

class EntityManagerExtension :
    TestInstancePostProcessor, ParameterResolver,
    AfterAllCallback, BeforeEachCallback, AfterEachCallback {

    private companion object {
        private val entityManagerFactory = Persistence.createEntityManagerFactory("order")

        private val entityManagerType = EntityManager::class.createType()
    }

    private val entityManager: EntityManager = entityManagerFactory.createEntityManager()
    private val transaction = entityManager.transaction

    override fun supportsParameter(parameterContext: ParameterContext, extensionContext: ExtensionContext): Boolean {
        return parameterContext.parameter.type == EntityManager::class.java
    }

    override fun resolveParameter(parameterContext: ParameterContext, extensionContext: ExtensionContext): Any {
        return entityManager
    }

    override fun postProcessTestInstance(testInstance: Any, context: ExtensionContext) {
        injectEntityManager(testInstance)
    }

    override fun afterAll(context: ExtensionContext) {
        entityManager.close()
    }

    override fun beforeEach(context: ExtensionContext) {
        transaction.begin()
    }

    override fun afterEach(context: ExtensionContext) {
        transaction.rollback()
    }

    @Suppress("UNCHECKED_CAST")
    private fun injectEntityManager(testInstance: Any) = testInstance::class.memberProperties
        .filter { it.returnType == entityManagerType }
        .forEach { entityManagerProperty ->
            entityManagerProperty as KProperty1<Any, Any>

            if (entityManagerProperty.isAlreadyInitialized(testInstance)) return

            entityManagerProperty.apply {
                this.isAccessible = true

                if (this !is KMutableProperty1<Any, Any>) {
                    throw IllegalStateException(
                        "${entityManagerProperty.name} in ${testInstance::class.simpleName} is read-only property"
                    )
                }

                this.set(testInstance, entityManager)
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
