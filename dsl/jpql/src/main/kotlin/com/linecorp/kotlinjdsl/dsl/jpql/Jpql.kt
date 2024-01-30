package com.linecorp.kotlinjdsl.dsl.jpql

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.dsl.jpql.delete.DeleteQueryWhereStep
import com.linecorp.kotlinjdsl.dsl.jpql.delete.impl.DeleteQueryDsl
import com.linecorp.kotlinjdsl.dsl.jpql.expression.CaseThenFirstStep
import com.linecorp.kotlinjdsl.dsl.jpql.expression.CaseValueWhenFirstStep
import com.linecorp.kotlinjdsl.dsl.jpql.expression.TrimFromStep
import com.linecorp.kotlinjdsl.dsl.jpql.expression.impl.CaseThenFirstStepDsl
import com.linecorp.kotlinjdsl.dsl.jpql.expression.impl.CaseValueWhenFirstStepDsl
import com.linecorp.kotlinjdsl.dsl.jpql.expression.impl.TrimBothFromStepDsl
import com.linecorp.kotlinjdsl.dsl.jpql.expression.impl.TrimFromStepDsl
import com.linecorp.kotlinjdsl.dsl.jpql.expression.impl.TrimLeadingFromStepDsl
import com.linecorp.kotlinjdsl.dsl.jpql.expression.impl.TrimTrailingFromStepDsl
import com.linecorp.kotlinjdsl.dsl.jpql.join.AssociationJoinOnStep
import com.linecorp.kotlinjdsl.dsl.jpql.join.JoinOnStep
import com.linecorp.kotlinjdsl.dsl.jpql.join.impl.AssociationFetchJoinDsl
import com.linecorp.kotlinjdsl.dsl.jpql.join.impl.AssociationJoinDsl
import com.linecorp.kotlinjdsl.dsl.jpql.join.impl.FetchJoinDsl
import com.linecorp.kotlinjdsl.dsl.jpql.join.impl.JoinDsl
import com.linecorp.kotlinjdsl.dsl.jpql.select.SelectQueryFromStep
import com.linecorp.kotlinjdsl.dsl.jpql.select.impl.SelectQueryFromStepDsl
import com.linecorp.kotlinjdsl.dsl.jpql.sort.SortNullsStep
import com.linecorp.kotlinjdsl.dsl.jpql.sort.impl.SortDsl
import com.linecorp.kotlinjdsl.dsl.jpql.update.UpdateQuerySetFirstStep
import com.linecorp.kotlinjdsl.dsl.jpql.update.impl.UpdateQuerySetStepFirstDsl
import com.linecorp.kotlinjdsl.querymodel.jpql.JpqlQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.JpqlQueryable
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entities
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entity
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entityable
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressionable
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Subquery
import com.linecorp.kotlinjdsl.querymodel.jpql.join.JoinType
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Path
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Pathable
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicatable
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicate
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicates
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.sort.Sort
import java.math.BigDecimal
import java.math.BigInteger
import kotlin.internal.Exact
import kotlin.internal.LowPriorityInOverloadResolution
import kotlin.reflect.KClass
import kotlin.reflect.KFunction1
import kotlin.reflect.KProperty1

/**
 * Builds new JPQL query using newly created JpqlDsl.
 */
@SinceJdsl("3.0.0")
inline fun <Q : JpqlQuery<Q>> jpql(init: Jpql.() -> JpqlQueryable<Q>): Q {
    return Jpql().init().toQuery()
}

/**
 * Builds new JPQL query using provided JpqlDsl.
 */
@SinceJdsl("3.0.0")
inline fun <DSL : JpqlDsl, Q : JpqlQuery<Q>> jpql(dsl: JpqlDsl.Constructor<DSL>, init: DSL.() -> JpqlQueryable<Q>): Q {
    return dsl.newInstance().init().toQuery()
}

/**
 * Default implementation of DSL for building a JPQL query.
 */
@SinceJdsl("3.0.0")
open class Jpql : JpqlDsl {
    companion object Constructor : JpqlDsl.Constructor<Jpql> {
        override fun newInstance(): Jpql = Jpql()
    }

    /**
     * Creates a parameter expression with a generated name and the value.
     */
    @SinceJdsl("3.0.0")
    fun <T> value(value: @Exact T): Expression<T & Any> {
        return Expressions.value(value)
    }

    /**
     * Creates a parameter expression with a generated name and null.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> nullValue(): Expression<T> {
        return Expressions.nullValue()
    }

    /**
     * Creates a literal expression with the int.
     */
    @SinceJdsl("3.0.0")
    fun intLiteral(int: Int): Expression<Int> {
        return Expressions.intLiteral(int)
    }

    /**
     * Creates a literal expression with the long.
     */
    @SinceJdsl("3.0.0")
    fun longLiteral(long: Long): Expression<Long> {
        return Expressions.longLiteral(long)
    }

    /**
     * Creates a literal expression with the float.
     */
    @SinceJdsl("3.0.0")
    fun floatLiteral(float: Float): Expression<Float> {
        return Expressions.floatLiteral(float)
    }

    /**
     * Creates a literal expression with the double.
     */
    @SinceJdsl("3.0.0")
    fun doubleLiteral(double: Double): Expression<Double> {
        return Expressions.doubleLiteral(double)
    }

    /**
     * Creates a literal expression with the boolean.
     */
    @SinceJdsl("3.0.0")
    fun booleanLiteral(boolean: Boolean): Expression<Boolean> {
        return Expressions.booleanLiteral(boolean)
    }

    /**
     * Creates a literal expression with the char.
     * If the char is '(single quote), it is rendered as ''(two single quotes).
     */
    @SinceJdsl("3.0.0")
    fun charLiteral(char: Char): Expression<Char> {
        return Expressions.charLiteral(char)
    }

    /**
     * Creates a literal expression with the string.
     * If the string contains '(single quote), it is rendered as ''(two single quotes).
     * For example: literal''s.
     */
    @SinceJdsl("3.0.0")
    fun stringLiteral(string: String): Expression<String> {
        return Expressions.stringLiteral(string)
    }

    /**
     * Creates a literal expression with the enum.
     */
    @SinceJdsl("3.0.0")
    fun <T : Enum<T>> enumLiteral(enum: T): Expression<T> {
        return Expressions.enumLiteral(enum)
    }

    /**
     * Creates a literal expression with null.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> nullLiteral(): Expression<T> {
        return Expressions.nullLiteral()
    }

    /**
     * Creates a parameter expression with the name.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> param(name: String): Expression<T> {
        return Expressions.param(name)
    }

    /**
     * Creates a parameter expression with the name and value.
     * The value can be overridden in rendering.
     */
    @SinceJdsl("3.0.0")
    fun <T> param(name: String, value: @Exact T): Expression<T & Any> {
        return Expressions.param(name, value)
    }

    /**
     * Creates an entity expression with the type and alias.
     * The entity is identified and referenced by its alias.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> entity(type: KClass<T>, alias: String = type.simpleName!!): Entity<T> {
        return Entities.entity(type, alias)
    }

    /**
     * Creates a path expression with the property.
     * The path starts from the entity which is the owner of the property.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any, V> path(property: KProperty1<T, @Exact V>): Path<V & Any> {
        return Paths.path(property)
    }

    /**
     * Creates a path expression with the property.
     * The path starts from the entity which is the owner of the property.
     */
    @SinceJdsl("3.1.0")
    fun <T : Any, V> path(getter: KFunction1<T, @Exact V>): Path<V & Any> {
        return Paths.path(getter)
    }

    /**
     * Creates a path expression with the entity and property.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any, V> Entityable<T>.path(property: KProperty1<in T, @Exact V>): Path<V & Any> {
        return Paths.path(this.toEntity(), property)
    }

    /**
     * Creates a path expression with the entity and property.
     */
    @SinceJdsl("3.1.0")
    fun <T : Any, V> Entityable<T>.path(getter: KFunction1<T, @Exact V>): Path<V & Any> {
        return Paths.path(this.toEntity(), getter)
    }

    /**
     * Creates a path expression with the path and property.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any, V> Pathable<T>.path(property: KProperty1<in T, @Exact V>): Path<V & Any> {
        return Paths.path(this.toPath(), property)
    }

    /**
     * Creates a path expression with the path and property.
     */
    @SinceJdsl("3.1.0")
    fun <T : Any, V> Pathable<T>.path(getter: KFunction1<T, @Exact V>): Path<V & Any> {
        return Paths.path(this.toPath(), getter)
    }

    /**
     * Creates a path expression with the entity and property.
     */
    @SinceJdsl("3.0.0")
    operator fun <T : Any, V> Entityable<T>.invoke(property: KProperty1<in T, @Exact V>): Path<V & Any> {
        return Paths.path(this.toEntity(), property)
    }

    /**
     * Creates a path expression with the entity and property.
     */
    @SinceJdsl("3.1.0")
    operator fun <T : Any, V> Entityable<T>.invoke(getter: KFunction1<T, @Exact V>): Path<V & Any> {
        return Paths.path(this.toEntity(), getter)
    }

    /**
     * Creates a path expression with the path and property.
     */
    @SinceJdsl("3.0.0")
    operator fun <T : Any, V> Pathable<T>.invoke(property: KProperty1<in T, @Exact V>): Path<V & Any> {
        return Paths.path(this.toPath(), property)
    }

    /**
     * Creates a path expression with the path and property.
     */
    @SinceJdsl("3.1.0")
    operator fun <T : Any, V> Pathable<T>.invoke(getter: KFunction1<T, @Exact V>): Path<V & Any> {
        return Paths.path(this.toPath(), getter)
    }

    /**
     * Creates an aliased expression with the alias expression.
     * The aliased expression can be referenced by the alias expression.
     *
     * @see expression for creating an alias expression.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> Expressionable<@Exact T>.`as`(alias: Expression<T>): Expression<T> {
        return Expressions.alias(this.toExpression(), alias)
    }

    /**
     * Creates an aliased expression with the alias expression.
     * The aliased expression can be referenced by the alias expression.
     *
     * @see expression for creating an alias expression.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> Expressionable<@Exact T>.alias(alias: Expression<T>): Expression<T> {
        return Expressions.alias(this.toExpression(), alias)
    }

    /**
     * Creates an expression to reference.
     * The expression can be used for aliasing and referencing.
     *
     * @see as for aliasing an expression.
     * @see alias for aliasing an expression.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> expression(type: KClass<T>, alias: String): Expression<T> {
        return Expressions.expression(type, alias)
    }

    /**
     * Creates an expression to reference.
     * The expression can be used for aliasing and referencing.
     *
     * @see as for aliasing an expression.
     * @see alias for aliasing an expression.
     */
    @SinceJdsl("3.0.0")
    inline fun <reified T : Any> expression(alias: String): Expression<T> {
        return Expressions.expression(T::class, alias)
    }

