package com.linecorp.kotlinjdsl.querymodel.jpql

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.querymodel.Query

/**
 * Marker interface to represent a JPQL query.
 */
@SinceJdsl("3.0.0")
interface JpqlQuery<SELF : JpqlQuery<SELF>> : JpqlQueryable<SELF>, Query<SELF>
