// Inspiration: https://www.geeksforgeeks.org/design-a-chess-game/

import ChessPackage.*;
public class App {

// castling
// en passant
// approx. a gazillion tests
// javafx

    public static void main(String[] args) {
        System.out.println("Main function of App");

        // Game game = new Game("r3k2r/p4ppp/1pN5/n2P4/8/7P/P4qPB/R2R3K w - - 0 1");
        // game.makeMove("d1", "e1");
        // game.makeMove("f2", "e1");
        // game.makeMove("a1", "e1");
        // game.makeMove("e8", "f8");
        // game.makeMove("h2", "d6");
        // game.makeMove("f8", "g8");
        // game.makeMove("c6", "e7");
        // game.makeMove("g8", "f8");
        // game.makeMove("e7", "c8");
        // game.makeMove("f8", "g8");
        // game.makeMove("e1", "e8");
        // System.out.println(game.getMoveList());

        Game whiteCastleKingside = new Game("r3k2r/pppppppp/8/8/8/8/PPPPPPPP/R3K2R w KQkq - 0 1");
        whiteCastleKingside.makeMove("e1", "g1");
        System.out.println(whiteCastleKingside.getMoveList());
        System.out.println(whiteCastleKingside.getWhiteKingSquare());
        Game whiteCastleQueenside = new Game("r3k2r/pppppppp/8/8/8/8/PPPPPPPP/R3K2R w KQkq - 0 1");
        whiteCastleQueenside.makeMove("e1", "a1");
        System.out.println(whiteCastleQueenside.getMoveList());
        System.out.println(whiteCastleQueenside.getWhiteKingSquare());
        Game blackCastleKingside = new Game("r3k2r/pppppppp/8/8/8/8/PPPPPPPP/R3K2R b KQkq - 0 1");
        blackCastleKingside.makeMove("e8", "h8");
        System.out.println(blackCastleKingside.getMoveList());
        System.out.println(blackCastleKingside.getBlackKingSquare());
        Game blackCastleQueenside = new Game("r3k2r/pppppppp/8/8/8/8/PPPPPPPP/R3K2R b KQkq - 0 1");
        blackCastleQueenside.makeMove("e8", "b8");
        System.out.println(blackCastleQueenside.getMoveList());
        System.out.println(blackCastleQueenside.getBlackKingSquare());
        Game whiteCastleThroughCheck = new Game("r3k2r/pppppppp/8/8/3q4/8/PPPPP1PP/R3K2R w KQkq - 0 1");
        whiteCastleThroughCheck.makeMove("e1", "h1");
        System.out.println(whiteCastleThroughCheck.getMoveList());
        System.out.println(whiteCastleThroughCheck.getWhiteKingSquare());
        System.out.println(whiteCastleThroughCheck);

        Game whiteCastleQueenside960 = new Game("rkqbrnbn/pp3pp1/2p1p2p/3p4/B2P4/2P1Q3/PP2PPPP/RK2RNBN w EAea - 0 5");
        whiteCastleQueenside960.makeMove("b1", "a1");
        System.out.println(whiteCastleQueenside960.getMoveList());
        System.out.println(whiteCastleQueenside960.getWhiteKingSquare());
        System.out.println(whiteCastleQueenside960);
        Game whiteCastleKingside960 = new Game("q1nrbbkr/pp1nppp1/3p3p/2p5/4PP2/3B2B1/PPPP2PP/QNNR2KR w HDhd - 2 5");
        whiteCastleKingside960.makeMove("g1", "h1");
        System.out.println(whiteCastleKingside960.getMoveList());
        System.out.println(whiteCastleKingside960.getWhiteKingSquare());
        System.out.println(whiteCastleKingside960);
    }
}