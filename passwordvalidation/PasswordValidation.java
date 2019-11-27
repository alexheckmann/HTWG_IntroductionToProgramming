package passwordvalidation;

import java.util.Scanner;

public class PasswordValidation {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter your password:");
        String password = sc.nextLine();
        sc.close();
        String regExPassword = "[A-Za-z]\\w{3,14}";

        if (password.matches(regExPassword)) {
            System.out.println("Password valid");
        } else {
            System.out.println("Password invalid");
        }
    }
}
