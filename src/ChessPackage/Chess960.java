package ChessPackage;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

// Credit for regex-related code: https://rosettacode.org/wiki/Generate_Chess960_starting_position#Java

public class Chess960{
    private static List<Character> pieces = Arrays.asList('R','B','N','Q','K','N','B','R');

    public static String generatePosition() {
        String firstRank = generateFirstRank();
        int queensideIndex = firstRank.indexOf("R");
        char queensideRook = (char) (queensideIndex + 65);
        char kingsideRook = (char) (firstRank.indexOf("R", queensideIndex + 1) + 65);
        return (firstRank.toLowerCase() + "/pppppppp/8/8/8/8/PPPPPPPP/" + firstRank
            + " w " + kingsideRook + queensideRook + Character.toLowerCase(kingsideRook)
            + Character.toLowerCase(queensideRook) + " - 0 1");
    }

    private static String generateFirstRank(){
        String firstRank = "";
        do {
            Collections.shuffle(pieces);
            // Remove brackets and commas from toString result
            firstRank = pieces.toString().replaceAll("[^A-Z]", "");
        } while(!isValid(firstRank));
        return firstRank;
    }

    private static boolean isValid(String firstRank){
        // Check that King is in between Rooks and Bishops have valid opposite-color places
        return firstRank.matches(".*R.*K.*R.*") && firstRank.matches(".*B(..|....|......|)B.*");
    }

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            System.out.println(generatePosition());
        }
    }
}
