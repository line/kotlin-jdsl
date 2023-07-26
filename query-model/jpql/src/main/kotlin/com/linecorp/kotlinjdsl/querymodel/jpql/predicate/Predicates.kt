package com.linecorp.kotlinjdsl.querymodel.jpql.predicate

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressionable
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Subquery
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlNull
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Path
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
    fun <T : Any> isNull(value: Expressionable<T>): Predicate {
        return JpqlIsNull(value.toExpression())
    }

    @SinceJdsl("3.0.0")
    fun <T : Any> isNotNull(value: Expressionable<T>): Predicate {
        return JpqlIsNotNull(value.toExpression())
    }

    @SinceJdsl("3.0.0")
    fun <T : Any> equal(value: Expressionable<T>, compareValue: Expressionable<T>): Predicate {
        val casedCompareValue = compareValue.toExpression() as Expression<*>

        return if (casedCompareValue is JpqlNull) {
            isNull(value)
        } else {
            JpqlEqual(value.toExpression(), casedCompareValue)
        }
    }

    @SinceJdsl("3.0.0")
    fun <T : Any> notEqual(value: Expressionable<T>, compareValue: Expressionable<T>): Predicate {
        val casedCompareValue = compareValue.toExpression() as Expression<*>

        return if (casedCompareValue is JpqlNull) {
            isNotNull(value)
        } else {
            JpqlNotEqual(value.toExpression(), casedCompareValue)
        }
    }

    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> lessThan(
        value: Expressionable<T>,
        compareValue: Expressionable<T>,
    ): Predicate {
        return JpqlLessThan(
            value.toExpression(),
            compareValue.toExpression(),
        )
    }

    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> lessThanOrEqualTo(
        value: Expressionable<T>,
        compareValue: Expressionable<T>,
    ): Predicate {
        return JpqlLessThanOrEqualTo(
            value.toExpression(),
            compareValue.toExpression(),
        )
    }

    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> greaterThan(
        value: Expressionable<T>,
        compareValue: Expressionable<T>,
    ): Predicate {
        return JpqlGreaterThan(
            value.toExpression(),
            compareValue.toExpression(),
        )
    }

    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> greaterThanOrEqualTo(
        value: Expressionable<T>,
        compareValue: Expressionable<T>,
    ): Predicate {
        return JpqlGreaterThanOrEqualTo(
            value.toExpression(),
            compareValue.toExpression(),
        )
    }

    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> between(
        value: Expressionable<T>,
        min: Expressionable<T>,
        max: Expressionable<T>,
    ): Predicate {
        return JpqlBetween(
            value.toExpression(),
            min.toExpression(),
            max.toExpression(),
        )
    }

    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> notBetween(
        value: Expressionable<T>,
        min: Expressionable<T>,
        max: Expressionable<T>
    ): Predicate {
        return JpqlNotBetween(
            value.toExpression(),
            min.toExpression(),
            max.toExpression(),
        )
    }

    @SinceJdsl("3.0.0")
    fun <T : Any> `in`(value: Expressionable<T>, compareValues: Iterable<Expressionable<T>?>): Predicate {
        return JpqlIn(value.toExpression(), compareValues.map { it?.toExpression() })
    }

    @SinceJdsl("3.0.0")
    fun <T : Any> `in`(value: Expressionable<T>, subquery: Subquery<T>): Predicate {
        return JpqlInSubquery(value.toExpression(), subquery)
    }

    @SinceJdsl("3.0.0")
    fun <T : Any> notIn(value: Expressionable<T>, compareValues: Iterable<Expressionable<T>?>): Predicate {
        return JpqlNotIn(value.toExpression(), compareValues.map { it?.toExpression() })
    }

    @SinceJdsl("3.0.0")
    fun <T : Any> notIn(value: Expressionable<T>, subquery: Subquery<T>): Predicate {
        return JpqlNotInSubquery(value.toExpression(), subquery)
    }

    @SinceJdsl("3.0.0")
    fun like(
        value: Expressionable<String>,
        pattern: Expressionable<String>,
    ): Predicate {
        return JpqlLike(
            value = value.toExpression(),
            pattern = pattern.toExpression(),
        )
    }

    @SinceJdsl("3.0.0")
    fun notLike(
        value: Expressionable<String>,
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
    fun <T : Any> exists(
        subquery: Subquery<T>,
    ): Predicate {
        return JpqlExists(subquery)
    }

    @SinceJdsl("3.0.0")
    fun <T : Any> notExists(
        subquery: Subquery<T>,
    ): Predicate {
        return JpqlNotExists(subquery)
    }
}
