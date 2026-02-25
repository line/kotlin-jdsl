package com.linecorp.kotlinjdsl.example.spring.data.jpa.jpql.select

import com.linecorp.kotlinjdsl.dsl.jpql.Jpql
import com.linecorp.kotlinjdsl.example.spring.data.jpa.jpql.entity.author.Author
import com.linecorp.kotlinjdsl.example.spring.data.jpa.jpql.entity.book.Book
import com.linecorp.kotlinjdsl.example.spring.data.jpa.jpql.entity.book.BookAuthor
import com.linecorp.kotlinjdsl.example.spring.data.jpa.jpql.entity.book.Isbn
import com.linecorp.kotlinjdsl.example.spring.data.jpa.jpql.repository.author.AuthorRepository
import com.linecorp.kotlinjdsl.example.spring.data.jpa.jpql.repository.book.BookRepository
import com.linecorp.kotlinjdsl.querymodel.jpql.JpqlQueryable
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.transaction.annotation.Transactional

@Transactional
@SpringBootTest
class Issue1039Test : WithAssertions {
    @Autowired
    private lateinit var authorRepository: AuthorRepository

    @Autowired
    private lateinit var bookRepository: BookRepository

    data class AuthorDto(
        val id: Long,
        val name: String?,
    )

    @Test
    fun `findPage with selectNew and nullValue - Issue 1039`() {
        // given
        val pageable = PageRequest.of(0, 10)

        // when - Original issue: Spring's parser failed with nullValue() in selectNew
        val actual =
            authorRepository.findPage(pageable) {
                selectNew<AuthorDto>(
                    path(Author::authorId),
                    nullValue<String>(),
                ).from(
                    entity(Author::class),
                )
            }

        // then - Should work and count 4 authors from data.sql
        assertThat(actual.content).isNotEmpty
        assertThat(actual.totalElements).isEqualTo(4L)
    }

    @Test
    fun `findPage with simple select should count correctly across multiple pages`() {
        // given
        val pageable0 = PageRequest.of(0, 2)
        val pageable1 = PageRequest.of(1, 2)

        // when
        val actual0 =
            authorRepository.findPage(pageable0) {
                select(path(Author::authorId))
                    .from(entity(Author::class))
            }
        val actual1 =
            authorRepository.findPage(pageable1) {
                select(path(Author::authorId))
                    .from(entity(Author::class))
            }

        // then - 4 authors in data.sql, totalElements should be 4 for both
        assertThat(actual0.totalElements).isEqualTo(4L)
        assertThat(actual0.content).hasSize(2)

        assertThat(actual1.totalElements).isEqualTo(4L)
        assertThat(actual1.content).hasSize(2)
        assertThat(actual1.content).isNotEqualTo(actual0.content)
    }

    @Test
    fun `findPage with distinct should count correctly without join`() {
        // given
        val pageable = PageRequest.of(0, 10)

        // when - DISTINCT without join (no effect on count)
        val actual =
            authorRepository.findPage(pageable) {
                selectDistinct(path(Author::authorId))
                    .from(entity(Author::class))
            }

        // then - Still 4 authors
        assertThat(actual.totalElements).isEqualTo(4L)
    }

    @Test
    fun `findPage with entity select should count correctly`() {
        // given
        val pageable = PageRequest.of(0, 10)

        // when
        val actual =
            authorRepository.findPage(pageable) {
                select(entity(Author::class))
                    .from(entity(Author::class))
            }

        // then - 4 authors in data.sql
        assertThat(actual.totalElements).isEqualTo(4L)
    }

    @Test
    fun `findPage with join without distinct should count all joined rows including duplicates`() {
        // given
        val pageable = PageRequest.of(0, 10)

        // From data.sql: 15 book_author rows (Book01 has 3 authors, Book02 has 2 authors, others have 1 each)
        // when - JOIN without DISTINCT returns all joined rows
        val actual =
            bookRepository.findPage(pageable) {
                select(path(Book::isbn))
                    .from(
                        entity(Book::class),
                        innerJoin(Book::authors),
                    )
            }

        // then - Should count 15 (all book_author rows including duplicates)
        assertThat(actual.totalElements).isEqualTo(15L)
    }

    @Test
    fun `findPage with join and distinct should count only distinct books`() {
        // given
        val pageable = PageRequest.of(0, 10)

        // From data.sql: 12 distinct books, but 15 book_author rows due to multiple authors per book
        // when - DISTINCT with JOIN should count only distinct books
        val actual =
            bookRepository.findPage(pageable) {
                selectDistinct(path(Book::isbn))
                    .from(
                        entity(Book::class),
                        innerJoin(Book::authors),
                    )
            }

        // then - Should count 12 (distinct books only, not the 15 joined rows)
        assertThat(actual.totalElements).isEqualTo(12L)
    }

