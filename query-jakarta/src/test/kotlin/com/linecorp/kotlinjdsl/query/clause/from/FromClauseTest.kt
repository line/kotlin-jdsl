package com.linecorp.kotlinjdsl.query.clause.from

import com.linecorp.kotlinjdsl.query.spec.*
import com.linecorp.kotlinjdsl.query.spec.expression.ColumnSpec
import com.linecorp.kotlinjdsl.query.spec.expression.EntitySpec
import com.linecorp.kotlinjdsl.test.WithKotlinJdslAssertions
import com.linecorp.kotlinjdsl.test.entity.employee.Employee
import com.linecorp.kotlinjdsl.test.entity.employee.FullTimeEmployee
import com.linecorp.kotlinjdsl.test.entity.employee.PartTimeEmployee
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import jakarta.persistence.criteria.*


private typealias ExplicitErasedParent = Number
private typealias ExplicitErasedChild = Int

@ExtendWith(MockKExtension::class)
internal class FromClauseTest : WithKotlinJdslAssertions {
    @MockK
    private lateinit var query: AbstractQuery<*>

    @MockK
    private lateinit var updateQuery: CriteriaUpdate<Data1>

    @MockK
    private lateinit var deleteQuery: CriteriaDelete<Data1>

    @MockK
    private lateinit var criteriaBuilder: CriteriaBuilder

    private val entitySpec1 = EntitySpec(Data1::class.java)
    private val entitySpec2 = EntitySpec(Data2::class.java)
    private val entitySpec3 = EntitySpec(Data3::class.java)
    private val entitySpec4 = EntitySpec(Data4::class.java)

    @Test
    fun join() {
        // given
        val fromEntitySpec = entitySpec1
        val fromClause = FromClause(fromEntitySpec)

        val joinSpec1 = SimpleJoinSpec(entitySpec1, entitySpec2, "data2", JoinType.INNER)
        val joinSpec2 = SimpleJoinSpec(entitySpec2, entitySpec3, "data3", JoinType.LEFT)
        val joinSpec3 = FetchJoinSpec(entitySpec2, entitySpec3, "data3", JoinType.INNER)
        val joinSpec4 = FetchJoinSpec(entitySpec2, entitySpec3, "data3", JoinType.LEFT)
        val joinSpec5 = CrossJoinSpec(entitySpec4)
        val joinClause = JoinClause(listOf(joinSpec1, joinSpec2, joinSpec3, joinSpec4, joinSpec5))

        val root = mockk<Root<Data1>>()
        val join1 = mockk<Join<Any, Any>>()
        val fetch1 = mockk<MockFetch<Any, Any>>()
        val crossJoin1 = mockk<Root<Data4>>()

        every { query.from(Data1::class.java) } returns root
        every { root.join<Any, Any>(any<String>(), any()) } returns join1
        every { join1.fetch<Any, Any>(any(), any()) } returns fetch1
        every { query.from(Data4::class.java) } returns crossJoin1

        // when
        val actual = fromClause.join(joinClause, query, criteriaBuilder)

        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(
            Froms(
                root = root,
                map = mapOf(
                    entitySpec1 to root,
                    entitySpec2 to join1,
                    entitySpec3 to fetch1,
                    entitySpec4 to crossJoin1,
                )
            )
        )

        verifyOrder {
            query.from(Data1::class.java)
            root.join<Any, Any>("data2", JoinType.INNER)
            join1.fetch<Any, Any>("data3", JoinType.LEFT)
            query.from(Data4::class.java)
        }

        verify {
            root.hashCode()
            join1.hashCode()
            fetch1.hashCode()
            crossJoin1.hashCode()
        }

        confirmVerified(root, join1, fetch1, crossJoin1, query)
    }

