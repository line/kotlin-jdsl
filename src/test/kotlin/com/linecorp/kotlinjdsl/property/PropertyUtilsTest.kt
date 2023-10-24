package com.linecorp.kotlinjdsl.property

import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

class PropertyUtilsTest : WithAssertions {
    private class TestClass1 {
        val property1: String = ""
    }

    @Test
    fun getOwner() {
        assertThat(PropertyUtils.getOwner(TestClass1::property1)).isEqualTo(TestClass1::class)
    }
}
