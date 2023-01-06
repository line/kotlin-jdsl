package com.linecorp.kotlinjdsl.querydsl.from

import com.linecorp.kotlinjdsl.query.clause.from.JoinClause
import com.linecorp.kotlinjdsl.query.clause.from.SimpleAssociatedJoinClause
import com.linecorp.kotlinjdsl.query.clause.where.WhereClause
import com.linecorp.kotlinjdsl.query.spec.CrossJoinSpec
import com.linecorp.kotlinjdsl.query.spec.SimpleAssociatedJoinSpec
import com.linecorp.kotlinjdsl.query.spec.SimpleJoinSpec
import com.linecorp.kotlinjdsl.query.spec.TreatJoinSpec
import com.linecorp.kotlinjdsl.query.spec.expression.ColumnSpec
import com.linecorp.kotlinjdsl.query.spec.expression.EntitySpec
import com.linecorp.kotlinjdsl.query.spec.predicate.AndSpec
import com.linecorp.kotlinjdsl.query.spec.predicate.PredicateSpec
import com.linecorp.kotlinjdsl.querydsl.QueryDslImpl
import com.linecorp.kotlinjdsl.test.WithKotlinJdslAssertions
import com.linecorp.kotlinjdsl.test.entity.employee.Employee
import com.linecorp.kotlinjdsl.test.entity.employee.FullTimeEmployee
import com.linecorp.kotlinjdsl.test.entity.employee.Project
import io.mockk.mockk
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import jakarta.persistence.criteria.JoinType

internal class QueryDslImplJoinTest : WithKotlinJdslAssertions {
    @Test
    fun on() {
        val predicateSpec: PredicateSpec = mockk()

        val actual: PredicateSpec

        QueryDslImpl(Data1::class.java).apply {
            actual = on(predicateSpec)
        }

        assertThat(actual).isEqualTo(predicateSpec)
    }

    @Test
    fun onLambda() {
        val predicateSpec: PredicateSpec = mockk()

        val actual: PredicateSpec

        QueryDslImpl(Data1::class.java).apply {
            actual = on { predicateSpec }
        }

        assertThat(actual).isEqualTo(predicateSpec)
    }

    @Test
    fun join() {
        // when
        val actual = QueryDslImpl(Data1::class.java).apply {
            select(Data1::class.java)
            from(Data1::class.java)
            join(entity(Data1::class), entity(Data2::class), on(Data1::data2), JoinType.LEFT)
            join(Data1::class, entity(Data2::class), on(Data1::data2), JoinType.LEFT)
            join(entity(Data1::class), Data2::class, on(Data1::data2), JoinType.LEFT)
            join(Data1::class, Data2::class, on(Data1::data2), JoinType.LEFT)
        }

        // then
        val joinSpec = SimpleJoinSpec(
            left = EntitySpec(Data1::class.java),
            right = EntitySpec(Data2::class.java),
            path = "data2",
            JoinType.LEFT
        )

        val criteriaQuerySpec = actual.createCriteriaQuerySpec()

        assertThat(criteriaQuerySpec.join).isEqualTo(
            JoinClause(listOf(joinSpec, joinSpec, joinSpec, joinSpec))
        )

        val subquerySpec = actual.createSubquerySpec()

        assertThat(subquerySpec.join).isEqualTo(
            JoinClause(listOf(joinSpec, joinSpec, joinSpec, joinSpec))
        )
    }

    @Test
    fun associate() {
        // when
        val actual = QueryDslImpl(Data1::class.java).apply {
            select(Data1::class.java)
            from(Data1::class.java)
            associate(entity(Data1::class), entity(Data2::class), on(Data1::data2), JoinType.LEFT)
            associate(Data1::class, entity(Data2::class), on(Data1::data2), JoinType.LEFT)
            associate(entity(Data1::class), Data2::class, on(Data1::data2), JoinType.LEFT)
            associate(Data1::class, Data2::class, on(Data1::data2), JoinType.LEFT)
        }

        // then
        val joinSpec = SimpleAssociatedJoinSpec(
            left = EntitySpec(Data1::class.java),
            right = EntitySpec(Data2::class.java),
            path = "data2"
        )

        val criteriaQuerySpec = actual.createCriteriaQuerySpec()

        assertThat(criteriaQuerySpec.join).isEqualTo(
            JoinClause(listOf(joinSpec, joinSpec, joinSpec, joinSpec))
        )

        val subquerySpec = actual.createSubquerySpec()

        assertThat(subquerySpec.join).isEqualTo(
            JoinClause(listOf(joinSpec, joinSpec, joinSpec, joinSpec))
        )
    }

