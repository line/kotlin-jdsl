package com.linecorp.kotlinjdsl.dsl.jpql

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.dsl.jpql.delete.DeleteQueryWhereStep
import com.linecorp.kotlinjdsl.dsl.jpql.delete.impl.DeleteQueryDsl
import com.linecorp.kotlinjdsl.dsl.jpql.expression.CaseThenFirstStep
import com.linecorp.kotlinjdsl.dsl.jpql.expression.CaseValueWhenFirstStep
import com.linecorp.kotlinjdsl.dsl.jpql.expression.impl.CaseThenFirstStepDsl
import com.linecorp.kotlinjdsl.dsl.jpql.expression.impl.CaseValueWhenFirstStepDsl
import com.linecorp.kotlinjdsl.dsl.jpql.join.AssociationJoinOnStep
import com.linecorp.kotlinjdsl.dsl.jpql.join.JoinOnStep
import com.linecorp.kotlinjdsl.dsl.jpql.join.impl.AssociationFetchJoinDsl
import com.linecorp.kotlinjdsl.dsl.jpql.join.impl.AssociationJoinDsl
import com.linecorp.kotlinjdsl.dsl.jpql.join.impl.FetchJoinDsl
import com.linecorp.kotlinjdsl.dsl.jpql.join.impl.JoinDsl
import com.linecorp.kotlinjdsl.dsl.jpql.select.SelectQueryFromStep
import com.linecorp.kotlinjdsl.dsl.jpql.select.impl.SelectQueryFromStepDsl
import com.linecorp.kotlinjdsl.dsl.jpql.sort.SortNullsStep
import com.linecorp.kotlinjdsl.dsl.jpql.sort.impl.SortDsl
import com.linecorp.kotlinjdsl.dsl.jpql.update.UpdateQuerySetFirstStep
import com.linecorp.kotlinjdsl.dsl.jpql.update.impl.UpdateQuerySetStepFirstDsl
import com.linecorp.kotlinjdsl.querymodel.jpql.JpqlQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.JpqlQueryable
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entities
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entity
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entityable
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressionable
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Subquery
import com.linecorp.kotlinjdsl.querymodel.jpql.join.JoinType
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Path
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Pathable
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicatable
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicate
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicates
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.sort.Sort
import java.math.BigDecimal
import java.math.BigInteger
import kotlin.internal.Exact
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1

@SinceJdsl("3.0.0")
inline fun <Q : JpqlQuery<Q>> jpql(init: Jpql.() -> JpqlQueryable<Q>): Q {
    return Jpql().init().toQuery()
}

@SinceJdsl("3.0.0")
inline fun <DSL : JpqlDsl, Q : JpqlQuery<Q>> jpql(dsl: JpqlDsl.Constructor<DSL>, init: DSL.() -> JpqlQueryable<Q>): Q {
    return dsl.newInstance().init().toQuery()
}

@SinceJdsl("3.0.0")
open class Jpql : JpqlDsl {
    companion object Constructor : JpqlDsl.Constructor<Jpql> {
        override fun newInstance(): Jpql = Jpql()
    }

    @SinceJdsl("3.0.0")
    fun <T> value(value: @Exact T): Expression<T & Any> {
        return Expressions.value(value)
    }

    @SinceJdsl("3.0.0")
    fun <T : Any> nullValue(): Expression<T> {
        return Expressions.nullValue()
    }

    @SinceJdsl("3.0.0")
    fun intLiteral(int: Int): Expression<Int> {
        return Expressions.intLiteral(int)
    }

    @SinceJdsl("3.0.0")
    fun longLiteral(long: Long): Expression<Long> {
        return Expressions.longLiteral(long)
    }

    @SinceJdsl("3.0.0")
    fun floatLiteral(float: Float): Expression<Float> {
        return Expressions.floatLiteral(float)
    }

    @SinceJdsl("3.0.0")
    fun doubleLiteral(double: Double): Expression<Double> {
        return Expressions.doubleLiteral(double)
    }

    @SinceJdsl("3.0.0")
    fun booleanLiteral(boolean: Boolean): Expression<Boolean> {
        return Expressions.booleanLiteral(boolean)
    }

    @SinceJdsl("3.0.0")
    fun charLiteral(char: Char): Expression<Char> {
        return Expressions.charLiteral(char)
    }

    @SinceJdsl("3.0.0")
    fun stringLiteral(string: String): Expression<String> {
        return Expressions.stringLiteral(string)
    }

    @SinceJdsl("3.0.0")
    fun <T : Enum<T>> enumLiteral(enum: T): Expression<T> {
        return Expressions.enumLiteral(enum)
    }

    @SinceJdsl("3.0.0")
    fun <T : Any> nullLiteral(): Expression<T> {
        return Expressions.nullLiteral()
    }

    @SinceJdsl("3.0.0")
    fun <T : Any> param(name: String): Expression<T> {
        return Expressions.param(name)
    }

    @SinceJdsl("3.0.0")
    fun <T> param(name: String, value: @Exact T): Expression<T & Any> {
        return Expressions.param(name, value)
    }

    @SinceJdsl("3.0.0")
    fun <T : Any> entity(type: KClass<T>, alias: String = type.simpleName!!): Entity<T> {
        return Entities.entity(type, alias)
    }

    @SinceJdsl("3.0.0")
    fun <T : Any, V> path(property: KProperty1<T, @Exact V>): Path<V & Any> {
        return Paths.path(property)
    }

    @SinceJdsl("3.0.0")
    fun <T : Any, V> Path<T>.path(property: KProperty1<T, @Exact V>): Path<V & Any> {
        return Paths.path(this, property)
    }

    @SinceJdsl("3.0.0")
    operator fun <T : Any, V> Path<T>.invoke(property: KProperty1<T, @Exact V>): Path<V & Any> {
        return Paths.path(this, property)
    }

    @SinceJdsl("3.0.0")
    fun <T : Any> Expressionable<@Exact T>.`as`(alias: Expression<T>): Expression<T> {
        return Expressions.alias(this.toExpression(), alias)
    }

    @SinceJdsl("3.0.0")
    fun <T : Any> Expressionable<@Exact T>.alias(alias: Expression<T>): Expression<T> {
        return Expressions.alias(this.toExpression(), alias)
    }

    @SinceJdsl("3.0.0")
    fun <T : Any> expression(type: KClass<T>, alias: String): Expression<T> {
        return Expressions.expression(type, alias)
    }

    @SinceJdsl("3.0.0")
    inline fun <reified T : Any> expression(alias: String): Expression<T> {
        return Expressions.expression(T::class, alias)
    }

    @SinceJdsl("3.0.0")
    fun <T : Any, S : T> Entityable<T>.treat(type: KClass<S>): Entity<S> {
        return Entities.treat(this.toEntity(), type)
    }

    @SinceJdsl("3.0.0")
    fun <T : Any, S : T> Path<T>.treat(type: KClass<S>): Path<S> {
        return Paths.treat(this, type)
    }

    /**
     * Expression that returns the plus of [value1] and [value2].
     * [value1] and [value2] are each enclosed in parentheses.
     *
     * This is the same as ```(value1) + (value2)```.
     */
    @JvmName("plusWithParentheses")
    @SinceJdsl("3.0.0")
    fun <T : Number, S : T?> plus(value1: Expressionable<@Exact T>, value2: S): Expression<T> {
        return Expressions.plus(
            Expressions.parentheses(value1.toExpression()),
            Expressions.parentheses(Expressions.value(value2)),
        )
    }

    /**
     * Expression that returns the plus of [value1] and [value2].
     * [value1] and [value2] are each enclosed in parentheses.
     *
     * This is the same as ```(value1) + (value2)```.
     */
    @JvmName("plusWithParentheses")
    @SinceJdsl("3.0.0")
    fun <T : Number, S : T> plus(value1: Expressionable<@Exact T>, value2: Expressionable<S>): Expression<T> {
        return Expressions.plus(
            Expressions.parentheses(value1.toExpression()),
            Expressions.parentheses(value2.toExpression()),
        )
    }

    /**
     * Expression that returns the plus of [this] and [plus].
     *
     * This is the same as ```value1 + value2```.
     */
    @SinceJdsl("3.0.0")
    fun <T : Number, S : T?> Expressionable<@Exact T>.plus(value: S): Expression<T> {
        return Expressions.plus(this.toExpression(), Expressions.value(value))
    }

