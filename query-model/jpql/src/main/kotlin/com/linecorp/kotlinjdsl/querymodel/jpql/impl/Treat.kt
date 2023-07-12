package com.linecorp.kotlinjdsl.querymodel.jpql.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.Path
import kotlin.reflect.KClass

data class Treat<PARENT, CHILD>(
    val path: Path<PARENT>,
    override val type: KClass<CHILD & Any>,
) : Path<CHILD>
