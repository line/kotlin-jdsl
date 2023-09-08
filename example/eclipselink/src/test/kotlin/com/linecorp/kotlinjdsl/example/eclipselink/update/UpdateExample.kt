package com.linecorp.kotlinjdsl.example.eclipselink.update

import com.linecorp.kotlinjdsl.dsl.jpql.jpql
import com.linecorp.kotlinjdsl.example.eclipselink.EntityManagerFactoryTestUtils
import com.linecorp.kotlinjdsl.example.eclipselink.entity.author.Author
import com.linecorp.kotlinjdsl.example.eclipselink.jpql.JpqlRenderContextUtils
import com.linecorp.kotlinjdsl.support.eclipselink.extension.createQuery
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

class UpdateExample : WithAssertions {
    private val entityManagerFactory = EntityManagerFactoryTestUtils.getEntityManagerFactory()
    private val entityManager = entityManagerFactory.createEntityManager()

    private val context = JpqlRenderContextUtils.getJpqlRenderContext()

    @AfterEach
    fun tearDown() {
        entityManager.close()
    }

    @Test
    fun `update author's name with id 1`() {
        // when
        val updateJpqlQuery = jpql {
            update(
                entity(Author::class),
            ).set(
                path(Author::name),
                "Author001",
            ).where(
                path(Author::authorId).eq(1L),
            )
        }

        val selectJpqlQuery = jpql {
            select(
                entity(Author::class),
            ).from(
                entity(Author::class),
            ).where(
                path(Author::authorId).eq(1L),
            )
        }

        val actual: Author

        entityManager.transaction.also {
            it.begin()

            entityManager.createQuery(updateJpqlQuery, context).executeUpdate()
            actual = entityManager.createQuery(selectJpqlQuery, context).setMaxResults(1).singleResult

            it.rollback()
        }

        // then
        assertThat(actual.name).isEqualTo("Author001")
    }
}
