package emailaddress;

import java.util.Scanner;

public class Registration {
    private static String suggestUsername(String emailAddress) {
        String username = emailAddress.substring(0, emailAddress.indexOf('@'));
        return username;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter your email:");
        String emailAddress = sc.nextLine();
        sc.close();
        String regExEmail = ".{3,15}@.*\\.(de|org|ch)";

        if (emailAddress.matches(regExEmail)) {
            System.out.println("Email valid");
            String regExName = regExEmail.substring(0,regExEmail.indexOf('@'));
            String suggestion = suggestUsername(emailAddress);
            if (suggestion.matches(regExName)) {
                System.out.println("Username suggestion: " + suggestion);
            }
        } else {
            System.out.println("Email invalid");
        }
    }
}
