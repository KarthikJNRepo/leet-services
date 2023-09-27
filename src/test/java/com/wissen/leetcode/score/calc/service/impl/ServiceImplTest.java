package com.wissen.leetcode.score.calc.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

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

@ExtendWith(MockitoExtension.class)
class ServiceImplTest {

	@InjectMocks
	private ServiceImpl serviceimp;
	@Mock
	private UserRepository userRepo;
	@Mock
	private SubmissionRepo submissionRepo;

	@Mock
	private ModelMapper modelMapper;
	@Mock
	private MissingEntryRepo missingRepo;
	
	@Test
	@DisplayName("Testing Sign In--Positive")
	void testSignIn() {
		LoginDTO user = new LoginDTO(6750, "root", "Message","Mohit");
		UserData user1 = new UserData(0, 6750, "Mohit", "root", "mohit@gmail.com", 2);
		when(userRepo.findByEmpId(6750)).thenReturn(Optional.of(user1));
		LoginDTO expected = new LoginDTO(6750, null, "Login successfull","Mohit");
		assertEquals(expected, serviceimp.signIn(user));
	}

	@Test
	@DisplayName("Testing Sign In--Negative")
	void testSignIn_Negative() {
		LoginDTO user = new LoginDTO(6750, "root", "Message","Mohit");
		UserData user1 = new UserData(0, 6750, "Mohit", "root1", "mohit@gmail.com", 2);
		when(userRepo.findByEmpId(6750)).thenReturn(Optional.of(user1));
		if (user.getPassword().equals(user.getPassword())) {
		}
		assertThrows(NotFoundException.class, () -> serviceimp.signIn(user));
	}
	
	@Test
	@DisplayName("Testing Sign Up")
	void testsignUp() {
		UserData userData = new UserData(0, 6750, "Mohit", "root", "mohit@gmail.com", 0);
		SignUpDTO signUpDTO = new SignUpDTO(6750, "Mohit", "root", "mohit@gmail.com");
		when(userRepo.save(userData)).thenReturn(userData);
		assertEquals(userData, serviceimp.signUp(signUpDTO));
	}

	@Test
	@DisplayName("Testing save submissionData --Positive")
	void saveSubmissionData() {
		SubmissionDTO newuser = new SubmissionDTO(6745, "mohit@gmial.com", "Mohit", 2, 2023, 5335, "Comment", 3, 2, 4);
		SubmissionData sub = serviceimp.convertSubmissionDTOToSubmissionData(newuser);
		when(submissionRepo.save(any())).thenReturn(sub);
		assertEquals(newuser, serviceimp.saveSubmissionData(newuser));
	}
	@Test
	@DisplayName("Testing save submissionData --Negative")
	void saveSubmissionData_negative() {
		SubmissionDTO submissionDTO = new SubmissionDTO();
		submissionDTO.setEmpId(6750);
		submissionDTO.setWeek(1);
		SubmissionData submissionData = new SubmissionData();
		when(submissionRepo.findByEmpIdAndWeek(6750, 1)).thenReturn(Optional.of(submissionData)); 
		assertThrows(AlreadyExistException.class,() ->serviceimp.saveSubmissionData(submissionDTO));
	}
	
	
	@Test
	@DisplayName("Testing getEmployee by ID --Positive")
	void testGetSubmissionDTOByEmpId() {
		SubmissionData User = new SubmissionData(0, 6747, null, null, 0, 0, 0, null, 0, 0, 0, 0);
		List<SubmissionData> list = new ArrayList<>();
		list.add(User);
		when(submissionRepo.findByEmpId(6747)).thenReturn(list);
		List<SubmissionDTO> excpted = null;
		if (!list.isEmpty()) {
			excpted = list.stream().map(serviceimp::convertToSubmissionDataToSubmissionDTO)
					.collect(Collectors.toList());
		}
		assertEquals(excpted, serviceimp.getSubmissionDTOByEmpId(6747));
	}

	@Test
	@DisplayName("Testing getemployee by ID --Negative")
	void testGetSubmissionDTOByEmpId_Negative() {
		List<SubmissionData> list = new ArrayList<>();
		when(submissionRepo.findByEmpId(6747)).thenReturn(list);
		assertThrows(NotFoundException.class, () -> serviceimp.getSubmissionDTOByEmpId(6747));
	}