    /**
     * Creates an entity with downcasting.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any, S : T> Entityable<T>.treat(type: KClass<S>): Entity<S> {
        return Entities.treat(this.toEntity(), type)
    }

    /**
     * Creates a path with downcasting.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any, S : T> Pathable<T>.treat(type: KClass<S>): Path<S> {
        return Paths.treat(this.toPath(), type)
    }

    /**
     * Creates an expression that represents the plus of values.
     * The values are each enclosed in parentheses.
     *
     * This is the same as ```(value1) + (value2)```.
     */
    @JvmName("plusWithParentheses")
    @SinceJdsl("3.0.0")
    fun <T : Number, S : T?> plus(value1: Expressionable<@Exact T>, value2: S): Expression<T> {
        return Expressions.plus(
            Expressions.parentheses(value1.toExpression()),
            Expressions.parentheses(Expressions.value(value2)),
        )
    }

    /**
     * Creates an expression that represents the plus of values.
     * The values are each enclosed in parentheses.
     *
     * This is the same as ```(value1) + (value2)```.
     */
    @JvmName("plusWithParentheses")
    @SinceJdsl("3.0.0")
    fun <T : Number, S : T> plus(value1: Expressionable<@Exact T>, value2: Expressionable<S>): Expression<T> {
        return Expressions.plus(
            Expressions.parentheses(value1.toExpression()),
            Expressions.parentheses(value2.toExpression()),
        )
    }

    /**
     * Creates an expression that represents the plus of values.
     *
     * This is the same as ```value1 + value2```.
     */
    @SinceJdsl("3.0.0")
    fun <T : Number, S : T?> Expressionable<@Exact T>.plus(value: S): Expression<T> {
        return Expressions.plus(this.toExpression(), Expressions.value(value))
    }

    /**
     * Creates an expression that represents the plus of values.
     *
     * This is the same as ```value1 + value2```.
     */
    @SinceJdsl("3.0.0")
    fun <T : Number, S : T> Expressionable<@Exact T>.plus(value: Expressionable<S>): Expression<T> {
        return Expressions.plus(this.toExpression(), value.toExpression())
    }

    /**
     * Creates an expression that represents the minus of values.
     * The values are each enclosed in parentheses.
     *
     * This is the same as ```(value1) - (value2)```.
     */
    @JvmName("minusWithParentheses")
    @SinceJdsl("3.0.0")
    fun <T : Number, S : T?> minus(value1: Expressionable<@Exact T>, value2: S): Expression<T> {
        return Expressions.minus(
            Expressions.parentheses(value1.toExpression()),
            Expressions.parentheses(Expressions.value(value2)),
        )
    }

    /**
     * Creates an expression that represents the minus of values.
     * The values are each enclosed in parentheses.
     *
     * This is the same as ```(value1) - (value2)```.
     */
    @JvmName("minusWithParentheses")
    @SinceJdsl("3.0.0")
    fun <T : Number, S : T> minus(value1: Expressionable<@Exact T>, value2: Expressionable<S>): Expression<T> {
        return Expressions.minus(
            Expressions.parentheses(value1.toExpression()),
            Expressions.parentheses(value2.toExpression()),
        )
    }

    /**
     * Creates an expression that represents the minus of values.
     *
     * This is the same as ```value1 - value2```.
     */
    @SinceJdsl("3.0.0")
    fun <T : Number, S : T?> Expressionable<@Exact T>.minus(value: S): Expression<T> {
        return Expressions.minus(this.toExpression(), Expressions.value(value))
    }

    /**
     * Creates an expression that represents the minus of values.
     *
     * This is the same as ```value1 - value2```.
     */
    @SinceJdsl("3.0.0")
    fun <T : Number, S : T> Expressionable<@Exact T>.minus(value: Expressionable<S>): Expression<T> {
        return Expressions.minus(this.toExpression(), value.toExpression())
    }

    /**
     * Creates an expression that represents the times of values.
     * The values are each enclosed in parentheses.
     *
     * This is the same as ```(value1) * (value2)```.
     */
    @JvmName("timesWithParentheses")
    @SinceJdsl("3.0.0")
    fun <T : Number, S : T?> times(value1: Expressionable<@Exact T>, value2: S): Expression<T> {
        return Expressions.times(
            Expressions.parentheses(value1.toExpression()),
            Expressions.parentheses(Expressions.value(value2)),
        )
    }

    /**
     * Creates an expression that represents the times of values.
     * The values are each enclosed in parentheses.
     *
     * This is the same as ```(value1) * (value2)```.
     */
    @JvmName("timesWithParentheses")
    @SinceJdsl("3.0.0")
    fun <T : Number, S : T> times(value1: Expressionable<@Exact T>, value2: Expressionable<S>): Expression<T> {
        return Expressions.times(
            Expressions.parentheses(value1.toExpression()),
            Expressions.parentheses(value2.toExpression()),
        )
    }

    /**
     * Creates an expression that represents the times of values.
     *
     * This is the same as ```value1 * value2```.
     */
    @SinceJdsl("3.0.0")
    fun <T : Number, S : T?> Expressionable<@Exact T>.times(value: S): Expression<T> {
        return Expressions.times(this.toExpression(), Expressions.value(value))
    }

    /**
     * Creates an expression that represents the times of values.
     *
     * This is the same as ```value1 * value2```.
     */
    @SinceJdsl("3.0.0")
    fun <T : Number, S : T> Expressionable<@Exact T>.times(value: Expressionable<S>): Expression<T> {
        return Expressions.times(this.toExpression(), value.toExpression())
    }

    /**
     * Creates an expression that represents the divide of values.
     * The values are each enclosed in parentheses.
     *
     * This is the same as ```(value1) / (value2)```.
     */
    @JvmName("divWithParentheses")
    @SinceJdsl("3.0.0")
    fun <T : Number, S : T?> div(value1: Expressionable<@Exact T>, value2: S): Expression<T> {
        return Expressions.div(
            Expressions.parentheses(value1.toExpression()),
            Expressions.parentheses(Expressions.value(value2)),
        )
    }

    /**
     * Creates an expression that represents the divide of values.
     * The values are each enclosed in parentheses.
     *
     * This is the same as ```(value1) / (value2)```.
     */
    @JvmName("divWithParentheses")
    @SinceJdsl("3.0.0")
    fun <T : Number, S : T> div(value1: Expressionable<@Exact T>, value2: Expressionable<S>): Expression<T> {
        return Expressions.div(
            Expressions.parentheses(value1.toExpression()),
            Expressions.parentheses(value2.toExpression()),
        )
    }

    /**
     * Creates an expression that represents the divide of values.
     *
     * This is the same as ```value1 / value2```.
     */
    @SinceJdsl("3.0.0")
    fun <T : Number, S : T?> Expressionable<@Exact T>.div(value: S): Expression<T> {
        return Expressions.div(this.toExpression(), Expressions.value(value))
    }

    /**
     * Creates an expression that represents the divide of values.
     *
     * This is the same as ```value1 / value2```.
     */
    @SinceJdsl("3.0.0")
    fun <T : Number, S : T> Expressionable<@Exact T>.div(value: Expressionable<S>): Expression<T> {
        return Expressions.div(this.toExpression(), value.toExpression())
    }

    /**
     * Creates an expression that represents the absolute value.
     */
    @SinceJdsl("3.4.0")
    fun <T : Any, V : Number> abs(expr: KProperty1<T, @Exact V>): Expression<V> {
        return Expressions.abs(Paths.path(expr))
    }

    /**
     * Creates an expression that represents the absolute value.
     */
    @SinceJdsl("3.4.0")
    fun <T : Number> abs(expr: Expressionable<T>): Expression<T> {
        return Expressions.abs(expr.toExpression())
    }

    /**
     * Creates an expression that is enclosed in ceiling
     */
    @SinceJdsl("3.4.0")
    fun <T : Any, V : Number> ceiling(expr: KProperty1<T, @Exact V>): Expression<V> {
        return Expressions.ceiling(Paths.path(expr))
    }

    /**
     * Creates an expression that is enclosed in ceiling
     */
    @SinceJdsl("3.4.0")
    fun <T : Number> ceiling(value: Expressionable<T>): Expression<T> {
        return Expressions.ceiling(value.toExpression())
    }

    /**
     * Creates an expression that is enclosed in floor
     */
    @SinceJdsl("3.4.0")
    fun <T : Any, V : Number> floor(expr: KProperty1<T, @Exact V>): Expression<V> {
        return Expressions.floor(Paths.path(expr))
    }

    /**
     * Creates an expression that is enclosed in floor
     */
    @SinceJdsl("3.4.0")
    fun <T : Number> floor(value: Expressionable<T>): Expression<T> {
        return Expressions.floor(value.toExpression())
    }

