package com.linecorp.kotlinjdsl.dsl.jpql.select

import com.linecorp.kotlinjdsl.dsl.jpql.entity.book.Book
import com.linecorp.kotlinjdsl.dsl.jpql.jpql
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entities
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQueries
import com.linecorp.kotlinjdsl.querymodel.jpql.select.impl.JpqlSelectQueryUnion
import com.linecorp.kotlinjdsl.querymodel.jpql.select.impl.JpqlSelectQueryUnionAll
import com.linecorp.kotlinjdsl.querymodel.jpql.sort.Sorts
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

class SetOperatorDslTest : WithAssertions {
    private val entity1 = Entities.entity(Book::class, "entity1")
    private val entity2 = Entities.entity(Book::class, "entity2")

    private val sort1 = Sorts.asc(entity1)

    @Test
    fun `union between two queries`() {
        // when
        val query = jpql {
            val subquery1 = select(
                entity1,
            ).from(
                entity1,
            )

            val subquery2 = select(
                entity2,
            ).from(
                entity2,
            )

            union(
                subquery1,
                subquery2,
            )
        }

        // then
        val subquery1 = SelectQueries.selectQuery(
            returnType = Book::class,
            distinct = false,
            select = listOf(entity1),
            from = listOf(entity1),
        )

        val subquery2 = SelectQueries.selectQuery(
            returnType = Book::class,
            distinct = false,
            select = listOf(entity2),
            from = listOf(entity2),
        )

        assertThat(query).isInstanceOf(JpqlSelectQueryUnion::class.java)

        val actualUnion = query as JpqlSelectQueryUnion

        assertThat(actualUnion.returnType).isEqualTo(Book::class)
        assertThat(actualUnion.left.toQuery()).isEqualTo(subquery1)
        assertThat(actualUnion.right.toQuery()).isEqualTo(subquery2)
        assertThat(actualUnion.orderBy).isNull()
    }

    @Test
    fun `union between two queries with orderBy`() {
        // when
        val query = jpql {
            val subquery1 = select(
                entity1,
            ).from(
                entity1,
            )

            val subquery2 = select(
                entity2,
            ).from(
                entity2,
            )

            union(
                subquery1,
                subquery2,
            ).orderBy(
                sort1,
            )
        }

        // then
        val subquery1 = SelectQueries.selectQuery(
            returnType = Book::class,
            distinct = false,
            select = listOf(entity1),
            from = listOf(entity1),
        )

        val subquery2 = SelectQueries.selectQuery(
            returnType = Book::class,
            distinct = false,
            select = listOf(entity2),
            from = listOf(entity2),
        )

        assertThat(query).isInstanceOf(JpqlSelectQueryUnion::class.java)

        val actualUnion = query as JpqlSelectQueryUnion

        assertThat(actualUnion.returnType).isEqualTo(Book::class)
        assertThat(actualUnion.left.toQuery()).isEqualTo(subquery1)
        assertThat(actualUnion.right.toQuery()).isEqualTo(subquery2)
        assertThat(actualUnion.orderBy).isEqualTo(listOf(sort1))
    }

    @Test
    fun `union all between two queries`() {
        // when
        val query = jpql {
            val subquery1 = select(
                entity1,
            ).from(
                entity1,
            )

            val subquery2 = select(
                entity2,
            ).from(
                entity2,
            )

            unionAll(
                subquery1,
                subquery2,
            )
        }

        // then
        val subquery1 = SelectQueries.selectQuery(
            returnType = Book::class,
            distinct = false,
            select = listOf(entity1),
            from = listOf(entity1),
        )

        val subquery2 = SelectQueries.selectQuery(
            returnType = Book::class,
            distinct = false,
            select = listOf(entity2),
            from = listOf(entity2),
        )

        assertThat(query).isInstanceOf(JpqlSelectQueryUnionAll::class.java)

        val actualUnionAll = query as JpqlSelectQueryUnionAll

        assertThat(actualUnionAll.returnType).isEqualTo(Book::class)
        assertThat(actualUnionAll.left.toQuery()).isEqualTo(subquery1)
        assertThat(actualUnionAll.right.toQuery()).isEqualTo(subquery2)
        assertThat(actualUnionAll.orderBy).isNull()
    }

    @Test
    fun `union all between two queries with orderBy`() {
        // when
        val query = jpql {
            val subquery1 = select(
                entity1,
            ).from(
                entity1,
            )

            val subquery2 = select(
                entity2,
            ).from(
                entity2,
            )

            unionAll(
                subquery1,
                subquery2,
            ).orderBy(
                sort1,
            )
        }

        // then
        val subquery1 = SelectQueries.selectQuery(
            returnType = Book::class,
            distinct = false,
            select = listOf(entity1),
            from = listOf(entity1),
        )

        val subquery2 = SelectQueries.selectQuery(
            returnType = Book::class,
            distinct = false,
            select = listOf(entity2),
            from = listOf(entity2),
        )

        assertThat(query).isInstanceOf(JpqlSelectQueryUnionAll::class.java)

        val actualUnionAll = query as JpqlSelectQueryUnionAll

        assertThat(actualUnionAll.returnType).isEqualTo(Book::class)
        assertThat(actualUnionAll.left.toQuery()).isEqualTo(subquery1)
        assertThat(actualUnionAll.right.toQuery()).isEqualTo(subquery2)
        assertThat(actualUnionAll.orderBy).isEqualTo(listOf(sort1))
    }
}
