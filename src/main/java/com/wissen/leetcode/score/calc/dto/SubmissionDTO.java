package com.wissen.leetcode.score.calc.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SubmissionDTO {
		
	@NotNull(message = "Emp Id is Empty")
	private Integer empId;
	
	@Email(message="invalid Email address")
	private String empEmail;
	
	@NotNull(message="User name should not be null")
	private String userName;
	
	@NotNull(message="week should not be null")
	private Integer week;
	
	@NotNull(message="year should not be null")
	private Integer year;
	
	@NotNull(message = "leetranking should not be null")
	private Integer leetRanking;
	
	private String comments;
	
	@NotNull(message = "should not be null")
	@Min(value = 0, message = "value should be positive")
	@Max(value = 700, message = "value should be below 700")
	private Integer easy;
	
	@NotNull(message = "medium program should not be null")
	@Min(value = 0, message = "value should be positive")
	@Max(value = 700, message = "value should be below 700")
	private Integer medium;
	
	@NotNull(message = "hard program should not be null")
	@Min(value = 0, message = "value should be positive")
	@Max(value = 700, message = "value should be below 700")
	private Integer hard;

}
