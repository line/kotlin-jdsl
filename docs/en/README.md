---
description: 'Latest stable version: 3.0.0'
---

# Kotlin JDSL

## What is Kotlin JDSL?

Kotlin JDSL is a Kotlin library that makes it easy to build and execute a query without a generated metamodel.

Kotlin JDSL does not provide an executor or wrapper class, because it is designed to help you build and execute the
query with the library you are using, not to replace it.

Kotlin JDSL provides two things: DSL and extension functions. The DSL is based on KClass and KProperty, so it allows you
to build the query using your entity and table classes. The extension functions allow you to use the library to execute
the query created by the DSL.

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
