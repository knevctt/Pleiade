package app.Pleiade;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"app.Pleiade"})
@EntityScan("app.Pleiade.Entity")
@EnableJpaRepositories("app.Pleiade.Repository")
public class PleiadeApplication {

	public static void main(String[] args) {

		SpringApplication.run(PleiadeApplication.class, args);
	}

}
