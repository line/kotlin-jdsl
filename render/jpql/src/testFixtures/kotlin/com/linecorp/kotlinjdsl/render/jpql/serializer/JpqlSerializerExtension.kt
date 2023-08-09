package com.linecorp.kotlinjdsl.render.jpql.serializer

import io.mockk.every
import io.mockk.excludeRecords
import io.mockk.isMockKMock
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.api.extension.InvocationInterceptor
import org.junit.jupiter.api.extension.ReflectiveInvocationContext
import java.lang.reflect.Method

class JpqlSerializerExtension : InvocationInterceptor {
    override fun interceptTestMethod(
        invocation: InvocationInterceptor.Invocation<Void>,
        invocationContext: ReflectiveInvocationContext<Method>,
        extensionContext: ExtensionContext?,
    ) {
        val args = invocationContext.arguments

        args.forEach {
            if (it is JpqlRenderSerializer && isMockKMock(it)) {
                every { it.key } returns JpqlRenderSerializer
                excludeRecords { it.key }
            }
        }

        super.interceptTestMethod(invocation, invocationContext, extensionContext)
    }
}
