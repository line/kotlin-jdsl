package com.linecorp.kotlinjdsl.dsl.jpql.join.impl

import com.linecorp.kotlinjdsl.dsl.jpql.join.AssociationJoinAsStep
import com.linecorp.kotlinjdsl.dsl.jpql.join.AssociationJoinOnStep
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entity
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entityable
import com.linecorp.kotlinjdsl.querymodel.jpql.from.From
import com.linecorp.kotlinjdsl.querymodel.jpql.join.Join
import com.linecorp.kotlinjdsl.querymodel.jpql.join.JoinType
import com.linecorp.kotlinjdsl.querymodel.jpql.join.Joinable
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Path
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicatable

@PublishedApi
internal data class AssociationFetchJoinDsl<T : Any>(
    private val builder: AssociationFetchJoinBuilder<T>
) : AssociationJoinOnStep<T>,
    AssociationJoinAsStep<T> {

    constructor(
        entity: Entity<T>,
        association: Path<*>,
        joinType: JoinType,
    ) : this(
        AssociationFetchJoinBuilder(
            entity,
            association,
            joinType,
        ),
    )

    override fun on(predicate: Predicatable): AssociationJoinAsStep<T> {
        builder.on(predicate.toPredicate())

        return this
    }

    override fun `as`(entity: Entityable<T>): Joinable {
        builder.alias(entity.toEntity())

        return this
    }

    override fun alias(entity: Entityable<T>): Joinable {
        builder.alias(entity.toEntity())

        return this
    }

    override fun toJoin(): Join {
        return builder.build()
    }

    override fun toFrom(): From {
        return builder.build()
    }
}

