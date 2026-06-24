package kr.me.seesaw.service;

import kr.me.seesaw.command.CreateWebPushSubscriptionCommand;
import kr.me.seesaw.command.DeleteWebPushSubscriptionCommand;
import kr.me.seesaw.core.authentication.PrincipalProvider;
import kr.me.seesaw.domain.Site;
import kr.me.seesaw.domain.User;
import kr.me.seesaw.domain.WebPushSubscription;
import kr.me.seesaw.model.WebPushSubscriptionModel;
import kr.me.seesaw.repository.SiteRepository;
import kr.me.seesaw.repository.UserRepository;
import kr.me.seesaw.repository.WebPushSubscriptionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultWebPushSubscriptionServiceTest {

    @Mock
    private WebPushSubscriptionRepository webPushSubscriptionRepository;

    @Mock
    private SiteRepository siteRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PrincipalProvider principalProvider;

    private WebPushSubscriptionService webPushSubscriptionService;

    @BeforeEach
    void setup() {
        webPushSubscriptionService = new DefaultWebPushSubscriptionService(
                webPushSubscriptionRepository,
                siteRepository,
                userRepository,
                principalProvider
        );
    }

    @Test
    @DisplayName("구독 생성 요청 시 endpoint 기준으로 구독 정보를 저장합니다.")
    void subscribeShouldCreateSubscription() {
        CreateWebPushSubscriptionCommand command = createCommand();
        Site site = createSite();
        User user = createUser();

        when(principalProvider.getAuthentication()).thenReturn(new UsernamePasswordAuthenticationToken("user", "password"));
        when(siteRepository.findById("site-id")).thenReturn(Optional.of(site));
        when(userRepository.findByUsername("user")).thenReturn(Optional.of(user));
        when(webPushSubscriptionRepository.findByEndpoint("endpoint")).thenReturn(Optional.empty());
        when(webPushSubscriptionRepository.save(any(WebPushSubscription.class))).thenAnswer(invocation -> invocation.getArgument(0));

        WebPushSubscriptionModel model = webPushSubscriptionService.subscribe(command, "user-agent");

        assertNotNull(model);
        assertEquals("site-id", model.getSiteId());
        assertEquals("user-id", model.getUserId());
        assertEquals("endpoint", model.getEndpoint());
        assertTrue(model.isEnabled());
        verify(webPushSubscriptionRepository).save(any(WebPushSubscription.class));
    }

    @Test
    @DisplayName("기존 endpoint로 구독 요청 시 저장된 구독 정보를 갱신합니다.")
    void subscribeShouldUpdateExistingSubscription() {
        CreateWebPushSubscriptionCommand command = createCommand();
        Site site = createSite();
        User user = createUser();
        WebPushSubscription subscription = new WebPushSubscription(site, user, "endpoint", "old-p256dh", "old-auth", "old-agent");
        subscription.disable();

        when(principalProvider.getAuthentication()).thenReturn(new UsernamePasswordAuthenticationToken("user", "password"));
        when(siteRepository.findById("site-id")).thenReturn(Optional.of(site));
        when(userRepository.findByUsername("user")).thenReturn(Optional.of(user));
        when(webPushSubscriptionRepository.findByEndpoint("endpoint")).thenReturn(Optional.of(subscription));
        when(webPushSubscriptionRepository.save(subscription)).thenReturn(subscription);

        webPushSubscriptionService.subscribe(command, "user-agent");

        assertEquals("p256dh", subscription.getP256dh());
        assertEquals("auth", subscription.getAuth());
        assertEquals("user-agent", subscription.getUserAgent());
        assertTrue(subscription.isEnabled());
    }

    @Test
    @DisplayName("구독 삭제 요청 시 구독을 비활성화합니다.")
    void unsubscribeShouldDisableSubscription() {
        DeleteWebPushSubscriptionCommand command = new DeleteWebPushSubscriptionCommand();
        command.setSiteId("site-id");
        command.setEndpoint("endpoint");

        WebPushSubscription subscription = new WebPushSubscription(createSite(), createUser(), "endpoint", "p256dh", "auth", "user-agent");

        when(principalProvider.getAuthentication()).thenReturn(new UsernamePasswordAuthenticationToken("user", "password"));
        when(webPushSubscriptionRepository.findBySiteIdAndUserUsernameAndEndpoint("site-id", "user", "endpoint"))
                .thenReturn(Optional.of(subscription));
        when(webPushSubscriptionRepository.save(subscription)).thenReturn(subscription);

        webPushSubscriptionService.unsubscribe(command);

        assertFalse(subscription.isEnabled());
        verify(webPushSubscriptionRepository).save(subscription);
    }

    private CreateWebPushSubscriptionCommand createCommand() {
        CreateWebPushSubscriptionCommand.Keys keys = new CreateWebPushSubscriptionCommand.Keys();
        keys.setP256dh("p256dh");
        keys.setAuth("auth");

        CreateWebPushSubscriptionCommand command = new CreateWebPushSubscriptionCommand();
        command.setSiteId("site-id");
        command.setEndpoint("endpoint");
        command.setKeys(keys);
        return command;
    }

    private Site createSite() {
        Site site = new Site();
        site.setId("site-id");
        return site;
    }

    private User createUser() {
        User user = new User();
        user.setId("user-id");
        user.setUsername("user");
        return user;
    }

}
