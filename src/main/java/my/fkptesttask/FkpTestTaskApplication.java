package my.fkptesttask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.SimpleCommandLinePropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FkpTestTaskApplication {

    public static void main(String[] args) {
        PropertySource<?> propertySource = new SimpleCommandLinePropertySource(args);
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext();
        context.getEnvironment().getPropertySources().addFirst(propertySource);
        context.refresh();
        SpringApplication.run(FkpTestTaskApplication.class, args);
    }
}
