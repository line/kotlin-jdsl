package org.springframework.data.support

import java.util.function.LongSupplier
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

internal object PageableExecutionUtilsAdaptor {
    fun <T> getPage(content: List<T>, pageable: Pageable, totalSupplier: LongSupplier): Page<T> {
        return PageableExecutionUtils.getPage(content, pageable, totalSupplier)
    }
}
