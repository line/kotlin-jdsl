package com.linecorp.kotlinjdsl.querymodel.jpql.sort

import com.linecorp.kotlinjdsl.querymodel.jpql.entity.book.Book
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import com.linecorp.kotlinjdsl.querymodel.jpql.sort.impl.JpqlSort
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

class SortsTest : WithAssertions {
    private val expression1 = Paths.path(Book::isbn)

    @ParameterizedTest
    @EnumSource(Sort.NullOrder::class)
    fun asc(nullOrder: Sort.NullOrder) {
        // when
        val actual = Sorts.asc(
            expr = expression1,
            nullOrder = nullOrder,
        )

        // then
        val expected = JpqlSort(
            expr = expression1,
            order = Sort.Order.ASC,
            nullOrder = nullOrder,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest
    @EnumSource(Sort.NullOrder::class)
    fun desc(nullOrder: Sort.NullOrder) {
        // when
        val actual = Sorts.desc(
            expr = expression1,
            nullOrder = nullOrder,
        )

        // then
        val expected = JpqlSort(
            expr = expression1,
            order = Sort.Order.DESC,
            nullOrder = nullOrder,
        )

        assertThat(actual).isEqualTo(expected)
    }
}
