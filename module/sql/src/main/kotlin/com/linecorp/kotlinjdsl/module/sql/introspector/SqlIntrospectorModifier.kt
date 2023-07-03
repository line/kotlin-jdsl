package com.linecorp.kotlinjdsl.module.sql.introspector

import com.linecorp.kotlinjdsl.render.sql.introspector.SqlIntrospector

interface SqlIntrospectorModifier {
    fun modifyIntrospector(introspector: SqlIntrospector): SqlIntrospector
}
