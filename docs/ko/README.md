---
description: 'Latest stable version: 3.0.0'
---

# Kotlin JDSL

## Kotlin JDSL은?

Kotlin JDSL은 쿼리를 쉽게 만들고 실행시킬 수 있게 도와주는 Kotlin 라이브러리입니다. 어노테이션 프로세서 없이 본인이 작성한 클래스와 Kotlin에서 제공하는 함수들을 이용해서 쉽게 쿼리를 만들고 실행시킬 수 있습니다.&#x20;

## Features

### JPQL

Kotlin JDSL을 이용하여 JPQL 쿼리를 만들고 실행시킬 수 있습니다.

`jpql` DSL은 Kotlin JDSL이 제공하는 여러가지 DSL 함수들을 제공합니다. DSL 함수를 이용하여 JPQL 쿼리를 만들 수 있습니다.

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

만들어진 JPQL 쿼리는 Kotlin JDSL이 제공하는 Kotlin 확장함수를 이용하여 쉽게 실행시킬 수 있습니다.

```kotlin
val `the most prolific author` = entityManager.createQuery(query, context).apply {
    maxResults = 1
}
```

더 많은 정보를 원하신다면 이 문서를 봐주세요.
