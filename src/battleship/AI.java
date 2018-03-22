package battleship;

import java.util.ArrayList;

/**
 *
 * @author User
 */
public class AI {
    
    public boolean autoPlaceShip(ShipList shipList, int shipSize) {
        if (shipList.isShipPlaced(shipSize)) {
            return false;
        }
        while (true) {
            char startColumn = (char)((int)(Math.random()*10)+65);
            int startRow = (int)(Math.random()*10)+1;
            String start = String.valueOf(startColumn) + String.valueOf(startRow);
            
            if (startRow <= 11-shipSize) {
                ArrayList<String> shipLocation = new ArrayList<>();
                shipLocation.add(start);
                
                for (int i = 1; i < shipSize; i++) {
                    shipLocation.add(String.valueOf(startColumn) + String.valueOf(startRow + i));
                }
                if (shipIsValid(shipList, shipLocation)) {
                    shipList.placeShip(shipLocation);
                    return true;
                }
            }
            
            if ((int)startColumn <= 76-shipSize) {
                ArrayList<String> shipLocation = new ArrayList<>();
                shipLocation.add(start);
                
                for (int i = 1; i < shipSize; i++) {
                    shipLocation.add(String.valueOf((char)(startColumn + i)) + String.valueOf(startRow));
                }
                if (shipIsValid(shipList, shipLocation)) {
                    shipList.placeShip(shipLocation);
                    return true;
                }
            }
            
            if (startRow >= shipSize) {
                ArrayList<String> shipLocation = new ArrayList<>();
                shipLocation.add(start);
                
                for (int i = 1; i < shipSize; i++) {
                    shipLocation.add(String.valueOf(startColumn) + String.valueOf(startRow - i));
                }
                if (shipIsValid(shipList, shipLocation)) {
                    shipList.placeShip(shipLocation);
                    return true;
                }
            }
            if ((int)startColumn >= 64+shipSize) {
                ArrayList<String> shipLocation = new ArrayList<>();
                shipLocation.add(start);
                
                for (int i = 1; i < shipSize; i++) {
                    shipLocation.add(String.valueOf((char)(startColumn - i)) + String.valueOf(startRow));
                }
                if (shipIsValid(shipList, shipLocation)) {
                    shipList.placeShip(shipLocation);
                    return true;
                }
            }
        }
    }
    
    private boolean autoPlaceShipHorizontal(ShipList shipList, int shipSize) {
        if (shipList.isShipPlaced(shipSize)) {
            return false;
        }
        while (true) {
            char startColumn = (char)((int)(Math.random()*10)+65);
            int startRow = (int)(Math.random()*10)+1;
            String start = String.valueOf(startColumn) + String.valueOf(startRow);
            
            if ((int)startColumn <= 75-shipSize) {
                ArrayList<String> shipLocation = new ArrayList<>();
                shipLocation.add(start);
                
                for (int i = 1; i < shipSize; i++) {
                    shipLocation.add(String.valueOf((char)(startColumn + i)) + String.valueOf(startRow));
                }
                if (shipIsValid(shipList, shipLocation)) {
                    shipList.placeShip(shipLocation);
                    return true;
                }
            }
            if ((int)startColumn >= 64+shipSize) {
                ArrayList<String> shipLocation = new ArrayList<>();
                shipLocation.add(start);
                
                for (int i = 1; i < shipSize; i++) {
                    shipLocation.add(String.valueOf((char)(startColumn - i)) + String.valueOf(startRow));
                }
                if (shipIsValid(shipList, shipLocation)) {
                    shipList.placeShip(shipLocation);
                    return true;
                }
            }
        }
    }
    
    private boolean autoPlaceShipVertical(ShipList shipList, int shipSize) {
        if (shipList.isShipPlaced(shipSize)) {
            return false;
        }
        while (true) {
            char startColumn = (char)((int)(Math.random()*10)+65);
            int startRow = (int)(Math.random()*10)+1;
            String start = String.valueOf(startColumn) + String.valueOf(startRow);
            if (startRow <= 11-shipSize) {
                ArrayList<String> shipLocation = new ArrayList<>();
                shipLocation.add(start);
                
                for (int i = 1; i < shipSize; i++) {
                    shipLocation.add(String.valueOf(startColumn) + String.valueOf(startRow + i));
                }
                if (shipIsValid(shipList, shipLocation)) {
                    shipList.placeShip(shipLocation);
                    return true;
                }
            }
            if (startRow >= shipSize) {
                ArrayList<String> shipLocation = new ArrayList<>();
                shipLocation.add(start);
                
                for (int i = 1; i < shipSize; i++) {
                    shipLocation.add(String.valueOf(startColumn) + String.valueOf(startRow - i));
                }
                if (shipIsValid(shipList, shipLocation)) {
                    shipList.placeShip(shipLocation);
                    return true;
                }
            }
        }
    }
    
    public boolean autoPlaceAll(ShipList shipList) {
        if (shipList.isShipPlaced(5)) {
            return false;
        }
        else {
            int[] ships = {5,4,3,3,2};
            for(int i = 0; i<ships.length;i++) {
                int direction = (int)(Math.random()*2);

                if (direction == 0) {
                autoPlaceShipHorizontal(shipList, ships[i]);
                }
                else {
                    autoPlaceShipVertical(shipList, ships[i]);
                }
            }
            return true;
        }
    }
    
    private boolean shipIsValid(ShipList shipList, ArrayList<String> shipLocation) {
        return shipLocation.stream().noneMatch(string -> (shipList.getAll().contains(string)));
    }
    
    public String hitRandomShip(ShipList shipList, ArrayList<String> remaining) {
        if (remaining.isEmpty()) {
            return null;
        }
        while (true) {
            char column = (char)((int)(Math.random()*10)+65);
            int row = (int)(Math.random()*10)+1;
            String target = String.valueOf(column) + String.valueOf(row);
            
            if (remaining.contains(target)) {
                if (shipList.hasShipAt(target)) {
                    shipList.hit(target);
                    return target;
                }
                else {
                    return target;
                }
            }
        }
        
    }
    
}
