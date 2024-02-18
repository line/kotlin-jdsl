package com.linecorp.kotlinjdsl.support.spring.data.jpa.repository

import com.linecorp.kotlinjdsl.dsl.jpql.Jpql
import com.linecorp.kotlinjdsl.dsl.jpql.JpqlDsl
import com.linecorp.kotlinjdsl.querymodel.jpql.JpqlQueryable
import com.linecorp.kotlinjdsl.querymodel.jpql.delete.DeleteQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.update.UpdateQuery
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.support.spring.data.jpa.JpqlEntityManagerUtils
import io.mockk.every
import io.mockk.excludeRecords
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.verifySequence
import jakarta.persistence.EntityManager
import jakarta.persistence.Query
import jakarta.persistence.TypedQuery
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Slice

@ExtendWith(MockKExtension::class)
class KotlinJdslJpqlExecutorImplTest : WithAssertions {
    @InjectMockKs
    private lateinit var sut: KotlinJdslJpqlExecutorImpl

    @MockK
    private lateinit var entityManager: EntityManager

    @MockK
    private lateinit var renderContext: RenderContext

    @MockK
    private lateinit var createSelectQuery1: Jpql.() -> JpqlQueryable<SelectQuery<String>>

    @MockK
    private lateinit var createSelectQuery2: MyJpql.() -> JpqlQueryable<SelectQuery<String>>

    @MockK
    private lateinit var createSelectQuery3: MyJpqlObject.() -> JpqlQueryable<SelectQuery<String>>

    @MockK
    private lateinit var createUpdateQuery1: Jpql.() -> JpqlQueryable<UpdateQuery<String>>

    @MockK
    private lateinit var createUpdateQuery2: MyJpql.() -> JpqlQueryable<UpdateQuery<String>>

    @MockK
    private lateinit var createUpdateQuery3: MyJpqlObject.() -> JpqlQueryable<UpdateQuery<String>>

    @MockK
    private lateinit var createDeleteQuery1: Jpql.() -> JpqlQueryable<DeleteQuery<String>>

    @MockK
    private lateinit var createDeleteQuery2: MyJpql.() -> JpqlQueryable<DeleteQuery<String>>

    @MockK
    private lateinit var createDeleteQuery3: MyJpqlObject.() -> JpqlQueryable<DeleteQuery<String>>

    @MockK
    private lateinit var selectQuery1: SelectQuery<String>

    @MockK
    private lateinit var selectQuery2: SelectQuery<String>

    @MockK
    private lateinit var selectQuery3: SelectQuery<String>

    @MockK
    private lateinit var updateQuery1: UpdateQuery<String>

    @MockK
    private lateinit var updateQuery2: UpdateQuery<String>

    @MockK
    private lateinit var updateQuery3: UpdateQuery<String>

    @MockK
    private lateinit var deleteQuery1: DeleteQuery<String>

    @MockK
    private lateinit var deleteQuery2: DeleteQuery<String>

    @MockK
    private lateinit var deleteQuery3: DeleteQuery<String>

    @MockK
    private lateinit var typedQuery1: TypedQuery<String>

    @MockK
    private lateinit var typedQuery2: TypedQuery<String>

    @MockK
    private lateinit var typedQuery3: TypedQuery<String>

    @MockK
    private lateinit var query1: Query

    private class MyJpql : Jpql() {
        companion object Constructor : JpqlDsl.Constructor<MyJpql> {
            override fun newInstance(): MyJpql = MyJpql()
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            return javaClass == other?.javaClass
        }

        override fun hashCode(): Int {
            return javaClass.hashCode()
        }
    }

    private object MyJpqlObject : Jpql() {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            return javaClass == other?.javaClass
        }