    /**
     * Creates an expression that represents the count of non-null values.
     *
     * If there are no matching rows, it returns 0.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any, V> count(expr: KProperty1<T, @Exact V>): Expression<Long> {
        return Expressions.count(distinct = false, Paths.path(expr))
    }

    /**
     * Creates an expression that represents the count of non-null values.
     *
     * If there are no matching rows, it returns 0.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> count(expr: Expressionable<T>): Expression<Long> {
        return Expressions.count(distinct = false, expr.toExpression())
    }

    /**
     * Creates an expression that represents the count of non-null values.
     *
     * If there are no matching rows, it returns 0.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any, V> countDistinct(expr: KProperty1<T, @Exact V>): Expression<Long> {
        return Expressions.count(distinct = true, Paths.path(expr))
    }

    /**
     * Creates an expression that represents the count of non-null values.
     *
     * If there are no matching rows, it returns 0.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> countDistinct(expr: Expressionable<T>): Expression<Long> {
        return Expressions.count(distinct = true, expr.toExpression())
    }

    /**
     * Creates an expression that represents the maximum value.
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any, V : Comparable<*>?> max(expr: KProperty1<T, @Exact V>): Expression<V & Any> {
        return Expressions.max(distinct = false, Paths.path(expr))
    }

    /**
     * Creates an expression that represents the maximum value.
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<*>> max(expr: Expressionable<@Exact T>): Expression<T> {
        return Expressions.max(distinct = false, expr.toExpression())
    }

    /**
     * Creates an expression that represents the maximum value.
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any, V : Comparable<*>?> maxDistinct(expr: KProperty1<T, @Exact V>): Expression<V & Any> {
        return Expressions.max(distinct = true, Paths.path(expr))
    }

    /**
     * Creates an expression that represents the maximum value.
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<*>> maxDistinct(expr: Expressionable<@Exact T>): Expression<T> {
        return Expressions.max(distinct = true, expr.toExpression())
    }

    /**
     * Creates an expression that represents the minimum value.
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any, V : Comparable<*>?> min(expr: KProperty1<T, @Exact V>): Expression<V & Any> {
        return Expressions.min(distinct = false, Paths.path(expr))
    }

    /**
     * Creates an expression that represents the minimum value.
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<*>> min(expr: Expressionable<@Exact T>): Expression<T> {
        return Expressions.min(distinct = false, expr.toExpression())
    }

    /**
     * Creates an expression that represents the minimum value.
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any, V : Comparable<*>?> minDistinct(expr: KProperty1<T, @Exact V>): Expression<V & Any> {
        return Expressions.min(distinct = true, Paths.path(expr))
    }

    /**
     * Creates an expression that represents the minimum value.
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<*>> minDistinct(expr: Expressionable<@Exact T>): Expression<T> {
        return Expressions.min(distinct = true, expr.toExpression())
    }

    /**
     * Creates an expression that represents the average value.
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any, V : Number?> avg(expr: KProperty1<T, @Exact V>): Expression<Double> {
        return Expressions.avg(distinct = false, Paths.path(expr))
    }

    /**
     * Creates an expression that represents the average value.
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @SinceJdsl("3.0.0")
    fun <T : Number> avg(expr: Expressionable<@Exact T>): Expression<Double> {
        return Expressions.avg(distinct = false, expr.toExpression())
    }

    /**
     * Creates an expression that represents the average value.
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any, V : Number?> avgDistinct(expr: KProperty1<T, @Exact V>): Expression<Double> {
        return Expressions.avg(distinct = true, Paths.path(expr))
    }

    /**
     * Creates an expression that represents the average value.
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @SinceJdsl("3.0.0")
    fun <T : Number> avgDistinct(expr: Expressionable<@Exact T>): Expression<Double> {
        return Expressions.avg(distinct = true, expr.toExpression())
    }

    /**
     * Creates an expression that represents the sum of values.
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sumInt")
    @SinceJdsl("3.0.0")
    fun <T : Any> sum(expr: KProperty1<T, Int?>): Expression<Long> {
        return Expressions.sum(distinct = false, Paths.path(expr))
    }

    /**
     * Creates an expression that represents the sum of values.
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sumLong")
    @SinceJdsl("3.0.0")
    fun <T : Any> sum(expr: KProperty1<T, Long?>): Expression<Long> {
        return Expressions.sum(distinct = false, Paths.path(expr))
    }

    /**
     * Creates an expression that represents the sum of values.
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sumFloat")
    @SinceJdsl("3.0.0")
    fun <T : Any> sum(expr: KProperty1<T, Float?>): Expression<Double> {
        return Expressions.sum(distinct = false, Paths.path(expr))
    }

    /**
     * Creates an expression that represents the sum of values.
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sumDouble")
    @SinceJdsl("3.0.0")
    fun <T : Any> sum(expr: KProperty1<T, Double?>): Expression<Double> {
        return Expressions.sum(distinct = false, Paths.path(expr))
    }

    /**
     * Creates an expression that represents the sum of values.
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sumBigInteger")
    @SinceJdsl("3.0.0")
    fun <T : Any> sum(expr: KProperty1<T, BigInteger?>): Expression<BigInteger> {
        return Expressions.sum(distinct = false, Paths.path(expr))
    }

    /**
     * Creates an expression that represents the sum of values.
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sumBigDecimal")
    @SinceJdsl("3.0.0")
    fun <T : Any> sum(expr: KProperty1<T, BigDecimal?>): Expression<BigDecimal> {
        return Expressions.sum(distinct = false, Paths.path(expr))
    }

    /**
     * Creates an expression that represents the sum of values.
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sumInt")
    @SinceJdsl("3.0.0")
    fun sum(expr: Expressionable<Int>): Expression<Long> {
        return Expressions.sum(distinct = false, expr.toExpression())
    }

    /**
     * Creates an expression that represents the sum of values.
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sumLong")
    @SinceJdsl("3.0.0")
    fun sum(expr: Expressionable<Long>): Expression<Long> {
        return Expressions.sum(distinct = false, expr.toExpression())
    }

    /**
     * Creates an expression that represents the sum of values.
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sumFloat")
    @SinceJdsl("3.0.0")
    fun sum(expr: Expressionable<Float>): Expression<Double> {
        return Expressions.sum(distinct = false, expr.toExpression())
    }

    /**
     * Creates an expression that represents the sum of values.
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sumDouble")
    @SinceJdsl("3.0.0")
    fun sum(expr: Expressionable<Double>): Expression<Double> {
        return Expressions.sum(distinct = false, expr.toExpression())
    }

    /**
     * Creates an expression that represents the sum of values.
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sumBigInteger")
    @SinceJdsl("3.0.0")
    fun sum(expr: Expressionable<BigInteger>): Expression<BigInteger> {
        return Expressions.sum(distinct = false, expr.toExpression())
    }

    /**
     * Creates an expression that represents the sum of values.
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sumBigDecimal")
    @SinceJdsl("3.0.0")
    fun sum(expr: Expressionable<BigDecimal>): Expression<BigDecimal> {
        return Expressions.sum(distinct = false, expr.toExpression())
    }

    /**
     * Creates an expression that represents the sum of values.
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sumDistinctInt")
    @SinceJdsl("3.0.0")
    fun <T : Any> sumDistinct(expr: KProperty1<T, Int?>): Expression<Long> {
        return Expressions.sum(distinct = true, Paths.path(expr))
    }

    /**
     * Creates an expression that represents the sum of values.
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sumDistinctLong")
    @SinceJdsl("3.0.0")
    fun <T : Any> sumDistinct(expr: KProperty1<T, Long?>): Expression<Long> {
        return Expressions.sum(distinct = true, Paths.path(expr))
    }

    /**
     * Creates an expression that represents the sum of values.
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sumDistinctFloat")
    @SinceJdsl("3.0.0")
    fun <T : Any> sumDistinct(expr: KProperty1<T, Float?>): Expression<Double> {
        return Expressions.sum(distinct = true, Paths.path(expr))
    }

    /**
     * Creates an expression that represents the sum of values.
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sumDistinctDouble")
    @SinceJdsl("3.0.0")
    fun <T : Any> sumDistinct(expr: KProperty1<T, Double?>): Expression<Double> {
        return Expressions.sum(distinct = true, Paths.path(expr))
    }

    /**
     * Creates an expression that represents the sum of values.
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sumDistinctBigInteger")
    @SinceJdsl("3.0.0")
    fun <T : Any> sumDistinct(expr: KProperty1<T, BigInteger?>): Expression<BigInteger> {
        return Expressions.sum(distinct = true, Paths.path(expr))
    }

    /**
     * Creates an expression that represents the sum of values.
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sumDistinctBigDecimal")
    @SinceJdsl("3.0.0")
    fun <T : Any> sumDistinct(expr: KProperty1<T, BigDecimal?>): Expression<BigDecimal> {
        return Expressions.sum(distinct = true, Paths.path(expr))
    }

    /**
     * Creates an expression that represents the sum of values.
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sumDistinctInt")
    @SinceJdsl("3.0.0")
    fun sumDistinct(expr: Expressionable<Int>): Expression<Long> {
        return Expressions.sum(distinct = true, expr.toExpression())
    }

    /**
     * Creates an expression that represents the sum of values.
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sumDistinctLong")
    @SinceJdsl("3.0.0")
    fun sumDistinct(expr: Expressionable<Long>): Expression<Long> {
        return Expressions.sum(distinct = true, expr.toExpression())
    }

    /**
     * Creates an expression that represents the sum of values.
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sumDistinctFloat")
    @SinceJdsl("3.0.0")
    fun sumDistinct(expr: Expressionable<Float>): Expression<Double> {
        return Expressions.sum(distinct = true, expr.toExpression())
    }

    /**
     * Creates an expression that represents the sum of values.
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sumDistinctDouble")
    @SinceJdsl("3.0.0")
    fun sumDistinct(expr: Expressionable<Double>): Expression<Double> {
        return Expressions.sum(distinct = true, expr.toExpression())
    }

    /**
     * Creates an expression that represents the sum of values.
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sumDistinctBigInteger")
    @SinceJdsl("3.0.0")
    fun sumDistinct(expr: Expressionable<BigInteger>): Expression<BigInteger> {
        return Expressions.sum(distinct = true, expr.toExpression())
    }

    /**
     * Creates an expression that represents the sum of values.
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sumDistinctBigDecimal")
    @SinceJdsl("3.0.0")
    fun sumDistinct(expr: Expressionable<BigDecimal>): Expression<BigDecimal> {
        return Expressions.sum(distinct = true, expr.toExpression())
    }

    /**
     * Creates an expression that represents the DTO projection.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> new(type: KClass<T>, vararg args: Any): Expression<T> {
        return Expressions.new(type, args.map { Expressions.value(it) })
    }

    /**
     * Creates an expression that represents the DTO projection.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> new(type: KClass<T>, vararg args: Expressionable<*>): Expression<T> {
        return Expressions.new(type, args.map { it.toExpression() })
    }

    /**
     * Creates an expression that represents the result for the first predicate that is true.
     * If no predicate is true, the result after `ELSE` is returned, or null if there is no `ELSE`.
     */
    @SinceJdsl("3.0.0")
    fun caseWhen(predicate: Predicatable): CaseThenFirstStep {
        return CaseThenFirstStepDsl(predicate.toPredicate())
    }

    /**
     * Creates an expression that represents the result for the first value = compareValue comparison that is true.
     * If no comparison is true, the result after `ELSE` is returned, or null if there is no `ELSE` part.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> caseValue(value: Pathable<T>): CaseValueWhenFirstStep<T> {
        return CaseValueWhenFirstStepDsl(value.toPath())
    }

    /**
     * Creates an expression that represents the first non-null value in the values,
     * or null if there are no non-null value.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any, S : T?> coalesce(
        value: Expressionable<@Exact T>,
        alternate: S,
        vararg others: S,
    ): Expression<T> {
        return Expressions.coalesce(
            value = value.toExpression(),
            alternate = Expressions.value(alternate),
            others = others.map { Expressions.value(it) },
        )
    }

    /**
     * Creates an expression that represents the first non-null value in the values,
     * or null if there are no non-null value.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any, S : T> coalesce(
        value: Expressionable<@Exact T>,
        alternate: Expressionable<S>,
        vararg others: Expressionable<S>,
    ): Expression<T> {
        return Expressions.coalesce(
            value = value.toExpression(),
            alternate = alternate.toExpression(),
            others = others.map { it.toExpression() },
        )
    }

    /**
     * Creates an expression that represents null if left = right is true, otherwise represents left.
     *
     * This is the same as ```CASE WHEN left = right THEN NULL ELSE left END```.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any, S : T?> nullIf(value: Expressionable<@Exact T>, compareValue: S): Expression<T> {
        return Expressions.nullIf(value.toExpression(), Expressions.value(compareValue))
    }

    /**
     * Creates an expression that represents null if left = right is true, otherwise represents left.
     *
     * This is the same as ```CASE WHEN left = right THEN NULL ELSE left END```.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> nullIf(value: Expressionable<@Exact T>, compareValue: Expressionable<T>): Expression<T> {
        return Expressions.nullIf(value.toExpression(), compareValue.toExpression())
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
    fun type(entity: Entityable<*>): Expression<KClass<*>> {
        return Expressions.type(entity.toEntity())
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
    fun type(path: Pathable<*>): Expression<KClass<*>> {
        return Expressions.type(path.toPath())
    }

    /**
     * Creates an expression that represents the concatenation of two or more string values.
     */
    @SinceJdsl("3.2.0")
    fun concat(
        value1: String?,
        value2: String?,
        vararg others: String?,
    ): Expression<String> {
        return Expressions.concat(
            Expressions.value(value1),
            Expressions.value(value2),
            others.map { Expressions.value(it) },
        )
    }

