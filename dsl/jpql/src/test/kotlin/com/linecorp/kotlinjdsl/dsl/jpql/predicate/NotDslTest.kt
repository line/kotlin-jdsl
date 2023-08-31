package com.linecorp.kotlinjdsl.dsl.jpql.predicate

import com.linecorp.kotlinjdsl.dsl.jpql.entity.book.Book
import com.linecorp.kotlinjdsl.dsl.jpql.queryPart
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicate
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicates
import java.math.BigDecimal
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

class NotDslTest : WithAssertions {
    private val predicate1 = Predicates.equal(
        Paths.path(Book::price),
        Expressions.value(BigDecimal.valueOf(100)),
    )

    @Test
    fun not() {
        // when
        val predicate = queryPart {
            not(predicate1)
        }

        val actual: Predicate = predicate // for type check

        // then
        val excepted = Predicates.not(
            predicate1,
        )

        assertThat(actual).isEqualTo(excepted)
    }
}
