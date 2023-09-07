package com.linecorp.kotlinjdsl.example.eclipselink.delete

import com.linecorp.kotlinjdsl.dsl.jpql.jpql
import com.linecorp.kotlinjdsl.example.eclipselink.entity.author.Author
import com.linecorp.kotlinjdsl.example.eclipselink.entity.book.Book
import com.linecorp.kotlinjdsl.example.eclipselink.jpql.JpqlRenderContextUtils
import com.linecorp.kotlinjdsl.example.eclipselink.reader.MultipleLinesSqlCommandFileReader
import com.linecorp.kotlinjdsl.support.eclipselink.extension.createQuery
import jakarta.persistence.EntityManager
import jakarta.persistence.Persistence
import java.time.OffsetDateTime
import org.assertj.core.api.WithAssertions
import org.eclipse.persistence.jpa.JpaEntityManager
import org.junit.jupiter.api.Test

class DeleteExample : WithAssertions {
    private val entityManagerFactory =
        Persistence.createEntityManagerFactory(
            "example",
            mapOf(
                "jakarta.persistence.schema-generation.create-script-source" to
                    MultipleLinesSqlCommandFileReader("../src/main/resources/schema.sql"),
                "jakarta.persistence.schema-generation.drop-script-source" to
                    MultipleLinesSqlCommandFileReader("../src/main/resources/drop.sql"),
                "jakarta.persistence.sql-load-script-source" to
                    MultipleLinesSqlCommandFileReader("../src/main/resources/data.sql"),
            ),
        )

    private val context = JpqlRenderContextUtils.getJpqlRenderContext()

    @Test
    fun `delete author with id 2`() {
        // given
        val entityManger = entityManagerFactory.createEntityManager().unwrap(JpaEntityManager::class.java)
        val deleteJpqlQuery = jpql {
            deleteFrom(
                entity(Author::class),
            ).where(
                path(Author::authorId).eq(2L),
            )
        }
        val selectJpqlQuery = jpql {
            select(
                entity(Author::class),
            ).from(
                entity(Author::class),
            )
        }

        // when
        val actual = entityManger.withTransaction {
            entityManger.createQuery(deleteJpqlQuery, context).executeUpdate()
            entityManger.createQuery(selectJpqlQuery, context).resultList
        }

        // then
        assertThat(actual.map { it.authorId }).isEqualTo(
            listOf(
                1L,
                3L,
                4L,
            ),
        )
    }

    @Test
    fun `delete all books published after June 2023`() {
        // given
        val entityManger = entityManagerFactory.createEntityManager().unwrap(JpaEntityManager::class.java)
        val deleteJpqlQuery = jpql {
            deleteFrom(
                entity(Book::class),
            ).where(
                path(Book::publishDate).ge(OffsetDateTime.parse("2023-06-01T00:00:00+09:00")),
            )
        }
        val selectJpqlQuery = jpql {
            select(
                entity(Book::class),
            ).from(
                entity(Book::class),
            )
        }

        // when
        val actual = entityManger.withTransaction {
            entityManger.createQuery(deleteJpqlQuery, context).executeUpdate()
            entityManger.createQuery(selectJpqlQuery, context).resultList
        }

        // then
        assertThat(actual.map { it.isbn }).isEqualTo(
            listOf(
                "01",
                "02",
                "03",
                "04",
                "05",
            ),
        )
    }

    private fun <T> EntityManager.withTransaction(work: () -> T): T {
        val transaction = this.transaction
        transaction.begin()
        return try {
            work()
        } finally {
            transaction.rollback()
        }
    }
}
