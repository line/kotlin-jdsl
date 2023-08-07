package com.linecorp.kotlinjdsl.querymodel.jpql.join.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entity
import com.linecorp.kotlinjdsl.querymodel.jpql.join.Join
import com.linecorp.kotlinjdsl.querymodel.jpql.join.JoinType
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Path
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicate

/**
 * Class that represents join using association.
 *
 * @property entity The result of association join. The alias of entity represents the alias of association join.
 * @property association The path used in association join.
 * @property on The additional condition of assoication join.
 */
@Internal
data class JpqlInnerAssociationJoin<T : Any> internal constructor(
    val entity: Entity<T>,
    val association: Path<*>,
    val on: Predicate?,
) : Join {
    override val joinType: JoinType = JoinType.INNER
    override val fetch: Boolean = false
}
