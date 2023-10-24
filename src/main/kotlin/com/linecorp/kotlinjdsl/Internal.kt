package com.linecorp.kotlinjdsl

/**
 * Marks declarations that are **internal** in JDSL API, which means that should not be used outside of
 * `com.linecorp.kotlinjdsl`, because their signatures and semantics will change between future releases without any
 * warnings and without providing any migration aids.
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
annotation class Internal
