# How to use database-specific functions?

You can use database-specific functions that are not part of the standard JPQL specification by using the `function()` DSL function. This is useful for functions like `JSON_VALUE`, `GROUP_CONCAT`, etc.

To use a database function, you need to provide the return type, the function name, and the arguments.

Here is an example of using `GROUP_CONCAT` in MySQL:

```kotlin
val query = jpql {
    select(
        function(
            String::class,
            "GROUP_CONCAT",
            path(Book::name)
        )
    ).from(
        entity(Book::class)
    )
}
```

## Registering the function in your JPA provider

For the JPA provider to understand the function, you might need to register it.

### Hibernate

If you are using Hibernate, you need to register the function using a `FunctionContributor`.

```kotlin
class MyFunctionContributor : FunctionContributor {
    override fun contributeFunctions(functionContributions: FunctionContributions) {
        functionContributions.functionRegistry.register(
            "group_concat",
            StandardSQLFunction("group_concat", StandardBasicTypes.STRING)
        )
    }
}
```

And then you need to register this contributor. You can do this by creating a file `src/main/resources/META-INF/services/org.hibernate.boot.model.FunctionContributor` with the fully qualified name of your contributor class.

```
com.example.MyFunctionContributor
```

Or if you are using Spring Boot, you can set the property in `application.yml`:

```yaml
spring:
  jpa:
    properties:
      hibernate:
        metadata_builder_contributor: com.example.MyFunctionContributor
```

With this setup, you can use `group_concat` in your queries.
