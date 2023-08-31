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
import kotlin.reflect.KProperty1

@SinceJdsl("3.0.0")
object Paths {
    @SinceJdsl("3.0.0")
    fun <T : Any, V> path(property: KProperty1<T, @Exact V>): Path<V & Any> {
        val owner = PropertyUtils.getOwner(property)

        return JpqlEntityProperty(Entities.entity(owner), property)
    }

    @SinceJdsl("3.0.0")
    fun <T : Any, V> path(entity: Entity<T>, property: KProperty1<T, @Exact V>): Path<V & Any> {
        return JpqlEntityProperty(entity, property)
    }

    @SinceJdsl("3.0.0")
    fun <T : Any, V> path(path: Path<T>, property: KProperty1<T, @Exact V>): Path<V & Any> {
        return JpqlPathProperty(path, property)
    }

    @SinceJdsl("3.0.0")
    fun <T : Any, S : T> treat(path: Path<T>, type: KClass<S>): Path<S> {
        return JpqlPathTreat(path, type)
    }
}
