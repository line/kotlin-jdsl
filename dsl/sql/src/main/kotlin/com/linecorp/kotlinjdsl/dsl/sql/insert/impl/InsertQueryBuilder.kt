package com.linecorp.kotlinjdsl.dsl.sql.insert.impl

import com.linecorp.kotlinjdsl.querymodel.sql.Expression
import com.linecorp.kotlinjdsl.querymodel.sql.InsertQuery
import com.linecorp.kotlinjdsl.querymodel.sql.Table
import com.linecorp.kotlinjdsl.querymodel.sql.TableReference
import com.linecorp.kotlinjdsl.querymodel.sql.impl.NormalInsertQuery

class InsertQueryBuilder<T : Any>(
    private val table: TableReference<T>,
) {
    private var columns: Collection<com.linecorp.kotlinjdsl.querymodel.sql.Column<T, *>>? = null
    private var values: MutableList<Collection<Expression<*>>>? = null
    private var select: Table<*>? = null

    fun columns(columns: Collection<com.linecorp.kotlinjdsl.querymodel.sql.Column<T, *>>): InsertQueryBuilder<T> {
        this.columns = columns

        return this
    }

    fun values(values: Collection<Expression<*>>): InsertQueryBuilder<T> {
        this.values = (this.values ?: mutableListOf()).also { it.add(values) }

        return this
    }

    fun select(select: Table<*>): InsertQueryBuilder<T> {
        this.select = select

        return this
    }

    fun build(): InsertQuery<T> {
        if (values != null && select != null) {
            throw IllegalStateException("VALUES and SELECT can't be used together within INSERT query.")
        }

        return NormalInsertQuery(
            table = table,
            columns = columns,
            values = values,
            select = select,
        )
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as InsertQueryBuilder<*>

        if (table != other.table) return false
        if (columns != other.columns) return false
        if (values != other.values) return false
        return select == other.select
    }

    override fun hashCode(): Int {
        var result = table.hashCode()
        result = 31 * result + (columns?.hashCode() ?: 0)
        result = 31 * result + (values?.hashCode() ?: 0)
        result = 31 * result + (select?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "InsertQueryBuilder(" +
            "table=$table, " +
            "columns=$columns, " +
            "values=$values, " +
            "select=$select)"
    }
}
