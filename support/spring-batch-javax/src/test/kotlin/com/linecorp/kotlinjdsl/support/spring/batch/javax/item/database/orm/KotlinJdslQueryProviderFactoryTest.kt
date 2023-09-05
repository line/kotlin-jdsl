package com.linecorp.kotlinjdsl.support.spring.batch.javax.item.database.orm

import com.linecorp.kotlinjdsl.dsl.jpql.Jpql
import com.linecorp.kotlinjdsl.dsl.jpql.JpqlDsl
import com.linecorp.kotlinjdsl.dsl.jpql.jpql
import com.linecorp.kotlinjdsl.querymodel.jpql.JpqlQueryable
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.support.spring.batch.javax.entity.author.Author
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class KotlinJdslQueryProviderFactoryTest : WithAssertions {
    @InjectMockKs
    private lateinit var sut: KotlinJdslQueryProviderFactory

    @MockK
    private lateinit var context: RenderContext

    private val createSelectQuery1: Jpql.() -> JpqlQueryable<SelectQuery<Author>> = {
        select(
            entity(Author::class),
        ).from(
            entity(Author::class),
        )
    }

    private val createSelectQuery2: MyJpql.() -> JpqlQueryable<SelectQuery<Author>> = {
        select(
            entity(Author::class),
        ).from(
            entity(Author::class),
        ).where(
            path(Author::authorId).eq(1L),
        )
    }

    private val selectQuery1: SelectQuery<Author> = jpql(createSelectQuery1)

    private val queryParams1 = mapOf("authorId" to 1L)

    @Test
    fun `create() with JpqlQueryable`() {
        // when
        val actual = sut.create(createSelectQuery1)

        // then
        val expected = KotlinJdslQueryProvider(
            query = jpql(createSelectQuery1),
            queryParams = emptyMap(),
            context = context,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `create() with JpqlQueryable and query parameters`() {
        // when
        val actual = sut.create(queryParams1, createSelectQuery1)

        // then
        val expected = KotlinJdslQueryProvider(
            query = jpql(createSelectQuery1),
            queryParams = queryParams1,
            context = context,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `create() with JpqlDsl and JpqlQueryable`() {
        // when
        val actual = sut.create(MyJpql, createSelectQuery2)

        // then
        val expected = KotlinJdslQueryProvider(
            query = jpql(MyJpql, createSelectQuery2),
            queryParams = emptyMap(),
            context = context,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `create() with JpqlDsl, JpqlQueryable and query parameters`() {
        // when
        val actual = sut.create(MyJpql, queryParams1, createSelectQuery2)

        // then
        val expected = KotlinJdslQueryProvider(
            query = jpql(MyJpql, createSelectQuery2),
            queryParams = queryParams1,
            context = context,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `create() with SelectQuery`() {
        // when
        val actual = sut.create(selectQuery1)

        // then
        val expected = KotlinJdslQueryProvider(
            query = selectQuery1,
            queryParams = emptyMap(),
            context = context,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `create() with SelectQuery and query parameters`() {
        // when
        val actual = sut.create(selectQuery1, queryParams1)

        // then
        val expected = KotlinJdslQueryProvider(
            query = selectQuery1,
            queryParams = queryParams1,
            context = context,
        )

        assertThat(actual).isEqualTo(expected)
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
}
