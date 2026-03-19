package kr.me.seesaw.service;

import kr.me.seesaw.command.CreateSiteCommand;
import kr.me.seesaw.domain.vo.Address;
import kr.me.seesaw.model.SiteModel;
import kr.me.seesaw.repository.AttachmentRepository;
import kr.me.seesaw.repository.SiteRepository;
import kr.me.seesaw.repository.UserRepository;
import kr.me.seesaw.repository.impl.SiteRepositoryImpl;
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
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.io.IOException;
import java.nio.file.Path;

@ActiveProfiles({"test"})
@DataJpaTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@Import(SiteRepositoryImpl.class)
class DefaultSiteServiceTest {

    private final TestEntityManager entityManager;

    private final SiteRepository siteRepository;

    private SiteService siteService;

    @TempDir
    private Path tempDir;

    @MockitoBean
    private AttachmentRepository attachmentRepository;

    @MockitoBean
    private UserRepository userRepository;

    public DefaultSiteServiceTest(TestEntityManager entityManager, SiteRepository siteRepository) {
        this.entityManager = entityManager;
        this.siteRepository = siteRepository;
    }

    @BeforeEach
    void setUp() {
        String filepath = tempDir + "/";
        siteService = new DefaultSiteService(
                filepath,
                siteRepository,
                attachmentRepository,
                userRepository
        );
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
