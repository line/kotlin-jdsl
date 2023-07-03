package com.linecorp.kotlinjdsl.dsl.sql

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.dsl.owner
import com.linecorp.kotlinjdsl.dsl.sql.delete.DeleteQueryWhereStep
import com.linecorp.kotlinjdsl.dsl.sql.delete.impl.DeleteQueryDsl
import com.linecorp.kotlinjdsl.dsl.sql.expression.CaseWhenFirstStep
import com.linecorp.kotlinjdsl.dsl.sql.expression.CaseWhenMoreStep
import com.linecorp.kotlinjdsl.dsl.sql.expression.impl.CaseDsl
import com.linecorp.kotlinjdsl.dsl.sql.insert.InsertQueryColumnValueStep
import com.linecorp.kotlinjdsl.dsl.sql.insert.impl.InsertQueryDsl
import com.linecorp.kotlinjdsl.dsl.sql.select.SelectQueryFromStep
import com.linecorp.kotlinjdsl.dsl.sql.select.impl.SelectQueryDsl
import com.linecorp.kotlinjdsl.dsl.sql.update.UpdateQuerySetFirstStep
import com.linecorp.kotlinjdsl.dsl.sql.update.impl.UpdateQueryDsl
import com.linecorp.kotlinjdsl.query.sql.*
import com.linecorp.kotlinjdsl.query.sql.impl.*
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1

@SinceJdsl("3.0.0")
open class Normal : SqlDsl {
    companion object Constructor : SqlDsl.Constructor<Normal> {
        override fun newInstance(): Normal = Normal()
    }

    @JvmName("asterisk1")
    @SinceJdsl("3.0.0")
    fun asterisk(): Expression<Any> {
        return Asterisk
    }

    @JvmName("asterisk2")
    @SinceJdsl("3.0.0")
    fun <T : Any> Table<T>.asterisk(): Expression<T> {
        return TableAsterisk(this)
    }

    @JvmName("asterisk3")
    @SinceJdsl("3.0.0")
    fun <T : Any> asterisk(table: Table<T>): Expression<T> {
        return TableAsterisk(table)
    }

    @JvmName("literal1")
    @SinceJdsl("3.0.0")
    fun <T> literal(value: T): Expression<T> {
        return Literal(value)
    }

    @JvmName("max1")
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> max(expression: Expressionable<T>): Expression<T> {
        return Max(expression.toExpression(), distinct = false)
    }

    @JvmName("maxDistinct1")
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> maxDistinct(expression: Expressionable<T>): Expression<T> {
        return Max(expression.toExpression(), distinct = true)
    }

    @JvmName("min1")
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> min(expression: Expressionable<T>): Expression<T> {
        return Min(expression.toExpression(), distinct = false)
    }

    @JvmName("minDistinct1")
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> minDistinct(expression: Expressionable<T>): Expression<T> {
        return Min(expression.toExpression(), distinct = true)
    }

    @JvmName("count1")
    @SinceJdsl("3.0.0")
    fun <T : Number> count(expression: Expressionable<T>): Expression<Long> {
        return Count(expression.toExpression(), distinct = false)
    }

    @JvmName("countDistinct1")
    @SinceJdsl("3.0.0")
    fun <T : Number> countDistinct(expression: Expressionable<T>): Expression<Long> {
        return Count(expression.toExpression(), distinct = true)
    }

    @JvmName("avg1")
    @SinceJdsl("3.0.0")
    fun <T : Number> avg(expression: Expressionable<T>): Expression<Double> {
        return Avg(expression.toExpression(), distinct = false)
    }

    @JvmName("avgDistinct1")
    @SinceJdsl("3.0.0")
    fun <T : Number> avgDistinct(expression: Expressionable<T>): Expression<Double> {
        return Avg(expression.toExpression(), distinct = true)
    }

    @JvmName("sum1")
    @SinceJdsl("3.0.0")
    fun <T : Number> sum(expression: Expressionable<T>): Expression<Long> {
        return Sum(expression.toExpression(), distinct = false)
    }

    @JvmName("sumDistinct1")
    @SinceJdsl("3.0.0")
    fun <T : Number> sumDistinct(expression: Expressionable<T>): Expression<Long> {
        return Sum(expression.toExpression(), distinct = true)
    }

    @JvmName("case1")
    @SinceJdsl("3.0.0")
    fun <T> case(): CaseWhenFirstStep<T> {
        return CaseDsl()
    }

