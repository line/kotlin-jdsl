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
internal class EmptyPredicateSpecTest : WithKotlinJdslAssertions {
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
        toCriteriaPredicate { EmptyPredicateSpec.toCriteriaPredicate(froms, query, criteriaBuilder) }
    }

    @Test
    fun `update toCriteriaPredicate`() {
        toCriteriaPredicate { EmptyPredicateSpec.toCriteriaPredicate(froms, updateQuery, criteriaBuilder) }
    }

    @Test
    fun `delete toCriteriaPredicate`() {
        toCriteriaPredicate { EmptyPredicateSpec.toCriteriaPredicate(froms, deleteQuery, criteriaBuilder) }
    }

    private fun toCriteriaPredicate(predicate: () -> Predicate) {
        // given
        val emptyPredicate: Predicate = mockk()

        every { criteriaBuilder.conjunction() } returns emptyPredicate

        // when
        val actual = predicate()

        // then
        assertThat(actual).isEqualTo(emptyPredicate)

        verify(exactly = 1) {
            criteriaBuilder.conjunction()
        }

        confirmVerified(froms, query, criteriaBuilder)
    }
}