        override fun hashCode(): Int {
            return javaClass.hashCode()
        }
    }

    @BeforeEach
    fun setUp() {
        mockkObject(JpqlEntityManagerUtils)

        every { createSelectQuery1.invoke(any()) } returns selectQuery1
        every { createSelectQuery2.invoke(any()) } returns selectQuery2
        every { createSelectQuery3.invoke(any()) } returns selectQuery3
        every { createUpdateQuery1.invoke(any()) } returns updateQuery1
        every { createUpdateQuery2.invoke(any()) } returns updateQuery2
        every { createUpdateQuery3.invoke(any()) } returns updateQuery3
        every { createDeleteQuery1.invoke(any()) } returns deleteQuery1
        every { createDeleteQuery2.invoke(any()) } returns deleteQuery2
        every { createDeleteQuery3.invoke(any()) } returns deleteQuery3
        every { selectQuery1.toQuery() } returns selectQuery1
        every { selectQuery2.toQuery() } returns selectQuery2
        every { selectQuery3.toQuery() } returns selectQuery3
        every { updateQuery1.toQuery() } returns updateQuery1
        every { updateQuery2.toQuery() } returns updateQuery2
        every { updateQuery3.toQuery() } returns updateQuery3
        every { deleteQuery1.toQuery() } returns deleteQuery1
        every { deleteQuery2.toQuery() } returns deleteQuery2
        every { deleteQuery3.toQuery() } returns deleteQuery3

        excludeRecords { createSelectQuery1.invoke(any()) }
        excludeRecords { createSelectQuery2.invoke(any()) }
        excludeRecords { createSelectQuery3.invoke(any()) }
        excludeRecords { createUpdateQuery1.invoke(any()) }
        excludeRecords { createUpdateQuery2.invoke(any()) }
        excludeRecords { createUpdateQuery3.invoke(any()) }
        excludeRecords { createDeleteQuery1.invoke(any()) }
        excludeRecords { createDeleteQuery2.invoke(any()) }
        excludeRecords { createDeleteQuery3.invoke(any()) }
        excludeRecords { selectQuery1.toQuery() }
        excludeRecords { selectQuery2.toQuery() }
        excludeRecords { selectQuery3.toQuery() }
        excludeRecords { updateQuery1.toQuery() }
        excludeRecords { updateQuery2.toQuery() }
        excludeRecords { updateQuery3.toQuery() }
        excludeRecords { deleteQuery1.toQuery() }
        excludeRecords { deleteQuery2.toQuery() }
        excludeRecords { deleteQuery3.toQuery() }
    }

    @Test
    fun findAll() {
        // given
        val list1 = listOf("1", "2", "3")

        every { JpqlEntityManagerUtils.createQuery(any(), any<SelectQuery<String>>(), any()) } returns typedQuery1
        every { typedQuery1.resultList } returns list1

        // when
        val actual = sut.findAll(createSelectQuery1)

        // then
        assertThat(actual).isEqualTo(list1)

        verifySequence {
            JpqlEntityManagerUtils.createQuery(entityManager, selectQuery1, renderContext)
            typedQuery1.resultList
        }
    }

    @Test
    fun `findAll() with a dsl`() {
        // given
        val list1 = listOf("1", "2", "3")

        every { JpqlEntityManagerUtils.createQuery(any(), any<SelectQuery<String>>(), any()) } returns typedQuery2
        every { typedQuery2.resultList } returns list1

        // when
        val actual = sut.findAll(MyJpql, createSelectQuery2)

        // then
        assertThat(actual).isEqualTo(list1)

        verifySequence {
            JpqlEntityManagerUtils.createQuery(entityManager, selectQuery2, renderContext)
            typedQuery2.resultList
        }
    }

    @Test
    fun `findAll() with a dsl object`() {
        // given
        val list1 = listOf("1", "2", "3")

        every { JpqlEntityManagerUtils.createQuery(any(), any<SelectQuery<String>>(), any()) } returns typedQuery3
        every { typedQuery3.resultList } returns list1

        // when
        val actual = sut.findAll(MyJpqlObject, createSelectQuery3)

        // then
        assertThat(actual).isEqualTo(list1)

        verifySequence {
            JpqlEntityManagerUtils.createQuery(entityManager, selectQuery3, renderContext)
            typedQuery3.resultList
        }
    }

    @Test
    fun `findAll() with a pageable`() {
        // given
        val pageable1 = PageRequest.of(0, 10)
        val list1 = listOf("1", "2", "3")

        every { JpqlEntityManagerUtils.queryForList(any(), any<SelectQuery<String>>(), any(), any()) } returns list1

        // when
        val actual = sut.findAll(pageable1, createSelectQuery1)

        // then
        assertThat(actual).isEqualTo(list1)

        verifySequence {
            JpqlEntityManagerUtils.queryForList(entityManager, selectQuery1, pageable1, renderContext)
        }
    }

    @Test
    fun `findAll() with a dsl and a pageable`() {
        // given
        val pageable1 = PageRequest.of(0, 10)
        val list1 = listOf("1", "2", "3")

        every { JpqlEntityManagerUtils.queryForList(any(), any<SelectQuery<String>>(), any(), any()) } returns list1

        // when
        val actual = sut.findAll(MyJpql, pageable1, createSelectQuery2)

        // then
        assertThat(actual).isEqualTo(list1)

        verifySequence {
            JpqlEntityManagerUtils.queryForList(entityManager, selectQuery2, pageable1, renderContext)
        }
    }

    @Test
    fun `findAll() with a dsl object and a pageable`() {
        // given
        val pageable1 = PageRequest.of(0, 10)
        val list1 = listOf("1", "2", "3")

        every { JpqlEntityManagerUtils.queryForList(any(), any<SelectQuery<String>>(), any(), any()) } returns list1

        // when
        val actual = sut.findAll(MyJpqlObject, pageable1, createSelectQuery3)

        // then
        assertThat(actual).isEqualTo(list1)

        verifySequence {
            JpqlEntityManagerUtils.queryForList(entityManager, selectQuery3, pageable1, renderContext)
        }
    }

    @Test
    fun findPage() {
        // given
        val pageable1 = PageRequest.of(0, 10)
        val page1: Page<String?> = mockk()

        every { JpqlEntityManagerUtils.queryForPage(any(), any<SelectQuery<String>>(), any(), any()) } returns page1

        // when
        val actual = sut.findPage(pageable1, createSelectQuery1)

        // then
        assertThat(actual).isEqualTo(page1)

        verifySequence {
            JpqlEntityManagerUtils.queryForPage(entityManager, selectQuery1, pageable1, renderContext)
        }
    }

    @Test
    fun `findPage() with a dsl`() {
        // given
        val pageable1 = PageRequest.of(0, 10)
        val page1: Page<String?> = mockk()

        every { JpqlEntityManagerUtils.queryForPage(any(), any<SelectQuery<String>>(), any(), any()) } returns page1

        // when
        val actual = sut.findPage(MyJpql, pageable1, createSelectQuery2)

        // then
        assertThat(actual).isEqualTo(page1)

        verifySequence {
            JpqlEntityManagerUtils.queryForPage(entityManager, selectQuery2, pageable1, renderContext)
        }
    }

    @Test
    fun `findPage() with a dsl object`() {
        // given
        val pageable1 = PageRequest.of(0, 10)
        val page1: Page<String?> = mockk()

        every { JpqlEntityManagerUtils.queryForPage(any(), any<SelectQuery<String>>(), any(), any()) } returns page1

        // when
        val actual = sut.findPage(MyJpqlObject, pageable1, createSelectQuery3)

        // then
        assertThat(actual).isEqualTo(page1)

        verifySequence {
            JpqlEntityManagerUtils.queryForPage(entityManager, selectQuery3, pageable1, renderContext)
        }
    }

    @Test
    fun findSlice() {
        // given
        val pageable1 = PageRequest.of(0, 10)
        val slice: Slice<String?> = mockk()

        every { JpqlEntityManagerUtils.queryForSlice(any(), any<SelectQuery<String>>(), any(), any()) } returns slice

        // when
        val actual = sut.findSlice(pageable1, createSelectQuery1)

        // then
        assertThat(actual).isEqualTo(slice)

        verifySequence {
            JpqlEntityManagerUtils.queryForSlice(entityManager, selectQuery1, pageable1, renderContext)
        }
    }

    @Test
    fun `findSlice() with a dsl`() {
        // given
        val pageable1 = PageRequest.of(0, 10)
        val slice1: Slice<String?> = mockk()

        every { JpqlEntityManagerUtils.queryForSlice(any(), any<SelectQuery<String>>(), any(), any()) } returns slice1

        // when
        val actual = sut.findSlice(MyJpql, pageable1, createSelectQuery2)

        // then
        assertThat(actual).isEqualTo(slice1)

        verifySequence {
            JpqlEntityManagerUtils.queryForSlice(entityManager, selectQuery2, pageable1, renderContext)
        }
    }

    @Test
    fun `findSlice() with a dsl object`() {
        // given
        val pageable1 = PageRequest.of(0, 10)
        val slice1: Slice<String?> = mockk()

        every { JpqlEntityManagerUtils.queryForSlice(any(), any<SelectQuery<String>>(), any(), any()) } returns slice1

        // when
        val actual = sut.findSlice(MyJpqlObject, pageable1, createSelectQuery3)

        // then
        assertThat(actual).isEqualTo(slice1)

        verifySequence {
            JpqlEntityManagerUtils.queryForSlice(entityManager, selectQuery3, pageable1, renderContext)
        }
    }

    @Test
    fun update() {
        // given
        every { JpqlEntityManagerUtils.createQuery(any(), any<UpdateQuery<String>>(), any()) } returns query1
        every { query1.executeUpdate() } returns 1

        // when
        val actual = sut.update(createUpdateQuery1)

        // then
        assertThat(actual).isEqualTo(1)

        verifySequence {
            JpqlEntityManagerUtils.createQuery(entityManager, updateQuery1, renderContext)
        }
    }

    @Test
    fun `update() with a dsl`() {
        // given
        every { JpqlEntityManagerUtils.createQuery(any(), any<UpdateQuery<String>>(), any()) } returns query1
        every { query1.executeUpdate() } returns 1

        // when
        val actual = sut.update(MyJpql, createUpdateQuery2)

        // then
        assertThat(actual).isEqualTo(1)

        verifySequence {
            JpqlEntityManagerUtils.createQuery(entityManager, updateQuery2, renderContext)
        }
    }

    @Test
    fun `update() with a dsl object`() {
        // given
        every { JpqlEntityManagerUtils.createQuery(any(), any<UpdateQuery<String>>(), any()) } returns query1
        every { query1.executeUpdate() } returns 1

        // when
        val actual = sut.update(MyJpqlObject, createUpdateQuery3)

        // then
        assertThat(actual).isEqualTo(1)

        verifySequence {
            JpqlEntityManagerUtils.createQuery(entityManager, updateQuery3, renderContext)
        }
    }

    @Test
    fun delete() {
        // given
        every { JpqlEntityManagerUtils.createQuery(any(), any<DeleteQuery<String>>(), any()) } returns query1
        every { query1.executeUpdate() } returns 1

        // when
        val actual = sut.delete(createDeleteQuery1)

        // then
        assertThat(actual).isEqualTo(1)

        verifySequence {
            JpqlEntityManagerUtils.createQuery(entityManager, deleteQuery1, renderContext)
        }
    }

    @Test
    fun `delete() with a dsl`() {
        // given
        every { JpqlEntityManagerUtils.createQuery(any(), any<DeleteQuery<String>>(), any()) } returns query1
        every { query1.executeUpdate() } returns 1

        // when
        val actual = sut.delete(MyJpql, createDeleteQuery2)

        // then
        assertThat(actual).isEqualTo(1)

        verifySequence {
            JpqlEntityManagerUtils.createQuery(entityManager, deleteQuery2, renderContext)
        }
    }

    @Test
    fun `delete() with a dsl object`() {
        // given
        every { JpqlEntityManagerUtils.createQuery(any(), any<DeleteQuery<String>>(), any()) } returns query1
        every { query1.executeUpdate() } returns 1

        // when
        val actual = sut.delete(MyJpqlObject, createDeleteQuery3)

        // then
        assertThat(actual).isEqualTo(1)

        verifySequence {
            JpqlEntityManagerUtils.createQuery(entityManager, deleteQuery3, renderContext)
        }
    }
}
