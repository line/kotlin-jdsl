package com.linecorp.kotlinjdsl.querydsl

import com.linecorp.kotlinjdsl.query.CriteriaDeleteQuerySpec
import com.linecorp.kotlinjdsl.query.CriteriaQuerySpec
import com.linecorp.kotlinjdsl.query.CriteriaUpdateQuerySpec
import com.linecorp.kotlinjdsl.query.SubquerySpec
import com.linecorp.kotlinjdsl.query.clause.from.FromClause
import com.linecorp.kotlinjdsl.query.clause.from.JoinClause
import com.linecorp.kotlinjdsl.query.clause.from.SimpleAssociatedJoinClause
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
import javax.persistence.Query
import javax.persistence.TypedQuery
import javax.persistence.criteria.JoinType

/**
 * Internal DSL Implementation
 *
 * Don't use this directly because it's an <string>INTERNAL</strong> class.
 * It does not support backward compatibility.
 * This class should be used with the understanding that it is not thread safe and therefore not suitable for parallel processing.
 */
open class QueryDslImpl<T>(
    private val returnType: Class<T>,
) : CriteriaQueryDsl<T>, SubqueryDsl<T>, CriteriaUpdateQueryDsl, CriteriaDeleteQueryDsl {
    private var singleSelectClause: SingleSelectClause<T>? = null
    private var multiSelectClause: MultiSelectClause<T>? = null
    private var fromClause: FromClause<*>? = null
    private var joins: Lazy<MutableList<JoinSpec<*>>> = lazy(LazyThreadSafetyMode.NONE) { mutableListOf() }
    private var wheres: Lazy<MutableList<PredicateSpec>> = lazy(LazyThreadSafetyMode.NONE) { mutableListOf() }
    private var groupBys: Lazy<MutableList<ExpressionSpec<*>>> = lazy(LazyThreadSafetyMode.NONE) { mutableListOf() }
    private var havings: Lazy<MutableList<PredicateSpec>> = lazy(LazyThreadSafetyMode.NONE) { mutableListOf() }
    private var orderBys: Lazy<MutableList<OrderSpec>> = lazy(LazyThreadSafetyMode.NONE) { mutableListOf() }
    private var offset: Int? = null
    private var maxResults: Int? = null
    private var sqlHints: Lazy<MutableList<String>> = lazy(LazyThreadSafetyMode.NONE) { mutableListOf() }
    private var jpaHints: Lazy<MutableMap<String, Any>> = lazy(LazyThreadSafetyMode.NONE) { mutableMapOf() }
    private var params: Lazy<MutableMap<ColumnSpec<*>, Any?>> = lazy(LazyThreadSafetyMode.NONE) { mutableMapOf() }

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
        joins.value.add(SimpleJoinSpec(left = left, right = right, path = relation.path, joinType = joinType))
    }

    override fun <T> join(entity: EntitySpec<T>, predicate: PredicateSpec) {
        joins.value.add(CrossJoinSpec(entity))
        wheres.value.add(predicate)
    }

    override fun <T, R> associate(
        left: EntitySpec<T>,
        right: EntitySpec<R>,
        relation: Relation<T, R?>,
        joinType: JoinType
    ) {
        joins.value.add(SimpleAssociatedJoinSpec(left = left, right = right, path = relation.path))
    }

    override fun <T, R> fetch(
        left: EntitySpec<T>,
        right: EntitySpec<R>,
        relation: Relation<T, R?>,
        joinType: JoinType
    ) {
        joins.value.add(FetchJoinSpec(left = left, right = right, path = relation.path, joinType = joinType))
    }

    override fun where(predicate: PredicateSpec) {
        wheres.value.add(predicate)
    }

    override fun groupBy(columns: List<ExpressionSpec<*>>) {
        groupBys.value.addAll(columns)
    }

    override fun having(predicate: PredicateSpec) {
        havings.value.add(predicate)
    }

    override fun orderBy(orders: List<OrderSpec>) {
        orderBys.value.addAll(orders)
    }

    override fun offset(offset: Int) {
        this.offset = offset
    }

    override fun maxResults(maxResults: Int) {
        this.maxResults = maxResults
    }

    override fun sqlHints(hints: List<String>) {
        sqlHints.value.addAll(hints)
    }

    override fun hints(hints: Map<String, Any>) {
        jpaHints.value.putAll(hints)
    }

    override fun setParams(params: Map<ColumnSpec<*>, Any?>) {
        this.params.value.putAll(params)
    }

    override fun set(column: ColumnSpec<*>, value: Any?) {
        params.value[column] = value
    }

    fun createCriteriaQuerySpec(): CriteriaQuerySpec<T, TypedQuery<T>> {
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

    @Suppress("UNCHECKED_CAST")
    fun createCriteriaUpdateQuerySpec(): CriteriaUpdateQuerySpec<T, Query> {
        return CriteriaUpdateQuerySpecImpl(
            targetEntity = returnType,
            from = getFromClause() as FromClause<T>,
            associate = getSimpleAssociatedJoinClauseOnly(),
            where = getWhereClause(),
            sqlHint = getSqlQueryHintClause(),
            jpaHint = getJpaQueryHintClause(),
            set = getSetClause()
        )
    }

    @Suppress("MemberVisibilityCanBePrivate")
    protected fun getSetClause() = SetClause(params.orEmpty())

    @Suppress("UNCHECKED_CAST")
    fun createCriteriaDeleteQuerySpec(): CriteriaDeleteQuerySpec<T, Query> {
        return CriteriaDeleteQuerySpecImpl(
            targetEntity = returnType,
            from = getFromClause() as FromClause<T>,
            associate = getSimpleAssociatedJoinClauseOnly(),
            where = getWhereClause(),
            sqlHint = getSqlQueryHintClause(),
            jpaHint = getJpaQueryHintClause()
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

    protected fun getFromClause(): FromClause<*> {
        mustBe(fromClause != null) { "There is no from clause in query" }

        return fromClause!!
    }

    @Suppress("MemberVisibilityCanBePrivate")
    protected fun getJoinClause(): JoinClause {
        return JoinClause(joins.orEmpty())
    }

    @Suppress("MemberVisibilityCanBePrivate")
    protected fun getJoinClauseDoesNotHaveFetch(): JoinClause {
        mustBe(joins.orEmpty().filterIsInstance<FetchJoinSpec<*, *>>().isEmpty()) { "This query does not support fetch" }

        return getJoinClause()
    }

    @Suppress("MemberVisibilityCanBePrivate")
    protected fun getSimpleAssociatedJoinClauseOnly(): SimpleAssociatedJoinClause {
        val joins = joins.orEmpty()
        return joins.filterIsInstance<SimpleAssociatedJoinSpec<*, *>>().let {
            mustBe(it.size == joins.size) { "This query only support associate" }
            SimpleAssociatedJoinClause(it)
        }
    }

    protected fun getWhereClause(): WhereClause {
        return WhereClause(wheres.orEmpty().merge())
    }

    @Suppress("MemberVisibilityCanBePrivate")
    protected fun getGroupByClause(): GroupByClause {
        return GroupByClause(groupBys.orEmpty())
    }

    protected fun getEmptyGroupByClause(): GroupByClause {
        return GroupByClause(emptyList())
    }

    @Suppress("MemberVisibilityCanBePrivate")
    protected fun getHavingClause(): HavingClause {
        return HavingClause(havings.orEmpty().merge())
    }

    protected fun getEmptyHavingClause(): HavingClause {
        return HavingClause(PredicateSpec.empty)
    }

    @Suppress("MemberVisibilityCanBePrivate")
    protected fun getOrderByClause(): CriteriaQueryOrderByClause {
        return OrderByClause(orderBys.orEmpty())
    }

    protected fun getEmptyOrderByClause(): CriteriaQueryOrderByClause {
        return OrderByClause.empty
    }

    @Suppress("MemberVisibilityCanBePrivate")
    protected fun <Q : Query> getLimitClause(): QueryLimitClause<Q> {
        return LimitClause(offset, maxResults)
    }

    protected fun <Q : Query> getEmptyLimitClause(): QueryLimitClause<Q> {
        return LimitClause.empty()
    }

    protected fun <Q : Query> getJpaQueryHintClause(): JpaQueryHintClause<Q> {
        return JpaQueryHintClauseImpl(jpaHints.orEmpty())
    }

    @Suppress("UNCHECKED_CAST")
    protected fun <Q : Query> getSqlQueryHintClause(): SqlQueryHintClause<Q> {
        return SqlQueryHintClauseProvider.provide(sqlHints.orEmpty()) as SqlQueryHintClause<Q>
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
        override val from: FromClause<*>,
        override val join: JoinClause,
        override val where: CriteriaQueryWhereClause,
        override val groupBy: CriteriaQueryGroupByClause,
        override val having: CriteriaQueryHavingClause,
        override val orderBy: CriteriaQueryOrderByClause,
        override val limit: QueryLimitClause<TypedQuery<T>>,
        override val jpaHint: JpaQueryHintClause<TypedQuery<T>>,
        override val sqlHint: SqlQueryHintClause<TypedQuery<T>>,
    ) : CriteriaQuerySpec<T, TypedQuery<T>>

    data class CriteriaUpdateQuerySpecImpl<T, Q>(
        override val targetEntity: Class<T>,
        override val from: FromClause<T>,
        override val associate: SimpleAssociatedJoinClause,
        override val where: CriteriaQueryWhereClause,
        override val jpaHint: JpaQueryHintClause<Q>,
        override val sqlHint: SqlQueryHintClause<Q>,
        override val set: SetClause
    ) : CriteriaUpdateQuerySpec<T, Q>

    data class CriteriaDeleteQuerySpecImpl<T, Q>(
        override val targetEntity: Class<T>,
        override val from: FromClause<T>,
        override val associate: SimpleAssociatedJoinClause,
        override val where: CriteriaQueryWhereClause,
        override val jpaHint: JpaQueryHintClause<Q>,
        override val sqlHint: SqlQueryHintClause<Q>
    ) : CriteriaDeleteQuerySpec<T, Q>

    data class SubquerySpecImpl<T>(
        override val select: SubquerySelectClause<T>,
        override val from: FromClause<*>,
        override val join: JoinClause,
        override val where: SubqueryWhereClause,
        override val groupBy: SubqueryGroupByClause,
        override val having: SubqueryHavingClause
    ) : SubquerySpec<T>

    private fun <T> Lazy<List<T>>.orEmpty() = if (isInitialized()) value else emptyList()
    private fun <K, V> Lazy<Map<K, V>>.orEmpty() = if (isInitialized()) value else emptyMap()
}
