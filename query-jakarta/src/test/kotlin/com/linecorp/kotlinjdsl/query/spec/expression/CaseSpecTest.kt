package com.linecorp.kotlinjdsl.query.spec.expression

import com.linecorp.kotlinjdsl.query.spec.Froms
import com.linecorp.kotlinjdsl.query.spec.predicate.PredicateSpec
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
internal class CaseSpecTest : WithKotlinJdslAssertions {
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
    fun toCriteriaExpression() {
        // given
        val predicateSpec1 = mockk<PredicateSpec>()
        val predicateSpec2 = mockk<PredicateSpec>()

        val predicate1 = mockk<Predicate>()
        val predicate2 = mockk<Predicate>()

        val resultSpec1 = mockk<ExpressionSpec<Int>>()
        val resultSpec2 = mockk<ExpressionSpec<Int>>()

        val result1 = mockk<Expression<Int>>()
        val result2 = mockk<Expression<Int>>()

        val when1 = CaseSpec.WhenSpec(predicateSpec1, resultSpec1)
        val when2 = CaseSpec.WhenSpec(predicateSpec2, resultSpec2)

        val otherwise1 = mockk<ExpressionSpec<Int?>>()
        val otherwise1Expression = mockk<Expression<Int?>>()

        val case = mockk<CriteriaBuilder.Case<Int>>()
        val caseExpression = mockk<Expression<Int>>()

        every { predicateSpec1.toCriteriaPredicate(any(), any<CriteriaQuery<*>>(), any()) } returns predicate1
        every { predicateSpec2.toCriteriaPredicate(any(), any<CriteriaQuery<*>>(), any()) } returns predicate2

        every { resultSpec1.toCriteriaExpression(any(), any<CriteriaQuery<*>>(), any()) } returns result1
        every { resultSpec2.toCriteriaExpression(any(), any<CriteriaQuery<*>>(), any()) } returns result2

        every { otherwise1.toCriteriaExpression(any(), any<CriteriaQuery<*>>(), any()) } returns otherwise1Expression

        every { criteriaBuilder.selectCase<Int>() } returns case
        every { case.`when`(any(), any<Expression<Int>>()) } returns case
        every { case.otherwise(any<Expression<Int>>()) } returns caseExpression

        // when
        val spec = CaseSpec(listOf(when1, when2), otherwise1)

        val actual = spec.toCriteriaExpression(froms, query, criteriaBuilder)

        // then
        assertThat(actual).isEqualTo(caseExpression)

        verify(exactly = 1) {
            predicateSpec1.toCriteriaPredicate(froms, query, criteriaBuilder)
            predicateSpec2.toCriteriaPredicate(froms, query, criteriaBuilder)

            resultSpec1.toCriteriaExpression(froms, query, criteriaBuilder)
            resultSpec2.toCriteriaExpression(froms, query, criteriaBuilder)

            otherwise1.toCriteriaExpression(froms, query, criteriaBuilder)

            criteriaBuilder.selectCase<Int>()
            case.`when`(predicate1, result1)
            case.`when`(predicate2, result2)
            case.otherwise(otherwise1Expression)
        }

        confirmVerified(
            predicateSpec1, predicateSpec2,
            resultSpec1, resultSpec2,
            otherwise1, case,
            froms, query, criteriaBuilder
        )
    }

