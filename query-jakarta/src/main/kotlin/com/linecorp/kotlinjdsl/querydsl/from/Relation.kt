package com.linecorp.kotlinjdsl.querydsl.from

data class Relation<T, out R>(
    val path: String
)
