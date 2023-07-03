package com.linecorp.kotlinjdsl.dsl.sql

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.query.sql.SqlQuery
import com.linecorp.kotlinjdsl.query.sql.SqlQueryable

@SinceJdsl("3.0.0")
inline fun <Q : SqlQuery<Q>> sql(init: Normal.() -> SqlQueryable<Q>): Q {
    return Normal().init().toQuery()
}

@SinceJdsl("3.0.0")
inline fun <DSL : SqlDsl, Q : SqlQuery<Q>> sql(dsl: SqlDsl.Constructor<DSL>, init: DSL.() -> SqlQueryable<Q>): Q {
    return dsl.newInstance().init().toQuery()
}
