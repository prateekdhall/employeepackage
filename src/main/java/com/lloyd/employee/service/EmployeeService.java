package com.lloyd.employee.service;

import java.math.BigInteger;
import java.util.List;

import com.lloyd.employee.model.Employee;
import com.lloyd.employee.model.EmployeeSalaryRange;

public interface EmployeeService {

	public List<Employee> getAllEmployeeList();

	public List<Employee> updateEmployee(String place, int percent);

	public List<BigInteger> findSuperviseeByEmpId(int supervisor);

	public Double fetchTotalSalaryByParams(String domain, String value);

	public EmployeeSalaryRange getSalRange(String title);

	public List<Employee> getByPlace(String place);

}
