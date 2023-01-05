package com.linecorp.kotlinjdsl.spring.data.querydsl.predicate

import com.linecorp.kotlinjdsl.query.spec.expression.ExpressionSpec
import com.linecorp.kotlinjdsl.query.spec.predicate.*
import com.linecorp.kotlinjdsl.spring.data.querydsl.SpringDataQueryDslImpl
import com.linecorp.kotlinjdsl.test.WithKotlinJdslAssertions
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.springframework.data.domain.Range

internal class SpringDataQueryDslImplPredicateTest : WithKotlinJdslAssertions {
    @Test
    fun not() {
        val predicateSpec: PredicateSpec = mockk()

        val actual: PredicateSpec

        SpringDataQueryDslImpl(String::class.java).apply {
            actual = not(predicateSpec)
        }

        assertThat(actual).isEqualTo(NotSpec(predicateSpec))
    }

    @Test
    fun andVararg() {
        val predicateSpec1: PredicateSpec = mockk()
        val predicateSpec2: PredicateSpec = mockk()

        val actual: PredicateSpec

        SpringDataQueryDslImpl(String::class.java).apply {
            actual = and(predicateSpec1, predicateSpec2)
        }

        assertThat(actual).isEqualTo(AndSpec(listOf(predicateSpec1, predicateSpec2)))
    }

    @Test
    fun andList() {
        val predicateSpec1: PredicateSpec = mockk()
        val predicateSpec2: PredicateSpec = mockk()

        val actual: PredicateSpec

        SpringDataQueryDslImpl(String::class.java).apply {
            actual = and(listOf(predicateSpec1, predicateSpec2))
        }

        assertThat(actual).isEqualTo(AndSpec(listOf(predicateSpec1, predicateSpec2)))
    }

    @Test
    fun orVararg() {
        val predicateSpec1: PredicateSpec = mockk()
        val predicateSpec2: PredicateSpec = mockk()

        val actual: PredicateSpec

        SpringDataQueryDslImpl(String::class.java).apply {
            actual = or(predicateSpec1, predicateSpec2)
        }

        assertThat(actual).isEqualTo(OrSpec(listOf(predicateSpec1, predicateSpec2)))
    }

    @Test
    fun orList() {
        val predicateSpec1: PredicateSpec = mockk()
        val predicateSpec2: PredicateSpec = mockk()

        val actual: PredicateSpec

        SpringDataQueryDslImpl(String::class.java).apply {
            actual = or(listOf(predicateSpec1, predicateSpec2))
        }

        assertThat(actual).isEqualTo(OrSpec(listOf(predicateSpec1, predicateSpec2)))
    }

    @Test
    fun equalValue() {
        val expressionSpec: ExpressionSpec<String> = mockk()
        val value = "test"

        val actual: PredicateSpec

        SpringDataQueryDslImpl(String::class.java).apply {
            actual = expressionSpec.equal(value)
        }

        assertThat(actual).isEqualTo(EqualValueSpec(expressionSpec, value))
    }

    @Test
    fun equalExpression() {
        val expressionSpec1: ExpressionSpec<String> = mockk()
        val expressionSpec2: ExpressionSpec<String> = mockk()

        val actual: PredicateSpec

        SpringDataQueryDslImpl(String::class.java).apply {
            actual = expressionSpec1.equal(expressionSpec2)
        }

        assertThat(actual).isEqualTo(EqualExpressionSpec(expressionSpec1, expressionSpec2))
    }

    @Test
    fun notEqualValue() {
        val expressionSpec: ExpressionSpec<String> = mockk()
        val value = "test"

        val actual: PredicateSpec

        SpringDataQueryDslImpl(String::class.java).apply {
            actual = expressionSpec.notEqual(value)
        }

        assertThat(actual).isEqualTo(NotSpec(EqualValueSpec(expressionSpec, value)))
    }

    @Test
    fun notEqualExpression() {
        val expressionSpec1: ExpressionSpec<String> = mockk()
        val expressionSpec2: ExpressionSpec<String> = mockk()

        val actual: PredicateSpec

        SpringDataQueryDslImpl(String::class.java).apply {
            actual = expressionSpec1.notEqual(expressionSpec2)
        }

        assertThat(actual).isEqualTo(NotSpec(EqualExpressionSpec(expressionSpec1, expressionSpec2)))
    }

