# How is Kotlin JDSL different from jOOQ and QueryDSL?

Unlike jOOq and QueryDSL, Kotlin JDSL does not generate a `Metadata Model (code generation) for writing queries.

If you use code generation, you'll have to do the following:

```
Modify tables and entities →
Entity modification causes compile errors →
Run Manven or Gradle task to regenerate the metadate →
Write query based on modified entity
```

Then you might think about trying JPQL yourself, but that might not be a good idea.

JPQL uses a string-based way of writing queries, which makes it impossible to detect errors at compile time.

However, the Kotlin JDSL uses code generation and pure code-based query construction rather than string-based construction.

This means that as soon as you modify an entity or field name, it is reflected in the query code, and the above inconvenience does not exist.

It also adopts an ORM-based object-oriented query like JPQL, which supports specifications such as polymorphism and fetch joins.

Therefore, the way we write queries also relies on object names and fields to query, rather than querying the table/column itself.

Below is a table comparing the differences between Kotlin JDSL, QueryDSL, and jOOQ.



|                      | Kotlin JDSL | QueryDSL                                       | jOOQ                                           |
|----------------------|-------------|------------------------------------------------|------------------------------------------------|
| Code Generation      | ❌           | ✅                                              | ✅                                              | ✅ |
| How to write queries | JPQL        | SQL, SQL, JPQL                                 | SQL                                            |
| Language support     | Kotlin      | All JVM languages including Java, Kotlin, etc. | All JVM languages including Java, Kotlin, etc. |
| Type Stability       | ✅           | ✅                                              | ✅                                              | ✅ |
