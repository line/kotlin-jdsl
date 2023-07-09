package com.linecorp.kotlinjdsl.dsl.jpql

import com.linecorp.kotlinjdsl.dsl.jpql.expression.CaseValueWhenFirstStep
import com.linecorp.kotlinjdsl.dsl.jpql.expression.CaseWhenStep
import com.linecorp.kotlinjdsl.dsl.jpql.expression.impl.CaseDsl
import com.linecorp.kotlinjdsl.dsl.jpql.expression.impl.CaseValueWhenFirstStepDsl
import com.linecorp.kotlinjdsl.dsl.jpql.select.SelectQueryFromStep
import com.linecorp.kotlinjdsl.dsl.jpql.select.impl.SelectQueryFromStepDsl
import com.linecorp.kotlinjdsl.dsl.owner
import com.linecorp.kotlinjdsl.querymodel.jpql.*
import com.linecorp.kotlinjdsl.querymodel.jpql.impl.*
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1

object JpqlDslSupport {
    fun <T> value(value: T): Expression<T> {
        return Param(null, value)
    }

    fun <T> nullValue(): Expression<T> {
        return Param(null, null)
    }

    fun <T> param(): Expression<T> {
        return Param(null, null)
    }

    fun <T> param(name: String): Expression<T> {
        return Param(name, null)
    }

    fun <T> param(name: String, value: T): Expression<T> {
        return Param(name, value)
    }

    fun <T : Any> entity(type: KClass<T>, alias: String = type.simpleName!!): Path<T> {
        return AliasedPath(Entity(type), alias)
    }

    @JvmName("path1")
    fun <V> path(property: KProperty1<*, V>): Path<V> {
        val owner = property.owner()

        return Field(entity(owner), property.name)
    }

    @JvmName("path2")
    fun <T : Any, V> path(path: Path<T>, property: KProperty1<T, V>): Path<V> {
        return Field(path, property.name)
    }

    @JvmName("path3")
    fun <T : Any, V> path(path: Path<T?>, property: KProperty1<T, V>): Path<V?> {
        return Field(path, property.name)
    }

    @JvmName("path4")
    fun <T : Any, V> path(left: KProperty1<*, T>, right: KProperty1<T, V>): Path<V> {
        return Field(path(left), right.name)
    }

    @JvmName("path5")
    fun <T : Any, V> path(left: KProperty1<*, T?>, right: KProperty1<T, V>): Path<V?> {
        return Field(path(left), right.name)
    }

    fun <T> `as`(path: Path<T>, alias: String): Path<T> {
        return if (path is AliasedPath) {
            AliasedPath(path.path, alias)
        } else {
            AliasedPath(path, alias)
        }
    }

    fun <T> `as`(expression: Expressionable<T>, alias: String): Expression<T> {
        return if (expression is AliasedExpression) {
            AliasedExpression(expression.expression, alias)
        } else {
            AliasedExpression(expression.toExpression(), alias)
        }
    }

    @JvmName("treat1")
    fun <PARENT : Any, CHILD : PARENT> treat(path: Path<PARENT>, type: KClass<CHILD>): Path<CHILD> {
        return Treat(path, type)
    }

    @JvmName("treat2")
    fun <PARENT : Any, CHILD : PARENT> treat(path: Path<PARENT?>, type: KClass<CHILD>): Path<CHILD?> {
        return Treat(path, type)
    }

    fun <T> case(expression: Expressionable<T>): CaseValueWhenFirstStep<T> {
        return CaseValueWhenFirstStepDsl(expression.toExpression())
    }

    fun <T> caseWhen(predicate: Predicatable, then: T): CaseWhenStep<T?> {
        return CaseDsl(predicate.toPredicate(), value(then))
    }

    fun <T> caseWhen(predicate: Predicatable, then: Expressionable<T>): CaseWhenStep<T?> {
        @Suppress("UNCHECKED_CAST")
        return CaseDsl(predicate.toPredicate(), then.toExpression() as Expression<T?>)
    }

    fun <T> coalesce(expression: Expressionable<in T>, expressions: Collection<Expressionable<in T>>): Expression<T> {
        return Coalesce(listOf(expression.toExpression()) + expressions.map { it.toExpression() })
    }

    fun <T> nullIf(left: Expressionable<T>, right: Expressionable<T>): Expression<T?> {
        return NullIf(left.toExpression(), right.toExpression())
    }

    fun <T> type(path: Path<T>): Expression<KClass<T & Any>> {
        return Type(path)
    }

    fun <T> templateExpression(template: String, args: Collection<Expressionable<*>>): Expression<T> {
        return TemplateExpression(template, args.map { it.toExpression() })
    }

    fun <T, CLASS : T & Any> join(
        left: Path<*>,
        right: Path<T>,
        on: Predicate?,
        joinType: JoinType,
        fetch: Boolean,
        type: KClass<CLASS>,
    ): Path<T> {
        val aliasedRight = if (right is AliasedPath) {
            right
        } else {
            AliasedPath(right, type.simpleName!!)
        }

        return Join(
            left = left,
            right = aliasedRight,
            on = on,
            joinType = joinType,
            fetch = fetch,
        )
    }

    fun <T> equal(left: Expressionable<T>, right: T): Predicate {
        return Equal(left.toExpression(), value(right), not = false)
    }

    fun <T> equal(left: Expressionable<T>, right: Expressionable<T>): Predicate {
        return Equal(left.toExpression(), right.toExpression(), not = false)
    }

    fun <T> notEqual(left: Expressionable<T>, right: T): Predicate {
        return Equal(left.toExpression(), value(right), not = true)
    }

    fun <T> notEqual(left: Expressionable<T>, right: Expressionable<T>): Predicate {
        return Equal(left.toExpression(), right.toExpression(), not = true)
    }

    fun asc(expression: Expressionable<*>): Sort {
        return SortExpression(expression.toExpression(), Sort.Order.ASC)
    }

    fun desc(expression: Expressionable<*>): Sort {
        return SortExpression(expression.toExpression(), Sort.Order.DESC)
    }

    fun select(values: Collection<Any?>, distinct: Boolean = false): SelectQueryFromStep {
        val expressions = values.map {
            when (it) {
                is KProperty1<*, *> -> path(it)
                is Expressionable<*> -> it.toExpression()
                else -> value(it)
            }
        }

        return SelectQueryFromStepDsl(expressions, distinct)
    }
}
