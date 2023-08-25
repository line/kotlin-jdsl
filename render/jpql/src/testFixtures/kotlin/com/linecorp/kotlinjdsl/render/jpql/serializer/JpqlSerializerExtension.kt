package com.linecorp.kotlinjdsl.render.jpql.serializer

import com.linecorp.kotlinjdsl.querymodel.QueryPart
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.introspector.JpqlEntityDescription
import com.linecorp.kotlinjdsl.render.jpql.introspector.JpqlRenderIntrospector
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import io.mockk.*
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.api.extension.InvocationInterceptor
import org.junit.jupiter.api.extension.ReflectiveInvocationContext
import java.lang.reflect.Method
import kotlin.reflect.KClass
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

        if (any is JpqlRenderSerializer && isMockKMock(any)) {
            every { any.serialize(any<QueryPart>(), any<JpqlWriter>(), any<RenderContext>()) } just runs
        }

        if (any is JpqlRenderIntrospector && isMockKMock(any)) {
            every { any.introspect(any<KClass<*>>()) } returns mockk<JpqlEntityDescription>()
        }

        if (any is JpqlWriter && isMockKMock(any)) {
            every { any.write(any<String>()) } just runs
            every { any.writeIfAbsent(any<String>()) } just runs
            every { any.writeEach<Any>(any(), any(), any(), any(), any()) } answers {
                val predicates: Iterable<Any> = arg(0)
                val write: (Any) -> Unit = arg(4)

                predicates.forEach { predicate -> write(predicate) }
            }
            every { any.writeParentheses(any<() -> Unit>()) } answers {
                val inner: () -> Unit = arg(0)

                inner()
            }
            every { any.writeParam(any<String>()) } just runs
            every { any.writeParam(any<String>(), any<String>()) } just runs
        }
    }
}
