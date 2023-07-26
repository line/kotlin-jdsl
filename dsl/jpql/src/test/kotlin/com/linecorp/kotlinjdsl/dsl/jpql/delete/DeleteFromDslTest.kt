//package com.linecorp.kotlinjdsl.dsl.jpql.delete
//
//import com.linecorp.kotlinjdsl.dsl.jpql.AbstractJpqlDslTest
//import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
//import com.linecorp.kotlinjdsl.querymodel.jpql.delete.Deletes
//import com.linecorp.kotlinjdsl.querymodel.jpql.delete.DeleteQuery
//import org.junit.jupiter.api.Test
//
//class DeleteFromDslTest : AbstractJpqlDslTest() {
//    private val alias1 = "alias1"
//
//    @Test
//    fun `update entity`() {
//        // when
//        val delete = testJpql {
//            deleteFrom(entity(TestTable::class))
//        }.toQuery()
//
//        val actual: DeleteQuery<TestTable> = delete // for type check
//
//        // then
//        val expected = Deletes.delete(
//            entity = Paths.entity(TestTable::class),
//            where = null,
//        )
//
//        assertThat(actual).isEqualTo(expected)
//    }
//
//    @Test
//    fun `update entity as string`() {
//        // when
//        val delete = testJpql {
//            deleteFrom(entity(TestTable::class).`as`(alias1))
//        }.toQuery()
//
//        val actual: DeleteQuery<TestTable> = delete // for type check
//
//        // then
//        val expected = Deletes.delete(
//            entity = Paths.alias(Paths.entity(TestTable::class), alias1),
//            where = null,
//        )
//
//        assertThat(actual).isEqualTo(expected)
//    }
//
//    private class TestTable
//}
