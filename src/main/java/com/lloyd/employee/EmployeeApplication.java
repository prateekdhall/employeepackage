package com.lloyd.employee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import com.lloyd.employee.listner.DataInsertFromCSVListner;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableCaching
@EnableTransactionManagement
@EnableSwagger2
public class EmployeeApplication {

	public static void main(String[] args) {
		SpringApplication sa = new SpringApplication(EmployeeApplication.class);
		sa.addListeners(new DataInsertFromCSVListner());
		sa.run(args);
	}

}
