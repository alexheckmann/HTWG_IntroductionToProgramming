package enigma;

import java.util.Scanner;

public class Enigma {
    private Rotor[] rotors;
    private String configuration;

    /**
     *
     * @param leftRotor
     * @param centralRotor
     * @param rightRotor
     * @param key
     */
    public Enigma(int leftRotor, int centralRotor, int rightRotor, String key) {
        this.rotors = new Rotor[]{new Rotor(leftRotor), new Rotor(centralRotor), new Rotor(rightRotor)};
        this.configuration = key;
    }

    /**
     *
     * @param uncodedText
     * @return encrypted text
     */
    private String encrypt(String uncodedText) {
        char encryptedChar;
        String encryptedText = "";
        int rotation;
        for (int encryptionIndex = 0; encryptionIndex < uncodedText.length(); encryptionIndex++) {
            if (encryptionIndex % 2 == 0) {
                rotation = rotors[0].countClockwiseRotations(configuration.charAt(0), uncodedText.charAt(encryptionIndex));
                encryptedChar = rotors[2].rotateClockwise(configuration.charAt(2), rotation);
            } else {
                rotation = rotors[1].countCounterClockwiseRotations(configuration.charAt(1),
                        uncodedText.charAt(encryptionIndex));
                encryptedChar = rotors[2].rotateClockwise(configuration.charAt(2), rotation);

            }
            encryptedText += encryptedChar;
        }

        return encryptedText;
    }

    /**
     * @param cipherText
     * @return
     */
    private String decrypt(String cipherText) {
        char decryptedChar;
        String decryptedText = "";
        int rotation;
        for (int decryptionIndex = 0; decryptionIndex < cipherText.length(); decryptionIndex++) {
            if (decryptionIndex % 2 == 0) {
                rotation = rotors[2].countCounterClockwiseRotations(configuration.charAt(2),
                        cipherText.charAt(decryptionIndex));
                decryptedChar = rotors[0].rotateCounterClockwise(configuration.charAt(0), rotation);
            } else {
                rotation = rotors[2].countCounterClockwiseRotations(configuration.charAt(2),
                        cipherText.charAt(decryptionIndex));
                decryptedChar = rotors[1].rotateClockwise(configuration.charAt(1), rotation);
            }
            decryptedText += decryptedChar;
        }

        return decryptedText;
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter the text you want to encrypt!");
        String text = scan.nextLine().toUpperCase();
        System.out.println("Please enter the key.");
        String key = scan.nextLine().toUpperCase();
        System.out.println("Please enter your configuration.");
        int leftRotor = scan.nextInt();
        int centralRotor = scan.nextInt();
        int rightRotor = scan.nextInt();
        Enigma enigma = new Enigma(leftRotor, centralRotor, rightRotor, key);

        System.out.println("Would you like to encrypt or decrypt? Press:" + "\n" + "(1) for encryption / (2) for decryption.");
        String convertedText = "";
        int methodSwitch = scan.nextInt();
        switch (methodSwitch) {
            case 1:
                convertedText = enigma.encrypt(text);
                break;
            case 2:
                convertedText = enigma.decrypt(text);
                break;
            default:
                System.out.println("Please enter a valid number!");
                System.exit(methodSwitch);
        }

        System.out.println(convertedText);
        scan.close();
    }
}
