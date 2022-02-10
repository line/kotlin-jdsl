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
import javax.persistence.criteria.CriteriaUpdate
import javax.persistence.criteria.Predicate

@ExtendWith(MockKExtension::class)
internal class NotSpecTest : WithKotlinJdslAssertions {
    @MockK
    private lateinit var froms: Froms

    @MockK
    private lateinit var query: AbstractQuery<*>

    @MockK
    private lateinit var updateQuery: CriteriaUpdate<*>

    @MockK
    private lateinit var criteriaBuilder: CriteriaBuilder

    @Test
    fun toCriteriaPredicate() {
        // given
        val predicateSpec: PredicateSpec = mockk()
        val predicate: Predicate = mockk()

        val notPredicate: Predicate = mockk()

        every { predicateSpec.toCriteriaPredicate(froms, query, criteriaBuilder) } returns predicate
        every { criteriaBuilder.not(predicate) } returns notPredicate

        // when
        val actual = NotSpec(predicateSpec).toCriteriaPredicate(froms, query, criteriaBuilder)

        // then
        assertThat(actual).isEqualTo(notPredicate)

        verify(exactly = 1) {
            predicateSpec.toCriteriaPredicate(froms, query, criteriaBuilder)
            criteriaBuilder.not(predicate)
        }

        confirmVerified(predicateSpec, froms, query, criteriaBuilder)
    }

    @Test
    fun `update toCriteriaPredicate`() {
        // given
        val predicateSpec: PredicateSpec = mockk()
        val predicate: Predicate = mockk()

        val notPredicate: Predicate = mockk()

        every { predicateSpec.toCriteriaPredicate(froms, updateQuery, criteriaBuilder) } returns predicate
        every { criteriaBuilder.not(predicate) } returns notPredicate

        // when
        val actual = NotSpec(predicateSpec).toCriteriaPredicate(froms, updateQuery, criteriaBuilder)

        // then
        assertThat(actual).isEqualTo(notPredicate)

        verify(exactly = 1) {
            predicateSpec.toCriteriaPredicate(froms, updateQuery, criteriaBuilder)
            criteriaBuilder.not(predicate)
        }

        confirmVerified(predicateSpec, froms, updateQuery, criteriaBuilder)
    }
}
