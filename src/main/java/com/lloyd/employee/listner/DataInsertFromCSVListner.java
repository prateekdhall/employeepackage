package com.lloyd.employee.listner;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.util.ResourceUtils;

import com.lloyd.employee.dto.EmployeeDao;
import com.lloyd.employee.model.Employee;

/**
 * This class insert data from CSV to database
 * @author pradhal
 *
 */
public class DataInsertFromCSVListner implements ApplicationListener<ApplicationStartedEvent> {
	Logger logger = LogManager.getLogger(DataInsertFromCSVListner.class);

	@Override
	public void onApplicationEvent(ApplicationStartedEvent event) {
		try {
			logger.info("Inside DataInsertFromCSVListner :: onApplicationEvent");
			EmployeeDao empDao = event.getApplicationContext().getBean(EmployeeDao.class);
			File file = ResourceUtils.getFile("classpath:importcsv/EmployeeData.csv");
			if (file.exists()) {
				List<String> lines = Files.readAllLines(file.toPath());
				List<Employee> listEmp = lines.stream().map(mapToEmpObject).collect(Collectors.toList());
				listEmp.forEach(emp -> empDao.save(emp));
			}
		} catch (IOException io) {
			logger.error("EmployeeData csv file is missed");
		}
	}

	/**
	 * This method convert line object to Employee object
	 */
	private Function<String, Employee> mapToEmpObject = (line) -> {
		String[] data = line.split(",");
		Employee emp = new Employee();
		emp.setEmployeeId(data[0]);
		emp.setEmployeeName(data[1]);
		emp.setTitle(data[2]);
		emp.setBusinessUnit(data[3]);
		emp.setPlace(data[4]);
		emp.setSupervisorId(data[5]);
		emp.setCompetency(data[6]);
		emp.setSalary(Double.parseDouble(data[7]));
		return emp;
	};
}