    @JvmName("caseWhen1")
    @SinceJdsl("3.0.0")
    fun <T> caseWhen(predicate: Predicatable, expression: Expressionable<T>): CaseWhenMoreStep<T?> {
        return CaseDsl<T>().`when`(predicate.toPredicate(), expression.toExpression())
    }

    @JvmName("templateExpression1")
    @SinceJdsl("3.0.0")
    inline fun <reified T : Any> templateExpression(template: String, vararg args: Expressionable<*>): Expression<T> {
        return TemplateExpression(T::class, template, args.map { it.toExpression() })
    }

    @JvmName("templateExpression2")
    @SinceJdsl("3.0.0")
    inline fun <reified T : Any> templateExpression(
        template: String,
        args: Collection<Expressionable<*>>,
    ): Expression<T> {
        return TemplateExpression(T::class, template, args.map { it.toExpression() })
    }

    @JvmName("as1")
    @SinceJdsl("3.0.0")
    fun <T : Any> Table<T>.`as`(alias: String): Table<T> {
        return AliasedTable(this, alias)
    }

    @JvmName("as2")
    @SinceJdsl("3.0.0")
    fun <T> Expressionable<T>.`as`(alias: String): Expression<T> {
        return AliasedExpression(this.toExpression(), alias)
    }

    @JvmName("table1")
    @SinceJdsl("3.0.0")
    fun <T : Any> table(type: KClass<T>): TableReference<T> {
        return TableReference(type)
    }

    @JvmName("join1")
    @SinceJdsl("3.0.0")
    fun Table<*>.join(table: Table<*>): Table<Any> {
        return JoinedTable(this, table, null)
    }

    @JvmName("join2")
    @SinceJdsl("3.0.0")
    fun Table<*>.join(table: Table<*>, on: Predicatable): Table<Any> {
        return JoinedTable(this, table, on.toPredicate())
    }

    @JvmName("join3")
    @SinceJdsl("3.0.0")
    fun join(left: Table<*>, right: Table<*>): Table<Any> {
        return JoinedTable(left, right, null)
    }

    @JvmName("join4")
    @SinceJdsl("3.0.0")
    fun join(left: Table<*>, right: Table<*>, on: Predicatable): Table<Any> {
        return JoinedTable(left, right, on.toPredicate())
    }

    @JvmName("innerJoin1")
    @SinceJdsl("3.0.0")
    fun Table<*>.innerJoin(table: Table<*>): Table<Any> {
        return InnerJoinedTable(this, table, null)
    }

    @JvmName("innerJoin2")
    @SinceJdsl("3.0.0")
    fun Table<*>.innerJoin(table: Table<*>, on: Predicatable): Table<Any> {
        return InnerJoinedTable(this, table, on.toPredicate())
    }

    @JvmName("innerJoin3")
    @SinceJdsl("3.0.0")
    fun innerJoin(left: Table<*>, right: Table<*>): Table<Any> {
        return InnerJoinedTable(left, right, null)
    }

    @JvmName("innerJoin4")
    @SinceJdsl("3.0.0")
    fun innerJoin(left: Table<*>, right: Table<*>, on: Predicatable): Table<Any> {
        return InnerJoinedTable(left, right, on.toPredicate())
    }

    @JvmName("crossJoin1")
    @SinceJdsl("3.0.0")
    fun Table<*>.crossJoin(table: Table<*>): Table<Any> {
        return CrossJoinedTable(this, table, null)
    }

    @JvmName("crossJoin2")
    @SinceJdsl("3.0.0")
    fun Table<*>.crossJoin(table: Table<*>, on: Predicatable): Table<Any> {
        return CrossJoinedTable(this, table, on.toPredicate())
    }

    @JvmName("crossJoin3")
    @SinceJdsl("3.0.0")
    fun crossJoin(left: Table<*>, right: Table<*>): Table<Any> {
        return CrossJoinedTable(left, right, null)
    }

    @JvmName("crossJoin4")
    @SinceJdsl("3.0.0")
    fun crossJoin(left: Table<*>, right: Table<*>, on: Predicatable): Table<Any> {
        return CrossJoinedTable(left, right, on.toPredicate())
    }

    @JvmName("leftJoin1")
    @SinceJdsl("3.0.0")
    fun Table<*>.leftJoin(table: Table<*>, on: Predicatable): Table<Any> {
        return LeftOuterJoinedTable(this, table, on.toPredicate())
    }