	@Test
	@DisplayName("Testing toplast -- Positive")
	void testSubmissionDataAtLastAndTop() {
		SubmissionData user1 = new SubmissionData(1, 6747, "karthi@gmial.com", "Karthi", 2, 2023, 5333, "Comment", 3, 2,
				4, 0);
		SubmissionData user2 = new SubmissionData(2, 6750, "mohit@gmial.com", "Mohit", 2, 2023, 5334, "Comment", 3, 2,
				4, 0);
		SubmissionData user3 = new SubmissionData(3, 6747, "karthi@gmial.com", "Karthi", 3, 2023, 5333, "Comment", 3, 2,
				4, 0);
		SubmissionData user4 = new SubmissionData(4, 6745, "sanjeeth@gmial.com", "Sanjeeth", 3, 2023, 5334, "Comment",
				3, 2, 4, 0);

		List<SubmissionData> list = new ArrayList<>();
		list.add(user1);
		list.add(user2);
		list.add(user3);
		list.add(user4);

		when(submissionRepo.submissionDataAtLastAndTop()).thenReturn(list);
		List<Map<String, String>> excpected = null;
		if (!list.isEmpty()) {
			excpected = new ArrayList<>();
			for (SubmissionData submissionData : list) {
				Map<String, String> map = new HashMap<>();
				map.put("Emp Id", String.valueOf(submissionData.getEmpId()));
				map.put("User Name", submissionData.getUserName());
				map.put("Leet Ranking", String.valueOf(submissionData.getLeetRanking()));
				map.put("Week", String.valueOf(submissionData.getWeek()));
				excpected.add(map);
			}
		}
		assertEquals(excpected.stream().collect(Collectors.groupingBy(m -> m.get("Week"))).size(),
				serviceimp.submissionDataAtLastAndTop().size());
	}

	@Test
	@DisplayName("Testing toplast -- Negative")
	void testSubmissionDataAtLastAndTop_Negative() {
		List<SubmissionData> list = new ArrayList<>();
		when(submissionRepo.submissionDataAtLastAndTop()).thenReturn(list);
		assertThrows(NotFoundException.class, () -> serviceimp.submissionDataAtLastAndTop());
	}

	@Test

	@DisplayName("Testing TopThree -- Positive")
	void testSubmissionDataAtTopThree() {
		SubmissionData user1 = new SubmissionData(1, 6747, "karthi@gmial.com", "Karthi", 2, 2023, 5333, "Comment", 3, 2,
				4, 0);

		List<SubmissionData> list = new ArrayList<>();
		list.add(user1);
		when(submissionRepo.submissionDataAtTopThree()).thenReturn(list);
		List<Map<String, String>> excpected = null;
		if (!list.isEmpty()) {
			excpected = new ArrayList<>();
			for (SubmissionData submissionData : list) {
				Map<String, String> map = new HashMap<>();
				map.put("Emp Id", String.valueOf(submissionData.getEmpId()));
				map.put("User Name", submissionData.getUserName());
				map.put("Leet Ranking", String.valueOf(submissionData.getLeetRanking()));
				map.put("Week", String.valueOf(submissionData.getWeek()));
				excpected.add(map);
			}
		}
		assertEquals(excpected.size(), serviceimp.submissionDataAtTopThree().size());
	}
	
	@Test
	void testsubmissionDataAtTopThree_Negative() {
		List<SubmissionData> list = new ArrayList<>();
		when(submissionRepo.submissionDataAtTopThree()).thenReturn(list);
		assertThrows(NotFoundException.class, () -> serviceimp.submissionDataAtTopThree());
	}
	
	@Test
	@DisplayName("Testing getMultipleEmployees by IDs--Positive")
	void testgetSelectedSubmissionData() {
		SubmissionData User1 = new SubmissionData(0, 6747, null, null, 1, 0, 0, null, 0, 0, 0, 0);
		SubmissionData User2 = new SubmissionData(0, 6748, null, null, 2, 0, 0, null, 0, 0, 0, 0);
		List<SubmissionData> list = new ArrayList<>();
		list.add(User1);
		list.add(User2);
		List<Integer> empIds = new ArrayList<>();
		empIds.add(6747);
		empIds.add(6748);
		List<Integer> weeks = new ArrayList<>();
		weeks.add(1);
		weeks.add(2);
		when(submissionRepo.findByEmpIdAndWeek(empIds, weeks)).thenReturn(list);
		List<SubmissionDTO> excpted = null;
		if (!list.isEmpty()) {
			excpted = list.stream().map(serviceimp::convertToSubmissionDataToSubmissionDTO)
					.collect(Collectors.toList());
		}
		assertEquals(excpted, serviceimp.getSelectedSubmissionData(empIds, weeks));
	}

	@Test
	@DisplayName("Testing getMultipleemployee by IDs --Negative")
	void testgetSelectedSubmissionData_Negative() {
		List<SubmissionData> list = new ArrayList<>();
		List<Integer> empIds = new ArrayList<>();
		empIds.add(6747);
		empIds.add(6748);
		List<Integer> weeks = new ArrayList<>();
		weeks.add(1);
		weeks.add(2);
		when(submissionRepo.findByEmpIdAndWeek(empIds, weeks)).thenReturn(list);
		assertThrows(NotFoundException.class, () -> serviceimp.getSelectedSubmissionData(empIds, weeks));
	}

