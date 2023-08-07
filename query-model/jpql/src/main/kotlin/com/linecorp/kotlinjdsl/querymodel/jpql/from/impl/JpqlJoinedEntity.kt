package com.linecorp.kotlinjdsl.querymodel.jpql.from.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entity
import com.linecorp.kotlinjdsl.querymodel.jpql.from.From
import com.linecorp.kotlinjdsl.querymodel.jpql.join.Join
import com.linecorp.kotlinjdsl.querymodel.jpql.join.JoinType

data class JpqlJoinedEntity(
    val entity: Entity<*>,
    val join: Join,
) : Entity<Any>, Join {
    override val alias: String get() = entity.alias

    override val joinType: JoinType get() = join.joinType
    override val fetch: Boolean get() = join.fetch

    override fun toFrom(): From = this
}
