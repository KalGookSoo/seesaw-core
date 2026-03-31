package kr.me.seesaw.core.file;

import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * 파일 관리
 */
public interface FileManager {

    /**
     * 파일 쓰기
     *
     * @param absolutePath 절대경로
     * @param data         파일 데이터
     * @throws IOException 입출력 예외
     */
    void write(String absolutePath, byte[] data) throws IOException;

    /**
     * 파일 읽기
     *
     * @param absolutePath 절대경로
     * @return 파일 데이터
     * @throws IOException 입출력 예외
     */
    ByteArrayInputStream read(String absolutePath) throws IOException;

    /**
     * 파일 삭제
     *
     * @param absolutePath 절대경로
     * @return 삭제 성공 여부
     */
    boolean delete(String absolutePath);

}
