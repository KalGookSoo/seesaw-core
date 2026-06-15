package kr.me.seesaw.command;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "게시글 이동 커맨드")
public record MoveArticleCommand(
        @Parameter(description = "대상 카테고리 식별자", required = true)
        @Schema(description = "대상 카테고리 식별자", example = "UUID")
        @NotBlank
        @NotNull
        String categoryId
) {

}
