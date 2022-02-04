package com.linecorp.kotlinjdsl.query.spec.predicate

import com.linecorp.kotlinjdsl.test.WithKotlinJdslAssertions
import org.junit.jupiter.api.Test

internal class PredicateSpecTest : WithKotlinJdslAssertions {
    private val predicateSpec1: PredicateSpec = PredicateSpec { _, _, criteriaBuilder -> criteriaBuilder.conjunction() }
    private val predicateSpec2: PredicateSpec = PredicateSpec { _, _, criteriaBuilder -> criteriaBuilder.conjunction() }

    @Test
    fun isEmpty() {
        assertThat(PredicateSpec.empty.isEmpty()).isTrue
    }

    @Test
    fun reverse() {
        assertThat(predicateSpec1.reverse()).isEqualTo(NotSpec(predicateSpec1))
    }

    @Test
    fun and() {
        assertThat(predicateSpec1 and predicateSpec2).isEqualTo(AndSpec(listOf(predicateSpec1, predicateSpec2)))
    }

    @Test
    fun or() {
        assertThat(predicateSpec1 or predicateSpec2).isEqualTo(OrSpec(listOf(predicateSpec1, predicateSpec2)))
    }
}