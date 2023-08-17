---
description: 'Latest stable version: 3.0.0'
---

# Kotlin JDSL

## What is Kotlin JDSL?

Kotlin JDSL is a Kotlin library that makes query building and execution easy. You can build queries using your own classes and Kotlin's built-in functions without an annotation processor, and easily execute them.

## Features

### JPQL

You can build and execute the JPQL query using the Kotlin JDSL.

```kotlin
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
```

And you can easily execute the query that was created by Kotlin JDSL using the extension functions.

```kotlin
val `the most prolific author` = entityManager.createQuery(query, context).apply {
    maxResults = 1
}
```

See [more](jpql-with-kotlin-jdsl/) for more details.
