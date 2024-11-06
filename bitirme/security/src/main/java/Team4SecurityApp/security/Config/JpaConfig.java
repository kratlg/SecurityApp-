package Team4SecurityApp.security.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "Team4SecurityApp.security.Repository")
@EnableTransactionManagement

public class JpaConfig {

}
