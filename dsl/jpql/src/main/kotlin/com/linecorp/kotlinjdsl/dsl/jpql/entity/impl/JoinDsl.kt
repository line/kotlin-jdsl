package com.linecorp.kotlinjdsl.dsl.jpql.entity.impl

import com.linecorp.kotlinjdsl.dsl.jpql.entity.JoinOnStep
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entity
import com.linecorp.kotlinjdsl.querymodel.jpql.from.From
import com.linecorp.kotlinjdsl.querymodel.jpql.join.Join
import com.linecorp.kotlinjdsl.querymodel.jpql.join.JoinType
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Path
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicate

internal class JoinDsl private constructor(
    private val builder: JoinBuilder,
) : JoinOnStep {
    constructor(
        entity: Entity<*>,
        association: Path<*>?,
        joinType: JoinType,
        fetch: Boolean
    ) : this(
        JoinBuilder(
            entity = entity,
            association = association,
            joinType = joinType,
            fetch = fetch,
        ),
    )

    override fun on(predicate: Predicate): JoinOnStep {
        builder.on(predicate)

        return this
    }

    override fun toJoin(): Join {
        return builder.build()
    }

    override fun toFrom(): From {
        return builder.build()
    }
}