    @JvmName("leftJoin2")
    @SinceJdsl("3.0.0")
    fun leftJoin(left: Table<*>, right: Table<*>, on: Predicatable): Table<Any> {
        return LeftOuterJoinedTable(left, right, on.toPredicate())
    }

    @JvmName("rightJoin1")
    @SinceJdsl("3.0.0")
    fun Table<*>.rightJoin(table: Table<*>, on: Predicatable): Table<Any> {
        return RightOuterJoinedTable(this, table, on.toPredicate())
    }

    @JvmName("rightJoin2")
    @SinceJdsl("3.0.0")
    fun rightJoin(left: Table<*>, right: Table<*>, on: Predicatable): Table<Any> {
        return RightOuterJoinedTable(left, right, on.toPredicate())
    }

    @JvmName("asTable1")
    @SinceJdsl("3.0.0")
    fun SqlQueryable<SelectQuery>.asTable(): Table<Any> {
        return DerivedTable(this.toQuery())
    }

    @JvmName("asTable2")
    @SinceJdsl("3.0.0")
    fun <T : Any> SqlQueryable<SelectQuery>.asTable(): Table<T> {
        return DerivedTable(this.toQuery())
    }

    @JvmName("asTable3")
    @SinceJdsl("3.0.0")
    fun <T : Any> SqlQueryable<SelectQuery>.asTable(alias: String): Table<T> {
        return DerivedTable<T>(this.toQuery()).`as`(alias)
    }

    @JvmName("asExpression1")
    @SinceJdsl("3.0.0")
    fun <T> SelectQuery.asExpression(): Expression<T> {
        return Subquery(this)
    }

    @JvmName("asExpression2")
    @SinceJdsl("3.0.0")
    fun <T> SelectQuery.asExpression(alias: String): Expression<T> {
        return Subquery<T>(this).`as`(alias)
    }

    @JvmName("col1")
    @SinceJdsl("3.0.0")
    fun <T : Any, V> Table<T>.col(property: KProperty1<T, V>): Column<T, V> {
        return Column(this, property)
    }

    @JvmName("col2")
    @SinceJdsl("3.0.0")
    fun <T : Any, V> col(table: Table<T>, property: KProperty1<T, V>): Column<T, V> {
        return Column(table, property)
    }

    @JvmName("col3")
    @SinceJdsl("3.0.0")
    fun <T : Any, V> col(property: KProperty1<T, V>): Column<T, V> {
        return Column(table(property.owner()), property)
    }

    @JvmName("column1")
    @SinceJdsl("3.0.0")
    fun <T : Any, V> Table<T>.column(property: KProperty1<T, V>): Column<T, V> {
        return Column(this, property)
    }

    @JvmName("column2")
    @SinceJdsl("3.0.0")
    fun <T : Any, V> column(table: Table<T>, property: KProperty1<T, V>): Column<T, V> {
        return Column(table, property)
    }

    @JvmName("column3")
    @SinceJdsl("3.0.0")
    fun <T : Any, V> column(property: KProperty1<T, V>): Column<T, V> {
        return Column(table(property.owner()), property)
    }

    @JvmName("exits1")
    @SinceJdsl("3.0.0")
    fun Table<*>.exits(): Predicate {
        return Exists(this, not = false)
    }

    @JvmName("exits2")
    @SinceJdsl("3.0.0")
    fun exits(table: Table<*>): Predicate {
        return Exists(table, not = false)
    }

    @JvmName("notExits1")
    @SinceJdsl("3.0.0")
    fun Table<*>.notExits(): Predicate {
        return Exists(this, not = true)
    }

    @JvmName("notExits2")
    @SinceJdsl("3.0.0")
    fun notExits(table: Table<*>): Predicate {
        return Exists(table, not = true)
    }

    @JvmName("equal1")
    @SinceJdsl("3.0.0")
    fun <T> Expressionable<T>.equal(value: T): Predicate {
        return Equal(this.toExpression(), Literal(value), not = false)
    }

    @JvmName("equal2")
    @SinceJdsl("3.0.0")
    fun <T> Expressionable<T>.equal(expression: Expressionable<T>): Predicate {
        return Equal(this.toExpression(), expression.toExpression(), not = false)
    }

    @JvmName("equal3")
    @SinceJdsl("3.0.0")
    fun <T> equal(left: Expressionable<T>, right: T): Predicate {
        return Equal(left.toExpression(), Literal(right), not = false)
    }

    @JvmName("equal4")
    @SinceJdsl("3.0.0")
    fun <T> equal(left: Expressionable<T>, right: Expressionable<T>): Predicate {
        return Equal(left.toExpression(), right.toExpression(), not = false)
    }

