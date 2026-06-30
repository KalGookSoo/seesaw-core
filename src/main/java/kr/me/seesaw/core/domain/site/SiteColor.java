package kr.me.seesaw.core.domain.site;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.regex.Pattern;

import static lombok.AccessLevel.PROTECTED;

/**
 * 사이트 색상
 */
@Embeddable
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = PROTECTED)
public final class SiteColor implements Serializable {

    public static final String DEFAULT_THEME_COLOR = "#cc4202";

    public static final String DEFAULT_BACKGROUND_COLOR = "#ffffff";

    private static final Pattern HEX_COLOR_PATTERN = Pattern.compile("^#[0-9a-fA-F]{6}$");

    private String value;

    private SiteColor(String value) {
        this.value = normalize(value);
    }

    public static SiteColor of(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("사이트 색상은 비어 있을 수 없습니다.");
        }
        if (!HEX_COLOR_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("사이트 색상은 #RRGGBB 형식이어야 합니다. value: " + value);
        }
        return new SiteColor(value);
    }

    public static SiteColor themeDefault() {
        return new SiteColor(DEFAULT_THEME_COLOR);
    }

    public static SiteColor backgroundDefault() {
        return new SiteColor(DEFAULT_BACKGROUND_COLOR);
    }

    public static SiteColor themeOrDefault(String value) {
        return value == null || value.isBlank() ? themeDefault() : of(value);
    }

    public static SiteColor backgroundOrDefault(String value) {
        return value == null || value.isBlank() ? backgroundDefault() : of(value);
    }

    private static String normalize(String value) {
        return value.trim().toLowerCase();
    }

    @Override
    public String toString() {
        return value;
    }

}
