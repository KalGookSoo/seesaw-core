package kr.me.seesaw.dto.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@Schema(name = "EventQuery", description = "일정 검색 객체")
public class EventQuery {

    @Schema(description = "카테고리 식별자")
    private String categoryId;

    @Schema(description = "검색 시작 일시")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime start;

    @Schema(description = "검색 종료 일시")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime end;

    @Schema(description = "검색어 (제목/본문)")
    private String query;

}
