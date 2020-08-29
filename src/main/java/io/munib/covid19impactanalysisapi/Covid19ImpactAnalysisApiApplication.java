package io.munib.covid19impactanalysisapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import io.munib.covid19impactanalysisapi.security.repository.MyUserDetailsRepository;

@SpringBootApplication
@EnableScheduling
@EnableJpaRepositories(basePackageClasses = MyUserDetailsRepository.class)
public class Covid19ImpactAnalysisApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(Covid19ImpactAnalysisApiApplication.class, args);
	}

}