    @Test
    fun `findPage with union should count correctly across multiple pages`() {
        // given
        val pageable0 = PageRequest.of(0, 1)
        val pageable1 = PageRequest.of(1, 1)

        // when - Union of books ISBN 01 and ISBN 02
        val actual0 =
            bookRepository.findPage(pageable0) {
                val query1 =
                    select(path(Book::isbn).alias(expression(Isbn::class, "isbn")))
                        .from(entity(Book::class))
                        .where(path(Book::isbn).equal(Isbn("01")))
                val query2 =
                    select(path(Book::isbn).alias(expression(Isbn::class, "isbn")))
                        .from(entity(Book::class))
                        .where(path(Book::isbn).equal(Isbn("02")))

                union(query1, query2)
            }
        val actual1 =
            bookRepository.findPage(pageable1) {
                val query1 =
                    select(path(Book::isbn).alias(expression(Isbn::class, "isbn")))
                        .from(entity(Book::class))
                        .where(path(Book::isbn).equal(Isbn("01")))
                val query2 =
                    select(path(Book::isbn).alias(expression(Isbn::class, "isbn")))
                        .from(entity(Book::class))
                        .where(path(Book::isbn).equal(Isbn("02")))

                union(query1, query2)
            }

        // then - Both should have totalElements = 2
        assertThat(actual0.totalElements).isEqualTo(2L)
        assertThat(actual0.content).hasSize(1)

        assertThat(actual1.totalElements).isEqualTo(2L)
        assertThat(actual1.content).hasSize(1)
        assertThat(actual1.content).isNotEqualTo(actual0.content)
    }

    @Test
    fun `findPage with intersect should count correctly`() {
        // given
        val pageable = PageRequest.of(0, 10)

        // when - Intersect of (01, 02) and (02, 03)
        val actual =
            bookRepository.findPage(pageable) {
                val query1 =
                    select(path(Book::isbn).alias(expression(Isbn::class, "isbn")))
                        .from(entity(Book::class))
                        .where(path(Book::isbn).`in`(Isbn("01"), Isbn("02")))
                val query2 =
                    select(path(Book::isbn).alias(expression(Isbn::class, "isbn")))
                        .from(entity(Book::class))
                        .where(path(Book::isbn).`in`(Isbn("02"), Isbn("03")))

                intersect(query1, query2)
            }

        // then - Should count 1 (ISBN 02)
        assertThat(actual.totalElements).isEqualTo(1L)
    }

    @Test
    fun `findPage with except should count correctly`() {
        // given
        val pageable = PageRequest.of(0, 10)

        // when - Except (01, 02 minus 02, 03)
        val actual =
            bookRepository.findPage(pageable) {
                val query1 =
                    select(path(Book::isbn).alias(expression(Isbn::class, "isbn")))
                        .from(entity(Book::class))
                        .where(path(Book::isbn).`in`(Isbn("01"), Isbn("02")))
                val query2 =
                    select(path(Book::isbn).alias(expression(Isbn::class, "isbn")))
                        .from(entity(Book::class))
                        .where(path(Book::isbn).`in`(Isbn("02"), Isbn("03")))

                except(query1, query2)
            }

        // then - Should count 1 (ISBN 01)
        assertThat(actual.totalElements).isEqualTo(1L)
    }

    @Test
    fun `findPage with fetchJoin should work by stripping fetch from count query`() {
        // given
        val pageable = PageRequest.of(0, 10)

        // when - FETCH join is allowed in select query but forbidden in count query
        val actual =
            bookRepository.findPage(pageable) {
                select(entity(Book::class))
                    .from(
                        entity(Book::class),
                        fetchJoin(Book::authors),
                    )
            }

        // then - Should count 15 (stripped fetch results in normal join behavior)
        assertThat(actual.totalElements).isEqualTo(15L)
    }

    @Test
    fun `findPage with groupBy and having should paginate and count correctly`() {
        // given - page size 1 so grouped results span multiple pages
        val firstPageable = PageRequest.of(0, 1)
        val secondPageable = PageRequest.of(1, 1)
        // when - Group books by ISBN and keep only specific ISBNs via HAVING
        val firstPage =
            bookRepository.findPage(firstPageable) {
                select(path(Book::isbn))
                    .from(entity(Book::class))
                    .groupBy(path(Book::isbn))
                    .having(path(Book::isbn).`in`(Isbn("01"), Isbn("02")))
            }
        val secondPage =
            bookRepository.findPage(secondPageable) {
                select(path(Book::isbn))
                    .from(entity(Book::class))
                    .groupBy(path(Book::isbn))
                    .having(path(Book::isbn).`in`(Isbn("01"), Isbn("02")))
            }
        // then - There should be 2 grouped results (ISBN 01 and 02) across pages
        assertThat(firstPage.totalElements).isEqualTo(2L)
        assertThat(secondPage.totalElements).isEqualTo(2L)
        assertThat(firstPage.content).hasSize(1)
        assertThat(secondPage.content).hasSize(1)
    }
}
