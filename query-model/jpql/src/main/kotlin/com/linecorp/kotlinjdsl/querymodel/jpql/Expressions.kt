package com.linecorp.kotlinjdsl.querymodel.jpql

import com.linecorp.kotlinjdsl.Experimental
import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.ExpressionAndExpression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressionable
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Subquery
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.*
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Path
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicatable
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.select.impl.JpqlSelectQuery
import java.math.BigDecimal
import java.math.BigInteger
import kotlin.reflect.KClass

object Expressions {
    @SinceJdsl("3.0.0")
    fun <T> value(value: T): Expression<T> {
        return if (value == null) {
            @Suppress("UNCHECKED_CAST")
            JpqlNull as Expression<T>
        } else {
            JpqlValue(value)
        }
    }

    @SinceJdsl("3.0.0")
    fun <T> nullValue(): Expression<T?> {
        @Suppress("UNCHECKED_CAST")
        return JpqlNull as Expression<T?>
    }

    @JvmName("literal1")
    @SinceJdsl("3.0.0")
    fun literal(short: Short): Expression<Short> {
        return JpqlLiteral.ShortLiteral(short)
    }

    @JvmName("literal2")
    @SinceJdsl("3.0.0")
    fun literal(short: Short?): Expression<Short?> {
        @Suppress("UNCHECKED_CAST")
        return if (short == null) {
            JpqlLiteral.NullLiteral
        } else {
            JpqlLiteral.ShortLiteral(short)
        } as Expression<Short?>
    }

    @JvmName("literal3")
    @SinceJdsl("3.0.0")
    fun literal(int: Int): Expression<Int> {
        return JpqlLiteral.IntLiteral(int)
    }

    @JvmName("literal4")
    @SinceJdsl("3.0.0")
    fun literal(int: Int?): Expression<Int?> {
        @Suppress("UNCHECKED_CAST")
        return if (int == null) {
            JpqlLiteral.NullLiteral
        } else {
            JpqlLiteral.IntLiteral(int)
        } as Expression<Int?>
    }

    @JvmName("literal5")
    @SinceJdsl("3.0.0")
    fun literal(long: Long): Expression<Long> {
        return JpqlLiteral.LongLiteral(long)
    }

    @JvmName("literal6")
    @SinceJdsl("3.0.0")
    fun literal(long: Long?): Expression<Long?> {
        @Suppress("UNCHECKED_CAST")
        return if (long == null) {
            JpqlLiteral.NullLiteral
        } else {
            JpqlLiteral.LongLiteral(long)
        } as Expression<Long?>
    }

    @JvmName("literal7")
    @SinceJdsl("3.0.0")
    fun literal(float: Float): Expression<Float> {
        return JpqlLiteral.FloatLiteral(float)
    }

    @JvmName("literal8")
    @SinceJdsl("3.0.0")
    fun literal(float: Float?): Expression<Float?> {
        @Suppress("UNCHECKED_CAST")
        return if (float == null) {
            JpqlLiteral.NullLiteral
        } else {
            JpqlLiteral.FloatLiteral(float)
        } as Expression<Float?>
    }

    @JvmName("literal9")
    @SinceJdsl("3.0.0")
    fun literal(double: Double): Expression<Double> {
        return JpqlLiteral.DoubleLiteral(double)
    }

    @JvmName("literal10")
    @SinceJdsl("3.0.0")
    fun literal(double: Double?): Expression<Double?> {
        @Suppress("UNCHECKED_CAST")
        return if (double == null) {
            JpqlLiteral.NullLiteral
        } else {
            JpqlLiteral.DoubleLiteral(double)
        } as Expression<Double?>
    }

    @JvmName("literal11")
    @SinceJdsl("3.0.0")
    fun literal(boolean: Boolean): Expression<Boolean> {
        return JpqlLiteral.BooleanLiteral(boolean)
    }

    @JvmName("literal12")
    @SinceJdsl("3.0.0")
    fun literal(boolean: Boolean?): Expression<Boolean?> {
        @Suppress("UNCHECKED_CAST")
        return if (boolean == null) {
            JpqlLiteral.NullLiteral
        } else {
            JpqlLiteral.BooleanLiteral(boolean)
        } as Expression<Boolean?>
    }

