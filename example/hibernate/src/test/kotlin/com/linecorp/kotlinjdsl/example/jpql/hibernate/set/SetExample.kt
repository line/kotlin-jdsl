package com.linecorp.kotlinjdsl.example.jpql.hibernate.set

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

class SetExample : WithAssertions {
    private val entityManagerFactory = EntityManagerFactoryTestUtils.getEntityManagerFactory()
    private val entityManager = entityManagerFactory.createEntityManager()

    private val context = JpqlRenderContextUtils.getJpqlRenderContext()
    private val aliasName = "isbnAlias"

    private val bookIsbn1 = Isbn("01")
    private val bookIsbn2 = Isbn("02")
    private val bookIsbn3 = Isbn("03")

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
            bookIsbn1,
            bookIsbn2,
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
            bookIsbn1,
            bookIsbn2,
        )
    }

    // 임시 테스트 메서드 1: ORDER BY 컬럼명
    @Test
    fun temporaryTestUnionAllOrderByColumnName() {
        // Given
        val bookIsbn1 = Isbn("01")
        val bookIsbn2 = Isbn("01")

        // When
        val jpql =
            "(SELECT b.isbn FROM Book b WHERE b.price.value < ?1 " +
                "UNION ALL SELECT b2.isbn FROM Book b2 WHERE b2.salePrice.value < ?2) " +
                "ORDER BY isbn ASC"
        val typedQuery = entityManager.createQuery(jpql, Isbn::class.java)
        typedQuery.setParameter(1, 3.toBigDecimal())
        typedQuery.setParameter(2, 2.toBigDecimal())
        typedQuery.maxResults = 2

        val actual = typedQuery.resultList

        // Then
        println("Temporary Test (ORDER BY isbn) Result: $actual")
        assertThat(actual).containsExactly(
            bookIsbn1,
            bookIsbn2,
        )
    }

    // 임시 테스트 메서드 2: SELECT에 별칭 부여 + ORDER BY 별칭
    @Test
    fun temporaryTestUnionAllOrderByAlias() {
        // Given
        val bookIsbn1 = Isbn("01")
        val bookIsbn2 = Isbn("01")

        // When
        val jpql =
            "(SELECT b.isbn AS sort_key FROM Book b WHERE b.price.value < ?1 " +
                "UNION ALL SELECT b2.isbn AS sort_key FROM Book b2 WHERE b2.salePrice.value < ?2) " +
                "ORDER BY sort_key ASC"
        val typedQuery = entityManager.createQuery(jpql, Isbn::class.java)
        typedQuery.setParameter(1, 3.toBigDecimal())
        typedQuery.setParameter(2, 2.toBigDecimal())
        typedQuery.maxResults = 2

        val actual = typedQuery.resultList

        // Then
        println("Temporary Test (ORDER BY sort_key) Result: $actual")
        assertThat(actual).containsExactly(
            bookIsbn1,
            bookIsbn2,
        )
    }

    // 임시 테스트 메서드 3: UNION + ORDER BY 별칭 + LIMIT
    @Test
    fun temporaryTestUnionOrderByAlias() {
        // Given
        val bookIsbn1 = Isbn("01")
        val bookIsbn2 = Isbn("02")

        // When
        val jpql =
            "(SELECT b.isbn AS bookIsbn FROM Book b WHERE b.price.value < ?1 " +
                "UNION SELECT b2.isbn AS bookIsbn FROM Book b2 WHERE b2.salePrice.value < ?2) " +
                "ORDER BY bookIsbn ASC"
        val typedQuery = entityManager.createQuery(jpql, Isbn::class.java)
        typedQuery.setParameter(1, 3.toBigDecimal())
        typedQuery.setParameter(2, 2.toBigDecimal())

        val actual = typedQuery.resultList

        // Then
        println("Temporary Test (UNION ORDER BY alias) Result: $actual")
        assertThat(actual).containsExactly(
            bookIsbn1,
            bookIsbn2,
        )
    }
}