    /**
     * Creates an expression that represents the concatenation of two or more string values.
     */
    @SinceJdsl("3.2.0")
    fun concat(
        value1: Expressionable<String>,
        value2: Expressionable<String>,
        vararg others: Expressionable<String>,
    ): Expression<String> {
        return Expressions.concat(
            value1.toExpression(),
            value2.toExpression(),
            others.map { it.toExpression() },
        )
    }

    /**
     * Creates an expression that represents a substring of the specified length from the start position of the string.
     * If the length is not specified, it is returned from the start position of the string to the end of the string.
     * The first position of a string is 1.
     */
    @SinceJdsl("3.2.0")
    fun substring(
        value: String,
        start: Int,
        length: Int? = null,
    ): Expression<String> {
        return Expressions.substring(
            Expressions.value(value),
            Expressions.value(start),
            length?.let { Expressions.value(length) },
        )
    }

    /**
     * Creates an expression that represents a substring of the specified length from the start position of the string.
     * If the length is not specified, it is returned from the start position of the string to the end of the string.
     * The first position of a string is 1.
     */
    @SinceJdsl("3.2.0")
    fun substring(
        value: Expressionable<String>,
        start: Int,
        length: Int? = null,
    ): Expression<String> {
        return Expressions.substring(
            value.toExpression(),
            Expressions.value(start),
            length?.let { Expressions.value(length) },
        )
    }

    /**
     * Creates an expression that represents a substring of the specified length from the start position of the string.
     * If the length is not specified, it is returned from the start position of the string to the end of the string.
     * The first position of a string is 1.
     */
    @SinceJdsl("3.2.0")
    fun substring(
        value: Expressionable<String>,
        start: Expressionable<Int>,
        length: Expressionable<Int>? = null,
    ): Expression<String> {
        return Expressions.substring(
            value.toExpression(),
            start.toExpression(),
            length?.toExpression(),
        )
    }

    /**
     * Creates an expression that represents a string with the whitespaces all trimmed
     * from the both sides of the string.
     */
    @SinceJdsl("3.1.0")
    fun trim(value: String): Expression<String> {
        return Expressions.trim(value = Expressions.value(value))
    }

    /**
     * Creates an expression that represents a string with the whitespaces all trimmed
     * from the both sides of the string.
     */
    @SinceJdsl("3.1.0")
    fun trim(value: Expressionable<String>): Expression<String> {
        return Expressions.trim(value = value.toExpression())
    }

    /**
     * Creates an expression that represents a string with the specified characters all trimmed
     * from the both sides of the string.
     * If the character is not specified, it will be assumed to be whitespace.
     */
    @LowPriorityInOverloadResolution
    @SinceJdsl("3.1.0")
    fun trim(character: Char? = null): TrimFromStep {
        return TrimFromStepDsl(character?.let { Expressions.value(it) })
    }

    /**
     * Creates an expression that represents a string with the specified characters all trimmed
     * from the both sides of the string.
     * If the character is not specified, it will be assumed to be whitespace.
     */
    @SinceJdsl("3.1.0")
    fun trim(character: Expressionable<Char>? = null): TrimFromStep {
        return TrimFromStepDsl(character?.toExpression())
    }

    /**
     * Creates an expression that represents a string with the specified characters all trimmed
     * from the leading side of the string.
     * If the character is not specified, it will be assumed to be whitespace.
     */
    @LowPriorityInOverloadResolution
    @SinceJdsl("3.1.0")
    fun trimLeading(character: Char? = null): TrimFromStep {
        return TrimLeadingFromStepDsl(character?.let { Expressions.value(it) })
    }

    /**
     * Creates an expression that represents a string with the specified characters all trimmed
     * from the leading side of the string.
     * If the character is not specified, it will be assumed to be whitespace.
     */
    @SinceJdsl("3.1.0")
    fun trimLeading(character: Expressionable<Char>? = null): TrimFromStep {
        return TrimLeadingFromStepDsl(character?.toExpression())
    }

    /**
     * Creates an expression that represents a string with the specified characters all trimmed
     * from the trailing side of the string.
     * If the character is not specified, it will be assumed to be whitespace.
     */
    @LowPriorityInOverloadResolution
    @SinceJdsl("3.1.0")
    fun trimTrailing(character: Char? = null): TrimFromStep {
        return TrimTrailingFromStepDsl(character?.let { Expressions.value(it) })
    }

    /**
     * Creates an expression that represents a string with the specified characters all trimmed
     * from the trailing side of the string.
     * If the character is not specified, it will be assumed to be whitespace.
     */
    @SinceJdsl("3.1.0")
    fun trimTrailing(character: Expressionable<Char>? = null): TrimFromStep {
        return TrimTrailingFromStepDsl(character?.toExpression())
    }

    /**
     * Creates an expression that represents a string with the specified characters all trimmed
     * from the both sides of the string.
     * If the character is not specified, it will be assumed to be whitespace.
     */
    @LowPriorityInOverloadResolution
    @SinceJdsl("3.1.0")
    fun trimBoth(character: Char? = null): TrimFromStep {
        return TrimBothFromStepDsl(character?.let { Expressions.value(it) })
    }

    /**
     * Creates an expression that represents a string with the specified characters all trimmed
     * from the both sides of the string.
     * If the character is not specified, it will be assumed to be whitespace.
     */
    @SinceJdsl("3.1.0")
    fun trimBoth(character: Expressionable<Char>? = null): TrimFromStep {
        return TrimBothFromStepDsl(character?.toExpression())
    }

    /**
     * Creates an expression that represents the string in uppercase.
     */
    @SinceJdsl("3.0.0")
    fun upper(value: String): Expression<String> {
        return Expressions.upper(Expressions.value(value))
    }

    /**
     * Creates an expression that represents the string in uppercase.
     */
    @SinceJdsl("3.0.0")
    fun upper(value: Expressionable<String>): Expression<String> {
        return Expressions.upper(value.toExpression())
    }

    /**
     * Creates an expression that represents the string in lowercase.
     */
    @SinceJdsl("3.0.0")
    fun lower(value: String): Expression<String> {
        return Expressions.lower(Expressions.value(value))
    }

    /**
     * Creates an expression that represents the string in lowercase.
     */
    @SinceJdsl("3.0.0")
    fun lower(value: Expressionable<String>): Expression<String> {
        return Expressions.lower(value.toExpression())
    }

    /**
     * Creates an expression that represents the length of the string as an integer.
     */
    @SinceJdsl("3.0.0")
    fun length(value: String): Expression<Int> {
        return Expressions.length(Expressions.value(value))
    }

    /**
     * Creates an expression that represents the length of the string as an integer.
     */
    @SinceJdsl("3.0.0")
    fun length(value: Expressionable<String>): Expression<Int> {
        return Expressions.length(value.toExpression())
    }

    /**
     * Creates an expression that represents the position of the first occurrence of a substring in a string.
     * If the substring is not found, returns 0.
     * The position starts with 1.
     */
    @SinceJdsl("3.0.0")
    fun locate(
        substring: String,
        string: String,
        start: Int? = null,
    ): Expression<Int> {
        return Expressions.locate(
            Expressions.value(substring),
            Expressions.value(string),
            start?.let { Expressions.value(it) },
        )
    }

    /**
     * Creates an expression that represents the position of the first occurrence of a substring in a string.
     * If the substring is not found, returns 0.
     * The position starts with 1.
     */
    @SinceJdsl("3.0.0")
    fun locate(
        substring: String,
        string: Expressionable<String>,
        start: Int? = null,
    ): Expression<Int> {
        return Expressions.locate(
            Expressions.value(substring),
            string.toExpression(),
            start?.let { Expressions.value(it) },
        )
    }

    /**
     * Creates an expression that represents the position of the first occurrence of a substring in a string.
     * If the substring is not found, returns 0.
     * The position starts with 1.
     */
    @SinceJdsl("3.0.0")
    fun locate(
        substring: Expressionable<String>,
        string: Expressionable<String>,
        start: Expressionable<Int>? = null,
    ): Expression<Int> {
        return Expressions.locate(
            substring.toExpression(),
            string.toExpression(),
            start?.toExpression(),
        )
    }

    /**
     * Creates an expression that represents predefined database functions and user-defined database functions.
     */
    @LowPriorityInOverloadResolution
    @SinceJdsl("3.0.0")
    fun <T : Any> function(type: KClass<T>, name: String, vararg args: Any): Expression<T> {
        return Expressions.function(type, name, args.map { Expressions.value(it) })
    }

