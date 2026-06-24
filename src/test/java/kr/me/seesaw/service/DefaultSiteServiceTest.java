package kr.me.seesaw.service;

import kr.me.seesaw.command.CreateSiteCommand;
import kr.me.seesaw.config.SeesawProperties;
import kr.me.seesaw.core.authentication.AnonymousPrincipalProvider;
import kr.me.seesaw.core.file.LocalFileManager;
import kr.me.seesaw.domain.Site;
import kr.me.seesaw.domain.vo.Address;
import kr.me.seesaw.domain.vo.SiteColor;
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
@Import({
        DefaultSiteService.class,
        SeesawProperties.class,
        SiteRepositoryImpl.class,
        AttachmentRepositoryImpl.class,
        UserRepositoryImpl.class,
        LocalFileManager.class,
        AnonymousPrincipalProvider.class
})
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
                "#CC4202",
                "#FFFFFF",
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
        Assertions.assertEquals("#cc4202", siteModel.getThemeColor());
        Assertions.assertEquals("#ffffff", siteModel.getBackgroundColor());
    }

    @Test
    @DisplayName("사이트 생성 시 색상이 비어 있으면 기본 색상을 반환한다.")
    void createSiteShouldUseDefaultColorsWhenColorsAreBlank() throws IOException {
        // given
        CreateSiteCommand createSiteCommand = new CreateSiteCommand(
                "site-1",
                "",
                "",
                "",
                true,
                false,
                "",
                null,
                "",
                Address.empty(),
                "",
                "",
                "",
                null,
                null
        );

        // when
        SiteModel siteModel = siteService.createSite(createSiteCommand);
        entityManager.flush();

        // then
        Assertions.assertEquals(SiteColor.DEFAULT_THEME_COLOR, siteModel.getThemeColor());
        Assertions.assertEquals(SiteColor.DEFAULT_BACKGROUND_COLOR, siteModel.getBackgroundColor());
    }

    @Test
    @DisplayName("사이트 생성 시 올바르지 않은 색상은 예외를 던진다.")
    void createSiteShouldThrowExceptionWhenColorIsInvalid() {
        // given
        CreateSiteCommand createSiteCommand = new CreateSiteCommand(
                "site-1",
                "",
                "",
                "",
                true,
                false,
                "orange",
                "#ffffff",
                "",
                Address.empty(),
                "",
                "",
                "",
                null,
                null
        );

        // when & then
        Assertions.assertThrows(IllegalArgumentException.class, () -> siteService.createSite(createSiteCommand));
    }

    @Test
    @DisplayName("사이트 수정 시 색상을 사이트 모델에 반영한다.")
    void updateSiteShouldReturnSiteModelWithColors() throws IOException {
        // given
        Site site = new Site();
        site.setName("site-1");
        site.setDomainName("site-1.local");
        entityManager.persistAndFlush(site);

        CreateSiteCommand updateSiteCommand = new CreateSiteCommand(
                "site-1-updated",
                "site-1-updated.local",
                "",
                "",
                true,
                false,
                "#123456",
                "#ABCDEF",
                "",
                Address.empty(),
                "",
                "",
                "",
                null,
                null
        );

        // when
        SiteModel siteModel = siteService.updateSite(site.getId(), updateSiteCommand);
        entityManager.flush();

        // then
        Assertions.assertEquals("#123456", siteModel.getThemeColor());
        Assertions.assertEquals("#abcdef", siteModel.getBackgroundColor());
    }

}
