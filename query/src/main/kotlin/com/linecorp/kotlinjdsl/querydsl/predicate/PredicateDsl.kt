package com.linecorp.kotlinjdsl.querydsl.predicate

import com.linecorp.kotlinjdsl.query.spec.expression.ExpressionSpec
import com.linecorp.kotlinjdsl.query.spec.expression.SubqueryExpressionSpec
import com.linecorp.kotlinjdsl.query.spec.predicate.*

@Suppress("RemoveExplicitTypeArguments")
interface PredicateDsl {
    fun not(predicate: PredicateSpec): PredicateSpec = NotSpec(predicate)

    fun and(vararg others: PredicateSpec?): PredicateSpec = and(others.toList())
    fun and(others: List<PredicateSpec?>): PredicateSpec = AndSpec(others)

    fun or(vararg others: PredicateSpec?): PredicateSpec = or(others.toList())
    fun or(others: List<PredicateSpec?>): PredicateSpec = OrSpec(others)

    fun <T> exists(subqueryExpression: SubqueryExpressionSpec<T>) = ExistsSpec(subqueryExpression)
    fun <T> notExists(subqueryExpression: SubqueryExpressionSpec<T>) = not(ExistsSpec(subqueryExpression))

    fun <R> ExpressionSpec<R>.equal(value: R) = EqualValueSpec(this, value)
    fun <R> ExpressionSpec<R>.equal(expression: ExpressionSpec<R>) = EqualExpressionSpec(this, expression)

    fun <R> ExpressionSpec<R>.notEqual(value: R) = not(EqualValueSpec(this, value))
    fun <R> ExpressionSpec<R>.notEqual(expression: ExpressionSpec<R>) = not(EqualExpressionSpec(this, expression))

    fun <R> ExpressionSpec<R>.`in`(vararg values: R) = `in`(values.toList())
    fun <R> ExpressionSpec<R>.`in`(values: Collection<R>) = InValueSpec(this, values)

    fun <R> ExpressionSpec<R>.`in`(vararg expressions: ExpressionSpec<R>) = `in`(expressions.toList())
    fun <R> ExpressionSpec<R>.`in`(expressions: List<ExpressionSpec<R>>) = InExpressionSpec(this, expressions)

    fun <T, R> ExpressionSpec<T>.greaterThanOrEqualTo(value: R)
        where R : Comparable<R>, R : Any, T : R? = greaterThan<T, R>(value, true)

    fun <T, R> ExpressionSpec<T>.greaterThan(value: R, inclusive: Boolean = false)
        where R : Comparable<R>, R : Any, T : R? = GreaterThanValueSpec<T, R>(this, value, inclusive)

    fun <T> ExpressionSpec<T>.greaterThanOrEqualTo(expression: ExpressionSpec<T>)
        where T : Comparable<T> = greaterThan(expression, true)

    fun <T> ExpressionSpec<T>.greaterThan(expression: ExpressionSpec<T>, inclusive: Boolean = false)
        where T : Comparable<T> = GreaterThanExpressionSpec<T>(this, expression, inclusive)

    fun <T, R> ExpressionSpec<T>.lessThanOrEqualTo(value: R)
        where R : Comparable<R>, R : Any, T : R? = lessThan<T, R>(value, true)

    fun <T, R> ExpressionSpec<T>.lessThan(value: R, inclusive: Boolean = false)
        where R : Comparable<R>, R : Any, T : R? = LessThanValueSpec<T, R>(this, value, inclusive)

    fun <T> ExpressionSpec<T>.lessThanOrEqualTo(expression: ExpressionSpec<T>)
        where T : Comparable<T> = lessThan(expression, true)

    fun <T> ExpressionSpec<T>.lessThan(expression: ExpressionSpec<T>, inclusive: Boolean = false)
        where T : Comparable<T> = LessThanExpressionSpec<T>(this, expression, inclusive)

    fun <T, R> ExpressionSpec<T>.between(value1: R, value2: R)
        where R : Comparable<R>, R : Any, T : R? = BetweenValueSpec<T, R>(this, value1, value2)

    fun <T> ExpressionSpec<T>.between(expression1: ExpressionSpec<T>, expression2: ExpressionSpec<T>)
        where T : Comparable<T> = BetweenExpressionSpec(this, expression1, expression2)

    fun ExpressionSpec<out Boolean?>.isTrue() = IsTrueSpec(this)
    fun ExpressionSpec<out Boolean?>.isFalse() = IsFalseSpec(this)

    fun <R : Any?> ExpressionSpec<R>.isNull() = IsNullSpec(this)
    fun <R : Any?> ExpressionSpec<R>.isNotNull() = not(IsNullSpec(this))

    fun ExpressionSpec<out String?>.like(value: String) = LikeSpec(this, value)
    fun ExpressionSpec<out String?>.notLike(value: String) = not(LikeSpec(this, value))
}
