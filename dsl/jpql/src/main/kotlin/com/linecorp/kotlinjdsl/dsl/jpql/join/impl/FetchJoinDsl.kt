package com.linecorp.kotlinjdsl.dsl.jpql.join.impl

import com.linecorp.kotlinjdsl.dsl.jpql.join.JoinAsStep
import com.linecorp.kotlinjdsl.dsl.jpql.join.JoinOnStep
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entity
import com.linecorp.kotlinjdsl.querymodel.jpql.from.From
import com.linecorp.kotlinjdsl.querymodel.jpql.join.Join
import com.linecorp.kotlinjdsl.querymodel.jpql.join.JoinType
import com.linecorp.kotlinjdsl.querymodel.jpql.join.Joinable
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicate

@PublishedApi
internal data class FetchJoinDsl<T : Any>(
    private val builder: FetchJoinBuilder<T>,
) : JoinOnStep<T>,
    JoinAsStep<T> {

    constructor(entity: Entity<T>, joinType: JoinType) : this(FetchJoinBuilder(entity, joinType))

    override fun on(predicate: Predicate): JoinAsStep<T> {
        builder.on(predicate)

        return this
    }

    override fun `as`(entity: Entity<T>): Joinable {
        builder.alias(entity)

        return this
    }

    override fun alias(entity: Entity<T>): Joinable {
        builder.alias(entity)

        return this
    }

    override fun toJoin(): Join {
        return builder.build()
    }

    override fun toFrom(): From {
        return builder.build()
    }
}