    /**
     * Expression that returns the plus of [this] and [plus].
     *
     * This is the same as ```value1 + value2```.
     */
    @SinceJdsl("3.0.0")
    fun <T : Number, S : T> Expressionable<@Exact T>.plus(value: Expressionable<S>): Expression<T> {
        return Expressions.plus(this.toExpression(), value.toExpression())
    }

    /**
     * Expression that returns the minus of [value1] and [value2].
     * [value1] and [value2] are each enclosed in parentheses.
     *
     * This is the same as ```(value1) - (value2)```.
     */
    @JvmName("minusWithParentheses")
    @SinceJdsl("3.0.0")
    fun <T : Number, S : T?> minus(value1: Expressionable<@Exact T>, value2: S): Expression<T> {
        return Expressions.minus(
            Expressions.parentheses(value1.toExpression()),
            Expressions.parentheses(Expressions.value(value2)),
        )
    }

    /**
     * Expression that returns the minus of [value1] and [value2].
     * [value1] and [value2] are each enclosed in parentheses.
     *
     * This is the same as ```(value1) - (value2)```.
     */
    @JvmName("minusWithParentheses")
    @SinceJdsl("3.0.0")
    fun <T : Number, S : T> minus(value1: Expressionable<@Exact T>, value2: Expressionable<S>): Expression<T> {
        return Expressions.minus(
            Expressions.parentheses(value1.toExpression()),
            Expressions.parentheses(value2.toExpression()),
        )
    }

    /**
     * Expression that returns the minus of [this] and [plus].
     *
     * This is the same as ```value1 - value2```.
     */
    @SinceJdsl("3.0.0")
    fun <T : Number, S : T?> Expressionable<@Exact T>.minus(value: S): Expression<T> {
        return Expressions.minus(this.toExpression(), Expressions.value(value))
    }

    /**
     * Expression that returns the minus of [this] and [plus].
     *
     * This is the same as ```value1 - value2```.
     */
    @SinceJdsl("3.0.0")
    fun <T : Number, S : T> Expressionable<@Exact T>.minus(value: Expressionable<S>): Expression<T> {
        return Expressions.minus(this.toExpression(), value.toExpression())
    }

    /**
     * Expression that returns the times of [value1] and [value2].
     * [value1] and [value2] are each enclosed in parentheses.
     *
     * This is the same as ```(value1) * (value2)```.
     */
    @JvmName("timesWithParentheses")
    @SinceJdsl("3.0.0")
    fun <T : Number, S : T?> times(value1: Expressionable<@Exact T>, value2: S): Expression<T> {
        return Expressions.times(
            Expressions.parentheses(value1.toExpression()),
            Expressions.parentheses(Expressions.value(value2)),
        )
    }

    /**
     * Expression that returns the times of [value1] and [value2].
     * [value1] and [value2] are each enclosed in parentheses.
     *
     * This is the same as ```(value1) * (value2)```.
     */
    @JvmName("timesWithParentheses")
    @SinceJdsl("3.0.0")
    fun <T : Number, S : T> times(value1: Expressionable<@Exact T>, value2: Expressionable<S>): Expression<T> {
        return Expressions.times(
            Expressions.parentheses(value1.toExpression()),
            Expressions.parentheses(value2.toExpression()),
        )
    }

    /**
     * Expression that returns the times of [this] and [plus].
     *
     * This is the same as ```value1 * value2```.
     */
    @SinceJdsl("3.0.0")
    fun <T : Number, S : T?> Expressionable<@Exact T>.times(value: S): Expression<T> {
        return Expressions.times(this.toExpression(), Expressions.value(value))
    }

    /**
     * Expression that returns the times of [this] and [plus].
     *
     * This is the same as ```value1 * value2```.
     */
    @SinceJdsl("3.0.0")
    fun <T : Number, S : T> Expressionable<@Exact T>.times(value: Expressionable<S>): Expression<T> {
        return Expressions.times(this.toExpression(), value.toExpression())
    }

    /**
     * Expression that returns the divide of [value1] and [value2].
     * [value1] and [value2] are each enclosed in parentheses.
     *
     * This is the same as ```(value1) / (value2)```.
     */
    @JvmName("divWithParentheses")
    @SinceJdsl("3.0.0")
    fun <T : Number, S : T?> div(value1: Expressionable<@Exact T>, value2: S): Expression<T> {
        return Expressions.div(
            Expressions.parentheses(value1.toExpression()),
            Expressions.parentheses(Expressions.value(value2)),
        )
    }

    /**
     * Expression that returns the divide of [value1] and [value2].
     * [value1] and [value2] are each enclosed in parentheses.
     *
     * This is the same as ```(value1) / (value2)```.
     */
    @JvmName("divWithParentheses")
    @SinceJdsl("3.0.0")
    fun <T : Number, S : T> div(value1: Expressionable<@Exact T>, value2: Expressionable<S>): Expression<T> {
        return Expressions.div(
            Expressions.parentheses(value1.toExpression()),
            Expressions.parentheses(value2.toExpression()),
        )
    }

    /**
     * Expression that returns the divide of [this] and [plus].
     *
     * This is the same as ```value1 / value2```.
     */
    @SinceJdsl("3.0.0")
    fun <T : Number, S : T?> Expressionable<@Exact T>.div(value: S): Expression<T> {
        return Expressions.div(this.toExpression(), Expressions.value(value))
    }

    /**
     * Expression that returns the divide of [this] and [plus].
     *
     * This is the same as ```value1 / value2```.
     */
    @SinceJdsl("3.0.0")
    fun <T : Number, S : T> Expressionable<@Exact T>.div(value: Expressionable<S>): Expression<T> {
        return Expressions.div(this.toExpression(), value.toExpression())
    }

