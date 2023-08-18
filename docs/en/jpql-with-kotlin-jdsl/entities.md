# Entities

Entity represents an entity in JPA. It can be represented by entity function or KClass.&#x20;

```kotlin
entity(Book::class)
```

### Alias

All entities have an alias. If you don't specify an alias in entity function, Kotlin JDSL automatically generates one from the class name.

```kotlin
entity(Book::class) // == entity(Book::class, Book::class.simpleName!!)

entity(Book::class, alias = "b")
```

### Expression

Entity can be treated as an expression. It can be used where an expression is required, such as a [select clause](statements.md#select-clause) or [predicate](predicates.md), in which case only the alias is printed in the query and is used only to refer to the entity.

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

### Treat

Entity can be cast to sub-class using treat function.&#x20;

```kotlin
entity(Employee::class).treat(FullTimeEmployee::class)
```
