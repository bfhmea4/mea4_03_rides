package com.spring.webtest;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class WebTestApplicationTests {

	private FizzBuzz fb = new FizzBuzz();

	@Test
	void oneReturnsOneAsString() {
		assertEquals(fb.fizzBuzz(1), "1");
	}

	@Test
	void integerReturnsIntegerAsString() {
		assertEquals(fb.fizzBuzz(7), "7");
	}

	@Test
	void threeReturnsFizz() {
		assertEquals(fb.fizzBuzz(3), "Fizz");
	}

	@Test
	void dividableByThreeReturnsFizz() {
		assertEquals(fb.fizzBuzz(27), "Fizz");
	}

	@Test
	void fiveReturnsBuzz() {
		assertEquals(fb.fizzBuzz(5), "Buzz");
	}

	@Test
	void dividableByFiveReturnsBuzz() {
		assertEquals(fb.fizzBuzz(25), "Buzz");
	}

	@Test
	void fifteenReturnsFizzBuzz() {
		assertEquals(fb.fizzBuzz(15), "FizzBuzz");
	}

	@Test
	void dividableByFiveAndThreeReturnsFizzBuzz() {
		assertEquals(fb.fizzBuzz(45), "FizzBuzz");
	}

}
