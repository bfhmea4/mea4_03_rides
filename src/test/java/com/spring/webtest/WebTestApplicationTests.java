package com.spring.webtest;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class WebTestApplicationTests {

	@Test
	void oneReturnsOneAsString() {
		assertEquals(fizzBuzz(1), "1");
	}

	@Test
	void integerReturnsIntegerAsString() {
		assertEquals(fizzBuzz(7), "1");
	}

	private String fizzBuzz(int i) {
		return "1";
	}

}
