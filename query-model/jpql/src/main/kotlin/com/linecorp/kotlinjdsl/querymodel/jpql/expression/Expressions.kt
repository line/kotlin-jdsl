package com.linecorp.kotlinjdsl.querymodel.jpql.expression

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entity
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlAbs
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlAliasedExpression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlAvg
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlCaseValue
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlCaseWhen
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlCeiling
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlCoalesce
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlConcat
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlCount
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlCustomExpression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlDivide
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlEntityType
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlExpression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlExpressionParentheses
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlFloor
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlFunctionExpression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlLength
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlLiteral
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlLocate
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlLower
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlMax
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlMin
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlMinus
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlNew
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlNull
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlNullIf
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlParam
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlPathType
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlPlus
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlRound
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlSign
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlSqrt
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlSubquery
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlSubstring
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlSum
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlTimes
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlTrim
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlTrimBoth
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlTrimLeading
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlTrimTrailing
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlUpper
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlValue
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Path
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicate
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.select.impl.JpqlSelectQuery
import java.math.BigDecimal
import java.math.BigInteger
import kotlin.internal.Exact
import kotlin.reflect.KClass

/**
 * Factory class that creates [Expression].
 */
@SinceJdsl("3.0.0")
object Expressions {
    /**
     * Creates a parameter expression with a generated name and the value.
     */
    @SinceJdsl("3.0.0")
    fun <T> value(value: @Exact T): Expression<T & Any> {
        return if (value == null) {
            nullValue()
        } else {
            JpqlValue(value)
        }
    }

    /**
     * Creates a parameter expression with a generated name and null.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> nullValue(): Expression<T> {
        @Suppress("UNCHECKED_CAST")
        return JpqlNull as Expression<T>
    }

    /**
     * Creates a literal expression with the int.
     */
    @SinceJdsl("3.0.0")
    fun intLiteral(int: Int): Expression<Int> {
        return JpqlLiteral.IntLiteral(int)
    }

    /**
     * Creates a literal expression with the long.
     */
    @SinceJdsl("3.0.0")
    fun longLiteral(long: Long): Expression<Long> {
        return JpqlLiteral.LongLiteral(long)
    }

    /**
     * Creates a literal expression with the float.
     */
    @SinceJdsl("3.0.0")
    fun floatLiteral(float: Float): Expression<Float> {
        return JpqlLiteral.FloatLiteral(float)
    }

    /**
     * Creates a literal expression with the double.
     */
    @SinceJdsl("3.0.0")
    fun doubleLiteral(double: Double): Expression<Double> {
        return JpqlLiteral.DoubleLiteral(double)
    }

    /**
     * Creates a literal expression with the boolean.
     */
    @SinceJdsl("3.0.0")
    fun booleanLiteral(boolean: Boolean): Expression<Boolean> {
        return JpqlLiteral.BooleanLiteral(boolean)
    }

    /**
     * Creates a literal expression with the char.
     * If the char is '(single quote), it is rendered as ''(two single quotes).
     */
    @SinceJdsl("3.0.0")
    fun charLiteral(char: Char): Expression<Char> {
        return JpqlLiteral.CharLiteral(char)
    }

    /**
     * Creates a literal expression with the string.
     * If the string contains '(single quote), it is rendered as ''(two single quotes).
     * For example: literal''s.
     */
    @SinceJdsl("3.0.0")
    fun stringLiteral(string: String): Expression<String> {
        return JpqlLiteral.StringLiteral(string)
    }

    /**
     * Creates a literal expression with the enum.
     */
    @SinceJdsl("3.0.0")
    fun <T : Enum<T>> enumLiteral(enum: T): Expression<T> {
        return JpqlLiteral.EnumLiteral(enum)
    }

    /**
     * Creates a literal expression with null.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> nullLiteral(): Expression<T> {
        @Suppress("UNCHECKED_CAST")
        return JpqlLiteral.NullLiteral as Expression<T>
    }

    /**
     * Creates a parameter expression with the name.
     */
    @SinceJdsl("3.0.0")
    fun <T> param(name: String): Expression<T & Any> {
        return JpqlParam(name, null)
    }

