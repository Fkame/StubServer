package ni.shi.app.utils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Component
public class EndpointsListener implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext applicationContext = event.getApplicationContext();
        System.out.println("Endpoints:");
        String template = "Endpoint: %s || Controller: %s";
        applicationContext.getBean(RequestMappingHandlerMapping.class).getHandlerMethods()
                .forEach((endpoint, controller) ->
                                System.out.println(String.format(template, endpoint, controller)));
    }
}
