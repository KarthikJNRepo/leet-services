package com.wissen.leetcode.score.calc.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wissen.leetcode.score.calc.model.UserData;

@Repository
public interface UserRepository extends JpaRepository<UserData, Integer> {
	
	public Optional<UserData> findByEmpId(int empId);

	@Modifying
	@Query(value = "UPDATE user_data SET missed_entries = missed_entries + 1 WHERE emp_id IN (SELECT emp_id FROM missing_entries WHERE missed_week = :week and missed_year = :year);", nativeQuery = true)
	void updateUserMissedCount(@Param("week") int week, @Param("year") int year);

	@Query(value = "SELECT * from user_data where emp_id IN (Select emp_id from missing_entries where missed_week=:week and missed_year=:year);", nativeQuery = true)
	List<UserData> getMissedUser(@Param("week") int week, @Param("year") int year);
	
}