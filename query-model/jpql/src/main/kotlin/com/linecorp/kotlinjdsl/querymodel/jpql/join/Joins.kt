package com.linecorp.kotlinjdsl.querymodel.jpql.join

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entity
import com.linecorp.kotlinjdsl.querymodel.jpql.join.impl.*
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Path
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicate

object Joins {
    @SinceJdsl("3.0.0")
    fun <T : Any> innerJoin(
        entity: Entity<T>,
        predicate: Predicate,
    ): Join {
        return JpqlInnerJoin(entity, predicate)
    }

    @SinceJdsl("3.0.0")
    fun <T : Any> innerJoin(
        entity: Entity<T>,
        association: Path<*>,
        predicate: Predicate?,
    ): Join {
        return JpqlInnerAssociationJoin(entity, association, predicate)
    }

    @SinceJdsl("3.0.0")
    fun <T : Any> leftJoin(
        entity: Entity<T>,
        predicate: Predicate,
    ): Join {
        return JpqlLeftJoin(entity, predicate)
    }

    @SinceJdsl("3.0.0")
    fun <T : Any> leftJoin(
        entity: Entity<T>,
        association: Path<*>,
        predicate: Predicate?,
    ): Join {
        return JpqlLeftAssociationJoin(entity, association, predicate)
    }

    @SinceJdsl("3.0.0")
    fun <T : Any> innerFetchJoin(
        entity: Entity<T>,
        predicate: Predicate,
    ): Join {
        return JpqlInnerFetchJoin(entity, predicate)
    }

    @SinceJdsl("3.0.0")
    fun <T : Any> innerFetchJoin(
        entity: Entity<T>,
        association: Path<*>,
        predicate: Predicate?,
    ): Join {
        return JpqlInnerAssociationFetchJoin(entity, association, predicate)
    }

    @SinceJdsl("3.0.0")
    fun <T : Any> leftFetchJoin(
        entity: Entity<T>,
        predicate: Predicate,
    ): Join {
        return JpqlLeftFetchJoin(entity, predicate)
    }

    @SinceJdsl("3.0.0")
    fun <T : Any> leftFetchJoin(
        entity: Entity<T>,
        association: Path<*>,
        predicate: Predicate?,
    ): Join {
        return JpqlLeftAssociationFetchJoin(entity, association, predicate)
    }
}
