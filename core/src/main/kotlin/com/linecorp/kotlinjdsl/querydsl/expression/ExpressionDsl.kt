package com.linecorp.kotlinjdsl.querydsl.expression

import com.linecorp.kotlinjdsl.query.spec.expression.*
import com.linecorp.kotlinjdsl.query.spec.predicate.PredicateSpec
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1

interface ExpressionDsl {
    @Suppress("UNCHECKED_CAST")
    fun <T : Any, R : T?> entity(entity: KClass<T>) = EntitySpec(entity.java as Class<R>)
    fun <T : Any> entity(entity: KClass<T>, alias: String) = EntitySpec(entity.java, alias)
    fun <T : Any> KClass<T>.alias(alias: String) = EntitySpec(this.java, alias)

    fun <R : Any> literal(value: R) = LiteralSpec(value)
    fun <R> nullLiteral(type: Class<R>) = NullLiteralSpec(type)
    fun <T, R> col(entity: EntitySpec<T>, property: KProperty1<T, R>) = column(entity, property)
    fun <T, R> column(entity: EntitySpec<T>, property: KProperty1<T, R>) = ColumnSpec<R>(entity, property.name)

    fun <N : Number?> max(column: ColumnSpec<N>) = MaxSpec(column)
    fun <N : Number?> min(column: ColumnSpec<N>) = MinSpec(column)
    fun <N : Number?> avg(column: ColumnSpec<N>) = AvgSpec(column)
    fun <N : Number?> sum(column: ColumnSpec<N>) = SumSpec(column)
    fun count(distinct: Boolean, column: ColumnSpec<*>) = CountSpec(distinct, column)
    fun count(column: ColumnSpec<*>) = CountSpec(distinct = false, column)
    fun countDistinct(column: ColumnSpec<*>) = CountSpec(distinct = true, column)
    fun <R : Comparable<R>?> greatest(column: ColumnSpec<R>) = GreatestSpec(column)
    fun <R : Comparable<R>?> least(column: ColumnSpec<R>) = LeastSpec(column)

    fun <T> case(whens: List<CaseSpec.WhenSpec<T>>, `else`: ExpressionSpec<T>) = CaseSpec(whens, `else`)
    fun <T> case(vararg whens: CaseSpec.WhenSpec<T>, `else`: ExpressionSpec<T>) = case(whens.toList(), `else`)

    fun <T> `when`(predicate: PredicateSpec, expression: ExpressionSpec<T>) = CaseSpec.WhenSpec(predicate, expression)
    fun `when`(predicate: PredicateSpec) = WhenDsl(predicate)
    fun `when`(predicate: () -> PredicateSpec) = WhenDsl(predicate())

    fun <T> function(name: String, returnType: Class<T>, vararg expressions: ExpressionSpec<*>) =
        function(name, returnType, expressions.toList())
    fun <T> function(name: String, returnType: Class<T>, expressions: List<ExpressionSpec<*>>) =
        FunctionSpec(name, returnType, expressions)
}
