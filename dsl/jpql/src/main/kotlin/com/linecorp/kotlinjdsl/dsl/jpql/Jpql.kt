package com.linecorp.kotlinjdsl.dsl.jpql

import com.linecorp.kotlinjdsl.Experimental
import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.dsl.jpql.expression.CaseValueWhenFirstStep
import com.linecorp.kotlinjdsl.dsl.jpql.expression.CaseWhenStep
import com.linecorp.kotlinjdsl.dsl.jpql.select.SelectQueryFromStep
import com.linecorp.kotlinjdsl.querymodel.jpql.*
import com.linecorp.kotlinjdsl.querymodel.jpql.impl.JoinType
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
    fun <T> value(value: T): Expression<T> {
        return JpqlDslSupport.value(value)
    }

    @JvmName("nullValue1")
    fun <T> nullValue(): Expression<T?> {
        return JpqlDslSupport.nullValue()
    }

    @JvmName("param1")
    fun <T> param(name: String): Expression<T> {
        return JpqlDslSupport.param(name)
    }

    @JvmName("param2")
    fun <T> param(name: String, value: T): Expression<T> {
        return JpqlDslSupport.param(name, value)
    }

    @JvmName("entity1")
    fun <T : Any> entity(type: KClass<T>, alias: String = type.simpleName!!): Path<T> {
        return JpqlDslSupport.entity(type, alias)
    }

    @JvmName("path1")
    fun <V> path(property: KProperty1<*, V>): Path<V> {
        return JpqlDslSupport.path(property)
    }

    @JvmName("path2")
    fun <T : Any, V> Path<T>.path(property: KProperty1<T, V>): Path<V> {
        return JpqlDslSupport.path(this, property)
    }

    @JvmName("path3")
    fun <T : Any, V> Path<T?>.path(property: KProperty1<T, V>): Path<V?> {
        return JpqlDslSupport.path(this, property)
    }

    @JvmName("invoke1")
    operator fun <T : Any, V> Path<T>.invoke(property: KProperty1<T, V>): Path<V> {
        return JpqlDslSupport.path(this, property)
    }

    @JvmName("invoke2")
    operator fun <T : Any, V> Path<T?>.invoke(property: KProperty1<T, V>): Path<V?> {
        return JpqlDslSupport.path(this, property)
    }

    @JvmName("as1")
    fun <T> Path<T>.`as`(alias: String): Path<T> {
        return JpqlDslSupport.`as`(this, alias)
    }

    @JvmName("as2")
    fun <T> Expressionable<T>.`as`(alias: String): Expression<T> {
        return JpqlDslSupport.`as`(this, alias)
    }

    @JvmName("treat1")
    fun <PARENT : Any, CHILD : PARENT> Path<PARENT>.treat(type: KClass<CHILD>): Path<CHILD> {
        return JpqlDslSupport.treat(this, type)
    }

    @JvmName("treat2")
    fun <PARENT : Any, CHILD : PARENT> Path<PARENT?>.treat(type: KClass<CHILD>): Path<CHILD?> {
        return JpqlDslSupport.treat(this, type)
    }

    /**
     * Expression that returns a count of the number of non-null values of [expression].
     *
     * If there are no matching rows, it returns 0.
     */
    @JvmName("count1")
    fun count(expression: Expression<*>, distinct: Boolean = false): Expression<Long> {
        return JpqlDslSupport.count(expression, distinct)
    }

    /**
     * Expression that returns a count of the number of non-null values of [expression].
     *
     * If there are no matching rows, it returns 0.
     */
    @JvmName("countDistinct1")
    fun countDistinct(expression: Expression<*>): Expression<Long> {
        return JpqlDslSupport.count(expression, distinct = true)
    }

    /**
     * Expression that returns the maximum value of [expression].
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("max1")
    fun <T : Comparable<T>> max(expression: Expression<T?>, distinct: Boolean = false): Expression<T?> {
        return JpqlDslSupport.max(expression, distinct)
    }

    /**
     * Expression that returns the maximum value of [expression].
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("maxDistinct1")
    fun <T : Comparable<T>> maxDistinct(expression: Expression<T?>): Expression<T?> {
        return JpqlDslSupport.maxDistinct(expression)
    }

    /**
     * Expression that returns the minimum value of [expression].
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("min1")
    fun <T : Comparable<T>> min(expression: Expression<T?>, distinct: Boolean = false): Expression<T?> {
        return JpqlDslSupport.min(expression, distinct)
    }

    /**
     * Expression that returns the minimum value of [expression].
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("minDistinct1")
    fun <T : Comparable<T>> minDistinct(expression: Expression<T?>): Expression<T?> {
        return JpqlDslSupport.minDistinct(expression)
    }

    /**
     * Expression that returns the result for the first value = compareValue comparison that is true.
     * If no comparison is true, the result after ELSE is returned, or NULL if there is no ELSE part.
     */
    @JvmName("case1")
    fun <T> case(expression: Expressionable<T>): CaseValueWhenFirstStep<T> {
        return JpqlDslSupport.case(expression)
    }

    /**
     * Expression that returns the result for the first predicate that is true.
     * If no predicate is true, the result after ELSE is returned, or NULL if there is no ELSE part.
     */
    @JvmName("caseWhen1")
    fun <T> caseWhen(predicate: Predicatable, then: T): CaseWhenStep<T?> {
        return JpqlDslSupport.caseWhen(predicate, then)
    }

    /**
     * Expression that returns the result for the first predicate that is true.
     * If no predicate is true, the result after ELSE is returned, or NULL if there is no ELSE part.
     */
    @JvmName("caseWhen2")
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
     * customExpression("CAST({0} as VARCHAR)", path(User::age))
     * ```
     */
    @JvmName("customExpression1")
    fun <T> customExpression(template: String, vararg args: Expressionable<*>): Expression<T> {
        return JpqlDslSupport.customExpression(template, args.toList())
    }

    @JvmName("join1")
    inline fun <reified T> Path<*>.join(
        path: Path<T>,
        on: Predicate? = null,
        joinType: JoinType = JoinType.INNER,
        fetch: Boolean = false,
    ): Path<T> {
        return JpqlDslSupport.join(this, path, on, joinType, fetch, T::class)
    }

    @JvmName("join2")
    inline fun <reified T> Path<*>.join(
        path: Path<T>,
        on: Predicate? = null,
        fetch: Boolean = false,
    ): Path<T> {
        return JpqlDslSupport.join(this, path, on, JoinType.INNER, fetch, T::class)
    }

    @JvmName("innerJoin1")
    inline fun <reified T> Path<*>.innerJoin(
        path: Path<T>,
        on: Predicate? = null,
        fetch: Boolean = false,
    ): Path<T> {
        return JpqlDslSupport.join(this, path, on, JoinType.INNER, fetch, T::class)
    }

    @JvmName("leftJoin1")
    inline fun <reified T> Path<*>.leftJoin(
        path: Path<T>,
        on: Predicate? = null,
        fetch: Boolean = false,
    ): Path<T> {
        return JpqlDslSupport.join(this, path, on, JoinType.LEFT, fetch, T::class)
    }

    @JvmName("equal1")
    fun <T> Expressionable<T>.equal(value: T): Predicate {
        return JpqlDslSupport.equal(this, value)
    }

    @JvmName("equal2")
    fun <T> Expressionable<T>.equal(expression: Expressionable<T>): Predicate {
        return JpqlDslSupport.equal(this, expression)
    }

    @JvmName("notEqual1")
    fun <T> Expressionable<T>.notEqual(value: T): Predicate {
        return JpqlDslSupport.notEqual(this, value)
    }

    @JvmName("notEqual2")
    fun <T> Expressionable<T>.notEqual(expression: Expressionable<T>): Predicate {
        return JpqlDslSupport.notEqual(this, expression)
    }

    @JvmName("asc1")
    fun Expressionable<*>.asc(): Sort {
        return JpqlDslSupport.asc(this)
    }

    @JvmName("desc1")
    fun Expressionable<*>.desc(): Sort {
        return JpqlDslSupport.desc(this)
    }

    @JvmName("select1")
    fun select(vararg values: Any?, distinct: Boolean = false): SelectQueryFromStep {
        return JpqlDslSupport.select(values.toList(), distinct)
    }

    @JvmName("select2")
    fun select(values: Collection<Any?>, distinct: Boolean = false): SelectQueryFromStep {
        return JpqlDslSupport.select(values.toList(), distinct)
    }

    @JvmName("selectDistinct1")
    fun selectDistinct(vararg values: Any?): SelectQueryFromStep {
        return JpqlDslSupport.select(values.toList(), distinct = true)
    }

    @JvmName("selectDistinct2")
    fun selectDistinct(vararg values: Collection<Any?>): SelectQueryFromStep {
        return JpqlDslSupport.select(values.toList(), distinct = true)
    }
}
