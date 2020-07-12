package com.lloyd.employee.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.lloyd.employee.EmployeeApplication;
import com.lloyd.employee.model.EmployeeSalaryRange;
import com.lloyd.employee.service.EmployeeService;

@ContextConfiguration(classes = EmployeeApplication.class)
@WebMvcTest(controllers = EmployeeController.class)
public class EmployeeControllerTest {

	@MockBean
	EmployeeService employeeService;

	@MockBean
	EmployeeSalaryRange employeeSalaryRange;

	@Autowired
	private MockMvc mockmvc;

	@Test
	public void testGetEmployeeByPlace() throws Exception {
		mockmvc.perform(get("/employee/place/{place}", "Gurgaon")).andExpect(status().isOk());
	}

	@Test
	public void testGetAllEmployeeList() throws Exception {
		mockmvc.perform(get("/employee/findAll")).andExpect(status().isOk());
	}

	@Test
	public void testGetEmployeeSupervisees() throws Exception {
		List<BigInteger> resonse = new ArrayList<BigInteger>();
		resonse.add(new BigInteger("4"));
		resonse.add(new BigInteger("40"));

		given(this.employeeService.findSuperviseeByEmpId(110011)).willReturn(resonse);

		mockmvc.perform(get("/employee/supervisees/{supervisor}", 110011)).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("[4,40]"));

	}

	@Test
	public void testGetTotalSalaryByParams() throws Exception {
		mockmvc.perform(get("/employee/totalSalary/{domain}/{value}", "businessUnit", "DELEVIRY"))
				.andExpect(status().isOk());
	}

}
