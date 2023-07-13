package com.linecorp.kotlinjdsl.dsl.jpql

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.dsl.jpql.delete.DeleteQueryWhereStep
import com.linecorp.kotlinjdsl.dsl.jpql.delete.impl.DeleteQueryDsl
import com.linecorp.kotlinjdsl.dsl.jpql.expression.CaseValueWhenFirstStep
import com.linecorp.kotlinjdsl.dsl.jpql.expression.CaseWhenStep
import com.linecorp.kotlinjdsl.dsl.jpql.expression.ExpressionAndExpression
import com.linecorp.kotlinjdsl.dsl.jpql.expression.PathAndExpression
import com.linecorp.kotlinjdsl.dsl.jpql.expression.impl.CaseDsl
import com.linecorp.kotlinjdsl.dsl.jpql.expression.impl.CaseValueWhenFirstStepDsl
import com.linecorp.kotlinjdsl.dsl.jpql.expression.impl.ExpressionAndExpressionImpl
import com.linecorp.kotlinjdsl.dsl.jpql.expression.impl.PathAndExpressionImpl
import com.linecorp.kotlinjdsl.dsl.jpql.select.SelectQueryFromStep
import com.linecorp.kotlinjdsl.dsl.jpql.select.impl.SelectQueryFromStepDsl
import com.linecorp.kotlinjdsl.dsl.jpql.update.UpdateQuerySetStep
import com.linecorp.kotlinjdsl.dsl.jpql.update.impl.UpdateQuerySetStepDsl
import com.linecorp.kotlinjdsl.dsl.owner
import com.linecorp.kotlinjdsl.querymodel.jpql.*
import com.linecorp.kotlinjdsl.querymodel.jpql.impl.*
import java.math.BigDecimal
import java.math.BigInteger
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1

/**
 * Operations for JPQL DSL.
 */
@SinceJdsl("3.0.0")
object JpqlDslSupport {
    @SinceJdsl("3.0.0")
    fun <T> value(value: T): Expression<T> {
        return Value(value)
    }

    @SinceJdsl("3.0.0")
    fun <T> nullValue(): Expression<T?> {
        return Value(null)
    }

    @SinceJdsl("3.0.0")
    fun <T> param(name: String): Expression<T> {
        return Param(name, null)
    }

    @SinceJdsl("3.0.0")
    fun <T> param(name: String, value: T): Expression<T> {
        return Param(name, value)
    }

    @SinceJdsl("3.0.0")
    fun <T> to(first: Path<T>, second: T): PathAndExpression<T> {
        return PathAndExpressionImpl(first, value(second))
    }

    @SinceJdsl("3.0.0")
    fun <T> to(first: Path<T>, second: Expressionable<T>): PathAndExpression<T> {
        return PathAndExpressionImpl(first, second.toExpression())
    }

    @SinceJdsl("3.0.0")
    fun <T> to(first: Expressionable<T>, second: T): ExpressionAndExpression<T> {
        return ExpressionAndExpressionImpl(first.toExpression(), value(second))
    }

    @SinceJdsl("3.0.0")
    fun <T> to(first: Expressionable<T>, second: Expressionable<T>): ExpressionAndExpression<T> {
        return ExpressionAndExpressionImpl(first.toExpression(), second.toExpression())
    }

    @SinceJdsl("3.0.0")
    fun <T : Any> entity(type: KClass<T>, alias: String = type.simpleName!!): Path<T> {
        return AliasedPath(Entity(type), alias)
    }

    @JvmName("path1")
    @SinceJdsl("3.0.0")
    inline fun <reified V> path(property: KProperty1<*, V>): Path<V> {
        val owner = property.owner()

        return Field(V::class, entity(owner), property.name)
    }

    @JvmName("path2")
    @SinceJdsl("3.0.0")
    inline fun <T : Any, reified V> path(path: Path<T>, property: KProperty1<T, V>): Path<V> {
        return Field(V::class, path, property.name)
    }

