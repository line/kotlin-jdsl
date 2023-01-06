package com.linecorp.kotlinjdsl.querydsl.from

import jakarta.persistence.criteria.JoinType
import kotlin.reflect.KProperty1

inline fun <reified T : Any, reified R : Any> AssociateDsl.associate(
    property: KProperty1<T, R?>,
    joinType: JoinType = JoinType.INNER,
) {
    associate(T::class, R::class, Relation(property.name), joinType)
}

@JvmName("associateCollection")
inline fun <reified T : Any, reified R : Collection<E>, reified E : Any> AssociateDsl.associate(
    property: KProperty1<T, R?>,
    joinType: JoinType = JoinType.INNER,
) {
    associate(T::class, E::class, Relation(property.name), joinType)
}
