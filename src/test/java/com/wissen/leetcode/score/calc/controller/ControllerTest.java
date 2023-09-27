package com.wissen.leetcode.score.calc.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.wissen.leetcode.score.calc.dto.LoginDTO;
import com.wissen.leetcode.score.calc.dto.MissingEntriesDTO;
import com.wissen.leetcode.score.calc.dto.SignUpDTO;
import com.wissen.leetcode.score.calc.dto.SubmissionDTO;
import com.wissen.leetcode.score.calc.dto.UserDataDTO;
import com.wissen.leetcode.score.calc.exception.NotFoundException;
import com.wissen.leetcode.score.calc.model.SubmissionData;
import com.wissen.leetcode.score.calc.model.UserData;
import com.wissen.leetcode.score.calc.service.ServiceClass;

@ExtendWith(MockitoExtension.class)
class ControllerTest {

	@InjectMocks
	private Controller controller;

	@Mock
	private ServiceClass service;

	@Test
	@DisplayName("Testing Sign In--Positive")
	void testSignIn() {
		LoginDTO user = new LoginDTO(6750, null, "Message","Mohit");
		LoginDTO expected = new LoginDTO(6750, "root", "Login successfull","Mohit");
		when(service.signIn(user)).thenReturn(expected);
		assertEquals(new ResponseEntity<>(expected, HttpStatus.OK), controller.signIn(user));
	}

	@Test
	@DisplayName("Testing Sign In--Negative")
	void testSignIn_Negative() {
		LoginDTO user = new LoginDTO(675056, null, "Message", "Mohit");
		when(service.signIn(user)).thenThrow(NotFoundException.class);
		assertThrows(NotFoundException.class, () -> controller.signIn(user));
	}

	@Test
	@DisplayName("Testing SignUP")
	void testSignUp() {
		UserData userData = new UserData(0, 6750, "Mohit", "root", "mohit@gmail.com", 2);
		SignUpDTO signUpDTO = new SignUpDTO(6750, "Mohit", "root", "mohit@gmail.com");
		when(service.signUp(signUpDTO)).thenReturn(userData);
		assertEquals(userData, controller.signUp(signUpDTO));
	}

	@Test
	@DisplayName("Tesing Save Submission")
	void testSaveSubmissionData() {
		SubmissionDTO expected = new SubmissionDTO(6745, "mohit@gmial.com", "Mohit", 2, 2023, 5335, "Comment", 3, 2, 4);
		when(service.saveSubmissionData(expected)).thenReturn(expected);
		assertEquals(new ResponseEntity<>(expected, HttpStatus.CREATED), controller.saveSubmissionData(expected));
	}

	@Test
	@DisplayName("Testing getEmployee by ID --Positive")
	void testGgetSubmissionDataByEmpId() {
		SubmissionDTO User = new SubmissionDTO(null, "mohit@gmial.com", "Mohit", 2, 2023, 5335, "Comment", 3, 2, 4);
		List<SubmissionDTO> expected = new ArrayList<>();
		expected.add(User);
		when(service.getSubmissionDTOByEmpId(6745)).thenReturn(expected);
		assertEquals(ResponseEntity.ok(expected), controller.getSubmissionDataByEmpId(6745));
	}

	@Test
	@DisplayName("Testing getEmployee by ID --Negative")
	void testGetSubmissionDataByEmpId_Negative() {
		NotFoundException expected = new NotFoundException(null);
		when(service.getSubmissionDTOByEmpId(6745)).thenThrow(expected);
		assertThrows(NotFoundException.class, () -> controller.getSubmissionDataByEmpId(6745));
	}