    @JvmName("path3")
    @SinceJdsl("3.0.0")
    inline fun <T : Any, reified V> path(path: Path<T?>, property: KProperty1<T, V>): Path<V?> {
        return Field(V::class, path, property.name)
    }

    @JvmName("path4")
    @SinceJdsl("3.0.0")
    inline fun <reified T : Any, reified V> path(left: KProperty1<*, T>, right: KProperty1<T, V>): Path<V> {
        return Field(V::class, path(left), right.name)
    }

    @JvmName("path5")
    @SinceJdsl("3.0.0")
    inline fun <reified T : Any, reified V> path(left: KProperty1<*, T?>, right: KProperty1<T, V>): Path<V?> {
        return Field(V::class, path(left), right.name)
    }

    @SinceJdsl("3.0.0")
    fun <T> alias(path: Path<T>): Path<T> {
        return when (path) {
            is AliasedPath, is Join -> path

            else -> AliasedPath(path, path.type.simpleName!!)
        }
    }

    @SinceJdsl("3.0.0")
    fun <T> alias(path: Path<T>, alias: String): Path<T> {
        return when (path) {
            is AliasedPath -> AliasedPath(path.path, alias)

            is Join -> {
                @Suppress("UNCHECKED_CAST")
                Join(
                    left = path.left,
                    right = alias(path.right, alias),
                    on = path.on,
                    joinType = path.joinType,
                    fetch = path.fetch,
                ) as Path<T>
            }

            else -> AliasedPath(path, alias)
        }
    }

    @SinceJdsl("3.0.0")
    fun <T> alias(expression: Expressionable<T>, alias: String): Expression<T> {
        return when (val resolvedExpression = expression.toExpression()) {
            is AliasedExpression -> AliasedExpression(resolvedExpression.expression, alias)

            else -> AliasedExpression(resolvedExpression, alias)
        }
    }

    @JvmName("treat1")
    @SinceJdsl("3.0.0")
    fun <PARENT : Any, CHILD : PARENT> treat(path: Path<PARENT>, type: KClass<CHILD>): Path<CHILD> {
        return Treat(path, type)
    }

    @JvmName("treat2")
    @SinceJdsl("3.0.0")
    fun <PARENT : Any, CHILD : PARENT> treat(path: Path<PARENT?>, type: KClass<CHILD>): Path<CHILD?> {
        return Treat(path, type)
    }

    @SinceJdsl("3.0.0")
    fun count(expression: Expressionable<*>, distinct: Boolean): Expression<Long> {
        return Count(expression.toExpression(), distinct)
    }

    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> max(expression: Expressionable<T?>, distinct: Boolean): Expression<T?> {
        return Max(expression.toExpression(), distinct)
    }

    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> maxDistinct(expression: Expressionable<T?>): Expression<T?> {
        return Max(expression.toExpression(), distinct = true)
    }

    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> min(expression: Expressionable<T?>, distinct: Boolean): Expression<T?> {
        return Min(expression.toExpression(), distinct)
    }

    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> minDistinct(expression: Expressionable<T?>): Expression<T?> {
        return Min(expression.toExpression(), distinct = true)
    }

    @SinceJdsl("3.0.0")
    fun avg(expression: Expressionable<out Number?>, distinct: Boolean): Expression<Double?> {
        return Avg(expression.toExpression(), distinct)
    }

    @SinceJdsl("3.0.0")
    fun avgDistinct(expression: Expressionable<out Number?>): Expression<Double?> {
        return Avg(expression.toExpression(), distinct = true)
    }

    @JvmName("sum1")
    @SinceJdsl("3.0.0")
    fun sum(expression: Expressionable<Int?>, distinct: Boolean): Expression<Long?> {
        return Sum.IntSum(expression.toExpression(), distinct)
    }

