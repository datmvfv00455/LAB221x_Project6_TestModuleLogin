package lms.lab221x.ass6.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@ComponentScan(basePackages = "lms.lab221x.ass6")
@EnableWebMvc
public class MvcConfiguration implements WebMvcConfigurer {

    /**
     * Internal resource view resolver.
     *
     * @return the view resolver
     */
    @Bean
    public ViewResolver internalResourceViewResolver() {

        InternalResourceViewResolver resolver;
        resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");
        return resolver;

    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/resources/**")
                .addResourceLocations("/resources/");

    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new LogInterceptor());

        registry.addInterceptor(new AuthorizationInterceptor())
                .addPathPatterns("/home")
                .addPathPatterns("/fisttime-login");

    }

}
