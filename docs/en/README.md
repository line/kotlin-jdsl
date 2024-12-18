---
description: 'Latest stable version: 3.5.4'
---

# Kotlin JDSL

## What is Kotlin JDSL?

Kotlin JDSL is a Kotlin library that makes it easy to build a query without a generated metamodel.
There are many libraries that use an annotation processing tool (APT) to do the job.
However, with the APT, you have to recompile whenever the name or type of field in an entity or a table class is changed.
Kotlin JDSL provides a domain-specific language (DSL) based on KClass and KProperty to help you easily build queries without such inconveniences from the APT.

Kotlin JDSL does not provide an executor or a wrapper class as it is designed to help you build and execute queries with the library you are using.

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

See [here](jpql-with-kotlin-jdsl/) for more information on JPQL.
