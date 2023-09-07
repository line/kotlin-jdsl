package com.linecorp.kotlinjdsl.example.eclipselink.update

import com.linecorp.kotlinjdsl.dsl.jpql.jpql
import com.linecorp.kotlinjdsl.example.eclipselink.JpaEntityManagerFactoryTestUtils
import com.linecorp.kotlinjdsl.example.eclipselink.entity.author.Author
import com.linecorp.kotlinjdsl.example.eclipselink.jpql.JpqlRenderContextUtils
import com.linecorp.kotlinjdsl.example.eclipselink.withTransaction
import com.linecorp.kotlinjdsl.support.eclipselink.extension.createQuery
import org.assertj.core.api.WithAssertions
import org.eclipse.persistence.jpa.JpaEntityManager
import org.junit.jupiter.api.Test

class UpdateExample : WithAssertions {
    private val entityManagerFactory = JpaEntityManagerFactoryTestUtils.getJpaEntityManagerFactory()

    private val context = JpqlRenderContextUtils.getJpqlRenderContext()

    @Test
    fun `update author's name with id 1`() {
        // given
        val entityManger = entityManagerFactory.createEntityManager().unwrap(JpaEntityManager::class.java)
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

        // when
        val actual = entityManger.withTransaction {
            entityManger.createQuery(updateJpqlQuery, context).executeUpdate()
            entityManger.createQuery(selectJpqlQuery, context).setMaxResults(1).singleResult
        }

        // then
        assertThat(actual.name).isEqualTo("Author001")
    }
}
