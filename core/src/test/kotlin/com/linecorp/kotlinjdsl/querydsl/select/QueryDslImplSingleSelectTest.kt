package com.linecorp.kotlinjdsl.querydsl.select

import com.linecorp.kotlinjdsl.query.clause.select.SingleSelectClause
import com.linecorp.kotlinjdsl.query.spec.expression.ColumnSpec
import com.linecorp.kotlinjdsl.query.spec.expression.EntitySpec
import com.linecorp.kotlinjdsl.querydsl.QueryDslImpl
import com.linecorp.kotlinjdsl.querydsl.expression.column
import com.linecorp.kotlinjdsl.test.WithKotlinJdslAssertions
import org.junit.jupiter.api.Test

internal class QueryDslImplSingleSelectTest : WithKotlinJdslAssertions {
    @Test
    fun noSelect() {
        // when
        val actual = QueryDslImpl(Data1::class.java).apply {
            from(entity(Data1::class))
        }

        // then
        val exception1 = catchThrowable(IllegalStateException::class) {
            actual.createCriteriaQuerySpec()
        }

        assertThat(exception1).hasMessageContaining("There is no select clause in query")

        val exception2 = catchThrowable(IllegalStateException::class) {
            actual.createSubquerySpec()
        }

        assertThat(exception2).hasMessageContaining("There is no select clause in query")
    }

    @Test
    fun selectEntity() {
        // when
        val actual = QueryDslImpl(Data1::class.java).apply {
            select(distinct = true, Data1::class.java)
            from(entity(Data1::class))
        }

        // then
        val criteriaQuerySpec = actual.createCriteriaQuerySpec()

        assertThat(criteriaQuerySpec.select).isEqualTo(
            SingleSelectClause(Data1::class.java, distinct = true, EntitySpec(Data1::class.java))
        )

        val subquerySpec = actual.createSubquerySpec()

        assertThat(subquerySpec.select).isEqualTo(
            SingleSelectClause(Data1::class.java, distinct = true, EntitySpec(Data1::class.java))
        )
    }

    @Test
    fun selectExpression() {
        // when
        val actual = QueryDslImpl(String::class.java).apply {
            select(distinct = true, column(Data1::id))
            from(entity(Data1::class))
        }

        // then
        val criteriaQuerySpec = actual.createCriteriaQuerySpec()

        assertThat(criteriaQuerySpec.select).isEqualTo(
            SingleSelectClause(String::class.java, distinct = true, ColumnSpec(EntitySpec(Data1::class.java), "id"))
        )

        val subquerySpec = actual.createSubquerySpec()

        assertThat(subquerySpec.select).isEqualTo(
            SingleSelectClause(String::class.java, distinct = true, ColumnSpec(EntitySpec(Data1::class.java), "id"))
        )
    }

    @Test
    fun selectNonDistinctEntity() {
        // when
        val actual = QueryDslImpl(Data1::class.java).apply {
            select(Data1::class.java)
            from(entity(Data1::class))
        }

        // then
        val criteriaQuerySpec = actual.createCriteriaQuerySpec()

        assertThat(criteriaQuerySpec.select).isEqualTo(
            SingleSelectClause(Data1::class.java, distinct = false, EntitySpec(Data1::class.java))
        )

        val subquerySpec = actual.createSubquerySpec()

        assertThat(subquerySpec.select).isEqualTo(
            SingleSelectClause(Data1::class.java, distinct = false, EntitySpec(Data1::class.java))
        )
    }

    @Test
    fun selectNonDistinctExpression() {
        // when
        val actual = QueryDslImpl(String::class.java).apply {
            select(column(Data1::id))
            from(entity(Data1::class))
        }

        // then
        val criteriaQuerySpec = actual.createCriteriaQuerySpec()

        assertThat(criteriaQuerySpec.select).isEqualTo(
            SingleSelectClause(String::class.java, distinct = false, ColumnSpec(EntitySpec(Data1::class.java), "id"))
        )

        val subquerySpec = actual.createSubquerySpec()

        assertThat(subquerySpec.select).isEqualTo(
            SingleSelectClause(String::class.java, distinct = false, ColumnSpec(EntitySpec(Data1::class.java), "id"))
        )
    }

    @Test
    fun selectDistinctEntity() {
        // when
        val actual = QueryDslImpl(Data1::class.java).apply {
            selectDistinct(Data1::class.java)
            from(entity(Data1::class))
        }

        // then
        val criteriaQuerySpec = actual.createCriteriaQuerySpec()

        assertThat(criteriaQuerySpec.select).isEqualTo(
            SingleSelectClause(Data1::class.java, distinct = true, EntitySpec(Data1::class.java))
        )

        val subquerySpec = actual.createSubquerySpec()

        assertThat(subquerySpec.select).isEqualTo(
            SingleSelectClause(Data1::class.java, distinct = true, EntitySpec(Data1::class.java))
        )
    }

    @Test
    fun selectDistinctExpression() {
        // when
        val actual = QueryDslImpl(String::class.java).apply {
            selectDistinct(column(Data1::id))
            from(entity(Data1::class))
        }

        // then
        val criteriaQuerySpec = actual.createCriteriaQuerySpec()

        assertThat(criteriaQuerySpec.select).isEqualTo(
            SingleSelectClause(String::class.java, distinct = true, ColumnSpec(EntitySpec(Data1::class.java), "id"))
        )

        val subquerySpec = actual.createSubquerySpec()

        assertThat(subquerySpec.select).isEqualTo(
            SingleSelectClause(String::class.java, distinct = true, ColumnSpec(EntitySpec(Data1::class.java), "id"))
        )
    }

    class Data1 {
        val id: String = "test"
    }
}