    /**
     * Expression that returns the count of the number of non-null values of [expr].
     *
     * If there are no matching rows, it returns 0.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any, V> count(expr: KProperty1<T, @Exact V>): Expression<Long> {
        return Expressions.count(distinct = false, Paths.path(expr))
    }

    /**
     * Expression that returns the count of the number of non-null values of [expr].
     *
     * If there are no matching rows, it returns 0.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> count(expr: Expressionable<T>): Expression<Long> {
        return Expressions.count(distinct = false, expr.toExpression())
    }

    /**
     * Expression that returns the count of the number of non-null values of [expr].
     *
     * If there are no matching rows, it returns 0.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any, V> countDistinct(expr: KProperty1<T, @Exact V>): Expression<Long> {
        return Expressions.count(distinct = true, Paths.path(expr))
    }

    /**
     * Expression that returns the count of the number of non-null values of [expr].
     *
     * If there are no matching rows, it returns 0.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> countDistinct(expr: Expressionable<T>): Expression<Long> {
        return Expressions.count(distinct = true, expr.toExpression())
    }

    /**
     * Expression that returns the maximum value of [expr].
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any, V : Comparable<*>?> max(distinct: Boolean, expr: KProperty1<T, @Exact V>): Expression<V & Any> {
        return Expressions.max(distinct, Paths.path(expr))
    }

    /**
     * Expression that returns the maximum value of [expr].
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any, V : Comparable<*>?> max(expr: KProperty1<T, @Exact V>): Expression<V & Any> {
        return max(distinct = false, expr)
    }

    /**
     * Expression that returns the maximum value of [expr].
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any, V : Comparable<*>?> maxDistinct(expr: KProperty1<T, @Exact V>): Expression<V & Any> {
        return max(distinct = true, expr)
    }

    /**
     * Expression that returns the maximum value of [expr].
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<*>> max(distinct: Boolean, expr: Expressionable<@Exact T>): Expression<T> {
        return Expressions.max(distinct, expr.toExpression())
    }

    /**
     * Expression that returns the maximum value of [expr].
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<*>> max(expr: Expressionable<@Exact T>): Expression<T> {
        return max(distinct = false, expr)
    }

    /**
     * Expression that returns the maximum value of [expr].
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<*>> maxDistinct(expr: Expressionable<@Exact T>): Expression<T> {
        return max(distinct = true, expr)
    }

    /**
     * Expression that returns the minimum value of [expr].
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any, V : Comparable<*>?> min(distinct: Boolean, expr: KProperty1<T, @Exact V>): Expression<V & Any> {
        return Expressions.min(distinct, Paths.path(expr))
    }

    /**
     * Expression that returns the minimum value of [expr].
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any, V : Comparable<*>?> min(expr: KProperty1<T, @Exact V>): Expression<V & Any> {
        return min(distinct = false, expr)
    }

    /**
     * Expression that returns the minimum value of [expr].
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any, V : Comparable<*>?> minDistinct(expr: KProperty1<T, @Exact V>): Expression<V & Any> {
        return min(distinct = true, expr)
    }

    /**
     * Expression that returns the minimum value of [expr].
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<*>> min(distinct: Boolean, expr: Expressionable<@Exact T>): Expression<T> {
        return Expressions.min(distinct, expr.toExpression())
    }

    /**
     * Expression that returns the minimum value of [expr].
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<*>> min(expr: Expressionable<@Exact T>): Expression<T> {
        return min(distinct = false, expr)
    }

    /**
     * Expression that returns the minimum value of [expr].
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<*>> minDistinct(expr: Expressionable<@Exact T>): Expression<T> {
        return min(distinct = true, expr)
    }

    /**
     * Expression that returns the average value of [expr].
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any, V : Number?> avg(expr: KProperty1<T, @Exact V>): Expression<Double> {
        return Expressions.avg(distinct = false, Paths.path(expr))
    }

    /**
     * Expression that returns the average value of [expr].
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @SinceJdsl("3.0.0")
    fun <T : Number> avg(expr: Expressionable<@Exact T>): Expression<Double> {
        return Expressions.avg(distinct = false, expr.toExpression())
    }

    /**
     * Expression that returns the average value of [expr]
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any, V : Number?> avgDistinct(expr: KProperty1<T, @Exact V>): Expression<Double> {
        return Expressions.avg(distinct = true, Paths.path(expr))
    }

    /**
     * Expression that returns the average value of [expr]
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @SinceJdsl("3.0.0")
    fun <T : Number> avgDistinct(expr: Expressionable<@Exact T>): Expression<Double> {
        return Expressions.avg(distinct = true, expr.toExpression())
    }

    /**
     * Expression that returns the sum of [expr].
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sumInt")
    @SinceJdsl("3.0.0")
    fun <T : Any> sum(distinct: Boolean, expr: KProperty1<T, Int?>): Expression<Long> {
        return Expressions.sum(distinct, Paths.path(expr))
    }

    /**
     * Expression that returns the sum of [expr].
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sumInt")
    @SinceJdsl("3.0.0")
    fun <T : Any> sum(expr: KProperty1<T, Int?>): Expression<Long> {
        return sum(distinct = false, expr)
    }

    /**
     * Expression that returns the sum of [expr].
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sumLong")
    @SinceJdsl("3.0.0")
    fun <T : Any> sum(distinct: Boolean, expr: KProperty1<T, Long?>): Expression<Long> {
        return Expressions.sum(distinct, Paths.path(expr))
    }

    /**
     * Expression that returns the sum of [expr].
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sumLong")
    @SinceJdsl("3.0.0")
    fun <T : Any> sum(expr: KProperty1<T, Long?>): Expression<Long> {
        return sum(distinct = false, expr)
    }

    /**
     * Expression that returns the sum of [expr].
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sumFloat")
    @SinceJdsl("3.0.0")
    fun <T : Any> sum(distinct: Boolean, expr: KProperty1<T, Float?>): Expression<Double> {
        return Expressions.sum(distinct, Paths.path(expr))
    }

    /**
     * Expression that returns the sum of [expr].
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sumFloat")
    @SinceJdsl("3.0.0")
    fun <T : Any> sum(expr: KProperty1<T, Float?>): Expression<Double> {
        return sum(distinct = false, expr)
    }

    /**
     * Expression that returns the sum of [expr].
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sumDouble")
    @SinceJdsl("3.0.0")
    fun <T : Any> sum(distinct: Boolean, expr: KProperty1<T, Double?>): Expression<Double> {
        return Expressions.sum(distinct, Paths.path(expr))
    }

    /**
     * Expression that returns the sum of [expr].
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sumDouble")
    @SinceJdsl("3.0.0")
    fun <T : Any> sum(expr: KProperty1<T, Double?>): Expression<Double> {
        return sum(distinct = false, expr)
    }

    /**
     * Expression that returns the sum of [expr].
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sumBigInteger")
    @SinceJdsl("3.0.0")
    fun <T : Any> sum(distinct: Boolean, expr: KProperty1<T, BigInteger?>): Expression<BigInteger> {
        return Expressions.sum(distinct, Paths.path(expr))
    }

    /**
     * Expression that returns the sum of [expr].
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sumBigInteger")
    @SinceJdsl("3.0.0")
    fun <T : Any> sum(expr: KProperty1<T, BigInteger?>): Expression<BigInteger> {
        return sum(distinct = false, Paths.path(expr))
    }

    /**
     * Expression that returns the sum of [expr].
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sumBigDecimal")
    @SinceJdsl("3.0.0")
    fun <T : Any> sum(distinct: Boolean, expr: KProperty1<T, BigDecimal?>): Expression<BigDecimal> {
        return Expressions.sum(distinct, Paths.path(expr))
    }

    /**
     * Expression that returns the sum of [expr].
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sumBigDecimal")
    @SinceJdsl("3.0.0")
    fun <T : Any> sum(expr: KProperty1<T, BigDecimal?>): Expression<BigDecimal> {
        return sum(distinct = false, expr)
    }

    /**
     * Expression that returns the sum of [expr].
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sumInt")
    @SinceJdsl("3.0.0")
    fun sum(distinct: Boolean, expr: Expressionable<Int>): Expression<Long> {
        return Expressions.sum(distinct, expr.toExpression())
    }

    /**
     * Expression that returns the sum of [expr].
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sumInt")
    @SinceJdsl("3.0.0")
    fun sum(expr: Expressionable<Int>): Expression<Long> {
        return sum(distinct = false, expr)
    }

    /**
     * Expression that returns the sum of [expr].
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sumLong")
    @SinceJdsl("3.0.0")
    fun sum(distinct: Boolean, expr: Expressionable<Long>): Expression<Long> {
        return Expressions.sum(distinct, expr.toExpression())
    }

    /**
     * Expression that returns the sum of [expr].
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sumLong")
    @SinceJdsl("3.0.0")
    fun sum(expr: Expressionable<Long>): Expression<Long> {
        return sum(distinct = false, expr)
    }

    /**
     * Expression that returns the sum of [expr].
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sumFloat")
    @SinceJdsl("3.0.0")
    fun sum(distinct: Boolean, expr: Expressionable<Float>): Expression<Double> {
        return Expressions.sum(distinct, expr.toExpression())
    }

    /**
     * Expression that returns the sum of [expr].
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sumFloat")
    @SinceJdsl("3.0.0")
    fun sum(expr: Expressionable<Float>): Expression<Double> {
        return sum(distinct = false, expr)
    }

    /**
     * Expression that returns the sum of [expr].
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sumDouble")
    @SinceJdsl("3.0.0")
    fun sum(distinct: Boolean, expr: Expressionable<Double>): Expression<Double> {
        return Expressions.sum(distinct, expr.toExpression())
    }

    /**
     * Expression that returns the sum of [expr].
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sumDouble")
    @SinceJdsl("3.0.0")
    fun sum(expr: Expressionable<Double>): Expression<Double> {
        return sum(distinct = false, expr)
    }

    /**
     * Expression that returns the sum of [expr].
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sumBigInteger")
    @SinceJdsl("3.0.0")
    fun sum(distinct: Boolean, expr: Expressionable<BigInteger>): Expression<BigInteger> {
        return Expressions.sum(distinct, expr.toExpression())
    }

    /**
     * Expression that returns the sum of [expr].
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sumBigInteger")
    @SinceJdsl("3.0.0")
    fun sum(expr: Expressionable<BigInteger>): Expression<BigInteger> {
        return sum(distinct = false, expr)
    }

    /**
     * Expression that returns the sum of [expr].
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sumBigDecimal")
    @SinceJdsl("3.0.0")
    fun sum(distinct: Boolean, expr: Expressionable<BigDecimal>): Expression<BigDecimal> {
        return Expressions.sum(distinct, expr.toExpression())
    }

    /**
     * Expression that returns the sum of [expr].
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sumBigDecimal")
    @SinceJdsl("3.0.0")
    fun sum(expr: Expressionable<BigDecimal>): Expression<BigDecimal> {
        return sum(distinct = false, expr)
    }

    /**
     * Expression that returns the sum of [expr].
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sumDistinctInt")
    @SinceJdsl("3.0.0")
    fun <T : Any> sumDistinct(expr: KProperty1<T, Int?>): Expression<Long> {
        return sum(distinct = true, expr)
    }

    /**
     * Expression that returns the sum of [expr].
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sumDistinctLong")
    @SinceJdsl("3.0.0")
    fun <T : Any> sumDistinct(expr: KProperty1<T, Long?>): Expression<Long> {
        return sum(distinct = true, expr)
    }

    /**
     * Expression that returns the sum of [expr].
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sumDistinctFloat")
    @SinceJdsl("3.0.0")
    fun <T : Any> sumDistinct(expr: KProperty1<T, Float?>): Expression<Double> {
        return sum(distinct = true, expr)
    }

    /**
     * Expression that returns the sum of [expr].
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sumDistinctDouble")
    @SinceJdsl("3.0.0")
    fun <T : Any> sumDistinct(expr: KProperty1<T, Double?>): Expression<Double> {
        return sum(distinct = true, expr)
    }

    /**
     * Expression that returns the sum of [expr].
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sumDistinctBigInteger")
    @SinceJdsl("3.0.0")
    fun <T : Any> sumDistinct(expr: KProperty1<T, BigInteger?>): Expression<BigInteger> {
        return sum(distinct = true, expr)
    }

    /**
     * Expression that returns the sum of [expr].
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sumDistinctBigDecimal")
    @SinceJdsl("3.0.0")
    fun <T : Any> sumDistinct(expr: KProperty1<T, BigDecimal?>): Expression<BigDecimal> {
        return sum(distinct = true, expr)
    }

    /**
     * Expression that returns the sum of [expr].
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sumDistinctInt")
    @SinceJdsl("3.0.0")
    fun sumDistinct(expr: Expressionable<Int>): Expression<Long> {
        return sum(distinct = true, expr)
    }

    /**
     * Expression that returns the sum of [expr].
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sumDistinctLong")
    @SinceJdsl("3.0.0")
    fun sumDistinct(expr: Expressionable<Long>): Expression<Long> {
        return sum(distinct = true, expr)
    }

    /**
     * Expression that returns the sum of [expr].
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sumDistinctFloat")
    @SinceJdsl("3.0.0")
    fun sumDistinct(expr: Expressionable<Float>): Expression<Double> {
        return sum(distinct = true, expr)
    }

    /**
     * Expression that returns the sum of [expr].
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sumDistinctDouble")
    @SinceJdsl("3.0.0")
    fun sumDistinct(expr: Expressionable<Double>): Expression<Double> {
        return sum(distinct = true, expr)
    }

    /**
     * Expression that returns the sum of [expr].
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sumDistinctBigInteger")
    @SinceJdsl("3.0.0")
    fun sumDistinct(expr: Expressionable<BigInteger>): Expression<BigInteger> {
        return sum(distinct = true, expr)
    }

    /**
     * Expression that returns the sum of [expr].
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sumDistinctBigDecimal")
    @SinceJdsl("3.0.0")
    fun sumDistinct(expr: Expressionable<BigDecimal>): Expression<BigDecimal> {
        return sum(distinct = true, expr)
    }

    /**
     * Expression that return the object specified by [type].
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> new(type: KClass<T>, vararg args: Any): Expression<T> {
        return Expressions.new(type, args.map { Expressions.value(it) })
    }

    /**
     * Expression that return the object specified by [type].
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> new(type: KClass<T>, vararg args: Expressionable<*>): Expression<T> {
        return Expressions.new(type, args.map { it.toExpression() })
    }

    /**
     * Expression that returns the result for the first predicate that is true.
     * If no predicate is true, the result after ELSE is returned, or NULL if there is no ELSE part.
     */
    @SinceJdsl("3.0.0")
    fun caseWhen(predicate: Predicatable): CaseThenFirstStep {
        return CaseThenFirstStepDsl(predicate.toPredicate())
    }

