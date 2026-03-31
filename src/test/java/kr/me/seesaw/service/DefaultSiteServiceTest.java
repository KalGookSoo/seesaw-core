package kr.me.seesaw.service;

import kr.me.seesaw.command.CreateSiteCommand;
import kr.me.seesaw.config.SeesawProperties;
import kr.me.seesaw.core.file.LocalFileManager;
import kr.me.seesaw.domain.vo.Address;
import kr.me.seesaw.model.SiteModel;
import kr.me.seesaw.repository.impl.AttachmentRepositoryImpl;
import kr.me.seesaw.repository.impl.SiteRepositoryImpl;
import kr.me.seesaw.repository.impl.UserRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;

import java.io.IOException;
import java.nio.file.Path;

@ActiveProfiles({"test"})
@DataJpaTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@Import({DefaultSiteService.class, SeesawProperties.class, SiteRepositoryImpl.class, AttachmentRepositoryImpl.class, UserRepositoryImpl.class, LocalFileManager.class})
class DefaultSiteServiceTest {

    private final TestEntityManager entityManager;

    private final SiteService siteService;

    private final SeesawProperties seesawProperties;

    @TempDir
    private Path tempDir;

    public DefaultSiteServiceTest(TestEntityManager entityManager, SiteService siteService, SeesawProperties seesawProperties) {
        this.entityManager = entityManager;
        this.siteService = siteService;
        this.seesawProperties = seesawProperties;
    }

    @BeforeEach
    void setUp() {
        seesawProperties.setFilepath(tempDir + "/");
    }

    @Test
    @DisplayName("사이트 생성 시 사이트 모델을 반환한다.")
    void createSiteShouldReturnSiteModel() throws IOException {
        // given
        CreateSiteCommand createSiteCommand = new CreateSiteCommand(
                "site-1",
                "",
                "",
                "",
                true,
                false,
                "",
                Address.empty(),
                "",
                "",
                "",
                new MockMultipartFile("profileImage", "profile-image.jpeg", MediaType.IMAGE_JPEG_VALUE, new byte[4]),
                new MockMultipartFile("backgroundImage", "background-image.jpeg", MediaType.IMAGE_JPEG_VALUE, new byte[4])
        );

        // when
        SiteModel siteModel = siteService.createSite(createSiteCommand);
        entityManager.flush();

        // then
        Assertions.assertNotNull(siteModel);
    }

}
