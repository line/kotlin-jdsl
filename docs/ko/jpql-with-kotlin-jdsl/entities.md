# Entities

Kotlin JDSL has `Entity` interface. `Entity` is an interface to represent an entity in JPQL. To build `Entity`, you can
use `entity()`.

```kotlin
entity(Book::class)
```

## Alias

`Entity` has an alias. If you don't specify an alias in `entity()`, Kotlin JDSL generates an alias from the class
name. `Entity` is distinguished by an alias, so if you use more than one `Entity` with the same type, you need to alias
them to distinguish them.

```kotlin
entity(Book::class)
entity(Book::class, Book::class.simpleName!!)

entity(Book::class, alias = "book1")
entity(Book::class, alias = "book2")
```

## Expression

`Entity` can be used as [`Expression`](expressions.md), such as in a [select clause](statements.md#select-clause)
or [predicate](predicates.md).

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

To cast type of `Entity` to subclass, you can use `treat()`.

```kotlin
entity(Employee::class).treat(FullTimeEmployee::class)
```
