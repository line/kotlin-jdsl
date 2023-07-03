package com.linecorp.kotlinjdsl.dsl.sql.update

import com.linecorp.kotlinjdsl.query.sql.SqlQueryable
import com.linecorp.kotlinjdsl.query.sql.UpdateQuery

interface UpdateQuerySetMoreStep<T : Any> : UpdateQuerySetStep<T>, SqlQueryable<UpdateQuery<T>>
