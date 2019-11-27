package sheet_11;
import java.util.Scanner;


public class PalindromeTester {

	public static boolean isPalindrome(String aWord) {
		System.out.println(aWord);
		if (aWord.length() == 1 ||aWord.length() == 0 || aWord == null) {
			return true;
		} else {
			if (aWord.toLowerCase().charAt(0) == aWord.toLowerCase().charAt(aWord.length() - 1) && aWord.length() > 1) {
				return isPalindrome(aWord.substring(1, aWord.length() - 1));
			} else {
				if (aWord.charAt(0) >= 32 && aWord.charAt(0) <= 47) {
					while (aWord.charAt(0) >= 32 && aWord.charAt(0) <= 47) {
						aWord = aWord.substring(1, aWord.length());
					}
					return isPalindrome(aWord);

				} else if (aWord.charAt(aWord.length() - 1) >= 32 && aWord.charAt(aWord.length() - 1) <= 47) {
					while (aWord.charAt(aWord.length() - 1) >= 32 && aWord.charAt(aWord.length() - 1) <= 47) {
						aWord = aWord.substring(0, aWord.length() - 1);
					}
					return isPalindrome(aWord);

				} else {
					return false;
				}
			}
		}
	}

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		System.out.println("Please enter a word: ");
		String palindrome = scan.nextLine();
		scan.close();
		System.out.println(isPalindrome(palindrome));
	}

}
