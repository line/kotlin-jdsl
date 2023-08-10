package com.linecorp.kotlinjdsl.executor.spring.jpa.sql

import com.linecorp.kotlinjdsl.render.sql.SqlRenderer
import com.linecorp.kotlinjdsl.render.sql.setting.ParamType
import com.linecorp.kotlinjdsl.render.sql.setting.SqlRenderSetting

internal object SqlRendererHolder {
    private var renderer = SqlRenderer(
        setting = SqlRenderSetting(
            paramType = ParamType.INDEXED,
        ),
    )

    fun get(): SqlRenderer {
        return renderer
    }

    fun set(renderer: SqlRenderer) {
        SqlRendererHolder.renderer = renderer
    }
}
