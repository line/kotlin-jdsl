package com.linecorp.kotlinjdsl.querymodel.jpql

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.ExpressionAndExpression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressionable
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Subquery
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.*
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Path
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicatable
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import java.math.BigDecimal
import java.math.BigInteger
import kotlin.reflect.KClass

object Expressions {
    @SinceJdsl("3.0.0")
    fun <T> value(value: T): Expression<T> {
        return JpqlValue(value)
    }

    @SinceJdsl("3.0.0")
    fun <T> nullValue(): Expression<T?> {
        return JpqlValue(null)
    }

    @SinceJdsl("3.0.0")
    fun <T> param(name: String): Expression<T> {
        return JpqlParam(name, null)
    }

    @SinceJdsl("3.0.0")
    fun <T> param(name: String, value: T): Expression<T> {
        return JpqlParam(name, value)
    }

    @SinceJdsl("3.0.0")
    fun count(expression: Expressionable<*>, distinct: Boolean): Expression<Long> {
        return JpqlCount(expression.toExpression(), distinct)
    }

    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> max(expression: Expressionable<T?>, distinct: Boolean): Expression<T?> {
        return JpqlMax(expression.toExpression(), distinct)
    }

    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> min(expression: Expressionable<T?>, distinct: Boolean): Expression<T?> {
        return JpqlMin(expression.toExpression(), distinct)
    }

    @SinceJdsl("3.0.0")
    fun avg(expression: Expressionable<out Number?>, distinct: Boolean): Expression<Double?> {
        return JpqlAvg(expression.toExpression(), distinct)
    }

    @JvmName("sum1")
    @SinceJdsl("3.0.0")
    fun sum(expression: Expressionable<Int?>, distinct: Boolean): Expression<Long?> {
        return JpqlSum.IntSum(expression.toExpression(), distinct)
    }

    @JvmName("sum2")
    @SinceJdsl("3.0.0")
    fun sum(expression: Expressionable<Long?>, distinct: Boolean): Expression<Long?> {
        return JpqlSum.LongSum(expression.toExpression(), distinct)
    }

    @JvmName("sum3")
    @SinceJdsl("3.0.0")
    fun sum(expression: Expressionable<Float?>, distinct: Boolean): Expression<Double?> {
        return JpqlSum.FloatSum(expression.toExpression(), distinct)
    }

    @JvmName("sum4")
    @SinceJdsl("3.0.0")
    fun sum(expression: Expressionable<Double?>, distinct: Boolean): Expression<Double?> {
        return JpqlSum.DoubleSum(expression.toExpression(), distinct)
    }

    @JvmName("sum5")
    @SinceJdsl("3.0.0")
    fun sum(expression: Expressionable<BigInteger?>, distinct: Boolean): Expression<BigInteger?> {
        return JpqlSum.BigIntegerSum(expression.toExpression(), distinct)
    }

    @JvmName("sum6")
    @SinceJdsl("3.0.0")
    fun sum(expression: Expressionable<BigDecimal?>, distinct: Boolean): Expression<BigDecimal?> {
        return JpqlSum.BigDecimalSum(expression.toExpression(), distinct)
    }

    @SinceJdsl("3.0.0")
    fun <T : Any> new(type: KClass<T>, args: Collection<Expressionable<*>>): Expression<T> {
        return JpqlNew(type, args.map { it.toExpression() })
    }

    @SinceJdsl("3.0.0")
    fun <T> case(
        whens: Collection<Pair<Predicatable, Expressionable<T>>>,
    ): Expression<T?> {
        @Suppress("UNCHECKED_CAST")
        return JpqlCase(
            whens = whens.map { JpqlCaseWhen(it.first.toPredicate(), it.second.toExpression()) },
            `else` = null,
        ) as Expression<T?>
    }

    @SinceJdsl("3.0.0")
    fun <T> case(
        whens: Collection<Pair<Predicatable, Expressionable<T>>>,
        `else`: Expressionable<out T>
    ): Expression<T> {
        return JpqlCase(
            whens = whens.map { JpqlCaseWhen(it.first.toPredicate(), it.second.toExpression()) },
            `else` = `else`.toExpression(),
        )
    }

    @SinceJdsl("3.0.0")
    fun <T, V> caseValue(
        value: Expressionable<T>,
        whens: Collection<Pair<Expressionable<T>, Expressionable<V>>>,
    ): Expression<V?> {
        @Suppress("UNCHECKED_CAST")
        return JpqlCaseValue(
            value = value.toExpression(),
            whens = whens.map { JpqlCaseValueWhen(it.first.toExpression(), it.second.toExpression()) },
            `else` = null,
        ) as Expression<V?>
    }

    @SinceJdsl("3.0.0")
    fun <T, V> caseValue(
        value: Expressionable<T>,
        whens: Collection<Pair<Expressionable<T>, Expressionable<V>>>,
        `else`: Expressionable<out V>,
    ): Expression<V> {
        return JpqlCaseValue(
            value = value.toExpression(),
            whens = whens.map { JpqlCaseValueWhen(it.first.toExpression(), it.second.toExpression()) },
            `else` = `else`.toExpression(),
        )
    }

    @SinceJdsl("3.0.0")
    fun <T> coalesce(expression: Expressionable<in T>, expressions: Collection<Expressionable<in T>>): Expression<T> {
        return JpqlCoalesce(listOf(expression.toExpression()) + expressions.map { it.toExpression() })
    }

    @SinceJdsl("3.0.0")
    fun <T> nullIf(left: Expressionable<T>, right: Expressionable<T>): Expression<T?> {
        return JpqlNullIf(left.toExpression(), right.toExpression())
    }

    @JvmName("type1")
    @SinceJdsl("3.0.0")
    fun <T : Any, PATH : Path<T>> type(path: PATH): Expression<KClass<T>> {
        return JpqlType(path)
    }

    @JvmName("type2")
    @SinceJdsl("3.0.0")
    fun <T, PATH : Path<T>> type(path: PATH): Expression<KClass<T & Any>?> {
        @Suppress("UNCHECKED_CAST")
        return JpqlType(path) as Expression<KClass<T & Any>?>
    }

    @SinceJdsl("3.0.0")
    fun <T> customExpression(template: String, args: Collection<Expressionable<*>>): Expression<T> {
        return JpqlCustomExpression(template, args.map { it.toExpression() })
    }

    @SinceJdsl("3.0.0")
    fun <T> alias(expression: Expressionable<T>, alias: String): Expression<T> {
        return when (val resolvedExpression = expression.toExpression()) {
            is JpqlAliasedExpression -> JpqlAliasedExpression(resolvedExpression.expression, alias)

            else -> JpqlAliasedExpression(resolvedExpression, alias)
        }
    }

    fun <T> subquery(
        selectQuery: SelectQuery<T>
    ): Subquery<T> {
        return JpqlSubquery(
            selectQuery = selectQuery,
        )
    }

    @SinceJdsl("3.0.0")
    fun <T> pair(first: Expressionable<T>, second: Expressionable<T>): ExpressionAndExpression<T> {
        return JpqlExpressionAndExpression(first.toExpression(), second.toExpression())
    }
}
