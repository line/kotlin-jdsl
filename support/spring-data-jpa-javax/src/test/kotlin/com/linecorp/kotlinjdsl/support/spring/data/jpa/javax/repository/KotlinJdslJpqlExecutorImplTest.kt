package com.linecorp.kotlinjdsl.support.spring.data.jpa.javax.repository

import com.linecorp.kotlinjdsl.dsl.jpql.Jpql
import com.linecorp.kotlinjdsl.dsl.jpql.JpqlDsl
import com.linecorp.kotlinjdsl.querymodel.jpql.JpqlQueryable
import com.linecorp.kotlinjdsl.querymodel.jpql.delete.DeleteQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.update.UpdateQuery
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.support.spring.data.jpa.javax.EnhancedTypedQuery
import com.linecorp.kotlinjdsl.support.spring.data.jpa.javax.JpqlEntityManagerUtils
import io.mockk.every
import io.mockk.excludeRecords
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockkObject
import io.mockk.verifySequence
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.SliceImpl
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.support.CrudMethodMetadata
import org.springframework.data.jpa.repository.support.QueryHints
import org.springframework.data.support.PageableExecutionUtilsAdaptor
import java.util.function.BiConsumer
import java.util.function.LongSupplier
import java.util.stream.Stream
import javax.persistence.EntityManager
import javax.persistence.LockModeType
import javax.persistence.Query
import javax.persistence.TypedQuery
import kotlin.reflect.KClass

@ExtendWith(MockKExtension::class)
class KotlinJdslJpqlExecutorImplTest : WithAssertions {
    @InjectMockKs
    private lateinit var sut: KotlinJdslJpqlExecutorImpl

    @MockK
    private lateinit var entityManager: EntityManager

    @MockK
    private lateinit var renderContext: RenderContext

    @MockK
    private lateinit var metadata: CrudMethodMetadata

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
    private lateinit var stringTypedQuery1: TypedQuery<String>

    @MockK
    private lateinit var stringTypedQuery2: TypedQuery<String>

    @MockK
    private lateinit var stringTypedQuery3: TypedQuery<String>

    @MockK
    private lateinit var longTypedQuery1: TypedQuery<Long>

    @MockK
    private lateinit var longTypedQuery2: TypedQuery<Long>

    @MockK
    private lateinit var longTypedQuery3: TypedQuery<Long>

    @MockK
    private lateinit var query1: Query

    @MockK
    private lateinit var query2: Query

    @MockK
    private lateinit var query3: Query

    @MockK
    private lateinit var page1: Page<String>

    private val offset1 = 10

    private val limit1 = 20

    private val lockModeType1 = LockModeType.READ

    private val queryHint1 = "queryHintName1" to "queryHintValue1"
    private val queryHint2 = "queryHintName2" to "queryHintValue2"
    private val queryHint3 = "queryHintName3" to "queryHintValue3"

    private val queryHints1 = object : QueryHints {
        override fun withFetchGraphs(em: EntityManager): QueryHints = throw UnsupportedOperationException()
        override fun forCounts(): QueryHints = throw UnsupportedOperationException()

        override fun forEach(action: BiConsumer<String, Any>) {
            listOf(
                queryHint1,
                queryHint2,
                queryHint3,
            ).forEach { (name, value) ->
                action.accept(name, value)
            }
        }
    }

    private val queryHintForCount1 = "queryHintForCountName1" to "queryHintForCountValue1"
    private val queryHintForCount2 = "queryHintForCountName2" to "queryHintForCountValue2"
    private val queryHintForCount3 = "queryHintForCountName3" to "queryHintForCountValue3"

    private val queryHintsForCount1 = object : QueryHints {
        override fun withFetchGraphs(em: EntityManager): QueryHints = throw UnsupportedOperationException()
        override fun forCounts(): QueryHints = throw UnsupportedOperationException()

        override fun forEach(action: BiConsumer<String, Any>) {
            listOf(
                queryHintForCount1,
                queryHintForCount2,
                queryHintForCount3,
            ).forEach { (name, value) ->
                action.accept(name, value)
            }
        }
    }

    private val sort1 = Sort.by("property1")
    private val pageable1 = PageRequest.of(1, 10, sort1)

    private val list1 = listOf("1", "2", "3")
    private val stream1 = Stream.of("1", "2", "3")
    private val counts1 = listOf(1L, 1L, 1L)

    private val enhancedTypedQuery1: EnhancedTypedQuery<String> by lazy(LazyThreadSafetyMode.NONE) {
        EnhancedTypedQuery(stringTypedQuery1) { longTypedQuery1 }
    }
    private val enhancedTypedQuery2: EnhancedTypedQuery<String> by lazy(LazyThreadSafetyMode.NONE) {
        EnhancedTypedQuery(stringTypedQuery2) { longTypedQuery2 }
    }
    private val enhancedTypedQuery3: EnhancedTypedQuery<String> by lazy(LazyThreadSafetyMode.NONE) {
        EnhancedTypedQuery(stringTypedQuery3) { longTypedQuery3 }
    }

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
        mockkObject(PageableExecutionUtilsAdaptor)

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
        every { selectQuery1.returnType } returns String::class
        every {
            JpqlEntityManagerUtils.createQuery(any(), any<SelectQuery<String>>(), any<KClass<*>>(), any())
        } returns stringTypedQuery1
        every { stringTypedQuery1.setLockMode(any()) } returns stringTypedQuery1
        every { stringTypedQuery1.setHint(any(), any()) } returns stringTypedQuery1
        every { stringTypedQuery1.setFirstResult(any()) } returns stringTypedQuery1
        every { stringTypedQuery1.setMaxResults(any()) } returns stringTypedQuery1
        every { stringTypedQuery1.resultList } returns list1
        every { metadata.lockModeType } returns lockModeType1
        every { metadata.queryHints } returns queryHints1

