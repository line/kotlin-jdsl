package com.linecorp.kotlinjdsl.query.spec.predicate

import com.linecorp.kotlinjdsl.query.spec.Froms
import com.linecorp.kotlinjdsl.test.WithKotlinJdslAssertions
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import javax.persistence.criteria.AbstractQuery
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.Predicate

@ExtendWith(MockKExtension::class)
internal class AndSpecTest : WithKotlinJdslAssertions {
    @MockK
    private lateinit var froms: Froms

    @MockK
    private lateinit var query: AbstractQuery<*>

    @MockK
    private lateinit var criteriaBuilder: CriteriaBuilder

    @Test
    fun toCriteriaPredicate() {
        // given
        val predicateSpec1: PredicateSpec = mockk()
        val predicateSpec2: PredicateSpec = mockk()
        val predicateSpec3: PredicateSpec? = null

        val predicate1: Predicate = mockk()
        val predicate2: Predicate = mockk()
        val andPredicate: Predicate = mockk()

        every { predicateSpec1.toCriteriaPredicate(any(), any(), any()) } returns predicate1
        every { predicateSpec2.toCriteriaPredicate(any(), any(), any()) } returns predicate2

        every { criteriaBuilder.and(any(), any()) } returns andPredicate

        // when
        val actual = AndSpec(listOf(predicateSpec1, predicateSpec2, predicateSpec3))
            .toCriteriaPredicate(froms, query, criteriaBuilder)

        // then
        assertThat(actual).isEqualTo(andPredicate)

        verify(exactly = 1) {
            predicateSpec1.toCriteriaPredicate(froms, query, criteriaBuilder)
            predicateSpec2.toCriteriaPredicate(froms, query, criteriaBuilder)
            criteriaBuilder.and(predicate1, predicate2)
        }

        confirmVerified(predicateSpec1, predicateSpec2, froms, query, criteriaBuilder)
    }

    @Test
    fun `toCriteriaPredicate - if predicate is empty then return conjunction`() {
        // given
        val predicateSpec1: PredicateSpec? = null
        val predicateSpec2: PredicateSpec? = null

        val andPredicate: Predicate = mockk()

        every { criteriaBuilder.conjunction() } returns andPredicate

        // when
        val actual = AndSpec(listOf(predicateSpec1, predicateSpec2)).toCriteriaPredicate(froms, query, criteriaBuilder)

        // then
        assertThat(actual).isEqualTo(andPredicate)

        verify(exactly = 1) {
            criteriaBuilder.conjunction()
        }

        confirmVerified(froms, query, criteriaBuilder)
    }
}