    @Test
    fun inValueVararg() {
        val expressionSpec: ExpressionSpec<String> = mockk()
        val value1 = "test1"
        val value2 = "test2"

        val actual: PredicateSpec

        SpringDataQueryDslImpl(String::class.java).apply {
            actual = expressionSpec.`in`(value1, value2)
        }

        assertThat(actual).isEqualTo(InValueSpec(expressionSpec, listOf(value1, value2)))
    }

    @Test
    fun inValueList() {
        val expressionSpec: ExpressionSpec<String> = mockk()
        val value1 = "test1"
        val value2 = "test2"

        val actual: PredicateSpec

        SpringDataQueryDslImpl(String::class.java).apply {
            actual = expressionSpec.`in`(listOf(value1, value2))
        }

        assertThat(actual).isEqualTo(InValueSpec(expressionSpec, listOf(value1, value2)))
    }

    @Test
    fun inExpressionVararg() {
        val expressionSpec1: ExpressionSpec<String> = mockk()
        val expressionSpec2: ExpressionSpec<String> = mockk()
        val expressionSpec3: ExpressionSpec<String> = mockk()

        val actual: PredicateSpec

        SpringDataQueryDslImpl(String::class.java).apply {
            actual = expressionSpec1.`in`(expressionSpec2, expressionSpec3)
        }

        assertThat(actual).isEqualTo(InExpressionSpec(expressionSpec1, listOf(expressionSpec2, expressionSpec3)))
    }

    @Test
    fun inExpressionList() {
        val expressionSpec1: ExpressionSpec<String> = mockk()
        val expressionSpec2: ExpressionSpec<String> = mockk()
        val expressionSpec3: ExpressionSpec<String> = mockk()

        val actual: PredicateSpec

        SpringDataQueryDslImpl(String::class.java).apply {
            actual = expressionSpec1.`in`(listOf(expressionSpec2, expressionSpec3))
        }

        assertThat(actual).isEqualTo(InExpressionSpec(expressionSpec1, listOf(expressionSpec2, expressionSpec3)))
    }

    @Test
    fun greaterThanOrEqualToValue() {
        val expressionSpec: ExpressionSpec<String> = mockk()
        val value = "test1"

        val actual: PredicateSpec

        SpringDataQueryDslImpl(String::class.java).apply {
            actual = expressionSpec.greaterThanOrEqualTo(value)
        }

        assertThat(actual).isEqualTo(GreaterThanValueSpec(expressionSpec, value, true))
    }

    @Test
    fun greaterThanValue() {
        val expressionSpec: ExpressionSpec<String> = mockk()
        val value = "test1"

        val actual: PredicateSpec

        SpringDataQueryDslImpl(String::class.java).apply {
            actual = expressionSpec.greaterThan(value)
        }

        assertThat(actual).isEqualTo(GreaterThanValueSpec(expressionSpec, value, false))
    }

    @Test
    fun greaterThanInclusiveValue() {
        val expressionSpec: ExpressionSpec<String> = mockk()
        val value = "test1"

        val actual: PredicateSpec

        SpringDataQueryDslImpl(String::class.java).apply {
            actual = expressionSpec.greaterThan(value, inclusive = true)
        }

        assertThat(actual).isEqualTo(GreaterThanValueSpec(expressionSpec, value, true))
    }

    @Test
    fun greaterThanOrEqualToExpression() {
        val expressionSpec1: ExpressionSpec<String> = mockk()
        val expressionSpec2: ExpressionSpec<String> = mockk()

        val actual: PredicateSpec

        SpringDataQueryDslImpl(String::class.java).apply {
            actual = expressionSpec1.greaterThanOrEqualTo(expressionSpec2)
        }

        assertThat(actual).isEqualTo(GreaterThanExpressionSpec(expressionSpec1, expressionSpec2, true))
    }

    @Test
    fun greaterThanExpression() {
        val expressionSpec1: ExpressionSpec<String> = mockk()
        val expressionSpec2: ExpressionSpec<String> = mockk()

        val actual: PredicateSpec

        SpringDataQueryDslImpl(String::class.java).apply {
            actual = expressionSpec1.greaterThan(expressionSpec2)
        }

        assertThat(actual).isEqualTo(GreaterThanExpressionSpec(expressionSpec1, expressionSpec2, false))
    }

