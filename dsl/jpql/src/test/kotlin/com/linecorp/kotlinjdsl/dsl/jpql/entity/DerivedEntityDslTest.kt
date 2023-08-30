package com.linecorp.kotlinjdsl.dsl.jpql.entity

import com.linecorp.kotlinjdsl.dsl.jpql.entity.book.Book
import com.linecorp.kotlinjdsl.dsl.jpql.queryPart
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entities
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entity
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import com.linecorp.kotlinjdsl.querymodel.jpql.select.Selects
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

class DerivedEntityDslTest : WithAssertions {
    private val selectQuery1 = Selects.select(
        returnType = String::class,
        distinct = false,
        select = listOf(Paths.path(Book::title)),
        from = listOf(Entities.entity(Book::class)),
    )

    private val alias1 = "alias1"

    @Test
    fun asEntity() {
        // when
        val entity = queryPart {
            selectQuery1.asEntity()
        }

        val actual: Entity<String> = entity

        // then
        val expected = Entities.derivedEntity(
            selectQuery1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `asEntity() with an alias`() {
        // when
        val entity = queryPart {
            selectQuery1.asEntity(alias1)
        }

        val actual: Entity<String> = entity

        // then
        val expected = Entities.derivedEntity(
            selectQuery1,
            alias1,
        )

        assertThat(actual).isEqualTo(expected)
    }
}
