package com.linecorp.kotlinjdsl.querymodel.jpql.predicate

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Subquery
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlNull
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Path
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.*

object Predicates {
    @SinceJdsl("3.0.0")
    fun not(predicate: Predicate): Predicate {
        return JpqlNot(predicate)
    }

    @SinceJdsl("3.0.0")
    fun and(predicates: Iterable<Predicate>): Predicate {
        val unwrapped = predicates.flatMap {
            when (it) {
                is JpqlAnd -> it.predicates
                else -> listOf(it)
            }
        }

        return JpqlAnd(unwrapped)
    }

    @SinceJdsl("3.0.0")
    fun or(predicates: Iterable<Predicate>): Predicate {
        val unwrapped = predicates.flatMap {
            when (it) {
                is JpqlOr -> it.predicates
                else -> listOf(it)
            }
        }

        return JpqlOr(unwrapped)
    }

    @SinceJdsl("3.0.0")
    fun <T : Any> isNull(value: Expression<T>): Predicate {
        return JpqlIsNull(value)
    }

    @SinceJdsl("3.0.0")
    fun <T : Any> isNotNull(value: Expression<T>): Predicate {
        return JpqlIsNotNull(value.toExpression())
    }

    @SinceJdsl("3.0.0")
    fun <T : Any> equal(value: Expression<T>, compareValue: Expression<T>): Predicate {
        return if (compareValue is JpqlNull) {
            isNull(value)
        } else {
            JpqlEqual(value, compareValue)
        }
    }

    @SinceJdsl("3.0.0")
    fun <T : Any> equalAll(value: Expression<T>, subquery: Subquery<T>): Predicate {
        return JpqlEqualAll(value, subquery)
    }

    @SinceJdsl("3.0.0")
    fun <T : Any> equalAny(value: Expression<T>, subquery: Subquery<T>): Predicate {
        return JpqlEqualAny(value, subquery)
    }

    @SinceJdsl("3.0.0")
    fun <T : Any> notEqual(value: Expression<T>, compareValue: Expression<T>): Predicate {
        return if (compareValue is JpqlNull) {
            isNotNull(value)
        } else {
            JpqlNotEqual(value, compareValue)
        }
    }

    @SinceJdsl("3.0.0")
    fun <T : Any> notEqualAll(value: Expression<T>, subquery: Subquery<T>): Predicate {
        return JpqlNotEqualAll(value, subquery)
    }

    @SinceJdsl("3.0.0")
    fun <T : Any> notEqualAny(value: Expression<T>, subquery: Subquery<T>): Predicate {
        return JpqlNotEqualAny(value, subquery)
    }

    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> lessThan(value: Expression<T>, compareValue: Expression<T>): Predicate {
        return JpqlLessThan(value, compareValue)
    }

    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> lessThanAll(value: Expression<T>, subquery: Subquery<T>): Predicate {
        return JpqlLessThanAll(value, subquery)
    }

    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> lessThanAny(value: Expression<T>, subquery: Subquery<T>): Predicate {
        return JpqlLessThanAny(value, subquery)
    }

    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> lessThanOrEqualTo(value: Expression<T>, compareValue: Expression<T>): Predicate {
        return JpqlLessThanOrEqualTo(value, compareValue)
    }

    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> lessThanOrEqualToAll(value: Expression<T>, subquery: Subquery<T>): Predicate {
        return JpqlLessThanOrEqualToAll(value, subquery)
    }

    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> lessThanOrEqualToAny(value: Expression<T>, subquery: Subquery<T>): Predicate {
        return JpqlLessThanOrEqualToAny(value, subquery)
    }

    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> greaterThan(value: Expression<T>, compareValue: Expression<T>): Predicate {
        return JpqlGreaterThan(value, compareValue)
    }

    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> greaterThanAll(value: Expression<T>, subquery: Subquery<T>): Predicate {
        return JpqlGreaterThanAll(value, subquery)
    }

    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> greaterThanAny(value: Expression<T>, subquery: Subquery<T>): Predicate {
        return JpqlGreaterThanAny(value, subquery)
    }

    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> greaterThanOrEqualTo(value: Expression<T>, compareValue: Expression<T>): Predicate {
        return JpqlGreaterThanOrEqualTo(value, compareValue)
    }

    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> greaterThanOrEqualToAll(value: Expression<T>, subquery: Subquery<T>): Predicate {
        return JpqlGreaterThanOrEqualToAll(value, subquery)
    }

    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> greaterThanOrEqualToAny(value: Expression<T>, subquery: Subquery<T>): Predicate {
        return JpqlGreaterThanOrEqualToAny(value, subquery)
    }

    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> between(value: Expression<T>, min: Expression<T>, max: Expression<T>): Predicate {
        return JpqlBetween(value, min, max)
    }

    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> notBetween(value: Expression<T>, min: Expression<T>, max: Expression<T>): Predicate {
        return JpqlNotBetween(value, min, max)
    }

    @SinceJdsl("3.0.0")
    fun <T : Any> `in`(value: Expression<T>, compareValues: Iterable<Expression<T>>): Predicate {
        return JpqlIn(value, compareValues)
    }

    @SinceJdsl("3.0.0")
    fun <T : Any> `in`(value: Expression<T>, subquery: Subquery<T>): Predicate {
        return JpqlInSubquery(value, subquery)
    }

    @SinceJdsl("3.0.0")
    fun <T : Any> notIn(value: Expression<T>, compareValues: Iterable<Expression<T>>): Predicate {
        return JpqlNotIn(value, compareValues)
    }

    @SinceJdsl("3.0.0")
    fun <T : Any> notIn(value: Expression<T>, subquery: Subquery<T>): Predicate {
        return JpqlNotInSubquery(value, subquery)
    }

    @SinceJdsl("3.0.0")
    fun like(value: Expression<String>, pattern: Expression<String>, escape: Expression<Char>? = null): Predicate {
        return JpqlLike(value, pattern, escape)
    }

    @SinceJdsl("3.0.0")
    fun notLike(value: Expression<String>, pattern: Expression<String>, escape: Expression<Char>? = null): Predicate {
        return JpqlNotLike(value, pattern, escape)
    }

    @SinceJdsl("3.0.0")
    fun <T, S : Collection<T>> isEmpty(path: Path<S>): Predicate {
        return JpqlIsEmpty(path)
    }

    @SinceJdsl("3.0.0")
    fun <T, S : Collection<T>> isNotEmpty(path: Path<S>): Predicate {
        return JpqlIsNotEmpty(path)
    }

    @SinceJdsl("3.0.0")
    fun <T : Any> exists(subquery: Subquery<T>): Predicate {
        return JpqlExists(subquery)
    }

    @SinceJdsl("3.0.0")
    fun <T : Any> notExists(subquery: Subquery<T>): Predicate {
        return JpqlNotExists(subquery)
    }

    @SinceJdsl("3.0.0")
    fun parentheses(predicate: Predicate): Predicate {
        return JpqlPredicateParentheses(predicate)
    }
}
