package com.linecorp.kotlinjdsl.querymodel.jpql.from

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.querymodel.QueryPart

@SinceJdsl("3.0.0")
interface From : Fromable, QueryPart
