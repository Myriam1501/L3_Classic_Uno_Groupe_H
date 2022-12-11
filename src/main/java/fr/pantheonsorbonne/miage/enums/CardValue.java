package fr.pantheonsorbonne.miage.enums;

/**
 * An enum that represend the possible cards value from a deck
 */
public enum CardValue {
    /**
     * StringRepresentation is the number of Card
     * Value is the score of Card
     */
    ZERO("0", 0),
    ONE("1", 1),
    TWO("2", 2),
    THREE("3", 3),
    FOUR("4", 4),
    FIVE("5", 5),
    SIX("6", 6),
    SEVEN("7", 7),
    EIGHT("8", 8),
    NINE("9", 9),
    INVERSE("INVERSER", 20),
    SKIPTURN("SKIPTURN", 20),
    PLUS2("PLUS2", 20),
    JOKER("JOKER", 50),
    PLUS4("PLUS4", 50);

    final private String stringRepresentation;
    final private int rank;

    CardValue(String stringRepresentation, int value) {
        this.stringRepresentation = stringRepresentation;
        this.rank = value;
    }

    /**
     * From a string representation, return the cad
     *
     * @param str
     * @return the corresponding card
     * @throws RuntimeException if the representation is invalid
     */
    
    public static CardValue valueOfStr(String str) {
        for (CardValue value : CardValue.values()) {
            if (str.equals(value.getStringRepresentation())) {
                return value;
            }
        }
        throw new RuntimeException("failed to parse value");
    }
    /**
     * This function is to return StringRepresentation
     * @return StringRepresentation
     */
    public String getStringRepresentation() {
        return stringRepresentation;
    }

    /**
     * the rank of the card for comparison purpose. The higher the rank, the better the card
     *
     * @return
     */
    public int getRank() {
        return rank;
    }
}