    @Test
    fun greaterThanInclusiveExpression() {
        val expressionSpec1: ExpressionSpec<String> = mockk()
        val expressionSpec2: ExpressionSpec<String> = mockk()

        val actual: PredicateSpec

        SpringDataQueryDslImpl(String::class.java).apply {
            actual = expressionSpec1.greaterThan(expressionSpec2, inclusive = true)
        }

        assertThat(actual).isEqualTo(GreaterThanExpressionSpec(expressionSpec1, expressionSpec2, true))
    }

    @Test
    fun greaterThanBound() {
        val expressionSpec: ExpressionSpec<String> = mockk()
        val bound = Range.Bound.inclusive("test")

        val actual: PredicateSpec

        SpringDataQueryDslImpl(String::class.java).apply {
            actual = expressionSpec.greaterThan(bound)
        }

        assertThat(actual).isEqualTo(GreaterThanValueSpec(expressionSpec, "test", true))
    }

    @Test
    fun greaterThanBoundEmpty() {
        val expressionSpec: ExpressionSpec<String> = mockk()
        val bound = Range.Bound.unbounded<String>()

        val actual: PredicateSpec

        SpringDataQueryDslImpl(String::class.java).apply {
            actual = expressionSpec.greaterThan(bound)
        }

        assertThat(actual).isEqualTo(PredicateSpec.empty)
    }

    @Test
    fun lessThanOrEqualToValue() {
        val expressionSpec: ExpressionSpec<String> = mockk()
        val value = "test1"

        val actual: PredicateSpec

        SpringDataQueryDslImpl(String::class.java).apply {
            actual = expressionSpec.lessThanOrEqualTo(value)
        }

        assertThat(actual).isEqualTo(LessThanValueSpec(expressionSpec, value, true))
    }

    @Test
    fun lessThanValue() {
        val expressionSpec: ExpressionSpec<String> = mockk()
        val value = "test1"

        val actual: PredicateSpec

        SpringDataQueryDslImpl(String::class.java).apply {
            actual = expressionSpec.lessThan(value)
        }

        assertThat(actual).isEqualTo(LessThanValueSpec(expressionSpec, value, false))
    }

    @Test
    fun lessThanInclusiveValue() {
        val expressionSpec: ExpressionSpec<String> = mockk()
        val value = "test1"

        val actual: PredicateSpec

        SpringDataQueryDslImpl(String::class.java).apply {
            actual = expressionSpec.lessThan(value, inclusive = true)
        }

        assertThat(actual).isEqualTo(LessThanValueSpec(expressionSpec, value, true))
    }

    @Test
    fun lessThanOrEqualToExpression() {
        val expressionSpec1: ExpressionSpec<String> = mockk()
        val expressionSpec2: ExpressionSpec<String> = mockk()

        val actual: PredicateSpec

        SpringDataQueryDslImpl(String::class.java).apply {
            actual = expressionSpec1.lessThanOrEqualTo(expressionSpec2)
        }

        assertThat(actual).isEqualTo(LessThanExpressionSpec(expressionSpec1, expressionSpec2, true))
    }

    @Test
    fun lessThanExpression() {
        val expressionSpec1: ExpressionSpec<String> = mockk()
        val expressionSpec2: ExpressionSpec<String> = mockk()

        val actual: PredicateSpec

        SpringDataQueryDslImpl(String::class.java).apply {
            actual = expressionSpec1.lessThan(expressionSpec2)
        }

        assertThat(actual).isEqualTo(LessThanExpressionSpec(expressionSpec1, expressionSpec2, false))
    }

    @Test
    fun lessThanInclusiveExpression() {
        val expressionSpec1: ExpressionSpec<String> = mockk()
        val expressionSpec2: ExpressionSpec<String> = mockk()

        val actual: PredicateSpec

        SpringDataQueryDslImpl(String::class.java).apply {
            actual = expressionSpec1.lessThan(expressionSpec2, inclusive = true)
        }

        assertThat(actual).isEqualTo(LessThanExpressionSpec(expressionSpec1, expressionSpec2, true))
    }

    @Test
    fun lessThanBound() {
        val expressionSpec: ExpressionSpec<String> = mockk()
        val bound = Range.Bound.inclusive("test")

        val actual: PredicateSpec

        SpringDataQueryDslImpl(String::class.java).apply {
            actual = expressionSpec.lessThan(bound)
        }

        assertThat(actual).isEqualTo(LessThanValueSpec(expressionSpec, "test", true))
    }

