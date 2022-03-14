package com.linecorp.kotlinjdsl.querydsl.expression

import com.linecorp.kotlinjdsl.query.spec.expression.EntitySpec
import com.linecorp.kotlinjdsl.query.spec.expression.ExpressionSpec
import kotlin.reflect.KProperty1

private typealias Dsl = ExpressionDsl

inline fun <reified T, R> Dsl.col(property: KProperty1<T, R>) = column(property)
inline fun <reified T, R> Dsl.column(property: KProperty1<T, R>) = column(EntitySpec(T::class.java), property)
inline fun <reified T> Dsl.nullLiteral() = nullLiteral(T::class.java)

inline fun <reified T, N : Number?> Dsl.max(property: KProperty1<T, N>) = max(col(property))
inline fun <reified T, N : Number?> Dsl.min(property: KProperty1<T, N>) = min(col(property))
inline fun <reified T, N : Number?> Dsl.avg(property: KProperty1<T, N>) = avg(col(property))
inline fun <reified T, N : Number?> Dsl.sum(property: KProperty1<T, N>) = sum(col(property))
inline fun <reified T> Dsl.count(distinct: Boolean, property: KProperty1<T, *>) = count(distinct, col(property))
inline fun <reified T> Dsl.count(property: KProperty1<T, *>) = count(col(property))
inline fun <reified T> Dsl.countDistinct(property: KProperty1<T, *>) = countDistinct(col(property))
inline fun <reified T, R : Comparable<R>?> Dsl.greatest(property: KProperty1<T, R>) = greatest(col(property))
inline fun <reified T, R : Comparable<R>?> Dsl.least(property: KProperty1<T, R>) = least(col(property))
inline fun <reified T> Dsl.function(name: String, vararg expressions: ExpressionSpec<*>) =
    function<T>(name, expressions.toList())

inline fun <reified T> Dsl.function(name: String, expressions: List<ExpressionSpec<*>>) =
    function(name, T::class.java, expressions)
