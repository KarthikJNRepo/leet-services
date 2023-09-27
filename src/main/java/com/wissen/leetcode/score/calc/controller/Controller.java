package com.wissen.leetcode.score.calc.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wissen.leetcode.score.calc.dto.LoginDTO;
import com.wissen.leetcode.score.calc.dto.MissingEntriesDTO;
import com.wissen.leetcode.score.calc.dto.SignUpDTO;
import com.wissen.leetcode.score.calc.dto.SubmissionDTO;
import com.wissen.leetcode.score.calc.dto.UserDataDTO;
import com.wissen.leetcode.score.calc.exception.AlreadyExistException;
import com.wissen.leetcode.score.calc.exception.NotFoundException;
import com.wissen.leetcode.score.calc.model.UserData;
import com.wissen.leetcode.score.calc.service.ServiceClass;

@RestController
@RequestMapping("/user")
public class Controller {

	@Autowired
	private ServiceClass service;

	@PostMapping("/signin")
	public ResponseEntity<LoginDTO> signIn(@RequestBody LoginDTO loginDTO) throws NotFoundException {
		return new ResponseEntity<>(service.signIn(loginDTO), HttpStatus.OK);
	}

	@PostMapping("/signup")
	public UserData signUp(@RequestBody @Valid SignUpDTO dto) {
		return service.signUp(dto);
	}

	@PostMapping("/addsubmission")
	public ResponseEntity<SubmissionDTO> saveSubmissionData(@RequestBody @Valid SubmissionDTO submissionDTO)
			throws AlreadyExistException {
		return new ResponseEntity<>(service.saveSubmissionData(submissionDTO), HttpStatus.CREATED);
	}

	@GetMapping("/{empid}")
	public ResponseEntity<List<SubmissionDTO>> getSubmissionDataByEmpId(@PathVariable int empid)
			throws NotFoundException {
		return ResponseEntity.ok(service.getSubmissionDTOByEmpId(empid));
	}

	@GetMapping("/toplast")
	public ResponseEntity<Map<Object, List<Map<String, String>>>> submissionDataAtLastAndTop()
			throws NotFoundException {
		return ResponseEntity.ok(service.submissionDataAtLastAndTop());
	}

	@GetMapping("/topthree")
	public ResponseEntity<Map<Object, List<Map<String, String>>>> submissionDataAtTopThree() throws NotFoundException {
		return ResponseEntity.ok(service.submissionDataAtTopThree());
	}

	@GetMapping("/getselectedbyuserempids")
	public ResponseEntity<List<SubmissionDTO>> getSelectedSubmissionData(@RequestParam List<Integer> empIds,
			@RequestParam List<Integer> weeks) throws NotFoundException {
		return ResponseEntity.ok(service.getSelectedSubmissionData(empIds, weeks));
	}

	@GetMapping("/allusers")
	public ResponseEntity<List<Map<String, String>>> getAllUsers() {
		return new ResponseEntity<>(service.getAllUsers(), HttpStatus.OK);
	}

	@GetMapping(path = "/missedentries/{year}/{week}")
	public ResponseEntity<List<UserDataDTO>> getMissedUser(@PathVariable("week") int week,
			@PathVariable("year") int year) {
		List<UserDataDTO> userData = service.getMissedUser(week, year);
		return ResponseEntity.ok().body(userData);
	}

	@GetMapping(path = "/allmissedentriessorted")
	public ResponseEntity<List<MissingEntriesDTO>> getAllMissedUser() {
		List<MissingEntriesDTO> userData = service.getAllMissedUser();
		return ResponseEntity.ok(userData);
	}

	@GetMapping("/getrankrecords")
	public ResponseEntity<List<SubmissionDTO>> getRanksInInterval(@RequestParam int startWeek,
			@RequestParam int endWeek, @RequestParam List<Integer> empId) {
		return new ResponseEntity<>(service.getRanksInInterval(startWeek, endWeek, empId), HttpStatus.OK);
	}

	@GetMapping("/complexity")
	public ResponseEntity<List<SubmissionDTO>> getComplexity(@RequestParam List<Integer> empIds)
			throws NotFoundException {
		return ResponseEntity.ok(service.getComplexity(empIds));
	}

}
