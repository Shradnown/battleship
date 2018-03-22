package battleship;


import java.util.ArrayList;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author User
 */
public class Battleship extends Application {
    private boolean gameEnd = false;
    @Override
    public void start(Stage primaryStage) {
        
        GridPane placement = new GridPane();
        placement.setAlignment(Pos.BASELINE_CENTER);
        placement.setHgap(10);
        placement.setVgap(10);
        placement.setPadding(new Insets(25, 25, 25, 25));
        placement.getStylesheets().add(Battleship.class.getResource("Battleship.css").toExternalForm());
        for (int r = 0; r<10;r++){
            char column = 'A';
            for (int c = 0; c<10;c++) {
                String buttonID = String.valueOf(column) + String.valueOf(r+1);
                Button button = new Button(buttonID);
                button.setId(buttonID);
                
                button.setOnMouseEntered((MouseEvent e) -> {
                    button.setText("Hovering");
                });
                button.setOnMouseExited((MouseEvent e) -> {
                    button.setText(buttonID);
                });
                placement.add(button,c,r);
                column++;
            }
            
        }
        
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.BASELINE_CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        
        ArrayList<String> marked = new ArrayList<>();
        AI ai = new AI();
        
        ShipList shipList = new ShipList();
        ai.autoPlaceAll(shipList);
        
        ShipList aiBoard = new ShipList();
        ArrayList<String> aiMarked = new ArrayList<>();
        ai.autoPlaceAll(aiBoard);
        
        GridPane aiGrid = new GridPane();
        aiGrid.setAlignment(Pos.BASELINE_CENTER);
        aiGrid.setHgap(10);
        aiGrid.setVgap(10);
        aiGrid.setPadding(new Insets(25, 25, 25, 25));
        aiGrid.getStylesheets().add(Battleship.class.getResource("AI.css").toExternalForm());
        
        for (int r = 0; r < 10; r++) {
            char column = 'A';
            for (int c = 0; c < 10; c++) {
                String squareID = String.valueOf(column) + String.valueOf(r + 1);
                aiMarked.add(squareID);
                Button aiButton = new Button();
                aiButton.setId(squareID);
                if(aiBoard.hasShipAt(squareID)) {
                    aiButton.getStyleClass().add("used");
                }
                
                aiButton.setOnMouseEntered((MouseEvent e) -> aiButton.setText(squareID));
                aiButton.setOnMouseExited((MouseEvent e) -> aiButton.setText(""));
                aiGrid.add(aiButton,c,r);
                column++;
            }
        }
        
        for (int r = 0; r<10;r++){
            char column = 'A';
            for (int c = 0; c<10;c++) {
                String buttonID = String.valueOf(column) + String.valueOf(r+1);
                Button button = new Button(buttonID);
                button.setId(buttonID);
                button.getStyleClass().add("unused");
                button.setOnAction((ActionEvent e) -> {
                    if (!gameEnd) {
                        String buttonCoordinate = ((Button) e.getSource()).getId();
                        if (!marked.contains(buttonCoordinate)) {

                            marked.add(buttonCoordinate);
                            boolean sunk;
                            if (shipList.hasShipAt(buttonCoordinate)) {

                                sunk = shipList.hit(buttonCoordinate);
                                button.getStyleClass().removeAll("unused");
                                button.getStyleClass().add("hit");
                                button.setText(" X ");
                                if (sunk) {
                                    Ship sunkShip = shipList.getShipAt(buttonCoordinate);
                                    for (String string : sunkShip.getPostition()) {
                                        grid.lookup("#" + string).getStyleClass().removeAll("hit");
                                        grid.lookup("#" + string).getStyleClass().add("broken");
                                        ((Button)grid.lookup("#" + string)).setText("[ ]");
                                    }
                                    System.out.println("You sunk my " + sunkShip + "!");
                                }
                            }
                            else {
                                button.getStyleClass().removeAll("unused");
                                button.getStyleClass().add("miss");
                                button.setText(" x ");
                            }

                            if (shipList.areAllSunk()) {
                                System.out.println("You won!");
                                endGame();
                            }

                            String aiTarget = ai.hitShip(aiBoard, aiMarked);
                            aiMarked.remove(aiTarget);
                            if (aiBoard.hasShipAt(aiTarget)) {
                                aiGrid.lookup("#"+aiTarget).getStyleClass().removeAll("used");
                                aiGrid.lookup("#"+aiTarget).getStyleClass().add("aihit");
                                Ship aiShip = aiBoard.getShipAt(aiTarget);
                                if (aiShip.isSunk()) {
                                    for (String string : aiShip.getPostition()) {
                                        aiGrid.lookup("#"+string).getStyleClass().removeAll("aihit");
                                        aiGrid.lookup("#"+string).getStyleClass().add("aisunk");
                                    }
                                    System.out.println("Your " + aiBoard.getShipAt(aiTarget) + " has been sunk!");
                                }
                            }
                            else {
                                aiGrid.lookup("#"+aiTarget).getStyleClass().add("aimiss");
                            }
                            if (aiBoard.areAllSunk()) {
                                System.out.println("You lost!");
                                endGame();
                            }
                        }
                    }
                }); //Buttonevent
                
                grid.add(button,c,r);
                column++;
            }
        }
        
        Scene testScene = new Scene(placement);
        
        Button testButton = new Button("Test switch scene");
        testButton.setOnAction((ActionEvent e) -> {
            primaryStage.setScene(testScene);
        });
        grid.add(testButton, 0, 11);
        
        GridPane textGrid = new GridPane();
        
        textGrid.setAlignment(Pos.TOP_LEFT);
        textGrid.setHgap(10);
        textGrid.setVgap(10);
        textGrid.setPadding(new Insets(25, 25, 25, 25));
        
        Text shipsLeft = new Text("Ships left: (Logic not yet implemented)");
        textGrid.add(shipsLeft, 0, 0);
        Text carrier = new Text("Carrier (5)");
        textGrid.add(carrier, 0, 1);
        Text battleship = new Text("Battleship (4)");
        textGrid.add(battleship, 0, 2);
        Text cruiser = new Text("Cruiser (3)");
        textGrid.add(cruiser, 0, 3);
        Text submarine = new Text("Submarine (3)");
        textGrid.add(submarine, 0, 4);
        Text destroyer = new Text("Destroyer (2)");
        textGrid.add(destroyer, 0, 5);
        Text shipsDestroyed = new Text("Ships destroyed: ");
        textGrid.add(shipsDestroyed, 0, 6);
        
        
        HBox hbox = new HBox(grid, textGrid, aiGrid);
        Scene scene = new Scene(hbox);
        
        primaryStage.setTitle("Minigames - BattleShips");
        primaryStage.setScene(scene);
        scene.getStylesheets().add(Battleship.class.getResource("Battleship.css").toExternalForm());
        primaryStage.show();
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    private void endGame() {
        gameEnd = true;
    }
    
}
