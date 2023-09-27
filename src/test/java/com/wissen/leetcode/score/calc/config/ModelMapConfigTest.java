package com.wissen.leetcode.score.calc.config;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
class ModelMapConfigTest {

	@Mock
	private ModelMapper modelMapper;

	@InjectMocks
	private ModelMapConfig modelMapConfig;

	@Test
	@DisplayName("Testing Model Mapper")
	void testModelMapper() {

		ModelMapper mapper = modelMapConfig.modelMapper();

		assertNotNull(mapper);
	}
}
