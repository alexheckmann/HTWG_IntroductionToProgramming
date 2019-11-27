package enigma;

public class Rotor {

    private int rotorNumber;
    private final int ALPHABET_LENGTH = 26;
    private final char[] ROTOR_NUMBER_50 = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
            'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    private final char[] ROTOR_NUMBER_51 = {'A', 'D', 'C', 'B', 'E', 'H', 'F', 'G', 'I', 'L', 'J', 'K', 'M', 'P', 'N', 'O',
            'Q', 'T', 'R', 'S', 'U', 'X', 'V', 'W', 'Z', 'Y'};
    private final char[] ROTOR_NUMBER_60 = {'A', 'C', 'E', 'D', 'F', 'H', 'G', 'I', 'K', 'J', 'L', 'N', 'M', 'O', 'Q', 'P',
            'R', 'T', 'S', 'U', 'W', 'V', 'X', 'Z', 'Y', 'B'};
    private final char[] ROTOR_NUMBER_61 = {'A', 'Z', 'X', 'V', 'T', 'R', 'P', 'N', 'D', 'J', 'H', 'F', 'L', 'B', 'Y', 'W',
            'U', 'S', 'Q', 'O', 'M', 'K', 'I', 'G', 'E', 'C'};
    private final char[] ROTOR_NUMBER_70 = {'A', 'Z', 'Y', 'X', 'W', 'V', 'U', 'T', 'S', 'R', 'Q', 'P', 'O', 'N', 'M', 'L',
            'K', 'J', 'I', 'H', 'G', 'F', 'E', 'D', 'C', 'B'};
    private final char[] ROTOR_NUMBER_71 = {'A', 'E', 'B', 'C', 'D', 'F', 'J', 'G', 'H', 'I', 'K', 'O', 'L', 'M', 'N', 'P',
            'T', 'Q', 'R', 'S', 'U', 'Y', 'V', 'W', 'X', 'Z'};

    protected Rotor(int number) {
        this.rotorNumber = number;
    }

    private char[] getRotor() {

        char[] desiredRotor = new char[ALPHABET_LENGTH];
        switch (rotorNumber) {
            case 50:
                desiredRotor = ROTOR_NUMBER_50;
                break;
            case 51:
                desiredRotor = ROTOR_NUMBER_51;
                break;
            case 60:
                desiredRotor = ROTOR_NUMBER_60;
                break;
            case 61:
                desiredRotor = ROTOR_NUMBER_61;
                break;
            case 70:
                desiredRotor = ROTOR_NUMBER_70;
                break;
            case 71:
                desiredRotor = ROTOR_NUMBER_71;
                break;
            default:
                System.out.println("Unknown rotor number.");
                System.exit(rotorNumber);
        }
        return desiredRotor;
    }

    /**
     *
     * @param charStart
     * @return
     */
    private int clockwiseStartPosition(char charStart) {
        int startPosition = 0;
        for (int startIndex = 0; startIndex < ALPHABET_LENGTH; startIndex++) {
            if (getRotor()[startIndex] == charStart) {
                startPosition = startIndex;
            }
        }

        return startPosition;
    }

    /**
     *
     * @param charStart
     * @return starting position
     */
    private int counterClockwiseStartPosition(char charStart) {
        int startPosition = 0;
        for (int startIndex = ALPHABET_LENGTH - 1; startIndex > 0; startIndex--) {
            if (getRotor()[startIndex] == charStart) {
                startPosition = startIndex;
            }
        }

        return startPosition;
    }

    /**
     * method to
     * @param charStart
     * @param charEnd
     * @return
     */
    protected int countClockwiseRotations(char charStart, char charEnd) {
        int positionCount = 0;

        int position = clockwiseStartPosition(charStart);
        // rotate until desired letter is reached
        do {
            if (charStart != charEnd) {
                positionCount++;
                position++;
            }
        }
        // simulating rotation by calculating % 26
        while (getRotor()[position % ALPHABET_LENGTH] != charEnd);

        return positionCount;
    }

    /**
     *
     * @param charStart
     * @param charEnd
     * @return position
     */
    protected int countCounterClockwiseRotations(char charStart, char charEnd) {
        int positionCount = ALPHABET_LENGTH;

        int position = counterClockwiseStartPosition(charStart);

        // rotate until desired letter is reached
        do {
            if (charStart != charEnd) {
                positionCount--;
                position++;
            }
        } while (getRotor()[position % ALPHABET_LENGTH] != charEnd);

        return positionCount;
    }

    /**
     *
     * @param charStart
     * @param rotation
     * @return
     */
    protected char rotateClockwise(char charStart, int rotation) {

        // find rotor position after rotating
        int endPosition = clockwiseStartPosition(charStart) + rotation;
        char charEnd = getRotor()[endPosition % ALPHABET_LENGTH];
        return charEnd;
    }

    /**
     *
     * @param charStart
     * @param rotation
     * @return 
     */
    protected char rotateCounterClockwise(char charStart, int rotation) {

        int endPosition = counterClockwiseStartPosition(charStart) - rotation;
        // add 26 to avoid negative index
        if (endPosition < 0) {
            endPosition += ALPHABET_LENGTH;
        }
        char charEnd = getRotor()[endPosition % ALPHABET_LENGTH];
        return charEnd;
    }
}
