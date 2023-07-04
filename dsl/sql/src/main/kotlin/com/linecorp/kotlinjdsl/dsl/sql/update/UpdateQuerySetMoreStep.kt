package com.linecorp.kotlinjdsl.dsl.sql.update

import com.linecorp.kotlinjdsl.querymodel.sql.SqlQueryable
import com.linecorp.kotlinjdsl.querymodel.sql.UpdateQuery

interface UpdateQuerySetMoreStep<T : Any> : UpdateQuerySetStep<T>, SqlQueryable<UpdateQuery<T>>
