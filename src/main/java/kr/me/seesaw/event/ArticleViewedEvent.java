package kr.me.seesaw.event;

public record ArticleViewedEvent(String articleId, String viewerIp, String viewerUsername) {

}
