package com.linecorp.kotlinjdsl.querymodel.jpql.expression

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.*
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Path
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicate
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.select.impl.JpqlSelectQuery
import java.math.BigDecimal
import java.math.BigInteger
import kotlin.internal.Exact
import kotlin.reflect.KClass

object Expressions {
    @SinceJdsl("3.0.0")
    fun <T> value(value: @Exact T): Expression<T & Any> {
        return if (value == null) {
            nullValue()
        } else {
            JpqlValue(value)
        }
    }

    @SinceJdsl("3.0.0")
    fun <T : Any> nullValue(): Expression<T> {
        @Suppress("UNCHECKED_CAST")
        return JpqlNull as Expression<T>
    }

    @SinceJdsl("3.0.0")
    fun intLiteral(int: Int): Expression<Int> {
        return JpqlLiteral.IntLiteral(int)
    }

    @SinceJdsl("3.0.0")
    fun longLiteral(long: Long): Expression<Long> {
        return JpqlLiteral.LongLiteral(long)
    }

    @SinceJdsl("3.0.0")
    fun floatLiteral(float: Float): Expression<Float> {
        return JpqlLiteral.FloatLiteral(float)
    }

    @SinceJdsl("3.0.0")
    fun doubleLiteral(double: Double): Expression<Double> {
        return JpqlLiteral.DoubleLiteral(double)
    }

    @SinceJdsl("3.0.0")
    fun booleanLiteral(boolean: Boolean): Expression<Boolean> {
        return JpqlLiteral.BooleanLiteral(boolean)
    }

    @SinceJdsl("3.0.0")
    fun stringLiteral(string: String): Expression<String> {
        return JpqlLiteral.StringLiteral(string)
    }

    @SinceJdsl("3.0.0")
    fun <T : Enum<T>> enumLiteral(enum: T): Expression<T> {
        return JpqlLiteral.EnumLiteral(enum)
    }

    @SinceJdsl("3.0.0")
    fun <T : Any> nullLiteral(): Expression<T> {
        @Suppress("UNCHECKED_CAST")
        return JpqlLiteral.NullLiteral as Expression<T>
    }

    @SinceJdsl("3.0.0")
    fun <T> param(name: String): Expression<T & Any> {
        return JpqlParam(name, null)
    }

    @SinceJdsl("3.0.0")
    fun <T> param(name: String, value: @Exact T): Expression<T & Any> {
        return JpqlParam(name, value)
    }

    @SinceJdsl("3.0.0")
    fun <T : Number, S : T> plus(value1: Expression<T>, value2: Expression<S>): Expression<T> {
        return JpqlPlus(value1, value2)
    }

    @SinceJdsl("3.0.0")
    fun <T : Number, S : T> minus(value1: Expression<T>, value2: Expression<S>): Expression<T> {
        return JpqlMinus(value1, value2)
    }

    @SinceJdsl("3.0.0")
    fun <T : Number, S : T> times(value1: Expression<T>, value2: Expression<S>): Expression<T> {
        return JpqlTimes(value1, value2)
    }

    @SinceJdsl("3.0.0")
    fun <T : Number, S : T> div(value1: Expression<T>, value2: Expression<S>): Expression<T> {
        return JpqlDivide(value1, value2)
    }

    @SinceJdsl("3.0.0")
    fun <T : Any> count(distinct: Boolean, expr: Expression<T>): Expression<Long> {
        return JpqlCount(distinct, expr)
    }

    @SinceJdsl("3.0.0")
    fun <T : Comparable<*>> max(distinct: Boolean, expr: Expression<T>): Expression<T> {
        return JpqlMax(distinct, expr)
    }

    @SinceJdsl("3.0.0")
    fun <T : Comparable<*>> min(distinct: Boolean, expr: Expression<T>): Expression<T> {
        return JpqlMin(distinct, expr)
    }

    @SinceJdsl("3.0.0")
    fun <T : Number> avg(distinct: Boolean, expr: Expression<T>): Expression<Double> {
        return JpqlAvg(distinct, expr)
    }

    @JvmName("sum1")
    @SinceJdsl("3.0.0")
    fun sum(distinct: Boolean, expr: Expression<Int>): Expression<Long> {
        return JpqlSum.IntSum(distinct, expr)
    }