    @Suppress("UNCHECKED_CAST")
    @Test
    fun treatWithOtherRoot() {
        // given
        val fromEntitySpec = EntitySpec(RootOther::class.java, "root")
        val outerParent = EntitySpec(Parent::class.java, "outerParent")
        val innerParent = EntitySpec(Parent::class.java, "innerParent")
        val fetchInnerParent = EntitySpec(Parent::class.java, "fetchInnerParent")
        val fetchOuterParent = EntitySpec(Parent::class.java, "fetchOuterParent")

        val treatInnerParent = EntitySpec(Parent::class.java, "treatInnerParent")
        val treatOuterParent = EntitySpec(Parent::class.java, "treatOuterParent")
        val child1Inner = EntitySpec(Child1::class.java, "child1Inner")
        val child1Outer = EntitySpec(Child1::class.java, "child1Outer")
        val child1FetchInner = EntitySpec(Child1::class.java, "child1FetchInner")
        val child2FetchOuter = EntitySpec(Child2::class.java, "child2FetchOuter")
        val child3 = EntitySpec(Child3::class.java, "child3")
        val fromClause = FromClause(fromEntitySpec)

        val joinSpec1 = SimpleJoinSpec(fromEntitySpec, outerParent, RootOther::outerChild1.name, JoinType.INNER)
        // winner with joinSpec1
        val joinSpec2 = SimpleJoinSpec(fromEntitySpec, outerParent, RootOther::outerChild1.name, JoinType.LEFT)

        // winner
        val joinSpec3 = SimpleJoinSpec(fromEntitySpec, innerParent, RootOther::innerChild2.name, JoinType.INNER)

        // winner
        val joinSpec4 = CrossJoinSpec(child3)

        // winner
        val joinSpec5 =
            FetchJoinSpec(fromEntitySpec, fetchInnerParent, RootOther::fetchInnerChild1.name, JoinType.INNER)

        val joinSpec6 =
            FetchJoinSpec(fromEntitySpec, fetchOuterParent, RootOther::fetchOuterChild2.name, JoinType.INNER)
        // winner with joinSpec6
        val joinSpec7 = FetchJoinSpec(fromEntitySpec, fetchOuterParent, RootOther::fetchOuterChild2.name, JoinType.LEFT)

        // winner
        val joinSpec8 = TreatJoinSpec(
            left = innerParent,
            right = child1Inner,
            joinType = JoinType.INNER,
            root = ColumnSpec<Child1>(fromEntitySpec, RootOther::treatChildrenInner.name),
        )

        val joinSpec9 = TreatJoinSpec(
            left = outerParent,
            right = child1Outer,
            joinType = JoinType.INNER,
            root = ColumnSpec<Child1>(fromEntitySpec, RootOther::treatChildrenOuter.name),
        )
        // winner with joinSpec9
        val joinSpec10 = TreatJoinSpec(
            left = outerParent,
            right = child1Outer,
            joinType = JoinType.LEFT,
            root = ColumnSpec<Child1>(fromEntitySpec, RootOther::treatChildrenOuter.name),
        )

        // winner
        val joinSpec11 = TreatJoinSpec(
            left = treatInnerParent,
            right = child1FetchInner,
            joinType = JoinType.INNER,
            root = ColumnSpec<Child1>(fromEntitySpec, RootOther::treatFetchInnerChild1.name),
        )

        val joinSpec12 = TreatJoinSpec(
            left = treatOuterParent,
            right = child2FetchOuter,
            joinType = JoinType.INNER,
            root = ColumnSpec<Child2>(fromEntitySpec, RootOther::treatFetchOuterChild2.name),
        )
        // winner with joinSpec12
        val joinSpec13 = TreatJoinSpec(
            left = treatOuterParent,
            right = child2FetchOuter,
            joinType = JoinType.LEFT,
            root = ColumnSpec<Child2>(fromEntitySpec, RootOther::treatFetchOuterChild2.name),
        )


        val joinClause = JoinClause(
            listOf(
                joinSpec1,
                joinSpec2,
                joinSpec3,
                joinSpec4,
                joinSpec5,
                joinSpec6,
                joinSpec7,
                joinSpec8,
                joinSpec9,
                joinSpec10,
                joinSpec11,
                joinSpec12,
                joinSpec13,
            )
        )

        val root = mockk<Root<RootOther>>()
        val outerJoin1 = mockk<Join<RootOther, Parent>>()
        val innerJoin1 = mockk<Join<RootOther, Parent>>()
        val crossJoin1 = mockk<Root<Child3>>()

        val fetchInnerJoin1 = mockk<MockFetch<RootOther, Parent>>()
        val fetchOuterJoin1 = mockk<MockFetch<RootOther, Parent>>()

        val treatInnerParentJoin1 = mockk<Join<RootOther, Parent>>()
        val treatOuterParentJoin1 = mockk<Join<RootOther, Parent>>()

        val treatJoin1 = mockk<Join<ExplicitErasedParent, ExplicitErasedChild>>()
        val treatJoin2 = mockk<Join<ExplicitErasedParent, ExplicitErasedChild>>()
        val treatJoin3 = mockk<Join<ExplicitErasedParent, ExplicitErasedChild>>()
        val treatJoin4 = mockk<Join<ExplicitErasedParent, ExplicitErasedChild>>()

        every { query.from(RootOther::class.java) } returns root
        every { root.join<RootOther, Parent>(RootOther::outerChild1.name, JoinType.LEFT) } returns outerJoin1
        every { root.join<RootOther, Parent>(RootOther::innerChild2.name, JoinType.INNER) } returns innerJoin1
        every { query.from(Child3::class.java) } returns crossJoin1

        every {
            root.fetch<RootOther, Parent>(
                RootOther::fetchInnerChild1.name,
                JoinType.INNER
            )
        } returns fetchInnerJoin1
        every { root.fetch<RootOther, Parent>(RootOther::fetchOuterChild2.name, JoinType.LEFT) } returns fetchOuterJoin1

        every {
            criteriaBuilder.treat(
                innerJoin1 as Join<ExplicitErasedParent, ExplicitErasedChild>,
                Child1::class.java as Class<ExplicitErasedChild>
            )
        } returns treatJoin1
        every {
            criteriaBuilder.treat(
                outerJoin1 as Join<ExplicitErasedParent, ExplicitErasedChild>,
                Child1::class.java as Class<ExplicitErasedChild>
            )
        } returns treatJoin2

        every {
            root.join<RootOther, Parent>(
                RootOther::treatFetchInnerChild1.name,
                JoinType.INNER
            )
        } returns treatInnerParentJoin1
        every {
            root.join<RootOther, Parent>(
                RootOther::treatFetchOuterChild2.name,
                JoinType.LEFT
            )
        } returns treatOuterParentJoin1

        every {
            criteriaBuilder.treat(
                treatInnerParentJoin1 as Join<ExplicitErasedParent, ExplicitErasedChild>,
                Child1::class.java as Class<ExplicitErasedChild>
            )
        } returns treatJoin3
        every {
            criteriaBuilder.treat(
                treatOuterParentJoin1 as Join<ExplicitErasedParent, ExplicitErasedChild>,
                Child2::class.java as Class<ExplicitErasedChild>
            )
        } returns treatJoin4

        // when
        val actual = fromClause.join(joinClause, query, criteriaBuilder)

        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(
            Froms(
                root = root,
                map = mapOf(
                    fromEntitySpec to root,
                    fetchOuterParent to fetchOuterJoin1,
                    fetchInnerParent to fetchInnerJoin1,
                    outerParent to outerJoin1,
                    innerParent to innerJoin1,
                    child3 to crossJoin1,
                    treatOuterParent to treatOuterParentJoin1,
                    treatInnerParent to treatInnerParentJoin1,
                    child1Inner to treatJoin1,
                    child1Outer to treatJoin2,
                    child1FetchInner to treatJoin3,
                    child2FetchOuter to treatJoin4,
                )
            )
        )

        verify {
            query.from(RootOther::class.java)
            root.join<RootOther, Parent>(RootOther::outerChild1.name, JoinType.LEFT)
            root.join<RootOther, Parent>(RootOther::innerChild2.name, JoinType.INNER)
            query.from(Child3::class.java)
            root.fetch<RootOther, Parent>(
                RootOther::fetchInnerChild1.name,
                JoinType.INNER
            )
            root.fetch<RootOther, Parent>(RootOther::fetchOuterChild2.name, JoinType.LEFT)
            criteriaBuilder.treat(
                innerJoin1 as Join<ExplicitErasedParent, ExplicitErasedChild>,
                Child1::class.java as Class<ExplicitErasedChild>
            )
            criteriaBuilder.treat(
                outerJoin1 as Join<ExplicitErasedParent, ExplicitErasedChild>,
                Child1::class.java as Class<ExplicitErasedChild>
            )
            root.join<RootOther, Parent>(
                RootOther::treatFetchInnerChild1.name,
                JoinType.INNER
            )

            root.join<RootOther, Parent>(
                RootOther::treatFetchOuterChild2.name,
                JoinType.LEFT
            )
            criteriaBuilder.treat(
                treatInnerParentJoin1 as Join<ExplicitErasedParent, ExplicitErasedChild>,
                Child1::class.java as Class<ExplicitErasedChild>
            )
            criteriaBuilder.treat(
                treatOuterParentJoin1 as Join<ExplicitErasedParent, ExplicitErasedChild>,
                Child2::class.java as Class<ExplicitErasedChild>
            )
        }
    }

