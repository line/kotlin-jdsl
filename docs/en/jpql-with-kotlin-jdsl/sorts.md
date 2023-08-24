# Sorts

Kotlin JDSL has `Sort` interface to represent an order-by item in JPQL. To build `Sort`, you can use `asc()`
and `desc()` after [`Expression`](expressions.md).

```kotlin
path(Book::isbn).asc()

path(Book::isbn).desc()
```

## Null order

Calling nullsFirst() or nullsLast() on `Sort` allows to specify where null values appear in an ordered list.

```kotlin
path(Employee::nickname).asc().nullsFirst()

path(Employee::nickname).asc().nullsLast()
```