    /**
     * Expression that returns the result for the first value = compareValue comparison that is true.
     * If no comparison is true, the result after ELSE is returned, or NULL if there is no ELSE part.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> caseValue(value: Pathable<T>): CaseValueWhenFirstStep<T> {
        return CaseValueWhenFirstStepDsl(value.toPath())
    }

    /**
     * Expression that returns the first non-null value in the expressions,
     * or null if there are no non-null value in expressions.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any, S : T?> coalesce(
        value: Expressionable<@Exact T>,
        alternate: S,
        vararg others: S,
    ): Expression<T> {
        return Expressions.coalesce(
            value = value.toExpression(),
            alternate = Expressions.value(alternate),
            others = others.map { Expressions.value(it) },
        )
    }

    /**
     * Expression that returns the first non-null value in the expressions,
     * or null if there are no non-null value in expressions.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any, S : T> coalesce(
        value: Expressionable<@Exact T>,
        alternate: Expressionable<S>,
        vararg others: Expressionable<S>,
    ): Expression<T> {
        return Expressions.coalesce(
            value = value.toExpression(),
            alternate = alternate.toExpression(),
            others = others.map { it.toExpression() },
        )
    }

    /**
     * Expression that returns null if left = right is true, otherwise returns left.
     *
     * This is the same as ```CASE WHEN left = right THEN NULL ELSE left END```.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any, S : T?> nullIf(value: Expressionable<@Exact T>, compareValue: S): Expression<T> {
        return Expressions.nullIf(value.toExpression(), Expressions.value(compareValue))
    }

    /**
     * Expression that returns null if left = right is true, otherwise returns left.
     *
     * This is the same as ```CASE WHEN left = right THEN NULL ELSE left END```.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> nullIf(value: Expressionable<@Exact T>, compareValue: Expressionable<T>): Expression<T> {
        return Expressions.nullIf(value.toExpression(), compareValue.toExpression())
    }

    /**
     * Expression that returns the type of the entity.
     *
     * This is the same as ```TYPE(entity)``` and can be used to restrict query polymorphism.
     *
     * Examples:
     * ```sql
     * TYPE(entity) IN (Exempt, Contractor)
     * ```
     * ```sql
     * CASE TYPE(entity) WHEN Exempt THEN 'Exempt'
     *                   WHEN Contractor THEN 'Contractor'
     *                   ELSE 'NonExempt'
     * END
     * ```
     */
    @SinceJdsl("3.0.0")
    fun type(entity: Entityable<*>): Expression<KClass<*>> {
        return Expressions.type(entity.toEntity())
    }

    /**
     * Expression that returns the type of the entity.
     *
     * This is the same as ```TYPE(entity)``` and can be used to restrict query polymorphism.
     *
     * Examples:
     * ```sql
     * TYPE(entity) IN (Exempt, Contractor)
     * ```
     * ```sql
     * CASE TYPE(entity) WHEN Exempt THEN 'Exempt'
     *                   WHEN Contractor THEN 'Contractor'
     *                   ELSE 'NonExempt'
     * END
     * ```
     */
    @SinceJdsl("3.0.0")
    fun type(path: Pathable<*>): Expression<KClass<*>> {
        return Expressions.type(path.toPath())
    }

    @SinceJdsl("3.0.0")
    fun <T : Any> function(type: KClass<T>, name: String, vararg args: Any): Expression<T> {
        return Expressions.function(type, name, args.map { Expressions.value(it) })
    }

    @SinceJdsl("3.0.0")
    fun <T : Any> function(type: KClass<T>, name: String, vararg args: Expressionable<*>): Expression<T> {
        return Expressions.function(type, name, args.map { it.toExpression() })
    }

