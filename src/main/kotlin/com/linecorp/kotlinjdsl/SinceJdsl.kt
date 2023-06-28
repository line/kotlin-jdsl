package com.linecorp.kotlinjdsl

/**
 * Specifies the first version of JDSL where a declaration has appeared.
 *
 * @property version the version in the following formats: `<major>.<minor>` or `<major>.<minor>.<patch>`, where major, minor and patch
 * are non-negative integer numbers without leading zeros.
 */
@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.PROPERTY,
    AnnotationTarget.FIELD,
    AnnotationTarget.CONSTRUCTOR,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER,
    AnnotationTarget.TYPEALIAS,
)
@Retention(AnnotationRetention.BINARY)
@MustBeDocumented
annotation class SinceJdsl(val version: String)
