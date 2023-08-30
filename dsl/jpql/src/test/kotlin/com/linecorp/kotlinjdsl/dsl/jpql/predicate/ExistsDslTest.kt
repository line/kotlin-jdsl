package com.linecorp.kotlinjdsl.dsl.jpql.predicate

import com.linecorp.kotlinjdsl.dsl.jpql.entity.book.Book
import com.linecorp.kotlinjdsl.dsl.jpql.entity.book.Isbn
import com.linecorp.kotlinjdsl.dsl.jpql.queryPart
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entities
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicate
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicates
import com.linecorp.kotlinjdsl.querymodel.jpql.select.Selects
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

class ExistsDslTest : WithAssertions {
    private val subquery1 = Expressions.subquery(
        Selects.select(
            returnType = Isbn::class,
            distinct = false,
            select = listOf(Paths.path(Book::isbn)),
            from = listOf(Entities.entity(Book::class)),
        ),
    )

    @Test
    fun exists() {
        // when
        val predicate = queryPart {
            exists(subquery1)
        }

        val actual: Predicate = predicate // for type check

        // then
        val excepted = Predicates.exists(
            subquery1,
        )

        assertThat(actual).isEqualTo(excepted)
    }

    @Test
    fun notExists() {
        // when
        val predicate = queryPart {
            notExists(subquery1)
        }

        val actual: Predicate = predicate // for type check

        // then
        val excepted = Predicates.notExists(
            subquery1,
        )

        assertThat(actual).isEqualTo(excepted)
    }
}
