package kr.me.seesaw.core.domain.activity;

import java.util.List;
import kr.me.seesaw.core.domain.article.Article;
import kr.me.seesaw.core.domain.article.persistence.JpaArticleRepository;
import kr.me.seesaw.core.domain.category.Category;
import kr.me.seesaw.core.domain.reply.Reply;
import kr.me.seesaw.core.domain.reply.persistence.JpaReplyRepository;
import kr.me.seesaw.core.domain.site.Site;
import kr.me.seesaw.core.domain.vote.Vote;
import kr.me.seesaw.core.domain.vote.persistence.JpaVoteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@DataJpaTest
class ActivityRepositoryTest {
    @Autowired TestEntityManager em;
    @Autowired JpaArticleRepository articles;
    @Autowired JpaReplyRepository replies;
    @Autowired JpaVoteRepository votes;

    @Test
    void 사용자와_접근가능_카테고리_및_노출_여부로_활동을_제한한다() {
        Site site = new Site(); site.setName("site"); site.setDomainName("activity-test"); em.persist(site);
        Category category = new Category(); category.setSite(site); category.setName("board"); category.setExposed(true); em.persist(category);
        Category other = new Category(); other.setSite(site); other.setName("other"); other.setExposed(true); em.persist(other);
        Article visible = article(category, "alice", true);
        article(category, "bob", true);
        article(category, "alice", false);
        article(other, "alice", true);
        Reply reply = new Reply(); reply.setArticle(visible); reply.setCreatedBy("alice"); reply.setContent("reply"); reply.setExposed(true); em.persist(reply);
        Reply hidden = new Reply(); hidden.setArticle(visible); hidden.setCreatedBy("alice"); hidden.setContent("hidden"); hidden.setExposed(false); em.persist(hidden);
        Vote liked = new Vote(); liked.setReferenceId(visible.getId()); liked.setCreatedBy("alice"); liked.setApproved(true); em.persist(liked);
        Vote hiddenVote = new Vote(); hiddenVote.setReferenceId(hidden.getId()); hiddenVote.setCreatedBy("alice"); hiddenVote.setApproved(true); em.persist(hiddenVote);
        em.flush();
        var page = PageRequest.of(0, 10);
        assertThat(articles.findActivity("alice", List.of(category.getId()), page)).extracting(Article::getId).containsExactly(visible.getId());
        assertThat(replies.findActivity("alice", List.of(category.getId()), page)).extracting(Reply::getId).containsExactly(reply.getId());
        assertThat(votes.findActivity("alice", List.of(category.getId()), page)).extracting(Vote::getId).containsExactly(liked.getId());
        assertThat(articles.findActivity("ali", List.of(category.getId()), page)).isEmpty();
        assertThat(votes.findActivity("bob", List.of(category.getId()), page)).isEmpty();
    }

    private Article article(Category category, String author, boolean exposed) {
        Article article = new Article(); article.setCategory(category); article.setTitle("title"); article.setCreatedBy(author); article.setExposed(exposed); em.persist(article); return article;
    }
}
