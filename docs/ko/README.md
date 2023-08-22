---
description: 'Latest stable version: 3.0.0'
---

# Kotlin JDSL

## What is Kotlin JDSL?

Kotlin JDSL은 메타모델 생성 없이 쿼리를 쉽게 만들고 실행시킬 수 있는 Kotlin 라이브러리입니다. Kotlin JDSL은 사용자가 지금까지 사용하고 있던 라이브러리를 교체하기 보다는 그 라이브러리
사용하는데 도움을 주기 위해 만들어졌습니다.

Kotlin JDSL을 이용하면 Entity 혹은 Table 클래스를 통해 쿼리를 만들 수 있습니다. Kotlin JDSL은 KClass와 KProperty 기반의 DSL을 제공하기 때문에 이를 이용하면 메타모델을
생성하지 않고도 쿼리를 만들 수 있습니다.

Kotlin JDSL은 또한 DSL로 만든 쿼리를 사용자가 사용하던 라이브러리를 통해 실행시킬 수 있도록 다른 라이브러리들의 확장 포인트를 제공합니다.

## Supports

### JPQL

Kotlin JDSL을 이용하면 JPQL 쿼리를 쉽게 만들고 실행시킬 수 있습니다.

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

더 자세한 내용이 궁금하시면 [여기](jpql-with-kotlin-jdsl/)를 참고해주세요.
