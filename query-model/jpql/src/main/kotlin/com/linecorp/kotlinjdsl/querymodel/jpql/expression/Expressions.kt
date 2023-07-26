package com.linecorp.kotlinjdsl.querymodel.jpql.expression

import com.linecorp.kotlinjdsl.Experimental
import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.*
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Pathable
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicatable
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
    fun <T : Number, S : T> plus(
        value1: Expressionable<T>,
        value2: Expressionable<S>,
    ): Expression<T> {
        return JpqlPlus(value1.toExpression(), value2.toExpression())
    }

    @SinceJdsl("3.0.0")
    fun <T : Number, S : T> minus(
        value1: Expressionable<T>,
        value2: Expressionable<S>,
    ): Expression<T> {
        return JpqlMinus(value1.toExpression(), value2.toExpression())
    }

    @SinceJdsl("3.0.0")
    fun <T : Number, S : T> times(
        value1: Expressionable<T>,
        value2: Expressionable<S>,
    ): Expression<T> {
        return JpqlTimes(value1.toExpression(), value2.toExpression())
    }

    @SinceJdsl("3.0.0")
    fun <T : Number, S : T> div(
        value1: Expressionable<T>,
        value2: Expressionable<S>,
    ): Expression<T> {
        return JpqlDivide(value1.toExpression(), value2.toExpression())
    }

    @SinceJdsl("3.0.0")
    fun <T : Any> count(distinct: Boolean, expr: Expressionable<T>): Expression<Long> {
        return JpqlCount(distinct, expr.toExpression())
    }

    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> max(distinct: Boolean, expr: Expressionable<T>): Expression<T> {
        return JpqlMax(distinct, expr.toExpression())
    }

    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> min(distinct: Boolean, expr: Expressionable<T>): Expression<T> {
        return JpqlMin(distinct, expr.toExpression())
    }

    @SinceJdsl("3.0.0")
    fun <T : Number> avg(distinct: Boolean, expr: Expressionable<T>): Expression<Double> {
        return JpqlAvg(distinct, expr.toExpression())
    }

    @JvmName("sum1")
    @SinceJdsl("3.0.0")
    fun sum(distinct: Boolean, expr: Expressionable<Int>): Expression<Long> {
        return JpqlSum.IntSum(distinct, expr.toExpression())
    }

    @JvmName("sum2")
    @SinceJdsl("3.0.0")
    fun sum(distinct: Boolean, expr: Expressionable<Long>): Expression<Long> {
        return JpqlSum.LongSum(distinct, expr.toExpression())
    }

    @JvmName("sum3")
    @SinceJdsl("3.0.0")
    fun sum(distinct: Boolean, expr: Expressionable<Float>): Expression<Double> {
        return JpqlSum.FloatSum(distinct, expr.toExpression())
    }

    @JvmName("sum4")
    @SinceJdsl("3.0.0")
    fun sum(distinct: Boolean, expr: Expressionable<Double>): Expression<Double> {
        return JpqlSum.DoubleSum(distinct, expr.toExpression())
    }

    @JvmName("sum5")
    @SinceJdsl("3.0.0")
    fun sum(distinct: Boolean, expr: Expressionable<BigInteger>): Expression<BigInteger> {
        return JpqlSum.BigIntegerSum(distinct, expr.toExpression())
    }

    @JvmName("sum6")
    @SinceJdsl("3.0.0")
    fun sum(distinct: Boolean, expr: Expressionable<BigDecimal>): Expression<BigDecimal> {
        return JpqlSum.BigDecimalSum(distinct, expr.toExpression())
    }

    @SinceJdsl("3.0.0")
    fun <T : Any> new(type: KClass<T>, args: Iterable<Expressionable<*>>): Expression<T> {
        return JpqlNew(type, args.map { it.toExpression() })
    }

    @SinceJdsl("3.0.0")
    fun <T : Any> case(
        whens: Map<Predicatable, Expressionable<T>>,
        `else`: Expressionable<T>? = null,
    ): Expression<T> {
        return JpqlCase(
            whens = whens.map { it.key.toPredicate() to it.value.toExpression() }.toMap(),
            `else` = `else`?.toExpression(),
        )
    }

    @SinceJdsl("3.0.0")
    fun <T : Any, V : Any> caseValue(
        value: Pathable<T>,
        whens: Map<Expression<T>, Expression<V>>,
        `else`: Expressionable<V>? = null,
    ): Expression<V> {
        return JpqlCaseValue(
            value = value.toPath(),
            whens = whens,
            `else` = `else`?.toExpression(),
        )
    }

    @SinceJdsl("3.0.0")
    fun <T : Any> coalesce(
        value: Expressionable<T>,
        alternate: Expressionable<T>,
        others: Iterable<Expressionable<T>>,
    ): Expression<T> {
        val expr = listOf(
            value.toExpression(),
            alternate.toExpression(),
        ) + others.map {
            it.toExpression()
        }

        return JpqlCoalesce(expr)
    }

    @SinceJdsl("3.0.0")
    fun <T : Any> nullIf(value: Expressionable<T>, compareValue: Expressionable<out T>): Expression<T> {
        return JpqlNullIf(value.toExpression(), compareValue.toExpression())
    }

    @SinceJdsl("3.0.0")
    fun <T : Any> type(path: Pathable<T>): Expression<KClass<T>> {
        return JpqlType(path.toPath())
    }

    @SinceJdsl("3.0.0")
    fun <T : Any> function(
        type: KClass<T>,
        name: String,
        args: Iterable<Expressionable<*>>
    ): Expression<T> {
        return JpqlFunction(type, name, args.map { it.toExpression() })
    }

    @SinceJdsl("3.0.0")
    fun <T : Any> customExpression(
        type: KClass<T>,
        template: String,
        args: Iterable<Expressionable<*>>
    ): Expression<T> {
        return JpqlCustomExpression(type, template, args.map { it.toExpression() })
    }

    @SinceJdsl("3.0.0")
    fun <T : Any> subquery(
        selectQuery: SelectQuery<T>
    ): Subquery<T> {
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

    @Experimental
    fun <T : Any> all(subquery: Subquery<T>): Expression<T> {
        return JpqlAll(subquery)
    }

    @Experimental
    fun <T : Any> any(subquery: Subquery<T>): Expression<T> {
        return JpqlAny(subquery)
    }

    @Experimental
    fun <T : Any> some(subquery: Subquery<T>): Expression<T> {
        return JpqlSome(subquery)
    }

    @SinceJdsl("3.0.0")
    fun <T : Any> alias(expr: Expressionable<T>, alias: String): Expression<T> {
        val expression = expr.toExpression()

        return if (expression is JpqlAliasedExpression) {
            JpqlAliasedExpression(expression.expr, alias)
        } else {
            JpqlAliasedExpression(expression, alias)
        }
    }
}
