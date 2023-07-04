package com.linecorp.kotlinjdsl.dsl.sql.expression

import com.linecorp.kotlinjdsl.querymodel.sql.Expressionable

interface CaseWhenMoreStep<T> : CaseWhenStep<T>, CaseElseStep<T>, Expressionable<T>
