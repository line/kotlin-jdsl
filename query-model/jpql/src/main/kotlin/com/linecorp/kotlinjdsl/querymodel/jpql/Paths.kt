package com.linecorp.kotlinjdsl.querymodel.jpql

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.owner
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressionable
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlField
import com.linecorp.kotlinjdsl.querymodel.jpql.path.JoinType
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Path
import com.linecorp.kotlinjdsl.querymodel.jpql.path.PathAndExpression
import com.linecorp.kotlinjdsl.querymodel.jpql.path.impl.*
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicate
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1

object Paths {
    @SinceJdsl("3.0.0")
    fun <T : Any> entity(type: KClass<T>, alias: String = type.simpleName!!): Path<T> {
        return JpqlAliasedPath(JpqlEntity(type), alias)
    }

    @JvmName("path1")
    @SinceJdsl("3.0.0")
    fun <V> path(type: KClass<*>, property: KProperty1<*, V>): Path<V> {
        val owner = property.owner()

        return JpqlField(type, entity(owner), property.name)
    }

    @JvmName("path2")
    @SinceJdsl("3.0.0")
    inline fun <reified V> path(property: KProperty1<*, V>): Path<V> {
        return path(V::class, property)
    }

    @JvmName("path3")
    @SinceJdsl("3.0.0")
    fun <T, V> path(type: KClass<*>, path: Path<T>, property: KProperty1<*, V>): Path<V> {
        return JpqlField(type, path, property.name)
    }

    @JvmName("path4")
    @SinceJdsl("3.0.0")
    inline fun <T : Any, reified V> path(path: Path<T>, property: KProperty1<T, V>): Path<V> {
        return path(V::class, path, property)
    }

    @JvmName("path5")
    @SinceJdsl("3.0.0")
    inline fun <T : Any, reified V> path(path: Path<T?>, property: KProperty1<T, V>): Path<V?> {
        return path(V::class, path, property)
    }

    @JvmName("treat1")
    @SinceJdsl("3.0.0")
    fun <PARENT : Any, CHILD : PARENT> treat(path: Path<PARENT>, type: KClass<CHILD>): Path<CHILD> {
        return JpqlTreat(path, type)
    }

    @JvmName("treat2")
    @SinceJdsl("3.0.0")
    fun <PARENT : Any, CHILD : PARENT> treat(path: Path<PARENT?>, type: KClass<CHILD>): Path<CHILD?> {
        return JpqlTreat(path, type)
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

        return JpqlJoin(
            left = aliasedLeft,
            right = aliasedRight,
            on = on,
            joinType = joinType,
            fetch = fetch,
        )
    }

    @SinceJdsl("3.0.0")
    fun <T> alias(path: Path<T>): Path<T> {
        return when (path) {
            is JpqlAliasedPath, is JpqlJoin -> path

            else -> JpqlAliasedPath(path, path.type.simpleName!!)
        }
    }

    @SinceJdsl("3.0.0")
    fun <T> alias(path: Path<T>, alias: String): Path<T> {
        return when (path) {
            is JpqlAliasedPath -> JpqlAliasedPath(path.path, alias)

            is JpqlJoin -> {
                @Suppress("UNCHECKED_CAST")
                JpqlJoin(
                    left = path.left,
                    right = alias(path.right, alias),
                    on = path.on,
                    joinType = path.joinType,
                    fetch = path.fetch,
                ) as Path<T>
            }

            else -> JpqlAliasedPath(path, alias)
        }
    }

    @SinceJdsl("3.0.0")
    fun <T> pair(first: Path<T>, second: Expressionable<T>): PathAndExpression<T> {
        return JpqlPathAndExpression(first, second.toExpression())
    }
}
