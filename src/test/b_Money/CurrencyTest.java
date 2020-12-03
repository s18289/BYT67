package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CurrencyTest {
	Currency SEK, DKK, NOK, EUR;

	@Before
	public void setUp() throws Exception {
		/* Setup currencies with exchange rates */
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
		NOK = new Currency("NOK", 3.2);
	}

	@Test
	public void testGetName() {
		assertEquals("SEK", SEK.getName());
		assertEquals("DKK", DKK.getName());
		assertEquals("EUR", EUR.getName());
		assertEquals("NOK", NOK.getName());
	}

	@Test
	public void testGetRate() {
		assertEquals(0.15, SEK.getRate(), 0);
		assertEquals(0.20, DKK.getRate(), 0);
		assertEquals(1.5, EUR.getRate(), 0);
		assertEquals(3.2, NOK.getRate(), 0);
	}

	@Test
	public void testSetRate() {
		SEK.setRate(0.30);
		DKK.setRate(0.40);
		EUR.setRate(1.7);
		NOK.setRate(2.2);

		assertEquals(0.30,SEK.getRate(),0);
		assertEquals(0.40,DKK.getRate(),0);
		assertEquals(1.7,EUR.getRate(),0);
		assertEquals(2.2,NOK.getRate(),0);
	}

	@Test
	public void testGlobalValue() {
		assertEquals(Integer.valueOf(750), SEK.universalValue(50));
		assertEquals(Integer.valueOf(200), DKK.universalValue(10));
		assertEquals(Integer.valueOf(150), EUR.universalValue(1));
		assertEquals(Integer.valueOf(3200), NOK.universalValue(10));
	}

	@Test
	public void testValueInThisCurrency() {
		assertEquals(Integer.valueOf(133), SEK.valueInThisCurrency(100, DKK));
		assertEquals(Integer.valueOf(750), DKK.valueInThisCurrency(100, EUR));
		assertEquals(Integer.valueOf(213), EUR.valueInThisCurrency(100, NOK));
		assertEquals(Integer.valueOf(4), NOK.valueInThisCurrency(100, SEK));
	}
}
