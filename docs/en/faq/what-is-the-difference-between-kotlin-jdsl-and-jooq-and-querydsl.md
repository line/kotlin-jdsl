# What is the difference between Kotlin JDSL and jOOQ and QueryDSL?

Unlike jOOQ and QueryDSL, Kotlin JDSL does not need a `Metadata Model` for building queries.

Creating a metadata model typically involves code generation, which comes with several disadvantages:

- It increases the complexity of the initial project setup.
- It adds an extra step of metadata generation to the regular build process.

Although the first disadvantage can be solved through various plugins and references, the second one cannot. Using a code generator requires additional steps between entity modification and query building, which can interrupt a developer's working:

```
1. Modify tables and entities.
2. The modifications causes compile errors.
3. Run Maven or Gradle task to regenerate the metadata model.
4. Build queries based on the modified entities.
```

Despite these disadvantages, jOOQ and QueryDSL are still popular because they provide type safety, which allows the developer to catch errors in the query at compile time. Kotlin JDSL provides this type safety without the need for a metadata model.

Kotlin JDSL supports the building of type-based queries using Kotlin's KProperty. There is no need to worry about performance impact due to reflection because Kotlin JDSL uses only the names of KProperties, and when Kotlin compiles to Java, it registers these names as constants.

In addition, KProperty allows automatic detection of field changes by the IDE, ensuring that any changes to class field names are reflected immediately.

In summary, Kotlin JDSL overcomes the disadvantages of the metadata model used by jOOQ and QueryDSL, while retaining the benefits of type safety, and without any performance impact, enabling efficient and reliable query building.
