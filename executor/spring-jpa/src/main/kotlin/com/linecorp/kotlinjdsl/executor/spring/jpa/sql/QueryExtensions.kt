package com.linecorp.kotlinjdsl.executor.spring.jpa.sql

import com.linecorp.kotlinjdsl.render.sql.SqlRenderedParameters
import jakarta.persistence.Query

internal fun Query.setParameters(parameters: SqlRenderedParameters.Indexed) {
    parameters.forEachIndexed { index, value ->
        setParameter(index, value)
    }
}

internal fun Query.setParameters(parameters: SqlRenderedParameters.Named) {
    parameters.forEach { (name, value) ->
        setParameter(name, value)
    }
}
