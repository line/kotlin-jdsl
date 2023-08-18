# Paths

Path represents an path expression in JPA. It can be represented by path or invoke function.&#x20;

```kotlin
// Book.isbn.value
path(Book::isbn).path(Isbn::value)
path(Book::isbn)(Isbn::value)

// b.isbn.value
entity(Book::class, "b").path(Book::isbn).path(Isbn::value)
entity(Book::class, "b")(Book::isbn)(Isbn::value)
```

### Expression

Path can be treated as an expression. It can be used where an expression is required, such as a [select clause](statements.md#select-clause) or [predicate](predicates.md).

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

### Treat

Path can be cast to sub-class using treat function.&#x20;

```kotlin
path(EmployeeDepartment::employee).treat(FullTimeEmployee::class)
```
