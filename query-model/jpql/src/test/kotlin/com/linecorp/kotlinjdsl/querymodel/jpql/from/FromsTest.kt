package com.linecorp.kotlinjdsl.querymodel.jpql.from

import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entities
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.author.Author
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.book.Book
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.book.BookAuthor
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.book.BookPublisher
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.publisher.Publisher
import com.linecorp.kotlinjdsl.querymodel.jpql.from.impl.JpqlJoinedEntity
import com.linecorp.kotlinjdsl.querymodel.jpql.join.Joins
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

class FromsTest : WithAssertions {
    private val entity1 = Entities.entity(Book::class)
    private val entity2 = Entities.entity(Author::class)
    private val entity3 = Entities.entity(Publisher::class)

    private val join1 = Joins.innerJoin(Entities.entity(BookAuthor::class), Paths.path(Book::authors))
    private val join2 = Joins.innerJoin(Entities.entity(BookPublisher::class), Paths.path(Book::publisher))

    @Test
    fun reduce() {
        // when
        val actual = Froms.reduce(
            listOf(
                entity1,
                join1,
                join2,
                entity2,
                entity3,
            ),
        )

        // then
        assertThat(actual).isEqualTo(
            listOf(
                JpqlJoinedEntity(JpqlJoinedEntity(entity1, join1), join2),
                entity2,
                entity3,
            ),
        )
    }
}
