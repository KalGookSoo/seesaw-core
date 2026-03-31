package kr.me.seesaw.core.authentication;

/**
 * 공개 IP 주소를 추출하기 위한 인터페이스
 */
public interface IpAddressExtractor {

    /**
     * 현재 요청의 공개 IP 주소를 추출합니다.
     *
     * @return 추출된 IP 주소 문자열
     */
    String getCurrentIp();

}
