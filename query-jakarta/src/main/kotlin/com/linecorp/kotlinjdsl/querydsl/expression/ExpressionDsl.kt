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
    fun <T, R> ColumnSpec<T>.nested(property: KProperty1<T, R>): NestedColumnSpec<R> = NestedColumnSpec(
        this,
        property.name
    )

    fun <R : Any> literal(value: R) = LiteralSpec(value)
    fun <R> nullLiteral(type: Class<R>) = NullLiteralSpec(type)
    fun <T, R> col(entity: EntitySpec<T>, property: KProperty1<T, R>) = column(entity, property)
    fun <T, R> column(entity: EntitySpec<T>, property: KProperty1<T, R>) = ColumnSpec<R>(entity, property.name)

    fun <C, R> nestedCol(columnSpec: ColumnSpec<C>, property: KProperty1<C, R>) = NestedColumnSpec<R>(columnSpec, property.name)
    fun <N : Number?> max(expression: ExpressionSpec<N>) = MaxSpec(expression)
    fun <N : Number?> min(expression: ExpressionSpec<N>) = MinSpec(expression)
    fun <N : Number?> avg(expression: ExpressionSpec<N>) = AvgSpec(expression)
    fun <N : Number?> sum(expression: ExpressionSpec<N>) = SumSpec(expression)
    fun count(distinct: Boolean, expression: ExpressionSpec<*>) = CountSpec(distinct, expression)
    fun count(expression: ExpressionSpec<*>) = CountSpec(distinct = false, expression)
    fun countDistinct(expression: ExpressionSpec<*>) = CountSpec(distinct = true, expression)
    fun <R : Comparable<R>?> greatest(expression: ExpressionSpec<R>) = GreatestSpec(expression)
    fun <R : Comparable<R>?> least(expression: ExpressionSpec<R>) = LeastSpec(expression)

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