    @Test
    fun lessThanBoundEmpty() {
        val expressionSpec: ExpressionSpec<String> = mockk()
        val bound = Range.Bound.unbounded<String>()

        val actual: PredicateSpec

        SpringDataQueryDslImpl(String::class.java).apply {
            actual = expressionSpec.greaterThan(bound)
        }

        assertThat(actual).isEqualTo(PredicateSpec.empty)
    }

    @Test
    fun betweenValue() {
        val expressionSpec: ExpressionSpec<String> = mockk()
        val value1 = "test1"
        val value2 = "test2"

        val actual: PredicateSpec

        SpringDataQueryDslImpl(String::class.java).apply {
            actual = expressionSpec.between(value1, value2)
        }

        assertThat(actual).isEqualTo(BetweenValueSpec(expressionSpec, value1, value2))
    }

    @Test
    fun betweenExpression() {
        val expressionSpec1: ExpressionSpec<String> = mockk()
        val expressionSpec2: ExpressionSpec<String> = mockk()
        val expressionSpec3: ExpressionSpec<String> = mockk()

        val actual: PredicateSpec

        SpringDataQueryDslImpl(String::class.java).apply {
            actual = expressionSpec1.between(expressionSpec2, expressionSpec3)
        }

        assertThat(actual).isEqualTo(BetweenExpressionSpec(expressionSpec1, expressionSpec2, expressionSpec3))
    }

    @Test
    fun betweenRange() {
        val expressionSpec: ExpressionSpec<String> = mockk()
        val range = Range.leftOpen("test1", "test2")

        val actual: PredicateSpec

        SpringDataQueryDslImpl(String::class.java).apply {
            actual = expressionSpec.between(range)
        }

        assertThat(actual).isEqualTo(
            AndSpec(
                listOf(
                    GreaterThanValueSpec(expressionSpec, "test1", false),
                    LessThanValueSpec(expressionSpec, "test2", true)
                )
            )
        )
    }

    @Test
    fun betweenRangeEmpty() {
        val expressionSpec: ExpressionSpec<String> = mockk()
        val range = Range.unbounded<String>()

        val actual: PredicateSpec

        SpringDataQueryDslImpl(String::class.java).apply {
            actual = expressionSpec.between(range)
        }

        assertThat(actual).isEqualTo(
            AndSpec(listOf(PredicateSpec.empty, PredicateSpec.empty))
        )
    }

    @Test
    fun isTrue() {
        val expressionSpec: ExpressionSpec<Boolean?> = mockk()

        val actual: PredicateSpec

        SpringDataQueryDslImpl(String::class.java).apply {
            actual = expressionSpec.isTrue()
        }

        assertThat(actual).isEqualTo(IsTrueSpec(expressionSpec))
    }

    @Test
    fun isFalse() {
        val expressionSpec: ExpressionSpec<Boolean?> = mockk()

        val actual: PredicateSpec

        SpringDataQueryDslImpl(String::class.java).apply {
            actual = expressionSpec.isFalse()
        }

        assertThat(actual).isEqualTo(IsFalseSpec(expressionSpec))
    }

    @Test
    fun isNull() {
        val expressionSpec: ExpressionSpec<Boolean?> = mockk()

        val actual: PredicateSpec

        SpringDataQueryDslImpl(String::class.java).apply {
            actual = expressionSpec.isNull()
        }

        assertThat(actual).isEqualTo(IsNullSpec(expressionSpec))
    }

    @Test
    fun isNotNull() {
        val expressionSpec: ExpressionSpec<Boolean?> = mockk()

        val actual: PredicateSpec

        SpringDataQueryDslImpl(String::class.java).apply {
            actual = expressionSpec.isNotNull()
        }

        assertThat(actual).isEqualTo(NotSpec(IsNullSpec(expressionSpec)))
    }

    @Test
    fun like() {
        val expressionSpec: ExpressionSpec<String> = mockk()
        val value = "test"

        val actual: PredicateSpec

        SpringDataQueryDslImpl(String::class.java).apply {
            actual = expressionSpec.like(value)
        }

        assertThat(actual).isEqualTo(LikeSpec(expressionSpec, value))
    }

    @Test
    fun notLike() {
        val expressionSpec: ExpressionSpec<String> = mockk()
        val value = "test"

        val actual: PredicateSpec

        SpringDataQueryDslImpl(String::class.java).apply {
            actual = expressionSpec.notLike(value)
        }

        assertThat(actual).isEqualTo(NotSpec(LikeSpec(expressionSpec, value)))
    }
}
