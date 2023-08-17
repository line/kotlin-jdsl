# Entities

Kotlin JDSL has an Entity interface for representing entities in JPA. This entity interface can be treated as an expression and used in select clauses and where clauses, and it can also be treated as a from element and used in from clauses.

```kotlin
```

Some DSL functions can treat KClasses as entities and pass them as parameters.&#x20;

```kotlin
```

Entities must always have an alias. If you don't add an alias when creating an entity via the entity DSL function, Kotlin JDSL automatically creates an alias from the class name. So if you want to represent separate entities with the same entity type, you can use an alias to create entities with the same type but different references.

```kotlin
```

Select statements can also be treated as entities through the asEntity() DSL function. See this for more information.

```kotlin
```
