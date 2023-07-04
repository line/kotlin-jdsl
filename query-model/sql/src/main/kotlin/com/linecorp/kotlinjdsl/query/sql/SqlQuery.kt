package com.linecorp.kotlinjdsl.query.sql

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.query.Query

/**
 * Represents a **SQL** query.
 */
@SinceJdsl("3.0.0")
interface SqlQuery<SELF : SqlQuery<SELF>> : SqlQueryable<SELF>, Query<SELF>
