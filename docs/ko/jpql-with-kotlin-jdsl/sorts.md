# Sorts

Sort represents an orderby item in JPQL.

Sort can be represented by the asc and desc extension functions in [expression](expressions.md).

```kotlin
path(Book::isbn).asc()
path(Book::isbn).desc()
```

## Null order

You can use null order, depending on your JPA implementation. NULL ORDER determines whether null values are sorted before or after a column containing null values.

```kotlin
path(Employee::nickname).asc().nullsFirst()

path(Employee::nickname).asc().nullsLast()
```
