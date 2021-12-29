package com.linecorp.kotlinjdsl.test

import com.linecorp.kotlinjdsl.test.time.Time
import org.assertj.core.api.ThrowableAssert
import org.assertj.core.api.WithAssertions
import kotlin.reflect.KClass

interface WithKotlinJdslAssertions: WithAssertions {
    fun assertTrue(condition: () -> Boolean) {
        assertThat(condition()).isTrue
    }

    fun assertFalse(condition: () -> Boolean) {
        assertThat(condition()).isFalse
    }

    fun <T : Throwable> catchThrowable(type: KClass<out T>, executable: () -> Unit): T? {
        val throwable = ThrowableAssert.ThrowingCallable {
            executable()
        }

        return catchThrowableOfType(throwable, type.java)
    }

    fun <T> withMockTime(mockTime: Time, executable: () -> T): T {
        Time.startMocking(mockTime)

        val result: T = executable()

        Time.finishMocking()

        return result
    }
}