	@Test
	@DisplayName("Testing All Users")
	void testallUsers() {
		UserData User = new UserData(0, 1001, "Test User", "password", "user@gmail.com", 2);
		UserData User2 = new UserData(0, 1002, "Test  AnotherUser", "password1", "user1@gmail.com", 2);
		List<UserData> list = new ArrayList<>();
		list.add(User);
		list.add(User2);
		when(userRepo.findAll()).thenReturn(list);
		Map<String, String> map = new HashMap<>();
		map.put("userName", "Test User");
		map.put("empId", "1001");
		List<Map<String, String>> employees = serviceimp.getAllUsers();
		assertEquals(map.get("userName"), employees.get(0).get("userName"));
	}

	@Test
	@DisplayName("testing rankreports")
	void testGetRanksInInterval() {
		SubmissionData User1 = new SubmissionData(1, 6747, null, null, 1, 0, 0, null, 0, 0, 0, 0);
		SubmissionData User2 = new SubmissionData(2, 6747, null, null, 2, 0, 0, null, 0, 0, 0, 0);
		List<SubmissionData> list = new ArrayList<>();
		list.add(User1);
		list.add(User2);
		List<Integer> empId = new ArrayList<>();
		empId.add(6747);
		when(submissionRepo.getRanksInInterval(1, 2, empId)).thenReturn(list);

		List<SubmissionDTO> expected = list.stream().map(serviceimp::convertToSubmissionDataToSubmissionDTO)
				.collect(Collectors.toList());
		assertEquals(expected, serviceimp.getRanksInInterval(1, 2, empId));
	}

	@Test
	@DisplayName("Testing getMissedUser")
	void testGetMissedUser() {
		UserData user1 = new UserData(1, 6745, "sanjeeth", "password", "sanjeeth@gmail.com", 4);
		UserData user2 = new UserData(2, 6747, "karthi", "password", "karthi@gmail.com", 2);
		List<UserData> list = new ArrayList<>();
		list.add(user1);
		list.add(user2);
		when(userRepo.getMissedUser(2, 2023)).thenReturn(list);

		List<UserDataDTO> expected = list.stream().map(userData -> new UserDataDTO(userData.getEmpId(),
				userData.getUserName(), userData.getEmpEmail(), userData.getMissedEntries()))
				.collect(Collectors.toList());
		assertEquals(expected, serviceimp.getMissedUser(2, 2023));
	}

	@Test
	@DisplayName("Testing getALLMissedUser")
	void testGetAllMissedUser() {
		MissingEntries user = new MissingEntries(0, 6745, "null@gmail.com", "Mohit", 2, 2023);
		List<MissingEntries> list = new ArrayList<>();
		list.add(user);
		when(missingRepo.getAllMissedUser()).thenReturn(list);
		List<MissingEntriesDTO> expected = list
				.stream().map(allUser -> new MissingEntriesDTO(allUser.getEmpId(), allUser.getEmpEmail(),
						allUser.getUserName(), allUser.getMissedWeek(), allUser.getMissedYear()))
				.collect(Collectors.toList());
		assertEquals(expected, serviceimp.getAllMissedUser());
	}

	@Test
	@DisplayName("Testing addMissingToTable")
	void testAddMissingToTable() {
		serviceimp.addMissingToTable(2, 2023);
	}

	@Test
	@DisplayName("Testing updateUserMissedCount")
	void updateUserMissedCount() {
		serviceimp.updateUserMissedCount(2, 2023);
	}
	@Test
	@DisplayName("Testing Complexity--Positive")
	void getComplexity() {
		SubmissionData User1 = new SubmissionData(0, 6747, null, null, 1, 0, 0, null, 0, 0, 0, 0);
		SubmissionData User2 = new SubmissionData(0, 6748, null, null, 2, 0, 0, null, 0, 0, 0, 0);
		List<SubmissionData> list = new ArrayList<>();
		list.add(User1);
		list.add(User2);
		List<Integer> empIds = new ArrayList<>();
		empIds.add(6747);
		empIds.add(6748);
		when(submissionRepo.findByComplexity(empIds)).thenReturn(list);
		List<SubmissionDTO> excpted = null;
		if (!list.isEmpty()) {
			excpted = list.stream().map(serviceimp::convertToSubmissionDataToSubmissionDTO)
					.collect(Collectors.toList());
		}
		assertEquals(excpted, serviceimp.getComplexity(empIds));
	}
	
	@Test
	@DisplayName("Testing Complexity --Negative")
	void getComplexity_Negative() {
		List<SubmissionData> list = new ArrayList<>();
		List<Integer> empIds = new ArrayList<>();
		empIds.add(6747);
		empIds.add(6748);
		when(submissionRepo.findByComplexity(empIds)).thenReturn(list);
		assertThrows(NotFoundException.class, () -> serviceimp.getComplexity(empIds));
	}
}
