package com.linecorp.kotlinjdsl.iterable

import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

class IterableUtilsTest : WithAssertions {
    @Test
    fun isEmpty() {
        assertThat(IterableUtils.isEmpty(listOf<Int>())).isTrue
        assertThat(IterableUtils.isEmpty(Iterable { iterator<Int> { } })).isTrue

        assertThat(IterableUtils.isEmpty(listOf(1))).isFalse
        assertThat(IterableUtils.isEmpty(Iterable { iterator { yield(1) } })).isFalse
    }

    @Test
    fun isNotEmpty() {
        assertThat(IterableUtils.isNotEmpty(listOf<Int>())).isFalse
        assertThat(IterableUtils.isNotEmpty(Iterable { iterator<Int> { } })).isFalse

        assertThat(IterableUtils.isNotEmpty(listOf(1))).isTrue
        assertThat(IterableUtils.isNotEmpty(Iterable { iterator { yield(1) } })).isTrue
    }
}
