package com.linecorp.kotlinjdsl

/**
 * A comparator capable of sorting types based on their depth from the [target] type.
 *
 * It is designed for only compares depth to super classes, subclasses were not taken into consideration.
 */
@Internal
class SuperClassDepthComparator(
    private val target: Class<*>,
) : Comparator<Class<*>> {
    override fun compare(o1: Class<*>, o2: Class<*>): Int {
        val depth1 = getDepth(o1, target, 0)
        val depth2 = getDepth(o2, target, 0)

        return depth1 - depth2
    }

    private tailrec fun getDepth(declaredType: Class<*>, typeToMatch: Class<*>, depth: Int): Int {
        if (typeToMatch == declaredType) {
            return depth
        }

        // If we've gone as far as we can go and haven't found it...
        if (typeToMatch == Any::class.java) {
            return Int.MAX_VALUE
        }

        return getDepth(declaredType, typeToMatch.superclass, depth + 1)
    }
}
