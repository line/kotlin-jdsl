package com.linecorp.kotlinjdsl.dsl.sql.insert

interface InsertQueryColumnValueStep<T : Any> : InsertQueryColumnStep<T>, InsertQueryValueStepN<T>
