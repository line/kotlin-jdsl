package com.linecorp.kotlinjdsl.querymodel.sql

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.querymodel.QueryPart

@SinceJdsl("3.0.0")
interface Table<T : Any> : QueryPart
