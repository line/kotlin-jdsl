package org.springframework.data.jpa.repository.support

internal class CrudMethodMetadataPostProcessorAdaptor {
    private val delegate = CrudMethodMetadataPostProcessor()

    fun getCrudMethodMetadata(): CrudMethodMetadata? = delegate.crudMethodMetadata
}