    @Test
    fun `update toCriteriaExpression`() {
        // given
        val predicateSpec1 = mockk<PredicateSpec>()
        val predicateSpec2 = mockk<PredicateSpec>()

        val predicate1 = mockk<Predicate>()
        val predicate2 = mockk<Predicate>()

        val resultSpec1 = mockk<ExpressionSpec<Int>>()
        val resultSpec2 = mockk<ExpressionSpec<Int>>()

        val result1 = mockk<Expression<Int>>()
        val result2 = mockk<Expression<Int>>()

        val when1 = CaseSpec.WhenSpec(predicateSpec1, resultSpec1)
        val when2 = CaseSpec.WhenSpec(predicateSpec2, resultSpec2)

        val otherwise1 = mockk<ExpressionSpec<Int?>>()
        val otherwise1Expression = mockk<Expression<Int?>>()

        val case = mockk<CriteriaBuilder.Case<Int>>()
        val caseExpression = mockk<Expression<Int>>()

        every { predicateSpec1.toCriteriaPredicate(any(), any<CriteriaUpdate<*>>(), any()) } returns predicate1
        every { predicateSpec2.toCriteriaPredicate(any(), any<CriteriaUpdate<*>>(), any()) } returns predicate2

        every { resultSpec1.toCriteriaExpression(any(), any<CriteriaUpdate<*>>(), any()) } returns result1
        every { resultSpec2.toCriteriaExpression(any(), any<CriteriaUpdate<*>>(), any()) } returns result2

        every { otherwise1.toCriteriaExpression(any(), any<CriteriaUpdate<*>>(), any()) } returns otherwise1Expression

        every { criteriaBuilder.selectCase<Int>() } returns case
        every { case.`when`(any(), any<Expression<Int>>()) } returns case
        every { case.otherwise(any<Expression<Int>>()) } returns caseExpression

        // when
        val spec = CaseSpec(listOf(when1, when2), otherwise1)

        val actual = spec.toCriteriaExpression(froms, updateQuery, criteriaBuilder)

        // then
        assertThat(actual).isEqualTo(caseExpression)

        verify(exactly = 1) {
            predicateSpec1.toCriteriaPredicate(froms, updateQuery, criteriaBuilder)
            predicateSpec2.toCriteriaPredicate(froms, updateQuery, criteriaBuilder)

            resultSpec1.toCriteriaExpression(froms, updateQuery, criteriaBuilder)
            resultSpec2.toCriteriaExpression(froms, updateQuery, criteriaBuilder)

            otherwise1.toCriteriaExpression(froms, updateQuery, criteriaBuilder)

            criteriaBuilder.selectCase<Int>()
            case.`when`(predicate1, result1)
            case.`when`(predicate2, result2)
            case.otherwise(otherwise1Expression)
        }

        confirmVerified(
            predicateSpec1, predicateSpec2,
            resultSpec1, resultSpec2,
            otherwise1, case,
            froms, updateQuery, criteriaBuilder
        )
    }

    @Test
    fun `delete toCriteriaExpression`() {
        // given
        val predicateSpec1 = mockk<PredicateSpec>()
        val predicateSpec2 = mockk<PredicateSpec>()

        val predicate1 = mockk<Predicate>()
        val predicate2 = mockk<Predicate>()

        val resultSpec1 = mockk<ExpressionSpec<Int>>()
        val resultSpec2 = mockk<ExpressionSpec<Int>>()

        val result1 = mockk<Expression<Int>>()
        val result2 = mockk<Expression<Int>>()

        val when1 = CaseSpec.WhenSpec(predicateSpec1, resultSpec1)
        val when2 = CaseSpec.WhenSpec(predicateSpec2, resultSpec2)

        val otherwise1 = mockk<ExpressionSpec<Int?>>()
        val otherwise1Expression = mockk<Expression<Int?>>()

        val case = mockk<CriteriaBuilder.Case<Int>>()
        val caseExpression = mockk<Expression<Int>>()

        every { predicateSpec1.toCriteriaPredicate(any(), any<CriteriaDelete<*>>(), any()) } returns predicate1
        every { predicateSpec2.toCriteriaPredicate(any(), any<CriteriaDelete<*>>(), any()) } returns predicate2

        every { resultSpec1.toCriteriaExpression(any(), any<CriteriaDelete<*>>(), any()) } returns result1
        every { resultSpec2.toCriteriaExpression(any(), any<CriteriaDelete<*>>(), any()) } returns result2

        every { otherwise1.toCriteriaExpression(any(), any<CriteriaDelete<*>>(), any()) } returns otherwise1Expression

        every { criteriaBuilder.selectCase<Int>() } returns case
        every { case.`when`(any(), any<Expression<Int>>()) } returns case
        every { case.otherwise(any<Expression<Int>>()) } returns caseExpression

        // when
        val spec = CaseSpec(listOf(when1, when2), otherwise1)

        val actual = spec.toCriteriaExpression(froms, deleteQuery, criteriaBuilder)

        // then
        assertThat(actual).isEqualTo(caseExpression)

        verify(exactly = 1) {
            predicateSpec1.toCriteriaPredicate(froms, deleteQuery, criteriaBuilder)
            predicateSpec2.toCriteriaPredicate(froms, deleteQuery, criteriaBuilder)

            resultSpec1.toCriteriaExpression(froms, deleteQuery, criteriaBuilder)
            resultSpec2.toCriteriaExpression(froms, deleteQuery, criteriaBuilder)

            otherwise1.toCriteriaExpression(froms, deleteQuery, criteriaBuilder)

            criteriaBuilder.selectCase<Int>()
            case.`when`(predicate1, result1)
            case.`when`(predicate2, result2)
            case.otherwise(otherwise1Expression)
        }

        confirmVerified(
            predicateSpec1, predicateSpec2,
            resultSpec1, resultSpec2,
            otherwise1, case,
            froms, deleteQuery, criteriaBuilder
        )
    }
}
