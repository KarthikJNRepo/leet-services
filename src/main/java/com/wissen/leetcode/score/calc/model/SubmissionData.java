package com.wissen.leetcode.score.calc.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFilter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@JsonFilter("SomeBeenFilter")
public class SubmissionData {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int submissionId;

	private int empId;

	private String empEmail;

	private String userName;

	private int week;

	private int year;

	private int leetRanking;

	private String comments;

	private int easy;

	private int medium;

	private int hard;

	private int wissenRanking;

}
