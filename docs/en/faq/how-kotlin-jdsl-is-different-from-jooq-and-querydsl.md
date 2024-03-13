# How is Kotlin JDSL different from jOOQ and QueryDSL?

Unlike jOOq and QueryDSL, Kotlin JDSL does not generate a `Metadata Model (code generation) for writing queries.

If you use code generation, you'll have to do the following.

```
Modify tables / entities →
Compilation errors due to code and metacode mismatch →
Temporarily change all code associated with the modified code, e.g. by commenting it out, to make it compile.
Regenerate code
Remove the temporary code that was processed to pass compilation and replace it with working code.
```

In addition, JPQL uses a string-based query writing method, so it can pass compilation but may cause errors when running the web application.

However, Line-JDSL allows you to write queries based on pure code rather than using string-based writing and code generation.

This means that as soon as you modify an entity or field name, it is reflected in the query code and can be compiled and executed.

It also adopts an ORM-based object-oriented query like JPQL, which supports specifications such as polymorphism and fetch joins.

Therefore, the way we write queries also relies on object names and fields to query, rather than querying the table/column itself.

Below is a table comparing the differences between Kotlin JDSL, QueryDSL, and jOOQ.



|                      | Kotlin JDSL | QueryDSL                                       | jOOQ                                           |
|----------------------|-------------|------------------------------------------------|------------------------------------------------|
| Code Generation      | ❌           | ✅                                              | ✅                                              | ✅ |
| How to write queries | JPQL        | SQL, SQL, JPQL                                 | SQL                                            |
| Language support     | Kotlin      | All JVM languages including Java, Kotlin, etc. | All JVM languages including Java, Kotlin, etc. |
| Type Stability       | ✅           | ✅                                              | ✅                                              | ✅ |
