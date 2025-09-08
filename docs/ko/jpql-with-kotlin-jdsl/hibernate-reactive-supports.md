# Hibernate Reactive 지원

Kotlin JDSL은 [Hibernate Reactive](https://hibernate.org/reactive/)를 사용하여 쿼리를 쉽게 실행할 수 있도록 `hibernate-reactive-support` 모듈을 제공합니다.

이 모듈은 Hibernate Reactive의 `Session` 및 `StatelessSession` 객체에 대한 확장 함수를 제공하여, Kotlin JDSL 쿼리 객체를 직접 전달하여 리액티브 쿼리를 생성할 수 있게 해줍니다.

## 쿼리 실행하기

제공되는 주요 확장 함수는 다음과 같습니다:
- `createQuery()`: `Session`과 `StatelessSession` 모두에서 `SELECT` 구문을 위해 사용됩니다. `StatelessSession`의 경우, `UPDATE`와 `DELETE` 구문에도 사용됩니다.
- `createMutationQuery()`: 상태를 가지는(stateful) `Session`에서 `UPDATE`와 `DELETE` 구문을 위해 사용됩니다.

이 함수들은 보통 `SessionFactory`로부터 얻는 `Session` 또는 `StatelessSession` 인스턴스에서 사용됩니다.

### Mutiny

#### Stateful Session (상태 기반 세션)
상태를 가지는 `Mutiny.Session`에서는 `SELECT` 구문에 `createQuery`를, `UPDATE`/`DELETE` 구문에 `createMutationQuery`를 사용합니다.

```kotlin
import com.linecorp.kotlinjdsl.support.hibernate.reactive.extension.createQuery
import com.linecorp.kotlinjdsl.support.hibernate.reactive.extension.createMutationQuery
import io.smallrye.mutiny.Uni
import org.hibernate.reactive.mutiny.Mutiny

val sessionFactory: Mutiny.SessionFactory = ...
val context = JpqlRenderContext()

// 결과 목록 조회
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

// Update 구문 실행
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

#### Stateless Session (상태 비저장 세션)
`Mutiny.StatelessSession`에서는 모든 종류의 구문(`SELECT`, `UPDATE`, `DELETE`)에 `createQuery` 확장 함수가 사용됩니다.

```kotlin
import com.linecorp.kotlinjdsl.support.hibernate.reactive.extension.createQuery

// Stateless 세션으로 delete 구문 실행
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

`Stage.Session`과 `Stage.StatelessSession`의 사용 패턴은 Mutiny와 유사합니다.

#### Stateful Session (상태 기반 세션)
```kotlin
import com.linecorp.kotlinjdsl.support.hibernate.reactive.extension.createQuery
import com.linecorp.kotlinjdsl.support.hibernate.reactive.extension.createMutationQuery
import java.util.concurrent.CompletionStage
import org.hibernate.reactive.stage.Stage

val sessionFactory: Stage.SessionFactory = ...
val context = JpqlRenderContext()

// 결과 목록 조회
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

// Delete 구문 실행
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

#### Stateless Session (상태 비저장 세션)
```kotlin
import com.linecorp.kotlinjdsl.support.hibernate.reactive.extension.createQuery

// Stateless 세션으로 delete 구문 실행
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

## Fetch 전략과 세션 범위에 대한 참고사항

{% hint style="danger" %}
활성화된 리액티브 세션 외부에서 지연 로딩된 연관 관계에 접근하면 `LazyInitializationException`이 발생합니다.
{% endhint %}

리액티브 세션의 범위는 일반적으로 `withSession`이나 `withTransaction`과 같은 메서드의 람다 블록으로 제한됩니다. 리액티브 스트림이 완료되고 블록 실행이 끝나면 세션은 닫힙니다.

#### 안전한 방법: 세션 범위 내에서 지연 로딩된 연관 관계 접근하기
세션 범위 **내에서** 접근하는 한, 지연 로딩된 연관 관계를 안전하게 사용할 수 있습니다.

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
        // 세션이 아직 활성 상태이므로 여기서 book.authors에 접근하는 것은 안전합니다.
        bookList.map { it.authors.size }
    }
}
```

#### 필수적인 방법: 세션 범위 밖에서 사용하기 위해 `fetch join` 사용하기
세션이 닫힌 **후에** (예: 리액티브 파이프라인의 다음 단계에서) 연관 관계에 접근해야 하는 경우, `fetch join`을 사용하여 반드시 즉시 로딩해야 합니다.

```kotlin
// fetch join을 사용하여 authors를 즉시 로딩합니다.
val query = jpql {
    select(
        distinct(entity(Book::class))
    ).from(
        entity(Book::class),
        fetchJoin(Book::authors) // authors 컬렉션을 즉시 fetch합니다.
    )
}

val books: Uni<List<Book>> = sessionFactory.withSession { session ->
    session.createQuery(query, context).resultList
}

// authors 컬렉션이 즉시 로딩되었으므로 이제 안전합니다.
books.onItem().invoke { bookList ->
    bookList.forEach { book ->
        println(book.authors.size)
    }
}
```
