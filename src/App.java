import ChessPackage.*;
// import ChessPackage.Pieces.*;
public class App {
    public static void main(String[] args) throws Exception {
        Board b = new Board();
        // The following four statements are equivalent, all check if a rook move from a1 to a8 is allowed
        // System.out.println("Rook moves from a1 to a8:");
        // System.out.println(b.getSquare(0, 0).getPiece().legalMove(b, b.getSquare(0, 0), b.getSquare(0, 7)));
        // System.out.println(b.getSquare("a1").getPiece().legalMove(b, b.getSquare("a1"), b.getSquare("a8")));
        // System.out.println(b.getPiece(0, 0).legalMove(b, "a1", "a8"));
        // System.out.println(b.getPiece("a1").legalMove(b, "a1", "a8"));

        // System.out.println("W Rook from a3 to h3: " + b.getPiece("a1").legalMove(b, "a3", "h3"));
        System.out.println("W Rook from e3 to e7: " + b.getPiece("a1").legalMove(b, "e3", "e7"));
        System.out.println("W Rook from e2 to e7: " + b.getPiece("a1").legalMove(b, "e2", "e7"));
        System.out.println("W Rook from e7 to e3: " + b.getPiece("a1").legalMove(b, "e7", "e3"));
        // System.out.println("B rook from e6 to e3: " + b.getPiece("a8").legalMove(b, "e6", "e3"));

        System.out.println("W bishop from e3 to h6: " + b.getPiece("c1").legalMove(b, "e3", "h6"));
        System.out.println("W bishop from e4 to h7: " + b.getPiece("c1").legalMove(b, "e4", "h7"));
        System.out.println("B bishop from h7 to e4: " + b.getPiece("c8").legalMove(b, "h7", "e4"));
        System.out.println("B bishop from a6 to c4: " + b.getPiece("c8").legalMove(b, "a6", "c4"));

        System.out.println("W queen from e2 to e7: " + b.getPiece("d1").legalMove(b, "e2", "e7"));
        System.out.println("B queen from e2 to e7: " + b.getPiece("d8").legalMove(b, "e2", "e7"));
        System.out.println("W queen from e2 to a6: " + b.getPiece("d1").legalMove(b, "e2", "a6"));
        // System.out.println("Queen moves:");
        // Piece p = b.getSquare("d1").getPiece();
        // System.out.println(p);
        // System.out.println(p.legalMove(b, "d1", "f3"));
        
        // System.out.println("Pawn moves:");
        // System.out.println(b.getPiece("e2").legalMove(b, "e2", "e4"));
        // System.out.println(b.getPiece("e2").legalMove(b, "e2", "e3"));
        // System.out.println(b.getPiece("e2").legalMove(b, "e2", "e1"));
        // System.out.println(b.getPiece("e7").legalMove(b, "e7", "e5"));
        // System.out.println(b.getPiece("e7").legalMove(b, "e7", "e8"));

        // System.out.println(b.getPiece("e1").legalMove(b, "e2", "e1"));
        // System.out.println(b.getPiece("f3"));

        // for (int i = 0; i < 8; i++) {
        //     System.out.println(b.getSquare(i, 0));
        // }
        // for (int i = 1; i < 9; i++) {
        //     System.out.println(b.getSquare(Character.toString(i+64)+8));
        // }
    }
}