    @JvmName("sum2")
    @SinceJdsl("3.0.0")
    fun sum(expression: Expressionable<Long?>, distinct: Boolean): Expression<Long?> {
        return Sum.LongSum(expression.toExpression(), distinct)
    }

    @JvmName("sum3")
    @SinceJdsl("3.0.0")
    fun sum(expression: Expressionable<Float?>, distinct: Boolean): Expression<Double?> {
        return Sum.FloatSum(expression.toExpression(), distinct)
    }

    @JvmName("sum4")
    @SinceJdsl("3.0.0")
    fun sum(expression: Expressionable<Double?>, distinct: Boolean): Expression<Double?> {
        return Sum.DoubleSum(expression.toExpression(), distinct)
    }

    @JvmName("sum5")
    @SinceJdsl("3.0.0")
    fun sum(expression: Expressionable<BigInteger?>, distinct: Boolean): Expression<BigInteger?> {
        return Sum.BigIntegerSum(expression.toExpression(), distinct)
    }

    @JvmName("sum6")
    @SinceJdsl("3.0.0")
    fun sum(expression: Expressionable<BigDecimal?>, distinct: Boolean): Expression<BigDecimal?> {
        return Sum.BigDecimalSum(expression.toExpression(), distinct)
    }

    @JvmName("sumDistinct1")
    @SinceJdsl("3.0.0")
    fun sumDistinct(expression: Expressionable<Int?>): Expression<Long?> {
        return Sum.IntSum(expression.toExpression(), distinct = true)
    }

    @JvmName("sumDistinct2")
    @SinceJdsl("3.0.0")
    fun sumDistinct(expression: Expressionable<Long?>): Expression<Long?> {
        return Sum.LongSum(expression.toExpression(), distinct = true)
    }

    @JvmName("sumDistinct3")
    @SinceJdsl("3.0.0")
    fun sumDistinct(expression: Expressionable<Float?>): Expression<Double?> {
        return Sum.FloatSum(expression.toExpression(), distinct = true)
    }

    @JvmName("sumDistinct4")
    @SinceJdsl("3.0.0")
    fun sumDistinct(expression: Expressionable<Double?>): Expression<Double?> {
        return Sum.DoubleSum(expression.toExpression(), distinct = true)
    }

    @JvmName("sumDistinct5")
    @SinceJdsl("3.0.0")
    fun sumDistinct(expression: Expressionable<BigInteger?>): Expression<BigInteger?> {
        return Sum.BigIntegerSum(expression.toExpression(), distinct = true)
    }

    @JvmName("sumDistinct6")
    @SinceJdsl("3.0.0")
    fun sumDistinct(expression: Expressionable<BigDecimal?>): Expression<BigDecimal?> {
        return Sum.BigDecimalSum(expression.toExpression(), distinct = true)
    }

    @SinceJdsl("3.0.0")
    fun <T : Any> new(type: KClass<T>, args: Collection<Expressionable<*>>): Expression<T> {
        return New(type, args.map { it.toExpression() })
    }

    @SinceJdsl("3.0.0")
    fun <T> case(expression: Expressionable<T>): CaseValueWhenFirstStep<T> {
        return CaseValueWhenFirstStepDsl(expression.toExpression())
    }

    @SinceJdsl("3.0.0")
    fun <T> caseWhen(predicate: Predicatable, then: T): CaseWhenStep<T?> {
        return CaseDsl(predicate.toPredicate(), value(then))
    }

    @SinceJdsl("3.0.0")
    fun <T> caseWhen(predicate: Predicatable, then: Expressionable<T>): CaseWhenStep<T?> {
        @Suppress("UNCHECKED_CAST")
        return CaseDsl(predicate.toPredicate(), then.toExpression() as Expression<T?>)
    }

    @SinceJdsl("3.0.0")
    fun <T> coalesce(expression: Expressionable<in T>, expressions: Collection<Expressionable<in T>>): Expression<T> {
        return Coalesce(listOf(expression.toExpression()) + expressions.map { it.toExpression() })
    }

