package kr.me.seesaw.search;

import kr.me.seesaw.domain.vo.CategoryType;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Data
public class ArticleSearch {

    private String categoryId;

    private CategoryType categoryType;

    private ViewType viewType = ViewType.TABLE;

    private String keyField;

    private String keyWord;

    @Getter
    @RequiredArgsConstructor
    public enum ViewType {
        TABLE("목록"),
        CARD("카드");

        private final String description;
    }

}
