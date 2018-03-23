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
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Erik
 */
public class Battleship extends Application {
    private boolean gameEnd = false;
    @Override
    public void start(Stage primaryStage) {
        
        GridPane placement = new GridPane();
        
        Scene placementScene = new Scene(placement);
        placementScene.getStylesheets().add(Battleship.class.getResource("PlacementMenu.css").toExternalForm());
        
        ShipList aiBoard = new ShipList();
        ArrayList<String> placeMarks = new ArrayList<>();
        
        placement.setAlignment(Pos.BASELINE_CENTER);
        placement.setHgap(10);
        placement.setVgap(10);
        placement.setPadding(new Insets(25, 25, 25, 25));
        //placement.getStylesheets().add(Battleship.class.getResource("Battleship.css").toExternalForm());
        for (int r = 0; r<10;r++){
            char column = 'A';
            for (int c = 0; c<10;c++) {
                String buttonID = String.valueOf(column) + String.valueOf(r+1);
                Button button = new Button(buttonID);
                button.setId(buttonID);
                
                button.setOnAction((ActionEvent e) -> {
                    if (!aiBoard.hasShipAt(buttonID) && placeMarks.size()<5){
                        if (placeMarks.contains(buttonID)) {
                            placeMarks.remove(buttonID);
                            button.getStyleClass().remove("marked");
                        }
                        else {
                            placeMarks.add(buttonID);
                            button.getStyleClass().add("marked");
                        }
                    }
                });
                
                placement.add(button,c,r);
                column++;
            }
        }
        
        Text placeinfo = new Text("Place one ship at a time");
        
        
        Button submit = new Button("Confirm Location");
        submit.getStyleClass().add("sidebutton");
        submit.setOnAction((ActionEvent e) -> {
            boolean placed = aiBoard.placeShip(placeMarks);
            if (placed) {
                for (String string : placeMarks) {
                    placement.lookup("#" + string).getStyleClass().remove("marked");
                    placement.lookup("#" + string).getStyleClass().add("placed");
                }
                placeMarks.clear();
                System.out.println("Ship Placed");
                
                if (aiBoard.areAllPlaced()) {
                    startgame(primaryStage, aiBoard);
                }
            }
            else {
                System.out.println("Invalid Location");
            }
        });
        
        Button autoPlace = new Button("Autoplace ships");
        autoPlace.getStyleClass().add("sidebutton");
        autoPlace.setOnAction((ActionEvent e) -> {
            AI ai = new AI();
            ai.autoPlaceAll(aiBoard);
            startgame(primaryStage, aiBoard);
        });
        placement.add(placeinfo, 11, 0);
        placement.add(submit, 11, 1);
        placement.add(autoPlace, 11, 2);
        
        Text shipsLeft = new Text("Ships left to place:");
        shipsLeft.getStyleClass().add("titletext");
        Text carrier = new Text("Carrier (5)");
        carrier.setId("Carrier");
        carrier.getStyleClass().add("text");
        Text battleship = new Text("Battleship (4)");
        battleship.setId("Battleship");
        battleship.getStyleClass().add("text");
        Text cruiser = new Text("Cruiser (3)");
        cruiser.setId("Cruiser");
        cruiser.getStyleClass().add("text");
        Text submarine = new Text("Submarine (3)");
        submarine.setId("Submarine");
        submarine.getStyleClass().add("text");
        Text destroyer = new Text("Destroyer (2)");
        destroyer.setId("Destroyer");
        destroyer.getStyleClass().add("text");
        
        VBox vbShipsLeft = new VBox(shipsLeft, carrier, battleship, cruiser, submarine, destroyer);
        vbShipsLeft.getStylesheets().add(Battleship.class.getResource("ShipTracker.css").toExternalForm());
        placement.add(vbShipsLeft, 11, 4, 1, 7);
        
        primaryStage.setTitle("Minigames - BattleShips");
        primaryStage.setScene(placementScene);
        
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
    
    private void startgame(Stage primaryStage, ShipList aiBoard) {
        GridPane playGrid = new GridPane();
        playGrid.setAlignment(Pos.BASELINE_CENTER);
        playGrid.setHgap(10);
        playGrid.setVgap(10);
        playGrid.setPadding(new Insets(25, 25, 25, 25));
        playGrid.getStylesheets().add(Battleship.class.getResource("Battleship.css").toExternalForm());
        
        GridPane aiGrid = new GridPane();
        aiGrid.setAlignment(Pos.BASELINE_CENTER);
        aiGrid.setHgap(10);
        aiGrid.setVgap(10);
        aiGrid.setPadding(new Insets(25, 25, 25, 25));
        aiGrid.getStylesheets().add(Battleship.class.getResource("AI.css").toExternalForm());
        
        ArrayList<String> marked = new ArrayList<>();
        AI ai = new AI();
        
        ShipList shipList = new ShipList();
        ai.autoPlaceAll(shipList);
        ArrayList<String> aiMarked = new ArrayList<>();
        
        Text shipsLeft = new Text("Ships left:");
        shipsLeft.getStyleClass().add("titletext");
        Text shipsDestroyed = new Text("Ships Destroyed:");
        shipsDestroyed.getStyleClass().add("titletext");
        
        Text carrier = new Text("Carrier (5)");
        carrier.setId("Carrier");
        carrier.getStyleClass().add("text");
        Text battleship = new Text("Battleship (4)");
        battleship.setId("Battleship");
        battleship.getStyleClass().add("text");
        Text cruiser = new Text("Cruiser (3)");
        cruiser.setId("Cruiser");
        cruiser.getStyleClass().add("text");
        Text submarine = new Text("Submarine (3)");
        submarine.setId("Submarine");
        submarine.getStyleClass().add("text");
        Text destroyer = new Text("Destroyer (2)");
        destroyer.setId("Destroyer");
        destroyer.getStyleClass().add("text");
        
        VBox vbShipsLeft = new VBox(shipsLeft, carrier, battleship, cruiser, submarine, destroyer);
        playGrid.add(vbShipsLeft, 11, 0, 1, 3);
        VBox vbShipsDestroyed = new VBox(shipsDestroyed);
        playGrid.add(vbShipsDestroyed, 11, 4, 1, 3);
        
        vbShipsLeft.getStylesheets().add(Battleship.class.getResource("ShipTracker.css").toExternalForm());
        vbShipsDestroyed.getStylesheets().add(Battleship.class.getResource("ShipTracker.css").toExternalForm());
        
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
                                        playGrid.lookup("#" + string).getStyleClass().removeAll("hit");
                                        playGrid.lookup("#" + string).getStyleClass().add("broken");
                                        ((Button)playGrid.lookup("#" + string)).setText("[ ]");
                                    }
                                    System.out.println("You sunk my " + sunkShip + "!");
                                    vbShipsLeft.getChildren().remove(vbShipsLeft.lookup("#"+sunkShip.toString()));
                                    Text temp = new Text(sunkShip.toString() + " (" + sunkShip.getPostition().size() + ")");
                                    temp.getStyleClass().add("text");
                                    vbShipsDestroyed.getChildren().add(temp);
                                    
                                    
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
                                Text winText = new Text("You Won!");
                                winText.getStyleClass().add("winText");
                                aiGrid.add(winText, 0, 11, 7, 5);
                                
                            }
                            if (!gameEnd){
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
                                    Text loseText = new Text("You Lost!");
                                    loseText.getStyleClass().add("loseText");
                                    aiGrid.add(loseText, 0, 11, 7, 5);
                                }
                            }
                        }
                    }
                }); //Buttonevent
                
                playGrid.add(button,c,r);
                column++;
            }
        
        }
        HBox hbox = new HBox(playGrid, aiGrid);
        Scene scene = new Scene(hbox);
        scene.getStylesheets().add(Battleship.class.getResource("Battleship.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
}
