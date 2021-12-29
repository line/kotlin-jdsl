package com.linecorp.kotlinjdsl.querydsl.from

import javax.persistence.criteria.JoinType
import kotlin.reflect.KProperty1

inline fun <reified T : Any, reified R : Any> JoinDsl.join(
    property: KProperty1<T, R?>,
    joinType: JoinType = JoinType.INNER,
) {
    join(T::class, R::class, Relation(property.name), joinType)
}

@JvmName("joinCollection")
inline fun <reified T : Any, reified R : Collection<E>, reified E : Any> JoinDsl.join(
    property: KProperty1<T, R?>,
    joinType: JoinType = JoinType.INNER,
) {
    join(T::class, E::class, Relation(property.name), joinType)
}

inline fun <reified T : Any, reified R : Any> JoinDsl.associate(
    property: KProperty1<T, R?>,
    joinType: JoinType = JoinType.INNER,
) {
    associate(T::class, R::class, Relation(property.name), joinType)
}

@JvmName("associateCollection")
inline fun <reified T : Any, reified R : Collection<E>, reified E : Any> JoinDsl.associate(
    property: KProperty1<T, R?>,
    joinType: JoinType = JoinType.INNER,
) {
    associate(T::class, E::class, Relation(property.name), joinType)
}