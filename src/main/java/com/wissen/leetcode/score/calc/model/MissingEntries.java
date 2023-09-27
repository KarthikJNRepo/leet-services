package com.wissen.leetcode.score.calc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "missing_entries")
public class MissingEntries {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "emp_id")
	private int empId;
	@Column(name = "emp_email")
	private String empEmail;
	@Column(name = "user_name")
	private String userName;
	@Column(name = "missed_week")
	private int missedWeek;
	@Column(name = "missed_year")
	private int missedYear;
}