    @Suppress("UNCHECKED_CAST")
    @Test
    fun treatJoinRoot() {
        // given
        val fromEntitySpec = EntitySpec(Employee::class.java)
        val entitySpec1 = EntitySpec(FullTimeEmployee::class.java, "fe")
        val entitySpec2 = EntitySpec(PartTimeEmployee::class.java, "pe")
        val fromClause = FromClause(fromEntitySpec)

        val joinSpec0 = TreatJoinSpec(
            left = fromEntitySpec,
            right = entitySpec1,
            joinType = JoinType.INNER,
            root = ColumnSpec<FullTimeEmployee>(fromEntitySpec, "fe"),
        )

        val joinSpec1 = TreatJoinSpec(
            left = fromEntitySpec,
            right = entitySpec2,
            joinType = JoinType.LEFT,
            root = ColumnSpec<PartTimeEmployee>(fromEntitySpec, "pe"),
        )

        val joinClause = JoinClause(
            listOf(
                joinSpec0,
                joinSpec1
            )
        )

        val root = mockk<Root<Employee>>()
        val join0 = mockk<Root<FullTimeEmployee>>()
        val join1 = mockk<Root<PartTimeEmployee>>()

        every { root.javaType } returns Employee::class.java
        every { query.from(Employee::class.java) } returns root
        every {
            criteriaBuilder.treat(
                root as Root<ExplicitErasedParent>,
                joinSpec0.right.type as Class<ExplicitErasedChild>
            )
        } returns join0 as Root<ExplicitErasedChild>
        every {
            criteriaBuilder.treat(
                root as Root<ExplicitErasedParent>,
                joinSpec1.right.type as Class<ExplicitErasedChild>
            )
        } returns join0 as Root<ExplicitErasedChild>

        // when
        val actual = fromClause.join(joinClause, query, criteriaBuilder)

        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(
            Froms(
                root = root,
                map = mapOf(
                    fromEntitySpec to root,
                    entitySpec1 to join0,
                    entitySpec2 to join1,
                )
            )
        )

        verify {
            query.from(Employee::class.java)
            root.javaType
            criteriaBuilder.treat(
                root as Root<ExplicitErasedParent>,
                joinSpec0.right.type as Class<ExplicitErasedChild>
            )
            criteriaBuilder.treat(
                root as Root<ExplicitErasedParent>,
                joinSpec1.right.type as Class<ExplicitErasedChild>
            )
            root.hashCode()
            join0.hashCode()
            join1.hashCode()
        }

        confirmVerified(root, join0, join1, query)
    }

