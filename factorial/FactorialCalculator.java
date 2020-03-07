package exercise10.task02;

public class FactorialCalculator {

    public static long faculty(int n) throws NotANaturalNumberException, NumberTooBigException {
        if (n < 0){
            throw new NotANaturalNumberException("The faculty function is not defined for negative integers.");
        } else if (n > 20) {
            throw new NumberTooBigException("The result of fac(n = " + n + ") is too big to be stored in the long data type.");
        }

        if (n == 0) {
            return 1;
        } else {
            return n * faculty(n - 1);
        }
    }

}
