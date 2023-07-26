package kotlin.internal

/**
 * Specifies that the corresponding type should be ignored during type inference.
 */
@Target(AnnotationTarget.TYPE)
@Retention(AnnotationRetention.BINARY)
internal annotation class NoInfer

/**
 * Specifies that the constraint built for the type during type inference should be an equality one.
 */
@Target(AnnotationTarget.TYPE)
@Retention(AnnotationRetention.BINARY)
internal annotation class Exact

/**
 * Specifies that a corresponding member has the lowest priority in overload resolution.
 */
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY, AnnotationTarget.CONSTRUCTOR)
@Retention(AnnotationRetention.BINARY)
internal annotation class LowPriorityInOverloadResolution
