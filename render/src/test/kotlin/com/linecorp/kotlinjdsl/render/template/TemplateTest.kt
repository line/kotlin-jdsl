package com.linecorp.kotlinjdsl.render.template

import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

class TemplateTest : WithAssertions {
    @Test
    fun compile() {
        // when
        val actual = Template.compile("{0}, {1}, {2}, {3}")

        // then
        assertThat(actual.elements).containsExactly(
            TemplateElement.ArgumentNumber(0),
            TemplateElement.String(", "),
            TemplateElement.ArgumentNumber(1),
            TemplateElement.String(", "),
            TemplateElement.ArgumentNumber(2),
            TemplateElement.String(", "),
            TemplateElement.ArgumentNumber(3),
        )
    }

    @Test
    fun `compile() creates elements with the same arg number, when the template contains the same arg numbers`() {
        // when
        val actual = Template.compile("{0}, {1}, {0}, {1}")

        // then
        assertThat(actual.elements).containsExactly(
            TemplateElement.ArgumentNumber(0),
            TemplateElement.String(", "),
            TemplateElement.ArgumentNumber(1),
            TemplateElement.String(", "),
            TemplateElement.ArgumentNumber(0),
            TemplateElement.String(", "),
            TemplateElement.ArgumentNumber(1),
        )
    }

    @Test
    fun `compile() creates a prefix and postfix, when there is no arg number at the start and end of the template`() {
        // when
        val actual = Template.compile("START{0}, {1}END")

        // then
        assertThat(actual.elements).containsExactly(
            TemplateElement.String("START"),
            TemplateElement.ArgumentNumber(0),
            TemplateElement.String(", "),
            TemplateElement.ArgumentNumber(1),
            TemplateElement.String("END"),
        )
    }

    @Test
    fun `compile() creates only string, when there is no arg number`() {
        // when
        val actual = Template.compile("TEST")

        // then
        assertThat(actual.elements).containsExactly(
            TemplateElement.String("TEST"),
        )
    }
}
