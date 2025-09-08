# 동적 쿼리는 어떻게 만드나요?

조건에 따라 `where` 절에 조건자를 추가하여 동적 쿼리를 만들 수 있습니다.
Kotlin JDSL의 `where` 절은 조건자 목록을 받을 수 있으며, `and()` 및 `or()` 함수가 이럴 때 유용합니다.

일반적인 패턴은 `Predicate`의 가변 목록을 만들고 여기에 조건을 추가한 다음, 이 목록을 `and()`를 사용하여 `where` 절에 전달하는 것입니다.

`and()`에 전달된 조건자 목록이 비어 있으면 `1 = 1`로 처리되어 필터링이 적용되지 않습니다. 이는 모든 조건이 선택 사항일 수 있는 동적 쿼리에 유용합니다.

{% hint style="warning" %}
동적 쿼리를 작성할 때는 주의가 필요합니다. 만약 아무 조건도 적용되지 않으면, 쿼리는 엔티티의 모든 행을 조회하게 되어 대용량 테이블에서 성능 문제를 일으킬 수 있습니다. 항상 필터가 활성화되지 않는 경우를 고려해야 합니다.
{% endhint %}

다음은 예시입니다.

```kotlin
fun findBooks(title: String?, authorName: String?): List<Book> {
    val query = jpql {
        select(
            entity(Book::class)
        ).from(
            entity(Book::class)
        ).where(
            and(
                title?.let { path(Book::title).like("%$it%") },
                authorName?.let { path(Book::author)(Author::name).like("%$it%") },
            )
        )
    }
    return entityManager.createQuery(query, context).resultList
}
```

위 예제에서 `title`이 null이 아니면 `like` 조건자가 생성됩니다. `authorName`도 마찬가지입니다. `and()` 함수는 매개변수가 null일 때 `let` 블록에서 발생하는 `null` 조건자를 필터링합니다. `title`과 `authorName`이 모두 `null`이면 `where` 절은 사실상 비어 있게 되어 모든 책을 반환합니다.

또는 조건자 목록을 만들 수도 있습니다.

```kotlin
fun findBooks(title: String?, authorName: String?): List<Book> {
    val query = jpql {
        select(
            entity(Book::class)
        ).from(
            entity(Book::class)
        ).whereAnd(
            mutableListOf<Predicate?>().apply {
                if (!title.isNullOrBlank()) {
                    add(path(Book::title).like("%$title%"))
                }
                if (!authorName.isNullOrBlank()) {
                    add(path(Book::author)(Author::name).like("%$authorName%"))
                }
            }
        )
    }
    return entityManager.createQuery(query, context).resultList
}
```

`whereAnd`는 `where(and(...))`의 축약형입니다. 목록이 비어 있으면 쿼리에 아무런 조건도 추가되지 않습니다.
