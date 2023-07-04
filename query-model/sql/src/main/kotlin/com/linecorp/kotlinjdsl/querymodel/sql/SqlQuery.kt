package com.linecorp.kotlinjdsl.querymodel.sql

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.querymodel.Query

/**
 * Represents a **SQL** query.
 */
@SinceJdsl("3.0.0")
interface SqlQuery<SELF : SqlQuery<SELF>> : SqlQueryable<SELF>, Query<SELF>