    /**
     * Expression that renders the user-defined string to JPQL.
     *
     * The user-defined string can have PlaceHolders.
     * PlaceHolders in string are replaced with Expression in args, matching with index.
     *
     * ```
     * PlaceHolder: { ArgumentIndex }
     * ```
     *
     * Examples:
     * ```
     * customExpression(String::class, "CAST({0} AS VARCHAR)", 100)
     * ```
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> customExpression(type: KClass<T>, template: String, vararg args: Any): Expression<T> {
        return Expressions.customExpression(type, template, args.map { Expressions.value(it) })
    }

    /**
     * Expression that renders the user-defined string to JPQL.
     *
     * The user-defined string can have PlaceHolders.
     * PlaceHolders in string are replaced with Expression in args, matching with index.
     *
     * ```
     * PlaceHolder: { ArgumentIndex }
     * ```
     *
     * Examples:
     * ```
     * customExpression(String::class, "CAST({0} AS VARCHAR)", path(User::age))
     * ```
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> customExpression(type: KClass<T>, template: String, vararg args: Expressionable<*>): Expression<T> {
        return Expressions.customExpression(type, template, args.map { it.toExpression() })
    }

    @SinceJdsl("3.0.0")
    fun <T : Any> JpqlQueryable<SelectQuery<T>>.asSubquery(): Subquery<T> {
        return Expressions.subquery(this.toQuery())
    }

    @SinceJdsl("3.0.0")
    fun <T : Any> JpqlQueryable<SelectQuery<T>>.asEntity(): Entity<T> {
        return Entities.derivedEntity(this.toQuery())
    }

    fun <T : Any> join(entity: KClass<T>): JoinOnStep<T> {
        return JoinDsl(Entities.entity(entity), JoinType.INNER)
    }

    fun <T : Any> join(entity: Entityable<T>): JoinOnStep<T> {
        return JoinDsl(entity.toEntity(), JoinType.INNER)
    }

    inline fun <T : Any, reified V> join(property: KProperty1<T, @Exact V>): AssociationJoinOnStep<V & Any> {
        @Suppress("UNCHECKED_CAST")
        val entity = Entities.entity(V::class as KClass<V & Any>)

        return AssociationJoinDsl(entity, Paths.path(property), JoinType.INNER)
    }

    @JvmName("joinCollection")
    inline fun <T : Any, reified V, S : Collection<V>> join(property: KProperty1<T, @Exact S>): AssociationJoinOnStep<V & Any> {
        @Suppress("UNCHECKED_CAST")
        val entity = Entities.entity(V::class as KClass<V & Any>)

        return AssociationJoinDsl(entity, Paths.path(property), JoinType.INNER)
    }

    inline fun <reified T : Any> join(path: Path<T>): AssociationJoinOnStep<T> {
        return AssociationJoinDsl(Entities.entity(T::class), path, JoinType.INNER)
    }

    @JvmName("joinCollection")
    inline fun <reified T : Any, S : Collection<T>> join(path: Path<S>): AssociationJoinOnStep<T> {
        return AssociationJoinDsl(Entities.entity(T::class), path, JoinType.INNER)
    }

    fun <T : Any> innerJoin(entity: KClass<T>): JoinOnStep<T> {
        return JoinDsl(Entities.entity(entity), JoinType.INNER)
    }

    fun <T : Any> innerJoin(entity: Entityable<T>): JoinOnStep<T> {
        return JoinDsl(entity.toEntity(), JoinType.INNER)
    }

    inline fun <T : Any, reified V> innerJoin(property: KProperty1<T, @Exact V>): AssociationJoinOnStep<V & Any> {
        @Suppress("UNCHECKED_CAST")
        val entity = Entities.entity(V::class as KClass<V & Any>)

        return AssociationJoinDsl(entity, Paths.path(property), JoinType.INNER)
    }

    @JvmName("innerJoinCollection")
    inline fun <T : Any, reified V, S : Collection<V>> innerJoin(property: KProperty1<T, @Exact S>): AssociationJoinOnStep<V & Any> {
        @Suppress("UNCHECKED_CAST")
        val entity = Entities.entity(V::class as KClass<V & Any>)

        return AssociationJoinDsl(entity, Paths.path(property), JoinType.INNER)
    }

    inline fun <reified T : Any> innerJoin(path: Path<T>): AssociationJoinOnStep<T> {
        return AssociationJoinDsl(Entities.entity(T::class), path, JoinType.INNER)
    }

    @JvmName("innerJoinCollection")
    inline fun <reified T : Any, S : Collection<T>> innerJoin(path: Path<S>): AssociationJoinOnStep<T> {
        return AssociationJoinDsl(Entities.entity(T::class), path, JoinType.INNER)
    }

    fun <T : Any> leftJoin(entity: KClass<T>): JoinOnStep<T> {
        return JoinDsl(Entities.entity(entity), JoinType.LEFT)
    }

    fun <T : Any> leftJoin(entity: Entityable<T>): JoinOnStep<T> {
        return JoinDsl(entity.toEntity(), JoinType.LEFT)
    }

    inline fun <T : Any, reified V> leftJoin(property: KProperty1<T, @Exact V>): AssociationJoinOnStep<V & Any> {
        @Suppress("UNCHECKED_CAST")
        val entity = Entities.entity(V::class as KClass<V & Any>)

        return AssociationJoinDsl(entity, Paths.path(property), JoinType.LEFT)
    }

    @JvmName("leftJoinCollection")
    inline fun <T : Any, reified V, S : Collection<V>> leftJoin(property: KProperty1<T, @Exact S>): AssociationJoinOnStep<V & Any> {
        @Suppress("UNCHECKED_CAST")
        val entity = Entities.entity(V::class as KClass<V & Any>)

        return AssociationJoinDsl(entity, Paths.path(property), JoinType.LEFT)
    }

    inline fun <reified T : Any> leftJoin(path: Path<T>): AssociationJoinOnStep<T> {
        return AssociationJoinDsl(Entities.entity(T::class), path, JoinType.LEFT)
    }

    @JvmName("leftJoinCollection")
    inline fun <reified T : Any, S : Collection<T>> leftJoin(path: Path<S>): AssociationJoinOnStep<T> {
        return AssociationJoinDsl(Entities.entity(T::class), path, JoinType.LEFT)
    }

    fun <T : Any> fetchJoin(entity: KClass<T>): JoinOnStep<T> {
        return FetchJoinDsl(Entities.entity(entity), JoinType.INNER)
    }

    fun <T : Any> fetchJoin(entity: Entityable<T>): JoinOnStep<T> {
        return FetchJoinDsl(entity.toEntity(), JoinType.INNER)
    }

    inline fun <T : Any, reified V> fetchJoin(property: KProperty1<T, @Exact V>): AssociationJoinOnStep<V & Any> {
        @Suppress("UNCHECKED_CAST")
        val entity = Entities.entity(V::class as KClass<V & Any>)

        return AssociationFetchJoinDsl(entity, Paths.path(property), JoinType.INNER)
    }

    @JvmName("fetchJoinCollection")
    inline fun <T : Any, reified V, S : Collection<V>> fetchJoin(property: KProperty1<T, @Exact S>): AssociationJoinOnStep<V & Any> {
        @Suppress("UNCHECKED_CAST")
        val entity = Entities.entity(V::class as KClass<V & Any>)

        return AssociationFetchJoinDsl(entity, Paths.path(property), JoinType.INNER)
    }

    inline fun <reified T : Any> fetchJoin(path: Path<T>): AssociationJoinOnStep<T> {
        return AssociationFetchJoinDsl(Entities.entity(T::class), path, JoinType.INNER)
    }

    @JvmName("fetchJoinCollection")
    inline fun <reified T : Any, S : Collection<T>> fetchJoin(path: Path<S>): AssociationJoinOnStep<T> {
        return AssociationFetchJoinDsl(Entities.entity(T::class), path, JoinType.INNER)
    }

    fun <T : Any> innerFetchJoin(entity: KClass<T>): JoinOnStep<T> {
        return FetchJoinDsl(Entities.entity(entity), JoinType.INNER)
    }

    fun <T : Any> innerFetchJoin(entity: Entityable<T>): JoinOnStep<T> {
        return FetchJoinDsl(entity.toEntity(), JoinType.INNER)
    }

    inline fun <T : Any, reified V> innerFetchJoin(property: KProperty1<T, @Exact V>): AssociationJoinOnStep<V & Any> {
        @Suppress("UNCHECKED_CAST")
        val entity = Entities.entity(V::class as KClass<V & Any>)

        return AssociationFetchJoinDsl(entity, Paths.path(property), JoinType.INNER)
    }

    @JvmName("innerFetchJoinCollection")
    inline fun <T : Any, reified V, S : Collection<V>> innerFetchJoin(property: KProperty1<T, @Exact S>): AssociationJoinOnStep<V & Any> {
        @Suppress("UNCHECKED_CAST")
        val entity = Entities.entity(V::class as KClass<V & Any>)

        return AssociationFetchJoinDsl(entity, Paths.path(property), JoinType.INNER)
    }

    inline fun <reified T : Any> innerFetchJoin(path: Path<T>): AssociationJoinOnStep<T> {
        return AssociationFetchJoinDsl(Entities.entity(T::class), path, JoinType.INNER)
    }

    @JvmName("innerFetchJoinCollection")
    inline fun <reified T : Any, S : Collection<T>> innerFetchJoin(path: Path<S>): AssociationJoinOnStep<T> {
        return AssociationFetchJoinDsl(Entities.entity(T::class), path, JoinType.INNER)
    }

    fun <T : Any> leftFetchJoin(entity: KClass<T>): JoinOnStep<T> {
        return FetchJoinDsl(Entities.entity(entity), JoinType.LEFT)
    }

    fun <T : Any> leftFetchJoin(entity: Entityable<T>): JoinOnStep<T> {
        return FetchJoinDsl(entity.toEntity(), JoinType.LEFT)
    }

    inline fun <T : Any, reified V> leftFetchJoin(property: KProperty1<T, @Exact V>): AssociationJoinOnStep<V & Any> {
        @Suppress("UNCHECKED_CAST")
        val entity = Entities.entity(V::class as KClass<V & Any>)

        return AssociationFetchJoinDsl(entity, Paths.path(property), JoinType.LEFT)
    }

    @JvmName("leftFetchJoinCollection")
    inline fun <T : Any, reified V, S : Collection<V>> leftFetchJoin(property: KProperty1<T, @Exact S>): AssociationJoinOnStep<V & Any> {
        @Suppress("UNCHECKED_CAST")
        val entity = Entities.entity(V::class as KClass<V & Any>)

        return AssociationFetchJoinDsl(entity, Paths.path(property), JoinType.LEFT)
    }

    inline fun <reified T : Any> leftFetchJoin(path: Path<T>): AssociationJoinOnStep<T> {
        return AssociationFetchJoinDsl(Entities.entity(T::class), path, JoinType.LEFT)
    }

    @JvmName("leftFetchJoinCollection")
    inline fun <reified T : Any, S : Collection<T>> leftFetchJoin(path: Path<S>): AssociationJoinOnStep<T> {
        return AssociationFetchJoinDsl(Entities.entity(T::class), path, JoinType.LEFT)
    }

    @SinceJdsl("3.0.0")
    fun <T : Any> JpqlQueryable<SelectQuery<T>>.asEntity(alias: String): Entity<T> {
        return Entities.derivedEntity(this.toQuery(), alias)
    }

    @SinceJdsl("3.0.0")
    fun not(predicate: Predicatable): Predicate {
        return Predicates.not(predicate.toPredicate())
    }

    /**
     * Predicate that connects [predicates] with **AND**.
     *
     * If [predicates] is empty, then it represents **1 = 1**.
     */
    @SinceJdsl("3.0.0")
    fun and(vararg predicates: Predicatable?): Predicate {
        return Predicates.and(predicates.mapNotNull { it?.toPredicate() }.map { Predicates.parentheses(it) })
    }

