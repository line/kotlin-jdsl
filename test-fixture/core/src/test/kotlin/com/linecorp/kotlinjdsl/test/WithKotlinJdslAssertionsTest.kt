package com.linecorp.kotlinjdsl.test

import com.linecorp.kotlinjdsl.test.time.Time
import org.junit.jupiter.api.Test

internal class WithKotlinJdslAssertionsTest : WithKotlinJdslAssertions {
    @Test
    fun withMockTime() {
        withMockTime(Time.of("2020-01-01 10:00:00")) {
            assertThat(Time.now().toString("yyyy-MM-dd HH:mm:ss")).isEqualTo("2020-01-01 10:00:00")
        }
    }

    @Test
    fun catchThrowable() {
        val exception = catchThrowable(IllegalStateException::class) {
            throw IllegalStateException("TEST")
        }

        assertThat(exception).isInstanceOf(IllegalStateException::class.java).hasMessage("TEST")
    }

    @Test
    fun `catchThrowable - throw assertion exception if unexpected exception occurs`() {
        val exception = catchThrowable {
            catchThrowable(IllegalStateException::class) {
                throw IllegalArgumentException("TEST")
            }
        }

        assertThat(exception).isInstanceOf(AssertionError::class.java)
    }
}