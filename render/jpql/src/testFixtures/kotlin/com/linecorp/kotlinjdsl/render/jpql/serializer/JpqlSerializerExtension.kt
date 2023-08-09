package com.linecorp.kotlinjdsl.render.jpql.serializer

import com.linecorp.kotlinjdsl.render.jpql.introspector.JpqlRenderIntrospector
import io.mockk.every
import io.mockk.excludeRecords
import io.mockk.isMockKMock
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.api.extension.InvocationInterceptor
import org.junit.jupiter.api.extension.ReflectiveInvocationContext
import java.lang.reflect.Method
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible

internal class JpqlSerializerExtension : InvocationInterceptor, BeforeEachCallback {
    override fun interceptTestMethod(
        invocation: InvocationInterceptor.Invocation<Void>,
        invocationContext: ReflectiveInvocationContext<Method>,
        extensionContext: ExtensionContext?,
    ) {
        invocationContext.arguments.forEach {
            stubIfNecessary(it)
        }

        super.interceptTestMethod(invocation, invocationContext, extensionContext)
    }

    override fun beforeEach(context: ExtensionContext) {
        val instance = context.testInstance.get()

        instance::class.memberProperties.forEach {
            val originAccessible = it.isAccessible

            it.isAccessible = true

            @Suppress("UNCHECKED_CAST")
            val propertyValue = (it as KProperty1<Any, *>).get(instance)

            stubIfNecessary(propertyValue)

            it.isAccessible = originAccessible
        }
    }

    private fun stubIfNecessary(any: Any?) {
        if (any == null) return

        if (any is JpqlRenderIntrospector && isMockKMock(any)) {
            every { any.key } returns JpqlRenderIntrospector
            excludeRecords { any.key }
        }

        if (any is JpqlRenderSerializer && isMockKMock(any)) {
            every { any.key } returns JpqlRenderSerializer
            excludeRecords { any.key }
        }
    }
}