    @JvmName("notEqual1")
    @SinceJdsl("3.0.0")
    fun <T> Expressionable<T>.notEqual(value: T): Predicate {
        return Equal(this.toExpression(), Literal(value), not = true)
    }

    @JvmName("notEqual2")
    @SinceJdsl("3.0.0")
    fun <T> Expressionable<T>.notEqual(expression: Expressionable<T>): Predicate {
        return Equal(this.toExpression(), expression.toExpression(), not = true)
    }

    @JvmName("notEqual3")
    @SinceJdsl("3.0.0")
    fun <T> notEqual(left: Expressionable<T>, right: T): Predicate {
        return Equal(left.toExpression(), Literal(right), not = true)
    }

    @JvmName("notEqual4")
    @SinceJdsl("3.0.0")
    fun <T> notEqual(left: Expressionable<T>, right: Expressionable<T>): Predicate {
        return Equal(left.toExpression(), right.toExpression(), not = true)
    }

    @JvmName("between1")
    @SinceJdsl("3.0.0")
    fun <T> Expressionable<T>.between(value1: T, value2: T): Predicate {
        return Between(this.toExpression(), literal(value1), literal(value2), not = false)
    }

    @JvmName("between2")
    @SinceJdsl("3.0.0")
    fun <T> Expressionable<T>.between(expression1: Expressionable<T>, expression2: Expressionable<T>): Predicate {
        return Between(this.toExpression(), expression1.toExpression(), expression2.toExpression(), not = false)
    }

    @JvmName("between3")
    @SinceJdsl("3.0.0")
    fun <T> between(left: Expressionable<T>, right1: T, right2: T): Predicate {
        return Between(left.toExpression(), literal(right1), literal(right2), not = false)
    }

    @JvmName("between4")
    @SinceJdsl("3.0.0")
    fun <T> between(left: Expressionable<T>, right1: Expressionable<T>, right2: Expressionable<T>): Predicate {
        return Between(left.toExpression(), right1.toExpression(), right2.toExpression(), not = false)
    }

    @JvmName("notBetween1")
    @SinceJdsl("3.0.0")
    fun <T> Expressionable<T>.notBetween(value1: T, value2: T): Predicate {
        return Between(this.toExpression(), literal(value1), literal(value2), not = true)
    }

    @JvmName("notBetween2")
    @SinceJdsl("3.0.0")
    fun <T> Expressionable<T>.notBetween(expression1: Expressionable<T>, expression2: Expressionable<T>): Predicate {
        return Between(this.toExpression(), expression1.toExpression(), expression2.toExpression(), not = true)
    }

    @JvmName("notBetween3")
    @SinceJdsl("3.0.0")
    fun <T> notBetween(left: Expressionable<T>, right1: T, right2: T): Predicate {
        return Between(left.toExpression(), literal(right1), literal(right2), not = true)
    }

    @JvmName("notBetween4")
    @SinceJdsl("3.0.0")
    fun <T> notBetween(left: Expressionable<T>, right1: Expressionable<T>, right2: Expressionable<T>): Predicate {
        return Between(left.toExpression(), right1.toExpression(), right2.toExpression(), not = true)
    }

    @JvmName("like1")
    @SinceJdsl("3.0.0")
    fun Expressionable<*>.like(value: String): Predicate {
        return Like(this.toExpression(), Literal(value), not = false)
    }

    @JvmName("like2")
    @SinceJdsl("3.0.0")
    fun Expressionable<*>.like(expression: Expressionable<String>): Predicate {
        return Like(this.toExpression(), expression.toExpression(), not = false)
    }

    @JvmName("like3")
    @SinceJdsl("3.0.0")
    fun like(left: Expressionable<*>, right: String): Predicate {
        return Like(left.toExpression(), Literal(right), not = false)
    }

    @JvmName("like4")
    @SinceJdsl("3.0.0")
    fun like(left: Expressionable<*>, right: Expressionable<String>): Predicate {
        return Like(left.toExpression(), right.toExpression(), not = false)
    }

    @JvmName("notLike1")
    @SinceJdsl("3.0.0")
    fun Expressionable<*>.notLike(value: String): Predicate {
        return Like(this.toExpression(), Literal(value), not = true)
    }

    @JvmName("notLike2")
    @SinceJdsl("3.0.0")
    fun Expressionable<*>.notLike(expression: Expressionable<String>): Predicate {
        return Like(this.toExpression(), expression.toExpression(), not = true)
    }

