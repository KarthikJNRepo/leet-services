package com.wissen.leetcode.score.calc.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlreadyExistException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3080722678936577272L;

	public AlreadyExistException(String message) {
		super(message);
	}

}
