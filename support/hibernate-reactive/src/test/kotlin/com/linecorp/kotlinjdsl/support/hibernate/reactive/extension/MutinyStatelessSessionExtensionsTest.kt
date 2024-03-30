package com.linecorp.kotlinjdsl.support.hibernate.reactive.extension

import com.linecorp.kotlinjdsl.querymodel.jpql.delete.DeleteQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.update.UpdateQuery
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.support.hibernate.reactive.JpqlMutinyStatelessSessionUtils
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockkObject
import io.mockk.verifySequence
import org.assertj.core.api.WithAssertions
import org.hibernate.reactive.mutiny.Mutiny
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.reflect.KClass

@ExtendWith(MockKExtension::class)
class MutinyStatelessSessionExtensionsTest : WithAssertions {
    @MockK
    private lateinit var session: Mutiny.StatelessSession

    @MockK
    private lateinit var context: RenderContext

    @MockK
    private lateinit var selectQuery1: SelectQuery<String>

    @MockK
    private lateinit var updateQuery1: UpdateQuery<String>

    @MockK
    private lateinit var deleteQuery1: DeleteQuery<String>

    @MockK
    private lateinit var query1: Mutiny.Query<String>

    @MockK
    private lateinit var queryParams1: Map<String, Any?>

    @BeforeEach
    fun setUp() {
        mockkObject(JpqlMutinyStatelessSessionUtils)
    }

    @Test
    fun `createQuery() with a select query`() {
        // given
        every {
            JpqlMutinyStatelessSessionUtils.createQuery(any(), any<SelectQuery<String>>(), any<KClass<*>>(), any())
        } returns query1
        every { selectQuery1.returnType } returns String::class

        // when
        val actual = session.createQuery(selectQuery1, context)

        // then
        assertThat(actual).isEqualTo(query1)

        verifySequence {
            selectQuery1.returnType
            JpqlMutinyStatelessSessionUtils.createQuery(session, selectQuery1, String::class, context)
        }
    }

    @Test
    fun `createQuery() with a select query and query params`() {
        // given
        every {
            JpqlMutinyStatelessSessionUtils
                .createQuery(any(), any<SelectQuery<String>>(), any(), any<KClass<*>>(), any())
        } returns query1
        every { selectQuery1.returnType } returns String::class

        // when
        val actual = session.createQuery(selectQuery1, queryParams1, context)

        // then
        assertThat(actual).isEqualTo(query1)

        verifySequence {
            selectQuery1.returnType
            JpqlMutinyStatelessSessionUtils.createQuery(session, selectQuery1, queryParams1, String::class, context)
        }
    }

    @Test
    fun `createQuery() with an update query`() {
        // given
        every {
            JpqlMutinyStatelessSessionUtils.createQuery<String>(any(), any<UpdateQuery<String>>(), any())
        } returns query1

        // when
        val actual = session.createQuery(updateQuery1, context)

        // then
        assertThat(actual).isEqualTo(query1)

        verifySequence {
            JpqlMutinyStatelessSessionUtils.createQuery<String>(session, updateQuery1, context)
        }
    }

    @Test
    fun `createQuery() with an update query and query params`() {
        // given
        every {
            JpqlMutinyStatelessSessionUtils.createQuery<String>(
                any(), any<UpdateQuery<String>>(), any<Map<String, Any?>>(), any(),
            )
        } returns query1

        // when
        val actual = session.createQuery(updateQuery1, queryParams1, context)

        // then
        assertThat(actual).isEqualTo(query1)

        verifySequence {
            JpqlMutinyStatelessSessionUtils.createQuery<String>(session, updateQuery1, queryParams1, context)
        }
    }

    @Test
    fun `createQuery() with a delete query`() {
        // given
        every {
            JpqlMutinyStatelessSessionUtils.createQuery<String>(any(), any<DeleteQuery<String>>(), any())
        } returns query1

        // when
        val actual = session.createQuery(deleteQuery1, context)

        // then
        assertThat(actual).isEqualTo(query1)

        verifySequence {
            JpqlMutinyStatelessSessionUtils.createQuery<String>(session, deleteQuery1, context)
        }
    }

    @Test
    fun `createQuery() with a delete query and query params`() {
        // given
        every {
            JpqlMutinyStatelessSessionUtils.createQuery<String>(
                any(), any<DeleteQuery<String>>(), any<Map<String, Any?>>(), any(),
            )
        } returns query1

        // when
        val actual = session.createQuery(deleteQuery1, queryParams1, context)

        // then
        assertThat(actual).isEqualTo(query1)

        verifySequence {
            JpqlMutinyStatelessSessionUtils.createQuery<String>(session, deleteQuery1, queryParams1, context)
        }
    }
}
