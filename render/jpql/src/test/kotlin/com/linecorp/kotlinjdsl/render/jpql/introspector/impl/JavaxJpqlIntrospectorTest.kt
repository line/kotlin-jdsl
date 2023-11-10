package com.linecorp.kotlinjdsl.render.jpql.introspector.impl

import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

class JavaxJpqlIntrospectorTest : WithAssertions {
    private val sut = JavaxJpqlIntrospector()

    companion object {
        private const val ENTITY_NAME_1 = "MyEntity1"
    }

    @Test
    fun `introspect() returns name of entity annotation, when entity annotation has name`() {
        // given
        val type = EntityClass1::class

        // when
        val actual = sut.introspect(type)

        // then
        assertThat(actual?.name).isEqualTo(ENTITY_NAME_1)
    }

    @Test
    fun `introspect() returns name of class, when entity annotation does not have name`() {
        // given
        val type = EntityClass2::class

        // when
        val actual = sut.introspect(type)

        // then
        assertThat(actual?.name).isEqualTo(EntityClass2::class.simpleName)
    }

    @Test
    fun `introspect() returns null, when there is no entity annotation`() {
        // given
        val type = NonEntityCLass1::class

        // when
        val actual = sut.introspect(type)

        // then
        assertThat(actual?.name).isNull()
    }

    @javax.persistence.Entity(name = ENTITY_NAME_1)
    private open class EntityClass1

    @javax.persistence.Entity
    private open class EntityClass2

    private open class NonEntityCLass1
}
