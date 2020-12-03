package b_Money;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AccountTest {
	Currency SEK, DKK;
	Bank Nordea;
	Bank DanskeBank;
	Bank SweBank;
	Account testAccount;

	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		SweBank.openAccount("Annie");
		testAccount = new Account("Hans", SEK);
		testAccount.deposit(new Money(1000, SEK));

		//SweBank.deposit("Alice", new Money(1000000, SEK)); ------> exception here
	}

	@Test
	public void testAddRemoveTimedPayment() {
		testAccount.addTimedPayment("1",1,1, new Money(515, DKK), DanskeBank,"Daniel");
		assertTrue(testAccount.timedPaymentExists("1"));

		testAccount.removeTimedPayment("1");
		assertFalse(testAccount.timedPaymentExists("1"));
		assertFalse(testAccount.timedPaymentExists("2"));
		assertFalse(testAccount.timedPaymentExists("3"));
	}

	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
		assertEquals(Integer.valueOf(1000), testAccount.getBalance().getAmount());

		testAccount.addTimedPayment("1", 1, 1, new Money(100, SEK), SweBank, "Annie");

		for (int i = 0; i < 10; i++) {
			testAccount.tick();
		}

		assertEquals(Integer.valueOf(500), testAccount.getBalance().getAmount());
	}

	@Test
	public void testAddWithdraw() {
		testAccount.withdraw(new Money(500, SEK));
		assertEquals(Integer.valueOf(500), testAccount.getBalance().getAmount());
	}

	@Test
	public void testGetBalance() {
		assertEquals(Integer.valueOf(1000), testAccount.getBalance().getAmount());
	}
}
