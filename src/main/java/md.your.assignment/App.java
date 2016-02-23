package md.your.assignment;

import md.your.assignment.persistence.PhrasesFileRepositoryImpl;
import md.your.assignment.persistence.PhrasesRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ResourceLoader;

/**
 * Created by Martin on 2/21/2016.
 */
@SpringBootApplication
public class App {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    /**
     * Constructs phrases repository file implementation.
     *
     * @param resourceLoader the resource loader
     * @return the phrases repository
     */
    @Bean
    public PhrasesRepository phrasesRepository(ResourceLoader resourceLoader){
        return new PhrasesFileRepositoryImpl("phrases", resourceLoader);
    }
}