    /**
     * Creates a parameter expression with the name and value.
     * The value can be overridden in rendering.
     */
    @SinceJdsl("3.0.0")
    fun <T> param(name: String, value: @Exact T): Expression<T & Any> {
        return JpqlParam(name, value)
    }

    /**
     * Creates an expression that represents the plus of values.
     *
     * This is the same as ```value1 + value2```.
     */
    @SinceJdsl("3.0.0")
    fun <T : Number, S : T> plus(value1: Expression<T>, value2: Expression<S>): Expression<T> {
        return JpqlPlus(value1, value2)
    }

    /**
     * Creates an expression that represents the minus of values.
     *
     * This is the same as ```value1 - value2```.
     */
    @SinceJdsl("3.0.0")
    fun <T : Number, S : T> minus(value1: Expression<T>, value2: Expression<S>): Expression<T> {
        return JpqlMinus(value1, value2)
    }

    /**
     * Creates an expression that represents the times of values.
     *
     * This is the same as ```value1 * value2```.
     */
    @SinceJdsl("3.0.0")
    fun <T : Number, S : T> times(value1: Expression<T>, value2: Expression<S>): Expression<T> {
        return JpqlTimes(value1, value2)
    }

    /**
     * Creates an expression that represents the divide of values.
     *
     * This is the same as ```value1 / value2```.
     */
    @SinceJdsl("3.0.0")
    fun <T : Number, S : T> div(value1: Expression<T>, value2: Expression<S>): Expression<T> {
        return JpqlDivide(value1, value2)
    }

    /**
     * Creates an expression that represents the absolute value.
     */
    @SinceJdsl("3.4.0")
    fun <T : Number> abs(value: Expression<T>): Expression<T> {
        return JpqlAbs(value)
    }

    /**
     * Creates an expression that is enclosed in ceiling.
     */
    @SinceJdsl("3.4.0")
    fun <T : Number> ceiling(value: Expression<T>): Expression<T> {
        return JpqlCeiling(value)
    }

    /**
     * Creates an expression that is enclosed in floor.
     */
    @SinceJdsl("3.4.0")
    fun <T : Number> floor(value: Expression<T>): Expression<T> {
        return JpqlFloor(value)
    }

    /**
     * Creates an expression that represents the rounding of the specified value to a specified scale.
     */
    @SinceJdsl("3.4.0")
    fun <T : Number> round(value: Expression<T>, scale: Expression<Int>): Expression<T> {
        return JpqlRound(value, scale)
    }

    /**
     * Creates an expression that represents the sign of a numeric value.
     */
    @SinceJdsl("3.4.0")
    fun <T : Number> sign(value: Expression<T>): Expression<Int> {
        return JpqlSign(value)
    }

    /**
     * Creates an expression that represents the square root of the value.
     */
    @SinceJdsl("3.4.0")
    fun <T : Number> sqrt(value: Expression<T>): Expression<Double> {
        return JpqlSqrt(value)
    }

