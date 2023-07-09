package com.linecorp.kotlinjdsl.dsl.jpql

import com.linecorp.kotlinjdsl.querymodel.jpql.JpqlQuery
import org.assertj.core.api.WithAssertions

abstract class AbstractJpqlDslTest : WithAssertions {
    inline fun <Q : JpqlQuery<Q>, T> testJpql(init: Jpql.() -> T): T {
        return Jpql().init()
    }
}
