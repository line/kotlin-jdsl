package com.linecorp.kotlinjdsl.querymodel.jpql

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressionable
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicatable
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicate
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlAnd
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlEqual
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlNotEqual
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlOr

object Predicates {
    @SinceJdsl("3.0.0")
    fun and(predicates: Collection<Predicatable?>): Predicate {
        return JpqlAnd(predicates.map { it?.toPredicate() })
    }

    @SinceJdsl("3.0.0")
    fun or(predicates: Collection<Predicatable?>): Predicate {
        return JpqlOr(predicates.map { it?.toPredicate() })
    }

    @SinceJdsl("3.0.0")
    fun <T> equal(left: Expressionable<T>, right: Expressionable<T>): Predicate {
        return JpqlEqual(left.toExpression(), right.toExpression())
    }

    @SinceJdsl("3.0.0")
    fun <T> notEqual(left: Expressionable<T>, right: Expressionable<T>): Predicate {
        return JpqlNotEqual(left.toExpression(), right.toExpression())
    }
}
