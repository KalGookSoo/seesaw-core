package kr.me.seesaw.core.support.file;

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
     * 파일의 특정 구간만 읽기 (HTTP Range 요청 응답 등에 사용)
     *
     * @param absolutePath 절대경로
     * @param start        읽기 시작 위치(0-based, inclusive)
     * @param length       읽을 바이트 길이
     * @return 요청한 구간의 파일 데이터
     * @throws IOException 입출력 예외
     */
    ByteArrayInputStream read(String absolutePath, long start, long length) throws IOException;

    /**
     * 파일 삭제
     *
     * @param absolutePath 절대경로
     * @return 삭제 성공 여부
     */
    boolean delete(String absolutePath);

}
