package com.wissen.leetcode.score.calc.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wissen.leetcode.score.calc.dto.LoginDTO;
import com.wissen.leetcode.score.calc.dto.MissingEntriesDTO;
import com.wissen.leetcode.score.calc.dto.SignUpDTO;
import com.wissen.leetcode.score.calc.dto.SubmissionDTO;
import com.wissen.leetcode.score.calc.dto.UserDataDTO;
import com.wissen.leetcode.score.calc.exception.AlreadyExistException;
import com.wissen.leetcode.score.calc.exception.NotFoundException;
import com.wissen.leetcode.score.calc.model.MissingEntries;
import com.wissen.leetcode.score.calc.model.SubmissionData;
import com.wissen.leetcode.score.calc.model.UserData;
import com.wissen.leetcode.score.calc.repository.MissingEntryRepo;
import com.wissen.leetcode.score.calc.repository.SubmissionRepo;
import com.wissen.leetcode.score.calc.repository.UserRepository;
import com.wissen.leetcode.score.calc.service.ServiceClass;

@Service
@Transactional
public class ServiceImpl implements ServiceClass {

	@Autowired
	private MissingEntryRepo missingRepo;

	@Autowired
	private SubmissionRepo submissionRepo;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public LoginDTO signIn(LoginDTO loginDTO) throws NotFoundException {
		UserData user = userRepo.findByEmpId(loginDTO.getEmpId()).get();
		if (loginDTO.getPassword().equals(user.getPassword())) {
			loginDTO.setPassword(null);
			loginDTO.setMessage("Login successfull");
			loginDTO.setUserName(user.getUserName());
			return loginDTO;
		} else
			throw new NotFoundException("Username and password incorrect");
	}

	@Override
	public UserData signUp(SignUpDTO dto) {
		return userRepo
				.save(new UserData(0, dto.getEmpId(), dto.getUserName(), dto.getPassword(), dto.getEmpEmail(), 0));
	}

	@Override
	public SubmissionDTO saveSubmissionData(SubmissionDTO submissionDTO) throws AlreadyExistException {
		Optional<SubmissionData> user = submissionRepo.findByEmpIdAndWeek(submissionDTO.getEmpId(),
				submissionDTO.getWeek());
		if (user.isPresent()) {
			throw new AlreadyExistException("Data Already Submitted for this week");
		}
		submissionRepo.save(this.convertSubmissionDTOToSubmissionData(submissionDTO));
		return submissionDTO;
	}

	@Override
	public List<SubmissionDTO> getSubmissionDTOByEmpId(int empId) throws NotFoundException {
		List<SubmissionData> submissionDTO = submissionRepo.findByEmpId(empId);
		if (!submissionDTO.isEmpty()) {
			return submissionDTO.stream().map(this::convertToSubmissionDataToSubmissionDTO)
					.collect(Collectors.toList());
		} else {
			throw new NotFoundException("user is not found in that id :" + empId);
		}
	}

	@Override
	public Map<Object, List<Map<String, String>>> submissionDataAtLastAndTop() throws NotFoundException {
		List<SubmissionData> listOfSubmissionData = submissionRepo.submissionDataAtLastAndTop();
		if (!listOfSubmissionData.isEmpty()) {
			List<Map<String, String>> list = new ArrayList<>();
			for (SubmissionData submissionData : listOfSubmissionData) {
				Map<String, String> map = new HashMap<>();
				map.put("empId", String.valueOf(submissionData.getEmpId()));
				map.put("userName", submissionData.getUserName());
				map.put("leetRanking", String.valueOf(submissionData.getLeetRanking()));
				map.put("week", String.valueOf(submissionData.getWeek()));
				list.add(map);
			}
			return list.stream().collect(
					Collectors.groupingBy(m -> "week " + m.get("week"), LinkedHashMap::new, Collectors.toList()));
		} else
			throw new NotFoundException("No users are present");
	}