    /**
     * Creates an expression that represents the count of non-null values.
     *
     * If there are no matching rows, it returns 0.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> count(distinct: Boolean, expr: Expression<T>): Expression<Long> {
        return JpqlCount(distinct, expr)
    }

    /**
     * Creates an expression that represents the maximum value.
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<*>> max(distinct: Boolean, expr: Expression<T>): Expression<T> {
        return JpqlMax(distinct, expr)
    }

    /**
     * Creates an expression that represents the minimum value.
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<*>> min(distinct: Boolean, expr: Expression<T>): Expression<T> {
        return JpqlMin(distinct, expr)
    }

    /**
     * Creates an expression that represents the average value.
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @SinceJdsl("3.0.0")
    fun <T : Number> avg(distinct: Boolean, expr: Expression<T>): Expression<Double> {
        return JpqlAvg(distinct, expr)
    }

    /**
     * Creates an expression that represents the sum of values.
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sum1")
    @SinceJdsl("3.0.0")
    fun sum(distinct: Boolean, expr: Expression<Int>): Expression<Long> {
        return JpqlSum.IntSum(distinct, expr)
    }

    /**
     * Creates an expression that represents the sum of values.
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sum2")
    @SinceJdsl("3.0.0")
    fun sum(distinct: Boolean, expr: Expression<Long>): Expression<Long> {
        return JpqlSum.LongSum(distinct, expr)
    }

    /**
     * Creates an expression that represents the sum of values.
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sum3")
    @SinceJdsl("3.0.0")
    fun sum(distinct: Boolean, expr: Expression<Float>): Expression<Double> {
        return JpqlSum.FloatSum(distinct, expr)
    }

    /**
     * Creates an expression that represents the sum of values.
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sum4")
    @SinceJdsl("3.0.0")
    fun sum(distinct: Boolean, expr: Expression<Double>): Expression<Double> {
        return JpqlSum.DoubleSum(distinct, expr)
    }

    /**
     * Creates an expression that represents the sum of values.
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sum5")
    @SinceJdsl("3.0.0")
    fun sum(distinct: Boolean, expr: Expression<BigInteger>): Expression<BigInteger> {
        return JpqlSum.BigIntegerSum(distinct, expr)
    }

    /**
     * Creates an expression that represents the sum of values.
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sum6")
    @SinceJdsl("3.0.0")
    fun sum(distinct: Boolean, expr: Expression<BigDecimal>): Expression<BigDecimal> {
        return JpqlSum.BigDecimalSum(distinct, expr)
    }

    /**
     * Creates an expression that represents the DTO projection.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> new(type: KClass<T>, args: Iterable<Expression<*>>): Expression<T> {
        return JpqlNew(type, args)
    }

    /**
     * Creates an expression that represents the result for the first predicate that is true.
     * If no predicate is true, the result after `ELSE` is returned, or null if there is no `ELSE`.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> caseWhen(
        whens: Map<Predicate, Expression<T>>,
        `else`: Expression<T>? = null,
    ): Expression<T> {
        return JpqlCaseWhen(
            whens = whens,
            `else` = `else`,
        )
    }

    /**
     * Creates an expression that represents the result for the first value = compareValue comparison that is true.
     * If no comparison is true, the result after `ELSE` is returned, or null if there is no `ELSE` part.
     */
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

    /**
     * Creates an expression that represents the first non-null value in the values,
     * or null if there are no non-null value.
     */
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

    /**
     * Creates an expression that represents null if left = right is true, otherwise represents left.
     *
     * This is the same as ```CASE WHEN left = right THEN NULL ELSE left END```.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> nullIf(value: Expression<T>, compareValue: Expression<T>): Expression<T> {
        return JpqlNullIf(value, compareValue)
    }

    /**
     * Creates an expression that represents the type of the entity.
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
    fun type(entity: Entity<*>): Expression<KClass<*>> {
        return JpqlEntityType(entity)
    }

    /**
     * Creates an expression that represents the type of the path.
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
    fun type(path: Path<*>): Expression<KClass<*>> {
        return JpqlPathType(path)
    }

    /**
     * Creates an expression that represents the concatenation of two or more string values.
     */
    @SinceJdsl("3.2.0")
    fun concat(
        value1: Expression<String>,
        value2: Expression<String>,
        others: Iterable<Expression<String>>,
    ): Expression<String> {
        val values = listOf(
            value1,
            value2,
        ) + others

        return JpqlConcat(values)
    }

    /**
     * Creates an expression that represents a substring of the specified length from the start position of the string.
     * If the length is not specified, it is returned from the start position of the string to the end of the string.
     * The first position of a string is 1.
     */
    @SinceJdsl("3.2.0")
    fun substring(
        value: Expression<String>,
        start: Expression<Int>,
        length: Expression<Int>? = null,
    ): Expression<String> {
        return JpqlSubstring(value, start, length)
    }

    /**
     * Creates an expression that represents a string with the specified characters all trimmed
     * from the both sides of the string.
     * If the character is not specified, it will be assumed to be whitespace.
     */
    @SinceJdsl("3.1.0")
    fun trim(
        character: Expression<Char>? = null,
        value: Expression<String>,
    ): Expression<String> {
        return JpqlTrim(character, value)
    }

