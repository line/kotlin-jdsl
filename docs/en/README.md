---
description: 'Latest stable version: 3.0.2'
---

# Kotlin JDSL

## What is Kotlin JDSL?

Kotlin JDSL is a Kotlin library that makes it easy to build a query without a generated metamodel.
There are libraries that make it easy to build the query using the Annotation Processor Tool (APT).
However, with the APT, you have to recompile if the name or type of a field in an entity or table class changes.
Kotlin JDSL provides the Domain-Specific Language (DSL) based on KClass and KProperty to help you easily build the query without the APT.

Kotlin JDSL does not provide an executor or wrapper class.
Because it is designed to help you build and execute the query with the library you are using, not to replace it.

## Supports

### JPQL

```kotlin
val context = JpqlRenderContext()

val query = jpql {
    select(
        path(Author::authorId),
    ).from(
        entity(Author::class),
        join(BookAuthor::class).on(path(Author::authorId).equal(path(BookAuthor::authorId))),
    ).groupBy(
        path(Author::authorId),
    ).orderBy(
        count(Author::authorId).desc(),
    )
}

val `the most prolific author` = entityManager.createQuery(query, context).apply {
    maxResults = 1
}
```

See [more](jpql-with-kotlin-jdsl/) for more details.
