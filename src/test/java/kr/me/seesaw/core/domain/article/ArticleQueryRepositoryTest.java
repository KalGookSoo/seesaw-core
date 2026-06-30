package kr.me.seesaw.core.domain.article;

import kr.me.seesaw.core.domain.article.persistence.ArticleQueryRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;

import java.time.LocalDateTime;

import static org.assertj.core.api.Fail.fail;

@ActiveProfiles({"test"})
@DataJpaTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class ArticleQueryRepositoryTest {

    private final TestEntityManager entityManager;

    private ArticleQueryRepository articleQueryRepository;

    public ArticleQueryRepositoryTest(TestEntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @BeforeEach
    void setup() {
        articleQueryRepository = new ArticleQueryRepositoryImpl(entityManager.getEntityManager());
    }

    @Test
    @DisplayName("생성일시 기준 이전 하나 조회")
    void findFirst() {
        // Given
        final String categoryId = null;
        final String keyField = null;
        final String keyWord = null;
        LocalDateTime createdDate = LocalDateTime.of(2020, 1, 1, 0, 0);

        // When & Then
        try {
            articleQueryRepository.findFirstNext(
                    categoryId,
                    keyField,
                    keyWord,
                    createdDate,
                    Sort.Direction.DESC.name()
            );
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    @DisplayName("생성일시 기준 다음 하나 조회")
    void findFirstAsc() {
        // Given
        final String categoryId = null;
        final String keyField = null;
        final String keyWord = null;
        LocalDateTime createdDate = LocalDateTime.of(2020, 1, 1, 0, 0);

        // When & Then
        try {
            articleQueryRepository.findFirstNext(
                    categoryId,
                    keyField,
                    keyWord,
                    createdDate,
                    Sort.Direction.ASC.name()
            );
        } catch (Exception e) {
            fail();
        }
    }

}