    @JvmName("notLike3")
    @SinceJdsl("3.0.0")
    fun notLike(left: Expressionable<*>, right: String): Predicate {
        return Like(left.toExpression(), Literal(right), not = true)
    }

    @JvmName("notLike4")
    @SinceJdsl("3.0.0")
    fun notLike(left: Expressionable<*>, right: Expressionable<String>): Predicate {
        return Like(left.toExpression(), right.toExpression(), not = true)
    }

    @JvmName("greaterThan1")
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>?> Expressionable<T>.greaterThan(value: T, inclusive: Boolean = false): Predicate {
        return GreaterThan(this.toExpression(), Literal(value), inclusive, not = false)
    }

    @JvmName("greaterThan2")
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>?> Expressionable<T>.greaterThan(
        expression: Expressionable<T>,
        inclusive: Boolean = false
    ): Predicate {
        return GreaterThan(this.toExpression(), expression.toExpression(), inclusive, not = false)
    }

    @JvmName("greaterThan3")
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>?> greaterThan(left: Expressionable<T>, right: T, inclusive: Boolean = false): Predicate {
        return GreaterThan(left.toExpression(), Literal(right), inclusive, not = false)
    }

    @JvmName("greaterThan4")
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>?> greaterThan(
        left: Expressionable<T>,
        right: Expressionable<T>,
        inclusive: Boolean = false
    ): Predicate {
        return GreaterThan(left.toExpression(), right.toExpression(), inclusive, not = false)
    }

    @JvmName("greaterThanEqualTo1")
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>?> Expressionable<T>.greaterThanEqualTo(value: T): Predicate {
        return GreaterThan(this.toExpression(), Literal(value), inclusive = true, not = false)
    }

    @JvmName("greaterThanEqualTo2")
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>?> Expressionable<T>.greaterThanEqualTo(expression: Expressionable<T>): Predicate {
        return GreaterThan(this.toExpression(), expression.toExpression(), inclusive = true, not = false)
    }

    @JvmName("greaterThanEqualTo3")
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>?> greaterThanEqualTo(left: Expressionable<T>, right: T): Predicate {
        return GreaterThan(left.toExpression(), Literal(right), inclusive = true, not = false)
    }

    @JvmName("greaterThanEqualTo4")
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>?> greaterThanEqualTo(left: Expressionable<T>, right: Expressionable<T>): Predicate {
        return GreaterThan(left.toExpression(), right.toExpression(), inclusive = true, not = false)
    }

    @JvmName("notGreaterThan1")
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>?> Expressionable<T>.notGreaterThan(value: T, inclusive: Boolean = false): Predicate {
        return GreaterThan(this.toExpression(), Literal(value), inclusive, not = true)
    }

    @JvmName("notGreaterThan2")
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>?> Expressionable<T>.notGreaterThan(
        expression: Expressionable<T>,
        inclusive: Boolean = false
    ): Predicate {
        return GreaterThan(this.toExpression(), expression.toExpression(), inclusive, not = true)
    }

    @JvmName("notGreaterThan3")
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>?> notGreaterThan(left: Expressionable<T>, right: T, inclusive: Boolean = false): Predicate {
        return GreaterThan(left.toExpression(), Literal(right), inclusive, not = true)
    }

    @JvmName("notGreaterThan4")
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>?> notGreaterThan(
        left: Expressionable<T>,
        right: Expressionable<T>,
        inclusive: Boolean = false
    ): Predicate {
        return GreaterThan(left.toExpression(), right.toExpression(), inclusive, not = true)
    }

    @JvmName("notGreaterThanEqualTo1")
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>?> Expressionable<T>.notGreaterThanEqualTo(value: T): Predicate {
        return GreaterThan(this.toExpression(), Literal(value), inclusive = true, not = true)
    }

    @JvmName("notGreaterThanEqualTo2")
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>?> Expressionable<T>.notGreaterThanEqualTo(expression: Expressionable<T>): Predicate {
        return GreaterThan(this.toExpression(), expression.toExpression(), inclusive = true, not = true)
    }

    @JvmName("notGreaterThanEqualTo3")
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>?> notGreaterThanEqualTo(left: Expressionable<T>, right: T): Predicate {
        return GreaterThan(left.toExpression(), Literal(right), inclusive = true, not = true)
    }

