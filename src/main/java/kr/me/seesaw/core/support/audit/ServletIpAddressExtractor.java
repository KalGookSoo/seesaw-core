package kr.me.seesaw.core.support.audit;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class ServletIpAddressExtractor implements IpAddressExtractor {

    @Override
    public String getCurrentIp() {
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                HttpServletRequest request = attributes.getRequest();

                String ip = request.getHeader("X-Forwarded-For");
                if (ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip)) {
                    int commaIndex = ip.indexOf(',');
                    if (commaIndex > 0) {
                        return ip.substring(0, commaIndex).trim();
                    }
                    return ip.trim();
                }

                String[] headers = {"X-Real-IP", "Proxy-Client-IP", "WL-Proxy-Client-IP", "HTTP_X_FORWARDED_FOR",
                        "HTTP_X_FORWARDED", "HTTP_X_CLUSTER_CLIENT_IP", "HTTP_CLIENT_IP", "HTTP_FORWARDED_FOR",
                        "HTTP_FORWARDED", "HTTP_VIA", "REMOTE_ADDR"};

                for (String header : headers) {
                    ip = request.getHeader(header);
                    if (ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip)) {
                        return ip.trim();
                    }
                }

                return request.getRemoteAddr();
            }
        } catch (Exception e) {
            return "UNKNOWN";
        }
        return "UNKNOWN";
    }

}
