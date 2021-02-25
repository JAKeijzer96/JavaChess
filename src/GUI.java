import ChessPackage.ChessGame;
import ChessPackage.Square;
import ChessPackage.Pieces.Piece;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class GUI extends Application {

    // javac --module-path "C:\\Program Files\\JavaFX\\javafx-sdk-11.0.2\\lib" --add-modules javafx.controls GUI.java | java --module-path "C:\\Program Files\\JavaFX\\javafx-sdk-11.0.2\\lib" --add-modules javafx.controls GUI

    Stage stage;
    Scene scene;
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

        GridPane grid = new GridPane();
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
        scene.getStylesheets().add(GUI.class.getResource("GUI.css").toExternalForm());
        stage.show();
    }

    private void handleClickEvent(Button button) {
        int file = GridPane.getColumnIndex(button);
        int rank = Math.abs(GridPane.getRowIndex(button)-7);
        if (currentButton == null && currentSquare == null) {
            currentButton = button;
            currentSquare = game.getBoard().getSquare(file, rank);
        } else if (game.makeMove(currentSquare, game.getBoard().getSquare(file, rank))) {
            button.setGraphic(currentButton.getGraphic());
            currentButton.setGraphic(null);
            currentButton = null;
            currentSquare = null;
            System.out.println(game.getMoveList());
            if (game.isMate())
                System.out.println("Mate!");
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
        
        ImageView imageView = new ImageView();
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

        imageView.setImage(new Image("file:D:/Coding projects/Chess/img/merida/" + pieceString + ".png"));
        imageView.setFitHeight(80);
        imageView.setPreserveRatio(true);
        button.setGraphic(imageView);
    }

}
