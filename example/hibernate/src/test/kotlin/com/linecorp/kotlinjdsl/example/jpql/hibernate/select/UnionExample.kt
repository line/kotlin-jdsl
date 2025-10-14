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
import org.junit.jupiter.api.Test

class UnionExample : WithAssertions {
    private val entityManagerFactory = EntityManagerFactoryTestUtils.getEntityManagerFactory()
    private val entityManager = entityManagerFactory.createEntityManager()

    private val context = JpqlRenderContextUtils.getJpqlRenderContext()
    private val aliasName = "isbnAlias"

    private val bookIsbn1 = Isbn("01")
    private val bookIsbn2 = Isbn("02")

    @AfterEach
    fun tearDown() {
        entityManager.close()
    }

    @Test
    fun unionBooksByPriceAndSalePrice() {
        // When
        val query = jpql {
            val isbnPath = path(Book::isbn).`as`(Expressions.expression(Isbn::class, aliasName))
            union(
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
            bookIsbn2,
        )
    }

    @Test
    fun unionAllBooksByPriceAndSalePrice() {
        // When
        val query = jpql {
            val isbnPath = path(Book::isbn).`as`(Expressions.expression(Isbn::class, aliasName))
            unionAll(
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
            bookIsbn2,
            bookIsbn1,
        )
    }

    @Test
    fun unionBooksByPriceAndSalePriceWithNewDsl() {
        // When
        val query = jpql {
            val isbnPath = path(Book::isbn).`as`(Expressions.expression(Isbn::class, aliasName))
            select(
                isbnPath,
            ).from(
                entity(Book::class),
            ).where(
                path(Book::price)(BookPrice::value).lessThan(3.toBigDecimal()),
            ).union(
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
            bookIsbn2,
        )
    }

    @Test
    fun unionAllBooksByPriceAndSalePriceWithNewDsl() {
        // When
        val query = jpql {
            val isbnPath = path(Book::isbn).`as`(Expressions.expression(Isbn::class, aliasName))
            select(
                isbnPath,
            ).from(
                entity(Book::class),
            ).where(
                path(Book::price)(BookPrice::value).lessThan(3.toBigDecimal()),
            ).unionAll(
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
            bookIsbn2,
            bookIsbn1,
        )
    }
}