    /**
     * Creates an expression that represents a string with the specified characters all trimmed
     * from the leading side of the string.
     * If the character is not specified, it will be assumed to be whitespace.
     */
    @SinceJdsl("3.1.0")
    fun trimLeading(
        character: Expression<Char>? = null,
        value: Expression<String>,
    ): Expression<String> {
        return JpqlTrimLeading(character, value)
    }

    /**
     * Creates an expression that represents a string with the specified characters all trimmed
     * from the trailing side of the string.
     * If the character is not specified, it will be assumed to be whitespace.
     */
    @SinceJdsl("3.1.0")
    fun trimTrailing(
        character: Expression<Char>? = null,
        value: Expression<String>,
    ): Expression<String> {
        return JpqlTrimTrailing(character, value)
    }

    /**
     * Creates an expression that represents a string with the specified characters all trimmed
     * from the both sides of the string.
     * If the character is not specified, it will be assumed to be whitespace.
     */
    @SinceJdsl("3.1.0")
    fun trimBoth(
        character: Expression<Char>? = null,
        value: Expression<String>,
    ): Expression<String> {
        return JpqlTrimBoth(character, value)
    }

    /**
     * Creates an expression that represents the string in uppercase.
     */
    @SinceJdsl("3.0.0")
    fun upper(value: Expression<String>): Expression<String> {
        return JpqlUpper(value)
    }

    /**
     * Creates an expression that represents the string in lowercase.
     */
    @SinceJdsl("3.0.0")
    fun lower(value: Expression<String>): Expression<String> {
        return JpqlLower(value)
    }

    /**
     * Creates an expression that represents the length of the string as an integer.
     */
    @SinceJdsl("3.0.0")
    fun length(value: Expression<String>): Expression<Int> {
        return JpqlLength(value)
    }

    /**
     * Creates an expression that represents the position of the first occurrence of a substring in a string.
     * If the substring is not found, returns 0.
     * The position starts with 1.
     */
    @SinceJdsl("3.0.0")
    fun locate(
        substring: Expression<String>,
        string: Expression<String>,
        start: Expression<Int>? = null,
    ): Expression<Int> {
        return JpqlLocate(substring, string, start)
    }

    /**
     * Creates an expression that represents predefined database functions and user-defined database functions.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> function(type: KClass<T>, name: String, args: Iterable<Expression<*>>): Expression<T> {
        return JpqlFunctionExpression(type, name, args)
    }

    /**
     * Creates an expression that represents the user-defined expression.
     *
     * The template for the user-defined expression can have placeholders.
     * Placeholders in template are replaced with the expression in args, matching with index.
     *
     * ```
     * Placeholder: { ArgumentIndex }
     * ```
     *
     * Examples:
     * ```
     * Expressions.customExpression(String::class, "CAST({0} AS VARCHAR)", listOf(Paths.path(User::age)))
     * ```
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> customExpression(
        type: KClass<T>,
        template: String,
        args: Iterable<Expression<*>>,
    ): Expression<T> {
        return JpqlCustomExpression(type, template, args)
    }

    /**
     * Creates a subquery with the select query.
     */
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

    /**
     * Creates an aliased expression with the alias expression.
     * The aliased expression can be referenced by the alias expression.
     *
     * @see expression for creating an alias expression.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> alias(expr: Expression<T>, alias: Expression<T>): Expression<T> {
        return if (expr is JpqlAliasedExpression) {
            JpqlAliasedExpression(expr.expr, alias)
        } else {
            JpqlAliasedExpression(expr, alias)
        }
    }

    /**
     * Creates an expression to reference.
     * The expression can be used for aliasing and referencing.
     *
     * @see alias for aliasing an expression.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> expression(type: KClass<T>, alias: String): Expression<T> {
        return JpqlExpression(type, alias)
    }

    /**
     * Creates an expression that is enclosed in parentheses.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> parentheses(expr: Expression<T>): Expression<T> {
        return JpqlExpressionParentheses(expr)
    }
}
