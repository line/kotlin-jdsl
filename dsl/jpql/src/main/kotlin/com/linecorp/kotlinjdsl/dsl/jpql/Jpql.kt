package com.linecorp.kotlinjdsl.dsl.jpql

import com.linecorp.kotlinjdsl.Experimental
import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.dsl.jpql.delete.DeleteQueryWhereStep
import com.linecorp.kotlinjdsl.dsl.jpql.delete.impl.DeleteQueryDsl
import com.linecorp.kotlinjdsl.dsl.jpql.expression.CaseValueWhenFirstStep
import com.linecorp.kotlinjdsl.dsl.jpql.expression.CaseWhenStep
import com.linecorp.kotlinjdsl.dsl.jpql.expression.impl.CaseDsl
import com.linecorp.kotlinjdsl.dsl.jpql.expression.impl.CaseValueWhenFirstStepDsl
import com.linecorp.kotlinjdsl.dsl.jpql.select.SelectQueryFromStep
import com.linecorp.kotlinjdsl.dsl.jpql.select.impl.SelectQueryFromStepDsl
import com.linecorp.kotlinjdsl.dsl.jpql.update.UpdateQuerySetStep
import com.linecorp.kotlinjdsl.dsl.jpql.update.impl.UpdateQuerySetStepDsl
import com.linecorp.kotlinjdsl.querymodel.jpql.*
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.ExpressionAndExpression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressionable
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Subquery
import com.linecorp.kotlinjdsl.querymodel.jpql.path.JoinType
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Path
import com.linecorp.kotlinjdsl.querymodel.jpql.path.PathAndExpression
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicatable
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicate
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.sort.Sort
import java.math.BigDecimal
import java.math.BigInteger
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

    @JvmName("value1")
    @SinceJdsl("3.0.0")
    fun <T> value(value: T): Expression<T> {
        return Expressions.value(value)
    }

    @JvmName("nullValue1")
    @SinceJdsl("3.0.0")
    fun <T> nullValue(): Expression<T?> {
        return Expressions.nullValue()
    }

    @JvmName("literal1")
    @SinceJdsl("3.0.0")
    fun literal(short: Short): Expression<Short> {
        return Expressions.literal(short)
    }

    @JvmName("literal2")
    @SinceJdsl("3.0.0")
    fun literal(short: Short?): Expression<Short?> {
        return Expressions.literal(short)
    }

    @JvmName("literal3")
    @SinceJdsl("3.0.0")
    fun literal(int: Int): Expression<Int> {
        return Expressions.literal(int)
    }

    @JvmName("literal4")
    @SinceJdsl("3.0.0")
    fun literal(int: Int?): Expression<Int?> {
        return Expressions.literal(int)
    }

    @JvmName("literal5")
    @SinceJdsl("3.0.0")
    fun literal(long: Long): Expression<Long> {
        return Expressions.literal(long)
    }

    @JvmName("literal6")
    @SinceJdsl("3.0.0")
    fun literal(long: Long?): Expression<Long?> {
        return Expressions.literal(long)
    }

    @JvmName("literal7")
    @SinceJdsl("3.0.0")
    fun literal(float: Float): Expression<Float> {
        return Expressions.literal(float)
    }

    @JvmName("literal8")
    @SinceJdsl("3.0.0")
    fun literal(float: Float?): Expression<Float?> {
        return Expressions.literal(float)
    }

    @JvmName("literal9")
    @SinceJdsl("3.0.0")
    fun literal(double: Double): Expression<Double> {
        return Expressions.literal(double)
    }

    @JvmName("literal10")
    @SinceJdsl("3.0.0")
    fun literal(double: Double?): Expression<Double?> {
        return Expressions.literal(double)
    }

    @JvmName("literal11")
    @SinceJdsl("3.0.0")
    fun literal(boolean: Boolean): Expression<Boolean> {
        return Expressions.literal(boolean)
    }

    @JvmName("literal12")
    @SinceJdsl("3.0.0")
    fun literal(boolean: Boolean?): Expression<Boolean?> {
        return Expressions.literal(boolean)
    }

    @JvmName("literal13")
    @SinceJdsl("3.0.0")
    fun literal(string: String): Expression<String> {
        return Expressions.literal(string)
    }

    @JvmName("literal14")
    @SinceJdsl("3.0.0")
    fun literal(string: String?): Expression<String?> {
        return Expressions.literal(string)
    }

    @JvmName("param1")
    @SinceJdsl("3.0.0")
    fun <T> param(name: String): Expression<T> {
        return Expressions.param(name)
    }

    @JvmName("param2")
    @SinceJdsl("3.0.0")
    fun <T> param(name: String, value: T): Expression<T> {
        return Expressions.param(name, value)
    }

    @JvmName("to1")
    @SinceJdsl("3.0.0")
    infix fun <T> Path<T>.to(value: T): PathAndExpression<T> {
        return Paths.pair(this, Expressions.value(value))
    }

    @JvmName("to2")
    @SinceJdsl("3.0.0")
    infix fun <T> Path<T>.to(value: Expressionable<T>): PathAndExpression<T> {
        return Paths.pair(this, value)
    }

    @JvmName("to3")
    @SinceJdsl("3.0.0")
    infix fun <T> Expressionable<T>.to(value: T): ExpressionAndExpression<T> {
        return Expressions.pair(this, Expressions.value(value))
    }

    @JvmName("to4")
    @SinceJdsl("3.0.0")
    infix fun <T> Expressionable<T>.to(value: Expressionable<T>): ExpressionAndExpression<T> {
        return Expressions.pair(this, value)
    }

    @JvmName("entity1")
    @SinceJdsl("3.0.0")
    fun <T : Any> entity(type: KClass<T>, alias: String = type.simpleName!!): Path<T> {
        return Paths.entity(type, alias)
    }

    @JvmName("path1")
    @SinceJdsl("3.0.0")
    inline fun <reified V> path(property: KProperty1<*, V>): Path<V> {
        return Paths.path(property)
    }

    @JvmName("path2")
    @SinceJdsl("3.0.0")
    inline fun <reified T : Any, reified V> Path<T>.path(property: KProperty1<T, V>): Path<V> {
        return Paths.path(this, property)
    }

    @JvmName("path3")
    @SinceJdsl("3.0.0")
    inline fun <reified T : Any, reified V> Path<T?>.path(property: KProperty1<T, V>): Path<V?> {
        return Paths.path(this, property)
    }

    @JvmName("invoke1")
    @SinceJdsl("3.0.0")
    inline operator fun <reified T : Any, reified V> Path<T>.invoke(property: KProperty1<T, V>): Path<V> {
        return Paths.path(this, property)
    }

    @JvmName("invoke2")
    @SinceJdsl("3.0.0")
    inline operator fun <reified T : Any, reified V> Path<T?>.invoke(property: KProperty1<T, V>): Path<V?> {
        return Paths.path(this, property)
    }

    @JvmName("as1")
    @SinceJdsl("3.0.0")
    fun <T> Path<T>.`as`(alias: String): Path<T> {
        return Paths.alias(this, alias)
    }

    @JvmName("as2")
    @SinceJdsl("3.0.0")
    fun <T> Expressionable<T>.`as`(alias: String): Expression<T> {
        return Expressions.alias(this, alias)
    }

    @JvmName("alias1")
    @SinceJdsl("3.0.0")
    fun <T> Path<T>.alias(alias: String): Path<T> {
        return Paths.alias(this, alias)
    }

    @JvmName("alias2")
    @SinceJdsl("3.0.0")
    fun <T> Expressionable<T>.alias(alias: String): Expression<T> {
        return Expressions.alias(this, alias)
    }

    @JvmName("treat1")
    @SinceJdsl("3.0.0")
    fun <PARENT : Any, CHILD : PARENT> Path<PARENT>.treat(type: KClass<CHILD>): Path<CHILD> {
        return Paths.treat(this, type)
    }

    @JvmName("treat2")
    @SinceJdsl("3.0.0")
    fun <PARENT : Any, CHILD : PARENT> Path<PARENT?>.treat(type: KClass<CHILD>): Path<CHILD?> {
        return Paths.treat(this, type)
    }

    @JvmName("plus1")
    @SinceJdsl("3.0.0")
    fun <T : Number, S : T?> Expressionable<in S>.plus(value: T): Expression<T> {
        return Expressions.plus(this, Expressions.value(value))
    }

    @JvmName("plus2")
    @SinceJdsl("3.0.0")
    fun <T : Number, S : T?> Expressionable<in S>.plus(value: Expressionable<in S>): Expression<T> {
        return Expressions.plus(this, value)
    }

    @JvmName("minus1")
    @SinceJdsl("3.0.0")
    fun <T : Number, S : T?> Expressionable<in S>.minus(value: T): Expression<T> {
        return Expressions.minus(this, Expressions.value(value))
    }

    @JvmName("minus2")
    @SinceJdsl("3.0.0")
    fun <T : Number, S : T?> Expressionable<in S>.minus(value: Expressionable<in S>): Expression<T> {
        return Expressions.minus(this, value)
    }

    @JvmName("times1")
    @SinceJdsl("3.0.0")
    fun <T : Number, S : T?> Expressionable<in S>.times(value: T): Expression<T> {
        return Expressions.times(this, Expressions.value(value))
    }

    @JvmName("times2")
    @SinceJdsl("3.0.0")
    fun <T : Number, S : T?> Expressionable<in S>.times(value: Expressionable<in S>): Expression<T> {
        return Expressions.times(this, value)
    }

    @JvmName("div1")
    @SinceJdsl("3.0.0")
    fun <T : Number, S : T?> Expressionable<in S>.div(value: Expressionable<in S>): Expression<T> {
        return Expressions.div(this, value)
    }

    @JvmName("div2")
    @SinceJdsl("3.0.0")
    fun <T : Number, S : T?> Expressionable<in S>.div(value: T): Expression<T> {
        return Expressions.div(this, Expressions.value(value))
    }

    /**
     * Expression that returns a count of the number of non-null values of [expr].
     *
     * If there are no matching rows, it returns 0.
     */
    @JvmName("count1")
    @SinceJdsl("3.0.0")
    fun count(expr: Expressionable<*>, distinct: Boolean = false): Expression<Long> {
        return Expressions.count(expr, distinct)
    }

    /**
     * Expression that returns a count of the number of non-null values of [expr].
     *
     * If there are no matching rows, it returns 0.
     */
    @JvmName("countDistinct1")
    @SinceJdsl("3.0.0")
    fun countDistinct(expr: Expressionable<*>): Expression<Long> {
        return Expressions.count(expr, distinct = true)
    }

    /**
     * Expression that returns the maximum value of [expr].
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("max1")
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>, S : T?> max(expr: Expressionable<in S>, distinct: Boolean = false): Expression<T?> {
        return Expressions.max(expr, distinct)
    }

    /**
     * Expression that returns the maximum value of [expr].
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("maxDistinct1")
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>, S : T?> maxDistinct(expr: Expressionable<in S>): Expression<T?> {
        return Expressions.max(expr, distinct = true)
    }

    /**
     * Expression that returns the minimum value of [expr].
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("min1")
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>, S : T?> min(expr: Expressionable<in S>, distinct: Boolean = false): Expression<T?> {
        return Expressions.min(expr, distinct)
    }

    /**
     * Expression that returns the minimum value of [expr].
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("minDistinct1")
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>, S : T?> minDistinct(expr: Expressionable<in S>): Expression<T?> {
        return Expressions.min(expr, distinct = true)
    }

    /**
     * Expression that returns the average value of [expr].
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("avg1")
    @SinceJdsl("3.0.0")
    fun <T : Number, S : T?> avg(expr: Expressionable<in S>, distinct: Boolean = false): Expression<Double?> {
        return Expressions.avg(expr, distinct)
    }

    /**
     * Expression that returns the average value of [expr]
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("avgDistinct1")
    @SinceJdsl("3.0.0")
    fun <T : Number, S : T?> avgDistinct(expr: Expressionable<in S>): Expression<Double?> {
        return Expressions.avg(expr, distinct = true)
    }

    /**
     * Expression that returns the sum of [expr].
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sum1")
    @SinceJdsl("3.0.0")
    fun sum(expr: Expressionable<Int?>, distinct: Boolean = false): Expression<Long?> {
        return Expressions.sum(expr, distinct)
    }

    /**
     * Expression that returns the sum of [expr].
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sum2")
    @SinceJdsl("3.0.0")
    fun sum(expr: Expressionable<Long?>, distinct: Boolean = false): Expression<Long?> {
        return Expressions.sum(expr, distinct)
    }

    /**
     * Expression that returns the sum of [expr].
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sum3")
    @SinceJdsl("3.0.0")
    fun sum(expr: Expressionable<Float?>, distinct: Boolean = false): Expression<Double?> {
        return Expressions.sum(expr, distinct)
    }

    /**
     * Expression that returns the sum of [expr].
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sum4")
    @SinceJdsl("3.0.0")
    fun sum(expr: Expressionable<Double?>, distinct: Boolean = false): Expression<Double?> {
        return Expressions.sum(expr, distinct)
    }

    /**
     * Expression that returns the sum of [expr].
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sum5")
    @SinceJdsl("3.0.0")
    fun sum(expr: Expressionable<BigInteger?>, distinct: Boolean = false): Expression<BigInteger?> {
        return Expressions.sum(expr, distinct)
    }

    /**
     * Expression that returns the sum of [expr].
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sum6")
    @SinceJdsl("3.0.0")
    fun sum(expr: Expressionable<BigDecimal?>, distinct: Boolean = false): Expression<BigDecimal?> {
        return Expressions.sum(expr, distinct)
    }

    /**
     * Expression that returns the sum of [expr].
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sumDistinct1")
    @SinceJdsl("3.0.0")
    fun sumDistinct(expr: Expressionable<Int?>): Expression<Long?> {
        return Expressions.sum(expr, distinct = true)
    }

    /**
     * Expression that returns the sum of [expr].
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sumDistinct2")
    @SinceJdsl("3.0.0")
    fun sumDistinct(expr: Expressionable<Long?>): Expression<Long?> {
        return Expressions.sum(expr, distinct = true)
    }

    /**
     * Expression that returns the sum of [expr].
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sumDistinct3")
    @SinceJdsl("3.0.0")
    fun sumDistinct(expr: Expressionable<Float?>): Expression<Double?> {
        return Expressions.sum(expr, distinct = true)
    }

    /**
     * Expression that returns the sum of [expr].
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sumDistinct4")
    @SinceJdsl("3.0.0")
    fun sumDistinct(expr: Expressionable<Double?>): Expression<Double?> {
        return Expressions.sum(expr, distinct = true)
    }

    /**
     * Expression that returns the sum of [expr].
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sumDistinct5")
    @SinceJdsl("3.0.0")
    fun sumDistinct(expr: Expressionable<BigInteger?>): Expression<BigInteger?> {
        return Expressions.sum(expr, distinct = true)
    }

    /**
     * Expression that returns the sum of [expr].
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sumDistinct6")
    @SinceJdsl("3.0.0")
    fun sumDistinct(expr: Expressionable<BigDecimal?>): Expression<BigDecimal?> {
        return Expressions.sum(expr, distinct = true)
    }

    @JvmName("new1")
    @SinceJdsl("3.0.0")
    fun <T : Any> new(type: KClass<T>, vararg args: Expressionable<*>): Expression<T> {
        return Expressions.new(type, args.toList())
    }

    @JvmName("new2")
    @SinceJdsl("3.0.0")
    fun <T : Any> new(type: KClass<T>, args: Iterable<Expressionable<*>>): Expression<T> {
        return Expressions.new(type, args)
    }

    /**
     * Expression that returns the result for the first value = compareValue comparison that is true.
     * If no comparison is true, the result after ELSE is returned, or NULL if there is no ELSE part.
     */
    @JvmName("case1")
    @SinceJdsl("3.0.0")
    fun <T> case(value: Expressionable<T>): CaseValueWhenFirstStep<T> {
        return CaseValueWhenFirstStepDsl(value.toExpression())
    }

    /**
     * Expression that returns the result for the first predicate that is true.
     * If no predicate is true, the result after ELSE is returned, or NULL if there is no ELSE part.
     */
    @JvmName("caseWhen1")
    @SinceJdsl("3.0.0")
    fun <T> caseWhen(predicate: Predicatable, then: T): CaseWhenStep<T?> {
        return CaseDsl(predicate.toPredicate(), Expressions.value(then))
    }

    /**
     * Expression that returns the result for the first predicate that is true.
     * If no predicate is true, the result after ELSE is returned, or NULL if there is no ELSE part.
     */
    @JvmName("caseWhen2")
    @SinceJdsl("3.0.0")
    fun <T> caseWhen(predicate: Predicatable, then: Expressionable<T>): CaseWhenStep<T?> {
        @Suppress("UNCHECKED_CAST")
        return CaseDsl(predicate.toPredicate(), then.toExpression()) as CaseWhenStep<T?>
    }

    /**
     * Expression that returns the first non-null value in the expressions,
     * or null if there are no non-null value in expressions.
     *
     * If there is non-null type expression in expressions,
     * the return type is automatically determined as non-null expression.
     * It can be also determined as non-null expression by specifying generic as the non-null type.
     */
    @JvmName("coalesce1")
    @Experimental
    fun <T> coalesce(
        value: Expressionable<in T>,
        alternate: Expressionable<in T>,
        vararg others: Expressionable<in T>
    ): Expression<T> {
        return Expressions.coalesce(value, alternate, others.toList())
    }

    /**
     * Expression that returns null if left = right is true, otherwise returns left.
     *
     * This is the same as ```CASE WHEN left = right THEN NULL ELSE left END. ```
     */
    @JvmName("nullIf1")
    @Experimental
    fun <T> nullIf(value: Expressionable<T>, compareValue: Expressionable<T>): Expression<T?> {
        return Expressions.nullIf(value, compareValue)
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
    @JvmName("type1")
    @SinceJdsl("3.0.0")
    fun <T : Any, S : T> type(path: Path<S>): Expression<KClass<T>> {
        return Expressions.type(path)
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
    @JvmName("type2")
    @SinceJdsl("3.0.0")
    fun <T : Any, S : T?> type(path: Path<S>): Expression<KClass<T>?> {
        return Expressions.type(path)
    }

    @JvmName("concat1")
    @Experimental
    fun <T : String?> concat(expr: Iterable<Expression<out T>>): Expression<T> {
        return Expressions.concat(expr)
    }

    @JvmName("function1")
    @SinceJdsl("3.0.0")
    fun <T> function(name: String, args: Iterable<Expressionable<*>>): Expression<T> {
        return Expressions.function(name, args.map { it.toExpression() })
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
     * customExpression("CAST({0} AS VARCHAR)", path(User::age))
     * ```
     */
    @JvmName("customExpression1")
    @SinceJdsl("3.0.0")
    fun <T> customExpression(template: String, vararg args: Expressionable<*>): Expression<T> {
        return Expressions.customExpression(template, args.toList())
    }

    @JvmName("asSubquery1")
    @SinceJdsl("3.0.0")
    fun <T> JpqlQueryable<SelectQuery<T>>.asSubquery(): Subquery<T> {
        return Expressions.subquery(this.toQuery())
    }

    @JvmName("all1")
    @SinceJdsl("3.0.0")
    fun <T> all(subquery: Subquery<T>): Expression<T> {
        return Expressions.all(subquery)
    }

    @JvmName("any1")
    @SinceJdsl("3.0.0")
    fun <T> any(subquery: Subquery<T>): Expression<T> {
        return Expressions.any(subquery)
    }

    @JvmName("some1")
    @SinceJdsl("3.0.0")
    fun <T> some(subquery: Subquery<T>): Expression<T> {
        return Expressions.some(subquery)
    }

    @JvmName("join1")
    fun Path<*>.join(
        path: Path<*>,
        on: Predicate? = null,
        joinType: JoinType = JoinType.INNER,
        fetch: Boolean = false,
    ): Path<Any> {
        return Paths.join(this, path, on, joinType, fetch)
    }

    @JvmName("join2")
    fun Path<*>.join(
        path: Path<*>,
        on: Predicate? = null,
        fetch: Boolean = false,
    ): Path<Any> {
        return Paths.join(this, path, on, JoinType.INNER, fetch)
    }

    @JvmName("innerJoin1")
    fun Path<*>.innerJoin(
        path: Path<*>,
        on: Predicate? = null,
        fetch: Boolean = false,
    ): Path<Any> {
        return Paths.join(this, path, on, JoinType.INNER, fetch)
    }

    @JvmName("leftJoin1")
    fun Path<*>.leftJoin(
        path: Path<*>,
        on: Predicate? = null,
        fetch: Boolean = false,
    ): Path<Any> {
        return Paths.join(this, path, on, JoinType.LEFT, fetch)
    }

    @JvmName("joinFetch1")
    fun Path<*>.joinFetch(
        path: Path<*>,
        on: Predicate? = null,
        joinType: JoinType = JoinType.INNER,
    ): Path<Any> {
        return Paths.join(this, path, on, joinType, fetch = true)
    }

    @JvmName("joinFetch2")
    fun Path<*>.joinFetch(
        path: Path<*>,
        on: Predicate? = null,
    ): Path<Any> {
        return Paths.join(this, path, on, JoinType.INNER, fetch = true)
    }

    @JvmName("innerJoinFetch1")
    fun Path<*>.innerJoinFetch(
        path: Path<*>,
        on: Predicate? = null,
    ): Path<Any> {
        return Paths.join(this, path, on, JoinType.INNER, fetch = true)
    }

    @JvmName("leftJoinFetch1")
    fun Path<*>.leftJoinFetch(
        path: Path<*>,
        on: Predicate? = null,
    ): Path<Any> {
        return Paths.join(this, path, on, JoinType.LEFT, fetch = true)
    }

    @JvmName("fetch1")
    fun Path<*>.fetch(
        path: Path<*>,
        on: Predicate? = null,
        joinType: JoinType = JoinType.INNER,
    ): Path<Any> {
        return Paths.join(this, path, on, joinType, fetch = true)
    }

    @JvmName("fetch2")
    fun Path<*>.fetch(
        path: Path<*>,
        on: Predicate? = null,
    ): Path<Any> {
        return Paths.join(this, path, on, JoinType.INNER, fetch = true)
    }

    @JvmName("innerFetch1")
    fun Path<*>.innerFetch(
        path: Path<*>,
        on: Predicate? = null,
    ): Path<Any> {
        return Paths.join(this, path, on, JoinType.INNER, fetch = true)
    }

    @JvmName("leftFetch1")
    fun Path<*>.leftFetch(
        path: Path<*>,
        on: Predicate? = null,
    ): Path<Any> {
        return Paths.join(this, path, on, JoinType.LEFT, fetch = true)
    }

    @JvmName("not1")
    @SinceJdsl("3.0.0")
    fun not(predicate: Predicatable): Predicate {
        return Predicates.not(predicate)
    }

    @JvmName("and1")
    fun and(vararg predicates: Predicatable?): Predicate {
        return Predicates.and(predicates.toList())
    }

    @JvmName("and2")
    fun and(predicates: Iterable<Predicatable?>): Predicate {
        return Predicates.and(predicates)
    }

    @JvmName("and3")
    fun Predicatable.and(predicate: Predicatable): Predicate {
        return Predicates.and(listOf(this, predicate))
    }

    @JvmName("or1")
    fun or(vararg predicates: Predicatable?): Predicate {
        return Predicates.or(predicates.toList())
    }

    @JvmName("or2")
    fun or(predicates: Iterable<Predicatable?>): Predicate {
        return Predicates.or(predicates)
    }

    @JvmName("or3")
    fun Predicatable.or(predicate: Predicatable): Predicate {
        return Predicates.or(listOf(this, predicate))
    }

    @JvmName("isNull1")
    fun <T> Expressionable<T>.isNull(): Predicate {
        return Predicates.isNull(this)
    }

    @JvmName("isNotNull1")
    fun <T> Expressionable<T>.isNotNull(): Predicate {
        return Predicates.isNotNull(this)
    }

    @JvmName("equal1")
    @SinceJdsl("3.0.0")
    infix fun <T> Expressionable<T>.equal(value: T): Predicate {
        return Predicates.equal(this, Expressions.value(value))
    }

    @JvmName("equal2")
    @SinceJdsl("3.0.0")
    infix fun <T, S1 : T?, S2 : T?> Expressionable<in S1>.equal(value: Expressionable<in S2>): Predicate {
        return Predicates.equal(this, value)
    }

    @JvmName("eq1")
    @SinceJdsl("3.0.0")
    infix fun <T> Expressionable<T>.eq(value: T): Predicate {
        return Predicates.equal(this, Expressions.value(value))
    }

    @JvmName("eq2")
    @SinceJdsl("3.0.0")
    infix fun <T, S1 : T?, S2 : T?> Expressionable<in S1>.eq(value: Expressionable<in S2>): Predicate {
        return Predicates.equal(this, value)
    }

    @JvmName("notEqual1")
    @SinceJdsl("3.0.0")
    infix fun <T> Expressionable<T>.notEqual(value: T): Predicate {
        return Predicates.notEqual(this, Expressions.value(value))
    }

    @JvmName("notEqual2")
    @SinceJdsl("3.0.0")
    infix fun <T, S1 : T?, S2 : T?> Expressionable<in S1>.notEqual(value: Expressionable<in S2>): Predicate {
        return Predicates.notEqual(this, value)
    }

    @JvmName("ne1")
    @SinceJdsl("3.0.0")
    infix fun <T> Expressionable<T>.ne(value: T): Predicate {
        return Predicates.notEqual(this, Expressions.value(value))
    }

    @JvmName("ne2")
    @SinceJdsl("3.0.0")
    infix fun <T, S1 : T?, S2 : T?> Expressionable<in S1>.ne(value: Expressionable<in S2>): Predicate {
        return Predicates.notEqual(this, value)
    }

    /**
     * Predicate that tests if the [this] is less than the [value].
     */
    @JvmName("lessThan1")
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>, S : T?> Expressionable<in S>.lessThan(
        value: T,
        inclusive: Boolean = false,
    ): Predicate {
        return if (inclusive) {
            Predicates.lessThanOrEqualTo(this, Expressions.value(value))
        } else {
            Predicates.lessThan(this, Expressions.value(value))
        }
    }

    /**
     * Predicate that tests if the [this] is less than the [value].
     */
    @JvmName("lessThan2")
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>, S : T?> Expressionable<in S>.lessThan(
        value: Expressionable<in S>,
        inclusive: Boolean = false,
    ): Predicate {
        return if (inclusive) {
            Predicates.lessThanOrEqualTo(this, value)
        } else {
            Predicates.lessThan(this, value)
        }
    }

    /**
     * Predicate that tests if the [this] is less than the [value].
     */
    @JvmName("lt1")
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>, S : T?> Expressionable<in S>.lt(
        value: T,
        inclusive: Boolean = false,
    ): Predicate {
        return if (inclusive) {
            Predicates.lessThanOrEqualTo(this, Expressions.value(value))
        } else {
            Predicates.lessThan(this, Expressions.value(value))
        }
    }

    /**
     * Predicate that tests if the [this] is less than the [value].
     */
    @JvmName("lt2")
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>, S : T?> Expressionable<in S>.lt(
        value: Expressionable<in S>,
        inclusive: Boolean = false,
    ): Predicate {
        return if (inclusive) {
            Predicates.lessThanOrEqualTo(this, value)
        } else {
            Predicates.lessThan(this, value)
        }
    }

    /**
     * Predicate that tests if the [this] is less than or equal to the [value].
     */
    @JvmName("lessThanOrEqualTo1")
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>, S : T?> Expressionable<in S>.lessThanOrEqualTo(
        value: T,
    ): Predicate {
        return Predicates.lessThanOrEqualTo(this, Expressions.value(value))
    }

    /**
     * Predicate that tests if the [this] is less than or equal to the [value].
     */
    @JvmName("lessThanOrEqualTo2")
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>, S : T?> Expressionable<in S>.lessThanOrEqualTo(
        value: Expressionable<in S>,
    ): Predicate {
        return Predicates.lessThanOrEqualTo(this, value)
    }

    /**
     * Predicate that tests if the [this] is less than or equal to the [value].
     */
    @JvmName("le1")
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>, S : T?> Expressionable<in S>.le(
        value: T,
    ): Predicate {
        return Predicates.lessThanOrEqualTo(this, Expressions.value(value))
    }

    /**
     * Predicate that tests if the [this] is less than or equal to the [value].
     */
    @JvmName("le2")
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>, S : T?> Expressionable<in S>.le(
        value: Expressionable<in S>,
    ): Predicate {
        return Predicates.lessThanOrEqualTo(this, value)
    }

    /**
     * Predicate that tests if the [this] is greater than the [value].
     */
    @JvmName("greaterThan1")
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>, S : T?> Expressionable<in S>.greaterThan(
        value: T,
        inclusive: Boolean = false,
    ): Predicate {
        return if (inclusive) {
            Predicates.greaterThanOrEqualTo(this, Expressions.value(value))
        } else {
            Predicates.greaterThan(this, Expressions.value(value))
        }
    }

    /**
     * Predicate that tests if the [this] is greater than the [value].
     */
    @JvmName("greaterThan2")
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>, S : T?> Expressionable<in S>.greaterThan(
        value: Expressionable<in S>,
        inclusive: Boolean = false,
    ): Predicate {
        return if (inclusive) {
            Predicates.greaterThanOrEqualTo(this, value)
        } else {
            Predicates.greaterThan(this, value)
        }
    }

    /**
     * Predicate that tests if the [this] is greater than the [value].
     */
    @JvmName("gt1")
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>, S : T?> Expressionable<in S>.gt(
        value: T,
        inclusive: Boolean = false,
    ): Predicate {
        return if (inclusive) {
            Predicates.greaterThanOrEqualTo(this, Expressions.value(value))
        } else {
            Predicates.greaterThan(this, Expressions.value(value))
        }
    }

    /**
     * Predicate that tests if the [this] is greater than the [value].
     */
    @JvmName("gt2")
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>, S : T?> Expressionable<in S>.gt(
        value: Expressionable<in S>,
        inclusive: Boolean = false,
    ): Predicate {
        return if (inclusive) {
            Predicates.greaterThanOrEqualTo(this, value)
        } else {
            Predicates.greaterThan(this, value)
        }
    }

    /**
     * Predicate that tests if the [this] is greater than or equal to the [value].
     */
    @JvmName("greaterThanOrEqualTo1")
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>, S : T?> Expressionable<in S>.greaterThanOrEqualTo(
        value: T,
    ): Predicate {
        return Predicates.greaterThanOrEqualTo(this, Expressions.value(value))
    }

    /**
     * Predicate that tests if the [this] is greater than or equal to the [value].
     */
    @JvmName("greaterThanOrEqualTo2")
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>, S : T?> Expressionable<in S>.greaterThanOrEqualTo(
        value: Expressionable<in S>,
    ): Predicate {
        return Predicates.greaterThanOrEqualTo(this, value)
    }

    /**
     * Predicate that tests if the [this] is greater than or equal to the [value].
     */
    @JvmName("ge1")
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>, S : T?> Expressionable<in S>.ge(
        value: T,
    ): Predicate {
        return Predicates.greaterThanOrEqualTo(this, Expressions.value(value))
    }

    /**
     * Predicate that tests if the [this] is greater than or equal to the [value].
     */
    @JvmName("ge2")
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>, S : T?> Expressionable<in S>.ge(
        value: Expressionable<in S>,
    ): Predicate {
        return Predicates.greaterThanOrEqualTo(this, value)
    }

    @JvmName("between1")
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>, S : T?> Expressionable<in S>.between(
        min: T,
        max: T,
    ): Predicate {
        return Predicates.between(this.toExpression(), Expressions.value(min), Expressions.value(max))
    }

    @JvmName("between2")
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>, S : T?> Expressionable<in S>.between(
        min: Expressionable<in S>,
        max: Expressionable<in S>
    ): Predicate {
        return Predicates.between(this.toExpression(), min.toExpression(), max.toExpression())
    }

    @JvmName("notBetween1")
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>, S : T?> Expressionable<in S>.notBetween(
        min: T,
        max: T,
    ): Predicate {
        return Predicates.notBetween(this.toExpression(), Expressions.value(min), Expressions.value(max))
    }

    @JvmName("notBetween2")
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>, S : T?> Expressionable<in S>.notBetween(
        min: Expressionable<in S>,
        max: Expressionable<in S>,
    ): Predicate {
        return Predicates.notBetween(this.toExpression(), min.toExpression(), max.toExpression())
    }

    @JvmName("in1")
    @SinceJdsl("3.0.0")
    fun <T> Expressionable<T>.`in`(compareValues: Iterable<T>): Predicate {
        return Predicates.`in`(this, compareValues.map { Expressions.value(it) })
    }

    @JvmName("in2")
    @SinceJdsl("3.0.0")
    fun <T> Expressionable<T>.`in`(compareValues: Iterable<Expressionable<T>>): Predicate {
        return Predicates.`in`(this, compareValues)
    }

    @JvmName("in3")
    @SinceJdsl("3.0.0")
    fun <T> Expressionable<T>.`in`(subquery: Subquery<T>): Predicate {
        return Predicates.`in`(this, subquery)
    }

    @JvmName("notIn1")
    @SinceJdsl("3.0.0")
    fun <T> Expressionable<T>.notIn(compareValues: Iterable<T>): Predicate {
        return Predicates.notIn(this, compareValues.map { Expressions.value(it) })
    }

    @JvmName("notIn2")
    @SinceJdsl("3.0.0")
    fun <T> Expressionable<T>.notIn(compareValues: Iterable<Expressionable<T>>): Predicate {
        return Predicates.notIn(this, compareValues.map { it.toExpression() })
    }

    @JvmName("notIn3")
    @SinceJdsl("3.0.0")
    fun <T> Expressionable<T>.notIn(subquery: Subquery<T>): Predicate {
        return Predicates.notIn(this, subquery)
    }

    @JvmName("like1")
    @SinceJdsl("3.0.0")
    fun <T : String?> Expressionable<T>.like(pattern: String): Predicate {
        return Predicates.like(this.toExpression(), Expressions.value(pattern))
    }

    @JvmName("like2")
    @SinceJdsl("3.0.0")
    fun <T : String?> Expressionable<T>.like(pattern: Expressionable<String>): Predicate {
        return Predicates.like(this.toExpression(), pattern)
    }

    @JvmName("notLike1")
    @SinceJdsl("3.0.0")
    fun <T : String?> Expressionable<T>.notLike(pattern: String): Predicate {
        return Predicates.notLike(this.toExpression(), Expressions.value(pattern))
    }

    @JvmName("notLike2")
    @SinceJdsl("3.0.0")
    fun <T : String?> Expressionable<T>.notLike(pattern: Expressionable<String>): Predicate {
        return Predicates.notLike(this.toExpression(), pattern)
    }

    @JvmName("isEmpty1")
    @SinceJdsl("3.0.0")
    fun <T, S : Collection<T>> Path<S>.isEmpty(): Predicate {
        return Predicates.isEmpty(this)
    }

    @JvmName("isNotEmpty1")
    @SinceJdsl("3.0.0")
    fun <T, S : Collection<T>> Path<S>.isNotEmpty(): Predicate {
        return Predicates.isNotEmpty(this)
    }

    @JvmName("exists1")
    @SinceJdsl("3.0.0")
    fun <T> exists(subquery: Subquery<T>): Predicate {
        return Predicates.exists(subquery)
    }

    @JvmName("notExists1")
    @SinceJdsl("3.0.0")
    fun <T> notExists(subquery: Subquery<T>): Predicate {
        return Predicates.notExists(subquery)
    }

    @JvmName("asc1")
    @SinceJdsl("3.0.0")
    fun Expressionable<*>.asc(): Sort {
        return Sorts.asc(this)
    }

    @JvmName("desc1")
    @SinceJdsl("3.0.0")
    fun Expressionable<*>.desc(): Sort {
        return Sorts.desc(this)
    }

    @JvmName("select1")
    @SinceJdsl("3.0.0")
    fun <T> select(
        returnType: KClass<*>,
        expr: Iterable<Expressionable<*>>,
        distinct: Boolean = false,
    ): SelectQueryFromStep<T> {
        return SelectQueryFromStepDsl(returnType, expr.map { it.toExpression() }, distinct)
    }

    @JvmName("select2")
    @SinceJdsl("3.0.0")
    inline fun <reified T> select(
        expr: Expressionable<T>,
        distinct: Boolean = false,
    ): SelectQueryFromStep<T> {
        return select(T::class, listOf(expr.toExpression()), distinct)
    }

    @JvmName("select3")
    @SinceJdsl("3.0.0")
    inline fun <reified T : Any> select(
        vararg expr: Expressionable<*>,
        distinct: Boolean = false,
    ): SelectQueryFromStep<T> {
        return select(T::class, expr.toList(), distinct)
    }

    @JvmName("select4")
    @SinceJdsl("3.0.0")
    inline fun <reified T : Any> select(
        expr: Iterable<Expressionable<*>>,
        distinct: Boolean = false,
    ): SelectQueryFromStep<T> {
        return select(T::class, expr.toList(), distinct)
    }

    @JvmName("selectDistinct1")
    @SinceJdsl("3.0.0")
    fun <T> selectDistinct(
        returnType: KClass<*>,
        expr: Iterable<Expressionable<*>>,
    ): SelectQueryFromStep<T> {
        return SelectQueryFromStepDsl(returnType, expr.map { it.toExpression() }, distinct = true)
    }

    @JvmName("selectDistinct2")
    @SinceJdsl("3.0.0")
    inline fun <reified T> selectDistinct(
        expr: Expressionable<T>,
    ): SelectQueryFromStep<T> {
        return selectDistinct(T::class, listOf(expr.toExpression()))
    }

    @JvmName("selectDistinct3")
    @SinceJdsl("3.0.0")
    inline fun <reified T : Any> selectDistinct(
        vararg expr: Expressionable<*>,
    ): SelectQueryFromStep<T> {
        return selectDistinct(T::class, expr.toList())
    }

    @JvmName("selectDistinct4")
    @SinceJdsl("3.0.0")
    inline fun <reified T : Any> selectDistinct(
        expr: Iterable<Expressionable<*>>,
    ): SelectQueryFromStep<T> {
        return selectDistinct(T::class, expr.toList())
    }

    @JvmName("update1")
    @SinceJdsl("3.0.0")
    fun <T : Any> update(entity: Path<T>): UpdateQuerySetStep<T> {
        return UpdateQuerySetStepDsl(entity)
    }

    @JvmName("deleteFrom1")
    @SinceJdsl("3.0.0")
    fun <T : Any> deleteFrom(entity: Path<T>): DeleteQueryWhereStep<T> {
        return DeleteQueryDsl(entity)
    }
}
