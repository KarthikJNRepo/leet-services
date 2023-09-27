package com.wissen.leetcode.score.calc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDataDTO {
	private Integer empId;
	private String userName;
	private String empEmail;
	private int missedEntries;

}