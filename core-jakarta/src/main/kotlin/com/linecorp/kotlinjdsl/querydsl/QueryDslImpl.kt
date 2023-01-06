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
import com.linecorp.kotlinjdsl.query.spec.predicate.OrSpec
import com.linecorp.kotlinjdsl.query.spec.predicate.PredicateSpec
import com.linecorp.kotlinjdsl.querydsl.from.Relation
import com.linecorp.kotlinjdsl.querydsl.hint.SqlQueryHintClauseProvider
import jakarta.persistence.Query
import jakarta.persistence.TypedQuery
import jakarta.persistence.criteria.JoinType

/**
 * Internal DSL Implementation
 *
 * Don't use this directly because it's an **INTERNAL** class.
 * It does not support backward compatibility.
 * This class should be used with the understanding that it is not thread safe and therefore not suitable for parallel processing.
 */
open class QueryDslImpl<T>(
    private val returnType: Class<T>,
) : CriteriaQueryDsl<T>, SubqueryDsl<T>, CriteriaUpdateQueryDsl, CriteriaDeleteQueryDsl {
    private var singleSelectClause: SingleSelectClause<T>? = null
    private var multiSelectClause: MultiSelectClause<T>? = null
    private var fromClause: FromClause<*>? = null
    private var joins: MutableList<JoinSpec<*>>? = null
    private var wheres: MutableList<PredicateSpec>? = null
    private var groupBys: MutableList<ExpressionSpec<*>>? = null
    private var havings: MutableList<PredicateSpec>? = null
    private var orderBys: MutableList<OrderSpec>? = null
    private var offset: Int? = null
    private var maxResults: Int? = null
    private var sqlHints: MutableList<String>? = null
    private var jpaHints: MutableMap<String, Any>? = null
    private var params: MutableMap<ColumnSpec<*>, Any?>? = null

    private fun lazyJoins() = (joins ?: mutableListOf<JoinSpec<*>>().apply { joins = this })
    private fun lazyWheres() = (wheres ?: mutableListOf<PredicateSpec>().apply { wheres = this })
    private fun lazyGroupBys() = (groupBys ?: mutableListOf<ExpressionSpec<*>>().apply { groupBys = this })
    private fun lazyHavings() = (havings ?: mutableListOf<PredicateSpec>().apply { havings = this })
    private fun lazyOrderBys() = (orderBys ?: mutableListOf<OrderSpec>().apply { orderBys = this })
    private fun lazySqlHints() = (sqlHints ?: mutableListOf<String>().apply { sqlHints = this })
    private fun lazyJpaHints() = (jpaHints ?: mutableMapOf<String, Any>().apply { jpaHints = this })
    private fun lazyParams() = (params ?: mutableMapOf<ColumnSpec<*>, Any?>().apply { params = this })

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
        lazyJoins().add(SimpleJoinSpec(left = left, right = right, path = relation.path, joinType = joinType))
    }

    override fun <T> join(entity: EntitySpec<T>, predicate: PredicateSpec) {
        lazyJoins().add(CrossJoinSpec(entity))
        lazyWheres().add(predicate)
    }

    override fun <T, R> associate(
        left: EntitySpec<T>,
        right: EntitySpec<R>,
        relation: Relation<T, R?>,
        joinType: JoinType
    ) {
        lazyJoins().add(SimpleAssociatedJoinSpec(left = left, right = right, path = relation.path))
    }

    override fun <P, C : P> treat(
        root: ColumnSpec<*>,
        parent: EntitySpec<P>,
        child: EntitySpec<C>,
        parentJoinType: JoinType
    ) {
        lazyJoins().add(TreatJoinSpec(parent, child, parentJoinType, root))
    }

    override fun <T, R> fetch(
        left: EntitySpec<T>,
        right: EntitySpec<R>,
        relation: Relation<T, R?>,
        joinType: JoinType
    ) {
        lazyJoins().add(FetchJoinSpec(left = left, right = right, path = relation.path, joinType = joinType))
    }

    override fun where(predicate: PredicateSpec?) {
        predicate?.run { lazyWheres().add(this) }
    }

    override fun whereAnd(predicates: List<PredicateSpec?>) {
        predicates.filterNotNull()
            .takeIf { it.isNotEmpty() }
            ?.run { where(AndSpec(this)) }
    }

    override fun whereOr(predicates: List<PredicateSpec?>) {
        predicates.filterNotNull()
            .takeIf { it.isNotEmpty() }
            ?.run { where(OrSpec(this)) }
    }

    override fun groupBy(columns: List<ExpressionSpec<*>>) {
        lazyGroupBys().addAll(columns)
    }

    override fun having(predicate: PredicateSpec) {
        lazyHavings().add(predicate)
    }

    override fun orderBy(orders: List<OrderSpec>) {
        lazyOrderBys().addAll(orders)
    }

    override fun offset(offset: Int) {
        this.offset = offset
    }

    override fun maxResults(maxResults: Int) {
        this.maxResults = maxResults
    }

    override fun sqlHints(hints: List<String>) {
        lazySqlHints().addAll(hints)
    }

    override fun hints(hints: Map<String, Any>) {
        lazyJpaHints().putAll(hints)
    }

    override fun setParams(params: Map<ColumnSpec<*>, Any?>) {
        lazyParams().putAll(params)
    }

    override fun set(column: ColumnSpec<*>, value: Any?) {
        lazyParams()[column] = value
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
}
