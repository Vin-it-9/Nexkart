package org.nexus.nexkartbackend;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class MvcConfig implements WebMvcConfigurer {


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        exposeDirectory("user-photos", registry);
        exposeDirectory("./category-images", registry);
        exposeDirectory("./brands-logo", registry);
        exposeDirectory("./product-images", registry);
    }

    private void exposeDirectory(String pathPattern, ResourceHandlerRegistry registry) {
        Path path = Paths.get(pathPattern);
        String absolutePath = path.toFile().getAbsolutePath();

        String logicalPath = pathPattern.replace("./", "") + "/**";

        registry.addResourceHandler(logicalPath)
                .addResourceLocations("file:/" + absolutePath + "/");
    }


//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        exposeDirectory("user-photos", registry);
//        exposeDirectory("category-images", registry);
//        exposeDirectory("brands-logo", registry);
//        exposeDirectory("product-images", registry);
//    }
//
//    private void exposeDirectory(String dirName, ResourceHandlerRegistry registry) {
//        Path path = Paths.get(dirName);
//        String absolutePath = path.toFile().getAbsolutePath();
//        String logicalPath = "/" + dirName + "/**";
//        registry.addResourceHandler(logicalPath)
//                .addResourceLocations("file:" + absolutePath + "/");
//    }



}
