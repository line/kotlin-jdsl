package com.linecorp.kotlinjdsl.query.clause.select

import com.linecorp.kotlinjdsl.query.spec.Froms
import com.linecorp.kotlinjdsl.query.spec.expression.ExpressionSpec
import com.linecorp.kotlinjdsl.test.WithKotlinJdslAssertions
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import jakarta.persistence.criteria.CompoundSelection
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.CriteriaQuery
import jakarta.persistence.criteria.Expression

@ExtendWith(MockKExtension::class)
internal class MultiSelectClauseTest : WithKotlinJdslAssertions {
    @MockK
    private lateinit var froms: Froms

    @MockK
    private lateinit var criteriaQuery: CriteriaQuery<Data1>

    @MockK
    private lateinit var criteriaBuilder: CriteriaBuilder

    @Test
    fun apply() {
        // given
        val expressionSpec1: ExpressionSpec<*> = mockk()
        val expressionSpec2: ExpressionSpec<*> = mockk()

        val expression1: Expression<*> = mockk()
        val expression2: Expression<*> = mockk()

        val constructExpression: CompoundSelection<Data1> = mockk()

        every { expressionSpec1.toCriteriaExpression(froms, criteriaQuery, criteriaBuilder) } returns expression1
        every { expressionSpec2.toCriteriaExpression(froms, criteriaQuery, criteriaBuilder) } returns expression2
        every { criteriaBuilder.construct(Data1::class.java, expression1, expression2) } returns constructExpression
        every { criteriaQuery.select(constructExpression) } returns criteriaQuery
        every { criteriaQuery.distinct(true) } returns criteriaQuery

        // when
        MultiSelectClause(Data1::class.java, distinct = true, listOf(expressionSpec1, expressionSpec2))
            .apply(froms, criteriaQuery, criteriaBuilder)

        // then
        verify(exactly = 1) {
            expressionSpec1.toCriteriaExpression(froms, criteriaQuery, criteriaBuilder)
            expressionSpec2.toCriteriaExpression(froms, criteriaQuery, criteriaBuilder)
            criteriaBuilder.construct(Data1::class.java, expression1, expression2)
            criteriaQuery.select(constructExpression)
            criteriaQuery.distinct(true)
        }

        confirmVerified(expressionSpec1, expressionSpec2, froms, criteriaQuery, criteriaBuilder)
    }

    class Data1
}
