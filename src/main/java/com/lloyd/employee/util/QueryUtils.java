package com.lloyd.employee.util;

public class QueryUtils {

	public static String queryForSupervisee(int supervisor) {

		return "WITH RECURSIVE temp_emp AS\r\n" + "(\r\n"
				+ "    SELECT e.employee_id from employee as e WHERE e.supervisor_id=" + supervisor + "\r\n"
				+ "    UNION	\r\n" + "    SELECT ee.employee_id FROM temp_emp 	\r\n"
				+ "    INNER JOIN employee AS ee 	\r\n" + "    ON temp_emp.employee_id = ee.supervisor_id	\r\n"
				+ ")SELECT * FROM temp_emp;";
	}
}
