package com.linecorp.kotlinjdsl.querymodel.sql.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.sql.Expression
import com.linecorp.kotlinjdsl.querymodel.sql.Predicate
import com.linecorp.kotlinjdsl.querymodel.sql.TableReference
import com.linecorp.kotlinjdsl.querymodel.sql.UpdateQuery

@Internal
data class NormalUpdateQuery<T : Any>(
    val table: TableReference<T>,
    val sets: Map<com.linecorp.kotlinjdsl.querymodel.sql.Column<T, *>, Expression<*>>,
    val where: Predicate?,
    val orderBy: Collection<Expression<*>>?,
) : UpdateQuery<T>