    @Test
    fun `join - if join is incomplete then throw exception`() {
        // given
        val fromEntitySpec = entitySpec1
        val fromClause = FromClause(fromEntitySpec)

        val joinSpec = SimpleJoinSpec(entitySpec2, entitySpec3, "data3", JoinType.LEFT)
        val joinClause = JoinClause(listOf(joinSpec))

        val root = mockk<Root<Data1>>()

        every { query.from(Data1::class.java) } returns root

        // when
        val exception = catchThrowable(IllegalStateException::class) {
            fromClause.join(joinClause, query, criteriaBuilder)
        }

        // then
        assertThat(exception)
            .hasMessageContaining("Join clause is incomplete. Please check if the following Entities are joined")

        verify(exactly = 1) {
            query.from(Data1::class.java)
        }

        confirmVerified(root, query)
    }

    @Test
    fun associate() {
        // given
        val fromEntitySpec = entitySpec1
        val fromClause = FromClause(fromEntitySpec)

        val joinSpec1 = SimpleAssociatedJoinSpec(entitySpec1, entitySpec2, "data2")
        val joinSpec2 = SimpleAssociatedJoinSpec(entitySpec2, entitySpec3, "data3")
        val joinClause = SimpleAssociatedJoinClause(listOf(joinSpec1, joinSpec2))

        val root = mockk<Root<Data1>>()
        val join1 = mockk<Path<Any>>()
        val join2 = mockk<MockFetch<Any, Any>>()

        every { updateQuery.from(Data1::class.java) } returns root
        every { deleteQuery.from(Data1::class.java) } returns root
        every { root.get<Any>(joinSpec1.path) } returns join1
        every { join1.get<Any>(joinSpec2.path) } returns join2

        // when
        val actual = fromClause.associate(joinClause, updateQuery)
        val deleteActual = fromClause.associate(joinClause, deleteQuery)

        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(
            Froms(
                root = root,
                map = mapOf(
                    entitySpec1 to root,
                    entitySpec2 to join1,
                    entitySpec3 to join2,
                )
            )
        )
        assertThat(deleteActual).usingRecursiveComparison().isEqualTo(
            Froms(
                root = root,
                map = mapOf(
                    entitySpec1 to root,
                    entitySpec2 to join1,
                    entitySpec3 to join2,
                )
            )
        )

        verify(exactly = 1) {
            updateQuery.from(Data1::class.java)
            deleteQuery.from(Data1::class.java)
        }

        verify(exactly = 2) {
            root.get<Any>(joinSpec1.path)
            join1.get<Any>(joinSpec2.path)
        }

        verify {
            root.hashCode()
            join1.hashCode()
            join2.hashCode()
        }

        confirmVerified(root, join1, join2, updateQuery, deleteQuery)
    }

