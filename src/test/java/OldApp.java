// Inspiration: https://www.geeksforgeeks.org/design-a-chess-game/

import chess.*;
// import ChessPackage.Pieces.Piece;
// import ChessPackage.Pieces.*;
public class OldApp {
    public static void main(String[] args) {
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
        // System.out.println("W Rook from e7 to e2: " + b.getPiece("a1").legalMove(b, "e7", "e2"));
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
        // // Complete FEN: 5k2/ppp5/4P3/3R3p/6P1/1K2Nr2/PP3P2/8 b - - 1 32
        // Board test2 = new Board("5k2/ppp5/4P3/3R3p/6P1/1K2Nr2/PP3P2/8");
        // for(int i = 0; i < test2.getBoard().length; i++) {
        //     for(int j = 0; j < test2.getBoard().length; j++) {
        //         System.out.println(test2.getSquare(j, i));
        //     }
        // }
        // System.out.println(test2);

        Player white = new Player('W', "Jasper");
        Player black = new Player('B', "Computer");
        ChessGame game = new ChessGame(white, black);
        // for(Square s : game.getBoard().getSquaresOfPlayerColor('W'))
        //     System.out.print(s + "-" + s.getPiece() + ", ");
        // System.out.println();
        // for(Square s : game.getBoard().getSquaresOfPlayerColor('B'))
        //     System.out.print(s + "-" + s.getPiece() + ", ");
        // System.out.println();
        game.makeMove("e2", "e4");
        game.makeMove("e7", "e5");
        game.makeMove("g1", "f3");
        game.makeMove("b8", "c6");
        game.makeMove("f1", "c4");
        game.makeMove("g8", "f6");
        game.makeMove("f3", "g5");
        game.makeMove("d7", "d5");
        game.makeMove("e4", "d5");
        game.makeMove("f6", "d5");
        game.makeMove("g5", "f7");
        game.makeMove("e8", "f7");
        game.setCastlingAvailability("KQ");
        game.makeMove("d1", "f3");
        // FEN after f3: r1bq1b1r/ppp2kpp/2n5/3np3/2B5/5Q2/PPPP1PPP/RNB1K2R b KQ - 1 7
        game.makeMove("f7", "g8");
        game.makeMove("f3", "d5");
        game.makeMove("d8", "d5");
        game.makeMove("c4", "d5");
        game.makeMove("a7", "a6"); // not allowed because it doesnt remove check
        game.makeMove("c8", "e6");
        game.makeMove("d5", "e6");
        game.makeMove("g8", "f7"); // not allowed because it's mate already
        
        System.out.println(game.getMoveList());
        System.out.println(game);
        game.reverseLastMove();
        game.reverseLastMove();
        game.reverseLastMove();
        game.reverseLastMove();
        game.reverseLastMove();
        game.reverseLastMove();
        game.reverseLastMove();
        game.reverseLastMove();
        game.reverseLastMove();
        game.makeMove("g5", "f7");
        game.makeMove("e8", "f7");
        game.setCastlingAvailability("KQ");
        game.makeMove("d1", "f3");
        // FEN after f3: r1bq1b1r/ppp2kpp/2n5/3np3/2B5/5Q2/PPPP1PPP/RNB1K2R b KQ - 1 7
        game.makeMove("f7", "g8");
        game.makeMove("f3", "d5");
        game.makeMove("d8", "d5");
        game.makeMove("c4", "d5");
        game.makeMove("a7", "a6"); // not allowed because it doesnt remove check
        game.makeMove("c8", "e6");
        game.makeMove("d5", "e6");
        game.makeMove("g8", "f7"); // not allowed because it's mate already
        System.out.println(game.getMoveList());
        System.out.println(game);

        // ChessGame game2 = new ChessGame("r1bq1bkr/ppp3pp/2n5/3np3/2B5/5Q2/PPPP1PPP/RNB1K2R w KQ - 2 8");
        // System.out.println(game2);
        // ChessGame checkFromRight = new ChessGame("rnbqkbn1/pppppppp/8/3NR3/3B4/8/PPPPPPPr/RNBQK3 b Qq - 0 1");
        // checkFromRight.makeMove("h2", "h1");
        // ChessGame checkFromLeft = new ChessGame("rnbqkbn1/pppppppp/8/3NR3/2RBN3/3BQ3/rPPPPPP1/4K3 b q - 0 1");
        // checkFromLeft.makeMove("a2", "a1");
        // ChessGame checkFromUp = new ChessGame("rkb2r2/pppp1ppp/np6/Pb2K3/qn6/QB6/PPPP1PPP/RNB3NR b - - 0 1");
        // checkFromUp.makeMove("f8", "e8");
        // ChessGame checkFromDown = new ChessGame("rkb5/pppp1ppp/np6/Pb2K3/qn6/QB6/PPPP1PPP/RNB2rNR b - - 0 1");
        // checkFromDown.makeMove("f1", "e1");
        // ChessGame checkFromUpRightDiag = new ChessGame("2B5/7B/8/3k4/B7/8/8/K4B2 w - - 0 1");
        // checkFromUpRightDiag.makeMove("h7", "g8");
        // ChessGame checkFromDownLeftDiag = new ChessGame("2B5/7B/8/3k4/B7/8/8/K4B2 w - - 0 1");
        // checkFromDownLeftDiag.makeMove("a4", "b3");
        // ChessGame checkFromUpLeftDiag = new ChessGame("2B5/7B/8/3k4/B7/8/8/K4B2 w - - 0 1");
        // checkFromUpLeftDiag.makeMove("c8", "b7");
        // ChessGame checkFromDownRightDiag = new ChessGame("2B5/7B/8/3k4/B7/8/8/K4B2 w - - 0 1");
        // checkFromDownRightDiag.makeMove("f1", "g2");
        // ChessGame knight1 = new ChessGame("8/2n1n3/8/n5n1/3K4/n5n1/8/2n1n2k b - - 0 1");
        // knight1.makeMove("c7", "e6");
        // ChessGame knight2 = new ChessGame("8/2n1n3/8/n5n1/3K4/n5n1/8/2n1n2k b - - 0 1");
        // knight2.makeMove("g3", "f5");
        // ChessGame knight3 = new ChessGame("8/2n1n3/8/n5n1/3K4/n5n1/8/2n1n2k b - - 0 1");
        // knight3.makeMove("g5", "f3");
        // ChessGame knight4 = new ChessGame("8/2n1n3/8/n5n1/3K4/n5n1/8/2n1n2k b - - 0 1");
        // knight4.makeMove("c1", "e2");
        // ChessGame knight5 = new ChessGame("8/2n1n3/8/n5n1/3K4/n5n1/8/2n1n2k b - - 0 1");
        // knight5.makeMove("e1", "c2");
        // ChessGame knight6 = new ChessGame("8/2n1n3/8/n5n1/3K4/n5n1/8/2n1n2k b - - 0 1");
        // knight6.makeMove("a5", "b3");
        // ChessGame knight7 = new ChessGame("8/2n1n3/8/n5n1/3K4/n5n1/8/2n1n2k b - - 0 1");
        // knight7.makeMove("a3", "b5");
        // ChessGame knight8 = new ChessGame("8/2n1n3/8/n5n1/3K4/n5n1/8/2n1n2k b - - 0 1");
        // knight8.makeMove("e7", "c6");
        // System.out.println("Unpin checks:");
        // ChessGame singleCheckUnpin = new ChessGame("rnbqr1n1/ppppbppp/7k/2p5/2P5/2P5/PPPP2PP/RNBQKBNR b KQ - 0 1");
        // singleCheckUnpin.makeMove("e7", "f6");
        // ChessGame doubleCheckUnpin = new ChessGame("rnbqr1n1/ppppbppp/7k/2p5/2P5/2P5/PPPP2PP/RNBQKBNR b KQ - 0 1");
        // doubleCheckUnpin.makeMove("e7", "h4");
        // ChessGame doubleCheckKnightUnpin = new ChessGame("8/3r4/8/3n4/8/3K4/8/7k b - - 0 1");
        // doubleCheckKnightUnpin.makeMove("d5", "f4");
        // ChessGame noKingMovesNoCapture = new ChessGame("rnbqkbnr/ppp1pppp/4p3/8/2Q5/8/PPPPPPPP/RNB1KBNR w KQkq - 0 1");
        // noKingMovesNoCapture.makeMove("c4", "b5");
        // ChessGame noKingMovesCanCapture = new ChessGame("rnbqkbnr/ppp1pppp/4p3/8/2Q5/8/PPPPPPPP/RNB1KBNR w KQkq - 0 1");
        // noKingMovesCanCapture.makeMove("c4", "c6");
        // ChessGame takeOrMoveAfterCheck = new ChessGame("rb1qkb1r/pppppppp/8/8/7n/8/PPP3PP/RNBQKBNR b KQkq - 0 1");
        // takeOrMoveAfterCheck.makeMove("h4", "f3");
        // ChessGame moveKingDownLeft = new ChessGame("8/8/8/R7/3k3K/1P6/4R3/5R2 w - - 0 1");
        // moveKingDownLeft.makeMove("f1", "d1");
        // ChessGame unpinNoCheckBefore = new ChessGame("8/5k2/5r2/R7/7K/1P6/4R3/5R2 b - - 0 1");
        // System.out.println(unpinNoCheckBefore.makeMove("f6", "e6"));
        
        // Board test3 = new Board("r4rk1/1p3p2/pp3qpp/4p3/3n4/3Q1P1P/PP3P2/R2B1R1K");
        // for(int i = 0; i < test3.getBoard().length; i++) {
        //     for(int j = 0; j < test3.getBoard().length; j++) {
        //         System.out.println(test3.getSquare(j, i));
        //     }
        // }
        // Board b = new Board();
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