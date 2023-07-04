package com.linecorp.kotlinjdsl.querymodel.jpql

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.querymodel.Queryable

/**
 * A class that can create or represent a [JpqlQuery].
 */
@SinceJdsl("3.0.0")
interface JpqlQueryable<Q : JpqlQuery<Q>> : Queryable<Q>
