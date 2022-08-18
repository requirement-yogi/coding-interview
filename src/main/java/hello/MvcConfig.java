package hello;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {
        checkIfFileExistsInClasspath("templates/users.html", "Is the folder src/main/resources considered as a resource by your IDE? Did you run `mvn clean install`?");
        checkIfFileExistsInClasspath("static/js/app.js", "Did you run `npm run watch` in parallel?");

        registry.addViewController("/users").setViewName("users");
        registry.addViewController("/requirements").setViewName("traceability-matrix-react");
        registry.addViewController("/requirements/{key}").setViewName("requirement-detail");
        registry.addViewController("/requirements-vanilla").setViewName("traceability-matrix-vanilla");
        registry.addViewController("/").setViewName("users");
        registry.addViewController("/hello").setViewName("hello");
        registry.addViewController("/login").setViewName("login");
    }

    private void checkIfFileExistsInClasspath(String expectedResource, String message) {
        URL resource = this.getClass().getClassLoader().getResource(expectedResource);
        if (resource == null) {
            throw new RuntimeException(message + " The file target/classes/" + expectedResource + " is missing.");
        }
    }

}