# DTO 필드나 별칭(alias)으로 정렬하려면 어떻게 하나요?

DTO 프로젝션의 필드나 `select` 절에 정의한 별칭으로 쿼리 결과를 정렬할 수 있습니다.

이렇게 하려면 먼저 `select` 절의 표현식에 대한 별칭을 만들어야 합니다. 그런 다음 `orderBy` 절에서 이 별칭을 참조할 수 있습니다.

다음은 DTO로 프로젝션되는 별칭이 지정된 열로 정렬하는 예입니다.

```kotlin
data class BookInfo(
    val name: String,
    val authorCount: Long
)

// 1. 표현식에 대한 별칭을 정의합니다.
val authorCountAlias = expression(Long::class, "authorCount")

val query = jpql {
    selectNew<BookInfo>(
        path(Book::name),
        count(Book::authors).`as`(authorCountAlias) // 2. select 절에서 별칭을 사용합니다.
    ).from(
        entity(Book::class)
    ).groupBy(
        path(Book::name)
    ).orderBy(
        authorCountAlias.asc() // 3. orderBy 절에서 별칭을 사용합니다.
    )
}

val bookInfos = entityManager.createQuery(query, context).resultList
```

이 예제에서는 다음을 수행합니다.
1.  `authorCountAlias`라는 별칭 역할을 할 `Expression`을 만듭니다.
2.  `selectNew` 절에서 `as(authorCountAlias)`를 사용하여 `count(Book::authors)` 표현식을 별칭과 연결합니다.
3.  `orderBy` 절에서 `authorCountAlias`를 참조하여 결과를 정렬할 수 있습니다.

이 패턴을 사용하면 DTO에 포함된 계산된 값이나 집계 함수로 정렬할 수 있습니다.