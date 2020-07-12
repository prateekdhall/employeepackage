package com.lloyd.employee.service;

import java.math.BigInteger;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lloyd.employee.dto.EmployeeDao;
import com.lloyd.employee.model.Employee;
import com.lloyd.employee.model.EmployeeSalaryRange;

/**
 * This class list all service methods to perform Employee operations
 * 
 * @author pradhal
 *
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

	Logger logger = LogManager.getLogger(EmployeeServiceImpl.class);

	@Autowired
	private EmployeeDao employeeDao;

	/**
	 * Get all employee list
	 * 
	 * @return List<Employee>
	 */
	@Override
	public List<Employee> getAllEmployeeList() {
		logger.info("EmployeeServiceImpl: getAllEmployeeList:: Get all employee list");
		return employeeDao.getAllEmployeeList();
	}

	/**
	 * Get all employee list by place
	 * 
	 * @param place
	 * @return List<Employee>
	 */
	@Override
	public List<Employee> getByPlace(String place) {
		logger.info("EmployeeServiceImpl: getByPlace:: Get all employee by place");
		return employeeDao.findByPlace(place);
	}

	/**
	 * update employee details for a place with a given percentage
	 * 
	 * @param place
	 * @param percent
	 * @return List<Employee>
	 */
	@Override
	public List<Employee> updateEmployee(String place, int percent) {
		logger.info(
				"EmployeeServiceImpl: updateEmployee:: update employee details for a place with a given percentage");
		return employeeDao.updateEmployeePercentage(place, percent);
	}

	/**
	 * Get supervisees list by supervisor id
	 * 
	 * @param supervisor
	 * @return List<BigInteger>
	 */
	@Override
	public List<BigInteger> findSuperviseeByEmpId(int supervisor) {
		logger.info("EmployeeServiceImpl: findSuperviseeByEmpId:: find a list of supervisee for a supervisor");
		return employeeDao.getSupervisees(supervisor);
	}

	/**
	 * Get total salary by a specific domain
	 * 
	 * @param domain
	 * @param value
	 * @return Double
	 */
	@Override
	public Double fetchTotalSalaryByParams(String domain, String value) {
		logger.info("EmployeeServiceImpl: fetchTotalSalaryByParams:: find total salary for given domain");
		return employeeDao.getTotalSalaryByDomain(domain, value);
	}

	/**
	 * Get max and min salary range by employee title
	 * 
	 * @param title
	 * @return EmployeeSalaryRange
	 */
	@Override
	public EmployeeSalaryRange getSalRange(String title) {
		logger.info("EmployeeServiceImpl: getSalRange:: get salary Range for a given title");
		return employeeDao.getSalarayRange(title);
	}

}
