package com.linecorp.kotlinjdsl.querymodel.jpql.join

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entity
import com.linecorp.kotlinjdsl.querymodel.jpql.join.impl.JpqlInnerAssociationFetchJoin
import com.linecorp.kotlinjdsl.querymodel.jpql.join.impl.JpqlInnerAssociationJoin
import com.linecorp.kotlinjdsl.querymodel.jpql.join.impl.JpqlInnerFetchJoin
import com.linecorp.kotlinjdsl.querymodel.jpql.join.impl.JpqlInnerJoin
import com.linecorp.kotlinjdsl.querymodel.jpql.join.impl.JpqlLeftAssociationFetchJoin
import com.linecorp.kotlinjdsl.querymodel.jpql.join.impl.JpqlLeftAssociationJoin
import com.linecorp.kotlinjdsl.querymodel.jpql.join.impl.JpqlLeftFetchJoin
import com.linecorp.kotlinjdsl.querymodel.jpql.join.impl.JpqlLeftJoin
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Path
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicate

@SinceJdsl("3.0.0")
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
        predicate: Predicate? = null,
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
        predicate: Predicate? = null,
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
        predicate: Predicate? = null,
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
        predicate: Predicate? = null,
    ): Join {
        return JpqlLeftAssociationFetchJoin(entity, association, predicate)
    }
}
