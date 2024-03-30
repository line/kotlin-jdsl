package com.linecorp.kotlinjdsl.support.eclipselink.javax.extension

import com.linecorp.kotlinjdsl.querymodel.jpql.delete.DeleteQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.update.UpdateQuery
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.support.eclipselink.javax.JpqlEntityManagerUtils
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockkObject
import io.mockk.verifySequence
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import javax.persistence.EntityManager
import javax.persistence.Query
import javax.persistence.TypedQuery
import kotlin.reflect.KClass

@ExtendWith(MockKExtension::class)
class EntityManagerExtensionsTest : WithAssertions {
    @MockK
    private lateinit var entityManager: EntityManager

    @MockK
    private lateinit var context: RenderContext

    @MockK
    private lateinit var selectQuery1: SelectQuery<String>

    @MockK
    private lateinit var updateQuery1: UpdateQuery<String>

    @MockK
    private lateinit var deleteQuery1: DeleteQuery<String>

    @MockK
    private lateinit var query1: Query

    @MockK
    private lateinit var queryParams1: Map<String, Any?>

    @MockK
    private lateinit var typedQuery1: TypedQuery<String>

    @BeforeEach
    fun setUp() {
        mockkObject(JpqlEntityManagerUtils)
    }

    @Test
    fun `createQuery() with a select query`() {
        // given
        every {
            JpqlEntityManagerUtils.createQuery(any(), any<SelectQuery<String>>(), any<KClass<*>>(), any())
        } returns typedQuery1
        every { selectQuery1.returnType } returns String::class

        // when
        val actual = entityManager.createQuery(selectQuery1, context)

        // then
        assertThat(actual).isEqualTo(typedQuery1)

        verifySequence {
            selectQuery1.returnType
            JpqlEntityManagerUtils.createQuery(entityManager, selectQuery1, String::class, context)
        }
    }

    @Test
    fun `createQuery() with a select query and query params`() {
        // given
        every {
            JpqlEntityManagerUtils.createQuery(any(), any<SelectQuery<String>>(), any(), any<KClass<*>>(), any())
        } returns typedQuery1
        every { selectQuery1.returnType } returns String::class

        // when
        val actual = entityManager.createQuery(selectQuery1, queryParams1, context)

        // then
        assertThat(actual).isEqualTo(typedQuery1)

        verifySequence {
            selectQuery1.returnType
            JpqlEntityManagerUtils.createQuery(entityManager, selectQuery1, queryParams1, String::class, context)
        }
    }

    @Test
    fun `createQuery() with an update query`() {
        // given
        every {
            JpqlEntityManagerUtils.createQuery(any(), any<UpdateQuery<String>>(), any())
        } returns query1

        // when
        val actual = entityManager.createQuery(updateQuery1, context)

        // then
        assertThat(actual).isEqualTo(query1)

        verifySequence {
            JpqlEntityManagerUtils.createQuery(entityManager, updateQuery1, context)
        }
    }

    @Test
    fun `createQuery() with an update query and query params`() {
        // given
        every {
            JpqlEntityManagerUtils.createQuery(any(), any<UpdateQuery<String>>(), any(), any())
        } returns query1

        // when
        val actual = entityManager.createQuery(updateQuery1, queryParams1, context)

        // then
        assertThat(actual).isEqualTo(query1)

        verifySequence {
            JpqlEntityManagerUtils.createQuery(entityManager, updateQuery1, queryParams1, context)
        }
    }

    @Test
    fun `createQuery() with a delete query`() {
        // given
        every {
            JpqlEntityManagerUtils.createQuery(any(), any<DeleteQuery<String>>(), any())
        } returns query1

        // when
        val actual = entityManager.createQuery(deleteQuery1, context)

        // then
        assertThat(actual).isEqualTo(query1)

        verifySequence {
            JpqlEntityManagerUtils.createQuery(entityManager, deleteQuery1, context)
        }
    }

    @Test
    fun `createQuery() with a delete query and query params`() {
        // given
        every {
            JpqlEntityManagerUtils.createQuery(any(), any<DeleteQuery<String>>(), any(), any())
        } returns query1

        // when
        val actual = entityManager.createQuery(deleteQuery1, queryParams1, context)

        // then
        assertThat(actual).isEqualTo(query1)

        verifySequence {
            JpqlEntityManagerUtils.createQuery(entityManager, deleteQuery1, queryParams1, context)
        }
    }
}