    @SinceJdsl("3.0.0")
    fun Predicatable.and(predicate: Predicatable): Predicate {
        return Predicates.and(listOf(this.toPredicate(), predicate.toPredicate()))
    }

    /**
     * Predicate that connects [predicates] with **OR**.
     *
     * If [predicates] is empty, then it represents **0 = 1**.
     */
    @SinceJdsl("3.0.0")
    fun or(vararg predicates: Predicatable?): Predicate {
        return Predicates.or(predicates.mapNotNull { it?.toPredicate() }.map { Predicates.parentheses(it) })
    }

    @SinceJdsl("3.0.0")
    fun Predicatable.or(predicate: Predicatable): Predicate {
        return Predicates.or(listOf(this.toPredicate(), predicate.toPredicate()))
    }

    @SinceJdsl("3.0.0")
    fun <T : Any> Expressionable<@Exact T>.isNull(): Predicate {
        return Predicates.isNull(this.toExpression())
    }

    @SinceJdsl("3.0.0")
    fun <T : Any> Expressionable<@Exact T>.isNotNull(): Predicate {
        return Predicates.isNotNull(this.toExpression())
    }

    @SinceJdsl("3.0.0")
    fun <T : Any, S : T?> Expressionable<@Exact T>.equal(value: S): Predicate {
        return Predicates.equal(this.toExpression(), Expressions.value(value))
    }

    @SinceJdsl("3.0.0")
    fun <T : Any> Expressionable<@Exact T>.equal(value: Expressionable<T>): Predicate {
        return Predicates.equal(this.toExpression(), value.toExpression())
    }

    @SinceJdsl("3.0.0")
    fun <T : Any> Expressionable<@Exact T>.equalAll(subquery: Subquery<T>): Predicate {
        return Predicates.equalAll(this.toExpression(), subquery)
    }

    @SinceJdsl("3.0.0")
    fun <T : Any> Expressionable<@Exact T>.equalAny(subquery: Subquery<T>): Predicate {
        return Predicates.equalAny(this.toExpression(), subquery)
    }

    @SinceJdsl("3.0.0")
    fun <T : Any, S : T?> Expressionable<@Exact T>.eq(compareValue: S): Predicate {
        return Predicates.equal(this.toExpression(), Expressions.value(compareValue))
    }

    @SinceJdsl("3.0.0")
    fun <T : Any> Expressionable<@Exact T>.eq(compareValue: Expressionable<T>): Predicate {
        return Predicates.equal(this.toExpression(), compareValue.toExpression())
    }

    @SinceJdsl("3.0.0")
    fun <T : Any> Expressionable<@Exact T>.eqAll(subquery: Subquery<T>): Predicate {
        return Predicates.equalAll(this.toExpression(), subquery)
    }

    @SinceJdsl("3.0.0")
    fun <T : Any> Expressionable<@Exact T>.eqAny(subquery: Subquery<T>): Predicate {
        return Predicates.equalAny(this.toExpression(), subquery)
    }

    @SinceJdsl("3.0.0")
    fun <T : Any, S : T?> Expressionable<@Exact T>.notEqual(value: S): Predicate {
        return Predicates.notEqual(this.toExpression(), Expressions.value(value))
    }

    @SinceJdsl("3.0.0")
    fun <T : Any> Expressionable<@Exact T>.notEqual(value: Expressionable<T>): Predicate {
        return Predicates.notEqual(this.toExpression(), value.toExpression())
    }

    @SinceJdsl("3.0.0")
    fun <T : Any> Expressionable<@Exact T>.notEqualAll(subquery: Subquery<T>): Predicate {
        return Predicates.notEqualAll(this.toExpression(), subquery)
    }

    @SinceJdsl("3.0.0")
    fun <T : Any> Expressionable<@Exact T>.notEqualAny(subquery: Subquery<T>): Predicate {
        return Predicates.notEqualAny(this.toExpression(), subquery)
    }

    @SinceJdsl("3.0.0")
    fun <T : Any, S : T?> Expressionable<@Exact T>.ne(value: S): Predicate {
        return Predicates.notEqual(this.toExpression(), Expressions.value(value))
    }

    @SinceJdsl("3.0.0")
    fun <T : Any> Expressionable<@Exact T>.ne(value: Expressionable<T>): Predicate {
        return Predicates.notEqual(this.toExpression(), value.toExpression())
    }

    @SinceJdsl("3.0.0")
    fun <T : Any> Expressionable<@Exact T>.neAll(subquery: Subquery<T>): Predicate {
        return Predicates.notEqualAll(this.toExpression(), subquery)
    }

    @SinceJdsl("3.0.0")
    fun <T : Any> Expressionable<@Exact T>.neAny(subquery: Subquery<T>): Predicate {
        return Predicates.notEqualAny(this.toExpression(), subquery)
    }

