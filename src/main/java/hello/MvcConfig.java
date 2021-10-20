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
        checkIfFileExistsInClasspath("templates/home.html", "Is the folder src/main/resource considered a resource by your IDE?");
        checkIfFileExistsInClasspath("static/js/app.js", "Did you run `npm run watch` in parallel?");

        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/").setViewName("home");
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