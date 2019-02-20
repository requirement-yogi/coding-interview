package hello;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.template.soy.SoyFileSet;
import com.google.template.soy.jssrc.SoyJsSrcOptions;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * This compiles SOY templates from .soy -> .soy.js.
 *
 * They are read from src/main/resources/soy/.
 * They are made available at http://localhost:8080/soy/ (both .soy and .soy.js).
 */
@Configuration
public class SoyMvcConfiguration implements WebMvcConfigurer {

    public static final String SOYFILES_PATH = "target" + File.pathSeparator + "classes" + File.pathSeparator + "soy";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (!registry.hasMappingForPattern("/soy/**")) {
            if (!new File("target").isDirectory()) {
                throw new IllegalStateException("Please execute the program from the root of the project. " +
                        "For example, `java -jar target/project.jar` will work, but NOT `cd target ; java -jar project.war`. " +
                        "Same thing if you're using an IDE.");
            }
            compileSoyToJS();

            // This assumes that the current directory is the root of the project, as checked above.
            registry.addResourceHandler("/soy/**").addResourceLocations(
                    "file:target/classes/soy/");
        }
    }

    public void compileSoyToJS() {
        try {
            File soyTemplatesLocation = new FileSystemResourceLoader().getResource("classpath:/soy/").getFile();
            List<File> files = Lists.newArrayList();
            populateBuilderWithSoyTemplates(files, soyTemplatesLocation);

            SoyFileSet soyFileSet = buildSoyFileSet(files);

            SoyJsSrcOptions options = new SoyJsSrcOptions();
            List<String> src = soyFileSet.compileToJsSrc(options, null);

            writeAsJSFilesInTargetDirectory(files, src);

        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeAsJSFilesInTargetDirectory(List<File> files, List<String> src) throws IOException {
        File target = new File(SOYFILES_PATH);
        if (!target.isDirectory()) {
            target.mkdirs();
        }
        for (int i = 0 ; i < src.size() ; i++) {
            String soyName = files.get(i).getAbsolutePath();
            if (!soyName.contains(SOYFILES_PATH + File.pathSeparator)) {
                throw new IllegalStateException("Files are expected to be seen in the target directory, such as coding-interview/target/classes/soy. But their path is: " + soyName);
            }
            String jsName = StringUtils.substringAfter(soyName, SOYFILES_PATH + File.pathSeparator);
            File jsFile = new File(target, jsName + ".js");
            System.out.println("Writing compiles soy file to: " + jsFile.getAbsolutePath());
            FileUtils.writeStringToFile(jsFile, src.get(i), Charsets.UTF_8);
        }
    }

    private SoyFileSet buildSoyFileSet(List<File> files) {
        SoyFileSet.Builder soyFileSetBuilder = SoyFileSet.builder();
        for (File file : files) {
            soyFileSetBuilder.add(file);
        }
        return soyFileSetBuilder.build();
    }

    private static void populateBuilderWithSoyTemplates(final List<File> soyFiles,
                                                 final File directory) {
        final File[] files = directory.listFiles();
        if (files != null) {
            for (final File file : files) {
                if (file.isFile()) {
                    if (file.getName().endsWith(".soy")) {
                        soyFiles.add(file);
                    }
                } else if (file.isDirectory()) {
                    populateBuilderWithSoyTemplates(soyFiles, file);
                }
            }
        } else {
            throw new IllegalArgumentException(
                    "Failed to populate SoyFileSet.Builder with soy templates from directory [" + directory + "]. Check that it is" + " indeed a directory " +
                            "and not empty!");
        }
    }
}