    /**
     * Predicate that tests if the [this] is less than the [value].
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>, S : T?> Expressionable<@Exact T>.lessThan(value: S, inclusive: Boolean): Predicate {
        return if (inclusive) {
            Predicates.lessThanOrEqualTo(this.toExpression(), Expressions.value(value))
        } else {
            Predicates.lessThan(this.toExpression(), Expressions.value(value))
        }
    }

    /**
     * Predicate that tests if the [this] is less than the [value].
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>, S : T?> Expressionable<@Exact T>.lessThan(value: S): Predicate {
        return lessThan(value, inclusive = false)
    }

    /**
     * Predicate that tests if the [this] is less than the [value].
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.lessThan(value: Expressionable<T>, inclusive: Boolean): Predicate {
        return if (inclusive) {
            Predicates.lessThanOrEqualTo(this.toExpression(), value.toExpression())
        } else {
            Predicates.lessThan(this.toExpression(), value.toExpression())
        }
    }

    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.lessThanAll(subquery: Subquery<T>, inclusive: Boolean): Predicate {
        return if (inclusive) {
            Predicates.lessThanOrEqualToAll(this.toExpression(), subquery)
        } else {
            Predicates.lessThanAll(this.toExpression(), subquery)
        }
    }

    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.lessThanAny(subquery: Subquery<T>, inclusive: Boolean): Predicate {
        return if (inclusive) {
            Predicates.lessThanOrEqualToAny(this.toExpression(), subquery)
        } else {
            Predicates.lessThanAny(this.toExpression(), subquery)
        }
    }

    /**
     * Predicate that tests if the [this] is less than the [value].
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.lessThan(value: Expressionable<T>): Predicate {
        return lessThan(value, inclusive = false)
    }

    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.lessThanAll(subquery: Subquery<T>): Predicate {
        return lessThanAll(subquery, inclusive = false)
    }

    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.lessThanAny(subquery: Subquery<T>): Predicate {
        return lessThanAny(subquery, inclusive = false)
    }

    /**
     * Predicate that tests if the [this] is less than the [value].
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.lt(value: T, inclusive: Boolean): Predicate {
        return lessThan(value, inclusive)
    }

    /**
     * Predicate that tests if the [this] is less than the [value].
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>, S : T?> Expressionable<@Exact T>.lt(value: S): Predicate {
        return lessThan(value, inclusive = false)
    }

    /**
     * Predicate that tests if the [this] is less than the [value].
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.lt(value: Expressionable<T>, inclusive: Boolean): Predicate {
        return lessThan(value, inclusive)
    }

    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.ltAll(subquery: Subquery<T>, inclusive: Boolean): Predicate {
        return lessThanAll(subquery, inclusive)
    }

    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.ltAny(subquery: Subquery<T>, inclusive: Boolean): Predicate {
        return lessThanAny(subquery, inclusive)
    }

    /**
     * Predicate that tests if the [this] is less than the [value].
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.lt(value: Expressionable<T>): Predicate {
        return lessThan(value, inclusive = false)
    }

    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.ltAll(subquery: Subquery<T>): Predicate {
        return lessThanAll(subquery, inclusive = false)
    }

    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.ltAny(subquery: Subquery<T>): Predicate {
        return lessThanAny(subquery, inclusive = false)
    }

    /**
     * Predicate that tests if the [this] is less than or equal to the [value].
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>, S : T?> Expressionable<@Exact T>.lessThanOrEqualTo(value: S): Predicate {
        return lessThan(value, inclusive = true)
    }

    /**
     * Predicate that tests if the [this] is less than or equal to the [value].
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.lessThanOrEqualTo(value: Expressionable<T>): Predicate {
        return lessThan(value, inclusive = true)
    }

    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.lessThanOrEqualToAll(subquery: Subquery<T>): Predicate {
        return lessThanAll(subquery, inclusive = true)
    }

    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.lessThanOrEqualToAny(subquery: Subquery<T>): Predicate {
        return lessThanAny(subquery, inclusive = true)
    }

    /**
     * Predicate that tests if the [this] is less than or equal to the [value].
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>, S : T?> Expressionable<@Exact T>.le(value: S): Predicate {
        return lessThan(value, inclusive = true)
    }

    /**
     * Predicate that tests if the [this] is less than or equal to the [value].
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.le(value: Expressionable<T>): Predicate {
        return lessThan(value, inclusive = true)
    }

    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.leAll(subquery: Subquery<T>): Predicate {
        return lessThanAll(subquery, inclusive = true)
    }

    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.leAny(subquery: Subquery<T>): Predicate {
        return lessThanAny(subquery, inclusive = true)
    }

    /**
     * Predicate that tests if the [this] is greater than the [value].
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>, S : T?> Expressionable<@Exact T>.greaterThan(value: S, inclusive: Boolean): Predicate {
        return if (inclusive) {
            Predicates.greaterThanOrEqualTo(this.toExpression(), Expressions.value(value))
        } else {
            Predicates.greaterThan(this.toExpression(), Expressions.value(value))
        }
    }

    /**
     * Predicate that tests if the [this] is greater than the [value].
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>, S : T?> Expressionable<@Exact T>.greaterThan(value: S): Predicate {
        return greaterThan(value, inclusive = false)
    }

    /**
     * Predicate that tests if the [this] is greater than the [value].
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.greaterThan(
        value: Expressionable<T>,
        inclusive: Boolean
    ): Predicate {
        return if (inclusive) {
            Predicates.greaterThanOrEqualTo(this.toExpression(), value.toExpression())
        } else {
            Predicates.greaterThan(this.toExpression(), value.toExpression())
        }
    }

    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.greaterThanAll(
        subquery: Subquery<T>,
        inclusive: Boolean
    ): Predicate {
        return if (inclusive) {
            Predicates.greaterThanOrEqualToAll(this.toExpression(), subquery)
        } else {
            Predicates.greaterThanAll(this.toExpression(), subquery)
        }
    }

    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.greaterThanAny(
        subquery: Subquery<T>,
        inclusive: Boolean
    ): Predicate {
        return if (inclusive) {
            Predicates.greaterThanOrEqualToAny(this.toExpression(), subquery)
        } else {
            Predicates.greaterThanAny(this.toExpression(), subquery)
        }
    }

    /**
     * Predicate that tests if the [this] is greater than the [value].
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.greaterThan(value: Expressionable<T>): Predicate {
        return greaterThan(value, inclusive = false)
    }

    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.greaterThanAll(subquery: Subquery<T>): Predicate {
        return greaterThanAll(subquery, inclusive = false)
    }

    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.greaterThanAny(subquery: Subquery<T>): Predicate {
        return greaterThanAny(subquery, inclusive = false)
    }

    /**
     * Predicate that tests if the [this] is greater than the [value].
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>, S : T?> Expressionable<@Exact T>.gt(value: S, inclusive: Boolean): Predicate {
        return greaterThan(value, inclusive)
    }

    /**
     * Predicate that tests if the [this] is greater than the [value].
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>, S : T?> Expressionable<@Exact T>.gt(value: S): Predicate {
        return greaterThan(value, inclusive = false)
    }

    /**
     * Predicate that tests if the [this] is greater than the [value].
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.gt(value: Expressionable<T>, inclusive: Boolean): Predicate {
        return greaterThan(value, inclusive)
    }

    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.gtAll(subquery: Subquery<T>, inclusive: Boolean): Predicate {
        return greaterThanAll(subquery, inclusive)
    }

    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.gtAny(subquery: Subquery<T>, inclusive: Boolean): Predicate {
        return greaterThanAny(subquery, inclusive)
    }

    /**
     * Predicate that tests if the [this] is greater than the [value].
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.gt(value: Expressionable<T>): Predicate {
        return greaterThan(value, inclusive = false)
    }

    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.gtAll(subquery: Subquery<T>): Predicate {
        return greaterThanAll(subquery, inclusive = false)
    }

    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.gtAny(subquery: Subquery<T>): Predicate {
        return greaterThanAny(subquery, inclusive = false)
    }

    /**
     * Predicate that tests if the [this] is greater than or equal to the [value].
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>, S : T?> Expressionable<@Exact T>.greaterThanOrEqualTo(value: S): Predicate {
        return greaterThan(value, inclusive = true)
    }

    /**
     * Predicate that tests if the [this] is greater than or equal to the [value].
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.greaterThanOrEqualTo(value: Expressionable<T>): Predicate {
        return greaterThan(value, inclusive = true)
    }

    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.greaterThanOrEqualToAll(subquery: Subquery<T>): Predicate {
        return greaterThanAll(subquery, inclusive = true)
    }

    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.greaterThanOrEqualToAny(subquery: Subquery<T>): Predicate {
        return greaterThanAny(subquery, inclusive = true)
    }

    /**
     * Predicate that tests if the [this] is greater than or equal to the [value].
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>, S : T?> Expressionable<@Exact T>.ge(value: S): Predicate {
        return greaterThan(value, inclusive = true)
    }

    /**
     * Predicate that tests if the [this] is greater than or equal to the [value].
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.ge(value: Expressionable<T>): Predicate {
        return greaterThan(value, inclusive = true)
    }

    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.geAll(subquery: Subquery<T>): Predicate {
        return greaterThanAll(subquery, inclusive = true)
    }

    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.geAny(subquery: Subquery<T>): Predicate {
        return greaterThanAny(subquery, inclusive = true)
    }

    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>, S : T?> Expressionable<@Exact T>.between(min: S, max: S): Predicate {
        return Predicates.between(this.toExpression(), Expressions.value(min), Expressions.value(max))
    }

    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.between(
        min: Expressionable<T>,
        max: Expressionable<T>
    ): Predicate {
        return Predicates.between(this.toExpression(), min.toExpression(), max.toExpression())
    }

    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>, S : T?> Expressionable<@Exact T>.notBetween(min: S, max: S): Predicate {
        return Predicates.notBetween(this.toExpression(), Expressions.value(min), Expressions.value(max))
    }

    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.notBetween(
        min: Expressionable<T>,
        max: Expressionable<T>
    ): Predicate {
        return Predicates.notBetween(this.toExpression(), min.toExpression(), max.toExpression())
    }

    @SinceJdsl("3.0.0")
    fun <T : Any, S : T?> Expressionable<@Exact T>.`in`(vararg compareValues: S): Predicate {
        return Predicates.`in`(this.toExpression(), compareValues.map { Expressions.value(it) })
    }

    @SinceJdsl("3.0.0")
    fun <T : Any, S : T?> Expressionable<@Exact T>.`in`(compareValues: Iterable<S>): Predicate {
        return Predicates.`in`(this.toExpression(), compareValues.map { Expressions.value(it) })
    }

    @JvmName("inExpressions")
    @SinceJdsl("3.0.0")
    fun <T : Any> Expressionable<@Exact T>.`in`(compareValues: Iterable<Expressionable<T>?>): Predicate {
        return Predicates.`in`(this.toExpression(), compareValues.mapNotNull { it?.toExpression() })
    }

    @SinceJdsl("3.0.0")
    fun <T : Any> Expressionable<@Exact T>.`in`(vararg compareValues: Expressionable<T>?): Predicate {
        return Predicates.`in`(this.toExpression(), compareValues.mapNotNull { it?.toExpression() })
    }

    @SinceJdsl("3.0.0")
    fun <T : Any> Expressionable<@Exact T>.`in`(subquery: Subquery<T>): Predicate {
        return Predicates.`in`(this.toExpression(), subquery)
    }

    @SinceJdsl("3.0.0")
    fun <T : Any, S : T?> Expressionable<@Exact T>.notIn(compareValues: Iterable<S>): Predicate {
        return Predicates.notIn(this.toExpression(), compareValues.map { Expressions.value(it) })
    }

    @SinceJdsl("3.0.0")
    fun <T : Any, S : T?> Expressionable<@Exact T>.notIn(vararg compareValues: S): Predicate {
        return Predicates.notIn(this.toExpression(), compareValues.map { Expressions.value(it) })
    }

    @JvmName("notInExpressions")
    @SinceJdsl("3.0.0")
    fun <T : Any> Expressionable<@Exact T>.notIn(compareValues: Iterable<Expressionable<T>?>): Predicate {
        return Predicates.notIn(this.toExpression(), compareValues.mapNotNull { it?.toExpression() })
    }

    @SinceJdsl("3.0.0")
    fun <T : Any> Expressionable<@Exact T>.notIn(vararg compareValues: Expressionable<T>?): Predicate {
        return Predicates.notIn(this.toExpression(), compareValues.mapNotNull { it?.toExpression() })
    }

    @SinceJdsl("3.0.0")
    fun <T : Any> Expressionable<@Exact T>.notIn(subquery: Subquery<T>): Predicate {
        return Predicates.notIn(this.toExpression(), subquery)
    }

    @SinceJdsl("3.0.0")
    fun Expressionable<String>.like(pattern: String): Predicate {
        return Predicates.like(this.toExpression(), Expressions.value(pattern))
    }

    @SinceJdsl("3.0.0")
    fun Expressionable<String>.like(pattern: Expressionable<String>): Predicate {
        return Predicates.like(this.toExpression(), pattern.toExpression())
    }

    @SinceJdsl("3.0.0")
    fun Expressionable<String>.notLike(pattern: String): Predicate {
        return Predicates.notLike(this.toExpression(), Expressions.value(pattern))
    }

    @SinceJdsl("3.0.0")
    fun Expressionable<String>.notLike(pattern: Expressionable<String>): Predicate {
        return Predicates.notLike(this.toExpression(), pattern.toExpression())
    }

    @SinceJdsl("3.0.0")
    fun <T, S : Collection<T>> Path<S>.isEmpty(): Predicate {
        return Predicates.isEmpty(this)
    }

    @SinceJdsl("3.0.0")
    fun <T, S : Collection<T>> Path<S>.isNotEmpty(): Predicate {
        return Predicates.isNotEmpty(this)
    }

    @SinceJdsl("3.0.0")
    fun <T : Any> exists(subquery: Subquery<T>): Predicate {
        return Predicates.exists(subquery)
    }

    @SinceJdsl("3.0.0")
    fun <T : Any> notExists(subquery: Subquery<T>): Predicate {
        return Predicates.notExists(subquery)
    }

    @SinceJdsl("3.0.0")
    fun Expressionable<*>.asc(): SortNullsStep {
        return SortDsl(this.toExpression(), Sort.Order.ASC)
    }

    @SinceJdsl("3.0.0")
    fun Expressionable<*>.desc(): SortNullsStep {
        return SortDsl(this.toExpression(), Sort.Order.DESC)
    }

    @SinceJdsl("3.0.0")
    inline fun <reified T : Any> select(
        expr: Expressionable<T>,
    ): SelectQueryFromStep<T> {
        return SelectQueryFromStepDsl(T::class, distinct = false, listOf(expr.toExpression()))
    }

    @SinceJdsl("3.0.0")
    inline fun <reified T : Any> select(
        vararg expr: Expressionable<*>,
    ): SelectQueryFromStep<T> {
        return SelectQueryFromStepDsl(T::class, distinct = false, expr.map { it.toExpression() })
    }

    @SinceJdsl("3.0.0")
    fun <T : Any> select(
        returnType: KClass<T>,
        vararg expr: Expressionable<*>,
    ): SelectQueryFromStep<T> {
        return SelectQueryFromStepDsl(returnType, distinct = false, expr.map { it.toExpression() })
    }

    @SinceJdsl("3.0.0")
    inline fun <reified T : Any> selectDistinct(
        expr: Expressionable<T>,
    ): SelectQueryFromStep<T> {
        return SelectQueryFromStepDsl(T::class, distinct = true, listOf(expr.toExpression()))
    }

    @SinceJdsl("3.0.0")
    inline fun <reified T : Any> selectDistinct(
        vararg expr: Expressionable<*>,
    ): SelectQueryFromStep<T> {
        return SelectQueryFromStepDsl(T::class, distinct = true, expr.map { it.toExpression() })
    }

    @SinceJdsl("3.0.0")
    fun <T : Any> selectDistinct(
        returnType: KClass<T>,
        vararg expr: Expressionable<*>,
    ): SelectQueryFromStep<T> {
        return SelectQueryFromStepDsl(returnType, distinct = true, expr.map { it.toExpression() })
    }

    @SinceJdsl("3.0.0")
    inline fun <reified T : Any> selectNew(
        vararg expr: Expressionable<*>,
    ): SelectQueryFromStep<T> {
        return SelectQueryFromStepDsl(
            returnType = T::class,
            distinct = false,
            select = listOf(Expressions.new(T::class, expr.map { it.toExpression() })),
        )
    }

    @SinceJdsl("3.0.0")
    fun <T : Any> selectNew(
        returnType: KClass<T>,
        vararg expr: Expressionable<*>,
    ): SelectQueryFromStep<T> {
        return SelectQueryFromStepDsl(
            returnType = returnType,
            distinct = false,
            select = listOf(Expressions.new(returnType, expr.map { it.toExpression() })),
        )
    }

    @SinceJdsl("3.0.0")
    inline fun <reified T : Any> selectDistinctNew(
        vararg expr: Expressionable<*>,
    ): SelectQueryFromStep<T> {
        return SelectQueryFromStepDsl(
            returnType = T::class,
            distinct = true,
            select = listOf(Expressions.new(T::class, expr.map { it.toExpression() })),
        )
    }

    @SinceJdsl("3.0.0")
    fun <T : Any> selectDistinctNew(
        returnType: KClass<T>,
        vararg expr: Expressionable<*>,
    ): SelectQueryFromStep<T> {
        return SelectQueryFromStepDsl(
            returnType = returnType,
            distinct = true,
            select = listOf(Expressions.new(returnType, expr.map { it.toExpression() })),
        )
    }

    @SinceJdsl("3.0.0")
    fun <T : Any> update(entity: Entityable<T>): UpdateQuerySetFirstStep<T> {
        return UpdateQuerySetStepFirstDsl(entity.toEntity())
    }

    @SinceJdsl("3.0.0")
    fun <T : Any> deleteFrom(entity: Entityable<T>): DeleteQueryWhereStep<T> {
        return DeleteQueryDsl(entity.toEntity())
    }
}
