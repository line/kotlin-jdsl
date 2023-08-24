---
description: 'Latest stable version: 3.0.0'
---

# Kotlin JDSL

## What is Kotlin JDSL?

Kotlin JDSL은 메타모델 생성 없이 쿼리를 쉽게 만들 수 있게 도와주는 Kotlin 라이브러리입니다. Annotation Processor Tool (APT)을 이용하여 쿼리를 쉽게 만들 수 있게 도와주는
라이브러리는 많이 존재합니다. 하지만 APT를 사용하면 entity 혹은 table 클래스의 타입이나 필드명이 변경 되었을 때 다시 컴파일 해야 하는 불편함이 있습니다. Kotlin JDSL은 KClass와
KProperty 기반의 Domain-Specific Language (DSL)을 제공하여 APT 없이도 쉽게 쿼리를 만들 수 있게 도와줍니다.

Kotlin JDSL은 실행을 위한 구현체나 라이브러리의 래퍼 클래스를 제공하지 않습니다. 왜냐하면 Kotlin JDSL은 사용하고 있던 라이브러리 대체하기 보다 그대로 쓰면서 쿼리 만들 때 불편했던 부분을 도와주기
위해서 만들어졌기 때문입니다.

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

더 자세한 내용이 궁금하시면 [여기](jpql-with-kotlin-jdsl/)를 확인해주세요.
