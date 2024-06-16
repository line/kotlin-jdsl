# Why is there a support module that only has a nullable return type?

You may have noticed that the methods provided by Kotlin JDSL through some support module all have only nullable return types.
Kotlin JDSL cannot infer whether the return type of query is non-null or nullable, because it doesn't know whether the result of a Join in the query is nullable or not.
Here are some cases where this results in a nullable return.

| Return Type | Comment                                                                              |
|-------------|--------------------------------------------------------------------------------------|
| Column      | It can return null when the column's definition is nullable.                         |
| Entity      | It can return null when the entity is joined in a nullable way, such as a left join. |

For example, the following returns a nullable entity due to a left join.

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

Kotlin JDSL could have provided methods with nullable and non-null return types separately, but it didn't, because this could be confusing for users.
For example, JPQL uses nullable return types by default, so it doesn't have a naming convention for non-null return types.
If Kotlin JDSL introduced its own naming convention, users might have questions about the internal implementation.
For example, does a method filter out nullable values and return them, or does it throw an exception if it contains a null?
Because Kotlin JDSL doesn't want users to have an additional learning burden while using the library, it hasn't defined its own methods.
