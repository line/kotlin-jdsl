# Why does Kotlin JDSL allow nullable return types?

Kotlin JDSL helps you write queries in a type-safe way.
You may wonder if it also allows non-null data to be null when returning a bunch of data (e.g. `Slice<T?>` in `findSlice`, `Page<T?>` in `findPage`).
This article explains why the Kotlin JDSL allows nullable return types.

## Why do we need nullable return types?

The reason why Kotlin JDSL supports nullable return types is that depending on how the JPQL query is written, some of the returned list items may contain null values.
The simplest example is when you look up a column or entity without using DTO Projection.

The table below shows some of the cases where nullable return types can occur when using the Kotlin JDSL.

| Item           | Nullable or not | Reason                                                                                                                                                        |
|----------------|-----------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------|
| DTO Projection | X               | Calling the constructor for all ROWs did not result in the object being created to allow nulls.<br/>Fields in DTO can be null, but DTO object cannot be null. |
| Column         | O               | Exists when looking up a field that is null.                                                                                                                  |
| Entity         | O               | Exists if the Entity being joined on Left Join is null.                                                                                                       |

As another example, the code below shows a situation where the Author entity exists, but the BookAuthor entity, the target of the left join, may be null.

```kotlin
val query = jpql {
    select(
        path(BookAuthor),
    ).from(
        entity(Author::class),
        leftJoin(BookAuthor::class).on(path(Author::authorId).equal(path(BookAuthor::authorId)))
    )
}
```

## Background on the design decision

In the early development of Kotlin JDSL 3.0, we tried nullable inference.
This was an attempt to automatically infer whether a query result is nullable through the type system.
However, during the development of queries that use JOIN, we encountered the problem that perfect nullable inference was not possible.

Starting with Kotlin JDSL 3.0, we're settling on a path that utilizes the user's query writing knowledge as much as possible, with a small learning curve.
This avoids confusion due to different interfaces for different users, and allows them to build on their existing JPQL knowledge without having to learn Kotlin JDSL syntax separately.
