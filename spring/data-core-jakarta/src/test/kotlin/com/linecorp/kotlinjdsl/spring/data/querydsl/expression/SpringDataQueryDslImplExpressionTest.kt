package com.linecorp.kotlinjdsl.spring.data.querydsl.expression

import com.linecorp.kotlinjdsl.query.spec.expression.*
import com.linecorp.kotlinjdsl.query.spec.predicate.PredicateSpec
import com.linecorp.kotlinjdsl.querydsl.expression.WhenDsl
import com.linecorp.kotlinjdsl.spring.data.querydsl.SpringDataQueryDslImpl
import com.linecorp.kotlinjdsl.test.WithKotlinJdslAssertions
import io.mockk.mockk
import org.junit.jupiter.api.Test

internal class SpringDataQueryDslImplExpressionTest : WithKotlinJdslAssertions {
    @Test
    fun entity() {
        val actual: ExpressionSpec<Data1>

        SpringDataQueryDslImpl(Data1::class.java).apply {
            actual = entity(Data1::class)
        }

        assertThat(actual).isEqualTo(EntitySpec(Data1::class.java))
    }

    @Test
    fun entityWithAlias() {
        val actual: ExpressionSpec<Data1>

        SpringDataQueryDslImpl(Data1::class.java).apply {
            actual = entity(Data1::class, "data1")
        }

        assertThat(actual).isEqualTo(EntitySpec(Data1::class.java, "data1"))
    }

    @Test
    fun alias() {
        val actual: ExpressionSpec<Data1>

        SpringDataQueryDslImpl(Data1::class.java).apply {
            actual = Data1::class.alias("data1")
        }

        assertThat(actual).isEqualTo(EntitySpec(Data1::class.java, "data1"))
    }

    @Test
    fun literal() {
        val actual: ExpressionSpec<Int>

        SpringDataQueryDslImpl(Data1::class.java).apply {
            actual = literal(10)
        }

        assertThat(actual).isEqualTo(LiteralSpec(10))
    }

    @Test
    fun nullLiteral() {
        val actual: ExpressionSpec<Int?>

        SpringDataQueryDslImpl(Data1::class.java).apply {
            actual = nullLiteral(Int::class.java)
        }

        assertThat(actual).isEqualTo(NullLiteralSpec(Int::class.java))
    }

    @Test
    fun col() {
        val actual: ExpressionSpec<Int>

        SpringDataQueryDslImpl(Data1::class.java).apply {
            actual = col(entity(Data1::class), Data1::id)
        }

        assertThat(actual).isEqualTo(ColumnSpec<Int>(EntitySpec(Data1::class.java), "id"))
    }

    @Test
    fun column() {
        val actual: ExpressionSpec<Int>

        SpringDataQueryDslImpl(Data1::class.java).apply {
            actual = column(entity(Data1::class), Data1::id)
        }

        assertThat(actual).isEqualTo(ColumnSpec<Int>(EntitySpec(Data1::class.java), "id"))
    }

    @Test
    fun max() {
        val actual: ExpressionSpec<Int>

        SpringDataQueryDslImpl(Data1::class.java).apply {
            actual = max(col(entity(Data1::class), Data1::id))
        }

        assertThat(actual).isEqualTo(
            MaxSpec(ColumnSpec<Int>(EntitySpec(Data1::class.java), "id"))
        )
    }

    @Test
    fun min() {
        val actual: ExpressionSpec<Int>

        SpringDataQueryDslImpl(Data1::class.java).apply {
            actual = min(col(entity(Data1::class), Data1::id))
        }

        assertThat(actual).isEqualTo(
            MinSpec(ColumnSpec<Int>(EntitySpec(Data1::class.java), "id"))
        )
    }

    @Test
    fun avg() {
        val actual: ExpressionSpec<Double>

        SpringDataQueryDslImpl(Data1::class.java).apply {
            actual = avg(col(entity(Data1::class), Data1::id))
        }

        assertThat(actual).isEqualTo(
            AvgSpec(ColumnSpec<Int>(EntitySpec(Data1::class.java), "id"))
        )
    }

    @Test
    fun sum() {
        val actual: ExpressionSpec<Int>

        SpringDataQueryDslImpl(Data1::class.java).apply {
            actual = sum(col(entity(Data1::class), Data1::id))
        }

        assertThat(actual).isEqualTo(
            SumSpec(ColumnSpec<Int>(EntitySpec(Data1::class.java), "id"))
        )
    }

    @Test
    fun count() {
        val actual: ExpressionSpec<Long>

        SpringDataQueryDslImpl(Data1::class.java).apply {
            actual = count(distinct = true, col(entity(Data1::class), Data1::id))
        }

        assertThat(actual).isEqualTo(
            CountSpec(distinct = true, ColumnSpec<Int>(EntitySpec(Data1::class.java), "id"))
        )
    }

    @Test
    fun countNonDistinct() {
        val actual: ExpressionSpec<Long>

        SpringDataQueryDslImpl(Data1::class.java).apply {
            actual = count(col(entity(Data1::class), Data1::id))
        }

        assertThat(actual).isEqualTo(
            CountSpec(distinct = false, ColumnSpec<Int>(EntitySpec(Data1::class.java), "id"))
        )
    }

