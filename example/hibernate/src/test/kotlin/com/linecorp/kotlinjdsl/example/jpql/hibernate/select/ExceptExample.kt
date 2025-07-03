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

class ExceptExample : WithAssertions {
    private val entityManagerFactory = EntityManagerFactoryTestUtils.getEntityManagerFactory()
    private val entityManager = entityManagerFactory.createEntityManager()

    private val context = JpqlRenderContextUtils.getJpqlRenderContext()
    private val aliasName = "isbnAlias"

    private val bookIsbn2 = Isbn("02")

    @AfterEach
    fun tearDown() {
        entityManager.close()
    }

    @Test
    fun exceptBooksByPriceAndSalePrice() {
        // When
        val query = jpql {
            val isbnPath = path(Book::isbn).`as`(Expressions.expression(Isbn::class, aliasName))
            except(
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
            bookIsbn2,
        )
    }

    /**
     * Test for EXCEPT ALL operation.
     *
     * Note: This test is disabled because H2 database does not support EXCEPT ALL.
     * H2 only supports EXCEPT but not EXCEPT ALL as of version 2.3.232.
     *
     * References:
     * - H2 Database Commands documentation: https://h2database.com/html/commands.html#select
     * - Modern SQL EXCEPT ALL support: https://modern-sql.com/caniuse/except-all
     *   (Shows H2 versions 1.4.192 - 2.3.232 as not supporting EXCEPT ALL)
     */
    @Test
    @Disabled("H2 database does not support EXCEPT ALL - only EXCEPT is supported")
    fun exceptAllBooksByPriceAndSalePrice() {
        // When
        val query = jpql {
            val isbnPath = path(Book::isbn).`as`(Expressions.expression(Isbn::class, aliasName))
            exceptAll(
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
            bookIsbn2,
        )
    }

    @Test
    fun exceptBooksByPriceAndSalePriceWithNewDsl() {
        // When
        val query = jpql {
            val isbnPath = path(Book::isbn).`as`(Expressions.expression(Isbn::class, aliasName))
            select(
                isbnPath,
            ).from(
                entity(Book::class),
            ).where(
                path(Book::price)(BookPrice::value).lessThan(3.toBigDecimal()),
            ).except(
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
            bookIsbn2,
        )
    }

    /**
     * Test for EXCEPT ALL operation using extension function syntax.
     *
     * Note: This test is disabled because H2 database does not support EXCEPT ALL.
     * H2 only supports EXCEPT but not EXCEPT ALL as of version 2.3.232.
     *
     * References:
     * - H2 Database Commands documentation: https://h2database.com/html/commands.html#select
     * - Modern SQL EXCEPT ALL support: https://modern-sql.com/caniuse/except-all
     *   (Shows H2 versions 1.4.192 - 2.3.232 as not supporting EXCEPT ALL)
     */
    @Test
    @Disabled("H2 database does not support EXCEPT ALL - only EXCEPT is supported")
    fun exceptAllBooksByPriceAndSalePriceWithNewDsl() {
        // When
        val query = jpql {
            val isbnPath = path(Book::isbn).`as`(Expressions.expression(Isbn::class, aliasName))
            select(
                isbnPath,
            ).from(
                entity(Book::class),
            ).where(
                path(Book::price)(BookPrice::value).lessThan(3.toBigDecimal()),
            ).exceptAll(
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
            bookIsbn2,
        )
    }
}
