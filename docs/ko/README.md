---
description: 'Latest stable version: 3.4.1'
---

# Kotlin JDSL

## What is Kotlin JDSL?

Kotlin JDSL은 메타모델 없이 쿼리를 쉽게 만들 수 있게 도와주는 Kotlin 라이브러리입니다.
이미 Annotation Processor Tool (APT)을 이용해서 쿼리를 쉽게 만들도록 도와주는 라이브러리는 많이 있습니다.
하지만 APT를 이용하면 클래스명이나 필드명이 변경되었을 때마다 다시 컴파일 해야 하는 불편함이 있습니다.
Kotlin JDSL은 이런 불편함을 해소하면서 쉽게 쿼리를 만들 수 있도록 KClass와 KProperty 기반의 Domain-Specific Language를 제공합니다.

Kotlin JDSL은 새로운 DB 라이브러리가 아닌 DB 라이브러리를 사용할 때 도움을 주기 위해서 만들어진 라이브러리이기 때문에 직접 쿼리를 실행하거나 DB 라이브러리를 래핑하는 등의 작업은 하지 않습니다.

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

JPQL에 대해 더 궁금하시면 [여기](jpql-with-kotlin-jdsl/)를 참고해주세요.
