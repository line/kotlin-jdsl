package com.linecorp.kotlinjdsl.dsl.jpql

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
    fun <T> nullValue(): Expression<T> {
        return JpqlDslSupport.nullValue()
    }

    @JvmName("param1")
    fun <T> param(): Expression<T> {
        return JpqlDslSupport.param()
    }

    @JvmName("param2")
    fun <T> param(name: String): Expression<T> {
        return JpqlDslSupport.param(name)
    }

    @JvmName("param3")
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

    @JvmName("case1")
    fun <T> case(expression: Expressionable<T>): CaseValueWhenFirstStep<T> {
        return JpqlDslSupport.case(expression)
    }

    @JvmName("caseWhen1")
    fun <T> caseWhen(predicate: Predicatable, then: T): CaseWhenStep<T?> {
        return JpqlDslSupport.caseWhen(predicate, then)
    }

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
    fun <T> coalesce(expression: Expressionable<in T>, vararg expressions: Expressionable<in T>): Expression<T> {
        return JpqlDslSupport.coalesce(expression, expressions.toList())
    }

    /**
     * Expression that returns null if left = right is true, otherwise returns left.
     *
     * This is the same as ```CASE WHEN left = right THEN NULL ELSE left END. ```
     */
    @JvmName("nullIf1")
    fun <T> nullIf(left: Expressionable<T>, right: Expressionable<T>): Expression<T?> {
        return JpqlDslSupport.nullIf(left, right)
    }

    @JvmName("type1")
    fun <T> type(path: Path<T>): Expression<KClass<T & Any>> {
        return JpqlDslSupport.type(path)
    }

    @JvmName("templateExpression1")
    fun <T> templateExpression(template: String, vararg args: Expressionable<*>): Expression<T> {
        return JpqlDslSupport.templateExpression(template, args.toList())
    }

    @JvmName("join1")
    inline fun <T, reified CLASS : T & Any> Path<*>.join(
        path: Path<T>,
        on: Predicate? = null,
        joinType: JoinType = JoinType.INNER,
        fetch: Boolean = false,
    ): Path<T> {
        return JpqlDslSupport.join(this, path, on, joinType, fetch, CLASS::class)
    }

    @JvmName("join2")
    inline fun <T, reified CLASS : T & Any> Path<*>.join(
        path: Path<T>,
        on: Predicate? = null,
        fetch: Boolean = false,
    ): Path<T> {
        return JpqlDslSupport.join(this, path, on, JoinType.INNER, fetch, CLASS::class)
    }

    @JvmName("innerJoin1")
    inline fun <T, reified CLASS : T & Any> Path<*>.innerJoin(
        path: Path<T>,
        on: Predicate? = null,
        fetch: Boolean = false,
    ): Path<T> {
        return JpqlDslSupport.join(this, path, on, JoinType.INNER, fetch, CLASS::class)
    }

    @JvmName("leftJoin1")
    inline fun <T, reified CLASS : T & Any> Path<*>.leftJoin(
        path: Path<T>,
        on: Predicate? = null,
        fetch: Boolean = false,
    ): Path<T> {
        return JpqlDslSupport.join(this, path, on, JoinType.LEFT, fetch, CLASS::class)
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
