package kr.me.seesaw.service;

import kr.me.seesaw.model.ArticleModel;
import kr.me.seesaw.model.ReplyModel;

import java.util.List;

public interface ArticleQueryService {

    ArticleModel getArticleAggregation(String id);

    List<ReplyModel> getReplies(String articleId);

}