    @Test
    fun `associate - if associate is incomplete then throw exception`() {
        // given
        val fromEntitySpec = entitySpec1
        val fromClause = FromClause(fromEntitySpec)

        val joinSpec = SimpleAssociatedJoinSpec(entitySpec2, entitySpec3, "data3")
        val joinClause = SimpleAssociatedJoinClause(listOf(joinSpec))

        val root = mockk<Root<Data1>>()

        every { updateQuery.from(Data1::class.java) } returns root
        every { deleteQuery.from(Data1::class.java) } returns root

        // when
        val exception = catchThrowable(IllegalStateException::class) {
            fromClause.associate(joinClause, updateQuery)
        }

        val deleteException = catchThrowable(IllegalStateException::class) {
            fromClause.associate(joinClause, deleteQuery)
        }

        // then
        assertThat(exception)
            .hasMessageContaining("Associate clause is incomplete. Please check if the following Entities are associated")

        assertThat(deleteException)
            .hasMessageContaining("Associate clause is incomplete. Please check if the following Entities are associated")

        verify(exactly = 1) {
            updateQuery.from(Data1::class.java)
            deleteQuery.from(Data1::class.java)
        }

        confirmVerified(root, updateQuery, deleteQuery)
    }

    private class Data1
    private class Data2
    private class Data3
    private class Data4

    private class RootOther(
        val id: Long,
        val treatChildrenInner: List<Parent>,
        val treatChildrenOuter: List<Parent>,
        val innerChild2: Parent,
        val outerChild1: Parent,
        val fetchInnerChild1: Parent,
        val fetchOuterChild2: Parent,
        val treatFetchInnerChild1: Parent,
        val treatFetchOuterChild2: Parent
    )

    private open class Parent(val id: Long, val rootId: Long)
    private class Child1(parentId: Long, rootId: Long) : Parent(parentId, rootId)
    private class Child2(parentId: Long, rootId: Long) : Parent(parentId, rootId)
    private class Child3(parentId: Long, rootId: Long) : Parent(parentId, rootId)

    private interface MockFetch<T, R> : Join<T, R>, Fetch<T, R>
}
