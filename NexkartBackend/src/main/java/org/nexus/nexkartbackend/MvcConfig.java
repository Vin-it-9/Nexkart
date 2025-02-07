package org.nexus.nexkartbackend;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class MvcConfig implements WebMvcConfigurer {


//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        exposeDirectory("user-photos", registry);
//        exposeDirectory("./category-images", registry);
//        exposeDirectory("./brands-logo", registry);
//        exposeDirectory("./product-images", registry)
//    }
//
//
//    private void exposeDirectory(String pathPattern, ResourceHandlerRegistry registry) {
//        Path path = Paths.get(pathPattern);
//        String absolutePath = path.toFile().getAbsolutePath();
//
//        String logicalPath = pathPattern.replace("/", "") + "/**";
//
//        registry.addResourceHandler(logicalPath)
//                .addResourceLocations("file:/" + absolutePath + "/");
//    }


//
//    @Override
//        public void addResourceHandlers(ResourceHandlerRegistry registry) {
//
//            String dirName = "user-photos";
//            Path userPhotoDir = Paths.get("user-photos");
//            String userPhotoDirPath = userPhotoDir.toFile().getAbsolutePath();
//            registry.addResourceHandler("/" + dirName + "/**")
//                    .addResourceLocations("file:" + userPhotoDirPath + "/");
//
//
//            String categoryImageName = "./category-images";
//            Path categoryImageDir = Paths.get(categoryImageName);
//            String categoryImagePath = categoryImageDir.toFile().getAbsolutePath();
//            registry.addResourceHandler("/category-images/**")
//                    .addResourceLocations("file:" + categoryImagePath + "/");
//
//            String brandLogosDirName = "./brands-logo";
//            Path brandLogosDir = Paths.get(brandLogosDirName);
//            String brandLogosPath = brandLogosDir.toFile().getAbsolutePath();
//            registry.addResourceHandler("/brands-logo/**")
//                    .addResourceLocations("file:" + brandLogosPath + "/");
//
//
//            String productMainImageDirname = "./product-images";
//            Path productMainImageDir = Paths.get(productMainImageDirname);
//            String productMainImagePath = productMainImageDir.toFile().getAbsolutePath();
//            registry.addResourceHandler("/product-images/**")
//                    .addResourceLocations("file:" + productMainImagePath + "/");
//
//
//
//         }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        exposeDirectory("user-photos", registry);
        exposeDirectory("category-images", registry);
        exposeDirectory("brands-logo", registry);
        exposeDirectory("product-images", registry);
    }

    private void exposeDirectory(String dirName, ResourceHandlerRegistry registry) {
        Path path = Paths.get(dirName);
        String absolutePath = path.toFile().getAbsolutePath();
        String logicalPath = "/" + dirName + "/**";
        registry.addResourceHandler(logicalPath)
                .addResourceLocations("file:" + absolutePath + "/");
    }



}
