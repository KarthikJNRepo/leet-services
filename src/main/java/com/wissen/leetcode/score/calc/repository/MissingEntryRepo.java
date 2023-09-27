package com.wissen.leetcode.score.calc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wissen.leetcode.score.calc.model.MissingEntries;

@Repository
public interface MissingEntryRepo extends JpaRepository<MissingEntries, Integer> {
	@Modifying
	@Query(value = "INSERT INTO missing_entries (emp_id, missed_week,emp_email,user_name, missed_year) SELECT emp_id, :week, emp_email, user_name, :year FROM user_data tbl2 WHERE emp_id NOT IN (SELECT tbl1.emp_id FROM submission_data tbl1 JOIN user_data tbl2 ON tbl1.emp_id = tbl2.emp_id WHERE week = :week) AND NOT EXISTS ( SELECT * FROM missing_entries WHERE emp_id = tbl2.emp_id AND missed_week = :week);", nativeQuery = true)
	public int addMissingToTable(@Param("week") int week, @Param("year") int year);

	@Query(value = "SELECT * FROM missing_entries ORDER BY missed_week DESC;", nativeQuery = true)
	List<MissingEntries> getAllMissedUser();
}