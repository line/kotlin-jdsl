package com.linecorp.kotlinjdsl.executor.spring.jpa.javax

import com.linecorp.kotlinjdsl.querymodel.jpql.delete.DeleteQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.update.UpdateQuery
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.support.spring.data.jpa.javax.JpqlEntityManagerUtils
import com.linecorp.kotlinjdsl.support.spring.data.jpa.javax.extension.createQuery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockkObject
import io.mockk.verifySequence
import javax.persistence.EntityManager
import javax.persistence.Query
import javax.persistence.TypedQuery
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class EntityManagerExtensionsTest : WithAssertions {
    @MockK
    private lateinit var entityManager: EntityManager

    @MockK
    private lateinit var selectQuery: SelectQuery<String>

    @MockK
    private lateinit var updateQuery: UpdateQuery<String>

    @MockK
    private lateinit var deleteQuery: DeleteQuery<String>

    @MockK
    private lateinit var queryParams: Map<String, Any?>

    @MockK
    private lateinit var query: Query

    @MockK
    private lateinit var typedQuery: TypedQuery<String>

    @MockK
    private lateinit var context: RenderContext

    @BeforeEach
    fun setUp() {
        mockkObject(JpqlEntityManagerUtils)
    }

    @Test
    fun `createQuery - select query`() {
        // given
        every { JpqlEntityManagerUtils.createQuery(any(), any<SelectQuery<String>>(), any()) } returns typedQuery

        // when
        val actual = entityManager.createQuery(selectQuery, context)

        // then
        assertThat(actual).isEqualTo(typedQuery)

        verifySequence {
            JpqlEntityManagerUtils.createQuery(entityManager, selectQuery, context)
        }
    }

    @Test
    fun `createQuery - select query with query params`() {
        // given
        every { JpqlEntityManagerUtils.createQuery(any(), any<SelectQuery<String>>(), any(), any()) } returns typedQuery

        // when
        val actual = entityManager.createQuery(selectQuery, queryParams, context)

        // then
        assertThat(actual).isEqualTo(typedQuery)

        verifySequence {
            JpqlEntityManagerUtils.createQuery(entityManager, selectQuery, queryParams, context)
        }
    }

    @Test
    fun `createQuery - update query`() {
        // given
        every { JpqlEntityManagerUtils.createQuery(any(), any<UpdateQuery<String>>(), any()) } returns query

        // when
        val actual = entityManager.createQuery(updateQuery, context)

        // then
        assertThat(actual).isEqualTo(query)

        verifySequence {
            JpqlEntityManagerUtils.createQuery(entityManager, updateQuery, context)
        }
    }

    @Test
    fun `createQuery - update query with query params`() {
        // given
        every { JpqlEntityManagerUtils.createQuery(any(), any<UpdateQuery<String>>(), any(), any()) } returns query

        // when
        val actual = entityManager.createQuery(updateQuery, queryParams, context)

        // then
        assertThat(actual).isEqualTo(query)

        verifySequence {
            JpqlEntityManagerUtils.createQuery(entityManager, updateQuery, queryParams, context)
        }
    }

    @Test
    fun `createQuery - delete query`() {
        // given
        every { JpqlEntityManagerUtils.createQuery(any(), any<DeleteQuery<String>>(), any()) } returns query

        // when
        val actual = entityManager.createQuery(deleteQuery, context)

        // then
        assertThat(actual).isEqualTo(query)

        verifySequence {
            JpqlEntityManagerUtils.createQuery(entityManager, deleteQuery, context)
        }
    }

    @Test
    fun `createQuery - delete query with query params`() {
        // given
        every { JpqlEntityManagerUtils.createQuery(any(), any<DeleteQuery<String>>(), any(), any()) } returns query

        // when
        val actual = entityManager.createQuery(deleteQuery, queryParams, context)

        // then
        assertThat(actual).isEqualTo(query)

        verifySequence {
            JpqlEntityManagerUtils.createQuery(entityManager, deleteQuery, queryParams, context)
        }
    }
}
