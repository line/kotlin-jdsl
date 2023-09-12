package com.linecorp.kotlinjdsl.clazz

import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

class ClassUtilsTest : WithAssertions {
    @Test
    fun isPresent() {
        assertThat(ClassUtils.isPresent("java.lang.String")).isTrue
        assertThat(ClassUtils.isPresent("java.lang.String", ClassUtilsTest::class.java.classLoader)).isTrue

        assertThat(ClassUtils.isPresent("java.lang.NotExists")).isFalse
        assertThat(ClassUtils.isPresent("java.lang.NotExists", ClassUtilsTest::class.java.classLoader)).isFalse
    }
}
