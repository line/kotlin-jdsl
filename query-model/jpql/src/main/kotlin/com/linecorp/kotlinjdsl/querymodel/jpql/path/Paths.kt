package com.linecorp.kotlinjdsl.querymodel.jpql.path

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.property.PropertyUtils
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entities
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entity
import com.linecorp.kotlinjdsl.querymodel.jpql.path.impl.JpqlEntityProperty
import com.linecorp.kotlinjdsl.querymodel.jpql.path.impl.JpqlPathProperty
import com.linecorp.kotlinjdsl.querymodel.jpql.path.impl.JpqlPathTreat
import kotlin.internal.Exact
import kotlin.reflect.KClass
import kotlin.reflect.KFunction1
import kotlin.reflect.KProperty1

/**
 * Factory class that creates [Path].
 */
@SinceJdsl("3.0.0")
object Paths {
    /**
     * Creates a path with the property.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any, V> path(property: KProperty1<T, @Exact V>): Path<V & Any> {
        val owner = PropertyUtils.getOwner(property)

        return JpqlEntityProperty(Entities.entity(owner), property)
    }

    /**
     * Creates a path with the property.
     */
    @SinceJdsl("3.1.0")
    fun <T : Any, V> path(getter: KFunction1<T, @Exact V>): Path<V & Any> {
        val owner = PropertyUtils.getOwner(getter)

        return JpqlEntityProperty(Entities.entity(owner), getter)
    }

    /**
     * Creates a path with the entity and property.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any, V> path(entity: Entity<T>, property: KProperty1<in T, @Exact V>): Path<V & Any> {
        return JpqlEntityProperty(entity, property)
    }

    /**
     * Creates a path with the entity and property.
     */
    @SinceJdsl("3.1.0")
    fun <T : Any, V> path(entity: Entity<T>, getter: KFunction1<T, @Exact V>): Path<V & Any> {
        return JpqlEntityProperty(entity, getter)
    }

    /**
     * Creates a path with the path and property.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any, V> path(path: Path<T>, property: KProperty1<in T, @Exact V>): Path<V & Any> {
        return JpqlPathProperty(path, property)
    }

    /**
     * Creates a path with the path and property.
     */
    @SinceJdsl("3.1.0")
    fun <T : Any, V> path(path: Path<T>, getter: KFunction1<T, @Exact V>): Path<V & Any> {
        return JpqlPathProperty(path, getter)
    }

    /**
     * Creates a path with downcasting.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any, S : T> treat(path: Path<T>, type: KClass<S>): Path<S> {
        return JpqlPathTreat(path, type)
    }
}
