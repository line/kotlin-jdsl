package com.linecorp.kotlinjdsl

object ClassUtils {
    fun isPresent(className: String, classLoader: ClassLoader? = null): Boolean {
        val result = runCatching {
            if (classLoader != null) {
                Class.forName(className, false, classLoader)
            } else {
                Class.forName(className)
            }
        }

        return result.map { true }.getOrElse { false }
    }
}
