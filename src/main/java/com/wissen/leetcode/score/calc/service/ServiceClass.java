package com.wissen.leetcode.score.calc.service;

import java.util.List;
import java.util.Map;

import com.wissen.leetcode.score.calc.dto.LoginDTO;
import com.wissen.leetcode.score.calc.dto.MissingEntriesDTO;
import com.wissen.leetcode.score.calc.dto.SignUpDTO;
import com.wissen.leetcode.score.calc.dto.SubmissionDTO;
import com.wissen.leetcode.score.calc.dto.UserDataDTO;
import com.wissen.leetcode.score.calc.exception.AlreadyExistException;
import com.wissen.leetcode.score.calc.exception.NotFoundException;
import com.wissen.leetcode.score.calc.model.UserData;

public interface ServiceClass {

	public LoginDTO signIn(LoginDTO loginDTO) throws NotFoundException;

	public UserData signUp(SignUpDTO dto);

	public SubmissionDTO saveSubmissionData(SubmissionDTO submissionDTO) throws AlreadyExistException;

	public List<SubmissionDTO> getSubmissionDTOByEmpId(int id) throws NotFoundException;

	public Map<Object, List<Map<String, String>>> submissionDataAtLastAndTop() throws NotFoundException;

	public Map<Object, List<Map<String, String>>> submissionDataAtTopThree() throws NotFoundException;

	public List<SubmissionDTO> getSelectedSubmissionData(List<Integer> empIds, List<Integer> weeks)
			throws NotFoundException;

	public List<Map<String, String>> getAllUsers();

	public List<SubmissionDTO> getRanksInInterval(int startWeek, int endWeek, List<Integer> empId);

	public void addMissingToTable(int week, int year);

	public void updateUserMissedCount(int week, int year);

	public List<UserDataDTO> getMissedUser(int week, int year);

	public List<MissingEntriesDTO> getAllMissedUser();

	public List<SubmissionDTO> getComplexity(List<Integer> empIds) throws NotFoundException;

}