    @JvmName("sum2")
    @SinceJdsl("3.0.0")
    fun sum(distinct: Boolean, expr: Expression<Long>): Expression<Long> {
        return JpqlSum.LongSum(distinct, expr)
    }

    @JvmName("sum3")
    @SinceJdsl("3.0.0")
    fun sum(distinct: Boolean, expr: Expression<Float>): Expression<Double> {
        return JpqlSum.FloatSum(distinct, expr)
    }

    @JvmName("sum4")
    @SinceJdsl("3.0.0")
    fun sum(distinct: Boolean, expr: Expression<Double>): Expression<Double> {
        return JpqlSum.DoubleSum(distinct, expr)
    }

    @JvmName("sum5")
    @SinceJdsl("3.0.0")
    fun sum(distinct: Boolean, expr: Expression<BigInteger>): Expression<BigInteger> {
        return JpqlSum.BigIntegerSum(distinct, expr)
    }

    @JvmName("sum6")
    @SinceJdsl("3.0.0")
    fun sum(distinct: Boolean, expr: Expression<BigDecimal>): Expression<BigDecimal> {
        return JpqlSum.BigDecimalSum(distinct, expr)
    }

    @SinceJdsl("3.0.0")
    fun <T : Any> new(type: KClass<T>, args: Iterable<Expression<*>>): Expression<T> {
        return JpqlNew(type, args)
    }

    @SinceJdsl("3.0.0")
    fun <T : Any> case(
        whens: Map<Predicate, Expression<T>>,
        `else`: Expression<T>? = null,
    ): Expression<T> {
        return JpqlCase(
            whens = whens.map { it.key to it.value }.toMap(),
            `else` = `else`?.toExpression(),
        )
    }

    @SinceJdsl("3.0.0")
    fun <T : Any, V : Any> caseValue(
        value: Path<T>,
        whens: Map<Expression<T>, Expression<V>>,
        `else`: Expression<V>? = null,
    ): Expression<V> {
        return JpqlCaseValue(
            value = value,
            whens = whens,
            `else` = `else`,
        )
    }

    @SinceJdsl("3.0.0")
    fun <T : Any> coalesce(
        value: Expression<T>,
        alternate: Expression<T>,
        others: Iterable<Expression<T>>,
    ): Expression<T> {
        val expr = listOf(
            value,
            alternate,
        ) + others

        return JpqlCoalesce(expr)
    }

    @SinceJdsl("3.0.0")
    fun <T : Any> nullIf(value: Expression<T>, compareValue: Expression<T>): Expression<T> {
        return JpqlNullIf(value, compareValue)
    }

    @SinceJdsl("3.0.0")
    fun <T : Any> type(path: Path<T>): Expression<KClass<T>> {
        return JpqlType(path)
    }

    @SinceJdsl("3.0.0")
    fun <T : Any> function(type: KClass<T>, name: String, args: Iterable<Expression<*>>): Expression<T> {
        return JpqlFunction(type, name, args.map { it.toExpression() })
    }

    @SinceJdsl("3.0.0")
    fun <T : Any> customExpression(
        type: KClass<T>,
        template: String,
        args: Iterable<Expression<*>>
    ): Expression<T> {
        return JpqlCustomExpression(type, template, args)
    }

    @SinceJdsl("3.0.0")
    fun <T : Any> subquery(selectQuery: SelectQuery<T>): Subquery<T> {
        val trimmed = if (selectQuery is JpqlSelectQuery) {
            JpqlSelectQuery(
                returnType = selectQuery.returnType,
                select = selectQuery.select,
                distinct = selectQuery.distinct,
                from = selectQuery.from,
                where = selectQuery.where,
                groupBy = selectQuery.groupBy,
                having = selectQuery.having,
                orderBy = null,
            )
        } else {
            selectQuery
        }

        return JpqlSubquery(
            selectQuery = trimmed,
        )
    }

    @SinceJdsl("3.0.0")
    fun <T : Any> alias(expr: Expression<T>, alias: Expression<T>): Expression<T> {
        return if (expr is JpqlAliasedExpression) {
            JpqlAliasedExpression(expr.expr, alias)
        } else {
            JpqlAliasedExpression(expr, alias)
        }
    }

    @SinceJdsl("3.0.0")
    fun <T : Any> expression(type: KClass<T>, alias: String): Expression<T> {
        return JpqlExpression(type, alias)
    }
}
