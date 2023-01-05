package com.linecorp.kotlinjdsl.spring.data.querydsl.select

import com.linecorp.kotlinjdsl.query.clause.select.CountMultiSelectClause
import com.linecorp.kotlinjdsl.query.clause.select.MultiSelectClause
import com.linecorp.kotlinjdsl.query.spec.expression.ColumnSpec
import com.linecorp.kotlinjdsl.query.spec.expression.EntitySpec
import com.linecorp.kotlinjdsl.querydsl.expression.column
import com.linecorp.kotlinjdsl.spring.data.querydsl.SpringDataQueryDslImpl
import com.linecorp.kotlinjdsl.test.WithKotlinJdslAssertions
import org.junit.jupiter.api.Test

internal class SpringDataQueryDslImplMultiSelectTest : WithKotlinJdslAssertions {
    @Test
    fun select() {
        // when
        val actual = SpringDataQueryDslImpl(Row::class.java).apply {
            select(distinct = true, listOf(column(Data1::id), column(Data1::name)))
            from(entity(Data1::class))
        }

        // then
        val criteriaQuerySpec = actual.createCriteriaQuerySpec()

        assertThat(criteriaQuerySpec.select).isEqualTo(
            MultiSelectClause(
                returnType = Row::class.java,
                distinct = true,
                expressions = listOf(
                    ColumnSpec<String>(EntitySpec(Data1::class.java), "id"),
                    ColumnSpec(EntitySpec(Data1::class.java), "name"),
                )
            )
        )

        val exception = catchThrowable(IllegalStateException::class) {
            actual.createSubquerySpec()
        }

        assertThat(exception).hasMessageContaining("There is no select clause in query")

        val pageableQuerySpec = actual.createPageableQuerySpec()

        assertThat(pageableQuerySpec.select).isEqualTo(
            MultiSelectClause(
                returnType = Row::class.java,
                distinct = true,
                expressions = listOf(
                    ColumnSpec<String>(EntitySpec(Data1::class.java), "id"),
                    ColumnSpec(EntitySpec(Data1::class.java), "name"),
                )
            )
        )

        val pageableCountQuerySpec = actual.createPageableCountQuerySpec()

        assertThat(pageableCountQuerySpec.select).isEqualTo(
            CountMultiSelectClause(
                distinct = true,
                expressions = listOf(
                    ColumnSpec<String>(EntitySpec(Data1::class.java), "id"),
                    ColumnSpec(EntitySpec(Data1::class.java), "name"),
                )
            )
        )
    }

    @Test
    fun selectNonDistinct() {
        // when
        val actual = SpringDataQueryDslImpl(Row::class.java).apply {
            select(listOf(column(Data1::id), column(Data1::name)))
            from(entity(Data1::class))
        }

        // then
        val criteriaQuerySpec = actual.createCriteriaQuerySpec()

        assertThat(criteriaQuerySpec.select).isEqualTo(
            MultiSelectClause(
                returnType = Row::class.java,
                distinct = false,
                expressions = listOf(
                    ColumnSpec<String>(EntitySpec(Data1::class.java), "id"),
                    ColumnSpec(EntitySpec(Data1::class.java), "name"),
                )
            )
        )

        val exception = catchThrowable(IllegalStateException::class) {
            actual.createSubquerySpec()
        }

        assertThat(exception).hasMessageContaining("There is no select clause in query")

        val pageableQuerySpec = actual.createPageableQuerySpec()

        assertThat(pageableQuerySpec.select).isEqualTo(
            MultiSelectClause(
                returnType = Row::class.java,
                distinct = false,
                expressions = listOf(
                    ColumnSpec<String>(EntitySpec(Data1::class.java), "id"),
                    ColumnSpec(EntitySpec(Data1::class.java), "name"),
                )
            )
        )

        val pageableCountQuerySpec = actual.createPageableCountQuerySpec()

        assertThat(pageableCountQuerySpec.select).isEqualTo(
            CountMultiSelectClause(
                distinct = false,
                expressions = listOf(
                    ColumnSpec<String>(EntitySpec(Data1::class.java), "id"),
                    ColumnSpec(EntitySpec(Data1::class.java), "name"),
                )
            )
        )
    }

