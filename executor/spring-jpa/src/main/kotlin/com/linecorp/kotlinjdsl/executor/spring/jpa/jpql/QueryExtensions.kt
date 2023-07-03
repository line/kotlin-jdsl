package com.linecorp.kotlinjdsl.executor.spring.jpa.jpql

import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderedParams
import jakarta.persistence.Query

internal fun Query.setParameters(params: JpqlRenderedParams) {
    params.forEach { (name, value) ->
        setParameter(name, value)
    }
}
