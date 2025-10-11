package com.harshit.notes;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class NotesApplicationTests {

	@Disabled
	@Test
	void contextLoads() {
	}

	@Disabled
	//@CsvFileSource
	@ParameterizedTest
	@CsvSource({
			"1,1,2",
			"2,3,5",
			"3,3,3"
	})
	public void add(int a,int b,int expected){
		assertEquals(expected,a + b);
	}

}
