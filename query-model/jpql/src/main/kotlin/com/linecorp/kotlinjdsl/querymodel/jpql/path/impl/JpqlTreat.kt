package com.linecorp.kotlinjdsl.querymodel.jpql.path.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Path
import kotlin.reflect.KClass

@Internal
data class JpqlTreat<PARENT, CHILD> internal constructor(
    val path: Path<PARENT>,
    override val type: KClass<CHILD & Any>,
) : Path<CHILD>
