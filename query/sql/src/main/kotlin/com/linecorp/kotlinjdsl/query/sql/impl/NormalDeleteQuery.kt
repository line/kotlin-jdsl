package com.linecorp.kotlinjdsl.query.sql.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.query.sql.DeleteQuery
import com.linecorp.kotlinjdsl.query.sql.Expression
import com.linecorp.kotlinjdsl.query.sql.Predicate
import com.linecorp.kotlinjdsl.query.sql.TableReference

@Internal
data class NormalDeleteQuery<T : Any>(
    val table: TableReference<T>,
    val where: Predicate?,
    val orderBy: Collection<Expression<*>>?,
) : DeleteQuery<T>
