package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import java.net.ConnectException;
import java.net.URI;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ProxyController {

    private static final String FRONTEND_HOST = "localhost";
    private static final String FRONTEND_PORT = "8090";
    private static final HttpHeaders CONTENT_TYPE_JSON_HEADERS;

    static {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        CONTENT_TYPE_JSON_HEADERS = HttpHeaders.readOnlyHttpHeaders(headers);
    }

    private final RestTemplate restTemplate;

    @Autowired
    public ProxyController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @RequestMapping(name = "proxyRequest")
    public ResponseEntity<byte[]> proxyRequest(
            HttpMethod method,
            @RequestBody(required = false)
            byte[] body,
            @RequestHeader
            HttpHeaders headers,
            HttpServletRequest req) {
        URI uri = URI.create("http://" + FRONTEND_HOST + ":" + FRONTEND_PORT + req.getRequestURI() + req.getQueryString());
        HttpEntity<byte[]> entity = new HttpEntity<>(body, headers);

        try {
            return restTemplate.exchange(uri, method, entity, byte[].class);
        } catch (HttpClientErrorException ex) {
            return ResponseEntity.status(ex.getStatusCode())
                    .headers(ex.getResponseHeaders())
                    .body(ex.getResponseBodyAsByteArray());
        } catch (ResourceAccessException ex) {
            if (ex.getRootCause() instanceof ConnectException) {
                return ResponseEntity
                        .status(HttpStatus.BAD_GATEWAY)
                        .headers(CONTENT_TYPE_JSON_HEADERS)
                        .body("{\"status\": 502, \"error\": \"bad_gateway\", \"detail\": \"connect error\"}".getBytes());
            }
            throw ex;
        }
    }
}