    @Test
    fun countDistinct() {
        val actual: ExpressionSpec<Long>

        SpringDataQueryDslImpl(Data1::class.java).apply {
            actual = countDistinct(col(entity(Data1::class), Data1::id))
        }

        assertThat(actual).isEqualTo(
            CountSpec(distinct = true, ColumnSpec<Int>(EntitySpec(Data1::class.java), "id"))
        )
    }

    @Test
    fun greatest() {
        val actual: ExpressionSpec<Int>

        SpringDataQueryDslImpl(Data1::class.java).apply {
            actual = greatest(col(entity(Data1::class), Data1::id))
        }

        assertThat(actual).isEqualTo(
            GreatestSpec(ColumnSpec<Int>(EntitySpec(Data1::class.java), "id"))
        )
    }

    @Test
    fun least() {
        val actual: ExpressionSpec<Int>

        SpringDataQueryDslImpl(Data1::class.java).apply {
            actual = least(col(entity(Data1::class), Data1::id))
        }

        assertThat(actual).isEqualTo(
            LeastSpec(ColumnSpec<Int>(EntitySpec(Data1::class.java), "id"))
        )
    }

    @Test
    fun caseList() {
        val predicate1: PredicateSpec = mockk()
        val predicate2: PredicateSpec = mockk()

        val expression1: ExpressionSpec<String> = mockk()
        val expression2: ExpressionSpec<String> = mockk()

        val actual: ExpressionSpec<String?>

        SpringDataQueryDslImpl(Data1::class.java).apply {
            actual = case(
                listOf(
                    `when`(predicate1).then(expression1),
                    `when`(predicate2).then(expression2),
                ),
                `else` = nullLiteral(String::class.java)
            )
        }

        assertThat(actual).isEqualTo(
            CaseSpec(
                listOf(
                    CaseSpec.WhenSpec(predicate1, expression1),
                    CaseSpec.WhenSpec(predicate2, expression2)
                ), NullLiteralSpec(String::class.java)
            )
        )
    }

    @Test
    fun caseVararg() {
        val predicate1: PredicateSpec = mockk()
        val predicate2: PredicateSpec = mockk()

        val expression1: ExpressionSpec<String> = mockk()
        val expression2: ExpressionSpec<String> = mockk()

        val actual: ExpressionSpec<String?>

        SpringDataQueryDslImpl(Data1::class.java).apply {
            actual = case(
                `when`(predicate1).then(expression1),
                `when`(predicate2).then(expression2),
                `else` = nullLiteral(String::class.java)
            )
        }

        assertThat(actual).isEqualTo(
            CaseSpec(
                listOf(
                    CaseSpec.WhenSpec(predicate1, expression1),
                    CaseSpec.WhenSpec(predicate2, expression2)
                ), NullLiteralSpec(String::class.java)
            )
        )
    }

    @Test
    fun `when`() {
        val predicate1: PredicateSpec = mockk()
        val expression1: ExpressionSpec<String> = mockk()

        val actual: CaseSpec.WhenSpec<String>

        SpringDataQueryDslImpl(Data1::class.java).apply {
            actual = `when`(predicate1, expression1)
        }

        assertThat(actual).isEqualTo(CaseSpec.WhenSpec(predicate1, expression1))
    }

    @Test
    fun whenWithPredicate() {
        val predicate1: PredicateSpec = mockk()

        val actual: WhenDsl

        SpringDataQueryDslImpl(Data1::class.java).apply {
            actual = `when`(predicate1)
        }

        assertThat(actual).isEqualTo(WhenDsl(predicate1))
    }

    @Test
    fun whenWithLambda() {
        val predicate1: PredicateSpec = mockk()

        val actual: WhenDsl

        SpringDataQueryDslImpl(Data1::class.java).apply {
            actual = `when` { predicate1 }
        }

        assertThat(actual).isEqualTo(WhenDsl(predicate1))
    }


    @Test
    fun functionVarArg() {
        // when
        val actual: FunctionSpec<String>
        SpringDataQueryDslImpl(String::class.java).apply {
            actual = function(
                "substring",
                String::class.java,
                column(EntitySpec(Data1::class.java), Data1::id),
                literal(1),
                literal(2)
            )
        }

        // then
        assertThat(actual).isEqualTo(
            FunctionSpec(
                name = "substring",
                returnType = String::class.java,
                expressions = listOf(
                    ColumnSpec(EntitySpec(Data1::class.java), Data1::id.name),
                    LiteralSpec(1),
                    LiteralSpec(2)
                )
            )
        )
    }

    @Test
    fun functionList() {
        // when
        val actual: FunctionSpec<String>
        SpringDataQueryDslImpl(String::class.java).apply {
            actual = function(
                "substring",
                String::class.java,
                listOf(
                    column(EntitySpec(Data1::class.java), Data1::id),
                    literal(1),
                    literal(2)
                )
            )
        }

        // then
        assertThat(actual).isEqualTo(
            FunctionSpec(
                name = "substring",
                returnType = String::class.java,
                expressions = listOf(
                    ColumnSpec(EntitySpec(Data1::class.java), Data1::id.name),
                    LiteralSpec(1),
                    LiteralSpec(2)
                )
            )
        )
    }

    class Data1 {
        val id: Int = 10
    }
}
