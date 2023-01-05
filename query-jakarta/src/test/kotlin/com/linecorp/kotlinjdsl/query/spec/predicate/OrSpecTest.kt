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
import jakarta.persistence.criteria.*

@ExtendWith(MockKExtension::class)
internal class OrSpecTest : WithKotlinJdslAssertions {
    @MockK
    private lateinit var froms: Froms

    @MockK
    private lateinit var query: AbstractQuery<*>

    @MockK
    private lateinit var updateQuery: CriteriaUpdate<*>

    @MockK
    private lateinit var deleteQuery: CriteriaDelete<*>

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
        val orPredicate: Predicate = mockk()

        every { predicateSpec1.toCriteriaPredicate(any(), any<CriteriaQuery<*>>(), any()) } returns predicate1
        every { predicateSpec2.toCriteriaPredicate(any(), any<CriteriaQuery<*>>(), any()) } returns predicate2

        every { criteriaBuilder.or(any(), any()) } returns orPredicate

        // when
        val actual = OrSpec(listOf(predicateSpec1, predicateSpec2, predicateSpec3))
            .toCriteriaPredicate(froms, query, criteriaBuilder)

        // then
        assertThat(actual).isEqualTo(orPredicate)

        verify(exactly = 1) {
            predicateSpec1.toCriteriaPredicate(froms, query, criteriaBuilder)
            predicateSpec2.toCriteriaPredicate(froms, query, criteriaBuilder)
            criteriaBuilder.or(predicate1, predicate2)
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
        val actual = OrSpec(listOf(predicateSpec1, predicateSpec2)).toCriteriaPredicate(froms, query, criteriaBuilder)

        // then
        assertThat(actual).isEqualTo(andPredicate)

        verify(exactly = 1) {
            criteriaBuilder.conjunction()
        }

        confirmVerified(froms, query, criteriaBuilder)
    }

    @Test
    fun `update toCriteriaPredicate`() {
        // given
        val predicateSpec1: PredicateSpec = mockk()
        val predicateSpec2: PredicateSpec = mockk()
        val predicateSpec3: PredicateSpec? = null

        val predicate1: Predicate = mockk()
        val predicate2: Predicate = mockk()
        val orPredicate: Predicate = mockk()

        every { predicateSpec1.toCriteriaPredicate(any(), any<CriteriaUpdate<*>>(), any()) } returns predicate1
        every { predicateSpec2.toCriteriaPredicate(any(), any<CriteriaUpdate<*>>(), any()) } returns predicate2

        every { criteriaBuilder.or(any(), any()) } returns orPredicate

        // when
        val actual = OrSpec(listOf(predicateSpec1, predicateSpec2, predicateSpec3))
            .toCriteriaPredicate(froms, updateQuery, criteriaBuilder)

        // then
        assertThat(actual).isEqualTo(orPredicate)

        verify(exactly = 1) {
            predicateSpec1.toCriteriaPredicate(froms, updateQuery, criteriaBuilder)
            predicateSpec2.toCriteriaPredicate(froms, updateQuery, criteriaBuilder)
            criteriaBuilder.or(predicate1, predicate2)
        }

        confirmVerified(predicateSpec1, predicateSpec2, froms, updateQuery, criteriaBuilder)
    }

    @Test
    fun `update toCriteriaPredicate - if predicate is empty then return conjunction`() {
        // given
        val predicateSpec1: PredicateSpec? = null
        val predicateSpec2: PredicateSpec? = null

        val andPredicate: Predicate = mockk()

        every { criteriaBuilder.conjunction() } returns andPredicate

        // when
        val actual =
            OrSpec(listOf(predicateSpec1, predicateSpec2)).toCriteriaPredicate(froms, updateQuery, criteriaBuilder)

        // then
        assertThat(actual).isEqualTo(andPredicate)

        verify(exactly = 1) {
            criteriaBuilder.conjunction()
        }

        confirmVerified(froms, updateQuery, criteriaBuilder)
    }

    @Test
    fun `delete toCriteriaPredicate`() {
        // given
        val predicateSpec1: PredicateSpec = mockk()
        val predicateSpec2: PredicateSpec = mockk()
        val predicateSpec3: PredicateSpec? = null

        val predicate1: Predicate = mockk()
        val predicate2: Predicate = mockk()
        val orPredicate: Predicate = mockk()

        every { predicateSpec1.toCriteriaPredicate(any(), any<CriteriaDelete<*>>(), any()) } returns predicate1
        every { predicateSpec2.toCriteriaPredicate(any(), any<CriteriaDelete<*>>(), any()) } returns predicate2

        every { criteriaBuilder.or(any(), any()) } returns orPredicate

        // when
        val actual = OrSpec(listOf(predicateSpec1, predicateSpec2, predicateSpec3))
            .toCriteriaPredicate(froms, deleteQuery, criteriaBuilder)

        // then
        assertThat(actual).isEqualTo(orPredicate)

        verify(exactly = 1) {
            predicateSpec1.toCriteriaPredicate(froms, deleteQuery, criteriaBuilder)
            predicateSpec2.toCriteriaPredicate(froms, deleteQuery, criteriaBuilder)
            criteriaBuilder.or(predicate1, predicate2)
        }

        confirmVerified(predicateSpec1, predicateSpec2, froms, deleteQuery, criteriaBuilder)
    }

    @Test
    fun `delete toCriteriaPredicate - if predicate is empty then return conjunction`() {
        // given
        val predicateSpec1: PredicateSpec? = null
        val predicateSpec2: PredicateSpec? = null

        val andPredicate: Predicate = mockk()

        every { criteriaBuilder.conjunction() } returns andPredicate

        // when
        val actual =
            OrSpec(listOf(predicateSpec1, predicateSpec2)).toCriteriaPredicate(froms, deleteQuery, criteriaBuilder)

        // then
        assertThat(actual).isEqualTo(andPredicate)

        verify(exactly = 1) {
            criteriaBuilder.conjunction()
        }

        confirmVerified(froms, deleteQuery, criteriaBuilder)
    }
}
