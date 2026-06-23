package kr.me.seesaw.event;

public record ReplyCreatedEvent(String articleId, String replyId, String content) {

}
