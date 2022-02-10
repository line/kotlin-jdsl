package com.linecorp.kotlinjdsl.querydsl

import com.linecorp.kotlinjdsl.query.CriteriaQuerySpec
import com.linecorp.kotlinjdsl.query.CriteriaUpdateQuerySpec
import com.linecorp.kotlinjdsl.query.SubquerySpec
import com.linecorp.kotlinjdsl.query.clause.from.FromClause
import com.linecorp.kotlinjdsl.query.clause.from.JoinClause
import com.linecorp.kotlinjdsl.query.clause.groupby.CriteriaQueryGroupByClause
import com.linecorp.kotlinjdsl.query.clause.groupby.GroupByClause
import com.linecorp.kotlinjdsl.query.clause.groupby.SubqueryGroupByClause
import com.linecorp.kotlinjdsl.query.clause.having.CriteriaQueryHavingClause
import com.linecorp.kotlinjdsl.query.clause.having.HavingClause
import com.linecorp.kotlinjdsl.query.clause.having.SubqueryHavingClause
import com.linecorp.kotlinjdsl.query.clause.hint.JpaQueryHintClause
import com.linecorp.kotlinjdsl.query.clause.hint.JpaQueryHintClauseImpl
import com.linecorp.kotlinjdsl.query.clause.hint.SqlQueryHintClause
import com.linecorp.kotlinjdsl.query.clause.limit.LimitClause
import com.linecorp.kotlinjdsl.query.clause.limit.QueryLimitClause
import com.linecorp.kotlinjdsl.query.clause.orderby.CriteriaQueryOrderByClause
import com.linecorp.kotlinjdsl.query.clause.orderby.OrderByClause
import com.linecorp.kotlinjdsl.query.clause.select.CriteriaQuerySelectClause
import com.linecorp.kotlinjdsl.query.clause.select.MultiSelectClause
import com.linecorp.kotlinjdsl.query.clause.select.SingleSelectClause
import com.linecorp.kotlinjdsl.query.clause.select.SubquerySelectClause
import com.linecorp.kotlinjdsl.query.clause.set.SetClause
import com.linecorp.kotlinjdsl.query.clause.where.CriteriaQueryWhereClause
import com.linecorp.kotlinjdsl.query.clause.where.SubqueryWhereClause
import com.linecorp.kotlinjdsl.query.clause.where.WhereClause
import com.linecorp.kotlinjdsl.query.spec.*
import com.linecorp.kotlinjdsl.query.spec.expression.ColumnSpec
import com.linecorp.kotlinjdsl.query.spec.expression.EntitySpec
import com.linecorp.kotlinjdsl.query.spec.expression.ExpressionSpec
import com.linecorp.kotlinjdsl.query.spec.predicate.AndSpec
import com.linecorp.kotlinjdsl.query.spec.predicate.PredicateSpec
import com.linecorp.kotlinjdsl.querydsl.from.Relation
import com.linecorp.kotlinjdsl.querydsl.hint.SqlQueryHintClauseProvider
import javax.persistence.criteria.JoinType

/**
 * Internal DSL Implementation
 *
 * Don't use this directly because it's an <string>INTERNAL</strong> class.
 * It does not support backward compatibility.
 */