    @JvmName("literal13")
    @SinceJdsl("3.0.0")
    fun literal(string: String): Expression<String> {
        return JpqlLiteral.StringLiteral(string)
    }

    @JvmName("literal14")
    @SinceJdsl("3.0.0")
    fun literal(string: String?): Expression<String?> {
        @Suppress("UNCHECKED_CAST")
        return if (string == null) {
            JpqlLiteral.NullLiteral
        } else {
            JpqlLiteral.StringLiteral(string)
        } as Expression<String?>
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
    fun <T : Number, S : T?> plus(
        value1: Expressionable<in S>,
        value2: Expressionable<in S>,
    ): Expression<T> {
        return JpqlPlus(value1.toExpression(), value2.toExpression())
    }

    @SinceJdsl("3.0.0")
    fun <T : Number, S : T?> minus(
        value1: Expressionable<in S>,
        value2: Expressionable<in S>,
    ): Expression<T> {
        return JpqlMinus(value1.toExpression(), value2.toExpression())
    }

    @SinceJdsl("3.0.0")
    fun <T : Number, S : T?> times(
        value1: Expressionable<in S>,
        value2: Expressionable<in S>,
    ): Expression<T> {
        return JpqlTimes(value1.toExpression(), value2.toExpression())
    }

    @SinceJdsl("3.0.0")
    fun <T : Number, S : T?> div(
        value1: Expressionable<in S>,
        value2: Expressionable<in S>,
    ): Expression<T> {
        return JpqlDivide(value1.toExpression(), value2.toExpression())
    }

    @SinceJdsl("3.0.0")
    fun <T> count(expr: Expressionable<T>, distinct: Boolean): Expression<Long> {
        return JpqlCount(expr.toExpression(), distinct)
    }

    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>, S : T?> max(expr: Expressionable<in S>, distinct: Boolean): Expression<T?> {
        return JpqlMax(expr.toExpression(), distinct)
    }

    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>, S : T?> min(expr: Expressionable<in S>, distinct: Boolean): Expression<T?> {
        return JpqlMin(expr.toExpression(), distinct)
    }

    @SinceJdsl("3.0.0")
    fun <T : Number, S : T?> avg(expr: Expressionable<in S>, distinct: Boolean): Expression<Double?> {
        return JpqlAvg<T, S>(expr.toExpression(), distinct)
    }

    @JvmName("sum1")
    @SinceJdsl("3.0.0")
    fun sum(expr: Expressionable<Int?>, distinct: Boolean): Expression<Long?> {
        return JpqlSum.IntSum(expr.toExpression(), distinct)
    }

    @JvmName("sum2")
    @SinceJdsl("3.0.0")
    fun sum(expr: Expressionable<Long?>, distinct: Boolean): Expression<Long?> {
        return JpqlSum.LongSum(expr.toExpression(), distinct)
    }

    @JvmName("sum3")
    @SinceJdsl("3.0.0")
    fun sum(expr: Expressionable<Float?>, distinct: Boolean): Expression<Double?> {
        return JpqlSum.FloatSum(expr.toExpression(), distinct)
    }

    @JvmName("sum4")
    @SinceJdsl("3.0.0")
    fun sum(expr: Expressionable<Double?>, distinct: Boolean): Expression<Double?> {
        return JpqlSum.DoubleSum(expr.toExpression(), distinct)
    }

    @JvmName("sum5")
    @SinceJdsl("3.0.0")
    fun sum(expr: Expressionable<BigInteger?>, distinct: Boolean): Expression<BigInteger?> {
        return JpqlSum.BigIntegerSum(expr.toExpression(), distinct)
    }

    @JvmName("sum6")
    @SinceJdsl("3.0.0")
    fun sum(expr: Expressionable<BigDecimal?>, distinct: Boolean): Expression<BigDecimal?> {
        return JpqlSum.BigDecimalSum(expr.toExpression(), distinct)
    }

    @SinceJdsl("3.0.0")
    fun <T : Any> new(type: KClass<T>, args: Iterable<Expressionable<*>>): Expression<T> {
        return JpqlNew(type, args.map { it.toExpression() })
    }

    @SinceJdsl("3.0.0")
    fun <T> case(
        whens: Iterable<Pair<Predicatable, Expressionable<T>>>,
    ): Expression<T?> {
        return JpqlCase(
            whens = whens.map { JpqlCaseWhen(it.first.toPredicate(), it.second.toExpression()) },
            `else` = null,
        )
    }

    @SinceJdsl("3.0.0")
    fun <T> case(
        whens: Iterable<Pair<Predicatable, Expressionable<T>>>,
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
        whens: Iterable<Pair<Expressionable<T>, Expressionable<V>>>,
    ): Expression<V?> {
        return JpqlCaseValue(
            value = value.toExpression(),
            whens = whens.map { JpqlCaseValueWhen(it.first.toExpression(), it.second.toExpression()) },
            `else` = null,
        )
    }

    @SinceJdsl("3.0.0")
    fun <T, V> caseValue(
        value: Expressionable<T>,
        whens: Iterable<Pair<Expressionable<T>, Expressionable<V>>>,
        `else`: Expressionable<out V>,
    ): Expression<V> {
        return JpqlCaseValue(
            value = value.toExpression(),
            whens = whens.map { JpqlCaseValueWhen(it.first.toExpression(), it.second.toExpression()) },
            `else` = `else`.toExpression(),
        )
    }

    @SinceJdsl("3.0.0")
    fun <T> coalesce(
        value: Expressionable<in T>,
        alternate: Expressionable<in T>,
        others: Iterable<Expressionable<in T>>,
    ): Expression<T> {
        return JpqlCoalesce(
            listOf<Expression<in T>>(
                value.toExpression(),
                alternate.toExpression(),
            ) + others.map { it.toExpression() },
        )
    }

    @SinceJdsl("3.0.0")
    fun <T> nullIf(value: Expressionable<T>, compareValue: Expressionable<T>): Expression<T?> {
        return JpqlNullIf(value.toExpression(), compareValue.toExpression())
    }

    @JvmName("type1")
    @SinceJdsl("3.0.0")
    fun <T : Any, S : T> type(path: Path<S>): Expression<KClass<T>> {
        return JpqlType(path)
    }

    @JvmName("type2")
    @SinceJdsl("3.0.0")
    fun <T : Any, S : T?> type(path: Path<S>): Expression<KClass<T>?> {
        return JpqlType(path)
    }

    @SinceJdsl("3.0.0")
    fun <T : String?> concat(expr: Iterable<Expression<out T>>): Expression<T> {
        return JpqlConcat(expr)
    }

    @SinceJdsl("3.0.0")
    fun <T> function(name: String, args: Iterable<Expressionable<*>>): Expression<T> {
        return JpqlFunction(name, args.map { it.toExpression() })
    }

    @SinceJdsl("3.0.0")
    fun <T> customExpression(template: String, args: Iterable<Expressionable<*>>): Expression<T> {
        return JpqlCustomExpression(template, args.map { it.toExpression() })
    }

    @SinceJdsl("3.0.0")
    fun <T> subquery(
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
    fun <T> all(subquery: Subquery<T>): Expression<T> {
        return JpqlAll(subquery)
    }

    @Experimental
    fun <T> any(subquery: Subquery<T>): Expression<T> {
        return JpqlAny(subquery)
    }

    @Experimental
    fun <T> some(subquery: Subquery<T>): Expression<T> {
        return JpqlSome(subquery)
    }

    @SinceJdsl("3.0.0")
    fun <T> alias(expr: Expressionable<T>, alias: String): Expression<T> {
        return when (val resolvedExpression = expr.toExpression()) {
            is JpqlAliasedExpression -> JpqlAliasedExpression(resolvedExpression.expr, alias)

            else -> JpqlAliasedExpression(resolvedExpression, alias)
        }
    }

    @SinceJdsl("3.0.0")
    fun <T> pair(first: Expressionable<T>, second: Expressionable<T>): ExpressionAndExpression<T> {
        return JpqlExpressionAndExpression(first.toExpression(), second.toExpression())
    }
}
