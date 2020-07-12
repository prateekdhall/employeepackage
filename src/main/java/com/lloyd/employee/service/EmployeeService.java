package com.lloyd.employee.service;

import java.math.BigInteger;
import java.util.List;

import com.lloyd.employee.model.Employee;
import com.lloyd.employee.model.EmployeeSalaryRange;

/**
 * This interface list all service methods to perform Employee operations
 * 
 * @author pradhal
 *
 */
public interface EmployeeService {

	/**
	 * Get all employee list
	 * 
	 * @return List<Employee>
	 */
	public List<Employee> getAllEmployeeList();

	/**
	 * update employee details for a place with a given percentage
	 * 
	 * @param place
	 * @param percent
	 * @return List<Employee>
	 */
	public List<Employee> updateEmployee(String place, int percent);

	/**
	 * Get supervisees list by supervisor id
	 * 
	 * @param supervisor
	 * @return List<BigInteger>
	 */
	public List<BigInteger> findSuperviseeByEmpId(int supervisor);

	/**
	 * Get total salary by a specific domain
	 * 
	 * @param domain
	 * @param value
	 * @return Double
	 */
	public Double fetchTotalSalaryByParams(String domain, String value);

	/**
	 * Get max and min salary range by employee title
	 * 
	 * @param title
	 * @return EmployeeSalaryRange
	 */
	public EmployeeSalaryRange getSalRange(String title);

	/**
	 * Get all employee list by place
	 * 
	 * @param place
	 * @return List<Employee>
	 */
	public List<Employee> getByPlace(String place);

}