    @JvmName("notGreaterThanEqualTo4")
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>?> notGreaterThanEqualTo(left: Expressionable<T>, right: Expressionable<T>): Predicate {
        return GreaterThan(left.toExpression(), right.toExpression(), inclusive = true, not = true)
    }

    @JvmName("lessThan1")
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>?> Expressionable<T>.lessThan(value: T, inclusive: Boolean = false): Predicate {
        return LessThan(this.toExpression(), Literal(value), inclusive, not = false)
    }

    @JvmName("lessThan2")
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>?> Expressionable<T>.lessThan(
        expression: Expressionable<T>,
        inclusive: Boolean = false
    ): Predicate {
        return LessThan(this.toExpression(), expression.toExpression(), inclusive, not = false)
    }

    @JvmName("lessThan3")
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>?> lessThan(left: Expressionable<T>, right: T, inclusive: Boolean = false): Predicate {
        return LessThan(left.toExpression(), Literal(right), inclusive, not = false)
    }

    @JvmName("lessThan4")
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>?> lessThan(
        left: Expressionable<T>,
        right: Expressionable<T>,
        inclusive: Boolean = false
    ): Predicate {
        return LessThan(left.toExpression(), right.toExpression(), inclusive, not = false)
    }

    @JvmName("lessThanEqualTo1")
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>?> Expressionable<T>.lessThanEqualTo(value: T): Predicate {
        return LessThan(this.toExpression(), Literal(value), inclusive = true, not = false)
    }

    @JvmName("lessThanEqualTo2")
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>?> Expressionable<T>.lessThanEqualTo(expression: Expressionable<T>): Predicate {
        return LessThan(this.toExpression(), expression.toExpression(), inclusive = true, not = false)
    }

    @JvmName("lessThanEqualTo3")
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>?> lessThanEqualTo(left: Expressionable<T>, right: T): Predicate {
        return LessThan(left.toExpression(), Literal(right), inclusive = true, not = false)
    }

    @JvmName("lessThanEqualTo4")
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>?> lessThanEqualTo(left: Expressionable<T>, right: Expressionable<T>): Predicate {
        return LessThan(left.toExpression(), right.toExpression(), inclusive = true, not = false)
    }

    @JvmName("notLessThan1")
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>?> Expressionable<T>.notLessThan(value: T, inclusive: Boolean = false): Predicate {
        return LessThan(this.toExpression(), Literal(value), inclusive, not = true)
    }

    @JvmName("notLessThan2")
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>?> Expressionable<T>.notLessThan(
        expression: Expressionable<T>,
        inclusive: Boolean = false
    ): Predicate {
        return LessThan(this.toExpression(), expression.toExpression(), inclusive, not = true)
    }

    @JvmName("notLessThan3")
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>?> notLessThan(left: Expressionable<T>, right: T, inclusive: Boolean = false): Predicate {
        return LessThan(left.toExpression(), Literal(right), inclusive, not = true)
    }

    @JvmName("notLessThan4")
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>?> notLessThan(
        left: Expressionable<T>,
        right: Expressionable<T>,
        inclusive: Boolean = false
    ): Predicate {
        return LessThan(left.toExpression(), right.toExpression(), inclusive, not = true)
    }

    @JvmName("notLessThanEqualTo1")
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>?> Expressionable<T>.notLessThanEqualTo(value: T): Predicate {
        return LessThan(this.toExpression(), Literal(value), inclusive = true, not = true)
    }

    @JvmName("notLessThanEqualTo2")
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>?> Expressionable<T>.notLessThanEqualTo(expression: Expressionable<T>): Predicate {
        return LessThan(this.toExpression(), expression.toExpression(), inclusive = true, not = true)
    }

    @JvmName("notLessThanEqualTo3")
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>?> notLessThanEqualTo(left: Expressionable<T>, right: T): Predicate {
        return LessThan(left.toExpression(), Literal(right), inclusive = true, not = true)
    }

    @JvmName("notLessThanEqualTo4")
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>?> notLessThanEqualTo(left: Expressionable<T>, right: Expressionable<T>): Predicate {
        return LessThan(left.toExpression(), right.toExpression(), inclusive = true, not = true)
    }

    @JvmName("isNull1")
    @SinceJdsl("3.0.0")
    fun Expressionable<*>.isNull(): Predicate {
        return IsNull(this.toExpression(), not = false)
    }

    @JvmName("isNull2")
    @SinceJdsl("3.0.0")
    fun isNull(expression: Expressionable<*>): Predicate {
        return IsNull(expression.toExpression(), not = false)
    }

