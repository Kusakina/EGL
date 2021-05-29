package egl.client;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableConfigurationProperties
@EnableTransactionManagement
@EntityScan(basePackages = {"egl.client.model"})
public class SpringClientApplication {

	public static void main(String[] args) {
		Application.launch(JavaFxClientApplication.class, args);
	}

}
