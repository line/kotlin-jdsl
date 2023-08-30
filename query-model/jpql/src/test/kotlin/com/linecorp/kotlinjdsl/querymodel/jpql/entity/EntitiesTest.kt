package com.linecorp.kotlinjdsl.querymodel.jpql.entity

import com.linecorp.kotlinjdsl.querymodel.jpql.entity.book.Book
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.employee.Employee
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.employee.FullTimeEmployee
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.impl.JpqlDerivedEntity
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.impl.JpqlEntity
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.impl.JpqlEntityTreat
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicates
import com.linecorp.kotlinjdsl.querymodel.jpql.select.impl.JpqlSelectQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.sort.Sorts
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

class EntitiesTest : WithAssertions {
    private val entity1 = Entities.entity(Employee::class)

    private val expression1 = Paths.path(Employee::name)

    private val predicate1 = Predicates.equal(Paths.path(Employee::name), Expressions.value("name1"))
    private val predicate2 = Predicates.equal(Paths.path(Employee::nickname), Expressions.value("nickname1"))

    private val sort1 = Sorts.asc(expression1)

    private val selectQuery1 = JpqlSelectQuery(
        returnType = Book::class,
        distinct = false,
        select = listOf(entity1),
        from = listOf(entity1),
        where = predicate1,
        groupBy = listOf(expression1),
        having = predicate2,
        orderBy = listOf(sort1),
    )

    private val alias1 = "alias1"

    @Test
    fun entity() {
        // when
        val actual = Entities.entity(Employee::class)

        // then
        val expected = JpqlEntity(
            type = Employee::class,
            alias = Employee::class.simpleName!!,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `entity() with an alias`() {
        // when
        val actual = Entities.entity(Employee::class, alias1)

        // then
        val expected = JpqlEntity(
            type = Employee::class,
            alias = alias1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun treat() {
        // when
        val actual = Entities.treat(entity1, FullTimeEmployee::class)

        // then
        val expected = JpqlEntityTreat(
            entity1,
            FullTimeEmployee::class,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun derivedEntity() {
        // when
        val actual = Entities.derivedEntity(selectQuery1)

        // then
        val expected = JpqlDerivedEntity(
            selectQuery = selectQuery1.copy(orderBy = null),
            alias = selectQuery1.returnType.simpleName!!,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `derivedEntity() with an alias`() {
        // when
        val actual = Entities.derivedEntity(selectQuery1, alias1)

        // then
        val expected = JpqlDerivedEntity(
            selectQuery = selectQuery1.copy(orderBy = null),
            alias = alias1,
        )

        assertThat(actual).isEqualTo(expected)
    }
}
