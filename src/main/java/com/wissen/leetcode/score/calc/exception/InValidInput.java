package com.wissen.leetcode.score.calc.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InValidInput extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -271411285857200656L;

	public InValidInput(String message) {
		super(message);
	}

}
