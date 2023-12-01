# Entities

Kotlin JDSL has the `Entity` interface to represent an entity in JPQL.
Use `entity()` to build `Entity`.

```kotlin
entity(Book::class)
```

## Alias

All `Entity` has an alias.
If you don't specify an alias in `entity()`, Kotlin JDSL automatically generates an alias from the class name.
`Entity` is identified by its alias. If you use more than one `Entity` with the same type, you need to alias them to identify each `Entity`.

```kotlin
entity(Book::class)
entity(Book::class, Book::class.simpleName!!)

entity(Book::class, alias = "book1")
entity(Book::class, alias = "book2")
```

## Expression

`Entity` can be used as [`Expression`](expressions.md) in a [select clause](statements.md#select-clause) or [predicate](predicates.md).

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

Use `treat()` to typecast `Entity` to subclass.

```kotlin
entity(Employee::class).treat(FullTimeEmployee::class)
```
