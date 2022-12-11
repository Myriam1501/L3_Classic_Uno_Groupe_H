package fr.pantheonsorbonne.miage.enums;

/**
 * List the possible colors of a card
 */
public enum CardColor {
    BLEU(127137),
    RED(127137 + 16),
    YELLOW(127137 + 16 * 2),
    GREEN(127137 + 16 * 3),
    WHITE(127137 + 16 * 3);

    private final int code;

    CardColor(int code) {
        this.code = code;
    }
    /**
     * This function checks the existence of this color in the list of colors
     * @param substring The color will be check
     * @return the color you want
     */
    public static CardColor valueOfStr(String substring) {
        for (CardColor color : CardColor.values()) {
            if (color.name().substring(0, 1).equals(substring)) {
                return color;
            }
        }
        throw new RuntimeException("No Such Color");
    }
    /**
     * This function is to return the first letter of the String type color
     * @return the first letter of the color 
     */
    
    public String getStringRepresentation() {
        return "" + this.name().charAt(0);
    }
}
