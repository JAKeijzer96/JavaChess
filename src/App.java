import ChessPackage.*;
// import ChessPackage.Pieces.*;
public class App {
    public static void main(String[] args) throws Exception {
        // Board b = new Board();
        // // The following four statements are equivalent, all check if a rook move from a1 to a8 is allowed
        // System.out.println("Rook moves from a1 to a8:");
        // System.out.println(b.getSquare(0, 0).getPiece().legalMove(b, b.getSquare(0, 0), b.getSquare(0, 7)));
        // System.out.println(b.getSquare("a1").getPiece().legalMove(b, b.getSquare("a1"), b.getSquare("a8")));
        // System.out.println(b.getPiece(0, 0).legalMove(b, "a1", "a8"));
        // System.out.println(b.getPiece("a1").legalMove(b, "a1", "a8"));

        // System.out.println("W Rook from a3 to h3: " + b.getPiece("a1").legalMove(b, "a3", "h3"));
        // System.out.println("W Rook from e3 to e7: " + b.getPiece("a1").legalMove(b, "e3", "e7"));
        // System.out.println("W Rook from e2 to e7: " + b.getPiece("a1").legalMove(b, "e2", "e7"));
        // System.out.println("W Rook from e7 to e3: " + b.getPiece("a1").legalMove(b, "e7", "e3"));
        // System.out.println("B rook from e6 to e3: " + b.getPiece("a8").legalMove(b, "e6", "e3"));

        // System.out.println("W bishop from e3 to h6: " + b.getPiece("c1").legalMove(b, "e3", "h6"));
        // System.out.println("W bishop from e4 to h7: " + b.getPiece("c1").legalMove(b, "e4", "h7"));
        // System.out.println("B bishop from h7 to e4: " + b.getPiece("c8").legalMove(b, "h7", "e4"));
        // System.out.println("B bishop from a6 to c4: " + b.getPiece("c8").legalMove(b, "a6", "c4"));

        // System.out.println("W queen from e2 to e7: " + b.getPiece("d1").legalMove(b, "e2", "e7"));
        // System.out.println("B queen from e2 to e7: " + b.getPiece("d8").legalMove(b, "e2", "e7"));
        // System.out.println("W queen from e2 to a6: " + b.getPiece("d1").legalMove(b, "e2", "a6"));

        // System.out.println("W pawn from e2 to e4 first move: " + b.getPiece("e2").legalMove(b, "e2", "e4"));
        // System.out.println("W pawn from e2 to e3: " + b.getPiece("e2").legalMove(b, "e2", "e3"));
        // System.out.println("B pawn from e7 to e6: " + b.getPiece("e7").legalMove(b, "e7", "e6"));
        // System.out.println("B pawn from e7 to e5 second move: " + b.getPiece("e7").legalMove(b, "e7", "e5"));
        // System.out.println("B pawn from e4 to e2 first move:" + b.getPiece("a7").legalMove(b, "e4", "e2"));

        // System.out.println("W pawn from e5 to d6: " + b.getPiece("e2").legalMove(b, "e5", "d6"));
        // System.out.println("W pawn from e6 to e7: " + b.getPiece("e2").legalMove(b, "e6", "e7"));
        // System.out.println("W pawn from e6 to d7: " + b.getPiece("e2").legalMove(b, "e6", "d7"));
        // System.out.println("W pawn from e6 to f7: " + b.getPiece("e2").legalMove(b, "e6", "f7"));

        // System.out.println("B pawn from e4 to d3: " + b.getPiece("e7").legalMove(b, "e4", "d3"));
        // System.out.println("B pawn from e3 to e2: " + b.getPiece("e7").legalMove(b, "e3", "e2"));
        // System.out.println("B pawn from e3 to d2: " + b.getPiece("e7").legalMove(b, "e3", "d2"));
        // System.out.println("B pawn from e3 to f2: " + b.getPiece("e7").legalMove(b, "e3", "f2"));


        // System.out.println(b.getPiece("e7").legalMove(b, "e7", "e8"));
        // Board test = new Board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR");
        // for(int i = 0; i < test.getBoard().length; i++) {
        //     for(int j = 0; j < test.getBoard().length; j++) {
        //         System.out.println(test.getSquare(j, i));
        //     }
        // }
        // Complete FEN: 5k2/ppp5/4P3/3R3p/6P1/1K2Nr2/PP3P2/8 b - - 1 32
        // Board test2 = new Board("5k2/ppp5/4P3/3R3p/6P1/1K2Nr2/PP3P2/8");
        // for(int i = 0; i < test2.getBoard().length; i++) {
        //     for(int j = 0; j < test2.getBoard().length; j++) {
        //         System.out.println(test2.getSquare(j, i));
        //     }
        // }
        // System.out.println(test2);

        Player white = new Player('W', "Jasper");
        Player black = new Player('B', "Computer");
        Game game = new Game(white, black);
        game.makeMove("e2", "e4");
        game.makeMove("e7", "e5");
        game.makeMove("g1", "f3");
        game.makeMove("b8", "c6");
        // System.out.println(game.getBoard().getPiece("f1").legalMove(game.getBoard(), "f1", "c4"));
        // System.out.println(game.getBoard().getPiece("f1"));
        // System.out.println(game.getBoard().getPiece("e2"));
        // System.out.println(game.getBoard().getPiece("d3"));
        // System.out.println(game.getBoard().getPiece("c4"));
        game.makeMove("f1", "c4");
        game.makeMove("g8", "f6");
        game.makeMove("f3", "g5");
        System.out.println(game.getMoveList());
        System.out.println(game);
        // Board test3 = new Board("r4rk1/1p3p2/pp3qpp/4p3/3n4/3Q1P1P/PP3P2/R2B1R1K");
        // for(int i = 0; i < test3.getBoard().length; i++) {
        //     for(int j = 0; j < test3.getBoard().length; j++) {
        //         System.out.println(test3.getSquare(j, i));
        //     }
        // }
        // System.out.println("Pawn moves:");
        // System.out.println(b.getPiece("e2").legalMove(b, "e2", "e4"));
        // System.out.println(b.getPiece("e2").legalMove(b, "e2", "e3"));
        // System.out.println(b.getPiece("e2").legalMove(b, "e2", "e1"));
        // System.out.println(b.getPiece("e7").legalMove(b, "e7", "e5"));

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