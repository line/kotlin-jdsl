package com.linecorp.kotlinjdsl.example.hibernate.reactive.jakarta.jpql.select

import com.linecorp.kotlinjdsl.dsl.jpql.jpql
import com.linecorp.kotlinjdsl.example.hibernate.reactive.SessionFactoryTestUtils
import com.linecorp.kotlinjdsl.example.hibernate.reactive.jakarta.jpql.entity.book.Book
import com.linecorp.kotlinjdsl.example.hibernate.reactive.jakarta.jpql.entity.book.BookPrice
import com.linecorp.kotlinjdsl.example.hibernate.reactive.jakarta.jpql.entity.book.Isbn
import com.linecorp.kotlinjdsl.example.hibernate.reactive.jpql.JpqlRenderContextUtils
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.sort.Sorts
import com.linecorp.kotlinjdsl.support.hibernate.reactive.extension.createQuery
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

class UnionMutinyStatelessSessionExample : WithAssertions {
    private val sessionFactory = SessionFactoryTestUtils.getMutinySessionFactory()

    private val context = JpqlRenderContextUtils.getJpqlRenderContext()

    private val aliasName = "isbnAlias"

    private val bookIsbn1 = Isbn("01")
    private val bookIsbn2 = Isbn("02")

    @Test
    fun unionBooksByPriceAndSalePrice() {
        // When
        val query = jpql {
            val isbnPath = path(Book::isbn).`as`(
                Expressions.expression(
                    Isbn::class,
                    aliasName,
                ),
            )
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

        val actual = sessionFactory.withStatelessSession {
            it.createQuery(query, context).resultList
        }.await().indefinitely()

        // then
        assertThat(actual).containsExactly(
            bookIsbn1,
            bookIsbn2,
        )
    }

    @Test
    fun unionAllBooksByPriceAndSalePrice() {
        // When
        val query = jpql {
            val isbnPath = path(Book::isbn).`as`(
                Expressions.expression(
                    Isbn::class,
                    aliasName,
                ),
            )
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

        val actual = sessionFactory.withStatelessSession {
            it.createQuery(query, context).resultList
        }.await().indefinitely()

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
            val isbnPath = path(Book::isbn).`as`(
                Expressions.expression(
                    Isbn::class,
                    aliasName,
                ),
            )
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

        val actual = sessionFactory.withStatelessSession {
            it.createQuery(query, context).resultList
        }.await().indefinitely()

        // then
        assertThat(actual).containsExactly(
            bookIsbn1,
            bookIsbn2,
        )
    }

    @Test
    fun unionAllBooksByPriceAndSalePriceWithNewDsl() {
        // When
        val query = jpql {
            val isbnPath = path(Book::isbn).`as`(
                Expressions.expression(
                    Isbn::class,
                    aliasName,
                ),
            )
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

        val actual = sessionFactory.withStatelessSession {
            it.createQuery(query, context).resultList
        }.await().indefinitely()

        // Then
        assertThat(actual).containsExactly(
            bookIsbn1,
            bookIsbn2,
            bookIsbn1,
        )
    }
}
