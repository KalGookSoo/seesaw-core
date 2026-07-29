package kr.me.seesaw.core.support.file;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.util.FileCopyUtils;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class LocalFileManagerTest {

    private final LocalFileManager fileManager = new LocalFileManager();

    @Test
    @DisplayName("구간을 지정해 파일 일부만 읽는다")
    void readRange(@TempDir Path tempDir) throws Exception {
        // Given
        String absolutePath = tempDir.resolve("range.txt").toString();
        byte[] data = "0123456789".getBytes(StandardCharsets.UTF_8);
        fileManager.write(absolutePath, data);

        // When
        ByteArrayInputStream stream = fileManager.read(absolutePath, 2, 5);
        byte[] result = FileCopyUtils.copyToByteArray(stream);

        // Then
        assertThat(result).isEqualTo("23456".getBytes(StandardCharsets.UTF_8));
    }

    @Test
    @DisplayName("요청한 길이가 파일 끝을 넘어가면 남은 바이트만 읽는다")
    void readRangeBeyondEndOfFile(@TempDir Path tempDir) throws Exception {
        // Given
        String absolutePath = tempDir.resolve("range-eof.txt").toString();
        byte[] data = "0123456789".getBytes(StandardCharsets.UTF_8);
        fileManager.write(absolutePath, data);

        // When
        ByteArrayInputStream stream = fileManager.read(absolutePath, 7, 100);
        byte[] result = FileCopyUtils.copyToByteArray(stream);

        // Then
        assertThat(result).isEqualTo("789".getBytes(StandardCharsets.UTF_8));
    }

}
