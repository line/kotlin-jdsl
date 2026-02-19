package com.linecorp.kotlinjdsl.support.spring.batch.item.database.orm

import com.linecorp.kotlinjdsl.dsl.jpql.Jpql
import com.linecorp.kotlinjdsl.dsl.jpql.JpqlDsl
import com.linecorp.kotlinjdsl.dsl.jpql.jpql
import com.linecorp.kotlinjdsl.querymodel.jpql.JpqlQueryable
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import com.linecorp.kotlinjdsl.render.RenderContext
import io.mockk.every
import io.mockk.excludeRecords
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class KotlinJdslQueryProviderFactoryTest : WithAssertions {
    @InjectMockKs
    private lateinit var sut: KotlinJdslQueryProviderFactory

    @MockK
    private lateinit var context: RenderContext

    @MockK
    private lateinit var createSelectQuery1: Jpql.() -> JpqlQueryable<SelectQuery<String>>

    @MockK
    private lateinit var createSelectQuery2: MyJpql.() -> JpqlQueryable<SelectQuery<String>>

    @MockK
    private lateinit var createSelectQuery3: MyJpqlObject.() -> JpqlQueryable<SelectQuery<String>>

    @MockK
    private lateinit var selectQuery1: SelectQuery<String>

    @MockK
    private lateinit var selectQuery2: SelectQuery<String>

    @MockK
    private lateinit var selectQuery3: SelectQuery<String>

    private val queryParams1 = mapOf("authorId" to 1L)

    private class MyJpql : Jpql() {
        companion object Constructor : JpqlDsl.Constructor<MyJpql> {
            override fun newInstance(): MyJpql = MyJpql()
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            return javaClass == other?.javaClass
        }

        override fun hashCode(): Int = javaClass.hashCode()
    }

    private object MyJpqlObject : Jpql() {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            return javaClass == other?.javaClass
        }

        override fun hashCode(): Int = javaClass.hashCode()
    }

    @BeforeEach
    fun setUp() {
        every { createSelectQuery1.invoke(any()) } returns selectQuery1
        every { createSelectQuery2.invoke(any()) } returns selectQuery2
        every { createSelectQuery3.invoke(any()) } returns selectQuery3

        every { selectQuery1.toQuery() } returns selectQuery1
        every { selectQuery2.toQuery() } returns selectQuery2
        every { selectQuery3.toQuery() } returns selectQuery3

        excludeRecords { createSelectQuery1.invoke(any()) }
        excludeRecords { createSelectQuery2.invoke(any()) }
        excludeRecords { createSelectQuery3.invoke(any()) }
        excludeRecords { selectQuery1.toQuery() }
        excludeRecords { selectQuery2.toQuery() }
        excludeRecords { selectQuery3.toQuery() }
    }

    @Test
    fun `create() with a select queryable`() {
        // when
        val actual = sut.create(createSelectQuery1)

        // then
        val expected =
            KotlinJdslQueryProvider(
                query = jpql(createSelectQuery1),
                queryParams = emptyMap(),
                context = context,
            )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `create() with a select queryable and query params`() {
        // when
        val actual = sut.create(queryParams1, createSelectQuery1)

        // then
        val expected =
            KotlinJdslQueryProvider(
                query = jpql(createSelectQuery1),
                queryParams = queryParams1,
                context = context,
            )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `create() with a dsl and a select queryable`() {
        // when
        val actual = sut.create(MyJpql, createSelectQuery2)

        // then
        val expected =
            KotlinJdslQueryProvider(
                query = jpql(MyJpql, createSelectQuery2),
                queryParams = emptyMap(),
                context = context,
            )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `create() with a dsl, a select queryable, and query params`() {
        // when
        val actual = sut.create(MyJpql, queryParams1, createSelectQuery2)

        // then
        val expected =
            KotlinJdslQueryProvider(
                query = jpql(MyJpql, createSelectQuery2),
                queryParams = queryParams1,
                context = context,
            )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `create() with an dsl object and a select queryable`() {
        // when
        val actual = sut.create(MyJpqlObject, createSelectQuery3)

        // then
        val expected =
            KotlinJdslQueryProvider(
                query = jpql(MyJpqlObject, createSelectQuery3),
                queryParams = emptyMap(),
                context = context,
            )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `create() with an dsl object, a select queryable, and query params`() {
        // when
        val actual = sut.create(MyJpqlObject, queryParams1, createSelectQuery3)

        // then
        val expected =
            KotlinJdslQueryProvider(
                query = jpql(MyJpqlObject, createSelectQuery3),
                queryParams = queryParams1,
                context = context,
            )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `create() with a select query`() {
        // when
        val actual = sut.create(selectQuery1)

        // then
        val expected =
            KotlinJdslQueryProvider(
                query = selectQuery1,
                queryParams = emptyMap(),
                context = context,
            )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `create() with a select query and query params`() {
        // when
        val actual = sut.create(selectQuery1, queryParams1)

        // then
        val expected =
            KotlinJdslQueryProvider(
                query = selectQuery1,
                queryParams = queryParams1,
                context = context,
            )

        assertThat(actual).isEqualTo(expected)
    }
}
