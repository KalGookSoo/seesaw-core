package kr.me.seesaw.core.support.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

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
    public ByteArrayInputStream read(String absolutePath, long start, long length) throws IOException {
        Path path = Paths.get(absolutePath);
        try (SeekableByteChannel channel = Files.newByteChannel(path, StandardOpenOption.READ)) {
            channel.position(start);
            ByteBuffer buffer = ByteBuffer.allocate((int) length);
            // SeekableByteChannel#read는 한 번에 버퍼를 다 채우지 않을 수 있어 반복 호출한다.
            while (buffer.hasRemaining() && channel.read(buffer) != -1) ;
            return new ByteArrayInputStream(buffer.array(), 0, buffer.position());
        }
    }

    @Override
    public boolean delete(String absolutePath) {
        return new File(absolutePath).delete();
    }

}
