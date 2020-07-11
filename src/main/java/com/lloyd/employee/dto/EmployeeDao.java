package com.lloyd.employee.dto;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.lloyd.employee.model.Employee;
import com.lloyd.employee.model.EmployeeSalaryRange;
import com.lloyd.employee.util.QueryUtils;

@Repository
@Transactional
public class EmployeeDao {

	Logger logger = LogManager.getLogger(EmployeeDao.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void save(Employee employee) {
		entityManager.persist(employee);
	}

	@SuppressWarnings("unchecked")
	public List<Employee> getAllEmployeeList() {
		logger.info("EmployeeDao: getAllEmployeeList:: Get all employee list");
		return entityManager.createQuery("from Employee").getResultList();
	}

	@Cacheable(value = "employeeCache", key = "#place")
	@SuppressWarnings("unchecked")
	public List<Employee> findByPlace(String place) {
		logger.info("EmployeeDao: findByPlace:: Get all employee list by place");
		return (List<Employee>) entityManager.createQuery("from Employee where place = :place ")
				.setParameter("place", place).getResultList();
	}

	public EmployeeSalaryRange getSalarayRange(String title) {
		logger.info("EmployeeDao: getSalarayRange:: Get max and min salaray range by employee title");
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<EmployeeSalaryRange> cq = cb.createQuery(EmployeeSalaryRange.class);
		Root<Employee> from = cq.from(Employee.class);

		cq.multiselect(cb.min(from.get("salary")), cb.max(from.get("salary")));

		cq.where(cb.equal(from.get("title"), title));
		return entityManager.createQuery(cq).getSingleResult();
	}

	public List<BigInteger> getSupervisees(int supervisor) {
		logger.info("EmployeeDao: getSupervisees:: Get supervisees list by supervisor id");
		Query query = entityManager.createNativeQuery(QueryUtils.queryForSupervisee(supervisor));
		List<BigInteger> superviseeList = query.getResultList();
		return superviseeList;
	}

	public Double getTotalSalaryByDomain(String domain, String value) {
		logger.info("EmployeeDao: getTotalSalaryByDomain:: Get total salary by a specific domain");
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Double> query = cb.createQuery(Double.class);
		Root<Employee> obj = query.from(Employee.class);
		query.select(cb.sum(obj.get("salary")));
		query.where(cb.equal(obj.get(domain), value));
		return entityManager.createQuery(query).getSingleResult();
	}

	@Cacheable(value = "employeeCache")
	public List<Employee> updateEmployeePercentage(String place, int percent) {
		logger.info(
				"EmployeeDao: updateEmployeePercentage:: update employee details for a place with a given percentage");
		List<Employee> employeeList = findByPlace(place);

		List<Employee> updatedEmployeeList = employeeList.stream().map(emp -> {
			emp.setSalary(emp.getSalary() * percent);
			return emp;
		}).collect(Collectors.toList());

		updatedEmployeeList.forEach(obj -> entityManager.merge(obj));
		return updatedEmployeeList;
	}

}