open class QueryDslImpl<T>(
    private val returnType: Class<T>,
) : CriteriaQueryDsl<T>, SubqueryDsl<T>, CriteriaUpdateQueryDsl {
    private var singleSelectClause: SingleSelectClause<T>? = null
    private var multiSelectClause: MultiSelectClause<T>? = null
    private var fromClause: FromClause? = null
    private var joins: MutableList<JoinSpec<*>> = mutableListOf()
    private var wheres: MutableList<PredicateSpec> = mutableListOf()
    private var groupBys: MutableList<ExpressionSpec<*>> = mutableListOf()
    private var havings: MutableList<PredicateSpec> = mutableListOf()
    private var orderBys: MutableList<OrderSpec> = mutableListOf()
    private var offset: Int? = null
    private var maxResults: Int? = null
    private var sqlHints: MutableList<String> = mutableListOf()
    private var jpaHints: MutableMap<String, Any> = mutableMapOf()
    private var params: MutableMap<ColumnSpec<*>, Any?> = mutableMapOf()

    override fun select(distinct: Boolean, expression: ExpressionSpec<T>): SingleSelectClause<T> {
        return SingleSelectClause(
            returnType = returnType,
            distinct = distinct,
            expression = expression,
        ).also { singleSelectClause = it }
    }

    override fun select(distinct: Boolean, expressions: List<ExpressionSpec<*>>): MultiSelectClause<T> {
        return MultiSelectClause(
            returnType = returnType,
            distinct = distinct,
            expressions = expressions,
        ).also { multiSelectClause = it }
    }

    override fun from(entity: EntitySpec<*>) {
        fromClause = FromClause(entity)
    }

    override fun <T, R> join(left: EntitySpec<T>, right: EntitySpec<R>, relation: Relation<T, R?>, joinType: JoinType) {
        joins.add(SimpleJoinSpec(left = left, right = right, path = relation.path, joinType = joinType))
    }

    override fun <T> join(entity: EntitySpec<T>, predicate: PredicateSpec) {
        joins.add(CrossJoinSpec(entity))
        wheres.add(predicate)
    }

    override fun <T, R> associate(
        left: EntitySpec<T>,
        right: EntitySpec<R>,
        relation: Relation<T, R?>,
        joinType: JoinType
    ) {
        joins.add(SimpleAssociatedJoinSpec(left = left, right = right, path = relation.path))
    }

    override fun <T, R> fetch(
        left: EntitySpec<T>,
        right: EntitySpec<R>,
        relation: Relation<T, R?>,
        joinType: JoinType
    ) {
        joins.add(FetchJoinSpec(left = left, right = right, path = relation.path, joinType = joinType))
    }

    override fun where(predicate: PredicateSpec) {
        wheres.add(predicate)
    }

    override fun groupBy(columns: List<ExpressionSpec<*>>) {
        groupBys.addAll(columns)
    }

    override fun having(predicate: PredicateSpec) {
        havings.add(predicate)
    }

    override fun orderBy(orders: List<OrderSpec>) {
        orderBys.addAll(orders)
    }

    override fun offset(offset: Int) {
        this.offset = offset
    }

    override fun maxResults(maxResults: Int) {
        this.maxResults = maxResults
    }

    override fun sqlHints(hints: List<String>) {
        sqlHints.addAll(hints)
    }

    override fun hints(hints: Map<String, Any>) {
        jpaHints.putAll(hints)
    }

    override fun setParams(params: Map<ColumnSpec<*>, Any?>) {
        this.params.putAll(params)
    }

    override fun set(column: ColumnSpec<*>, value: Any?) {
        params[column] = value
    }

    fun createCriteriaQuerySpec(): CriteriaQuerySpec<T> {
        return CriteriaQuerySpecImpl(
            select = getCriteriaQuerySelectClause(),
            from = getFromClause(),
            join = getJoinClause(),
            where = getWhereClause(),
            groupBy = getGroupByClause(),
            having = getHavingClause(),
            orderBy = getOrderByClause(),
            limit = getLimitClause(),
            sqlHint = getSqlQueryHintClause(),
            jpaHint = getJpaQueryHintClause(),
        )
    }

    fun createCriteriaUpdateQuerySpec(): CriteriaUpdateQuerySpec<T> {
        return CriteriaUpdateQuerySpecImpl(
            targetEntity = returnType,
            from = getFromClause(),
            join = getJoinClauseDoesNotHaveFetch(),
            where = getWhereClause(),
            sqlHint = getSqlQueryHintClause(),
            jpaHint = getJpaQueryHintClause(),
            set = SetClause(params)
        )
    }

    fun createSubquerySpec(): SubquerySpec<T> {
        return SubquerySpecImpl(
            select = getSubquerySelectClause(),
            from = getFromClause(),
            join = getJoinClauseDoesNotHaveFetch(),
            where = getWhereClause(),
            groupBy = getGroupByClause(),
            having = getHavingClause(),
        )
    }

    protected fun getCriteriaQuerySelectClause(): CriteriaQuerySelectClause<T> {
        mustBe(singleSelectClause != null || multiSelectClause != null) {
            "There is no select clause in query"
        }

        return singleSelectClause ?: multiSelectClause!!
    }

    @Suppress("MemberVisibilityCanBePrivate")
    protected fun getSubquerySelectClause(): SubquerySelectClause<T> {
        mustBe(singleSelectClause != null) { "There is no select clause in query" }

        return singleSelectClause!!
    }

    protected fun getFromClause(): FromClause {
        mustBe(fromClause != null) { "There is no from clause in query" }

        return fromClause!!
    }

    @Suppress("MemberVisibilityCanBePrivate")
    protected fun getJoinClause(): JoinClause {
        return JoinClause(joins)
    }

    @Suppress("MemberVisibilityCanBePrivate")
    protected fun getJoinClauseDoesNotHaveFetch(): JoinClause {
        mustBe(joins.filterIsInstance<FetchJoinSpec<*, *>>().isEmpty()) { "This query does not support fetch" }

        return getJoinClause()
    }

    protected fun getWhereClause(): WhereClause {
        return WhereClause(wheres.merge())
    }

    @Suppress("MemberVisibilityCanBePrivate")
    protected fun getGroupByClause(): GroupByClause {
        return GroupByClause(groupBys)
    }

    protected fun getEmptyGroupByClause(): GroupByClause {
        return GroupByClause(emptyList())
    }

    @Suppress("MemberVisibilityCanBePrivate")
    protected fun getHavingClause(): HavingClause {
        return HavingClause(havings.merge())
    }

    protected fun getEmptyHavingClause(): HavingClause {
        return HavingClause(PredicateSpec.empty)
    }

    @Suppress("MemberVisibilityCanBePrivate")
    protected fun getOrderByClause(): CriteriaQueryOrderByClause {
        return OrderByClause(orderBys)
    }

    protected fun getEmptyOrderByClause(): CriteriaQueryOrderByClause {
        return OrderByClause.empty
    }

    @Suppress("MemberVisibilityCanBePrivate")
    protected fun getLimitClause(): QueryLimitClause {
        return LimitClause(offset, maxResults)
    }

    protected fun getEmptyLimitClause(): QueryLimitClause {
        return LimitClause.empty
    }

    protected fun getJpaQueryHintClause(): JpaQueryHintClause {
        return JpaQueryHintClauseImpl(jpaHints)
    }

    protected fun getSqlQueryHintClause(): SqlQueryHintClause {
        return SqlQueryHintClauseProvider.provide(sqlHints)
    }

    @Suppress("MemberVisibilityCanBePrivate")
    protected fun List<PredicateSpec>.merge(): PredicateSpec {
        return when {
            isEmpty() -> PredicateSpec.empty
            size == 1 -> first()
            else -> AndSpec(this)
        }
    }

    @Suppress("MemberVisibilityCanBePrivate")
    protected fun mustBe(condition: Boolean, lazyMessage: () -> String) {
        if (!condition) {
            throw IllegalStateException(lazyMessage())
        }
    }

    data class CriteriaQuerySpecImpl<T>(
        override val select: CriteriaQuerySelectClause<T>,
        override val from: FromClause,
        override val join: JoinClause,
        override val where: CriteriaQueryWhereClause,
        override val groupBy: CriteriaQueryGroupByClause,
        override val having: CriteriaQueryHavingClause,
        override val orderBy: CriteriaQueryOrderByClause,
        override val limit: QueryLimitClause,
        override val jpaHint: JpaQueryHintClause,
        override val sqlHint: SqlQueryHintClause,
    ) : CriteriaQuerySpec<T>

    data class CriteriaUpdateQuerySpecImpl<T>(
        override val targetEntity: Class<T>,
        override val from: FromClause,
        override val join: JoinClause,
        override val where: CriteriaQueryWhereClause,
        override val jpaHint: JpaQueryHintClause,
        override val sqlHint: SqlQueryHintClause,
        override val set: SetClause
    ) : CriteriaUpdateQuerySpec<T>


    data class SubquerySpecImpl<T>(
        override val select: SubquerySelectClause<T>,
        override val from: FromClause,
        override val join: JoinClause,
        override val where: SubqueryWhereClause,
        override val groupBy: SubqueryGroupByClause,
        override val having: SubqueryHavingClause
    ) : SubquerySpec<T> {
        constructor(spec: SubquerySpec<T>) : this(
            select = spec.select,
            from = spec.from,
            join = spec.join,
            where = spec.where,
            groupBy = spec.groupBy,
            having = spec.having,
        )
    }
}
