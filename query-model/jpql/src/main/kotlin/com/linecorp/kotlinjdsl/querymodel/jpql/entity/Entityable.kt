package com.linecorp.kotlinjdsl.querymodel.jpql.entity

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressionable
import com.linecorp.kotlinjdsl.querymodel.jpql.from.Fromable

@SinceJdsl("3.0.0")
interface Entityable<T : Any> : Expressionable<T>, Fromable {
    @SinceJdsl("3.0.0")
    fun toEntity(): Entity<T>
}
