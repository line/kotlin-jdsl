package com.linecorp.kotlinjdsl.dsl.jpql.join

import com.linecorp.kotlinjdsl.dsl.jpql.entity.author.Author
import com.linecorp.kotlinjdsl.dsl.jpql.entity.book.Book
import com.linecorp.kotlinjdsl.dsl.jpql.entity.book.BookAuthor
import com.linecorp.kotlinjdsl.dsl.jpql.entity.book.BookPublisher
import com.linecorp.kotlinjdsl.dsl.jpql.queryPart
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entities
import com.linecorp.kotlinjdsl.querymodel.jpql.join.Join
import com.linecorp.kotlinjdsl.querymodel.jpql.join.Joins
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicates
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

class JoinDslTest : WithAssertions {
    private val entity1 = Entities.entity(BookAuthor::class)
    private val entity2 = Entities.entity(BookPublisher::class)

    private val path1 = Paths.path(Book::authors)
    private val path2 = Paths.path(Book::publisher)

    private val alias1 = Entities.entity(BookAuthor::class, "author01")

    private val predicate1 = Predicates.equal(
        Paths.path(BookAuthor::authorId),
        Paths.path(Author::authorId),
    )

    @Test
    fun `join() with a class`() {
        // when
        val join = queryPart {
            join(BookAuthor::class).on(predicate1)
        }

        val actual: Join = join.toJoin()

        // then
        val expected = Joins.innerJoin(
            entity = Entities.entity(BookAuthor::class),
            predicate = predicate1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `join() with a class and an alias`() {
        // when
        val join1 = queryPart {
            join(BookAuthor::class).on(predicate1).`as`(alias1)
        }

        val join2 = queryPart {
            join(BookAuthor::class).on(predicate1).alias(alias1)
        }

        val actual1: Join = join1.toJoin()
        val actual2: Join = join2.toJoin()

        // then
        val expected = Joins.innerJoin(
            entity = alias1,
            predicate = predicate1,
        )

        assertThat(actual1).isEqualTo(expected)
        assertThat(actual2).isEqualTo(expected)
    }

    @Test
    fun `join() with an entity`() {
        // when
        val join = queryPart {
            join(entity1).on(predicate1)
        }

        val actual: Join = join.toJoin()

        // then
        val expected = Joins.innerJoin(
            entity = entity1,
            predicate = predicate1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `join() with an entity and an alias`() {
        // when
        val join1 = queryPart {
            join(entity1).on(predicate1).`as`(alias1)
        }

        val join2 = queryPart {
            join(entity1).on(predicate1).alias(alias1)
        }

        val actual1: Join = join1.toJoin()
        val actual2: Join = join2.toJoin()

        // then
        val expected = Joins.innerJoin(
            entity = alias1,
            predicate = predicate1,
        )

        assertThat(actual1).isEqualTo(expected)
        assertThat(actual2).isEqualTo(expected)
    }

    @Test
    fun `join() with a property`() {
        // when
        val join1 = queryPart {
            join(Book::authors)
        }

        val join2 = queryPart {
            join(Book::publisher)
        }

        val actual1: Join = join1.toJoin()
        val actual2: Join = join2.toJoin()

        // then
        val expected1 = Joins.innerJoin(
            entity = Entities.entity(BookAuthor::class),
            association = Paths.path(Book::authors),
        )

        val expected2 = Joins.innerJoin(
            entity = Entities.entity(BookPublisher::class),
            association = Paths.path(Book::publisher),
        )

        assertThat(actual1).isEqualTo(expected1)
        assertThat(actual2).isEqualTo(expected2)
    }

    @Test
    fun `join() with a property and a predicate`() {
        // when
        val join = queryPart {
            join(Book::authors).on(predicate1)
        }

        val actual: Join = join.toJoin()

        // then
        val expected = Joins.innerJoin(
            entity = Entities.entity(BookAuthor::class),
            association = Paths.path(Book::authors),
            predicate = predicate1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `join() with a property and an alias`() {
        // when
        val join1 = queryPart {
            join(Book::authors).`as`(alias1)
        }

        val join2 = queryPart {
            join(Book::authors).alias(alias1)
        }

        val actual1: Join = join1.toJoin()
        val actual2: Join = join2.toJoin()

        // then
        val expected = Joins.innerJoin(
            entity = alias1,
            association = Paths.path(Book::authors),
        )

        assertThat(actual1).isEqualTo(expected)
        assertThat(actual2).isEqualTo(expected)
    }

    @Test
    fun `join() with a property, a predicate, and an alias`() {
        // when
        val join1 = queryPart {
            join(Book::authors).on(predicate1).`as`(alias1)
        }

        val join2 = queryPart {
            join(Book::authors).on(predicate1).alias(alias1)
        }

        val actual1: Join = join1.toJoin()
        val actual2: Join = join2.toJoin()

        // then
        val expected = Joins.innerJoin(
            entity = alias1,
            association = Paths.path(Book::authors),
            predicate = predicate1,
        )

        assertThat(actual1).isEqualTo(expected)
        assertThat(actual2).isEqualTo(expected)
    }

    @Test
    fun `join() with a path`() {
        // when
        val join1 = queryPart {
            join(path1)
        }

        val join2 = queryPart {
            join(path2)
        }

        val actual1: Join = join1.toJoin()
        val actual2: Join = join2.toJoin()

        // then
        val expected1 = Joins.innerJoin(
            entity = entity1,
            association = path1,
        )

        val expected2 = Joins.innerJoin(
            entity = entity2,
            association = path2,
        )

        assertThat(actual1).isEqualTo(expected1)
        assertThat(actual2).isEqualTo(expected2)
    }

    @Test
    fun `join() with a path and a predicate`() {
        // when
        val join = queryPart {
            join(path1).on(predicate1)
        }

        val actual: Join = join.toJoin()

        // then
        val expected = Joins.innerJoin(
            entity = entity1,
            association = path1,
            predicate = predicate1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `join() with a path and an alias`() {
        // when
        val join1 = queryPart {
            join(path1).`as`(alias1)
        }

        val join2 = queryPart {
            join(path1).alias(alias1)
        }

        val actual1: Join = join1.toJoin()
        val actual2: Join = join2.toJoin()

        // then
        val expected = Joins.innerJoin(
            entity = alias1,
            association = path1,
        )

        assertThat(actual1).isEqualTo(expected)
        assertThat(actual2).isEqualTo(expected)
    }

    @Test
    fun `join() with a path, a predicate, and an alias`() {
        // when
        val join1 = queryPart {
            join(path1).on(predicate1).`as`(alias1)
        }

        val join2 = queryPart {
            join(path1).on(predicate1).alias(alias1)
        }

        val actual1: Join = join1.toJoin()
        val actual2: Join = join2.toJoin()

        // then
        val expected = Joins.innerJoin(
            entity = alias1,
            association = path1,
            predicate = predicate1,
        )

        assertThat(actual1).isEqualTo(expected)
        assertThat(actual2).isEqualTo(expected)
    }

    @Test
    fun `innerJoin() with a class`() {
        // when
        val join = queryPart {
            innerJoin(BookAuthor::class).on(predicate1)
        }

        val actual: Join = join.toJoin()

        // then
        val expected = Joins.innerJoin(
            entity = Entities.entity(BookAuthor::class),
            predicate = predicate1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `innerJoin() with a class and an alias`() {
        // when
        val join1 = queryPart {
            innerJoin(BookAuthor::class).on(predicate1).`as`(alias1)
        }

        val join2 = queryPart {
            innerJoin(BookAuthor::class).on(predicate1).alias(alias1)
        }

        val actual1: Join = join1.toJoin()
        val actual2: Join = join2.toJoin()

        // then
        val expected = Joins.innerJoin(
            entity = alias1,
            predicate = predicate1,
        )

        assertThat(actual1).isEqualTo(expected)
        assertThat(actual2).isEqualTo(expected)
    }

    @Test
    fun `innerJoin() with an entity`() {
        // when
        val join = queryPart {
            innerJoin(entity1).on(predicate1)
        }

        val actual: Join = join.toJoin()

        // then
        val expected = Joins.innerJoin(
            entity = entity1,
            predicate = predicate1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `innerJoin() with an entity and an alias`() {
        // when
        val join1 = queryPart {
            innerJoin(entity1).on(predicate1).`as`(alias1)
        }

        val join2 = queryPart {
            innerJoin(entity1).on(predicate1).alias(alias1)
        }

        val actual1: Join = join1.toJoin()
        val actual2: Join = join2.toJoin()

        // then
        val expected = Joins.innerJoin(
            entity = alias1,
            predicate = predicate1,
        )

        assertThat(actual1).isEqualTo(expected)
        assertThat(actual2).isEqualTo(expected)
    }

    @Test
    fun `innerJoin() with a property`() {
        // when
        val join1 = queryPart {
            innerJoin(Book::authors)
        }

        val join2 = queryPart {
            innerJoin(Book::publisher)
        }

        val actual1: Join = join1.toJoin()
        val actual2: Join = join2.toJoin()

        // then
        val expected1 = Joins.innerJoin(
            entity = Entities.entity(BookAuthor::class),
            association = Paths.path(Book::authors),
        )

        val expected2 = Joins.innerJoin(
            entity = Entities.entity(BookPublisher::class),
            association = Paths.path(Book::publisher),
        )

        assertThat(actual1).isEqualTo(expected1)
        assertThat(actual2).isEqualTo(expected2)
    }

    @Test
    fun `innerJoin() with a property and a predicate`() {
        // when
        val join = queryPart {
            innerJoin(Book::authors).on(predicate1)
        }

        val actual: Join = join.toJoin()

        // then
        val expected = Joins.innerJoin(
            entity = Entities.entity(BookAuthor::class),
            association = Paths.path(Book::authors),
            predicate = predicate1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `innerJoin() with a property and an alias`() {
        // when
        val join1 = queryPart {
            innerJoin(Book::authors).`as`(alias1)
        }

        val join2 = queryPart {
            innerJoin(Book::authors).alias(alias1)
        }

        val actual1: Join = join1.toJoin()
        val actual2: Join = join2.toJoin()

        // then
        val expected = Joins.innerJoin(
            entity = alias1,
            association = Paths.path(Book::authors),
        )

        assertThat(actual1).isEqualTo(expected)
        assertThat(actual2).isEqualTo(expected)
    }

    @Test
    fun `innerJoin() with a property, a predicate, and an alias`() {
        // when
        val join1 = queryPart {
            innerJoin(Book::authors).on(predicate1).`as`(alias1)
        }

        val join2 = queryPart {
            innerJoin(Book::authors).on(predicate1).alias(alias1)
        }

        val actual1: Join = join1.toJoin()
        val actual2: Join = join2.toJoin()

        // then
        val expected = Joins.innerJoin(
            entity = alias1,
            association = Paths.path(Book::authors),
            predicate = predicate1,
        )

        assertThat(actual1).isEqualTo(expected)
        assertThat(actual2).isEqualTo(expected)
    }

    @Test
    fun `innerJoin() with a path`() {
        // when
        val join1 = queryPart {
            innerJoin(path1)
        }

        val join2 = queryPart {
            innerJoin(path2)
        }

        val actual1: Join = join1.toJoin()
        val actual2: Join = join2.toJoin()

        // then
        val expected1 = Joins.innerJoin(
            entity = entity1,
            association = path1,
        )

        val expected2 = Joins.innerJoin(
            entity = entity2,
            association = path2,
        )

        assertThat(actual1).isEqualTo(expected1)
        assertThat(actual2).isEqualTo(expected2)
    }

    @Test
    fun `innerJoin() with a path and a predicate`() {
        // when
        val join = queryPart {
            innerJoin(path1).on(predicate1)
        }

        val actual: Join = join.toJoin()

        // then
        val expected = Joins.innerJoin(
            entity = entity1,
            association = path1,
            predicate = predicate1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `innerJoin() with a path and an alias`() {
        // when
        val join1 = queryPart {
            innerJoin(path1).`as`(alias1)
        }

        val join2 = queryPart {
            innerJoin(path1).alias(alias1)
        }

        val actual1: Join = join1.toJoin()
        val actual2: Join = join2.toJoin()

        // then
        val expected = Joins.innerJoin(
            entity = alias1,
            association = path1,
        )

        assertThat(actual1).isEqualTo(expected)
        assertThat(actual2).isEqualTo(expected)
    }

    @Test
    fun `innerJoin() with a path, a predicate, and an alias`() {
        // when
        val join1 = queryPart {
            innerJoin(path1).on(predicate1).`as`(alias1)
        }

        val join2 = queryPart {
            innerJoin(path1).on(predicate1).alias(alias1)
        }

        val actual1: Join = join1.toJoin()
        val actual2: Join = join2.toJoin()

        // then
        val expected = Joins.innerJoin(
            entity = alias1,
            association = path1,
            predicate = predicate1,
        )

        assertThat(actual1).isEqualTo(expected)
        assertThat(actual2).isEqualTo(expected)
    }

    @Test
    fun `leftJoin() with a class`() {
        // when
        val join = queryPart {
            leftJoin(BookAuthor::class).on(predicate1)
        }

        val actual: Join = join.toJoin()

        // then
        val expected = Joins.leftJoin(
            entity = Entities.entity(BookAuthor::class),
            predicate = predicate1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `leftJoin() with a class and an alias`() {
        // when
        val join1 = queryPart {
            leftJoin(BookAuthor::class).on(predicate1).`as`(alias1)
        }

        val join2 = queryPart {
            leftJoin(BookAuthor::class).on(predicate1).alias(alias1)
        }

        val actual1: Join = join1.toJoin()
        val actual2: Join = join2.toJoin()

        // then
        val expected = Joins.leftJoin(
            entity = alias1,
            predicate = predicate1,
        )

        assertThat(actual1).isEqualTo(expected)
        assertThat(actual2).isEqualTo(expected)
    }

    @Test
    fun `leftJoin() with an entity`() {
        // when
        val join = queryPart {
            leftJoin(entity1).on(predicate1)
        }

        val actual: Join = join.toJoin()

        // then
        val expected = Joins.leftJoin(
            entity = entity1,
            predicate = predicate1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `leftJoin() with an entity and an alias`() {
        // when
        val join1 = queryPart {
            leftJoin(entity1).on(predicate1).`as`(alias1)
        }

        val join2 = queryPart {
            leftJoin(entity1).on(predicate1).alias(alias1)
        }

        val actual1: Join = join1.toJoin()
        val actual2: Join = join2.toJoin()

        // then
        val expected = Joins.leftJoin(
            entity = alias1,
            predicate = predicate1,
        )

        assertThat(actual1).isEqualTo(expected)
        assertThat(actual2).isEqualTo(expected)
    }

    @Test
    fun `leftJoin() with a property`() {
        // when
        val join1 = queryPart {
            leftJoin(Book::authors)
        }

        val join2 = queryPart {
            leftJoin(Book::publisher)
        }

        val actual1: Join = join1.toJoin()
        val actual2: Join = join2.toJoin()

        // then
        val expected1 = Joins.leftJoin(
            entity = Entities.entity(BookAuthor::class),
            association = Paths.path(Book::authors),
        )

        val expected2 = Joins.leftJoin(
            entity = Entities.entity(BookPublisher::class),
            association = Paths.path(Book::publisher),
        )

        assertThat(actual1).isEqualTo(expected1)
        assertThat(actual2).isEqualTo(expected2)
    }

    @Test
    fun `leftJoin() with a property and a predicate`() {
        // when
        val join = queryPart {
            leftJoin(Book::authors).on(predicate1)
        }

        val actual: Join = join.toJoin()

        // then
        val expected = Joins.leftJoin(
            entity = Entities.entity(BookAuthor::class),
            association = Paths.path(Book::authors),
            predicate = predicate1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `leftJoin() with a property and an alias`() {
        // when
        val join1 = queryPart {
            leftJoin(Book::authors).`as`(alias1)
        }

        val join2 = queryPart {
            leftJoin(Book::authors).alias(alias1)
        }

        val actual1: Join = join1.toJoin()
        val actual2: Join = join2.toJoin()

        // then
        val expected = Joins.leftJoin(
            entity = alias1,
            association = Paths.path(Book::authors),
        )

        assertThat(actual1).isEqualTo(expected)
        assertThat(actual2).isEqualTo(expected)
    }

    @Test
    fun `leftJoin() with a property, a predicate, and an alias`() {
        // when
        val join1 = queryPart {
            leftJoin(Book::authors).on(predicate1).`as`(alias1)
        }

        val join2 = queryPart {
            leftJoin(Book::authors).on(predicate1).alias(alias1)
        }

        val actual1: Join = join1.toJoin()
        val actual2: Join = join2.toJoin()

        // then
        val expected = Joins.leftJoin(
            entity = alias1,
            association = Paths.path(Book::authors),
            predicate = predicate1,
        )

        assertThat(actual1).isEqualTo(expected)
        assertThat(actual2).isEqualTo(expected)
    }

    @Test
    fun `leftJoin() with a path`() {
        // when
        val join1 = queryPart {
            leftJoin(path1)
        }

        val join2 = queryPart {
            leftJoin(path2)
        }

        val actual1: Join = join1.toJoin()
        val actual2: Join = join2.toJoin()

        // then
        val expected1 = Joins.leftJoin(
            entity = entity1,
            association = path1,
        )

        val expected2 = Joins.leftJoin(
            entity = entity2,
            association = path2,
        )

        assertThat(actual1).isEqualTo(expected1)
        assertThat(actual2).isEqualTo(expected2)
    }

    @Test
    fun `leftJoin() with a path and a predicate`() {
        // when
        val join = queryPart {
            leftJoin(path1).on(predicate1)
        }

        val actual: Join = join.toJoin()

        // then
        val expected = Joins.leftJoin(
            entity = entity1,
            association = path1,
            predicate = predicate1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `leftJoin() with a path and an alias`() {
        // when
        val join1 = queryPart {
            leftJoin(path1).`as`(alias1)
        }

        val join2 = queryPart {
            leftJoin(path1).alias(alias1)
        }

        val actual1: Join = join1.toJoin()
        val actual2: Join = join2.toJoin()

        // then
        val expected = Joins.leftJoin(
            entity = alias1,
            association = path1,
        )

        assertThat(actual1).isEqualTo(expected)
        assertThat(actual2).isEqualTo(expected)
    }

    @Test
    fun `leftJoin() with a path, a predicate, and an alias`() {
        // when
        val join1 = queryPart {
            leftJoin(path1).on(predicate1).`as`(alias1)
        }

        val join2 = queryPart {
            leftJoin(path1).on(predicate1).alias(alias1)
        }

        val actual1: Join = join1.toJoin()
        val actual2: Join = join2.toJoin()

        // then
        val expected = Joins.leftJoin(
            entity = alias1,
            association = path1,
            predicate = predicate1,
        )

        assertThat(actual1).isEqualTo(expected)
        assertThat(actual2).isEqualTo(expected)
    }

    @Test
    fun `fetchJoin() with a class`() {
        // when
        val join = queryPart {
            fetchJoin(BookAuthor::class).on(predicate1)
        }

        val actual: Join = join.toJoin()

        // then
        val expected = Joins.innerFetchJoin(
            entity = Entities.entity(BookAuthor::class),
            predicate = predicate1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `fetchJoin() with a class and an alias`() {
        // when
        val join1 = queryPart {
            fetchJoin(BookAuthor::class).on(predicate1).`as`(alias1)
        }

        val join2 = queryPart {
            fetchJoin(BookAuthor::class).on(predicate1).alias(alias1)
        }

        val actual1: Join = join1.toJoin()
        val actual2: Join = join2.toJoin()

        // then
        val expected = Joins.innerFetchJoin(
            entity = alias1,
            predicate = predicate1,
        )

        assertThat(actual1).isEqualTo(expected)
        assertThat(actual2).isEqualTo(expected)
    }

    @Test
    fun `fetchJoin() with an entity`() {
        // when
        val join = queryPart {
            fetchJoin(entity1).on(predicate1)
        }

        val actual: Join = join.toJoin()

        // then
        val expected = Joins.innerFetchJoin(
            entity = entity1,
            predicate = predicate1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `fetchJoin() with an entity and an alias`() {
        // when
        val join1 = queryPart {
            fetchJoin(entity1).on(predicate1).`as`(alias1)
        }

        val join2 = queryPart {
            fetchJoin(entity1).on(predicate1).alias(alias1)
        }

        val actual1: Join = join1.toJoin()
        val actual2: Join = join2.toJoin()

        // then
        val expected = Joins.innerFetchJoin(
            entity = alias1,
            predicate = predicate1,
        )

        assertThat(actual1).isEqualTo(expected)
        assertThat(actual2).isEqualTo(expected)
    }

    @Test
    fun `fetchJoin() with a property`() {
        // when
        val join1 = queryPart {
            fetchJoin(Book::authors)
        }

        val join2 = queryPart {
            fetchJoin(Book::publisher)
        }

        val actual1: Join = join1.toJoin()
        val actual2: Join = join2.toJoin()

        // then
        val expected1 = Joins.innerFetchJoin(
            entity = Entities.entity(BookAuthor::class),
            association = Paths.path(Book::authors),
        )

        val expected2 = Joins.innerFetchJoin(
            entity = Entities.entity(BookPublisher::class),
            association = Paths.path(Book::publisher),
        )

        assertThat(actual1).isEqualTo(expected1)
        assertThat(actual2).isEqualTo(expected2)
    }

    @Test
    fun `fetchJoin() with a property and a predicate`() {
        // when
        val join = queryPart {
            fetchJoin(Book::authors).on(predicate1)
        }

        val actual: Join = join.toJoin()

        // then
        val expected = Joins.innerFetchJoin(
            entity = Entities.entity(BookAuthor::class),
            association = Paths.path(Book::authors),
            predicate = predicate1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `fetchJoin() with a property and an alias`() {
        // when
        val join1 = queryPart {
            fetchJoin(Book::authors).`as`(alias1)
        }

        val join2 = queryPart {
            fetchJoin(Book::authors).alias(alias1)
        }

        val actual1: Join = join1.toJoin()
        val actual2: Join = join2.toJoin()

        // then
        val expected = Joins.innerFetchJoin(
            entity = alias1,
            association = Paths.path(Book::authors),
        )

        assertThat(actual1).isEqualTo(expected)
        assertThat(actual2).isEqualTo(expected)
    }

    @Test
    fun `fetchJoin() with a property, a predicate, and an alias`() {
        // when
        val join1 = queryPart {
            fetchJoin(Book::authors).on(predicate1).`as`(alias1)
        }

        val join2 = queryPart {
            fetchJoin(Book::authors).on(predicate1).alias(alias1)
        }

        val actual1: Join = join1.toJoin()
        val actual2: Join = join2.toJoin()

        // then
        val expected = Joins.innerFetchJoin(
            entity = alias1,
            association = Paths.path(Book::authors),
            predicate = predicate1,
        )

        assertThat(actual1).isEqualTo(expected)
        assertThat(actual2).isEqualTo(expected)
    }

    @Test
    fun `fetchJoin() with a path`() {
        // when
        val join1 = queryPart {
            fetchJoin(path1)
        }

        val join2 = queryPart {
            fetchJoin(path2)
        }

        val actual1: Join = join1.toJoin()
        val actual2: Join = join2.toJoin()

        // then
        val expected1 = Joins.innerFetchJoin(
            entity = entity1,
            association = path1,
        )

        val expected2 = Joins.innerFetchJoin(
            entity = entity2,
            association = path2,
        )

        assertThat(actual1).isEqualTo(expected1)
        assertThat(actual2).isEqualTo(expected2)
    }

    @Test
    fun `fetchJoin() with a path and a predicate`() {
        // when
        val join = queryPart {
            fetchJoin(path1).on(predicate1)
        }

        val actual: Join = join.toJoin()

        // then
        val expected = Joins.innerFetchJoin(
            entity = entity1,
            association = path1,
            predicate = predicate1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `fetchJoin() with a path and an alias`() {
        // when
        val join1 = queryPart {
            fetchJoin(path1).`as`(alias1)
        }

        val join2 = queryPart {
            fetchJoin(path1).alias(alias1)
        }

        val actual1: Join = join1.toJoin()
        val actual2: Join = join2.toJoin()

        // then
        val expected = Joins.innerFetchJoin(
            entity = alias1,
            association = path1,
        )

        assertThat(actual1).isEqualTo(expected)
        assertThat(actual2).isEqualTo(expected)
    }

    @Test
    fun `fetchJoin() with a path, a predicate, and an alias`() {
        // when
        val join1 = queryPart {
            fetchJoin(path1).on(predicate1).`as`(alias1)
        }

        val join2 = queryPart {
            fetchJoin(path1).on(predicate1).alias(alias1)
        }

        val actual1: Join = join1.toJoin()
        val actual2: Join = join2.toJoin()

        // then
        val expected = Joins.innerFetchJoin(
            entity = alias1,
            association = path1,
            predicate = predicate1,
        )

        assertThat(actual1).isEqualTo(expected)
        assertThat(actual2).isEqualTo(expected)
    }

    @Test
    fun `innerFetchJoin() with a class`() {
        // when
        val join = queryPart {
            innerFetchJoin(BookAuthor::class).on(predicate1)
        }

        val actual: Join = join.toJoin()

        // then
        val expected = Joins.innerFetchJoin(
            entity = Entities.entity(BookAuthor::class),
            predicate = predicate1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `innerFetchJoin() with a class and an alias`() {
        // when
        val join1 = queryPart {
            innerFetchJoin(BookAuthor::class).on(predicate1).`as`(alias1)
        }

        val join2 = queryPart {
            innerFetchJoin(BookAuthor::class).on(predicate1).alias(alias1)
        }

        val actual1: Join = join1.toJoin()
        val actual2: Join = join2.toJoin()

        // then
        val expected = Joins.innerFetchJoin(
            entity = alias1,
            predicate = predicate1,
        )

        assertThat(actual1).isEqualTo(expected)
        assertThat(actual2).isEqualTo(expected)
    }

    @Test
    fun `innerFetchJoin() with an entity`() {
        // when
        val join = queryPart {
            innerFetchJoin(entity1).on(predicate1)
        }

        val actual: Join = join.toJoin()

        // then
        val expected = Joins.innerFetchJoin(
            entity = entity1,
            predicate = predicate1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `innerFetchJoin() with an entity and an alias`() {
        // when
        val join1 = queryPart {
            innerFetchJoin(entity1).on(predicate1).`as`(alias1)
        }

        val join2 = queryPart {
            innerFetchJoin(entity1).on(predicate1).alias(alias1)
        }

        val actual1: Join = join1.toJoin()
        val actual2: Join = join2.toJoin()

        // then
        val expected = Joins.innerFetchJoin(
            entity = alias1,
            predicate = predicate1,
        )

        assertThat(actual1).isEqualTo(expected)
        assertThat(actual2).isEqualTo(expected)
    }

    @Test
    fun `innerFetchJoin() with a property`() {
        // when
        val join1 = queryPart {
            innerFetchJoin(Book::authors)
        }

        val join2 = queryPart {
            innerFetchJoin(Book::publisher)
        }

        val actual1: Join = join1.toJoin()
        val actual2: Join = join2.toJoin()

        // then
        val expected1 = Joins.innerFetchJoin(
            entity = Entities.entity(BookAuthor::class),
            association = Paths.path(Book::authors),
        )

        val expected2 = Joins.innerFetchJoin(
            entity = Entities.entity(BookPublisher::class),
            association = Paths.path(Book::publisher),
        )

        assertThat(actual1).isEqualTo(expected1)
        assertThat(actual2).isEqualTo(expected2)
    }

    @Test
    fun `innerFetchJoin() with a property and a predicate`() {
        // when
        val join = queryPart {
            innerFetchJoin(Book::authors).on(predicate1)
        }

        val actual: Join = join.toJoin()

        // then
        val expected = Joins.innerFetchJoin(
            entity = Entities.entity(BookAuthor::class),
            association = Paths.path(Book::authors),
            predicate = predicate1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `innerFetchJoin() with a property and an alias`() {
        // when
        val join1 = queryPart {
            innerFetchJoin(Book::authors).`as`(alias1)
        }

        val join2 = queryPart {
            innerFetchJoin(Book::authors).alias(alias1)
        }

        val actual1: Join = join1.toJoin()
        val actual2: Join = join2.toJoin()

        // then
        val expected = Joins.innerFetchJoin(
            entity = alias1,
            association = Paths.path(Book::authors),
        )

        assertThat(actual1).isEqualTo(expected)
        assertThat(actual2).isEqualTo(expected)
    }

    @Test
    fun `innerFetchJoin() with a property, a predicate, and an alias`() {
        // when
        val join1 = queryPart {
            innerFetchJoin(Book::authors).on(predicate1).`as`(alias1)
        }

        val join2 = queryPart {
            innerFetchJoin(Book::authors).on(predicate1).alias(alias1)
        }

        val actual1: Join = join1.toJoin()
        val actual2: Join = join2.toJoin()

        // then
        val expected = Joins.innerFetchJoin(
            entity = alias1,
            association = Paths.path(Book::authors),
            predicate = predicate1,
        )

        assertThat(actual1).isEqualTo(expected)
        assertThat(actual2).isEqualTo(expected)
    }

    @Test
    fun `innerFetchJoin() with a path`() {
        // when
        val join1 = queryPart {
            innerFetchJoin(path1)
        }

        val join2 = queryPart {
            innerFetchJoin(path2)
        }

        val actual1: Join = join1.toJoin()
        val actual2: Join = join2.toJoin()

        // then
        val expected1 = Joins.innerFetchJoin(
            entity = entity1,
            association = path1,
        )

        val expected2 = Joins.innerFetchJoin(
            entity = entity2,
            association = path2,
        )

        assertThat(actual1).isEqualTo(expected1)
        assertThat(actual2).isEqualTo(expected2)
    }

    @Test
    fun `innerFetchJoin() with a path and a predicate`() {
        // when
        val join = queryPart {
            innerFetchJoin(path1).on(predicate1)
        }

        val actual: Join = join.toJoin()

        // then
        val expected = Joins.innerFetchJoin(
            entity = entity1,
            association = path1,
            predicate = predicate1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `innerFetchJoin() with a path and an alias`() {
        // when
        val join1 = queryPart {
            innerFetchJoin(path1).`as`(alias1)
        }

        val join2 = queryPart {
            innerFetchJoin(path1).alias(alias1)
        }

        val actual1: Join = join1.toJoin()
        val actual2: Join = join2.toJoin()

        // then
        val expected = Joins.innerFetchJoin(
            entity = alias1,
            association = path1,
        )

        assertThat(actual1).isEqualTo(expected)
        assertThat(actual2).isEqualTo(expected)
    }

    @Test
    fun `innerFetchJoin() with a path, a predicate, and an alias`() {
        // when
        val join1 = queryPart {
            innerFetchJoin(path1).on(predicate1).`as`(alias1)
        }

        val join2 = queryPart {
            innerFetchJoin(path1).on(predicate1).alias(alias1)
        }

        val actual1: Join = join1.toJoin()
        val actual2: Join = join2.toJoin()

        // then
        val expected = Joins.innerFetchJoin(
            entity = alias1,
            association = path1,
            predicate = predicate1,
        )

        assertThat(actual1).isEqualTo(expected)
        assertThat(actual2).isEqualTo(expected)
    }

    @Test
    fun `leftFetchJoin() with a class`() {
        // when
        val join = queryPart {
            leftFetchJoin(BookAuthor::class).on(predicate1)
        }

        val actual: Join = join.toJoin()

        // then
        val expected = Joins.leftFetchJoin(
            entity = Entities.entity(BookAuthor::class),
            predicate = predicate1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `leftFetchJoin() with a class and an alias`() {
        // when
        val join1 = queryPart {
            leftFetchJoin(BookAuthor::class).on(predicate1).`as`(alias1)
        }

        val join2 = queryPart {
            leftFetchJoin(BookAuthor::class).on(predicate1).alias(alias1)
        }

        val actual1: Join = join1.toJoin()
        val actual2: Join = join2.toJoin()

        // then
        val expected = Joins.leftFetchJoin(
            entity = alias1,
            predicate = predicate1,
        )

        assertThat(actual1).isEqualTo(expected)
        assertThat(actual2).isEqualTo(expected)
    }

    @Test
    fun `leftFetchJoin() with an entity`() {
        // when
        val join = queryPart {
            leftFetchJoin(entity1).on(predicate1)
        }

        val actual: Join = join.toJoin()

        // then
        val expected = Joins.leftFetchJoin(
            entity = entity1,
            predicate = predicate1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `leftFetchJoin() with an entity and an alias`() {
        // when
        val join1 = queryPart {
            leftFetchJoin(entity1).on(predicate1).`as`(alias1)
        }

        val join2 = queryPart {
            leftFetchJoin(entity1).on(predicate1).alias(alias1)
        }

        val actual1: Join = join1.toJoin()
        val actual2: Join = join2.toJoin()

        // then
        val expected = Joins.leftFetchJoin(
            entity = alias1,
            predicate = predicate1,
        )

        assertThat(actual1).isEqualTo(expected)
        assertThat(actual2).isEqualTo(expected)
    }

    @Test
    fun `leftFetchJoin() with a property`() {
        // when
        val join1 = queryPart {
            leftFetchJoin(Book::authors)
        }

        val join2 = queryPart {
            leftFetchJoin(Book::publisher)
        }

        val actual1: Join = join1.toJoin()
        val actual2: Join = join2.toJoin()

        // then
        val expected1 = Joins.leftFetchJoin(
            entity = Entities.entity(BookAuthor::class),
            association = Paths.path(Book::authors),
        )

        val expected2 = Joins.leftFetchJoin(
            entity = Entities.entity(BookPublisher::class),
            association = Paths.path(Book::publisher),
        )

        assertThat(actual1).isEqualTo(expected1)
        assertThat(actual2).isEqualTo(expected2)
    }

    @Test
    fun `leftFetchJoin() with a property and a predicate`() {
        // when
        val join = queryPart {
            leftFetchJoin(Book::authors).on(predicate1)
        }

        val actual: Join = join.toJoin()

        // then
        val expected = Joins.leftFetchJoin(
            entity = Entities.entity(BookAuthor::class),
            association = Paths.path(Book::authors),
            predicate = predicate1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `leftFetchJoin() with a property and an alias`() {
        // when
        val join1 = queryPart {
            leftFetchJoin(Book::authors).`as`(alias1)
        }

        val join2 = queryPart {
            leftFetchJoin(Book::authors).alias(alias1)
        }

        val actual1: Join = join1.toJoin()
        val actual2: Join = join2.toJoin()

        // then
        val expected = Joins.leftFetchJoin(
            entity = alias1,
            association = Paths.path(Book::authors),
        )

        assertThat(actual1).isEqualTo(expected)
        assertThat(actual2).isEqualTo(expected)
    }

    @Test
    fun `leftFetchJoin() with a property, a predicate, and an alias`() {
        // when
        val join1 = queryPart {
            leftFetchJoin(Book::authors).on(predicate1).`as`(alias1)
        }

        val join2 = queryPart {
            leftFetchJoin(Book::authors).on(predicate1).alias(alias1)
        }

        val actual1: Join = join1.toJoin()
        val actual2: Join = join2.toJoin()

        // then
        val expected = Joins.leftFetchJoin(
            entity = alias1,
            association = Paths.path(Book::authors),
            predicate = predicate1,
        )

        assertThat(actual1).isEqualTo(expected)
        assertThat(actual2).isEqualTo(expected)
    }

    @Test
    fun `leftFetchJoin() with a path`() {
        // when
        val join1 = queryPart {
            leftFetchJoin(path1)
        }

        val join2 = queryPart {
            leftFetchJoin(path2)
        }

        val actual1: Join = join1.toJoin()
        val actual2: Join = join2.toJoin()

        // then
        val expected1 = Joins.leftFetchJoin(
            entity = entity1,
            association = path1,
        )

        val expected2 = Joins.leftFetchJoin(
            entity = entity2,
            association = path2,
        )

        assertThat(actual1).isEqualTo(expected1)
        assertThat(actual2).isEqualTo(expected2)
    }

    @Test
    fun `leftFetchJoin() with a path and a predicate`() {
        // when
        val join = queryPart {
            leftFetchJoin(path1).on(predicate1)
        }

        val actual: Join = join.toJoin()

        // then
        val expected = Joins.leftFetchJoin(
            entity = entity1,
            association = path1,
            predicate = predicate1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `leftFetchJoin() with a path and an alias`() {
        // when
        val join1 = queryPart {
            leftFetchJoin(path1).`as`(alias1)
        }

        val join2 = queryPart {
            leftFetchJoin(path1).alias(alias1)
        }

        val actual1: Join = join1.toJoin()
        val actual2: Join = join2.toJoin()

        // then
        val expected = Joins.leftFetchJoin(
            entity = alias1,
            association = path1,
        )

        assertThat(actual1).isEqualTo(expected)
        assertThat(actual2).isEqualTo(expected)
    }

    @Test
    fun `leftFetchJoin() with a path, a predicate, and an alias`() {
        // when
        val join1 = queryPart {
            leftFetchJoin(path1).on(predicate1).`as`(alias1)
        }

        val join2 = queryPart {
            leftFetchJoin(path1).on(predicate1).alias(alias1)
        }

        val actual1: Join = join1.toJoin()
        val actual2: Join = join2.toJoin()

        // then
        val expected = Joins.leftFetchJoin(
            entity = alias1,
            association = path1,
            predicate = predicate1,
        )

        assertThat(actual1).isEqualTo(expected)
        assertThat(actual2).isEqualTo(expected)
    }
}
