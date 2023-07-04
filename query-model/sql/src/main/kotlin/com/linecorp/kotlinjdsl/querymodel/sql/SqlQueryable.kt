package com.linecorp.kotlinjdsl.querymodel.sql

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.querymodel.Queryable

/**
 * A class that can create or represent a [SqlQuery].
 */
@SinceJdsl("3.0.0")
interface SqlQueryable<Q : SqlQuery<Q>> : Queryable<Q>
