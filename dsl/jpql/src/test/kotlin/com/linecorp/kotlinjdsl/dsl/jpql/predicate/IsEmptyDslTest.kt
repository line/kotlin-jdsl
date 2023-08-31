package com.linecorp.kotlinjdsl.dsl.jpql.predicate

import com.linecorp.kotlinjdsl.dsl.jpql.entity.book.Book
import com.linecorp.kotlinjdsl.dsl.jpql.queryPart
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicate
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicates
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

class IsEmptyDslTest : WithAssertions {
    private val path1 = Paths.path(Book::authors)

    @Test
    fun isEmpty() {
        // when
        val predicate = queryPart {
            path1.isEmpty()
        }

        val actual: Predicate = predicate // for type check

        // then
        val excepted = Predicates.isEmpty(
            path1,
        )

        assertThat(actual).isEqualTo(excepted)
    }

    @Test
    fun isNotEmpty() {
        // when
        val predicate = queryPart {
            path1.isNotEmpty()
        }

        val actual: Predicate = predicate // for type check

        // then
        val excepted = Predicates.isNotEmpty(
            path1,
        )

        assertThat(actual).isEqualTo(excepted)
    }
}