	@Override
	public Map<Object, List<Map<String, String>>> submissionDataAtTopThree() throws NotFoundException {
		List<SubmissionData> listOfSubmissionData = submissionRepo.submissionDataAtTopThree();
		if (!listOfSubmissionData.isEmpty()) {
			List<Map<String, String>> list = new ArrayList<>();
			for (SubmissionData submissionData : listOfSubmissionData) {
				Map<String, String> map = new HashMap<>();
				map.put("empId", String.valueOf(submissionData.getEmpId()));
				map.put("userName", submissionData.getUserName());
				map.put("leetRanking", String.valueOf(submissionData.getLeetRanking()));
				map.put("week", String.valueOf(submissionData.getWeek()));
				list.add(map);
			}
			return list.stream().collect(
					Collectors.groupingBy(m -> "week " + m.get("week"), LinkedHashMap::new, Collectors.toList()));
		} else {
			throw new NotFoundException("No users present on the database");
		}
	}

	@Override
	public List<SubmissionDTO> getSelectedSubmissionData(List<Integer> empIds, List<Integer> weeks)
			throws NotFoundException {
		List<SubmissionData> list = submissionRepo.findByEmpIdAndWeek(empIds, weeks);
		if (!list.isEmpty()) {
			return list.stream().map(this::convertToSubmissionDataToSubmissionDTO).collect(Collectors.toList());
		} else {
			throw new NotFoundException(
					"user is not found in these ids :" + empIds + " and not for the weeks " + weeks);
		}
	}

	@Override
	public List<SubmissionDTO> getRanksInInterval(int startWeek, int endWeek, List<Integer> empId) {
		return submissionRepo.getRanksInInterval(startWeek, endWeek, empId).stream()
				.map(this::convertToSubmissionDataToSubmissionDTO).collect(Collectors.toList());
	}

	@Override
	public List<Map<String, String>> getAllUsers() {
		List<UserData> userData = userRepo.findAll();
		List<Map<String, String>> list = new ArrayList<>();
		for (UserData user : userData) {
			Map<String, String> map = new HashMap<>();
			map.put("empId", String.valueOf(user.getEmpId()));
			map.put("userName", user.getUserName());
			list.add(map);
		}
		return list;
	}

	public void addMissingToTable(int week, int year) {
		missingRepo.addMissingToTable(week, year);
	}

	public void updateUserMissedCount(int week, int year) {
		userRepo.updateUserMissedCount(week, year);
	}

	public List<UserDataDTO> getMissedUser(int week, int year) {
		List<UserData> userDataList = userRepo.getMissedUser(week, year);
		return userDataList.stream().map(userData -> new UserDataDTO(userData.getEmpId(), userData.getUserName(),
				userData.getEmpEmail(), userData.getMissedEntries())).collect(Collectors.toList());
	}

	public List<MissingEntriesDTO> getAllMissedUser() {
		List<MissingEntries> allUserData = missingRepo.getAllMissedUser();
		return allUserData
				.stream().map(allUser -> new MissingEntriesDTO(allUser.getEmpId(), allUser.getEmpEmail(),
						allUser.getUserName(), allUser.getMissedWeek(), allUser.getMissedYear()))
				.collect(Collectors.toList());
	}

	public SubmissionDTO convertToSubmissionDataToSubmissionDTO(SubmissionData submissionData) {
		return modelMapper.map(submissionData, SubmissionDTO.class);
	}

	public SubmissionData convertSubmissionDTOToSubmissionData(SubmissionDTO submissionDTO) {
		return modelMapper.map(submissionDTO, SubmissionData.class);
	}

	public List<SubmissionDTO> getComplexity(List<Integer> empIds) throws NotFoundException {
		List<SubmissionData> list = submissionRepo.findByComplexity(empIds);
		if (!list.isEmpty()) {
			return list.stream().map(this::convertToSubmissionDataToSubmissionDTO).collect(Collectors.toList());
		} else {
			throw new NotFoundException("User is not found for the selected Employee IDs :" + empIds);
		}
	}

}
