package com.linecorp.kotlinjdsl.example.spring.batch.jpql

import com.linecorp.kotlinjdsl.dsl.jpql.jpql
import com.linecorp.kotlinjdsl.example.spring.batch.jpql.entity.author.Author
import com.linecorp.kotlinjdsl.example.spring.batch.jpql.entity.book.Book
import com.linecorp.kotlinjdsl.example.spring.batch.jpql.entity.book.BookPrice
import com.linecorp.kotlinjdsl.example.spring.batch.jpql.repository.author.AuthorRepository
import com.linecorp.kotlinjdsl.example.spring.batch.jpql.repository.book.BookRepository
import com.linecorp.kotlinjdsl.support.spring.batch.item.database.orm.KotlinJdslQueryProviderFactory
import jakarta.persistence.EntityManagerFactory
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test
import org.springframework.batch.core.BatchStatus
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.database.JpaItemWriter
import org.springframework.batch.item.database.JpaPagingItemReader
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder
import org.springframework.batch.item.database.orm.JpaQueryProvider
import org.springframework.batch.test.JobLauncherTestUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.PlatformTransactionManager
import java.math.BigDecimal
import java.util.*

@SpringBootTest
class BatchExampleTest : WithAssertions {
    @Autowired
    private lateinit var jobRepository: JobRepository

    @Autowired
    private lateinit var jobLauncher: JobLauncher

    @Autowired
    private lateinit var transactionManager: PlatformTransactionManager

    @Autowired
    private lateinit var entityManagerFactory: EntityManagerFactory

    @Autowired
    private lateinit var queryProviderFactory: KotlinJdslQueryProviderFactory

    @Autowired
    private lateinit var authorRepository: AuthorRepository

    @Autowired
    private lateinit var bookRepository: BookRepository

    @Test
    fun `modify the name of authors with id greater than 2`() {
        // given
        val queryProvider = queryProviderFactory.create(
            jpql {
                select(
                    entity(Author::class),
                ).from(
                    entity(Author::class),
                ).where(
                    path(Author::authorId).gt(2L),
                )
            },
        )

        val processor = { author: Author ->
            author.apply { name = "Modified$name" }
        }

        // when
        val job = job(queryProvider, processor).launchJob()

        val actual = authorRepository.findAll()

        // then
        assertThat(job.status).isEqualTo(BatchStatus.COMPLETED)

        assertThat(actual.map { it.name }).containsOnly(
            "Author01",
            "Author02",
            "ModifiedAuthor03",
            "ModifiedAuthor04",
        )
    }

    @Test
    fun `10 percentage discount on all books`() {
        // given
        val queryProvider = queryProviderFactory.create {
            select(
                entity(Book::class),
            ).from(
                entity(Book::class),
                fetchJoin(Book::publisher),
            )
        }

        val processor = { book: Book ->
            book.apply { salePrice = BookPrice(salePrice.value * BigDecimal.valueOf(0.9)) }
        }

        // when
        val job = job(queryProvider, processor).launchJob()

        val actual = bookRepository.findAll()

        // then
        assertThat(job.status).isEqualTo(BatchStatus.COMPLETED)

        assertThat(actual.map { it.salePrice }).containsOnly(
            BookPrice(0.90),
            BookPrice(1.80),
            BookPrice(2.70),
            BookPrice(3.60),
            BookPrice(4.50),
            BookPrice(5.40),
            BookPrice(6.30),
            BookPrice(7.20),
            BookPrice(8.10),
            BookPrice(9.00),
            BookPrice(9.00),
            BookPrice(9.00),
        )
    }

    private fun <T : Any> job(queryProvider: JpaQueryProvider, processor: (T) -> T): JobLauncherTestUtils {
        val randomName = UUID.randomUUID().toString()

        @Suppress("UNCHECKED_CAST")
        val job = JobBuilder(randomName, jobRepository)
            .start(
                StepBuilder(randomName, jobRepository)
                    .chunk<Any, Any>(100, transactionManager)
                    .reader(createReader(queryProvider))
                    .processor(createProcessor(processor as (Any) -> Any))
                    .writer(createWriter())
                    .build(),
            )
            .build()

        return JobLauncherTestUtils().also {
            it.jobRepository = jobRepository
            it.jobLauncher = jobLauncher
            it.job = job
        }
    }

    private fun createReader(queryProvider: JpaQueryProvider): JpaPagingItemReader<Any> {
        return JpaPagingItemReaderBuilder<Any>()
            .entityManagerFactory(entityManagerFactory)
            .queryProvider(queryProvider)
            .pageSize(100)
            .saveState(false)
            .build()
    }

    private fun createProcessor(processor: (Any) -> Any): ItemProcessor<Any, Any> {
        return ItemProcessor { item ->
            processor(item)
        }
    }

    private fun createWriter(): JpaItemWriter<Any> {
        return JpaItemWriterBuilder<Any>()
            .entityManagerFactory(entityManagerFactory)
            .build()
    }
}
