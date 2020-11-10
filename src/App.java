import ChessPackage.*;
import ChessPackage.Pieces.*;

public class App {
    public static void main(String[] args) {
        Board b = new Board();
        System.out.println(b.getSquare(0, 0).getPiece().legalMove(b, b.getSquare(0, 0), b.getSquare(7, 0)));
        System.out.println(b.getSquare("a1").getPiece().legalMove(b, b.getSquare("a1"), b.getSquare("a8")));
        System.out.println(b.getSquare("a1").getPiece().legalMove(b, "a1", "a8"));
        
    }
}