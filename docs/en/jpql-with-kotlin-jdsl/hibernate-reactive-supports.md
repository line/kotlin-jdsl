# Hibernate Reactive supports

Kotlin JDSL provides the `hibernate-reactive-support` module to easily execute queries using [Hibernate Reactive](https://hibernate.org/reactive/).

This module offers extension functions for Hibernate Reactive's `Session` and `StatelessSession` objects, allowing you to pass a Kotlin JDSL query object directly to create a reactive query.

## Executing Queries

The main extension functions provided are:
- `createQuery()`: For `SELECT` statements on both `Session` and `StatelessSession`. For `StatelessSession`, it is also used for `UPDATE` and `DELETE` statements.
- `createMutationQuery()`: For `UPDATE` and `DELETE` statements on a stateful `Session`.

These functions are used on a `Session` or `StatelessSession` instance, which you typically get from a `SessionFactory`.

### Mutiny

#### Stateful Session
With a stateful `Mutiny.Session`, use `createQuery` for select statements and `createMutationQuery` for update/delete statements.

```kotlin
import com.linecorp.kotlinjdsl.support.hibernate.reactive.extension.createQuery
import com.linecorp.kotlinjdsl.support.hibernate.reactive.extension.createMutationQuery
import io.smallrye.mutiny.Uni
import org.hibernate.reactive.mutiny.Mutiny

val sessionFactory: Mutiny.SessionFactory = ...
val context = JpqlRenderContext()

// Get a list of results
val selectQuery = jpql {
    select(
        entity(Book::class)
    ).from(
        entity(Book::class)
    )
}
val books: Uni<List<Book>> = sessionFactory.withSession { session ->
    session.createQuery(selectQuery, context).resultList
}

// Execute an update statement
val updateQuery = jpql {
    update(
        entity(Book::class)
    ).set(
        path(Book::price), BookPrice(10)
    ).where(
        path(Book::isbn).eq(Isbn("01"))
    )
}
val updatedRows: Uni<Int> = sessionFactory.withTransaction { session, _ ->
    session.createMutationQuery(updateQuery, context).executeUpdate()
}
```

#### Stateless Session
With a `Mutiny.StatelessSession`, the `createQuery` extension function is used for all types of statements (`SELECT`, `UPDATE`, `DELETE`).

```kotlin
import com.linecorp.kotlinjdsl.support.hibernate.reactive.extension.createQuery

// Execute a delete statement with a stateless session
val deleteQuery = jpql {
    deleteFrom(
        entity(Book::class)
    ).where(
        path(Book::isbn).eq(Isbn("01"))
    )
}
val deletedRows: Uni<Int> = sessionFactory.withStatelessTransaction { session, _ ->
    session.createQuery(deleteQuery, context).executeUpdate()
}
```

### Stage

The usage pattern for `Stage.Session` and `Stage.StatelessSession` is similar to Mutiny's.

#### Stateful Session
```kotlin
import com.linecorp.kotlinjdsl.support.hibernate.reactive.extension.createQuery
import com.linecorp.kotlinjdsl.support.hibernate.reactive.extension.createMutationQuery
import java.util.concurrent.CompletionStage
import org.hibernate.reactive.stage.Stage

val sessionFactory: Stage.SessionFactory = ...
val context = JpqlRenderContext()

// Get a list of results
val selectQuery = jpql {
    select(
        entity(Book::class)
    ).from(
        entity(Book::class)
    )
}
val books: CompletionStage<List<Book>> = sessionFactory.withSession { session ->
    session.createQuery(selectQuery, context).resultList
}

// Execute a delete statement
val deleteQuery = jpql {
    deleteFrom(
        entity(Book::class)
    ).where(
        path(Book::isbn).eq(Isbn("01"))
    )
}
val deletedRows: CompletionStage<Int> = sessionFactory.withTransaction { session, _ ->
    session.createMutationQuery(deleteQuery, context).executeUpdate()
}
```

#### Stateless Session
```kotlin
import com.linecorp.kotlinjdsl.support.hibernate.reactive.extension.createQuery

// Execute a delete statement with a stateless session
val deleteQuery = jpql {
    deleteFrom(
        entity(Book::class)
    ).where(
        path(Book::isbn).eq(Isbn("01"))
    )
}
val deletedRows: CompletionStage<Int> = sessionFactory.withStatelessTransaction { session, _ ->
    session.createQuery(deleteQuery, context).executeUpdate()
}
```

## A Note on Fetching Strategies and Session Scope

{% hint style="danger" %}
Accessing lazy-loaded associations outside of an active reactive session will cause a `LazyInitializationException`.
{% endhint %}

The scope of a reactive session is typically limited to the lambda block of methods like `withSession` or `withTransaction`. Once the reactive stream completes and the block finishes executing, the session is closed.

#### Safe: Accessing Lazy Associations Inside the Session Scope
You can safely access lazy-loaded associations as long as it's done **inside** the session's scope.

```kotlin
val query = jpql {
    select(
        entity(Book::class)
    ).from(
        entity(Book::class)
    )
}

val bookAuthorSizes: Uni<List<Int>> = sessionFactory.withSession { session ->
    session.createQuery(query, context).resultList.onItem().transform { bookList ->
        // Accessing book.authors here is safe because the session is still active.
        bookList.map { it.authors.size }
    }
}
```

#### Required: Using `fetch join` for Access Outside the Session Scope
If you need to access associations **after** the session is closed (e.g., in a subsequent step of your reactive pipeline), you **must** initialize them eagerly using a `fetch join`.

```kotlin
// Use fetch join to load authors eagerly
val query = jpql {
    select(
        distinct(entity(Book::class))
    ).from(
        entity(Book::class),
        fetchJoin(Book::authors) // Eagerly fetch the authors collection
    )
}

val books: Uni<List<Book>> = sessionFactory.withSession { session ->
    session.createQuery(query, context).resultList
}

// This is now safe because the authors collection was eagerly fetched.
books.onItem().invoke { bookList ->
    bookList.forEach { book ->
        println(book.authors.size)
    }
}
```
