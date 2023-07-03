package com.linecorp.kotlinjdsl.executor.spring.jpa.sql

import com.linecorp.kotlinjdsl.render.sql.SqlRenderedParams
import jakarta.persistence.Query

internal fun Query.setParameters(params: SqlRenderedParams.Indexed) {
    params.forEachIndexed { index, value ->
        setParameter(index, value)
    }
}

internal fun Query.setParameters(params: SqlRenderedParams.Named) {
    params.forEach { (name, value) ->
        setParameter(name, value)
    }
}
