package kr.me.seesaw.event.listener;

import kr.me.seesaw.domain.Article;
import kr.me.seesaw.domain.View;
import kr.me.seesaw.event.ArticleViewedEvent;
import kr.me.seesaw.repository.ViewRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ArticleViewEventListener {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final ViewRepository viewRepository;

    public ArticleViewEventListener(ViewRepository viewRepository) {
        this.viewRepository = viewRepository;
    }

    @EventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void onArticleViewed(ArticleViewedEvent event) {
        logger.debug("게시글 조회 이벤트 처리: {}", event);
        View view = new View();
        Article article = new Article();
        article.setId(event.articleId());
        view.setArticle(article);
        viewRepository.save(view);
    }

}
