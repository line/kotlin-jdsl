package com.linecorp.kotlinjdsl.query.clause.function

import com.linecorp.kotlinjdsl.query.spec.Froms
import com.linecorp.kotlinjdsl.query.spec.expression.ColumnSpec
import com.linecorp.kotlinjdsl.query.spec.expression.EntitySpec
import com.linecorp.kotlinjdsl.query.spec.expression.FunctionSpec
import com.linecorp.kotlinjdsl.test.WithKotlinJdslAssertions
import com.linecorp.kotlinjdsl.test.entity.order.OrderItem
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
import javax.persistence.criteria.Path

@ExtendWith(MockKExtension::class)
internal class QueryFunctionClauseImplTest : WithKotlinJdslAssertions {
    @MockK
    private lateinit var froms: Froms

    @MockK
    private lateinit var query: AbstractQuery<*>

    @MockK
    private lateinit var criteriaBuilder: CriteriaBuilder

    @Test
    fun apply() {
        // given
        val path = mockk<Path<Long>>()
        val columnSpec = ColumnSpec<Long>(EntitySpec(OrderItem::class.java), OrderItem::productId.name)
        val spec = FunctionSpec(
            name = "sqrt",
            returnType = Double::class.java,
            expressions = listOf(columnSpec)
        )
        every { froms[columnSpec.entity] } returns mockk {
            every { this@mockk.get<Long>(columnSpec.path) } returns path
        }
        every {
            criteriaBuilder.function(
                spec.name,
                spec.returnType,
                path
            )
        } returns mockk()

        // when
        QueryFunctionClauseImpl(spec).apply(froms, query, criteriaBuilder)

        // then
        verify(exactly = 1) {
            criteriaBuilder.function(
                "sqrt",
                Double::class.java,
                path
            )
        }

        confirmVerified(criteriaBuilder)
    }
}