    @JvmName("isNotNull1")
    @SinceJdsl("3.0.0")
    fun Expressionable<*>.isNotNull(): Predicate {
        return IsNull(this.toExpression(), not = true)
    }

    @JvmName("isNotNull2")
    @SinceJdsl("3.0.0")
    fun isNotNull(expression: Expressionable<*>): Predicate {
        return IsNull(expression.toExpression(), not = true)
    }

    @JvmName("isTrue1")
    @SinceJdsl("3.0.0")
    fun <T : Boolean?> Expressionable<T>.isTrue(): Predicate {
        return IsTrue(this.toExpression(), not = false)
    }

    @JvmName("isTrue2")
    @SinceJdsl("3.0.0")
    fun <T : Boolean?> isTrue(expression: Expressionable<T>): Predicate {
        return IsTrue(expression.toExpression(), not = false)
    }

    @JvmName("isNotTrue1")
    @SinceJdsl("3.0.0")
    fun <T : Boolean?> Expressionable<T>.isNotTrue(): Predicate {
        return IsTrue(this.toExpression(), not = true)
    }

    @JvmName("isNotTrue2")
    @SinceJdsl("3.0.0")
    fun <T : Boolean?> isNotTrue(expression: Expressionable<T>): Predicate {
        return IsTrue(expression.toExpression(), not = true)
    }

    @JvmName("isFalse1")
    @SinceJdsl("3.0.0")
    fun <T : Boolean?> Expressionable<T>.isFalse(): Predicate {
        return IsFalse(this.toExpression(), not = false)
    }

    @JvmName("isFalse2")
    @SinceJdsl("3.0.0")
    fun <T : Boolean?> isFalse(expression: Expressionable<T>): Predicate {
        return IsFalse(expression.toExpression(), not = false)
    }

    @JvmName("isNotFalse1")
    @SinceJdsl("3.0.0")
    fun <T : Boolean?> Expressionable<T>.isNotFalse(): Predicate {
        return IsFalse(this.toExpression(), not = true)
    }

    @JvmName("isNotFalse2")
    @SinceJdsl("3.0.0")
    fun <T : Boolean?> isNotFalse(expression: Expressionable<T>): Predicate {
        return IsFalse(expression.toExpression(), not = true)
    }

    @JvmName("not1")
    @SinceJdsl("3.0.0")
    fun not(predicate: Predicatable): Predicate {
        return Not(predicate.toPredicate())
    }

    @JvmName("not2")
    @SinceJdsl("3.0.0")
    fun Predicatable.not(): Predicate {
        return Not(this.toPredicate())
    }

    @JvmName("and1")
    @SinceJdsl("3.0.0")
    fun Predicatable.and(predicate: Predicatable): Predicate {
        return And(listOf(this.toPredicate(), predicate.toPredicate()))
    }

    @JvmName("and2")
    @SinceJdsl("3.0.0")
    fun and(vararg predicates: Predicatable): Predicate {
        return And(predicates.map { it.toPredicate() })
    }

    @JvmName("and3")
    @SinceJdsl("3.0.0")
    fun and(predicates: Collection<Predicatable>): Predicate {
        return And(predicates.map { it.toPredicate() })
    }

    @JvmName("or1")
    @SinceJdsl("3.0.0")
    fun Predicatable.or(predicate: Predicatable): Predicate {
        return Or(listOf(this.toPredicate(), predicate.toPredicate()))
    }

    @JvmName("or2")
    @SinceJdsl("3.0.0")
    fun or(vararg predicates: Predicatable): Predicate {
        return Or(predicates.map { it.toPredicate() })
    }

    @JvmName("or3")
    @SinceJdsl("3.0.0")
    fun or(predicates: Collection<Predicatable>): Predicate {
        return Or(predicates.map { it.toPredicate() })
    }

    @JvmName("in1")
    @SinceJdsl("3.0.0")
    fun <T> Expressionable<T>.`in`(vararg expressions: T): Predicate {
        return In(this.toExpression(), expressions.map { Literal(it) }, not = false)
    }

    @JvmName("in2")
    @SinceJdsl("3.0.0")
    fun <T> Expressionable<T>.`in`(expressions: Collection<T>): Predicate {
        return In(this.toExpression(), expressions.map { Literal(it) }, not = false)
    }

    @JvmName("in3")
    @SinceJdsl("3.0.0")
    fun <T> Expressionable<T>.`in`(vararg expressions: Expressionable<T>): Predicate {
        return In(this.toExpression(), expressions.map { it.toExpression() }, not = false)
    }

