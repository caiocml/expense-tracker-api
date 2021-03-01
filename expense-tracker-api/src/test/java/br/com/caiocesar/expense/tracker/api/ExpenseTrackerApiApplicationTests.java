package br.com.caiocesar.expense.tracker.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.caiocesar.expense.tracker.api.util.Crypto;

@SpringBootTest
class ExpenseTrackerApiApplicationTests {

	@Test
	void contextLoads() {
	}
	
	@Test
	public void testCrypto() {
		
		String cryptoTest = "someStringToDigest";
		String cryptonTestTheSecond = new String(cryptoTest);
		assertEquals(Crypto.encrypt(cryptoTest), Crypto.encrypt(cryptonTestTheSecond));
	}
}