    /**
     * Creates an expression that represents predefined database functions and user-defined database functions.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> function(type: KClass<T>, name: String, vararg args: Expressionable<*>): Expression<T> {
        return Expressions.function(type, name, args.map { it.toExpression() })
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
     * customExpression(String::class, "CAST({0} AS VARCHAR)", 100)
     * ```
     */
    @LowPriorityInOverloadResolution
    @SinceJdsl("3.0.0")
    fun <T : Any> customExpression(type: KClass<T>, template: String, vararg args: Any): Expression<T> {
        return Expressions.customExpression(type, template, args.map { Expressions.value(it) })
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
     * customExpression(String::class, "CAST({0} AS VARCHAR)", path(User::age))
     * ```
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> customExpression(type: KClass<T>, template: String, vararg args: Expressionable<*>): Expression<T> {
        return Expressions.customExpression(type, template, args.map { it.toExpression() })
    }

    /**
     * Creates a subquery with the select query.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> JpqlQueryable<SelectQuery<T>>.asSubquery(): Subquery<T> {
        return Expressions.subquery(this.toQuery())
    }

    /**
     * Creates a derived entity with the select query.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> JpqlQueryable<SelectQuery<T>>.asEntity(): Entity<T> {
        return Entities.derivedEntity(this.toQuery())
    }

    /**
     * Creates a derived entity with the select query and alias.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> JpqlQueryable<SelectQuery<T>>.asEntity(alias: String): Entity<T> {
        return Entities.derivedEntity(this.toQuery(), alias)
    }

    /**
     * Creates a join with the entity specified by the type.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> join(
        type: KClass<T>,
    ): JoinOnStep<T> {
        return JoinDsl(Entities.entity(type), JoinType.INNER)
    }

    /**
     * Creates a join with the entity.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> join(
        entity: Entityable<T>,
    ): JoinOnStep<T> {
        return JoinDsl(entity.toEntity(), JoinType.INNER)
    }

    /**
     * Creates an association join with the path specified by the property.
     */
    @SinceJdsl("3.0.0")
    inline fun <T : Any, reified V> join(
        property: KProperty1<T, @Exact V>,
    ): AssociationJoinOnStep<V & Any> {
        @Suppress("UNCHECKED_CAST")
        val entity = Entities.entity(V::class as KClass<V & Any>)

        return AssociationJoinDsl(entity, Paths.path(property), JoinType.INNER)
    }

    /**
     * Creates an association join with the path specified by the property.
     */
    @JvmName("joinCollection")
    @SinceJdsl("3.0.0")
    inline fun <T : Any, reified V, S : Collection<V>> join(
        property: KProperty1<T, @Exact S>,
    ): AssociationJoinOnStep<V & Any> {
        @Suppress("UNCHECKED_CAST")
        val entity = Entities.entity(V::class as KClass<V & Any>)

        return AssociationJoinDsl(entity, Paths.path(property), JoinType.INNER)
    }

    /**
     * Creates an association join with the path.
     */
    @SinceJdsl("3.0.0")
    inline fun <reified T : Any> join(
        path: Pathable<T>,
    ): AssociationJoinOnStep<T> {
        return AssociationJoinDsl(Entities.entity(T::class), path.toPath(), JoinType.INNER)
    }

    /**
     * Creates an association join with the path.
     */
    @JvmName("joinCollection")
    @SinceJdsl("3.0.0")
    inline fun <reified T : Any, S : Collection<T>> join(
        path: Pathable<S>,
    ): AssociationJoinOnStep<T> {
        return AssociationJoinDsl(Entities.entity(T::class), path.toPath(), JoinType.INNER)
    }

    /**
     * Creates a join with the entity specified by the type.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> innerJoin(
        type: KClass<T>,
    ): JoinOnStep<T> {
        return JoinDsl(Entities.entity(type), JoinType.INNER)
    }

    /**
     * Creates a join with the entity.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> innerJoin(
        entity: Entityable<T>,
    ): JoinOnStep<T> {
        return JoinDsl(entity.toEntity(), JoinType.INNER)
    }

    /**
     * Creates an association join with the path specified by the property.
     */
    @SinceJdsl("3.0.0")
    inline fun <T : Any, reified V> innerJoin(
        property: KProperty1<T, @Exact V>,
    ): AssociationJoinOnStep<V & Any> {
        @Suppress("UNCHECKED_CAST")
        val entity = Entities.entity(V::class as KClass<V & Any>)

        return AssociationJoinDsl(entity, Paths.path(property), JoinType.INNER)
    }

    /**
     * Creates an association join with the path specified by the property.
     */
    @JvmName("innerJoinCollection")
    @SinceJdsl("3.0.0")
    inline fun <T : Any, reified V, S : Collection<V>> innerJoin(
        property: KProperty1<T, @Exact S>,
    ): AssociationJoinOnStep<V & Any> {
        @Suppress("UNCHECKED_CAST")
        val entity = Entities.entity(V::class as KClass<V & Any>)

        return AssociationJoinDsl(entity, Paths.path(property), JoinType.INNER)
    }

    /**
     * Creates an association join with the path.
     */
    @SinceJdsl("3.0.0")
    inline fun <reified T : Any> innerJoin(
        path: Pathable<T>,
    ): AssociationJoinOnStep<T> {
        return AssociationJoinDsl(Entities.entity(T::class), path.toPath(), JoinType.INNER)
    }

    /**
     * Creates an association join with the path.
     */
    @JvmName("innerJoinCollection")
    @SinceJdsl("3.0.0")
    inline fun <reified T : Any, S : Collection<T>> innerJoin(
        path: Pathable<S>,
    ): AssociationJoinOnStep<T> {
        return AssociationJoinDsl(Entities.entity(T::class), path.toPath(), JoinType.INNER)
    }

    /**
     * Creates a join with the entity specified by the type.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> leftJoin(
        type: KClass<T>,
    ): JoinOnStep<T> {
        return JoinDsl(Entities.entity(type), JoinType.LEFT)
    }

    /**
     * Creates a join with the entity.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> leftJoin(
        entity: Entityable<T>,
    ): JoinOnStep<T> {
        return JoinDsl(entity.toEntity(), JoinType.LEFT)
    }

    /**
     * Creates an association join with the path specified by the property.
     */
    @SinceJdsl("3.0.0")
    inline fun <T : Any, reified V> leftJoin(
        property: KProperty1<T, @Exact V>,
    ): AssociationJoinOnStep<V & Any> {
        @Suppress("UNCHECKED_CAST")
        val entity = Entities.entity(V::class as KClass<V & Any>)

        return AssociationJoinDsl(entity, Paths.path(property), JoinType.LEFT)
    }

    /**
     * Creates an association join with the path specified by the property.
     */
    @JvmName("leftJoinCollection")
    @SinceJdsl("3.0.0")
    inline fun <T : Any, reified V, S : Collection<V>> leftJoin(
        property: KProperty1<T, @Exact S>,
    ): AssociationJoinOnStep<V & Any> {
        @Suppress("UNCHECKED_CAST")
        val entity = Entities.entity(V::class as KClass<V & Any>)

        return AssociationJoinDsl(entity, Paths.path(property), JoinType.LEFT)
    }

    /**
     * Creates an association join with the path.
     */
    @SinceJdsl("3.0.0")
    inline fun <reified T : Any> leftJoin(
        path: Pathable<T>,
    ): AssociationJoinOnStep<T> {
        return AssociationJoinDsl(Entities.entity(T::class), path.toPath(), JoinType.LEFT)
    }

    /**
     * Creates an association join with the path.
     */
    @JvmName("leftJoinCollection")
    @SinceJdsl("3.0.0")
    inline fun <reified T : Any, S : Collection<T>> leftJoin(
        path: Pathable<S>,
    ): AssociationJoinOnStep<T> {
        return AssociationJoinDsl(Entities.entity(T::class), path.toPath(), JoinType.LEFT)
    }

    /**
     * Creates a fetch join with the entity specified by the type.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> fetchJoin(
        type: KClass<T>,
    ): JoinOnStep<T> {
        return FetchJoinDsl(Entities.entity(type), JoinType.INNER)
    }

    /**
     * Creates a fetch join with the entity.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> fetchJoin(
        entity: Entityable<T>,
    ): JoinOnStep<T> {
        return FetchJoinDsl(entity.toEntity(), JoinType.INNER)
    }

    /**
     * Creates an association fetch join with the path specified by the property.
     */
    @SinceJdsl("3.0.0")
    inline fun <T : Any, reified V> fetchJoin(
        property: KProperty1<T, @Exact V>,
    ): AssociationJoinOnStep<V & Any> {
        @Suppress("UNCHECKED_CAST")
        val entity = Entities.entity(V::class as KClass<V & Any>)

        return AssociationFetchJoinDsl(entity, Paths.path(property), JoinType.INNER)
    }

    /**
     * Creates an association fetch join with the path specified by the property.
     */
    @JvmName("fetchJoinCollection")
    @SinceJdsl("3.0.0")
    inline fun <T : Any, reified V, S : Collection<V>> fetchJoin(
        property: KProperty1<T, @Exact S>,
    ): AssociationJoinOnStep<V & Any> {
        @Suppress("UNCHECKED_CAST")
        val entity = Entities.entity(V::class as KClass<V & Any>)

        return AssociationFetchJoinDsl(entity, Paths.path(property), JoinType.INNER)
    }

    /**
     * Creates an association fetch join with the path.
     */
    @SinceJdsl("3.0.0")
    inline fun <reified T : Any> fetchJoin(
        path: Pathable<T>,
    ): AssociationJoinOnStep<T> {
        return AssociationFetchJoinDsl(Entities.entity(T::class), path.toPath(), JoinType.INNER)
    }

    /**
     * Creates an association fetch join with the path.
     */
    @JvmName("fetchJoinCollection")
    @SinceJdsl("3.0.0")
    inline fun <reified T : Any, S : Collection<T>> fetchJoin(
        path: Pathable<S>,
    ): AssociationJoinOnStep<T> {
        return AssociationFetchJoinDsl(Entities.entity(T::class), path.toPath(), JoinType.INNER)
    }

    /**
     * Creates a fetch join with the entity specified by the type.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> innerFetchJoin(
        type: KClass<T>,
    ): JoinOnStep<T> {
        return FetchJoinDsl(Entities.entity(type), JoinType.INNER)
    }

    /**
     * Creates a fetch join with the entity.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> innerFetchJoin(
        entity: Entityable<T>,
    ): JoinOnStep<T> {
        return FetchJoinDsl(entity.toEntity(), JoinType.INNER)
    }

    /**
     * Creates an association fetch join with the path specified by the property.
     */
    @SinceJdsl("3.0.0")
    inline fun <T : Any, reified V> innerFetchJoin(
        property: KProperty1<T, @Exact V>,
    ): AssociationJoinOnStep<V & Any> {
        @Suppress("UNCHECKED_CAST")
        val entity = Entities.entity(V::class as KClass<V & Any>)

        return AssociationFetchJoinDsl(entity, Paths.path(property), JoinType.INNER)
    }

    /**
     * Creates an association fetch join with the path specified by the property.
     */
    @JvmName("innerFetchJoinCollection")
    @SinceJdsl("3.0.0")
    inline fun <T : Any, reified V, S : Collection<V>> innerFetchJoin(
        property: KProperty1<T, @Exact S>,
    ): AssociationJoinOnStep<V & Any> {
        @Suppress("UNCHECKED_CAST")
        val entity = Entities.entity(V::class as KClass<V & Any>)

        return AssociationFetchJoinDsl(entity, Paths.path(property), JoinType.INNER)
    }

    /**
     * Creates an association fetch join with the path.
     */
    @SinceJdsl("3.0.0")
    inline fun <reified T : Any> innerFetchJoin(
        path: Pathable<T>,
    ): AssociationJoinOnStep<T> {
        return AssociationFetchJoinDsl(Entities.entity(T::class), path.toPath(), JoinType.INNER)
    }

    /**
     * Creates an association fetch join with the path.
     */
    @JvmName("innerFetchJoinCollection")
    @SinceJdsl("3.0.0")
    inline fun <reified T : Any, S : Collection<T>> innerFetchJoin(
        path: Pathable<S>,
    ): AssociationJoinOnStep<T> {
        return AssociationFetchJoinDsl(Entities.entity(T::class), path.toPath(), JoinType.INNER)
    }

    /**
     * Creates a fetch join with the entity specified by the type.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> leftFetchJoin(
        type: KClass<T>,
    ): JoinOnStep<T> {
        return FetchJoinDsl(Entities.entity(type), JoinType.LEFT)
    }

    /**
     * Creates a fetch join with the entity.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> leftFetchJoin(
        entity: Entityable<T>,
    ): JoinOnStep<T> {
        return FetchJoinDsl(entity.toEntity(), JoinType.LEFT)
    }

    /**
     * Creates an association fetch join with the path specified by the property.
     */
    @SinceJdsl("3.0.0")
    inline fun <T : Any, reified V> leftFetchJoin(
        property: KProperty1<T, @Exact V>,
    ): AssociationJoinOnStep<V & Any> {
        @Suppress("UNCHECKED_CAST")
        val entity = Entities.entity(V::class as KClass<V & Any>)

        return AssociationFetchJoinDsl(entity, Paths.path(property), JoinType.LEFT)
    }

    /**
     * Creates an association fetch join with the path specified by the property.
     */
    @JvmName("leftFetchJoinCollection")
    @SinceJdsl("3.0.0")
    inline fun <T : Any, reified V, S : Collection<V>> leftFetchJoin(
        property: KProperty1<T, @Exact S>,
    ): AssociationJoinOnStep<V & Any> {
        @Suppress("UNCHECKED_CAST")
        val entity = Entities.entity(V::class as KClass<V & Any>)

        return AssociationFetchJoinDsl(entity, Paths.path(property), JoinType.LEFT)
    }

    /**
     * Creates an association fetch join with the path.
     */
    @SinceJdsl("3.0.0")
    inline fun <reified T : Any> leftFetchJoin(
        path: Pathable<T>,
    ): AssociationJoinOnStep<T> {
        return AssociationFetchJoinDsl(Entities.entity(T::class), path.toPath(), JoinType.LEFT)
    }

    /**
     * Creates an association fetch join with the path.
     */
    @JvmName("leftFetchJoinCollection")
    @SinceJdsl("3.0.0")
    inline fun <reified T : Any, S : Collection<T>> leftFetchJoin(
        path: Pathable<S>,
    ): AssociationJoinOnStep<T> {
        return AssociationFetchJoinDsl(Entities.entity(T::class), path.toPath(), JoinType.LEFT)
    }

    /**
     * Creates a predicate the inverse of the predicate.
     */
    @SinceJdsl("3.0.0")
    fun not(predicate: Predicatable): Predicate {
        return Predicates.not(predicate.toPredicate())
    }

    /**
     * Creates a predicate that combines predicates with `AND`.
     * The predicates are each enclosed in parentheses.
     *
     * This is the same as ```(predicate1) AND (predicate2)```.
     *
     * If predicates is empty, then it represents `1 = 1`.
     */
    @SinceJdsl("3.0.0")
    fun and(vararg predicates: Predicatable?): Predicate {
        return Predicates.and(predicates.mapNotNull { it?.toPredicate() }.map { Predicates.parentheses(it) })
    }

    /**
     * Creates a predicate that combines predicates with `AND`.
     * The predicates are each enclosed in parentheses.
     *
     * This is the same as ```predicate1 AND predicate2```.
     */
    @SinceJdsl("3.0.0")
    fun Predicatable.and(predicate: Predicatable): Predicate {
        return Predicates.and(listOf(this.toPredicate(), predicate.toPredicate()))
    }

    /**
     * Creates a predicate that combines predicates with `OR`.
     * The predicates are each enclosed in parentheses.
     *
     * This is the same as ```(predicate1) OR (predicate2)```.
     *
     * If predicates is empty, then it represents `0 = 1`.
     */
    @SinceJdsl("3.0.0")
    fun or(vararg predicates: Predicatable?): Predicate {
        return Predicates.or(predicates.mapNotNull { it?.toPredicate() }.map { Predicates.parentheses(it) })
    }

    /**
     * Creates a predicate that combines predicates with `OR`.
     * The predicates are each enclosed in parentheses.
     *
     * This is the same as ```predicate1 OR predicate2```.
     */
    @SinceJdsl("3.0.0")
    fun Predicatable.or(predicate: Predicatable): Predicate {
        return Predicates.or(listOf(this.toPredicate(), predicate.toPredicate()))
    }

    /**
     * Creates a predicate that tests whether the value is null.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> Expressionable<@Exact T>.isNull(): Predicate {
        return Predicates.isNull(this.toExpression())
    }

    /**
     * Creates a predicate that tests whether the value is not null.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> Expressionable<@Exact T>.isNotNull(): Predicate {
        return Predicates.isNotNull(this.toExpression())
    }

    /**
     * Creates a predicate that tests whether values are equal.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any, S : T?> Expressionable<@Exact T>.equal(value: S): Predicate {
        return Predicates.equal(this.toExpression(), Expressions.value(value))
    }

    /**
     * Creates a predicate that tests whether values are equal.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> Expressionable<@Exact T>.equal(value: Expressionable<T>): Predicate {
        return Predicates.equal(this.toExpression(), value.toExpression())
    }

    /**
     * Creates a predicate that tests whether the value is equal to all values in the subquery.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> Expressionable<@Exact T>.equalAll(subquery: Subquery<T>): Predicate {
        return Predicates.equalAll(this.toExpression(), subquery)
    }

    /**
     * Creates a predicate that tests whether the value is equal to any value in the subquery.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> Expressionable<@Exact T>.equalAny(subquery: Subquery<T>): Predicate {
        return Predicates.equalAny(this.toExpression(), subquery)
    }

    /**
     * Creates a predicate that tests whether values are equal.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any, S : T?> Expressionable<@Exact T>.eq(compareValue: S): Predicate {
        return Predicates.equal(this.toExpression(), Expressions.value(compareValue))
    }

    /**
     * Creates a predicate that tests whether values are equal.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> Expressionable<@Exact T>.eq(compareValue: Expressionable<T>): Predicate {
        return Predicates.equal(this.toExpression(), compareValue.toExpression())
    }

    /**
     * Creates a predicate that tests whether the value is equal to all values in the subquery.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> Expressionable<@Exact T>.eqAll(subquery: Subquery<T>): Predicate {
        return Predicates.equalAll(this.toExpression(), subquery)
    }

    /**
     * Creates a predicate that tests whether the value is equal to any value in the subquery.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> Expressionable<@Exact T>.eqAny(subquery: Subquery<T>): Predicate {
        return Predicates.equalAny(this.toExpression(), subquery)
    }

    /**
     * Creates a predicate that tests whether values are not equal.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any, S : T?> Expressionable<@Exact T>.notEqual(value: S): Predicate {
        return Predicates.notEqual(this.toExpression(), Expressions.value(value))
    }

    /**
     * Creates a predicate that tests whether values are not equal.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> Expressionable<@Exact T>.notEqual(value: Expressionable<T>): Predicate {
        return Predicates.notEqual(this.toExpression(), value.toExpression())
    }

    /**
     * Creates a predicate that tests whether the value is not equal to all values in the subquery.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> Expressionable<@Exact T>.notEqualAll(subquery: Subquery<T>): Predicate {
        return Predicates.notEqualAll(this.toExpression(), subquery)
    }

    /**
     * Creates a predicate that tests whether the value is not equal to any value in the subquery.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> Expressionable<@Exact T>.notEqualAny(subquery: Subquery<T>): Predicate {
        return Predicates.notEqualAny(this.toExpression(), subquery)
    }

    /**
     * Creates a predicate that tests whether values are not equal.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any, S : T?> Expressionable<@Exact T>.ne(value: S): Predicate {
        return Predicates.notEqual(this.toExpression(), Expressions.value(value))
    }

    /**
     * Creates a predicate that tests whether values are not equal.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> Expressionable<@Exact T>.ne(value: Expressionable<T>): Predicate {
        return Predicates.notEqual(this.toExpression(), value.toExpression())
    }

    /**
     * Creates a predicate that tests whether the value is not equal to all values in the subquery.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> Expressionable<@Exact T>.neAll(subquery: Subquery<T>): Predicate {
        return Predicates.notEqualAll(this.toExpression(), subquery)
    }

    /**
     * Creates a predicate that tests whether the value is not equal to any value in the subquery.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> Expressionable<@Exact T>.neAny(subquery: Subquery<T>): Predicate {
        return Predicates.notEqualAny(this.toExpression(), subquery)
    }

    /**
     * Creates a predicate that tests whether the value1 is less than the value2.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>, S : T?> Expressionable<@Exact T>.lessThan(value: S, inclusive: Boolean): Predicate {
        return if (inclusive) {
            Predicates.lessThanOrEqualTo(this.toExpression(), Expressions.value(value))
        } else {
            Predicates.lessThan(this.toExpression(), Expressions.value(value))
        }
    }

    /**
     * Creates a predicate that tests whether the value1 is less than the value2.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>, S : T?> Expressionable<@Exact T>.lessThan(value: S): Predicate {
        return lessThan(value, inclusive = false)
    }

    /**
     * Creates a predicate that tests whether the value1 is less than the value2.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.lessThan(value: Expressionable<T>, inclusive: Boolean): Predicate {
        return if (inclusive) {
            Predicates.lessThanOrEqualTo(this.toExpression(), value.toExpression())
        } else {
            Predicates.lessThan(this.toExpression(), value.toExpression())
        }
    }

    /**
     * Creates a predicate that tests whether the value1 is less than the value2.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.lessThan(value: Expressionable<T>): Predicate {
        return lessThan(value, inclusive = false)
    }

    /**
     * Creates a predicate that tests whether the value is less than all values in the subquery.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.lessThanAll(subquery: Subquery<T>, inclusive: Boolean): Predicate {
        return if (inclusive) {
            Predicates.lessThanOrEqualToAll(this.toExpression(), subquery)
        } else {
            Predicates.lessThanAll(this.toExpression(), subquery)
        }
    }

    /**
     * Creates a predicate that tests whether the value is less than any value in the subquery.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.lessThanAny(subquery: Subquery<T>, inclusive: Boolean): Predicate {
        return if (inclusive) {
            Predicates.lessThanOrEqualToAny(this.toExpression(), subquery)
        } else {
            Predicates.lessThanAny(this.toExpression(), subquery)
        }
    }

    /**
     * Creates a predicate that tests whether the value is less than all values in the subquery.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.lessThanAll(subquery: Subquery<T>): Predicate {
        return lessThanAll(subquery, inclusive = false)
    }

    /**
     * Creates a predicate that tests whether the value is less than any value in the subquery.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.lessThanAny(subquery: Subquery<T>): Predicate {
        return lessThanAny(subquery, inclusive = false)
    }

    /**
     * Creates a predicate that tests whether the value1 is less than the value2.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.lt(value: T, inclusive: Boolean): Predicate {
        return lessThan(value, inclusive)
    }

    /**
     * Creates a predicate that tests whether the value1 is less than the value2.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>, S : T?> Expressionable<@Exact T>.lt(value: S): Predicate {
        return lessThan(value, inclusive = false)
    }

    /**
     * Creates a predicate that tests whether the value1 is less than the value2.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.lt(value: Expressionable<T>, inclusive: Boolean): Predicate {
        return lessThan(value, inclusive)
    }

    /**
     * Creates a predicate that tests whether the value is less than all values in the subquery.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.ltAll(subquery: Subquery<T>, inclusive: Boolean): Predicate {
        return lessThanAll(subquery, inclusive)
    }

    /**
     * Creates a predicate that tests whether the value is less than any value in the subquery.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.ltAny(subquery: Subquery<T>, inclusive: Boolean): Predicate {
        return lessThanAny(subquery, inclusive)
    }

    /**
     * Creates a predicate that tests whether the value1 is less than the value2.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.lt(value: Expressionable<T>): Predicate {
        return lessThan(value, inclusive = false)
    }

    /**
     * Creates a predicate that tests whether the value is less than all values in the subquery.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.ltAll(subquery: Subquery<T>): Predicate {
        return lessThanAll(subquery, inclusive = false)
    }

    /**
     * Creates a predicate that tests whether the value is less than any value in the subquery.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.ltAny(subquery: Subquery<T>): Predicate {
        return lessThanAny(subquery, inclusive = false)
    }

    /**
     * Creates a predicate that tests whether the value1 is less than or equal to the value2.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>, S : T?> Expressionable<@Exact T>.lessThanOrEqualTo(value: S): Predicate {
        return lessThan(value, inclusive = true)
    }

    /**
     * Creates a predicate that tests whether the value1 is less than or equal to the value2.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.lessThanOrEqualTo(value: Expressionable<T>): Predicate {
        return lessThan(value, inclusive = true)
    }

    /**
     * Creates a predicate that tests whether the value is less than or equal to all values in the subquery.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.lessThanOrEqualToAll(subquery: Subquery<T>): Predicate {
        return lessThanAll(subquery, inclusive = true)
    }

    /**
     * Creates a predicate that tests whether the value is less than or equal to any value in the subquery.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.lessThanOrEqualToAny(subquery: Subquery<T>): Predicate {
        return lessThanAny(subquery, inclusive = true)
    }

    /**
     * Creates a predicate that tests whether the value1 is less than or equal to the value2.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>, S : T?> Expressionable<@Exact T>.le(value: S): Predicate {
        return lessThan(value, inclusive = true)
    }

    /**
     * Creates a predicate that tests whether the value1 is less than or equal to the value2.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.le(value: Expressionable<T>): Predicate {
        return lessThan(value, inclusive = true)
    }

    /**
     * Creates a predicate that tests whether the value is less than or equal to all values in the subquery.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.leAll(subquery: Subquery<T>): Predicate {
        return lessThanAll(subquery, inclusive = true)
    }

    /**
     * Creates a predicate that tests whether the value is less than or equal to any value in the subquery.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.leAny(subquery: Subquery<T>): Predicate {
        return lessThanAny(subquery, inclusive = true)
    }

    /**
     * Creates a predicate that tests whether the value1 is greater than the value2.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>, S : T?> Expressionable<@Exact T>.greaterThan(value: S, inclusive: Boolean): Predicate {
        return if (inclusive) {
            Predicates.greaterThanOrEqualTo(this.toExpression(), Expressions.value(value))
        } else {
            Predicates.greaterThan(this.toExpression(), Expressions.value(value))
        }
    }

    /**
     * Creates a predicate that tests whether the value1 is greater than the value2.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>, S : T?> Expressionable<@Exact T>.greaterThan(value: S): Predicate {
        return greaterThan(value, inclusive = false)
    }

    /**
     * Creates a predicate that tests whether the value1 is greater than the value2.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.greaterThan(
        value: Expressionable<T>,
        inclusive: Boolean,
    ): Predicate {
        return if (inclusive) {
            Predicates.greaterThanOrEqualTo(this.toExpression(), value.toExpression())
        } else {
            Predicates.greaterThan(this.toExpression(), value.toExpression())
        }
    }

    /**
     * Creates a predicate that tests whether the value is greater than all values in the subquery.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.greaterThanAll(
        subquery: Subquery<T>,
        inclusive: Boolean,
    ): Predicate {
        return if (inclusive) {
            Predicates.greaterThanOrEqualToAll(this.toExpression(), subquery)
        } else {
            Predicates.greaterThanAll(this.toExpression(), subquery)
        }
    }

    /**
     * Creates a predicate that tests whether the value is greater than any value in the subquery.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.greaterThanAny(
        subquery: Subquery<T>,
        inclusive: Boolean,
    ): Predicate {
        return if (inclusive) {
            Predicates.greaterThanOrEqualToAny(this.toExpression(), subquery)
        } else {
            Predicates.greaterThanAny(this.toExpression(), subquery)
        }
    }

    /**
     * Creates a predicate that tests whether the value1 is greater than the value2.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.greaterThan(value: Expressionable<T>): Predicate {
        return greaterThan(value, inclusive = false)
    }

    /**
     * Creates a predicate that tests whether the value is greater than all values in the subquery.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.greaterThanAll(subquery: Subquery<T>): Predicate {
        return greaterThanAll(subquery, inclusive = false)
    }

    /**
     * Creates a predicate that tests whether the value is greater than any value in the subquery.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.greaterThanAny(subquery: Subquery<T>): Predicate {
        return greaterThanAny(subquery, inclusive = false)
    }

    /**
     * Creates a predicate that tests whether the value1 is greater than the value2.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>, S : T?> Expressionable<@Exact T>.gt(value: S, inclusive: Boolean): Predicate {
        return greaterThan(value, inclusive)
    }

    /**
     * Creates a predicate that tests whether the value1 is greater than the value2.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>, S : T?> Expressionable<@Exact T>.gt(value: S): Predicate {
        return greaterThan(value, inclusive = false)
    }

    /**
     * Creates a predicate that tests whether the value1 is greater than the value2.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.gt(value: Expressionable<T>, inclusive: Boolean): Predicate {
        return greaterThan(value, inclusive)
    }

    /**
     * Creates a predicate that tests whether the value is greater than all values in the subquery.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.gtAll(subquery: Subquery<T>, inclusive: Boolean): Predicate {
        return greaterThanAll(subquery, inclusive)
    }

    /**
     * Creates a predicate that tests whether the value is greater than any value in the subquery.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.gtAny(subquery: Subquery<T>, inclusive: Boolean): Predicate {
        return greaterThanAny(subquery, inclusive)
    }

    /**
     * Creates a predicate that tests whether the value1 is greater than the value2.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.gt(value: Expressionable<T>): Predicate {
        return greaterThan(value, inclusive = false)
    }

    /**
     * Creates a predicate that tests whether the value is greater than all values in the subquery.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.gtAll(subquery: Subquery<T>): Predicate {
        return greaterThanAll(subquery, inclusive = false)
    }

    /**
     * Creates a predicate that tests whether the value is greater than any value in the subquery.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.gtAny(subquery: Subquery<T>): Predicate {
        return greaterThanAny(subquery, inclusive = false)
    }

    /**
     * Creates a predicate that tests whether the value1 is greater than or equal to the value2.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>, S : T?> Expressionable<@Exact T>.greaterThanOrEqualTo(value: S): Predicate {
        return greaterThan(value, inclusive = true)
    }

    /**
     * Creates a predicate that tests whether the value1 is greater than or equal to the value2.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.greaterThanOrEqualTo(value: Expressionable<T>): Predicate {
        return greaterThan(value, inclusive = true)
    }

    /**
     * Creates a predicate that tests whether the value is greater than or equal to all values in the subquery.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.greaterThanOrEqualToAll(subquery: Subquery<T>): Predicate {
        return greaterThanAll(subquery, inclusive = true)
    }

    /**
     * Creates a predicate that tests whether the value is greater than or equal to any value in the subquery.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.greaterThanOrEqualToAny(subquery: Subquery<T>): Predicate {
        return greaterThanAny(subquery, inclusive = true)
    }

    /**
     * Creates a predicate that tests whether the value1 is greater than or equal to the value2.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>, S : T?> Expressionable<@Exact T>.ge(value: S): Predicate {
        return greaterThan(value, inclusive = true)
    }

    /**
     * Creates a predicate that tests whether the value1 is greater than or equal to the value2.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.ge(value: Expressionable<T>): Predicate {
        return greaterThan(value, inclusive = true)
    }

    /**
     * Creates a predicate that tests whether the value is greater than or equal to all values in the subquery.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.geAll(subquery: Subquery<T>): Predicate {
        return greaterThanAll(subquery, inclusive = true)
    }

    /**
     * Creates a predicate that tests whether the value is greater than or equal to any value in the subquery.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.geAny(subquery: Subquery<T>): Predicate {
        return greaterThanAny(subquery, inclusive = true)
    }

    /**
     * Creates a predicate that tests whether the value is between in min and max.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>, S : T?> Expressionable<@Exact T>.between(min: S, max: S): Predicate {
        return Predicates.between(this.toExpression(), Expressions.value(min), Expressions.value(max))
    }

    /**
     * Creates a predicate that tests whether the value is between in min and max.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.between(
        min: Expressionable<T>,
        max: Expressionable<T>,
    ): Predicate {
        return Predicates.between(this.toExpression(), min.toExpression(), max.toExpression())
    }

    /**
     * Creates a predicate that tests whether the value is not between in min and max.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>, S : T?> Expressionable<@Exact T>.notBetween(min: S, max: S): Predicate {
        return Predicates.notBetween(this.toExpression(), Expressions.value(min), Expressions.value(max))
    }

    /**
     * Creates a predicate that tests whether the value is not between in min and max.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.notBetween(
        min: Expressionable<T>,
        max: Expressionable<T>,
    ): Predicate {
        return Predicates.notBetween(this.toExpression(), min.toExpression(), max.toExpression())
    }

    /**
     * Creates a predicate that tests whether the value is in compareValues.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any, S : T?> Expressionable<@Exact T>.`in`(vararg compareValues: S): Predicate {
        return Predicates.`in`(this.toExpression(), compareValues.map { Expressions.value(it) })
    }

    /**
     * Creates a predicate that tests whether the value is in compareValues.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any, S : T?> Expressionable<@Exact T>.`in`(compareValues: Iterable<S>): Predicate {
        return Predicates.`in`(this.toExpression(), compareValues.map { Expressions.value(it) })
    }

    /**
     * Creates a predicate that tests whether the value is in compareValues.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> Expressionable<@Exact T>.`in`(vararg compareValues: Expressionable<T>?): Predicate {
        return Predicates.`in`(this.toExpression(), compareValues.mapNotNull { it?.toExpression() })
    }

    /**
     * Creates a predicate that tests whether the value is in compareValues.
     */
    @JvmName("inExpressions")
    @SinceJdsl("3.0.0")
    fun <T : Any> Expressionable<@Exact T>.`in`(compareValues: Iterable<Expressionable<T>?>): Predicate {
        return Predicates.`in`(this.toExpression(), compareValues.mapNotNull { it?.toExpression() })
    }

    /**
     * Creates a predicate that tests whether the value is in the subquery.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> Expressionable<@Exact T>.`in`(subquery: Subquery<T>): Predicate {
        return Predicates.`in`(this.toExpression(), subquery)
    }

    /**
     * Creates a predicate that tests whether the value is not in compareValues.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any, S : T?> Expressionable<@Exact T>.notIn(compareValues: Iterable<S>): Predicate {
        return Predicates.notIn(this.toExpression(), compareValues.map { Expressions.value(it) })
    }

    /**
     * Creates a predicate that tests whether the value is not in compareValues.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any, S : T?> Expressionable<@Exact T>.notIn(vararg compareValues: S): Predicate {
        return Predicates.notIn(this.toExpression(), compareValues.map { Expressions.value(it) })
    }

    /**
     * Creates a predicate that tests whether the value is not in compareValues.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> Expressionable<@Exact T>.notIn(vararg compareValues: Expressionable<T>?): Predicate {
        return Predicates.notIn(this.toExpression(), compareValues.mapNotNull { it?.toExpression() })
    }

    /**
     * Creates a predicate that tests whether the value is not in compareValues.
     */
    @JvmName("notInExpressions")
    @SinceJdsl("3.0.0")
    fun <T : Any> Expressionable<@Exact T>.notIn(compareValues: Iterable<Expressionable<T>?>): Predicate {
        return Predicates.notIn(this.toExpression(), compareValues.mapNotNull { it?.toExpression() })
    }

    /**
     * Creates a predicate that tests whether the value is not in the subquery.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> Expressionable<@Exact T>.notIn(subquery: Subquery<T>): Predicate {
        return Predicates.notIn(this.toExpression(), subquery)
    }

    /**
     * Creates a predicate that tests whether the value matches the pattern.
     */
    @SinceJdsl("3.0.0")
    fun Expressionable<String>.like(
        pattern: String,
        escape: Char? = null,
    ): Predicate {
        return Predicates.like(
            this.toExpression(),
            Expressions.value(pattern),
            escape?.let { Expressions.value(it) },
        )
    }

    /**
     * Creates a predicate that tests whether the value matches the pattern.
     */
    @SinceJdsl("3.0.0")
    fun Expressionable<String>.like(
        pattern: Expressionable<String>,
        escape: Expressionable<Char>? = null,
    ): Predicate {
        return Predicates.like(
            this.toExpression(),
            pattern.toExpression(),
            escape?.toExpression(),
        )
    }

    /**
     * Creates a predicate that tests whether the value does not match the pattern.
     */
    @SinceJdsl("3.0.0")
    fun Expressionable<String>.notLike(
        pattern: String,
        escape: Char? = null,
    ): Predicate {
        return Predicates.notLike(
            this.toExpression(),
            Expressions.value(pattern),
            escape?.let { Expressions.value(it) },
        )
    }

    /**
     * Creates a predicate that tests whether the value does not match the pattern.
     */
    @SinceJdsl("3.0.0")
    fun Expressionable<String>.notLike(
        pattern: Expressionable<String>,
        escape: Expressionable<Char>? = null,
    ): Predicate {
        return Predicates.notLike(
            this.toExpression(),
            pattern.toExpression(),
            escape?.toExpression(),
        )
    }

    /**
     * Creates a predicate that tests whether the path has no elements.
     */
    @SinceJdsl("3.0.0")
    fun <T, S : Collection<T>> Pathable<S>.isEmpty(): Predicate {
        return Predicates.isEmpty(this.toPath())
    }

    /**
     * Creates a predicate that tests whether the path has an element.
     */
    @SinceJdsl("3.0.0")
    fun <T, S : Collection<T>> Pathable<S>.isNotEmpty(): Predicate {
        return Predicates.isNotEmpty(this.toPath())
    }

    /**
     * Creates a predicate that tests whether the subquery has a row.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> exists(subquery: Subquery<T>): Predicate {
        return Predicates.exists(subquery)
    }

    /**
     * Creates a predicate that tests whether the subquery has no row.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> notExists(subquery: Subquery<T>): Predicate {
        return Predicates.notExists(subquery)
    }

    /**
     * Creates a predicate that represents predefined database functions and user-defined database functions.
     */
    @Suppress("UNUSED_PARAMETER")
    @LowPriorityInOverloadResolution
    @SinceJdsl("3.0.0")
    fun function(type: KClass<Boolean>, name: String, vararg args: Any): Predicate {
        return Predicates.function(name, args.map { Expressions.value(it) })
    }

    /**
     * Creates a predicate that represents predefined database functions and user-defined database functions.
     */
    @Suppress("UNUSED_PARAMETER")
    @SinceJdsl("3.0.0")
    fun function(type: KClass<Boolean>, name: String, vararg args: Expressionable<*>): Predicate {
        return Predicates.function(name, args.map { it.toExpression() })
    }

    /**
     * Creates a predicate that represents the user-defined predicate.
     *
     * The template for the user-defined predicate can have placeholders.
     * Placeholders in template are replaced with the expression in args, matching with index.
     *
     * ```
     * Placeholder: { ArgumentIndex }
     * ```
     *
     * Examples:
     * ```
     * customPredicate("{0} MEMBER OF {1}", value(author), path(Book::authors))
     * ```
     */
    @LowPriorityInOverloadResolution
    @SinceJdsl("3.3.0")
    fun customPredicate(template: String, vararg args: Any): Predicate {
        return Predicates.customPredicate(template, args.map { Expressions.value(it) })
    }

    /**
     * Creates a predicate that represents the user-defined predicate.
     *
     * The template for the user-defined predicate can have placeholders.
     * Placeholders in template are replaced with the expression in args, matching with index.
     *
     * ```
     * Placeholder: { ArgumentIndex }
     * ```
     *
     * Examples:
     * ```
     * customPredicate("{0} MEMBER OF {1}", value(author), path(Book::authors))
     * ```
     */
    @SinceJdsl("3.3.0")
    fun customPredicate(template: String, vararg args: Expressionable<*>): Predicate {
        return Predicates.customPredicate(template, args.map { it.toExpression() })
    }

    /**
     * Creates a sort that sorts the expression in ascending order.
     */
    @SinceJdsl("3.0.0")
    fun Expressionable<*>.asc(): SortNullsStep {
        return SortDsl(this.toExpression(), Sort.Order.ASC)
    }

    /**
     * Creates a sort that sorts the expression in descending order.
     */
    @SinceJdsl("3.0.0")
    fun Expressionable<*>.desc(): SortNullsStep {
        return SortDsl(this.toExpression(), Sort.Order.DESC)
    }

    /**
     * Creates a select clause in a select query.
     */
    @SinceJdsl("3.0.0")
    inline fun <reified T : Any> select(
        expr: Expressionable<T>,
    ): SelectQueryFromStep<T> {
        return SelectQueryFromStepDsl(
            T::class,
            distinct = false,
            listOf(expr.toExpression()),
        )
    }

    /**
     * Creates a select clause in a select query.
     */
    @SinceJdsl("3.0.0")
    inline fun <reified T : Any> select(
        expr: Expressionable<*>,
        vararg exprs: Expressionable<*>,
    ): SelectQueryFromStep<T> {
        return SelectQueryFromStepDsl(
            T::class,
            distinct = false,
            listOf(expr.toExpression()) + exprs.map { it.toExpression() },
        )
    }

    /**
     * Creates a select clause in a select query.
     */
    @SinceJdsl("3.0.0")
    inline fun <reified T : Any> selectDistinct(
        expr: Expressionable<T>,
    ): SelectQueryFromStep<T> {
        return SelectQueryFromStepDsl(
            T::class,
            distinct = true,
            listOf(expr.toExpression()),
        )
    }

    /**
     * Creates a select clause in a select query.
     */
    @SinceJdsl("3.0.0")
    inline fun <reified T : Any> selectDistinct(
        expr: Expressionable<*>,
        vararg exprs: Expressionable<*>,
    ): SelectQueryFromStep<T> {
        return SelectQueryFromStepDsl(
            T::class,
            distinct = true,
            listOf(expr.toExpression()) + exprs.map { it.toExpression() },
        )
    }

    /**
     * Creates a select clause with the DTO projection in a select query.
     */
    @SinceJdsl("3.0.0")
    inline fun <reified T : Any> selectNew(
        expr: Expressionable<*>,
        vararg exprs: Expressionable<*>,
    ): SelectQueryFromStep<T> {
        return SelectQueryFromStepDsl(
            returnType = T::class,
            distinct = false,
            select = listOf(
                Expressions.new(
                    T::class,
                    listOf(expr.toExpression()) + exprs.map { it.toExpression() },
                ),
            ),
        )
    }

    /**
     * Creates a select clause with the DTO projection in a select query.
     */
    @SinceJdsl("3.0.0")
    inline fun <reified T : Any> selectDistinctNew(
        expr: Expressionable<*>,
        vararg exprs: Expressionable<*>,
    ): SelectQueryFromStep<T> {
        return SelectQueryFromStepDsl(
            returnType = T::class,
            distinct = true,
            select = listOf(
                Expressions.new(
                    T::class,
                    listOf(expr.toExpression()) + exprs.map { it.toExpression() },
                ),
            ),
        )
    }

    /**
     * Creates an update clause in an update query.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> update(entity: Entityable<T>): UpdateQuerySetFirstStep<T> {
        return UpdateQuerySetStepFirstDsl(entity.toEntity())
    }

    /**
     * Creates a delete from clause in a delete query.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> deleteFrom(entity: Entityable<T>): DeleteQueryWhereStep<T> {
        return DeleteQueryDsl(entity.toEntity())
    }
}