    @Test
    fun selectDistinct() {
        // when
        val actual = SpringDataQueryDslImpl(Row::class.java).apply {
            selectDistinct(listOf(column(Data1::id), column(Data1::name)))
            from(entity(Data1::class))
        }

        // then
        val criteriaQuerySpec = actual.createCriteriaQuerySpec()

        assertThat(criteriaQuerySpec.select).isEqualTo(
            MultiSelectClause(
                returnType = Row::class.java,
                distinct = true,
                expressions = listOf(
                    ColumnSpec<String>(EntitySpec(Data1::class.java), "id"),
                    ColumnSpec(EntitySpec(Data1::class.java), "name"),
                )
            )
        )

        val exception = catchThrowable(IllegalStateException::class) {
            actual.createSubquerySpec()
        }

        assertThat(exception).hasMessageContaining("There is no select clause in query")

        val pageableQuerySpec = actual.createPageableQuerySpec()

        assertThat(pageableQuerySpec.select).isEqualTo(
            MultiSelectClause(
                returnType = Row::class.java,
                distinct = true,
                expressions = listOf(
                    ColumnSpec<String>(EntitySpec(Data1::class.java), "id"),
                    ColumnSpec(EntitySpec(Data1::class.java), "name"),
                )
            )
        )

        val pageableCountQuerySpec = actual.createPageableCountQuerySpec()

        assertThat(pageableCountQuerySpec.select).isEqualTo(
            CountMultiSelectClause(
                distinct = true,
                expressions = listOf(
                    ColumnSpec<String>(EntitySpec(Data1::class.java), "id"),
                    ColumnSpec(EntitySpec(Data1::class.java), "name"),
                )
            )
        )
    }

    @Test
    fun selectMulti() {
        // when
        val actual = SpringDataQueryDslImpl(Row::class.java).apply {
            selectMulti(distinct = true, listOf(column(Data1::id), column(Data1::name)))
            from(entity(Data1::class))
        }

        // then
        val criteriaQuerySpec = actual.createCriteriaQuerySpec()

        assertThat(criteriaQuerySpec.select).isEqualTo(
            MultiSelectClause(
                returnType = Row::class.java,
                distinct = true,
                expressions = listOf(
                    ColumnSpec<String>(EntitySpec(Data1::class.java), "id"),
                    ColumnSpec(EntitySpec(Data1::class.java), "name"),
                )
            )
        )

        val exception = catchThrowable(IllegalStateException::class) {
            actual.createSubquerySpec()
        }

        assertThat(exception).hasMessageContaining("There is no select clause in query")

        val pageableQuerySpec = actual.createPageableQuerySpec()

        assertThat(pageableQuerySpec.select).isEqualTo(
            MultiSelectClause(
                returnType = Row::class.java,
                distinct = true,
                expressions = listOf(
                    ColumnSpec<String>(EntitySpec(Data1::class.java), "id"),
                    ColumnSpec(EntitySpec(Data1::class.java), "name"),
                )
            )
        )

        val pageableCountQuerySpec = actual.createPageableCountQuerySpec()

        assertThat(pageableCountQuerySpec.select).isEqualTo(
            CountMultiSelectClause(
                distinct = true,
                expressions = listOf(
                    ColumnSpec<String>(EntitySpec(Data1::class.java), "id"),
                    ColumnSpec(EntitySpec(Data1::class.java), "name"),
                )
            )
        )
    }

    @Test
    fun selectMultiNonDistinctVararg() {
        // when
        val actual = SpringDataQueryDslImpl(Row::class.java).apply {
            selectMulti(column(Data1::id), column(Data1::name))
            from(entity(Data1::class))
        }

        // then
        val criteriaQuerySpec = actual.createCriteriaQuerySpec()

        assertThat(criteriaQuerySpec.select).isEqualTo(
            MultiSelectClause(
                returnType = Row::class.java,
                distinct = false,
                expressions = listOf(
                    ColumnSpec<String>(EntitySpec(Data1::class.java), "id"),
                    ColumnSpec(EntitySpec(Data1::class.java), "name"),
                )
            )
        )

        val exception = catchThrowable(IllegalStateException::class) {
            actual.createSubquerySpec()
        }

        assertThat(exception).hasMessageContaining("There is no select clause in query")

        val pageableQuerySpec = actual.createPageableQuerySpec()

        assertThat(pageableQuerySpec.select).isEqualTo(
            MultiSelectClause(
                returnType = Row::class.java,
                distinct = false,
                expressions = listOf(
                    ColumnSpec<String>(EntitySpec(Data1::class.java), "id"),
                    ColumnSpec(EntitySpec(Data1::class.java), "name"),
                )
            )
        )

        val pageableCountQuerySpec = actual.createPageableCountQuerySpec()

        assertThat(pageableCountQuerySpec.select).isEqualTo(
            CountMultiSelectClause(
                distinct = false,
                expressions = listOf(
                    ColumnSpec<String>(EntitySpec(Data1::class.java), "id"),
                    ColumnSpec(EntitySpec(Data1::class.java), "name"),
                )
            )
        )
    }

