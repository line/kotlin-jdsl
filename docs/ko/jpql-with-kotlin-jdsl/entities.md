# Entities

Kotlin JDSL은 JPQL의 entity를 표현하기 위해 `Entity` 인터페이스를 가지고 있습니다.
`Entity`를 만들기 위해, `entity()`를 사용할 수 있습니다.

```kotlin
entity(Book::class)
```

## Alias

모든 `Entity`는 alias를 가지고 있습니다.
만약 `entity()`에 alias를 명시하지 않으면 Kotlin JDSL이 class 명을 통해 자동으로 alias를 생성합니다.
`Entity`는 alias를 통해 구분이 되기 때문에 만약 동일한 타입의 `Entity`를 하나 이상 사용한다면 이 `Entity`들을 구별하기 위해서 alias가 필요합니다.

```kotlin
entity(Book::class)
entity(Book::class, Book::class.simpleName!!)

entity(Book::class, alias = "book1")
entity(Book::class, alias = "book2")
```

## Expression

`Entity`는 [select clause](statements.md#select-clause) 나 [predicate](predicates.md) 등에서 [`Expression`](expressions.md)으로 사용될 수 있습니다.

```kotlin
// SELECT b FROM Book AS b WHERE b.isbn.value = :param1
jpql {
    select(
        entity(Book::class, "b"),
    ).from(
        entity(Book::class, "b"),
    ).where(
        entity(Book::class, "b")(Book::isbn)(Ibsn::value).eq("01"),
    )
}
```

## Treat

`Entity`의 타입을 자식 타입으로 변경하기 위해, `treat()`를 사용할 수 있습니다.

```kotlin
entity(Employee::class).treat(FullTimeEmployee::class)
```
