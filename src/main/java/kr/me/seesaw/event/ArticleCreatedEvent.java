package kr.me.seesaw.event;

public record ArticleCreatedEvent(String categoryId, String articleId, String title, String content) {

}
