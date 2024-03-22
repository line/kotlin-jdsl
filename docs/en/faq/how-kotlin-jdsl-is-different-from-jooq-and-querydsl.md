# How is Kotlin JDSL different from jOOQ and QueryDSL?

Unlike jOOQ and QueryDSL, Kotlin JDSL does not generate a `Metadata Model (code generation) for writing queries.

There are several disadvantages to using the code generation approach

1. increases project initial setup complexity
2. adds metadata generation to the normal build process

While the first drawback can be overcome with a number of plugins and references, the second is not.

Code generation requires extra work between `modify entity` and `write query`, which breaks the developer's flow of thought.

```
1. Modify tables and entities
2. Entity modification causes compile errors
3. Run Maven or Gradle task to regenerate the metadata model
4. Write query based on modified entity
```

Then you might think about trying JPQL yourself, but that might not be a good idea.

JPQL uses a string-based way of writing queries, which makes it impossible to detect errors at compile time.

For example, if you change a specific field in an entity, the JPQL string query that uses that field is not automatically corrected.

Although the error can be resolved with the help of the IDE, it ends up being handled manually by the developer, which is a productivity killer.

However, the Kotlin JDSL uses code generation and pure code-based query construction rather than string-based construction.

This means that as soon as you modify an entity or field name, it is reflected in the query code, and the above inconvenience does not exist.

It also adopts an ORM-based, object-oriented query approach like JPQL, which supports specifications such as polymorphism and fetch joins.

Therefore, the way we write queries also relies on object names and fields to query, rather than querying the table/column itself.
