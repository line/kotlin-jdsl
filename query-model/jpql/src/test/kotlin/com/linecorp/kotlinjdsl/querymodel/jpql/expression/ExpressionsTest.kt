package com.linecorp.kotlinjdsl.querymodel.jpql.expression

import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entities
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.book.Book
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.book.BookAuthorType
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.employee.Employee
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlAliasedExpression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlAvg
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlCaseValue
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlCaseWhen
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlCoalesce
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlCount
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlCustomExpression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlDivide
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlEntityType
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlExpression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlExpressionParentheses
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlFunction
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlLiteral
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlMax
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlMin
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlMinus
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlNew
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlNull
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlNullIf
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlParam
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlPathType
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlPlus
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlSubquery
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlSum
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlTimes
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlValue
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicates
import com.linecorp.kotlinjdsl.querymodel.jpql.select.impl.JpqlSelectQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.sort.Sorts
import java.math.BigDecimal
import java.math.BigInteger
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class ExpressionsTest : WithAssertions {
    private val alias1 = "alias1"
    private val name1 = "name1"
    private val template1 = "template1"

    private val int1: Int = 100
    private val long1: Long = 100L
    private val float1: Float = 100f
    private val double1: Double = 100.0
    private val boolean1: Boolean = true
    private val char1: Char = 'a'
    private val string1: String = "string1"

    private val intExpression1: Expression<Int> = Expressions.value(100)
    private val intExpression2: Expression<Int> = Expressions.value(200)
    private val intExpression3: Expression<Int> = Expressions.value(300)
    private val longExpression1: Expression<Long> = Expressions.value(100L)
    private val longExpression2: Expression<Long> = Expressions.value(200L)
    private val floatExpression1: Expression<Float> = Expressions.value(100f)
    private val doubleExpression1: Expression<Double> = Expressions.value(100.0)
    private val bigIntegerExpression1: Expression<BigInteger> = Expressions.value(BigInteger.valueOf(100L))
    private val bigDecimalExpression1: Expression<BigDecimal> = Expressions.value(BigDecimal.valueOf(100L))

    private val entity1 = Entities.entity(Employee::class)

    private val path1 = Paths.path(Employee::employeeId)

    private val predicate1 = Predicates.equal(Paths.path(Book::price), Expressions.value(BigDecimal.valueOf(100)))
    private val predicate2 = Predicates.equal(Paths.path(Book::price), Expressions.value(BigDecimal.valueOf(200)))

    private val selectQuery1 = JpqlSelectQuery(
        returnType = Book::class,
        distinct = false,
        select = listOf(entity1),
        from = listOf(entity1),
        where = predicate1,
        groupBy = listOf(Paths.path(Employee::name)),
        having = predicate2,
        orderBy = listOf(Sorts.asc(Paths.path(Employee::name))),
    )

    private class Class1

    @Test
    fun value() {
        // when
        val actual = Expressions.value(string1)

        // then
        val expected = JpqlValue(
            string1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `value() with null`() {
        // when
        val actual = Expressions.value(null)

        // then
        val expected = JpqlNull

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun nullValue() {
        // when
        val actual = Expressions.nullValue<String>()

        // then
        val expected = JpqlNull

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun intLiteral() {
        // when
        val actual = Expressions.intLiteral(int1)

        // then
        val expected = JpqlLiteral.IntLiteral(
            int1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun longLiteral() {
        // when
        val actual = Expressions.longLiteral(long1)

        // then
        val expected = JpqlLiteral.LongLiteral(
            long1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun floatLiteral() {
        // when
        val actual = Expressions.floatLiteral(float1)

        // then
        val expected = JpqlLiteral.FloatLiteral(
            float1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun doubleLiteral() {
        // when
        val actual = Expressions.doubleLiteral(double1)

        // then
        val expected = JpqlLiteral.DoubleLiteral(
            double1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun booleanLiteral() {
        // when
        val actual = Expressions.booleanLiteral(boolean1)

        // then
        val expected = JpqlLiteral.BooleanLiteral(
            boolean1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun charLiteral() {
        // when
        val actual = Expressions.charLiteral(char1)

        // then
        val expected = JpqlLiteral.CharLiteral(
            char1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun stringLiteral() {
        // when
        val actual = Expressions.stringLiteral(string1)

        // then
        val expected = JpqlLiteral.StringLiteral(
            string1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun enumLiteral() {
        // when
        val actual = Expressions.enumLiteral(BookAuthorType.AUTHOR)

        // then
        val expected = JpqlLiteral.EnumLiteral(
            BookAuthorType.AUTHOR,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun nullLiteral() {
        // when
        val actual = Expressions.nullLiteral<String>()

        // then
        val expected = Expressions.nullLiteral<String>()

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `param() with a name`() {
        // when
        val actual = Expressions.param<String>(name1)

        // then
        val expected = JpqlParam(
            name = name1,
            value = null,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `param() with a name and a value`() {
        // when
        val actual = Expressions.param(name1, string1)

        // then
        val expected = JpqlParam(
            name = name1,
            value = string1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun plus() {
        // when
        val actual = Expressions.plus(intExpression1, intExpression2)

        // then
        val expected = JpqlPlus<Int>(
            intExpression1,
            intExpression2,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun minus() {
        // when
        val actual = Expressions.minus(intExpression1, intExpression2)

        // then
        val expected = JpqlMinus<Int>(
            intExpression1,
            intExpression2,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun times() {
        // when
        val actual = Expressions.times(intExpression1, intExpression2)

        // then
        val expected = JpqlTimes<Int>(
            intExpression1,
            intExpression2,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun div() {
        // when
        val actual = Expressions.div(intExpression1, intExpression2)

        // then
        val expected = JpqlDivide<Int>(
            intExpression1,
            intExpression2,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest
    @ValueSource(booleans = [true, false])
    fun count(distinct: Boolean) {
        // when
        val actual = Expressions.count(distinct, intExpression1)

        // then
        val expected = JpqlCount(
            distinct,
            intExpression1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest
    @ValueSource(booleans = [true, false])
    fun max(distinct: Boolean) {
        // when
        val actual = Expressions.max(distinct, intExpression1)

        // then
        val expected = JpqlMax(
            distinct,
            intExpression1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest
    @ValueSource(booleans = [true, false])
    fun min(distinct: Boolean) {
        // when
        val actual = Expressions.min(distinct, intExpression1)

        // then
        val expected = JpqlMin(
            distinct,
            intExpression1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest
    @ValueSource(booleans = [true, false])
    fun avg(distinct: Boolean) {
        // when
        val actual = Expressions.avg(distinct, intExpression1)

        // then
        val expected = JpqlAvg(
            distinct,
            intExpression1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest
    @ValueSource(booleans = [true, false])
    fun `sum() with an int expression`(distinct: Boolean) {
        // when
        val actual = Expressions.sum(distinct, intExpression1)

        // then
        val expected = JpqlSum.IntSum(
            distinct,
            intExpression1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest
    @ValueSource(booleans = [true, false])
    fun `sum() with a long expression`(distinct: Boolean) {
        // when
        val actual = Expressions.sum(distinct, longExpression1)

        // then
        val expected = JpqlSum.LongSum(
            distinct,
            longExpression1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest
    @ValueSource(booleans = [true, false])
    fun `sum() with a float expression`(distinct: Boolean) {
        // when
        val actual = Expressions.sum(distinct, floatExpression1)

        // then
        val expected = JpqlSum.FloatSum(
            distinct,
            floatExpression1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest
    @ValueSource(booleans = [true, false])
    fun `sum() with a double expression`(distinct: Boolean) {
        // when
        val actual = Expressions.sum(distinct, doubleExpression1)

        // then
        val expected = JpqlSum.DoubleSum(
            distinct,
            doubleExpression1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest
    @ValueSource(booleans = [true, false])
    fun `sum() with a bigInteger expression`(distinct: Boolean) {
        // when
        val actual = Expressions.sum(distinct, bigIntegerExpression1)

        // then
        val expected = JpqlSum.BigIntegerSum(
            distinct,
            bigIntegerExpression1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest
    @ValueSource(booleans = [true, false])
    fun `sum() with a bigDecimal expression`(distinct: Boolean) {
        // when
        val actual = Expressions.sum(distinct, bigDecimalExpression1)

        // then
        val expected = JpqlSum.BigDecimalSum(
            distinct,
            bigDecimalExpression1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun new() {
        // when
        val actual = Expressions.new(Class1::class, listOf(intExpression1, intExpression2))

        // then
        val expected = JpqlNew(
            Class1::class,
            listOf(intExpression1, intExpression2),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun caseWhen() {
        // when
        val actual = Expressions.caseWhen(
            whens = mapOf(
                predicate1 to intExpression1,
                predicate2 to intExpression2,
            ),
            `else` = intExpression3,
        )

        // then
        val expected = JpqlCaseWhen(
            whens = mapOf(
                predicate1 to intExpression1,
                predicate2 to intExpression2,
            ),
            `else` = intExpression3,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun caseValue() {
        // when
        val actual = Expressions.caseValue(
            value = path1,
            whens = mapOf(
                longExpression1 to intExpression1,
                longExpression2 to intExpression2,
            ),
            `else` = intExpression3,
        )

        // then
        val expected = JpqlCaseValue(
            value = path1,
            whens = mapOf(
                longExpression1 to intExpression1,
                longExpression2 to intExpression2,
            ),
            `else` = intExpression3,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun coalesce() {
        // when
        val actual = Expressions.coalesce(
            value = intExpression1,
            alternate = intExpression2,
            others = listOf(intExpression3),
        )

        // then
        val expected = JpqlCoalesce(
            listOf(
                intExpression1,
                intExpression2,
                intExpression3,
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun nullIf() {
        // when
        val actual = Expressions.nullIf(
            value = intExpression1,
            compareValue = intExpression2,
        )

        // then
        val expected = JpqlNullIf(
            value = intExpression1,
            compareValue = intExpression2,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `type() with an entity`() {
        // when
        val actual = Expressions.type(entity1)

        // then
        val expected = JpqlEntityType(
            entity1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `type() with a path`() {
        // when
        val actual = Expressions.type(path1)

        // then
        val expected = JpqlPathType(
            path1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun function() {
        // when
        val actual = Expressions.function(
            type = Class1::class,
            name = name1,
            args = listOf(intExpression1, intExpression2),
        )

        // then
        val expected = JpqlFunction(
            type = Class1::class,
            name = name1,
            args = listOf(intExpression1, intExpression2),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun customExpression() {
        // when
        val actual = Expressions.customExpression(
            type = Class1::class,
            template = template1,
            args = listOf(intExpression1, intExpression2),
        )

        // then
        val expected = JpqlCustomExpression(
            type = Class1::class,
            template = template1,
            args = listOf(intExpression1, intExpression2),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun subquery() {
        // when
        val actual = Expressions.subquery(
            selectQuery1,
        )

        // then
        val expected = JpqlSubquery(
            selectQuery1.copy(orderBy = null),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun alias() {
        // when
        val actual = Expressions.alias(
            intExpression1,
            intExpression2,
        )

        // then
        val expected = JpqlAliasedExpression(
            intExpression1,
            intExpression2,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `alias() with the already aliased expression`() {
        // when
        val actual = Expressions.alias(
            JpqlAliasedExpression(intExpression1, intExpression2),
            intExpression3,
        )

        // then
        val expected = JpqlAliasedExpression(
            intExpression1,
            intExpression3,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun expression() {
        // when
        val actual = Expressions.expression(String::class, alias1)

        // then
        val expected = JpqlExpression(
            type = String::class,
            alias = alias1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun parentheses() {
        // when
        val actual = Expressions.parentheses(intExpression1)

        // then
        val expected = JpqlExpressionParentheses(
            intExpression1,
        )

        assertThat(actual).isEqualTo(expected)
    }
}
