# Paths

Kotlin JDSL has `Path` interface to represent a path expression in JPQL. To build `Path`, you can use `path()`
and `invoke()`.

```kotlin
// Book.isbn.value
path(Book::isbn).path(Isbn::value)
path(Book::isbn)(Isbn::value)

// b.isbn.value
entity(Book::class, "b").path(Book::isbn).path(Isbn::value)
entity(Book::class, "b")(Book::isbn)(Isbn::value)
```

## Expression

`Path` can be used as [`Expression`](expressions.md), such as in a [select clause](statements.md#select-clause)
or [predicate](predicates.md).

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

To cast type of `Path` to subclass, you can use `treat()`.

```kotlin
path(EmployeeDepartment::employee).treat(FullTimeEmployee::class)
```
