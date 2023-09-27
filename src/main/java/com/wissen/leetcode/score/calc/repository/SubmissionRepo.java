package com.wissen.leetcode.score.calc.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wissen.leetcode.score.calc.model.SubmissionData;

public interface SubmissionRepo extends JpaRepository<SubmissionData, Integer> {

	public List<SubmissionData> findByEmpId(int empId);
	

	@Query(value = "SELECT sd FROM SubmissionData sd WHERE sd.empId in :empId and sd.week in :weeks")
	public List<SubmissionData> findByEmpIdAndWeek(List<Integer> empId, List<Integer> weeks);

	@Query(value = "SELECT * FROM submission_data\r\n"
			+ "WHERE (week, leet_ranking) IN (SELECT week, MAX(leet_ranking) AS max_ranking\r\n"
			+ "FROM submission_data GROUP BY week UNION ALL SELECT week, MIN(leet_ranking) AS min_ranking FROM submission_data\r\n"
			+ "GROUP BY week)\r\n" + "ORDER BY week, leet_ranking;", nativeQuery = true)
	public List<SubmissionData> submissionDataAtLastAndTop();

	@Query(value = "SELECT * FROM submission_data WHERE week IN (\r\n" + "SELECT DISTINCT week FROM submission_data\r\n"
			+ ") AND ( SELECT COUNT(*) FROM submission_data AS sd2\r\n" + "  WHERE sd2.week = submission_data.week\r\n"
			+ "  AND sd2.leet_ranking <= submission_data.leet_ranking\r\n"
			+ ") <= 3 ORDER BY week DESC, leet_ranking", nativeQuery = true)
	public List<SubmissionData> submissionDataAtTopThree();

	@Query(value = "SELECT sd FROM SubmissionData sd WHERE sd.week>=:startWeek and sd.week<=:endWeek and sd.empId in :empId")
	public List<SubmissionData> getRanksInInterval(int startWeek, int endWeek, List<Integer> empId);

	public Optional<SubmissionData> findByEmpIdAndWeek(int empId, int week);
	
	@Query(value = "SELECT sd FROM SubmissionData sd WHERE sd.empId in :empId")
	public List<SubmissionData> findByComplexity(List<Integer> empId);


}
