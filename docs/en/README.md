---
description: 'Latest stable version: 3.0.0'
---

# Kotlin JDSL

## What is Kotlin JDSL?

Kotlin JDSL is a Kotlin library that makes it easy to build and execute queries without generated metamodel. Kotlin JDSL is not meant to replace your favorite library, but to help you build and execute queries with your favorite libraries.

With Kotlin JDSL, you can build queries using the Entity or Table class you wrote. Kotlin JDSL provides DSL for building queries over KClass and KProperty. This DSL allows you to build queries without a generated metamodel.

Kotlin JDSL provides extension points to other libraries, so you can call your favorite libraries with queries created by the DSL.&#x20;

## Supports

### JPQL

With Kotlin JDSL, you can easily build and execute JPQL queries.

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
