package main;

import chess.ChessGame;
import chess.Move;
import chess.Square;
import piece.Piece;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class GUI extends Application {

    Stage stage;
    Scene scene;
    GridPane grid;
    // find a better solution than this
    Button currentButton = null;
    Square currentSquare = null;
    ChessGame game;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        game = new ChessGame();
        // game = new ChessGame("r3k2r/p4ppp/1pN5/n2P4/8/7P/P4qPB/R2R3K w - - 0 1");
        // game = new ChessGame("r3k2r/pppppppp/8/8/8/8/PPPPPPPP/R3K2R w KQkq - 0 1");
        stage = primaryStage;
        stage.setTitle("GUI test");

        grid = new GridPane();
        grid.setPadding(new Insets(10));
        
        for (int file = 0; file < 8; file++) {
            for (int rank = 0; rank < 8; rank++) {
                Button button = new Button();
                button.setMinSize(80, 80);
                button.setMaxSize(80, 80);
                
                button.setOnAction(e -> handleClickEvent(button));
                if((rank+file) % 2 != 0)
                    button.getStyleClass().add("black_square");
                else
                    button.getStyleClass().add("white_square");
                
                setPieceImage(button, file, Math.abs(rank - 7));
                
                grid.add(button, file, rank);
            }
        }
        
        // size: 8 * squareSize + 2 * padding
        scene = new Scene(grid, 660, 660);
        stage.setScene(scene);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("css/GUI.css").toExternalForm());
        stage.show();
    }

    private void handleClickEvent(Button button) {
        
        int file = GridPane.getColumnIndex(button);
        int rank = Math.abs(GridPane.getRowIndex(button)-7);
        if (currentButton == null && currentSquare == null) {
            currentSquare = game.getBoard().getSquare(file, rank);
            if (currentSquare.getPiece() == null) {
                currentSquare = null;
                return;
            }
            currentButton = button;
        } else if (game.makeMove(currentSquare, game.getBoard().getSquare(file, rank))) {
            Move move = game.getMoveList().get(game.getMoveList().size() - 1);
            // Convert chess rank back to grid row
            int gridRow = Math.abs(move.getStartSquare().getRank()-7);
            // Move Pieces on castling move. Wonky ass code. TODO: fix this
            if (move.toString().equals("O-O")) {
                // Move rook
                Button b = ((Button) getNodeByIndex(7, gridRow));
                ((Button) getNodeByIndex(5, gridRow)).setGraphic(b.getGraphic());
                b.setGraphic(null);
                // Move king
                ((Button) getNodeByIndex(6, gridRow)).setGraphic(currentButton.getGraphic());
                currentButton.setGraphic(null);
            } else if (move.toString().equals("O-O-O")) {
                // Move rook
                Button b = ((Button) getNodeByIndex(0, gridRow));
                ((Button) getNodeByIndex(3, gridRow)).setGraphic(b.getGraphic());
                b.setGraphic(null);
                // Move king
                ((Button) getNodeByIndex(2, gridRow)).setGraphic(currentButton.getGraphic());
                currentButton.setGraphic(null);
            } else {
                button.setGraphic(currentButton.getGraphic());
                currentButton.setGraphic(null);
            }
            // System.out.println(move);
            currentButton = null;
            currentSquare = null;



            if (game.isMate()) {
                System.out.println("Mate!");
                System.out.println("FEN: " + game);
                System.out.println("Movelist: " + game.getMoveList());
            }
        } else {
            currentButton = null;
            currentSquare = null;
        }
    }

    private void setPieceImage(Button button, int file, int rank) {
        Piece piece = game.getBoard().getPiece(file, rank);
        if (piece == null)
            return;
        String pieceString = "";
        if (piece.getColor() == 'W')
            pieceString += "White";
        else if (piece.getColor() == 'B')
            pieceString += "Black";
        else
            return;
        
        if (piece.getUpperCaseName() == 'P')
            pieceString += "Pawn";
        else {
            if (piece.getUpperCaseName() == 'R')
                pieceString += "Rook";
            else if (piece.getUpperCaseName() == 'N')
                pieceString += "Knight";
            else if (piece.getUpperCaseName() == 'B')
                pieceString += "Bishop";
            else if (piece.getUpperCaseName() == 'Q')
                pieceString += "Queen";
            else if (piece.getUpperCaseName() == 'K')
                pieceString += "King";
            else
                return;
        }

        ImageView imageView = new ImageView("img/merida/" + pieceString + ".png");
        imageView.setFitHeight(80);
        imageView.setPreserveRatio(true);
        button.setGraphic(imageView);
    }

    private Node getNodeByIndex(int colIndex, int rowIndex) {
        for(Node node : grid.getChildren()) {
            if (GridPane.getColumnIndex(node) == colIndex && GridPane.getRowIndex(node) == rowIndex)
                return node;
        }
        return null;
    }

}
