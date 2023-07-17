package com.linecorp.kotlinjdsl.querymodel.jpql

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlField
import com.linecorp.kotlinjdsl.querymodel.jpql.path.impl.JpqlAliasedPath
import com.linecorp.kotlinjdsl.querymodel.jpql.path.impl.JpqlEntity
import com.linecorp.kotlinjdsl.querymodel.jpql.sort.Sort
import com.linecorp.kotlinjdsl.querymodel.jpql.sort.impl.JpqlSort
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

class SortsTest : WithAssertions {
    @Test
    fun `expression asc`() {
        // when
        val sort = Sorts.asc(
            Paths.path(TestTable1::int1),
        )

        val actual: Sort = sort // for type check

        // then
        val expected = JpqlSort(
            JpqlField<Int>(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::int1.name,
            ),
            Sort.Order.ASC,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `nullable expression asc`() {
        // when
        val sort = Sorts.asc(
            Paths.path(TestTable1::nullableInt1),
        )

        val actual: Sort = sort // for type check

        // then
        val expected = JpqlSort(
            JpqlField<Int>(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::nullableInt1.name,
            ),
            Sort.Order.ASC,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `expression desc`() {
        // when
        val sort = Sorts.desc(
            Paths.path(TestTable1::int1),
        )

        val actual: Sort = sort // for type check

        // then
        val expected = JpqlSort(
            JpqlField<Int>(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::int1.name,
            ),
            Sort.Order.DESC,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `nullable expression desc`() {
        // when
        val sort = Sorts.desc(
            Paths.path(TestTable1::nullableInt1),
        )

        val actual: Sort = sort // for type check

        // then
        val expected = JpqlSort(
            JpqlField<Int>(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::nullableInt1.name,
            ),
            Sort.Order.DESC,
        )

        assertThat(actual).isEqualTo(expected)
    }

    private class TestTable1 {
        val int1: Int = 1
        val nullableInt1: Int? = null
    }
}
