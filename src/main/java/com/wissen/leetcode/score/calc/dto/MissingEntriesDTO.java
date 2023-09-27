package com.wissen.leetcode.score.calc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MissingEntriesDTO {
	private int empId;
	private String empEmail;
	private String userName;
	private int missedWeek;
	private int missedYear;
}