	@Test
	@DisplayName("Testing Last and Top -- Positive")
	void testSubmissionDataAtLastAndTop() {
		SubmissionData user1 = new SubmissionData(1, 6747, "karthi@gmial.com", "Karthi", 2, 2023, 5333, "Comment", 3, 2,
				4, 0);
		SubmissionData user2 = new SubmissionData(2, 6750, "mohit@gmial.com", "Mohit", 2, 2023, 5334, "Comment", 3, 2,
				4, 0);
		List<SubmissionData> list = new ArrayList<>();
		list.add(user1);
		list.add(user2);
		List<Map<String, String>> expected = new ArrayList<>();
		for (SubmissionData submissionData : list) {
			Map<String, String> map = new HashMap<>();
			map.put("Emp Id", String.valueOf(submissionData.getEmpId()));
			map.put("User Name", submissionData.getUserName());
			map.put("Leet Ranking", String.valueOf(submissionData.getLeetRanking()));
			map.put("Week", String.valueOf(submissionData.getWeek()));
			expected.add(map);
		}
		when(service.submissionDataAtLastAndTop())
				.thenReturn(expected.stream().collect(Collectors.groupingBy(m -> m.get("Week"))));
		assertEquals(ResponseEntity.ok(expected.stream().collect(Collectors.groupingBy(m -> m.get("Week")))),
				controller.submissionDataAtLastAndTop());
	}

	@Test
	@DisplayName("Testing Last and Top -- Negative")
	void testSubmissionDataAtLastAndTop_Negative() {
		NotFoundException expected = new NotFoundException("No users are present");
		when(service.submissionDataAtLastAndTop()).thenThrow(expected);
		assertThrows(NotFoundException.class, () -> controller.submissionDataAtLastAndTop());
	}

	@Test
	@DisplayName("Testing TopThree--Positive")
	void testSubmissionDataAtTopThree() {
		SubmissionData user1 = new SubmissionData(1, 6747, "karthi@gmial.com", "Karthi", 2, 2023, 5333, "Comment", 3, 2,
				4, 0);
		SubmissionData user2 = new SubmissionData(2, 6750, "mohit@gmail.com", "Mohit", 2, 2023, 5334, "Comment", 3, 2,
				4, 0);
		SubmissionData user3 = new SubmissionData(4, 6745, "sanjeeth@gmail.com", "Sanjeeth", 2, 2023, 5335, "Comment",
				3, 2, 4, 0);
		List<SubmissionData> list = new ArrayList<>();
		list.add(user1);
		list.add(user2);
		list.add(user3);
		List<Map<String, String>> expected = new ArrayList<>();
		for (SubmissionData submissionData : list) {
			Map<String, String> map = new HashMap<>();
			map.put("Emp Id", String.valueOf(submissionData.getEmpId()));
			map.put("User Name", submissionData.getUserName());
			map.put("Leet Ranking", String.valueOf(submissionData.getLeetRanking()));
			map.put("Week", String.valueOf(submissionData.getWeek()));
			expected.add(map);
		}
		when(service.submissionDataAtTopThree())
				.thenReturn(expected.stream().collect(Collectors.groupingBy(m -> m.get("Week"))));
		assertEquals(ResponseEntity.ok(expected.stream().collect(Collectors.groupingBy(m -> m.get("Week")))),
				controller.submissionDataAtTopThree());
	}

	@Test
	@DisplayName("Testing TopThree -- Negative")
	void testSubmissionDataAtTopThree_Negative() {
		NotFoundException expected = new NotFoundException("users are not present on that week of the year");
		when(service.submissionDataAtTopThree()).thenThrow(expected);
		assertThrows(NotFoundException.class, () -> controller.submissionDataAtTopThree());
	}

	@Test
	@DisplayName("Testing get multiple Employees by IDS--Positive")
	void testGetSelectedSubmissionData() {
		SubmissionDTO User1 = new SubmissionDTO(6745, "mohit@gmial.com", "Mohit", 1, 2023, 5335, "Comment", 3, 2, 4);
		SubmissionDTO User2 = new SubmissionDTO(6747, "karthik@gmial.com", "Karthik", 2, 2023, 4353, "Comment", 3, 2,
				4);
		List<SubmissionDTO> expected = new ArrayList<>();
		expected.add(User1);
		expected.add(User2);
		List<Integer> empIds = new ArrayList<>();
		empIds.add(6747);
		empIds.add(6745);
		List<Integer> weeks = new ArrayList<>();
		weeks.add(1);
		weeks.add(2);
		when(service.getSelectedSubmissionData(empIds, weeks)).thenReturn(expected);
		assertEquals(ResponseEntity.ok(expected), controller.getSelectedSubmissionData(empIds, weeks));
	}

