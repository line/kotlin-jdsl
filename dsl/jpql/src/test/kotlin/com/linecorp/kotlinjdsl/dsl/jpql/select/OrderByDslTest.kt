package com.linecorp.kotlinjdsl.dsl.jpql.select

import com.linecorp.kotlinjdsl.dsl.jpql.entity.book.Book
import com.linecorp.kotlinjdsl.dsl.jpql.queryPart
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entities
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.select.Selects
import com.linecorp.kotlinjdsl.querymodel.jpql.sort.Sorts
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

class OrderByDslTest : WithAssertions {
    private val entity1 = Entities.entity(Book::class)

    private val expression1 = Paths.path(Book::title)

    private val sort1 = Sorts.asc(Paths.path(Book::title))
    private val sort2 = Sorts.desc(Paths.path(Book::isbn))

    @Test
    fun orderBy() {
        // when
        val select = queryPart {
            select(
                expression1,
            ).from(
                entity1,
            ).orderBy(
                sort1,
                null,
                sort2,
            )
        }.toQuery()

        val actual: SelectQuery<String> = select // for type check

        // then
        val expected = Selects.select(
            returnType = String::class,
            distinct = false,
            select = listOf(expression1),
            from = listOf(entity1),
            orderBy = listOf(sort1, sort2),
        )

        assertThat(actual).isEqualTo(expected)
    }
}
