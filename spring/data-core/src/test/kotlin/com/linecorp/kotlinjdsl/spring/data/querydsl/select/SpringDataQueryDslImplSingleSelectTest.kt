package com.linecorp.kotlinjdsl.spring.data.querydsl.select

import com.linecorp.kotlinjdsl.query.clause.select.CountSingleSelectClause
import com.linecorp.kotlinjdsl.query.clause.select.SingleSelectClause
import com.linecorp.kotlinjdsl.query.spec.expression.ColumnSpec
import com.linecorp.kotlinjdsl.query.spec.expression.CountSpec
import com.linecorp.kotlinjdsl.query.spec.expression.EntitySpec
import com.linecorp.kotlinjdsl.querydsl.expression.column
import com.linecorp.kotlinjdsl.spring.data.querydsl.SpringDataQueryDslImpl
import com.linecorp.kotlinjdsl.test.WithKotlinJdslAssertions
import org.junit.jupiter.api.Test

internal class SpringDataQueryDslImplSingleSelectTest : WithKotlinJdslAssertions {
    @Test
    fun noSelect() {
        // when
        val actual = SpringDataQueryDslImpl(Data1::class.java).apply {
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

        val exception3 = catchThrowable(IllegalStateException::class) {
            actual.createPageableQuerySpec()
        }

        assertThat(exception3).hasMessageContaining("There is no select clause in query")

        val exception4 = catchThrowable(IllegalStateException::class) {
            actual.createPageableCountQuerySpec()
        }

        assertThat(exception4).hasMessageContaining("There is no select clause in query")
    }

    @Test
    fun selectEntity() {
        // when
        val actual = SpringDataQueryDslImpl(Data1::class.java).apply {
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

        val pageableQuerySpec = actual.createPageableQuerySpec()

        assertThat(pageableQuerySpec.select).isEqualTo(
            SingleSelectClause(Data1::class.java, distinct = true, EntitySpec(Data1::class.java))
        )

        val pageableCountQuerySpec = actual.createPageableCountQuerySpec()

        assertThat(pageableCountQuerySpec.select).isEqualTo(
            CountSingleSelectClause(distinct = true, EntitySpec(Data1::class.java))
        )
    }

    @Test
    fun selectExpression() {
        // when
        val actual = SpringDataQueryDslImpl(String::class.java).apply {
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

        val pageableQuerySpec = actual.createPageableQuerySpec()

        assertThat(pageableQuerySpec.select).isEqualTo(
            SingleSelectClause(String::class.java, distinct = true, ColumnSpec(EntitySpec(Data1::class.java), "id"))
        )

        val pageableCountQuerySpec = actual.createPageableCountQuerySpec()

        assertThat(pageableCountQuerySpec.select).isEqualTo(
            CountSingleSelectClause(distinct = true, ColumnSpec<String>(EntitySpec(Data1::class.java), "id"))
        )
    }

    @Test
    fun selectNonDistinctEntity() {
        // when
        val actual = SpringDataQueryDslImpl(Data1::class.java).apply {
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

        val pageableQuerySpec = actual.createPageableQuerySpec()

        assertThat(pageableQuerySpec.select).isEqualTo(
            SingleSelectClause(Data1::class.java, distinct = false, EntitySpec(Data1::class.java))
        )

        val pageableCountQuerySpec = actual.createPageableCountQuerySpec()

        assertThat(pageableCountQuerySpec.select).isEqualTo(
            CountSingleSelectClause(distinct = false, EntitySpec(Data1::class.java))
        )
    }

    @Test
    fun selectNonDistinctExpression() {
        // when
        val actual = SpringDataQueryDslImpl(String::class.java).apply {
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

        val pageableQuerySpec = actual.createPageableQuerySpec()

        assertThat(pageableQuerySpec.select).isEqualTo(
            SingleSelectClause(String::class.java, distinct = false, ColumnSpec(EntitySpec(Data1::class.java), "id"))
        )

        val pageableCountQuerySpec = actual.createPageableCountQuerySpec()

        assertThat(pageableCountQuerySpec.select).isEqualTo(
            CountSingleSelectClause(distinct = false, ColumnSpec<String>(EntitySpec(Data1::class.java), "id"))
        )
    }

    @Test
    fun selectDistinctEntity() {
        // when
        val actual = SpringDataQueryDslImpl(Data1::class.java).apply {
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

        val pageableQuerySpec = actual.createPageableQuerySpec()

        assertThat(pageableQuerySpec.select).isEqualTo(
            SingleSelectClause(Data1::class.java, distinct = true, EntitySpec(Data1::class.java))
        )

        val pageableCountQuerySpec = actual.createPageableCountQuerySpec()

        assertThat(pageableCountQuerySpec.select).isEqualTo(
            CountSingleSelectClause(distinct = true, EntitySpec(Data1::class.java))
        )
    }

    @Test
    fun selectDistinctExpression() {
        // when
        val actual = SpringDataQueryDslImpl(String::class.java).apply {
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

        val pageableQuerySpec = actual.createPageableQuerySpec()

        assertThat(pageableQuerySpec.select).isEqualTo(
            SingleSelectClause(String::class.java, distinct = true, ColumnSpec(EntitySpec(Data1::class.java), "id"))
        )

        val pageableCountQuerySpec = actual.createPageableCountQuerySpec()

        assertThat(pageableCountQuerySpec.select).isEqualTo(
            CountSingleSelectClause(distinct = true, ColumnSpec<Long>(EntitySpec(Data1::class.java), "id"))
        )
    }

    @Test
    fun `inject countSelectClause`() {
        val countSelectClause: SingleSelectClause<Long>

        // when
        val actual = SpringDataQueryDslImpl(String::class.java).apply {
            selectDistinct(column(Data1::id))
            from(entity(Data1::class))
        }

        SpringDataQueryDslImpl(Long::class.java).apply {
            countSelectClause = select(count(column(Data1::name)))
        }

        // then
        val pageableCountQuerySpec = actual.createPageableCountQuerySpec(countSelectClause)

        assertThat(pageableCountQuerySpec.select).isEqualTo(
            SingleSelectClause(
                returnType = Long::class.java,
                distinct = false,
                expression = CountSpec(
                    distinct = false,
                    expression = ColumnSpec<Long>(EntitySpec(Data1::class.java), "name")
                )
            )
        )
    }

    class Data1 {
        val id: String = "test"
        val name: String = "name"
    }
}
