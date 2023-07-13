package com.linecorp.kotlinjdsl.dsl.jpql

import com.linecorp.kotlinjdsl.Experimental
import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.dsl.jpql.expression.CaseValueWhenFirstStep
import com.linecorp.kotlinjdsl.dsl.jpql.expression.CaseWhenStep
import com.linecorp.kotlinjdsl.dsl.jpql.expression.ExpressionAndExpression
import com.linecorp.kotlinjdsl.dsl.jpql.expression.PathAndExpression
import com.linecorp.kotlinjdsl.dsl.jpql.select.SelectQueryFromStep
import com.linecorp.kotlinjdsl.dsl.jpql.update.UpdateQuerySetStep
import com.linecorp.kotlinjdsl.querymodel.jpql.*
import com.linecorp.kotlinjdsl.querymodel.jpql.impl.JoinType
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
        return JpqlDslSupport.value(value)
    }

    @JvmName("nullValue1")
    @SinceJdsl("3.0.0")
    fun <T> nullValue(): Expression<T?> {
        return JpqlDslSupport.nullValue()
    }

    @JvmName("param1")
    @SinceJdsl("3.0.0")
    fun <T> param(name: String): Expression<T> {
        return JpqlDslSupport.param(name)
    }

    @JvmName("param2")
    @SinceJdsl("3.0.0")
    fun <T> param(name: String, value: T): Expression<T> {
        return JpqlDslSupport.param(name, value)
    }

    @JvmName("to1")
    @SinceJdsl("3.0.0")
    infix fun <T> Path<T>.to(value: T): PathAndExpression<T> {
        return JpqlDslSupport.to(this, value)
    }

    @JvmName("to2")
    @SinceJdsl("3.0.0")
    infix fun <T> Path<T>.to(expression: Expressionable<T>): PathAndExpression<T> {
        return JpqlDslSupport.to(this, expression)
    }

    @JvmName("to3")
    @SinceJdsl("3.0.0")
    infix fun <T> Expressionable<T>.to(value: T): ExpressionAndExpression<T> {
        return JpqlDslSupport.to(this, value)
    }

    @JvmName("to4")
    @SinceJdsl("3.0.0")
    infix fun <T> Expressionable<T>.to(expression: Expressionable<T>): ExpressionAndExpression<T> {
        return JpqlDslSupport.to(this, expression)
    }

    @JvmName("entity1")
    @SinceJdsl("3.0.0")
    fun <T : Any> entity(type: KClass<T>, alias: String = type.simpleName!!): Path<T> {
        return JpqlDslSupport.entity(type, alias)
    }

    @JvmName("path1")
    @SinceJdsl("3.0.0")
    inline fun <reified V> path(property: KProperty1<*, V>): Path<V> {
        return JpqlDslSupport.path(property)
    }

    @JvmName("path2")
    @SinceJdsl("3.0.0")
    inline fun <reified T : Any, reified V> Path<T>.path(property: KProperty1<T, V>): Path<V> {
        return JpqlDslSupport.path(this, property)
    }

    @JvmName("path3")
    @SinceJdsl("3.0.0")
    inline fun <reified T : Any, reified V> Path<T?>.path(property: KProperty1<T, V>): Path<V?> {
        return JpqlDslSupport.path(this, property)
    }

    @JvmName("invoke1")
    @SinceJdsl("3.0.0")
    inline operator fun <reified T : Any, reified V> Path<T>.invoke(property: KProperty1<T, V>): Path<V> {
        return JpqlDslSupport.path(this, property)
    }

    @JvmName("invoke2")
    @SinceJdsl("3.0.0")
    inline operator fun <reified T : Any, reified V> Path<T?>.invoke(property: KProperty1<T, V>): Path<V?> {
        return JpqlDslSupport.path(this, property)
    }

    @JvmName("as1")
    @SinceJdsl("3.0.0")
    fun <T> Path<T>.`as`(alias: String): Path<T> {
        return JpqlDslSupport.alias(this, alias)
    }

    @JvmName("as2")
    @SinceJdsl("3.0.0")
    fun <T> Expressionable<T>.`as`(alias: String): Expression<T> {
        return JpqlDslSupport.alias(this, alias)
    }

    @JvmName("alias1")
    @SinceJdsl("3.0.0")
    fun <T> Path<T>.alias(alias: String): Path<T> {
        return JpqlDslSupport.alias(this, alias)
    }

    @JvmName("alias2")
    @SinceJdsl("3.0.0")
    fun <T> Expressionable<T>.alias(alias: String): Expression<T> {
        return JpqlDslSupport.alias(this, alias)
    }

    @JvmName("treat1")
    @SinceJdsl("3.0.0")
    fun <PARENT : Any, CHILD : PARENT> Path<PARENT>.treat(type: KClass<CHILD>): Path<CHILD> {
        return JpqlDslSupport.treat(this, type)
    }

    @JvmName("treat2")
    @SinceJdsl("3.0.0")
    fun <PARENT : Any, CHILD : PARENT> Path<PARENT?>.treat(type: KClass<CHILD>): Path<CHILD?> {
        return JpqlDslSupport.treat(this, type)
    }

    /**
     * Expression that returns a count of the number of non-null values of [expression].
     *
     * If there are no matching rows, it returns 0.
     */
    @JvmName("count1")
    @SinceJdsl("3.0.0")
    fun count(expression: Expressionable<*>, distinct: Boolean = false): Expression<Long> {
        return JpqlDslSupport.count(expression, distinct)
    }

    /**
     * Expression that returns a count of the number of non-null values of [expression].
     *
     * If there are no matching rows, it returns 0.
     */
    @JvmName("countDistinct1")
    @SinceJdsl("3.0.0")
    fun countDistinct(expression: Expressionable<*>): Expression<Long> {
        return JpqlDslSupport.count(expression, distinct = true)
    }

    /**
     * Expression that returns the maximum value of [expression].
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("max1")
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> max(expression: Expressionable<T?>, distinct: Boolean = false): Expression<T?> {
        return JpqlDslSupport.max(expression, distinct)
    }

    /**
     * Expression that returns the maximum value of [expression].
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("maxDistinct1")
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> maxDistinct(expression: Expressionable<T?>): Expression<T?> {
        return JpqlDslSupport.maxDistinct(expression)
    }

    /**
     * Expression that returns the minimum value of [expression].
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("min1")
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> min(expression: Expressionable<T?>, distinct: Boolean = false): Expression<T?> {
        return JpqlDslSupport.min(expression, distinct)
    }

    /**
     * Expression that returns the minimum value of [expression].
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("minDistinct1")
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> minDistinct(expression: Expressionable<T?>): Expression<T?> {
        return JpqlDslSupport.minDistinct(expression)
    }

    /**
     * Expression that returns the average value of [expression].
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("avg1")
    @SinceJdsl("3.0.0")
    fun avg(expression: Expressionable<out Number?>, distinct: Boolean = false): Expression<Double?> {
        return JpqlDslSupport.avg(expression, distinct)
    }

    /**
     * Expression that returns the average value of [expression]
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("avgDistinct1")
    @SinceJdsl("3.0.0")
    fun avgDistinct(expression: Expressionable<out Number?>): Expression<Double?> {
        return JpqlDslSupport.avgDistinct(expression)
    }

    /**
     * Expression that returns the sum of [expression].
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sum1")
    @SinceJdsl("3.0.0")
    fun sum(expression: Expressionable<Int?>, distinct: Boolean = false): Expression<Long?> {
        return JpqlDslSupport.sum(expression, distinct)
    }

    /**
     * Expression that returns the sum of [expression].
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sum2")
    @SinceJdsl("3.0.0")
    fun sum(expression: Expressionable<Long?>, distinct: Boolean = false): Expression<Long?> {
        return JpqlDslSupport.sum(expression, distinct)
    }

    /**
     * Expression that returns the sum of [expression].
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sum3")
    @SinceJdsl("3.0.0")
    fun sum(expression: Expressionable<Float?>, distinct: Boolean = false): Expression<Double?> {
        return JpqlDslSupport.sum(expression, distinct)
    }

    /**
     * Expression that returns the sum of [expression].
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sum4")
    @SinceJdsl("3.0.0")
    fun sum(expression: Expressionable<Double?>, distinct: Boolean = false): Expression<Double?> {
        return JpqlDslSupport.sum(expression, distinct)
    }

    /**
     * Expression that returns the sum of [expression].
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sum5")
    @SinceJdsl("3.0.0")
    fun sum(expression: Expressionable<BigInteger?>, distinct: Boolean = false): Expression<BigInteger?> {
        return JpqlDslSupport.sum(expression, distinct)
    }

    /**
     * Expression that returns the sum of [expression].
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sum6")
    @SinceJdsl("3.0.0")
    fun sum(expression: Expressionable<BigDecimal?>, distinct: Boolean = false): Expression<BigDecimal?> {
        return JpqlDslSupport.sum(expression, distinct)
    }

    /**
     * Expression that returns the sum of [expression].
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sumDistinct1")
    @SinceJdsl("3.0.0")
    fun sumDistinct(expression: Expressionable<Int?>): Expression<Long?> {
        return JpqlDslSupport.sumDistinct(expression)
    }

    /**
     * Expression that returns the sum of [expression].
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sumDistinct2")
    @SinceJdsl("3.0.0")
    fun sumDistinct(expression: Expressionable<Long?>): Expression<Long?> {
        return JpqlDslSupport.sumDistinct(expression)
    }

    /**
     * Expression that returns the sum of [expression].
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sumDistinct3")
    @SinceJdsl("3.0.0")
    fun sumDistinct(expression: Expressionable<Float?>): Expression<Double?> {
        return JpqlDslSupport.sumDistinct(expression)
    }

    /**
     * Expression that returns the sum of [expression].
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sumDistinct4")
    @SinceJdsl("3.0.0")
    fun sumDistinct(expression: Expressionable<Double?>): Expression<Double?> {
        return JpqlDslSupport.sumDistinct(expression)
    }

    /**
     * Expression that returns the sum of [expression].
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sumDistinct5")
    @SinceJdsl("3.0.0")
    fun sumDistinct(expression: Expressionable<BigInteger?>): Expression<BigInteger?> {
        return JpqlDslSupport.sumDistinct(expression)
    }

    /**
     * Expression that returns the sum of [expression].
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sumDistinct6")
    @SinceJdsl("3.0.0")
    fun sumDistinct(expression: Expressionable<BigDecimal?>): Expression<BigDecimal?> {
        return JpqlDslSupport.sumDistinct(expression)
    }

    @JvmName("new1")
    @SinceJdsl("3.0.0")
    fun <T : Any> new(type: KClass<T>, vararg args: Expressionable<*>): Expression<T> {
        return JpqlDslSupport.new(type, args.toList())
    }

    @JvmName("new2")
    @SinceJdsl("3.0.0")
    fun <T : Any> new(type: KClass<T>, args: Collection<Expressionable<*>>): Expression<T> {
        return JpqlDslSupport.new(type, args)
    }

    /**
     * Expression that returns the result for the first value = compareValue comparison that is true.
     * If no comparison is true, the result after ELSE is returned, or NULL if there is no ELSE part.
     */
    @JvmName("case1")
    @SinceJdsl("3.0.0")
    fun <T> case(expression: Expressionable<T>): CaseValueWhenFirstStep<T> {
        return JpqlDslSupport.case(expression)
    }

    /**
     * Expression that returns the result for the first predicate that is true.
     * If no predicate is true, the result after ELSE is returned, or NULL if there is no ELSE part.
     */
    @JvmName("caseWhen1")
    @SinceJdsl("3.0.0")
    fun <T> caseWhen(predicate: Predicatable, then: T): CaseWhenStep<T?> {
        return JpqlDslSupport.caseWhen(predicate, then)
    }

    /**
     * Expression that returns the result for the first predicate that is true.
     * If no predicate is true, the result after ELSE is returned, or NULL if there is no ELSE part.
     */
    @JvmName("caseWhen2")
    @SinceJdsl("3.0.0")
    fun <T> caseWhen(predicate: Predicatable, then: Expressionable<T>): CaseWhenStep<T?> {
        return JpqlDslSupport.caseWhen(predicate, then)
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
    fun <T> coalesce(expression: Expressionable<in T>, vararg expressions: Expressionable<in T>): Expression<T> {
        return JpqlDslSupport.coalesce(expression, expressions.toList())
    }

    /**
     * Expression that returns null if left = right is true, otherwise returns left.
     *
     * This is the same as ```CASE WHEN left = right THEN NULL ELSE left END. ```
     */
    @JvmName("nullIf1")
    @Experimental
    fun <T> nullIf(left: Expressionable<T>, right: Expressionable<T>): Expression<T?> {
        return JpqlDslSupport.nullIf(left, right)
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
    fun <T : Any, PATH : Path<T>> type(path: PATH): Expression<KClass<T>> {
        return JpqlDslSupport.type(path)
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
    fun <T, PATH : Path<T>> type(path: PATH): Expression<KClass<T & Any>?> {
        return JpqlDslSupport.type(path)
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
        return JpqlDslSupport.customExpression(template, args.toList())
    }

    @JvmName("join1")
    fun Path<*>.join(
        path: Path<*>,
        on: Predicate? = null,
        joinType: JoinType = JoinType.INNER,
        fetch: Boolean = false,
    ): Path<Any> {
        return JpqlDslSupport.join(this, path, on, joinType, fetch)
    }

    @JvmName("join2")
    fun Path<*>.join(
        path: Path<*>,
        on: Predicate? = null,
        fetch: Boolean = false,
    ): Path<Any> {
        return JpqlDslSupport.join(this, path, on, JoinType.INNER, fetch)
    }

    @JvmName("innerJoin1")
    fun Path<*>.innerJoin(
        path: Path<*>,
        on: Predicate? = null,
        fetch: Boolean = false,
    ): Path<Any> {
        return JpqlDslSupport.join(this, path, on, JoinType.INNER, fetch)
    }

    @JvmName("leftJoin1")
    fun Path<*>.leftJoin(
        path: Path<*>,
        on: Predicate? = null,
        fetch: Boolean = false,
    ): Path<Any> {
        return JpqlDslSupport.join(this, path, on, JoinType.LEFT, fetch)
    }

    @JvmName("joinFetch1")
    fun Path<*>.joinFetch(
        path: Path<*>,
        on: Predicate? = null,
        joinType: JoinType = JoinType.INNER,
    ): Path<Any> {
        return JpqlDslSupport.join(this, path, on, joinType, fetch = true)
    }

    @JvmName("joinFetch2")
    fun Path<*>.joinFetch(
        path: Path<*>,
        on: Predicate? = null,
    ): Path<Any> {
        return JpqlDslSupport.join(this, path, on, JoinType.INNER, fetch = true)
    }

    @JvmName("innerJoinFetch1")
    fun Path<*>.innerJoinFetch(
        path: Path<*>,
        on: Predicate? = null,
    ): Path<Any> {
        return JpqlDslSupport.join(this, path, on, JoinType.INNER, fetch = true)
    }

    @JvmName("leftJoinFetch1")
    fun Path<*>.leftJoinFetch(
        path: Path<*>,
        on: Predicate? = null,
    ): Path<Any> {
        return JpqlDslSupport.join(this, path, on, JoinType.LEFT, fetch = true)
    }

    @JvmName("fetch1")
    fun Path<*>.fetch(
        path: Path<*>,
        on: Predicate? = null,
        joinType: JoinType = JoinType.INNER,
    ): Path<Any> {
        return JpqlDslSupport.join(this, path, on, joinType, fetch = true)
    }

    @JvmName("fetch2")
    fun Path<*>.fetch(
        path: Path<*>,
        on: Predicate? = null,
    ): Path<Any> {
        return JpqlDslSupport.join(this, path, on, JoinType.INNER, fetch = true)
    }

    @JvmName("innerFetch1")
    fun Path<*>.innerFetch(
        path: Path<*>,
        on: Predicate? = null,
    ): Path<Any> {
        return JpqlDslSupport.join(this, path, on, JoinType.INNER, fetch = true)
    }

    @JvmName("leftFetch1")
    fun Path<*>.leftFetch(
        path: Path<*>,
        on: Predicate? = null,
    ): Path<Any> {
        return JpqlDslSupport.join(this, path, on, JoinType.LEFT, fetch = true)
    }

    @JvmName("and1")
    fun and(vararg predicates: Predicatable): Predicate {
        return JpqlDslSupport.and(predicates.toList())
    }

    @JvmName("and2")
    fun and(predicates: Collection<Predicatable>): Predicate {
        return JpqlDslSupport.and(predicates)
    }

    @JvmName("and3")
    fun Predicatable.and(predicate: Predicatable): Predicate {
        return JpqlDslSupport.and(listOf(this, predicate))
    }

    @JvmName("or1")
    fun or(vararg predicates: Predicatable): Predicate {
        return JpqlDslSupport.or(predicates.toList())
    }

    @JvmName("or2")
    fun or(predicates: Collection<Predicatable>): Predicate {
        return JpqlDslSupport.or(predicates)
    }

    @JvmName("or3")
    fun Predicatable.or(predicate: Predicatable): Predicate {
        return JpqlDslSupport.or(listOf(this, predicate))
    }

    @JvmName("equal1")
    @SinceJdsl("3.0.0")
    fun <T> Expressionable<T>.equal(value: T): Predicate {
        return JpqlDslSupport.equal(this, value)
    }

    @JvmName("equal2")
    @SinceJdsl("3.0.0")
    fun <T> Expressionable<T>.equal(expression: Expressionable<T>): Predicate {
        return JpqlDslSupport.equal(this, expression)
    }

    @JvmName("notEqual1")
    @SinceJdsl("3.0.0")
    fun <T> Expressionable<T>.notEqual(value: T): Predicate {
        return JpqlDslSupport.notEqual(this, value)
    }

    @JvmName("notEqual2")
    @SinceJdsl("3.0.0")
    fun <T> Expressionable<T>.notEqual(expression: Expressionable<T>): Predicate {
        return JpqlDslSupport.notEqual(this, expression)
    }

    @JvmName("asc1")
    @SinceJdsl("3.0.0")
    fun Expressionable<*>.asc(): Sort {
        return JpqlDslSupport.asc(this)
    }

    @JvmName("desc1")
    @SinceJdsl("3.0.0")
    fun Expressionable<*>.desc(): Sort {
        return JpqlDslSupport.desc(this)
    }

    @JvmName("select1")
    @SinceJdsl("3.0.0")
    inline fun <reified T> select(
        expression: Expressionable<T>,
        distinct: Boolean = false,
    ): SelectQueryFromStep<T> {
        return JpqlDslSupport.select(T::class, expression, distinct)
    }

    @JvmName("select2")
    @SinceJdsl("3.0.0")
    inline fun <reified T : Any> select(
        vararg expressions: Expressionable<*>,
        distinct: Boolean = false,
    ): SelectQueryFromStep<T> {
        return JpqlDslSupport.select(T::class, expressions.toList(), distinct)
    }

    @JvmName("select3")
    @SinceJdsl("3.0.0")
    fun select(
        vararg expressions: Expressionable<*>,
        distinct: Boolean = false,
    ): SelectQueryFromStep<Any> {
        return JpqlDslSupport.select(Any::class, expressions.toList(), distinct)
    }

    @JvmName("select4")
    @SinceJdsl("3.0.0")
    inline fun <reified T : Any> select(
        expressions: Collection<Expressionable<*>>,
        distinct: Boolean = false,
    ): SelectQueryFromStep<T> {
        return JpqlDslSupport.select(T::class, expressions.toList(), distinct)
    }

    @JvmName("select5")
    @SinceJdsl("3.0.0")
    fun select(
        expressions: Collection<Expressionable<*>>,
        distinct: Boolean = false,
    ): SelectQueryFromStep<Any> {
        return JpqlDslSupport.select(Any::class, expressions.toList(), distinct)
    }

    @JvmName("selectDistinct1")
    @SinceJdsl("3.0.0")
    inline fun <reified T> selectDistinct(
        expression: Expressionable<T>,
    ): SelectQueryFromStep<T> {
        return JpqlDslSupport.select(T::class, expression, distinct = true)
    }

    @JvmName("selectDistinct2")
    @SinceJdsl("3.0.0")
    inline fun <reified T : Any> selectDistinct(
        vararg expressions: Expressionable<*>,
    ): SelectQueryFromStep<T> {
        return JpqlDslSupport.select(T::class, expressions.toList(), distinct = true)
    }

    @JvmName("selectDistinct3")
    @SinceJdsl("3.0.0")
    fun selectDistinct(
        vararg expressions: Expressionable<*>,
    ): SelectQueryFromStep<Any> {
        return JpqlDslSupport.select(Any::class, expressions.toList(), distinct = true)
    }

    @JvmName("selectDistinct4")
    @SinceJdsl("3.0.0")
    inline fun <reified T : Any> selectDistinct(
        expressions: Collection<Expressionable<*>>,
    ): SelectQueryFromStep<T> {
        return JpqlDslSupport.select(T::class, expressions.toList(), distinct = true)
    }

    @JvmName("selectDistinct5")
    @SinceJdsl("3.0.0")
    fun selectDistinct(
        expressions: Collection<Expressionable<*>>,
    ): SelectQueryFromStep<Any> {
        return JpqlDslSupport.select(Any::class, expressions.toList(), distinct = true)
    }

    @JvmName("update1")
    @SinceJdsl("3.0.0")
    fun <T : Any> update(entity: Path<T>): UpdateQuerySetStep<T> {
        return JpqlDslSupport.update(entity)
    }
}
