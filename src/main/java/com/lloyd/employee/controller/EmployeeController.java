package com.lloyd.employee.controller;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.lloyd.employee.model.Employee;
import com.lloyd.employee.model.EmployeeSalaryRange;
import com.lloyd.employee.service.EmployeeService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
	Logger logger = LogManager.getLogger(EmployeeController.class);
	@Autowired
	private EmployeeService employeeService;

	@ApiOperation(value = "This API fetch employee list by place and update salary for a given percentage")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Update Data successfully"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@RequestMapping(value = "/place/{place}/salary/{percent}", method = RequestMethod.PUT)
	@ResponseBody
	public List<Employee> updateEmployee(@PathVariable(value = "place") String place,
			@PathVariable(value = "percent") int percent) {
		logger.info("EmployeeController: updateEmployee");
		List<Employee> employeeList = new ArrayList<Employee>();
		try {
			employeeList = employeeService.updateEmployee(place, percent);
		} catch (Exception exception) {
			logger.error("EmployeeController: updateEmployee", exception);
			return employeeList;
		}
		return employeeList;
	}

	@ApiOperation(value = "This API fetch all employee list from database")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Fetch data successfully"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@RequestMapping(value = "/findAll", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public List<Employee> getAllEmployeeList() {
		logger.info("EmployeeController: getAllEmployeeList");
		return employeeService.getAllEmployeeList();
	}

	@ApiOperation(value = "This API return the list of employees for a given place ")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Fetch list of employees for a given place successfully"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@RequestMapping(value = "/place/{place}", method = RequestMethod.GET)
	@ResponseBody
	public List<Employee> getByPlace(@PathVariable(name = "place") String place) {
		logger.info("EmployeeController: getByPlace");
		List<Employee> employee = new ArrayList<Employee>();
		employee = employeeService.getByPlace(place);
		return employee;
	}

	@ApiOperation(value = "This API return list of all supervisees of a given supervisor ")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Fetch list of all supervisees successfully"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@RequestMapping(value = "supervisees/{supervisor}", method = RequestMethod.GET)
	@ResponseBody
	public List<BigInteger> getSupervisees(@PathVariable(name = "supervisor") int supervisor) {
		logger.info("EmployeeController: getSupervisees");
		List<BigInteger> supervisees = null;
		try {
			supervisees = employeeService.findSuperviseeByEmpId(supervisor);
		} catch (Exception ex) {
			logger.error("EmployeeController: getSupervisees", ex);
			return supervisees;
		}
		return supervisees;
	}

	@ApiOperation(value = "This API return Total salary details by business-unit, supervisor, place")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Fetch Total salary successfully"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@RequestMapping(value = "/totalSalary/{domain}/{value}", method = RequestMethod.GET)
	@ResponseBody
	public Double findTotalSalaryByParams(@PathVariable(name = "domain") String domain,
			@PathVariable(name = "value") String value) {
		logger.info("EmployeeController: findTotalSalaryByParams");
		double totalSalary = 0;
		try {
			totalSalary = employeeService.fetchTotalSalaryByParams(domain, value);
		} catch (Exception e) {
			logger.error("EmployeeController: findTotalSalaryByParams", e);
			return totalSalary;
		}
		return totalSalary;
	}

	@ApiOperation(value = "This API return salary range for a title")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Fetch salary range for a title successfully"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@RequestMapping(value = "salaryrange/{title}", method = RequestMethod.GET)
	@ResponseBody
	public String getSalRange(@PathVariable(name = "title") String title) {
		logger.info("EmployeeController: getSalRange");
		EmployeeSalaryRange salary = new EmployeeSalaryRange();
		try {
			salary = employeeService.getSalRange(title);
		} catch (Exception ex) {
			logger.error("EmployeeController: getSalRange", ex);
			return salary.toString();
		}
		return salary.toString();
	}

}
