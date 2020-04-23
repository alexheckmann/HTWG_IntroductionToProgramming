import java.util.Scanner;

/**
 * @author Alexander Heckmann
 */
public class PalindromeTester {

    /**
     * Checks whether a given word is a palindrome.
     * A palindrome is a word, phrase, or sequence that reads the same backwards as forwards.
     *
     * @param word The word to be checked
     * @return Whether or not a word is a palindrome.
     */
    public static boolean isPalindrome(String word) {

        if (word == null) {
            throw new IllegalArgumentException();
        }

        if (word.length() == 1 || word.length() == 0) {
            return true;
        } else {
            if (word.toLowerCase().charAt(0) == word.toLowerCase().charAt(word.length() - 1)) {
                return isPalindrome(word.substring(1, word.length() - 1));
            } else {
                if (word.charAt(0) >= 32 && word.charAt(0) <= 47) {
                    while (word.charAt(0) >= 32 && word.charAt(0) <= 47) {
                        word = word.substring(1);
                    }
                    return isPalindrome(word);

                } else if (word.charAt(word.length() - 1) >= 32 && word.charAt(word.length() - 1) <= 47) {
                    while (word.charAt(word.length() - 1) >= 32 && word.charAt(word.length() - 1) <= 47) {
                        word = word.substring(0, word.length() - 1);
                    }
                    return isPalindrome(word);

                } else {
                    return false;
                }
            }
        }
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter a word: ");
        String word = scan.nextLine();
        scan.close();

        if (isPalindrome(word)) {
            System.out.println("It is a palindrome.");
        } else {
            System.out.println("It's not a palindrome.");
        }

    }

}