        // when
        val actual = sut.findAll(offset1, limit1, createSelectQuery1)

        // then
        assertThat(actual).isEqualTo(list1)

        verifySequence {
            selectQuery1.returnType
            JpqlEntityManagerUtils.createQuery(entityManager, selectQuery1, String::class, renderContext)
            metadata.lockModeType
            stringTypedQuery1.setLockMode(lockModeType1)
            metadata.queryHints
            stringTypedQuery1.setHint(queryHint1.first, queryHint1.second)
            stringTypedQuery1.setHint(queryHint2.first, queryHint2.second)
            stringTypedQuery1.setHint(queryHint3.first, queryHint3.second)
            stringTypedQuery1.setFirstResult(offset1)
            stringTypedQuery1.setMaxResults(limit1)
            stringTypedQuery1.resultList
        }
    }

    @Test
    fun `findAll() with a dsl`() {
        // given
        every { selectQuery2.returnType } returns String::class
        every {
            JpqlEntityManagerUtils.createQuery(any(), any<SelectQuery<String>>(), any<KClass<*>>(), any())
        } returns stringTypedQuery2
        every { stringTypedQuery2.setLockMode(any()) } returns stringTypedQuery2
        every { stringTypedQuery2.setHint(any(), any()) } returns stringTypedQuery2
        every { stringTypedQuery2.setFirstResult(any()) } returns stringTypedQuery2
        every { stringTypedQuery2.setMaxResults(any()) } returns stringTypedQuery2
        every { stringTypedQuery2.resultList } returns list1
        every { metadata.lockModeType } returns lockModeType1
        every { metadata.queryHints } returns queryHints1

        // when
        val actual = sut.findAll(MyJpql, offset1, limit1, createSelectQuery2)

        // then
        assertThat(actual).isEqualTo(list1)

        verifySequence {
            selectQuery2.returnType
            JpqlEntityManagerUtils.createQuery(entityManager, selectQuery2, String::class, renderContext)
            metadata.lockModeType
            stringTypedQuery2.setLockMode(lockModeType1)
            metadata.queryHints
            stringTypedQuery2.setHint(queryHint1.first, queryHint1.second)
            stringTypedQuery2.setHint(queryHint2.first, queryHint2.second)
            stringTypedQuery2.setHint(queryHint3.first, queryHint3.second)
            stringTypedQuery2.setFirstResult(offset1)
            stringTypedQuery2.setMaxResults(limit1)
            stringTypedQuery2.resultList
        }
    }

    @Test
    fun `findAll() with a dsl object`() {
        // given
        every { selectQuery3.returnType } returns String::class
        every {
            JpqlEntityManagerUtils.createQuery(any(), any<SelectQuery<String>>(), any<KClass<*>>(), any())
        } returns stringTypedQuery3
        every { stringTypedQuery3.setLockMode(any()) } returns stringTypedQuery3
        every { stringTypedQuery3.setHint(any(), any()) } returns stringTypedQuery3
        every { stringTypedQuery3.setFirstResult(any()) } returns stringTypedQuery3
        every { stringTypedQuery3.setMaxResults(any()) } returns stringTypedQuery3
        every { stringTypedQuery3.resultList } returns list1
        every { metadata.lockModeType } returns lockModeType1
        every { metadata.queryHints } returns queryHints1

        // when
        val actual = sut.findAll(MyJpqlObject, offset1, limit1, createSelectQuery3)

        // then
        assertThat(actual).isEqualTo(list1)

        verifySequence {
            selectQuery3.returnType
            JpqlEntityManagerUtils.createQuery(entityManager, selectQuery3, String::class, renderContext)
            metadata.lockModeType
            stringTypedQuery3.setLockMode(lockModeType1)
            metadata.queryHints
            stringTypedQuery3.setHint(queryHint1.first, queryHint1.second)
            stringTypedQuery3.setHint(queryHint2.first, queryHint2.second)
            stringTypedQuery3.setHint(queryHint3.first, queryHint3.second)
            stringTypedQuery3.setFirstResult(offset1)
            stringTypedQuery3.setMaxResults(limit1)
            stringTypedQuery3.resultList
        }
    }

    @Test
    fun `findAll() with a pageable`() {
        // given
        every { selectQuery1.returnType } returns String::class
        every {
            JpqlEntityManagerUtils.createEnhancedQuery(
                any(), any<SelectQuery<String>>(), any<KClass<*>>(), any(), any(),
            )
        } returns enhancedTypedQuery1
        every { stringTypedQuery1.setLockMode(any()) } returns stringTypedQuery1
        every { stringTypedQuery1.setHint(any(), any()) } returns stringTypedQuery1
        every { stringTypedQuery1.setFirstResult(any()) } returns stringTypedQuery1
        every { stringTypedQuery1.setMaxResults(any()) } returns stringTypedQuery1
        every { stringTypedQuery1.resultList } returns list1
        every { metadata.lockModeType } returns lockModeType1
        every { metadata.queryHints } returns queryHints1

        // when
        val actual = sut.findAll(pageable1, createSelectQuery1)

        // then
        assertThat(actual).isEqualTo(list1)

        verifySequence {
            selectQuery1.returnType
            JpqlEntityManagerUtils.createEnhancedQuery(entityManager, selectQuery1, String::class, sort1, renderContext)
            metadata.lockModeType
            stringTypedQuery1.setLockMode(lockModeType1)
            metadata.queryHints
            stringTypedQuery1.setHint(queryHint1.first, queryHint1.second)
            stringTypedQuery1.setHint(queryHint2.first, queryHint2.second)
            stringTypedQuery1.setHint(queryHint3.first, queryHint3.second)
            stringTypedQuery1.firstResult = pageable1.offset.toInt()
            stringTypedQuery1.maxResults = pageable1.pageSize
            stringTypedQuery1.resultList
        }
    }

    @Test
    fun `findAll() with a dsl and a pageable`() {
        // given
        every { selectQuery2.returnType } returns String::class
        every {
            JpqlEntityManagerUtils.createEnhancedQuery(
                any(), any<SelectQuery<String>>(), any<KClass<*>>(), any(), any(),
            )
        } returns enhancedTypedQuery2
        every { stringTypedQuery2.setLockMode(any()) } returns stringTypedQuery2
        every { stringTypedQuery2.setHint(any(), any()) } returns stringTypedQuery2
        every { stringTypedQuery2.setFirstResult(any()) } returns stringTypedQuery2
        every { stringTypedQuery2.setMaxResults(any()) } returns stringTypedQuery2
        every { stringTypedQuery2.resultList } returns list1
        every { metadata.lockModeType } returns lockModeType1
        every { metadata.queryHints } returns queryHints1

        // when
        val actual = sut.findAll(MyJpql, pageable1, createSelectQuery2)

        // then
        assertThat(actual).isEqualTo(list1)

        verifySequence {
            selectQuery2.returnType
            JpqlEntityManagerUtils.createEnhancedQuery(entityManager, selectQuery2, String::class, sort1, renderContext)
            metadata.lockModeType
            stringTypedQuery2.setLockMode(lockModeType1)
            metadata.queryHints
            stringTypedQuery2.setHint(queryHint1.first, queryHint1.second)
            stringTypedQuery2.setHint(queryHint2.first, queryHint2.second)
            stringTypedQuery2.setHint(queryHint3.first, queryHint3.second)
            stringTypedQuery2.firstResult = pageable1.offset.toInt()
            stringTypedQuery2.maxResults = pageable1.pageSize
            stringTypedQuery2.resultList
        }
    }

    @Test
    fun `findAll() with a dsl object and a pageable`() {
        // given
        every { selectQuery3.returnType } returns String::class
        every {
            JpqlEntityManagerUtils.createEnhancedQuery(
                any(), any<SelectQuery<String>>(), any<KClass<*>>(), any(), any(),
            )
        } returns enhancedTypedQuery3
        every { stringTypedQuery3.setLockMode(any()) } returns stringTypedQuery3
        every { stringTypedQuery3.setHint(any(), any()) } returns stringTypedQuery3
        every { stringTypedQuery3.setFirstResult(any()) } returns stringTypedQuery3
        every { stringTypedQuery3.setMaxResults(any()) } returns stringTypedQuery3
        every { stringTypedQuery3.resultList } returns list1
        every { metadata.lockModeType } returns lockModeType1
        every { metadata.queryHints } returns queryHints1

        // when
        val actual = sut.findAll(MyJpqlObject, pageable1, createSelectQuery3)

        // then
        assertThat(actual).isEqualTo(list1)

        verifySequence {
            selectQuery3.returnType
            JpqlEntityManagerUtils.createEnhancedQuery(entityManager, selectQuery3, String::class, sort1, renderContext)
            metadata.lockModeType
            stringTypedQuery3.setLockMode(lockModeType1)
            metadata.queryHints
            stringTypedQuery3.setHint(queryHint1.first, queryHint1.second)
            stringTypedQuery3.setHint(queryHint2.first, queryHint2.second)
            stringTypedQuery3.setHint(queryHint3.first, queryHint3.second)
            stringTypedQuery3.firstResult = pageable1.offset.toInt()
            stringTypedQuery3.maxResults = pageable1.pageSize
            stringTypedQuery3.resultList
        }
    }

    @Test
    fun findPage() {
        // given
        every { selectQuery1.returnType } returns String::class
        every {
            JpqlEntityManagerUtils.createEnhancedQuery(
                any(), any<SelectQuery<String>>(), any<KClass<*>>(), any(), any(),
            )
        } returns enhancedTypedQuery1
        every { stringTypedQuery1.setLockMode(any()) } returns stringTypedQuery1
        every { stringTypedQuery1.setHint(any(), any()) } returns stringTypedQuery1
        every { stringTypedQuery1.setFirstResult(any()) } returns stringTypedQuery1
        every { stringTypedQuery1.setMaxResults(any()) } returns stringTypedQuery1
        every { stringTypedQuery1.resultList } returns list1
        every { longTypedQuery1.setHint(any(), any()) } returns longTypedQuery1
        every { longTypedQuery1.setFirstResult(any()) } returns longTypedQuery1
        every { longTypedQuery1.resultList } returns counts1
        every { metadata.lockModeType } returns lockModeType1
        every { metadata.queryHints } returns queryHints1
        every { metadata.queryHintsForCount } returns queryHintsForCount1
        every { PageableExecutionUtilsAdaptor.getPage<String>(any(), any(), any()) } answers {
            lastArg<LongSupplier>().asLong

            page1
        }

        // when
        val actual = sut.findPage(pageable1, createSelectQuery1)

        // then
        assertThat(actual).isEqualTo(page1)

        verifySequence {
            selectQuery1.returnType
            JpqlEntityManagerUtils.createEnhancedQuery(entityManager, selectQuery1, String::class, sort1, renderContext)
            metadata.lockModeType
            stringTypedQuery1.setLockMode(lockModeType1)
            metadata.queryHints
            stringTypedQuery1.setHint(queryHint1.first, queryHint1.second)
            stringTypedQuery1.setHint(queryHint2.first, queryHint2.second)
            stringTypedQuery1.setHint(queryHint3.first, queryHint3.second)
            stringTypedQuery1.firstResult = pageable1.offset.toInt()
            stringTypedQuery1.maxResults = pageable1.pageSize
            stringTypedQuery1.resultList
            PageableExecutionUtilsAdaptor.getPage(list1, pageable1, any())
            metadata.queryHintsForCount
            longTypedQuery1.setHint(queryHintForCount1.first, queryHintForCount1.second)
            longTypedQuery1.setHint(queryHintForCount2.first, queryHintForCount2.second)
            longTypedQuery1.setHint(queryHintForCount3.first, queryHintForCount3.second)
            longTypedQuery1.resultList
        }
    }

    @Test
    fun `findPage() with a dsl`() {
        // given
        every { selectQuery2.returnType } returns String::class
        every {
            JpqlEntityManagerUtils.createEnhancedQuery(
                any(), any<SelectQuery<String>>(), any<KClass<*>>(), any(), any(),
            )
        } returns enhancedTypedQuery2
        every { stringTypedQuery2.setLockMode(any()) } returns stringTypedQuery2
        every { stringTypedQuery2.setHint(any(), any()) } returns stringTypedQuery2
        every { stringTypedQuery2.setFirstResult(any()) } returns stringTypedQuery2
        every { stringTypedQuery2.setMaxResults(any()) } returns stringTypedQuery2
        every { stringTypedQuery2.resultList } returns list1
        every { longTypedQuery2.setHint(any(), any()) } returns longTypedQuery2
        every { longTypedQuery2.setFirstResult(any()) } returns longTypedQuery2
        every { longTypedQuery2.resultList } returns counts1
        every { metadata.lockModeType } returns lockModeType1
        every { metadata.queryHints } returns queryHints1
        every { metadata.queryHintsForCount } returns queryHintsForCount1
        every { PageableExecutionUtilsAdaptor.getPage<String>(any(), any(), any()) } answers {
            lastArg<LongSupplier>().asLong

            page1
        }

        // when
        val actual = sut.findPage(MyJpql, pageable1, createSelectQuery2)

        // then
        assertThat(actual).isEqualTo(page1)

        verifySequence {
            selectQuery2.returnType
            JpqlEntityManagerUtils.createEnhancedQuery(entityManager, selectQuery2, String::class, sort1, renderContext)
            metadata.lockModeType
            stringTypedQuery2.setLockMode(lockModeType1)
            metadata.queryHints
            stringTypedQuery2.setHint(queryHint1.first, queryHint1.second)
            stringTypedQuery2.setHint(queryHint2.first, queryHint2.second)
            stringTypedQuery2.setHint(queryHint3.first, queryHint3.second)
            stringTypedQuery2.firstResult = pageable1.offset.toInt()
            stringTypedQuery2.maxResults = pageable1.pageSize
            stringTypedQuery2.resultList
            PageableExecutionUtilsAdaptor.getPage(list1, pageable1, any())
            metadata.queryHintsForCount
            longTypedQuery2.setHint(queryHintForCount1.first, queryHintForCount1.second)
            longTypedQuery2.setHint(queryHintForCount2.first, queryHintForCount2.second)
            longTypedQuery2.setHint(queryHintForCount3.first, queryHintForCount3.second)
            longTypedQuery2.resultList
        }
    }

    @Test
    fun `findPage() with a dsl object`() {
        // given
        every { selectQuery3.returnType } returns String::class
        every {
            JpqlEntityManagerUtils.createEnhancedQuery(
                any(), any<SelectQuery<String>>(), any<KClass<*>>(), any(), any(),
            )
        } returns enhancedTypedQuery3
        every { stringTypedQuery3.setLockMode(any()) } returns stringTypedQuery3
        every { stringTypedQuery3.setHint(any(), any()) } returns stringTypedQuery3
        every { stringTypedQuery3.setFirstResult(any()) } returns stringTypedQuery3
        every { stringTypedQuery3.setMaxResults(any()) } returns stringTypedQuery3
        every { stringTypedQuery3.resultList } returns list1
        every { longTypedQuery3.setHint(any(), any()) } returns longTypedQuery3
        every { longTypedQuery3.setFirstResult(any()) } returns longTypedQuery3
        every { longTypedQuery3.resultList } returns counts1
        every { metadata.lockModeType } returns lockModeType1
        every { metadata.queryHints } returns queryHints1
        every { metadata.queryHintsForCount } returns queryHintsForCount1
        every { PageableExecutionUtilsAdaptor.getPage<String>(any(), any(), any()) } answers {
            lastArg<LongSupplier>().asLong

            page1
        }

        // when
        val actual = sut.findPage(MyJpqlObject, pageable1, createSelectQuery3)

        // then
        assertThat(actual).isEqualTo(page1)

        verifySequence {
            selectQuery3.returnType
            JpqlEntityManagerUtils.createEnhancedQuery(entityManager, selectQuery3, String::class, sort1, renderContext)
            metadata.lockModeType
            stringTypedQuery3.setLockMode(lockModeType1)
            metadata.queryHints
            stringTypedQuery3.setHint(queryHint1.first, queryHint1.second)
            stringTypedQuery3.setHint(queryHint2.first, queryHint2.second)
            stringTypedQuery3.setHint(queryHint3.first, queryHint3.second)
            stringTypedQuery3.firstResult = pageable1.offset.toInt()
            stringTypedQuery3.maxResults = pageable1.pageSize
            stringTypedQuery3.resultList
            PageableExecutionUtilsAdaptor.getPage(list1, pageable1, any())
            metadata.queryHintsForCount
            longTypedQuery3.setHint(queryHintForCount1.first, queryHintForCount1.second)
            longTypedQuery3.setHint(queryHintForCount2.first, queryHintForCount2.second)
            longTypedQuery3.setHint(queryHintForCount3.first, queryHintForCount3.second)
            longTypedQuery3.resultList
        }
    }

    @Test
    fun findSlice() {
        // given
        val pageable1 = PageRequest.of(1, 3, sort1)
        val list1 = listOf("1", "2", "3", "4")

        every { selectQuery1.returnType } returns String::class
        every {
            JpqlEntityManagerUtils.createEnhancedQuery(
                any(), any<SelectQuery<String>>(), any<KClass<*>>(), any(), any(),
            )
        } returns enhancedTypedQuery1
        every { stringTypedQuery1.setLockMode(any()) } returns stringTypedQuery1
        every { stringTypedQuery1.setHint(any(), any()) } returns stringTypedQuery1
        every { stringTypedQuery1.setFirstResult(any()) } returns stringTypedQuery1
        every { stringTypedQuery1.setMaxResults(any()) } returns stringTypedQuery1
        every { stringTypedQuery1.resultList } returns list1
        every { metadata.lockModeType } returns lockModeType1
        every { metadata.queryHints } returns queryHints1

        // when
        val actual = sut.findSlice(pageable1, createSelectQuery1)

        // then
        assertThat(actual).isEqualTo(SliceImpl(list1.dropLast(1), pageable1, true))

        verifySequence {
            selectQuery1.returnType
            JpqlEntityManagerUtils.createEnhancedQuery(entityManager, selectQuery1, String::class, sort1, renderContext)
            metadata.lockModeType
            stringTypedQuery1.setLockMode(lockModeType1)
            metadata.queryHints
            stringTypedQuery1.setHint(queryHint1.first, queryHint1.second)
            stringTypedQuery1.setHint(queryHint2.first, queryHint2.second)
            stringTypedQuery1.setHint(queryHint3.first, queryHint3.second)
            stringTypedQuery1.firstResult = pageable1.offset.toInt()
            stringTypedQuery1.maxResults = pageable1.pageSize + 1
            stringTypedQuery1.resultList
        }
    }

    @Test
    fun `findSlice() with a dsl`() {
        // given
        val pageable1 = PageRequest.of(1, 3, sort1)
        val list1 = listOf("1", "2", "3", "4")

        every { selectQuery2.returnType } returns String::class
        every {
            JpqlEntityManagerUtils.createEnhancedQuery(
                any(), any<SelectQuery<String>>(), any<KClass<*>>(), any(), any(),
            )
        } returns enhancedTypedQuery2
        every { stringTypedQuery2.setLockMode(any()) } returns stringTypedQuery2
        every { stringTypedQuery2.setHint(any(), any()) } returns stringTypedQuery2
        every { stringTypedQuery2.setFirstResult(any()) } returns stringTypedQuery2
        every { stringTypedQuery2.setMaxResults(any()) } returns stringTypedQuery2
        every { stringTypedQuery2.resultList } returns list1
        every { metadata.lockModeType } returns lockModeType1
        every { metadata.queryHints } returns queryHints1

        // when
        val actual = sut.findSlice(MyJpql, pageable1, createSelectQuery2)

        // then
        assertThat(actual).isEqualTo(SliceImpl(list1.dropLast(1), pageable1, true))

        verifySequence {
            selectQuery2.returnType
            JpqlEntityManagerUtils.createEnhancedQuery(entityManager, selectQuery2, String::class, sort1, renderContext)
            metadata.lockModeType
            stringTypedQuery2.setLockMode(lockModeType1)
            metadata.queryHints
            stringTypedQuery2.setHint(queryHint1.first, queryHint1.second)
            stringTypedQuery2.setHint(queryHint2.first, queryHint2.second)
            stringTypedQuery2.setHint(queryHint3.first, queryHint3.second)
            stringTypedQuery2.firstResult = pageable1.offset.toInt()
            stringTypedQuery2.maxResults = pageable1.pageSize + 1
            stringTypedQuery2.resultList
        }
    }

    @Test
    fun `findSlice() with a dsl object`() {
        // given
        val pageable1 = PageRequest.of(1, 3, sort1)
        val list1 = listOf("1", "2", "3", "4")

        every { selectQuery3.returnType } returns String::class
        every {
            JpqlEntityManagerUtils.createEnhancedQuery(
                any(), any<SelectQuery<String>>(), any<KClass<*>>(), any(), any(),
            )
        } returns enhancedTypedQuery3
        every { stringTypedQuery3.setLockMode(any()) } returns stringTypedQuery3
        every { stringTypedQuery3.setHint(any(), any()) } returns stringTypedQuery3
        every { stringTypedQuery3.setFirstResult(any()) } returns stringTypedQuery3
        every { stringTypedQuery3.setMaxResults(any()) } returns stringTypedQuery3
        every { stringTypedQuery3.resultList } returns list1
        every { metadata.lockModeType } returns lockModeType1
        every { metadata.queryHints } returns queryHints1

        // when
        val actual = sut.findSlice(MyJpqlObject, pageable1, createSelectQuery3)

        // then
        assertThat(actual).isEqualTo(SliceImpl(list1.dropLast(1), pageable1, true))

        verifySequence {
            selectQuery3.returnType
            JpqlEntityManagerUtils.createEnhancedQuery(entityManager, selectQuery3, String::class, sort1, renderContext)
            metadata.lockModeType
            stringTypedQuery3.setLockMode(lockModeType1)
            metadata.queryHints
            stringTypedQuery3.setHint(queryHint1.first, queryHint1.second)
            stringTypedQuery3.setHint(queryHint2.first, queryHint2.second)
            stringTypedQuery3.setHint(queryHint3.first, queryHint3.second)
            stringTypedQuery3.firstResult = pageable1.offset.toInt()
            stringTypedQuery3.maxResults = pageable1.pageSize + 1
            stringTypedQuery3.resultList
        }
    }

    @Test
    fun findStream() {
        // given
        every { selectQuery1.returnType } returns String::class
        every {
            JpqlEntityManagerUtils.createQuery(any(), any<SelectQuery<String>>(), any<KClass<*>>(), any())
        } returns stringTypedQuery1
        every { stringTypedQuery1.setLockMode(any()) } returns stringTypedQuery1
        every { stringTypedQuery1.setHint(any(), any()) } returns stringTypedQuery1
        every { stringTypedQuery1.setFirstResult(any()) } returns stringTypedQuery1
        every { stringTypedQuery1.setMaxResults(any()) } returns stringTypedQuery1
        every { stringTypedQuery1.resultStream } returns stream1
        every { metadata.lockModeType } returns lockModeType1
        every { metadata.queryHints } returns queryHints1

        // when
        val actual = sut.findStream(offset1, limit1, createSelectQuery1)

        // then
        assertThat(actual).isEqualTo(stream1)

        verifySequence {
            selectQuery1.returnType
            JpqlEntityManagerUtils.createQuery(entityManager, selectQuery1, String::class, renderContext)
            metadata.lockModeType
            stringTypedQuery1.setLockMode(lockModeType1)
            metadata.queryHints
            stringTypedQuery1.setHint(queryHint1.first, queryHint1.second)
            stringTypedQuery1.setHint(queryHint2.first, queryHint2.second)
            stringTypedQuery1.setHint(queryHint3.first, queryHint3.second)
            stringTypedQuery1.setFirstResult(offset1)
            stringTypedQuery1.setMaxResults(limit1)
            stringTypedQuery1.resultStream
        }
    }

    @Test
    fun `findStream() with a dsl`() {
        // given
        every { selectQuery2.returnType } returns String::class
        every {
            JpqlEntityManagerUtils.createQuery(any(), any<SelectQuery<String>>(), any<KClass<*>>(), any())
        } returns stringTypedQuery2
        every { stringTypedQuery2.setLockMode(any()) } returns stringTypedQuery2
        every { stringTypedQuery2.setHint(any(), any()) } returns stringTypedQuery2
        every { stringTypedQuery2.setFirstResult(any()) } returns stringTypedQuery2
        every { stringTypedQuery2.setMaxResults(any()) } returns stringTypedQuery2
        every { stringTypedQuery2.resultStream } returns stream1
        every { metadata.lockModeType } returns lockModeType1
        every { metadata.queryHints } returns queryHints1

        // when
        val actual = sut.findStream(MyJpql, offset1, limit1, createSelectQuery2)

        // then
        assertThat(actual).isEqualTo(stream1)

        verifySequence {
            selectQuery2.returnType
            JpqlEntityManagerUtils.createQuery(entityManager, selectQuery2, String::class, renderContext)
            metadata.lockModeType
            stringTypedQuery2.setLockMode(lockModeType1)
            metadata.queryHints
            stringTypedQuery2.setHint(queryHint1.first, queryHint1.second)
            stringTypedQuery2.setHint(queryHint2.first, queryHint2.second)
            stringTypedQuery2.setHint(queryHint3.first, queryHint3.second)
            stringTypedQuery2.setFirstResult(offset1)
            stringTypedQuery2.setMaxResults(limit1)
            stringTypedQuery2.resultStream
        }
    }

    @Test
    fun `findStream() with a dsl object`() {
        // given
        every { selectQuery3.returnType } returns String::class
        every {
            JpqlEntityManagerUtils.createQuery(any(), any<SelectQuery<String>>(), any<KClass<*>>(), any())
        } returns stringTypedQuery3
        every { stringTypedQuery3.setLockMode(any()) } returns stringTypedQuery3
        every { stringTypedQuery3.setHint(any(), any()) } returns stringTypedQuery3
        every { stringTypedQuery3.setFirstResult(any()) } returns stringTypedQuery3
        every { stringTypedQuery3.setMaxResults(any()) } returns stringTypedQuery3
        every { stringTypedQuery3.resultStream } returns stream1
        every { metadata.lockModeType } returns lockModeType1
        every { metadata.queryHints } returns queryHints1

        // when
        val actual = sut.findStream(MyJpqlObject, offset1, limit1, createSelectQuery3)

        // then
        assertThat(actual).isEqualTo(stream1)

        verifySequence {
            selectQuery3.returnType
            JpqlEntityManagerUtils.createQuery(entityManager, selectQuery3, String::class, renderContext)
            metadata.lockModeType
            stringTypedQuery3.setLockMode(lockModeType1)
            metadata.queryHints
            stringTypedQuery3.setHint(queryHint1.first, queryHint1.second)
            stringTypedQuery3.setHint(queryHint2.first, queryHint2.second)
            stringTypedQuery3.setHint(queryHint3.first, queryHint3.second)
            stringTypedQuery3.setFirstResult(offset1)
            stringTypedQuery3.setMaxResults(limit1)
            stringTypedQuery3.resultStream
        }
    }

    @Test
    fun `findStream() with a pageable`() {
        // given
        every { selectQuery1.returnType } returns String::class
        every {
            JpqlEntityManagerUtils.createEnhancedQuery(
                any(), any<SelectQuery<String>>(), any<KClass<*>>(), any(), any(),
            )
        } returns enhancedTypedQuery1
        every { stringTypedQuery1.setLockMode(any()) } returns stringTypedQuery1
        every { stringTypedQuery1.setHint(any(), any()) } returns stringTypedQuery1
        every { stringTypedQuery1.setFirstResult(any()) } returns stringTypedQuery1
        every { stringTypedQuery1.setMaxResults(any()) } returns stringTypedQuery1
        every { stringTypedQuery1.resultStream } returns stream1
        every { metadata.lockModeType } returns lockModeType1
        every { metadata.queryHints } returns queryHints1

        // when
        val actual = sut.findStream(pageable1, createSelectQuery1)

        // then
        assertThat(actual).isEqualTo(stream1)

        verifySequence {
            selectQuery1.returnType
            JpqlEntityManagerUtils.createEnhancedQuery(entityManager, selectQuery1, String::class, sort1, renderContext)
            metadata.lockModeType
            stringTypedQuery1.setLockMode(lockModeType1)
            metadata.queryHints
            stringTypedQuery1.setHint(queryHint1.first, queryHint1.second)
            stringTypedQuery1.setHint(queryHint2.first, queryHint2.second)
            stringTypedQuery1.setHint(queryHint3.first, queryHint3.second)
            stringTypedQuery1.firstResult = pageable1.offset.toInt()
            stringTypedQuery1.maxResults = pageable1.pageSize
            stringTypedQuery1.resultStream
        }
    }

    @Test
    fun `findStream() with a dsl and a pageable`() {
        // given
        every { selectQuery2.returnType } returns String::class
        every {
            JpqlEntityManagerUtils.createEnhancedQuery(
                any(), any<SelectQuery<String>>(), any<KClass<*>>(), any(), any(),
            )
        } returns enhancedTypedQuery2
        every { stringTypedQuery2.setLockMode(any()) } returns stringTypedQuery2
        every { stringTypedQuery2.setHint(any(), any()) } returns stringTypedQuery2
        every { stringTypedQuery2.setFirstResult(any()) } returns stringTypedQuery2
        every { stringTypedQuery2.setMaxResults(any()) } returns stringTypedQuery2
        every { stringTypedQuery2.resultStream } returns stream1
        every { metadata.lockModeType } returns lockModeType1
        every { metadata.queryHints } returns queryHints1

        // when
        val actual = sut.findStream(MyJpql, pageable1, createSelectQuery2)

        // then
        assertThat(actual).isEqualTo(stream1)

        verifySequence {
            selectQuery2.returnType
            JpqlEntityManagerUtils.createEnhancedQuery(entityManager, selectQuery2, String::class, sort1, renderContext)
            metadata.lockModeType
            stringTypedQuery2.setLockMode(lockModeType1)
            metadata.queryHints
            stringTypedQuery2.setHint(queryHint1.first, queryHint1.second)
            stringTypedQuery2.setHint(queryHint2.first, queryHint2.second)
            stringTypedQuery2.setHint(queryHint3.first, queryHint3.second)
            stringTypedQuery2.firstResult = pageable1.offset.toInt()
            stringTypedQuery2.maxResults = pageable1.pageSize
            stringTypedQuery2.resultStream
        }
    }

    @Test
    fun `findStream() with a dsl object and a pageable`() {
        // given
        every { selectQuery3.returnType } returns String::class
        every {
            JpqlEntityManagerUtils.createEnhancedQuery(
                any(), any<SelectQuery<String>>(), any<KClass<*>>(), any(), any(),
            )
        } returns enhancedTypedQuery3
        every { stringTypedQuery3.setLockMode(any()) } returns stringTypedQuery3
        every { stringTypedQuery3.setHint(any(), any()) } returns stringTypedQuery3
        every { stringTypedQuery3.setFirstResult(any()) } returns stringTypedQuery3
        every { stringTypedQuery3.setMaxResults(any()) } returns stringTypedQuery3
        every { stringTypedQuery3.resultStream } returns stream1
        every { metadata.lockModeType } returns lockModeType1
        every { metadata.queryHints } returns queryHints1

        // when
        val actual = sut.findStream(MyJpqlObject, pageable1, createSelectQuery3)

        // then
        assertThat(actual).isEqualTo(stream1)

        verifySequence {
            selectQuery3.returnType
            JpqlEntityManagerUtils.createEnhancedQuery(entityManager, selectQuery3, String::class, sort1, renderContext)
            metadata.lockModeType
            stringTypedQuery3.setLockMode(lockModeType1)
            metadata.queryHints
            stringTypedQuery3.setHint(queryHint1.first, queryHint1.second)
            stringTypedQuery3.setHint(queryHint2.first, queryHint2.second)
            stringTypedQuery3.setHint(queryHint3.first, queryHint3.second)
            stringTypedQuery3.firstResult = pageable1.offset.toInt()
            stringTypedQuery3.maxResults = pageable1.pageSize
            stringTypedQuery3.resultStream
        }
    }

    @Test
    fun update() {
        // given
        every { JpqlEntityManagerUtils.createQuery(any(), any<UpdateQuery<String>>(), any()) } returns query1
        every { query1.setLockMode(any()) } returns query1
        every { query1.setHint(any(), any()) } returns query1
        every { query1.executeUpdate() } returns 1
        every { metadata.lockModeType } returns lockModeType1
        every { metadata.queryHints } returns queryHints1

        // when
        val actual = sut.update(createUpdateQuery1)

        // then
        assertThat(actual).isEqualTo(1)

        verifySequence {
            JpqlEntityManagerUtils.createQuery(entityManager, updateQuery1, renderContext)
            metadata.lockModeType
            query1.setLockMode(lockModeType1)
            metadata.queryHints
            query1.setHint(queryHint1.first, queryHint1.second)
            query1.setHint(queryHint2.first, queryHint2.second)
            query1.setHint(queryHint3.first, queryHint3.second)
            query1.executeUpdate()
        }
    }

    @Test
    fun `update() with a dsl`() {
        // given
        every { JpqlEntityManagerUtils.createQuery(any(), any<UpdateQuery<String>>(), any()) } returns query2
        every { query2.setLockMode(any()) } returns query2
        every { query2.setHint(any(), any()) } returns query2
        every { query2.executeUpdate() } returns 1
        every { metadata.lockModeType } returns lockModeType1
        every { metadata.queryHints } returns queryHints1

        // when
        val actual = sut.update(MyJpql, createUpdateQuery2)

        // then
        assertThat(actual).isEqualTo(1)

        verifySequence {
            JpqlEntityManagerUtils.createQuery(entityManager, updateQuery2, renderContext)
            metadata.lockModeType
            query2.setLockMode(lockModeType1)
            metadata.queryHints
            query2.setHint(queryHint1.first, queryHint1.second)
            query2.setHint(queryHint2.first, queryHint2.second)
            query2.setHint(queryHint3.first, queryHint3.second)
            query2.executeUpdate()
        }
    }

    @Test
    fun `update() with a dsl object`() {
        // given
        every { JpqlEntityManagerUtils.createQuery(any(), any<UpdateQuery<String>>(), any()) } returns query3
        every { query3.setLockMode(any()) } returns query3
        every { query3.setHint(any(), any()) } returns query3
        every { query3.executeUpdate() } returns 1
        every { metadata.lockModeType } returns lockModeType1
        every { metadata.queryHints } returns queryHints1

        // when
        val actual = sut.update(MyJpqlObject, createUpdateQuery3)

        // then
        assertThat(actual).isEqualTo(1)

        verifySequence {
            JpqlEntityManagerUtils.createQuery(entityManager, updateQuery3, renderContext)
            metadata.lockModeType
            query3.setLockMode(lockModeType1)
            metadata.queryHints
            query3.setHint(queryHint1.first, queryHint1.second)
            query3.setHint(queryHint2.first, queryHint2.second)
            query3.setHint(queryHint3.first, queryHint3.second)
            query3.executeUpdate()
        }
    }

    @Test
    fun delete() {
        // given
        every { JpqlEntityManagerUtils.createQuery(any(), any<DeleteQuery<String>>(), any()) } returns query1
        every { query1.setLockMode(any()) } returns query1
        every { query1.setHint(any(), any()) } returns query1
        every { query1.executeUpdate() } returns 1
        every { metadata.lockModeType } returns lockModeType1
        every { metadata.queryHints } returns queryHints1

        // when
        val actual = sut.delete(createDeleteQuery1)

        // then
        assertThat(actual).isEqualTo(1)

        verifySequence {
            JpqlEntityManagerUtils.createQuery(entityManager, deleteQuery1, renderContext)
            metadata.lockModeType
            query1.setLockMode(lockModeType1)
            metadata.queryHints
            query1.setHint(queryHint1.first, queryHint1.second)
            query1.setHint(queryHint2.first, queryHint2.second)
            query1.setHint(queryHint3.first, queryHint3.second)
            query1.executeUpdate()
        }
    }

    @Test
    fun `delete() with a dsl`() {
        // given
        every { JpqlEntityManagerUtils.createQuery(any(), any<DeleteQuery<String>>(), any()) } returns query2
        every { query2.setLockMode(any()) } returns query2
        every { query2.setHint(any(), any()) } returns query2
        every { query2.executeUpdate() } returns 1
        every { metadata.lockModeType } returns lockModeType1
        every { metadata.queryHints } returns queryHints1

        // when
        val actual = sut.delete(MyJpql, createDeleteQuery2)

        // then
        assertThat(actual).isEqualTo(1)

        verifySequence {
            JpqlEntityManagerUtils.createQuery(entityManager, deleteQuery2, renderContext)
            metadata.lockModeType
            query2.setLockMode(lockModeType1)
            metadata.queryHints
            query2.setHint(queryHint1.first, queryHint1.second)
            query2.setHint(queryHint2.first, queryHint2.second)
            query2.setHint(queryHint3.first, queryHint3.second)
            query2.executeUpdate()
        }
    }

    @Test
    fun `delete() with a dsl object`() {
        // given
        every { JpqlEntityManagerUtils.createQuery(any(), any<DeleteQuery<String>>(), any()) } returns query3
        every { query3.setLockMode(any()) } returns query3
        every { query3.setHint(any(), any()) } returns query3
        every { query3.executeUpdate() } returns 1
        every { metadata.lockModeType } returns lockModeType1
        every { metadata.queryHints } returns queryHints1

        // when
        val actual = sut.delete(MyJpqlObject, createDeleteQuery3)

        // then
        assertThat(actual).isEqualTo(1)

        verifySequence {
            JpqlEntityManagerUtils.createQuery(entityManager, deleteQuery3, renderContext)
            metadata.lockModeType
            query3.setLockMode(lockModeType1)
            metadata.queryHints
            query3.setHint(queryHint1.first, queryHint1.second)
            query3.setHint(queryHint2.first, queryHint2.second)
            query3.setHint(queryHint3.first, queryHint3.second)
            query3.executeUpdate()
        }
    }
}
