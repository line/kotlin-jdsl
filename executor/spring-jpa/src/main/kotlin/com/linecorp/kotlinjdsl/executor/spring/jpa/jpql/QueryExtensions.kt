package com.linecorp.kotlinjdsl.executor.spring.jpa.jpql

import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderedParams
import jakarta.persistence.Query

internal fun Query.setParams(params: JpqlRenderedParams) {
    params.forEach { (name, value) ->
        setParameter(name, value)
    }
}
