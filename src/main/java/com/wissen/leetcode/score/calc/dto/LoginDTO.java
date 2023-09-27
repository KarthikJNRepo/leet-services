package com.wissen.leetcode.score.calc.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoginDTO {

	private int empId;
	@JsonInclude(Include.NON_NULL)
	private String password;
	private String message;
	private String userName;
}
