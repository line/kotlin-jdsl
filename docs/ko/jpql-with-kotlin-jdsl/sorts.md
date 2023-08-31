# Sorts

Kotlin JDSL은 JPQL의 order-by item을 표현하기 위해 `Sort` 인터페이스를 가지고 있습니다.
`Sort`를 만들기 위해, `asc()`와 `desc()`를 [`Expression`](expressions.md) 뒤에 붙여 사용할 수 있습니다.

```kotlin
path(Book::isbn).asc()

path(Book::isbn).desc()
```

## Null order

`Sort`의 `nullsFirst()`와 `nullsLast()`를 호출하는 것으로 null이 조회 결과의 어디에 나타나야 하는지 표현할 수 있습니다.

```kotlin
path(Employee::nickname).asc().nullsFirst()

path(Employee::nickname).asc().nullsLast()
```
