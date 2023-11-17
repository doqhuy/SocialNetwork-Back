package instaface.backend.Chat.Controllers;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ImageConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Đăng ký đường dẫn ảo /images/ trỏ đến nơi chứa ảnh
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:D:/PictureSave/");
    }
}
