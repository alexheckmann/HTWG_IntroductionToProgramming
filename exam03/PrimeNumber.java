package exam01;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PrimeNumber {

    private static int usages = 0;
    private static final int MAX_TRIAL_USAGES = 3;

    private static boolean isPrime(int number) throws TrialVersionException {
        if (usages >= MAX_TRIAL_USAGES)
            throw new TrialVersionException("Limit exceeded. Trial versions may only test up to three numbers.");
        usages++;
        if (number < 2) return false;
        for (int i = 2; i < number; i++) {
            if (number % i == 0) return false;
        }

        return true;
    }

    @Before
    public void resetCounter() {
        usages = 0;
    }

    @Test
    public void testIsPrime() throws TrialVersionException {
        PrimeNumber primeNumber = new PrimeNumber();

        boolean isPrime = primeNumber.isPrime(2);

        assertTrue(isPrime);
    }

    @Test
    public void testIsPrime_tooSmall() throws TrialVersionException {
        PrimeNumber primeNumber = new PrimeNumber();

        boolean isPrime = primeNumber.isPrime(1);

        assertFalse(isPrime);
    }

    @Test
    public void testIsPrime_normalNumber() throws TrialVersionException {
        PrimeNumber primeNumber = new PrimeNumber();

        boolean isPrime = primeNumber.isPrime(4);

        assertFalse(isPrime);
    }

    @Test(expected = TrialVersionException.class)
    public void testIsPrime_trialExceeded() throws TrialVersionException {
        PrimeNumber primeNumber = new PrimeNumber();
        for (int i = 0; i < 3; i++) {
            primeNumber.isPrime(2);
        }

        boolean isPrime = primeNumber.isPrime(2);
    }

    public static class TrialVersionException extends Exception {
        public TrialVersionException(String message) {
            super(message);
        }
    }
}