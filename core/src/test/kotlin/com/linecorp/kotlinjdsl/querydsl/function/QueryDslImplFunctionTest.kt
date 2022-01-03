package com.linecorp.kotlinjdsl.querydsl.function

import com.linecorp.kotlinjdsl.query.clause.select.SingleSelectClause
import com.linecorp.kotlinjdsl.query.spec.expression.ColumnSpec
import com.linecorp.kotlinjdsl.query.spec.expression.EntitySpec
import com.linecorp.kotlinjdsl.query.spec.expression.FunctionSpec
import com.linecorp.kotlinjdsl.query.spec.expression.LiteralSpec
import com.linecorp.kotlinjdsl.querydsl.QueryDslImpl
import com.linecorp.kotlinjdsl.querydsl.expression.column
import com.linecorp.kotlinjdsl.test.WithKotlinJdslAssertions
import org.junit.jupiter.api.Test

internal class QueryDslImplFunctionTest : WithKotlinJdslAssertions {
    @Test
    fun function() {
        // when
        val actual = QueryDslImpl(String::class.java).apply {
            select(
                function(
                    name = "substring",
                    returnType = String::class.java,
                    column(Data1::id),
                    literal(1),
                    literal(2)
                )
            )
            from(entity(Data1::class))
        }

        // then
        val criteriaQuerySpec = actual.createCriteriaQuerySpec()

        assertThat(criteriaQuerySpec.select).isEqualTo(
            SingleSelectClause(
                returnType = String::class.java,
                distinct = false,
                expression = FunctionSpec(
                    "substring",
                    String::class.java,
                    listOf(
                        ColumnSpec<Data1>(EntitySpec(Data1::class.java), Data1::id.name),
                        LiteralSpec(1),
                        LiteralSpec(2),
                    )
                )
            )
        )
    }

    class Data1 {
        val id: String = "test"
        val name: String = "test_name"
    }
}
