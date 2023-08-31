package com.linecorp.kotlinjdsl.dsl.jpql.delete

import com.linecorp.kotlinjdsl.dsl.jpql.entity.book.Book
import com.linecorp.kotlinjdsl.dsl.jpql.queryPart
import com.linecorp.kotlinjdsl.querymodel.jpql.delete.DeleteQueries
import com.linecorp.kotlinjdsl.querymodel.jpql.delete.DeleteQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entities
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

class DeleteFromDslTest : WithAssertions {
    private val entity1 = Entities.entity(Book::class)

    @Test
    fun `deleteFrom() with an entity`() {
        // when
        val delete = queryPart {
            deleteFrom(
                entity1,
            )
        }.toQuery()

        val actual: DeleteQuery<Book> = delete // for type check

        // then
        val expected = DeleteQueries.deleteQuery(
            entity = Entities.entity(Book::class),
            where = null,
        )

        assertThat(actual).isEqualTo(expected)
    }
}