    @Test
    fun treat() {
        // when
        val actual = QueryDslImpl(FullTimeEmployee::class.java).apply {
            val project: EntitySpec<Project> = Project::class.alias("project")
            val fullTimeEmployee = FullTimeEmployee::class.alias("fe")
            val employee = Employee::class.alias("e")

            selectDistinct(fullTimeEmployee)
            from(project)
            treat(
                root = ColumnSpec<FullTimeEmployee>(entity = project, path = Project::employees.name),
                parent = employee,
                child = fullTimeEmployee,
                parentJoinType = JoinType.RIGHT
            )
            where(
                ColumnSpec<BigDecimal>(fullTimeEmployee, FullTimeEmployee::annualSalary.name)
                    .greaterThan(100000.toBigDecimal())
            )
        }

        // then
        val joinSpec = TreatJoinSpec(
            left = EntitySpec(Employee::class.java, "e"),
            right = EntitySpec(FullTimeEmployee::class.java, "fe"),
            joinType = JoinType.RIGHT,
            root = ColumnSpec<FullTimeEmployee>(EntitySpec(Project::class.java, "project"), Project::employees.name),
        )

        val criteriaQuerySpec = actual.createCriteriaQuerySpec()

        assertThat(criteriaQuerySpec.join).isEqualTo(
            JoinClause(listOf(joinSpec))
        )

        val subquerySpec = actual.createSubquerySpec()

        assertThat(subquerySpec.join).isEqualTo(
            JoinClause(listOf(joinSpec))
        )
    }

    @Test
    fun updateAssociate() {
        // when
        val actual = QueryDslImpl(Data1::class.java).apply {
            select(Data1::class.java)
            from(Data1::class.java)
            associate(entity(Data1::class), entity(Data2::class), on(Data1::data2), JoinType.LEFT)
            associate(Data1::class, entity(Data2::class), on(Data1::data2), JoinType.LEFT)
            associate(entity(Data1::class), Data2::class, on(Data1::data2), JoinType.LEFT)
            associate(Data1::class, Data2::class, on(Data1::data2), JoinType.LEFT)
        }

        // then
        val joinSpec = SimpleAssociatedJoinSpec(
            left = EntitySpec(Data1::class.java),
            right = EntitySpec(Data2::class.java),
            path = "data2"
        )

        val criteriaQuerySpec = actual.createCriteriaUpdateQuerySpec()

        assertThat(criteriaQuerySpec.associate).isEqualTo(
            SimpleAssociatedJoinClause(listOf(joinSpec, joinSpec, joinSpec, joinSpec))
        )

        val subquerySpec = actual.createSubquerySpec()

        assertThat(subquerySpec.join).isEqualTo(
            JoinClause(listOf(joinSpec, joinSpec, joinSpec, joinSpec))
        )
    }

    @Test
    fun updateAssociateOnlyAllowsSimpleAssociatedJoinSpec() {
        // when
        val actual = QueryDslImpl(Data1::class.java).apply {
            select(Data1::class.java)
            from(Data1::class.java)
            associate(entity(Data1::class), entity(Data2::class), on(Data1::data2), JoinType.LEFT)
            associate(Data1::class, entity(Data2::class), on(Data1::data2), JoinType.LEFT)
            associate(entity(Data1::class), Data2::class, on(Data1::data2), JoinType.LEFT)
            associate(Data1::class, Data2::class, on(Data1::data2), JoinType.LEFT)
            join(Data1::class, Data2::class, on(Data1::data2))
        }

        val result = catchThrowable(IllegalStateException::class) { actual.createCriteriaUpdateQuerySpec() }

        assertThat(result).hasMessage("This query only support associate")
    }

    @Test
    fun crossJoin() {
        // given
        val predicateSpec1: PredicateSpec = mockk()
        val predicateSpec2: PredicateSpec = mockk()

        // when
        val actual = QueryDslImpl(Data1::class.java).apply {
            select(Data1::class.java)
            from(Data1::class.java)
            join(Data2::class, on(predicateSpec1))
            join(entity(Data2::class), on(predicateSpec1))
            where(predicateSpec2)
        }

        // then
        val joinSpec = CrossJoinSpec(EntitySpec(Data2::class.java))

        val criteriaQuerySpec = actual.createCriteriaQuerySpec()

        assertThat(criteriaQuerySpec.join).isEqualTo(
            JoinClause(listOf(joinSpec, joinSpec))
        )

        assertThat(criteriaQuerySpec.where).isEqualTo(
            WhereClause(AndSpec(listOf(predicateSpec1, predicateSpec1, predicateSpec2)))
        )

        val subquerySpec = actual.createSubquerySpec()

        assertThat(subquerySpec.join).isEqualTo(
            JoinClause(listOf(joinSpec, joinSpec))
        )

        assertThat(subquerySpec.where).isEqualTo(
            WhereClause(AndSpec(listOf(predicateSpec1, predicateSpec1, predicateSpec2)))
        )
    }

    class Data1 {
        val data2: Data2 = Data2()
    }

    class Data2
}