    @SinceJdsl("3.0.0")
    fun <T> nullIf(left: Expressionable<T>, right: Expressionable<T>): Expression<T?> {
        return NullIf(left.toExpression(), right.toExpression())
    }

    @JvmName("type1")
    @SinceJdsl("3.0.0")
    fun <T : Any, PATH : Path<T>> type(path: PATH): Expression<KClass<T>> {
        return Type(path)
    }

    @JvmName("type2")
    @SinceJdsl("3.0.0")
    fun <T, PATH : Path<T>> type(path: PATH): Expression<KClass<T & Any>?> {
        @Suppress("UNCHECKED_CAST")
        return Type(path) as Expression<KClass<T & Any>?>
    }

    @SinceJdsl("3.0.0")
    fun <T> customExpression(template: String, args: Collection<Expressionable<*>>): Expression<T> {
        return CustomExpression(template, args.map { it.toExpression() })
    }

    @SinceJdsl("3.0.0")
    fun join(
        left: Path<*>,
        right: Path<*>,
        on: Predicate?,
        joinType: JoinType,
        fetch: Boolean,
    ): Path<Any> {
        val aliasedLeft = alias(left)
        val aliasedRight = alias(right)

        return Join(
            left = aliasedLeft,
            right = aliasedRight,
            on = on,
            joinType = joinType,
            fetch = fetch,
        )
    }

    @SinceJdsl("3.0.0")
    fun and(predicates: Collection<Predicatable>): Predicate {
        return And(predicates.map { it.toPredicate() })
    }

    @SinceJdsl("3.0.0")
    fun or(predicates: Collection<Predicatable>): Predicate {
        return Or(predicates.map { it.toPredicate() })
    }

    @SinceJdsl("3.0.0")
    fun <T> equal(left: Expressionable<T>, right: T): Predicate {
        return Equal(left.toExpression(), value(right))
    }

    @SinceJdsl("3.0.0")
    fun <T> equal(left: Expressionable<T>, right: Expressionable<T>): Predicate {
        return Equal(left.toExpression(), right.toExpression())
    }

    @SinceJdsl("3.0.0")
    fun <T> notEqual(left: Expressionable<T>, right: T): Predicate {
        return NotEqual(left.toExpression(), value(right))
    }

    @SinceJdsl("3.0.0")
    fun <T> notEqual(left: Expressionable<T>, right: Expressionable<T>): Predicate {
        return NotEqual(left.toExpression(), right.toExpression())
    }

    @SinceJdsl("3.0.0")
    fun asc(expression: Expressionable<*>): Sort {
        return SortExpression(expression.toExpression(), Sort.Order.ASC)
    }

    @SinceJdsl("3.0.0")
    fun desc(expression: Expressionable<*>): Sort {
        return SortExpression(expression.toExpression(), Sort.Order.DESC)
    }

    @SinceJdsl("3.0.0")
    fun <T> select(
        returnType: KClass<*>,
        expression: Expressionable<T>,
        distinct: Boolean,
    ): SelectQueryFromStep<T> {
        return SelectQueryFromStepDsl(returnType, listOf(expression.toExpression()), distinct)
    }

    @SinceJdsl("3.0.0")
    fun <T> select(
        returnType: KClass<*>,
        expressions: Collection<Expressionable<*>>,
        distinct: Boolean,
    ): SelectQueryFromStep<T> {
        return SelectQueryFromStepDsl(returnType, expressions.map { it.toExpression() }, distinct)
    }

    @SinceJdsl("3.0.0")
    fun <T : Any> update(entity: Path<T>): UpdateQuerySetStep<T> {
        return UpdateQuerySetStepDsl(entity)
    }

    @SinceJdsl("3.0.0")
    fun <T : Any> deleteFrom(entity: Path<T>): DeleteQueryWhereStep<T> {
        return DeleteQueryDsl(entity)
    }
}
