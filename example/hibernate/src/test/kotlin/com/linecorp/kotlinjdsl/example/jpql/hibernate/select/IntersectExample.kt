package com.linecorp.kotlinjdsl.example.jpql.hibernate.select

import com.linecorp.kotlinjdsl.dsl.jpql.jpql
import com.linecorp.kotlinjdsl.example.jpql.hibernate.EntityManagerFactoryTestUtils
import com.linecorp.kotlinjdsl.example.jpql.hibernate.entity.book.Book
import com.linecorp.kotlinjdsl.example.jpql.hibernate.entity.book.BookPrice
import com.linecorp.kotlinjdsl.example.jpql.hibernate.entity.book.Isbn
import com.linecorp.kotlinjdsl.example.jpql.hibernate.jpql.JpqlRenderContextUtils
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.sort.Sorts
import com.linecorp.kotlinjdsl.support.hibernate.extension.createQuery
import jakarta.persistence.TypedQuery
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

class IntersectExample : WithAssertions {
    private val entityManagerFactory = EntityManagerFactoryTestUtils.getEntityManagerFactory()
    private val entityManager = entityManagerFactory.createEntityManager()

    private val context = JpqlRenderContextUtils.getJpqlRenderContext()
    private val aliasName = "isbnAlias"

    private val bookIsbn1 = Isbn("01")

    @AfterEach
    fun tearDown() {
        entityManager.close()
    }

    @Test
    fun intersectBooksByPriceAndSalePrice() {
        // When
        val query = jpql {
            val isbnPath = path(Book::isbn).`as`(Expressions.expression(Isbn::class, aliasName))
            intersect(
                select(
                    isbnPath,
                ).from(
                    entity(Book::class),
                ).where(
                    path(Book::price)(BookPrice::value).lessThan(3.toBigDecimal()),
                ),
                select(
                    isbnPath,
                ).from(
                    entity(Book::class),
                ).where(
                    path(Book::salePrice)(BookPrice::value).lessThan(2.toBigDecimal()),
                ),
            ).orderBy(
                Sorts.asc(isbnPath),
            )
        }

        val typedQuery: TypedQuery<Isbn> = entityManager.createQuery(query, context)
        val actual: List<Isbn> = typedQuery.resultList

        // Then
        assertThat(actual).containsExactly(
            bookIsbn1,
        )
    }

    /**
     * Test for INTERSECT ALL operation.
     *
     * Note: This test is disabled because H2 database does not support INTERSECT ALL.
     * H2 only supports INTERSECT but not INTERSECT ALL as of version 2.3.232.
     *
     * References:
     * - H2 Database Commands documentation: https://h2database.com/html/commands.html#select
     * - Modern SQL INTERSECT ALL support: https://modern-sql.com/caniuse/intersect-all
     *   (Shows H2 versions 1.4.192 - 2.3.232 as not supporting INTERSECT ALL)
     */
    @Test
    @Disabled("H2 database does not support INTERSECT ALL - only INTERSECT is supported")
    fun intersectAllBooksByPriceAndSalePrice() {
        // When
        val query = jpql {
            val isbnPath = path(Book::isbn).`as`(Expressions.expression(Isbn::class, aliasName))
            intersectAll(
                select(
                    isbnPath,
                ).from(
                    entity(Book::class),
                ).where(
                    path(Book::price)(BookPrice::value).lessThan(3.toBigDecimal()),
                ),
                select(
                    isbnPath,
                ).from(
                    entity(Book::class),
                ).where(
                    path(Book::salePrice)(BookPrice::value).lessThan(2.toBigDecimal()),
                ),
            ).orderBy(
                Sorts.asc(isbnPath),
            )
        }

        val typedQuery: TypedQuery<Isbn> = entityManager.createQuery(query, context)
        val actual: List<Isbn> = typedQuery.resultList

        // Then
        assertThat(actual).containsExactly(
            bookIsbn1,
        )
    }

    @Test
    fun intersectBooksByPriceAndSalePriceWithNewDsl() {
        // When
        val query = jpql {
            val isbnPath = path(Book::isbn).`as`(Expressions.expression(Isbn::class, aliasName))
            select(
                isbnPath,
            ).from(
                entity(Book::class),
            ).where(
                path(Book::price)(BookPrice::value).lessThan(3.toBigDecimal()),
            ).intersect(
                select(
                    isbnPath,
                ).from(
                    entity(Book::class),
                ).where(
                    path(Book::salePrice)(BookPrice::value).lessThan(2.toBigDecimal()),
                ),
            ).orderBy(
                Sorts.asc(isbnPath),
            )
        }

        val typedQuery: TypedQuery<Isbn> = entityManager.createQuery(query, context)
        val actual: List<Isbn> = typedQuery.resultList

        // Then
        assertThat(actual).containsExactly(
            bookIsbn1,
        )
    }

    /**
     * Test for INTERSECT ALL operation using extension function syntax.
     *
     * Note: This test is disabled because H2 database does not support INTERSECT ALL.
     * H2 only supports INTERSECT but not INTERSECT ALL as of version 2.3.232.
     *
     * References:
     * - H2 Database Commands documentation: https://h2database.com/html/commands.html#select
     * - Modern SQL INTERSECT ALL support: https://modern-sql.com/caniuse/intersect-all
     *   (Shows H2 versions 1.4.192 - 2.3.232 as not supporting INTERSECT ALL)
     */
    @Test
    @Disabled("H2 database does not support INTERSECT ALL - only INTERSECT is supported")
    fun intersectAllBooksByPriceAndSalePriceWithNewDsl() {
        // When
        val query = jpql {
            val isbnPath = path(Book::isbn).`as`(Expressions.expression(Isbn::class, aliasName))
            select(
                isbnPath,
            ).from(
                entity(Book::class),
            ).where(
                path(Book::price)(BookPrice::value).lessThan(3.toBigDecimal()),
            ).intersectAll(
                select(
                    isbnPath,
                ).from(
                    entity(Book::class),
                ).where(
                    path(Book::salePrice)(BookPrice::value).lessThan(2.toBigDecimal()),
                ),
            ).orderBy(
                Sorts.asc(isbnPath),
            )
        }

        val typedQuery: TypedQuery<Isbn> = entityManager.createQuery(query, context)
        val actual: List<Isbn> = typedQuery.resultList

        // Then
        assertThat(actual).containsExactly(
            bookIsbn1,
        )
    }
}