    @Test
    fun selectMultiNonDistinctList() {
        // when
        val actual = SpringDataQueryDslImpl(Row::class.java).apply {
            selectMulti(listOf(column(Data1::id), column(Data1::name)))
            from(entity(Data1::class))
        }

        // then
        val criteriaQuerySpec = actual.createCriteriaQuerySpec()

        assertThat(criteriaQuerySpec.select).isEqualTo(
            MultiSelectClause(
                returnType = Row::class.java,
                distinct = false,
                expressions = listOf(
                    ColumnSpec<String>(EntitySpec(Data1::class.java), "id"),
                    ColumnSpec(EntitySpec(Data1::class.java), "name"),
                )
            )
        )

        val exception = catchThrowable(IllegalStateException::class) {
            actual.createSubquerySpec()
        }

        assertThat(exception).hasMessageContaining("There is no select clause in query")

        val pageableQuerySpec = actual.createPageableQuerySpec()

        assertThat(pageableQuerySpec.select).isEqualTo(
            MultiSelectClause(
                returnType = Row::class.java,
                distinct = false,
                expressions = listOf(
                    ColumnSpec<String>(EntitySpec(Data1::class.java), "id"),
                    ColumnSpec(EntitySpec(Data1::class.java), "name"),
                )
            )
        )

        val pageableCountQuerySpec = actual.createPageableCountQuerySpec()

        assertThat(pageableCountQuerySpec.select).isEqualTo(
            CountMultiSelectClause(
                distinct = false,
                expressions = listOf(
                    ColumnSpec<String>(EntitySpec(Data1::class.java), "id"),
                    ColumnSpec(EntitySpec(Data1::class.java), "name"),
                )
            )
        )
    }

    @Test
    fun selectMultiDistinctVararg() {
        // when
        val actual = SpringDataQueryDslImpl(Row::class.java).apply {
            selectDistinctMulti(column(Data1::id), column(Data1::name))
            from(entity(Data1::class))
        }

        // then
        val criteriaQuerySpec = actual.createCriteriaQuerySpec()

        assertThat(criteriaQuerySpec.select).isEqualTo(
            MultiSelectClause(
                returnType = Row::class.java,
                distinct = true,
                expressions = listOf(
                    ColumnSpec<String>(EntitySpec(Data1::class.java), "id"),
                    ColumnSpec(EntitySpec(Data1::class.java), "name"),
                )
            )
        )

        val exception = catchThrowable(IllegalStateException::class) {
            actual.createSubquerySpec()
        }

        assertThat(exception).hasMessageContaining("There is no select clause in query")

        val pageableQuerySpec = actual.createPageableQuerySpec()

        assertThat(pageableQuerySpec.select).isEqualTo(
            MultiSelectClause(
                returnType = Row::class.java,
                distinct = true,
                expressions = listOf(
                    ColumnSpec<String>(EntitySpec(Data1::class.java), "id"),
                    ColumnSpec(EntitySpec(Data1::class.java), "name"),
                )
            )
        )

        val pageableCountQuerySpec = actual.createPageableCountQuerySpec()

        assertThat(pageableCountQuerySpec.select).isEqualTo(
            CountMultiSelectClause(
                distinct = true,
                expressions = listOf(
                    ColumnSpec<String>(EntitySpec(Data1::class.java), "id"),
                    ColumnSpec(EntitySpec(Data1::class.java), "name"),
                )
            )
        )
    }

    @Test
    fun selectMultiDistinctList() {
        // when
        val actual = SpringDataQueryDslImpl(Row::class.java).apply {
            selectDistinctMulti(listOf(column(Data1::id), column(Data1::name)))
            from(entity(Data1::class))
        }

        // then
        val criteriaQuerySpec = actual.createCriteriaQuerySpec()

        assertThat(criteriaQuerySpec.select).isEqualTo(
            MultiSelectClause(
                returnType = Row::class.java,
                distinct = true,
                expressions = listOf(
                    ColumnSpec<String>(EntitySpec(Data1::class.java), "id"),
                    ColumnSpec(EntitySpec(Data1::class.java), "name"),
                )
            )
        )

        val exception = catchThrowable(IllegalStateException::class) {
            actual.createSubquerySpec()
        }

        assertThat(exception).hasMessageContaining("There is no select clause in query")

        val pageableQuerySpec = actual.createPageableQuerySpec()

        assertThat(pageableQuerySpec.select).isEqualTo(
            MultiSelectClause(
                returnType = Row::class.java,
                distinct = true,
                expressions = listOf(
                    ColumnSpec<String>(EntitySpec(Data1::class.java), "id"),
                    ColumnSpec(EntitySpec(Data1::class.java), "name"),
                )
            )
        )

        val pageableCountQuerySpec = actual.createPageableCountQuerySpec()

        assertThat(pageableCountQuerySpec.select).isEqualTo(
            CountMultiSelectClause(
                distinct = true,
                expressions = listOf(
                    ColumnSpec<String>(EntitySpec(Data1::class.java), "id"),
                    ColumnSpec(EntitySpec(Data1::class.java), "name"),
                )
            )
        )
    }

    data class Row(
        val id: String,
        val name: String,
    )

    class Data1 {
        val id: String = "test"
        val name: String = "test_name"
    }
}
