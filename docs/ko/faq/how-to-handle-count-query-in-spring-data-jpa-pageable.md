# Spring Data JPA Pageable에서 count 쿼리는 어떻게 처리하나요?

Spring Data JPA의 `Pageable`과 함께 `KotlinJdslJpqlExecutor`의 `findPage`를 사용할 때, 특히 `join` 및 `groupBy`를 포함하는 복잡한 쿼리에서 자동 생성된 count 쿼리에 문제가 발생할 수 있습니다.

기본적으로 Spring Data JPA는 주 쿼리에서 count 쿼리를 생성하려고 시도합니다. 그러나 이 생성된 쿼리는 복잡한 시나리오에서는 종종 올바르지 않습니다.

이 문제를 해결하기 위해 별도의 count 쿼리를 제공할 수 있습니다. `KotlinJdslJpqlExecutor`에는 별도의 count 쿼리 람다를 허용하는 `findPage` 메서드가 있습니다.

다음은 예시입니다.

```kotlin
interface BookRepository : JpaRepository<Book, Isbn>, KotlinJdslJpqlExecutor

val page: Page<Book> = bookRepository.findPage(pageable,
    { // 쿼리
        select(
            entity(Book::class)
        ).from(
            entity(Book::class),
            join(Book::author)
        ).where(
            // ... 일부 조건
        ).groupBy(
            path(Book::isbn)
        )
    },
    { // Count 쿼리
        select(
            countDistinct(path(Book::isbn))
        ).from(
            entity(Book::class),
            join(Book::author)
        ).where(
            // ... 동일한 조건
        )
    }
)
```

별도의 간단한 count 쿼리를 제공함으로써 자동 생성된 쿼리의 문제를 피할 수 있습니다. count 쿼리는 단일 `Long` 값을 반환해야 합니다. 정확한 총 개수를 얻으려면 주 쿼리에 적용하는 것과 동일한 `where` 조건을 count 쿼리에도 적용하는 것이 중요합니다.

주 쿼리에 `groupBy` 절이 있는 경우, `countDistinct(path(Book::isbn))` 예제에서 볼 수 있듯이 count 쿼리는 일반적으로 그룹화된 표현식의 고유한 값을 세어야 합니다.
