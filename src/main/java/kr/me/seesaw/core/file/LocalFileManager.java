package kr.me.seesaw.core.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class LocalFileManager implements FileManager {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void write(String absolutePath, byte[] data) throws IOException {
        logger.info("파일 쓰기: absolutePath={}", absolutePath);
        File file = new File(absolutePath);
        if (file.getParentFile() != null && !file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        FileCopyUtils.copy(data, file);
    }

    @Override
    public ByteArrayInputStream read(String absolutePath) throws IOException {
        Path path = Paths.get(absolutePath);
        try (InputStream inputStream = Files.newInputStream(path)) {
            byte[] bytes = FileCopyUtils.copyToByteArray(inputStream);
            return new ByteArrayInputStream(bytes);
        }
    }

    @Override
    public boolean delete(String absolutePath) {
        return new File(absolutePath).delete();
    }

}