    @JvmName("in4")
    @SinceJdsl("3.0.0")
    fun <T> Expressionable<T>.`in`(expressions: Collection<Expressionable<T>>): Predicate {
        return In(this.toExpression(), expressions.map { it.toExpression() }, not = false)
    }

    @JvmName("notIn1")
    @SinceJdsl("3.0.0")
    fun <T> Expressionable<T>.notIn(vararg expressions: T): Predicate {
        return In(this.toExpression(), expressions.map { Literal(it) }, not = true)
    }

    @JvmName("notIn2")
    @SinceJdsl("3.0.0")
    fun <T> Expressionable<T>.notIn(expressions: Collection<T>): Predicate {
        return In(this.toExpression(), expressions.map { Literal(it) }, not = true)
    }

    @JvmName("notIn3")
    @SinceJdsl("3.0.0")
    fun <T> Expressionable<T>.notIn(vararg expressions: Expressionable<T>): Predicate {
        return In(this.toExpression(), expressions.map { it.toExpression() }, not = true)
    }

    @JvmName("notIn4")
    @SinceJdsl("3.0.0")
    fun <T> Expressionable<T>.notIn(expressions: Collection<Expressionable<T>>): Predicate {
        return In(this.toExpression(), expressions.map { it.toExpression() }, not = true)
    }

    @JvmName("asc1")
    @SinceJdsl("3.0.0")
    fun Expressionable<*>.asc(): Sort {
        return SortExpression(this.toExpression(), Sort.Order.ASC)
    }

    @JvmName("desc1")
    @SinceJdsl("3.0.0")
    fun Expressionable<*>.desc(): Sort {
        return SortExpression(this.toExpression(), Sort.Order.DESC)
    }

    @JvmName("insertInto1")
    @SinceJdsl("3.0.0")
    fun <T : Any> insertInto(table: TableReference<T>): InsertQueryColumnValueStep<T> {
        return InsertQueryDsl<T, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?>(
            table,
        )
    }

    @JvmName("update1")
    @SinceJdsl("3.0.0")
    fun <T : Any> update(table: TableReference<T>): UpdateQuerySetFirstStep<T> {
        return UpdateQueryDsl(table)
    }

    @JvmName("deleteFrom1")
    @SinceJdsl("3.0.0")
    fun <T : Any> deleteFrom(table: TableReference<T>): DeleteQueryWhereStep<T> {
        return DeleteQueryDsl(table)
    }

    @JvmName("select1")
    @SinceJdsl("3.0.0")
    fun select(vararg values: Any?): SelectQueryFromStep {
        return SelectQueryDsl(values.map { Literal(it) }, distinct = false)
    }

    @JvmName("select2")
    @SinceJdsl("3.0.0")
    fun select(vararg expressions: Expressionable<*>): SelectQueryFromStep {
        return SelectQueryDsl(expressions.map { it.toExpression() }, distinct = false)
    }

    @JvmName("select3")
    @SinceJdsl("3.0.0")
    fun select(values: Collection<Any?>): SelectQueryFromStep {
        return SelectQueryDsl(values.map { Literal(it) }, distinct = false)
    }

    @JvmName("select4")
    @SinceJdsl("3.0.0")
    fun select(expressions: Collection<Expressionable<*>>): SelectQueryFromStep {
        return SelectQueryDsl(expressions.map { it.toExpression() }, distinct = false)
    }

    @JvmName("selectDistinct1")
    @SinceJdsl("3.0.0")
    fun selectDistinct(vararg values: Any?): SelectQueryFromStep {
        return SelectQueryDsl(values.map { Literal(it) }, distinct = true)
    }

    @JvmName("selectDistinct2")
    @SinceJdsl("3.0.0")
    fun selectDistinct(vararg expressions: Expressionable<*>): SelectQueryFromStep {
        return SelectQueryDsl(expressions.map { it.toExpression() }, distinct = true)
    }

    @JvmName("selectDistinct3")
    @SinceJdsl("3.0.0")
    fun selectDistinct(expressions: Collection<Expressionable<*>>): SelectQueryFromStep {
        return SelectQueryDsl(expressions.map { it.toExpression() }, distinct = true)
    }

    @JvmName("selectDistinct4")
    @SinceJdsl("3.0.0")
    fun selectDistinct(values: Collection<Any?>): SelectQueryFromStep {
        return SelectQueryDsl(values.map { Literal(it) }, distinct = true)
    }
}
