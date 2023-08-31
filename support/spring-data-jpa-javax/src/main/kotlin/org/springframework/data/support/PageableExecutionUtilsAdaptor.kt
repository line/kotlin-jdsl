package org.springframework.data.support

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.function.LongSupplier

internal object PageableExecutionUtilsAdaptor {
    fun <T> getPage(content: List<T>, pageable: Pageable, totalSupplier: LongSupplier): Page<T> {
        return PageableExecutionUtils.getPage(content, pageable, totalSupplier)
    }
}
