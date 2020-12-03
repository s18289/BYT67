package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BankTest {
	Currency SEK, DKK;
	Bank SweBank, Nordea, DanskeBank;

	@Before
	public void setUp() throws Exception {
		DKK = new Currency("DKK", 0.20);
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		Nordea = new Bank("Nordea", SEK);
		DanskeBank = new Bank("DanskeBank", DKK);
		SweBank.openAccount("Ulrika");
		SweBank.openAccount("Bob");
		Nordea.openAccount("Bob");
		DanskeBank.openAccount("Gertrud");
	}

	@Test
	public void testGetName() {
		assertEquals("SweBank", SweBank.getName());
		assertEquals("Nordea", Nordea.getName());
		assertEquals("DanskeBank", DanskeBank.getName());
	}

	@Test
	public void testGetCurrency() {
		assertEquals(SEK, SweBank.getCurrency());
		assertEquals(SEK, Nordea.getCurrency());
		assertEquals(DKK, DanskeBank.getCurrency());
	}

	@Test
	public void testOpenAccount() throws AccountExistsException, AccountDoesNotExistException {
		SweBank.openAccount("TestOne");
		Nordea.openAccount("AnotherOne");
	}

	@Test
	public void testDeposit() throws AccountDoesNotExistException {
		SweBank.deposit("Bob", new Money(150 , SEK));
		assertEquals(Integer.valueOf(150), SweBank.getBalance("Bob"));

		Nordea.deposit("Bob", new Money(512 , SEK));
		assertEquals(Integer.valueOf(512), Nordea.getBalance("Bob"));
	}

	@Test
	public void testWithdraw() throws AccountDoesNotExistException {
		SweBank.deposit("Bob", new Money(150 , SEK));
		assertEquals(Integer.valueOf(150), SweBank.getBalance("Bob"));

		SweBank.withdraw("Bob", new Money(50 , SEK));
		assertEquals(Integer.valueOf(100), SweBank.getBalance("Bob"));

		Nordea.deposit("Bob", new Money(512 , SEK));
		assertEquals(Integer.valueOf(512), Nordea.getBalance("Bob"));

		Nordea.withdraw("Bob", new Money(112 , SEK));
		assertEquals(Integer.valueOf(400), Nordea.getBalance("Bob"));
	}

	@Test
	public void testGetBalance() throws AccountDoesNotExistException {
		SweBank.deposit("Bob", new Money(1000 , SEK));
		Nordea.deposit("Bob", new Money(512 , SEK));

		assertEquals(Integer.valueOf(1000), SweBank.getBalance("Bob"));
		assertEquals(Integer.valueOf(512), Nordea.getBalance("Bob"));
	}

	@Test
	public void testTransfer() throws AccountDoesNotExistException {

		SweBank.deposit("Bob", new Money(1000 , SEK));
		SweBank.transfer("Bob", SweBank,"Ulrika", new Money(150, SEK));

		assertEquals(Integer.valueOf(150), SweBank.getBalance("Ulrika"));
		assertEquals(Integer.valueOf(850), SweBank.getBalance("Bob"));
	}

	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
		SweBank.addTimedPayment("Ulrika", "1", 1, 1, new Money(100, SEK), SweBank, "Bob");
		for (int i = 0; i < 10; i++) {
			SweBank.tick();
		}
		assertEquals(Integer.valueOf(-500), SweBank.getBalance("Ulrika"));
		assertEquals(Integer.valueOf(500), SweBank.getBalance("Bob"));
	}
}
