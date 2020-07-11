package com.lloyd.employee.model;

public class EmployeeSalaryRange {

	private double minSal;
	private double maxSal;

	public EmployeeSalaryRange() {
	}

	public EmployeeSalaryRange(double minSal, double maxSal) {
		this.maxSal = maxSal;
		this.minSal = minSal;

	}

	public double getMinSal() {
		return minSal;
	}

	public void setMinSal(double minSal) {
		this.minSal = minSal;
	}

	public double getMaxSal() {
		return maxSal;
	}

	public void setMaxSal(double maxSal) {
		this.maxSal = maxSal;
	}

	@Override
	public String toString() {
		return "EmployeeSalaryRange [minSal=" + minSal + ", maxSal=" + maxSal + "]";
	}

}
