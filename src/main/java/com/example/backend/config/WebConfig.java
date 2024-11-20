@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // すべてのリクエストを index.html にフォワード
        registry.addViewController("/{spring}").setViewName("forward:/index.html");
        registry.addViewController("/**/{spring}").setViewName("forward:/index.html");
        registry.addViewController("/{spring}/**").setViewName("forward:/index.html");
    }
}