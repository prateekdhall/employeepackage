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

/**
 * This class list all methods to perform Employee operations
 * 
 * @author pradhal
 *
 */
@RestController
@RequestMapping("/employee")
public class EmployeeController {
	@Autowired
	private EmployeeService employeeService;
	Logger logger = LogManager.getLogger(EmployeeController.class);
	

	/**
	 * This API fetch all employee list from database
	 * 
	 * @return List<Employee>
	 */
	@ApiOperation(value = "This API fetch all employee list from database")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Fetch data successfully"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@RequestMapping(value = "/findAll", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public List<Employee> getAllEmployeeList() {
		logger.info("EmployeeController: getAllEmployeeList");
		return employeeService.getAllEmployeeList();
	}

	/**
	 * This API return the list of employees for a given place
	 * 
	 * @param place
	 * @return List<Employee>
	 */
	@ApiOperation(value = "This API return the list of employees for a given place ")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Fetch list of employees for a given place successfully"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@RequestMapping(value = "/place/{place}", method = RequestMethod.GET)
	@ResponseBody
	public List<Employee> getEmployeeByPlace(@PathVariable(name = "place") String place) {
		logger.info("EmployeeController: getEmployeeByPlace");
		List<Employee> employee = new ArrayList<Employee>();
		employee = employeeService.getByPlace(place);
		return employee;
	}

	/**
	 * This API return list of all supervisees of a given supervisor
	 * 
	 * @param supervisor
	 * @return List<BigInteger>
	 */
	@ApiOperation(value = "This API return list of all supervisees of a given supervisor ")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Fetch list of all supervisees successfully"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@RequestMapping(value = "/supervisees/{supervisor}", method = RequestMethod.GET)
	@ResponseBody
	public List<BigInteger> getEmployeeSupervisees(@PathVariable(name = "supervisor") int supervisor) {
		logger.info("EmployeeController: getEmployeeSupervisees");
		List<BigInteger> supervisees = null;
		try {
			supervisees = employeeService.findSuperviseeByEmpId(supervisor);
		} catch (Exception ex) {
			logger.error("EmployeeController: getEmployeeSupervisees", ex);
			return supervisees;
		}
		return supervisees;
	}

	/**
	 * This API return Total salary details by business-unit, supervisor, place
	 * 
	 * @param domain
	 * @param value
	 * @return Double
	 */
	@ApiOperation(value = "This API return Total salary details by business-unit, supervisor, place")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Fetch Total salary successfully"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@RequestMapping(value = "/totalSalary/{domain}/{value}", method = RequestMethod.GET)
	@ResponseBody
	public Double getTotalSalaryByParams(@PathVariable(name = "domain") String domain,
			@PathVariable(name = "value") String value) {
		logger.info("EmployeeController: getTotalSalaryByParams");
		double totalSalary = 0;
		try {
			totalSalary = employeeService.fetchTotalSalaryByParams(domain, value);
		} catch (Exception ex) {
			logger.error("EmployeeController: getTotalSalaryByParams", ex);
			return totalSalary;
		}
		return totalSalary;
	}

	/**
	 * This API return salary range for a title
	 * 
	 * @param title
	 * @return String
	 */
	@ApiOperation(value = "This API return salary range for a title")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Fetch salary range for a title successfully"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@RequestMapping(value = "/salaryrange/{title}", method = RequestMethod.GET)
	@ResponseBody
	public String getEmployeeSalaryRange(@PathVariable(name = "title") String title) {
		EmployeeSalaryRange salary = new EmployeeSalaryRange();
		logger.info("EmployeeController: getEmployeeSalaryRange");
		try {
			salary = employeeService.getSalRange(title);
		} catch (Exception ex) {
			logger.error("EmployeeController: getEmployeeSalaryRange", ex);
			return salary.toString();
		}
		return salary.toString();
	}
	
	/**
	 * This API fetch employee list by place and update salary for a given
	 * percentage
	 * 
	 * @param place
	 * @param percent
	 * @return List<Employee>
	 */
	@ApiOperation(value = "This API fetch employee list by place and update salary for a given percentage")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Update Data successfully"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@RequestMapping(value = "/place/{place}/salary/{percent}", method = RequestMethod.PUT)
	@ResponseBody
	public List<Employee> updateEmployeeSalary(@PathVariable(value = "place") String place,
			@PathVariable(value = "percent") int percent) {
		List<Employee> employeeList = new ArrayList<Employee>();
		try {
			logger.info("EmployeeController: updateEmployeeSalary");
			employeeList = employeeService.updateEmployee(place, percent);
		} catch (Exception exception) {
			logger.error("EmployeeController: updateEmployeeSalary", exception);
			return employeeList;
		}
		return employeeList;
	}

}
