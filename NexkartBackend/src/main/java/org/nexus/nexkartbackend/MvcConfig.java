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

            String dirName = "user-photos";
            Path userPhotoDir = Paths.get("user-photos");
            String userPhotoDirPath = userPhotoDir.toFile().getAbsolutePath();
            registry.addResourceHandler("/" + dirName + "/**")
                    .addResourceLocations("file:" + userPhotoDirPath + "/");


            String categoryImageName = "./category-images";
            Path categoryImageDir = Paths.get(categoryImageName);
            String categoryImagePath = categoryImageDir.toFile().getAbsolutePath();
            registry.addResourceHandler("/category-images/**")
                    .addResourceLocations("file:" + categoryImagePath + "/");

            String brandLogosDirName = "./brands-logo";
            Path brandLogosDir = Paths.get(brandLogosDirName);
            String brandLogosPath = brandLogosDir.toFile().getAbsolutePath();
            registry.addResourceHandler("/brands-logo/**")
                    .addResourceLocations("file:" + brandLogosPath + "/");



         }


}
