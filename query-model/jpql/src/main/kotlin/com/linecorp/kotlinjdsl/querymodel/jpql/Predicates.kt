package com.linecorp.kotlinjdsl.querymodel.jpql

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressionable
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Subquery
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlNull
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Path
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicatable
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicate
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.*

object Predicates {
    @SinceJdsl("3.0.0")
    fun not(predicate: Predicatable): Predicate {
        return JpqlNot(predicate.toPredicate())
    }

    @SinceJdsl("3.0.0")
    fun and(predicates: Iterable<Predicatable?>): Predicate {
        return JpqlAnd(predicates.map { it?.toPredicate() })
    }

    @SinceJdsl("3.0.0")
    fun or(predicates: Iterable<Predicatable?>): Predicate {
        return JpqlOr(predicates.map { it?.toPredicate() })
    }

    @SinceJdsl("3.0.0")
    fun <T> isNull(value: Expressionable<T>): Predicate {
        return JpqlIsNull(value.toExpression())
    }

    @SinceJdsl("3.0.0")
    fun <T> isNotNull(value: Expressionable<T>): Predicate {
        return JpqlIsNotNull(value.toExpression())
    }

    @SinceJdsl("3.0.0")
    fun <T, S1 : T?, S2 : T?> equal(value: Expressionable<in S1>, compareValue: Expressionable<in S2>): Predicate {
        val casedCompareValue = compareValue.toExpression() as Expression<*>

        return if (casedCompareValue is JpqlNull) {
            isNull(value)
        } else {
            JpqlEqual(value.toExpression(), casedCompareValue)
        }
    }

    @SinceJdsl("3.0.0")
    fun <T, S1 : T?, S2 : T?> notEqual(value: Expressionable<in S1>, compareValue: Expressionable<in S2>): Predicate {
        val casedCompareValue = compareValue.toExpression() as Expression<*>

        return if (casedCompareValue is JpqlNull) {
            isNotNull(value)
        } else {
            JpqlNotEqual(value.toExpression(), casedCompareValue)
        }
    }

    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>, S : T?> lessThan(
        value: Expressionable<in S>,
        compareValue: Expressionable<in S>,
    ): Predicate {
        return JpqlLessThan(
            value.toExpression(),
            compareValue.toExpression(),
        )
    }

    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>, S : T?> lessThanOrEqualTo(
        value: Expressionable<in S>,
        compareValue: Expressionable<in S>,
    ): Predicate {
        return JpqlLessThanOrEqualTo(
            value.toExpression(),
            compareValue.toExpression(),
        )
    }

    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>, S : T?> greaterThan(
        value: Expressionable<in S>,
        compareValue: Expressionable<in S>,
    ): Predicate {
        return JpqlGreaterThan(
            value.toExpression(),
            compareValue.toExpression(),
        )
    }

    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>, S : T?> greaterThanOrEqualTo(
        value: Expressionable<in S>,
        compareValue: Expressionable<in S>,
    ): Predicate {
        return JpqlGreaterThanOrEqualTo(
            value.toExpression(),
            compareValue.toExpression(),
        )
    }

    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>, S : T?> between(
        value: Expressionable<in S>,
        min: Expressionable<in S>,
        max: Expressionable<in S>,
    ): Predicate {
        return JpqlBetween(
            value.toExpression(),
            min.toExpression(),
            max.toExpression(),
        )
    }

    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>, S : T?> notBetween(
        value: Expressionable<in S>,
        min: Expressionable<in S>,
        max: Expressionable<in S>
    ): Predicate {
        return JpqlNotBetween(
            value.toExpression(),
            min.toExpression(),
            max.toExpression(),
        )
    }

    @SinceJdsl("3.0.0")
    fun <T> `in`(value: Expressionable<T>, compareValues: Iterable<Expressionable<T>>): Predicate {
        return JpqlIn(value.toExpression(), compareValues.map { it.toExpression() })
    }

    @SinceJdsl("3.0.0")
    fun <T> `in`(value: Expressionable<T>, subquery: Subquery<T>): Predicate {
        return JpqlInSubquery(value.toExpression(), subquery)
    }

    @SinceJdsl("3.0.0")
    fun <T> notIn(value: Expressionable<T>, compareValues: Iterable<Expressionable<T>>): Predicate {
        return JpqlNotIn(value.toExpression(), compareValues.map { it.toExpression() })
    }

    @SinceJdsl("3.0.0")
    fun <T> notIn(value: Expressionable<T>, subquery: Subquery<T>): Predicate {
        return JpqlNotInSubquery(value.toExpression(), subquery)
    }

    @SinceJdsl("3.0.0")
    fun <T : String?> like(
        value: Expressionable<T>,
        pattern: Expressionable<String>,
    ): Predicate {
        return JpqlLike(
            value = value.toExpression(),
            pattern = pattern.toExpression(),
        )
    }

    @SinceJdsl("3.0.0")
    fun <T : String?> notLike(
        value: Expressionable<T>,
        pattern: Expressionable<String>,
    ): Predicate {
        return JpqlNotLike(
            value = value.toExpression(),
            pattern = pattern.toExpression(),
        )
    }

    @SinceJdsl("3.0.0")
    fun <T, S : Collection<T>> isEmpty(
        path: Path<S>
    ): Predicate {
        return JpqlIsEmpty(path)
    }

    @SinceJdsl("3.0.0")
    fun <T, S : Collection<T>> isNotEmpty(
        path: Path<S>
    ): Predicate {
        return JpqlIsNotEmpty(path)
    }

    @SinceJdsl("3.0.0")
    fun <T> exists(
        subquery: Subquery<T>,
    ): Predicate {
        return JpqlExists(subquery)
    }

    @SinceJdsl("3.0.0")
    fun <T> notExists(
        subquery: Subquery<T>,
    ): Predicate {
        return JpqlNotExists(subquery)
    }
}
