package com.lloyd.employee;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.lloyd.employee.service.EmployeeService;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableCaching
@EnableTransactionManagement
@EnableSwagger2
public class EmployeeApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeApplication.class, args);
	}

}

@Component
class EmployeeDataInsertFromCSV implements CommandLineRunner {
	Logger logger = LogManager.getLogger(EmployeeApplication.class);
	@Autowired
	private EmployeeService employeeService;

	@Value("${employeepackage.filepath}")
	private String importFilePath;

	@Override
	public void run(String... args) throws Exception {
		logger.info("EmployeeDataInsertFromCSV::insertCSVEmployeeData");
		employeeService.insertCSVEmployeeData(importFilePath);
	}
}