	@Test
	@DisplayName("Testing get multiple Employees by IDS--Negative")
	void testGetSelectedSubmissionData_Negative() {
		List<Integer> empIds = new ArrayList<>();
		empIds.add(6747);
		empIds.add(6745);
		List<Integer> weeks = new ArrayList<>();
		weeks.add(1);
		weeks.add(2);
		NotFoundException expected = new NotFoundException(null);
		when(service.getSelectedSubmissionData(empIds, weeks)).thenThrow(expected);
		assertThrows(NotFoundException.class, () -> controller.getSelectedSubmissionData(empIds, weeks));
	}

	@Test
	@DisplayName("Testing All users")
	void testGetAllUsers() {
		List<Map<String, String>> expected = new ArrayList<>();
		Map<String, String> map = new HashMap<>();
		map.put("empId", "1001");
		map.put("userName", "Test User");
		expected.add(map);
		when(service.getAllUsers()).thenReturn(expected);
		assertEquals(ResponseEntity.ok(expected), controller.getAllUsers());
	}

	@Test
	@DisplayName("testing rankreports")
	void testGetRanksInInterval() {
		SubmissionDTO User1 = new SubmissionDTO(6745, "mohit@gmial.com", "Mohit", 1, 2023, 5335, "Comment", 3, 2, 4);
		SubmissionDTO User2 = new SubmissionDTO(6745, "mohit@gmial.com", "Mohit", 2, 2023, 5335, "Comment", 3, 2, 4);
		List<SubmissionDTO> expected = new ArrayList<>();
		expected.add(User1);
		expected.add(User2);
		List<Integer> empId = new ArrayList<>();
		empId.add(6745);
		when(service.getRanksInInterval(1, 2, empId)).thenReturn(expected);
		assertEquals(new ResponseEntity<>(expected, HttpStatus.OK), controller.getRanksInInterval(1, 2, empId));
	}

	@Test
	@DisplayName("Testing GetMissedUser")
	void testGetMissedUser() {
		UserDataDTO user1 = new UserDataDTO(6747, "karthi", "karthi@gmail.com", 1);
		UserDataDTO user2 = new UserDataDTO(6750, "mohith", "mohith@gmail.com", 1);
		List<UserDataDTO> expected = new ArrayList<>();
		expected.add(user1);
		expected.add(user2);
		when(service.getMissedUser(2, 2023)).thenReturn(expected);
		assertEquals(ResponseEntity.ok().body(expected), controller.getMissedUser(2, 2023));
	}

	@Test
	@DisplayName("Testing GetAllMissedUser")
	void testGetAllMissedUser() {
		MissingEntriesDTO user = new MissingEntriesDTO(6747, "mohith@gmail.com", "mohith", 1, 2023);
		List<MissingEntriesDTO> expected = new ArrayList<>();
		expected.add(user);
		when(service.getAllMissedUser()).thenReturn(expected);
		assertEquals(ResponseEntity.ok(expected), controller.getAllMissedUser());
	}

	@Test
	@DisplayName("Testing Complexity--Positive")
	void tesGetComplexity() {
		SubmissionDTO User1 = new SubmissionDTO(6745, "mohit@gmail.com", "Mohit", null, 2023, 5335, "Comment", 3, 2, 4);
		SubmissionDTO User2 = new SubmissionDTO(6747, "karthik@gmail.com", "Karthik", null, 2023, 4353, "Comment", 3, 2,
				4);
		List<SubmissionDTO> expected = new ArrayList<>();
		expected.add(User1);
		expected.add(User2);
		List<Integer> empIds = new ArrayList<>();
		empIds.add(6747);
		empIds.add(6745);
		when(service.getComplexity(empIds)).thenReturn(expected);
		assertEquals(ResponseEntity.ok(expected), controller.getComplexity(empIds));
	}
	@Test
	@DisplayName("Testing Complexity--Negative")
	void tesGetComplexity_Negative() {
		List<Integer> empIds = new ArrayList<>();
		empIds.add(6747);
		empIds.add(6745);
		NotFoundException expected = new NotFoundException(null);
		when(service.getComplexity(empIds)).thenThrow(expected);
		assertThrows(NotFoundException.class, () -> controller.getComplexity(empIds));
	}

}
