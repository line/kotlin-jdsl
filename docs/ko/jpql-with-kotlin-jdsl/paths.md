# Paths

Kotlin JDSL은 JPQL의 path expression을 표현하기 위해서 `Path` 인터페이스를 가지고 있습니다.
`Path`를 만들기 위해, `path()` 와 `invoke()`를 사용할 수 있습니다.

```kotlin
// Book.isbn.value
path(Book::isbn).path(Isbn::value)
path(Book::isbn)(Isbn::value)

// b.isbn.value
entity(Book::class, "b").path(Book::isbn).path(Isbn::value)
entity(Book::class, "b")(Book::isbn)(Isbn::value)
```

## Expression

`Path`는 [select clause](statements.md#select-clause) 나 [predicate](predicates.md) 등에서 [`Expression`](expressions.md)으로 사용될 수 있습니다.

```kotlin
// SELECT Book.isbn FROM Book AS Book WHERE Book.isbn.value = :param1
jpql {
    select(
        path(Book::isbn),
    ).from(
        entity(Book::class),
    ).where(
        path(Book::isbn)(Ibsn::value).eq("01"),
    )
}
```

## Treat

`Path`의 타입을 자식 타입으로 변경하기 위해, `treat()`를 사용할 수 있습니다.

```kotlin
path(EmployeeDepartment::employee).treat(FullTimeEmployee::class)
```
