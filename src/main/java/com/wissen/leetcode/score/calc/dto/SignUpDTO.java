package com.wissen.leetcode.score.calc.dto;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_data", uniqueConstraints = @UniqueConstraint(columnNames = "emp_id"))
public class SignUpDTO {

	@NotNull(message = "Emp Id is Empty")
	@Column(unique = true)
	private int empId;

	@NotNull(message = "Emp Id is Empty")
	private String userName;

	@NotNull(message = "Emp Id is Empty")
	private String password;

	@NotNull
	@Email(message = "invalid Email address")
	private String empEmail;

}
