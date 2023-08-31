package com.linecorp.kotlinjdsl.dsl.jpql.sort

import com.linecorp.kotlinjdsl.dsl.jpql.entity.book.Book
import com.linecorp.kotlinjdsl.dsl.jpql.queryPart
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import com.linecorp.kotlinjdsl.querymodel.jpql.sort.Sort
import com.linecorp.kotlinjdsl.querymodel.jpql.sort.Sorts
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

class SortDslTest : WithAssertions {
    private val expression1 = Paths.path(Book::isbn)

    @Test
    fun asc() {
        // when
        val sort = queryPart {
            expression1.asc()
        }.toSort()

        val actual: Sort = sort // for type check

        // then
        val expected = Sorts.asc(
            expr = expression1,
            nullOrder = null,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `asc() and nullsFirst()`() {
        // when
        val sort = queryPart {
            expression1.asc().nullsFirst()
        }.toSort()

        val actual: Sort = sort // for type check

        // then
        val expected = Sorts.asc(
            expr = expression1,
            nullOrder = Sort.NullOrder.FIRST,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `asc() and nullsLast()`() {
        // when
        val sort = queryPart {
            expression1.asc().nullsLast()
        }.toSort()

        val actual: Sort = sort // for type check

        // then
        val expected = Sorts.asc(
            expr = expression1,
            nullOrder = Sort.NullOrder.LAST,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun desc() {
        // when
        val sort = queryPart {
            expression1.desc()
        }.toSort()

        val actual: Sort = sort // for type check

        // then
        val expected = Sorts.desc(
            expr = expression1,
            nullOrder = null,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `desc() and nullsFirst()`() {
        // when
        val sort = queryPart {
            expression1.desc().nullsFirst()
        }.toSort()

        val actual: Sort = sort // for type check

        // then
        val expected = Sorts.desc(
            expr = expression1,
            nullOrder = Sort.NullOrder.FIRST,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `desc() and nullsLast()`() {
        // when
        val sort = queryPart {
            expression1.desc().nullsLast()
        }.toSort()

        val actual: Sort = sort // for type check

        // then
        val expected = Sorts.desc(
            expr = expression1,
            nullOrder = Sort.NullOrder.LAST,
        )

        assertThat(actual).isEqualTo(expected)
    }
}
