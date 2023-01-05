package com.linecorp.kotlinjdsl.querydsl.from

import jakarta.persistence.criteria.JoinType
import kotlin.reflect.KProperty1

inline fun <reified T : Any, reified R : Any> FetchDsl.fetch(
    property: KProperty1<T, R?>,
    joinType: JoinType = JoinType.INNER,
) {
    fetch(T::class, R::class, Relation(property.name), joinType)
}

@JvmName("fetchCollection")
inline fun <reified T : Any, reified R : Collection<E>, reified E : Any> FetchDsl.fetch(
    property: KProperty1<T, R?>,
    joinType: JoinType = JoinType.INNER,
) {
    fetch(T::class, E::class, Relation(property.name), joinType)
}
