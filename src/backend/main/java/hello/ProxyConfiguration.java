package hello;

import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Configuration
public class ProxyConfiguration {
    @Bean
    protected WebMvcRegistrations webMvcRegistrationsForLocal() {
        return new WebMvcRegistrations() {
            @Override
            public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
                return new RequestMappingHandlerFallbackMapping();
            }
        };
    }

    static class RequestMappingHandlerFallbackMapping extends RequestMappingHandlerMapping {
        @Override
        protected HandlerMethod lookupHandlerMethod(String lookupPath, HttpServletRequest request) throws Exception {
            HandlerMethod handlerMethod = super.lookupHandlerMethod(lookupPath, request);

            if (handlerMethod == null) {
                List<HandlerMethod> handlerMethods = super.getHandlerMethodsForMappingName("proxyRequest");

                if (handlerMethods != null && !handlerMethods.isEmpty()) {
                    return handlerMethods.get(0);
                }
            }

            return handlerMethod;
        }
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder().build();
    }
}
