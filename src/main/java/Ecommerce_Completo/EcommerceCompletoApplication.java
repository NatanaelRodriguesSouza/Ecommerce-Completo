package Ecommerce_Completo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@ComponentScan(basePackages = "Ecommerce_Completo")
@EnableJpaRepositories(basePackages = {"Ecommerce_Completo.repository"})
@EnableTransactionManagement
public class EcommerceCompletoApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceCompletoApplication.class, args);
	}

}
