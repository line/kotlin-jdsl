package com.linecorp.kotlinjdsl.querymodel.jpql.entity

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.querymodel.QueryPart
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.from.From

@SinceJdsl("3.0.0")
interface Entity<T : Any> : From, Expression<T>, Entityable<T>, QueryPart {
    @SinceJdsl("3.0.0")
    val alias: String

    override fun toEntity(): Entity<T> = this
    override fun toExpression(): Expression<T> = this
    override fun toFrom(): From = this
}
