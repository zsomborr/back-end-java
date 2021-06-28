package com.codecool.peermentoringbackend;

import com.codecool.peermentoringbackend.entity.ProjectEntity;
import com.codecool.peermentoringbackend.entity.TechnologyEntity;
import com.codecool.peermentoringbackend.entity.UserEntity;
import com.codecool.peermentoringbackend.repository.ProjectTagRepository;
import com.codecool.peermentoringbackend.repository.TechnologyTagRepository;
import com.codecool.peermentoringbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;

@SpringBootApplication
public class PeerMentoringBackEndApplication {

    private TechnologyTagRepository technologyTagRepository;
    private UserRepository userRepository;
    private ProjectTagRepository projectTagRepository;

    @Autowired
    public PeerMentoringBackEndApplication(TechnologyTagRepository technologyTagRepository, UserRepository userRepository, ProjectTagRepository projectTagRepository) {
        this.technologyTagRepository = technologyTagRepository;
        this.userRepository = userRepository;
        this.projectTagRepository = projectTagRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(PeerMentoringBackEndApplication.class, args);
    }

    @Bean
    @Profile("production")
    public CommandLineRunner init() {
        return args -> {

            TechnologyEntity python = TechnologyEntity.builder().technologyTag("Python").build();
            TechnologyEntity flask = TechnologyEntity.builder().technologyTag("Flask").build();
            TechnologyEntity jinja = TechnologyEntity.builder().technologyTag("Jinja2").build();
            TechnologyEntity thymeleaf = TechnologyEntity.builder().technologyTag("thymeleaf").build();
            TechnologyEntity html = TechnologyEntity.builder().technologyTag("HTML").build();
            TechnologyEntity css = TechnologyEntity.builder().technologyTag("CSS").build();
            TechnologyEntity javaScript = TechnologyEntity.builder().technologyTag("JavaScript").build();
            TechnologyEntity postgreSQL = TechnologyEntity.builder().technologyTag("PostgreSQL").build();
            TechnologyEntity java = TechnologyEntity.builder().technologyTag("Java").build();
            TechnologyEntity javaFx = TechnologyEntity.builder().technologyTag("JavaFX").build();
            TechnologyEntity test = TechnologyEntity.builder().technologyTag("Test").build();
            TechnologyEntity jdbc = TechnologyEntity.builder().technologyTag("JDBC").build();
            TechnologyEntity spring = TechnologyEntity.builder().technologyTag("Spring").build();
            TechnologyEntity hibernate = TechnologyEntity.builder().technologyTag("Hibernate").build();
            TechnologyEntity jpa = TechnologyEntity.builder().technologyTag("JPA").build();
            TechnologyEntity react = TechnologyEntity.builder().technologyTag("React").build();
            TechnologyEntity lombok = TechnologyEntity.builder().technologyTag("Lombok").build();
            TechnologyEntity heroku = TechnologyEntity.builder().technologyTag("Heroku").build();
            TechnologyEntity netlify = TechnologyEntity.builder().technologyTag("Netlify").build();
            TechnologyEntity linux = TechnologyEntity.builder().technologyTag("Linux").build();
            TechnologyEntity bash = TechnologyEntity.builder().technologyTag("Bash").build();
            TechnologyEntity aws = TechnologyEntity.builder().technologyTag("AWS").build();
            TechnologyEntity docker = TechnologyEntity.builder().technologyTag("docker").build();
            TechnologyEntity kubernetes = TechnologyEntity.builder().technologyTag("Kubernetes").build();
            TechnologyEntity helm = TechnologyEntity.builder().technologyTag("Helm").build();
            TechnologyEntity jenkins = TechnologyEntity.builder().technologyTag("Jenkins").build();
            TechnologyEntity gitlab = TechnologyEntity.builder().technologyTag("Gitlab CI").build();
            TechnologyEntity terraform = TechnologyEntity.builder().technologyTag("Terraform").build();
            TechnologyEntity csharp = TechnologyEntity.builder().technologyTag("C#").build();
            TechnologyEntity aspNet = TechnologyEntity.builder().technologyTag("ASP.NET").build();
            TechnologyEntity php = TechnologyEntity.builder().technologyTag("PHP").build();
            TechnologyEntity android = TechnologyEntity.builder().technologyTag("Android").build();

            technologyTagRepository.saveAll(Arrays.asList(python,
                    flask,
                    postgreSQL,
                    java,
                    jdbc,
                    jpa,
                    jinja,
                    thymeleaf,
                    hibernate,
                    spring,
                    lombok,
                    html,
                    css,
                    javaScript,
                    javaFx,
                    test,
                    react,
                    heroku,
                    netlify,
                    csharp,
                    android,
                    aspNet,
                    php,
                    linux,
                    bash,
                    aws,
                    docker,
                    kubernetes,
                    helm,
                    jenkins,
                    gitlab,
                    terraform));

            UserEntity testUser = UserEntity.builder()
                    .firstName("test")
                    .lastName("test")
                    .email("test@codecool.com")
                    .city("Budapest")
                    .country("Hungary")
                    .username("test")
                    .password("{bcrypt}$2a$10$/A5s3AjcKnRem6lS3uwn5.hV.xZzqy6SK8mn4uRyBQXMj5RcHIUG6")
                    .build();
            userRepository.save(testUser);

            ProjectEntity askMate = ProjectEntity.builder().projectTag("Ask Mate").build();
            ProjectEntity sixHandshakes = ProjectEntity.builder().projectTag("Six Handshakes").build();
            ProjectEntity hangman = ProjectEntity.builder().projectTag("Hangman").build();

            projectTagRepository.saveAll(Arrays.asList(askMate, sixHandshakes, hangman));

        };
    }

}
