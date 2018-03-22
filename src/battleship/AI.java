package battleship;

import java.util.ArrayList;

/**
 *
 * @author User
 */
public class AI {
    private String firstHit = null;
    private String lastHit = null;

    public boolean autoPlaceShip(ShipList shipList, int shipSize) {
        if (shipList.isShipPlaced(shipSize)) {
            return false;
        }
        while (true) {
            char startColumn = (char) ((int) (Math.random() * 10) + 65);
            int startRow = (int) (Math.random() * 10) + 1;
            String start = String.valueOf(startColumn) + String.valueOf(startRow);

            if (startRow <= 11 - shipSize) {
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

            if ((int) startColumn <= 76 - shipSize) {
                ArrayList<String> shipLocation = new ArrayList<>();
                shipLocation.add(start);

                for (int i = 1; i < shipSize; i++) {
                    shipLocation.add(String.valueOf((char) (startColumn + i)) + String.valueOf(startRow));
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
            if ((int) startColumn >= 64 + shipSize) {
                ArrayList<String> shipLocation = new ArrayList<>();
                shipLocation.add(start);

                for (int i = 1; i < shipSize; i++) {
                    shipLocation.add(String.valueOf((char) (startColumn - i)) + String.valueOf(startRow));
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
            char startColumn = (char) ((int) (Math.random() * 10) + 65);
            int startRow = (int) (Math.random() * 10) + 1;
            String start = String.valueOf(startColumn) + String.valueOf(startRow);

            if ((int) startColumn <= 75 - shipSize) {
                ArrayList<String> shipLocation = new ArrayList<>();
                shipLocation.add(start);

                for (int i = 1; i < shipSize; i++) {
                    shipLocation.add(String.valueOf((char) (startColumn + i)) + String.valueOf(startRow));
                }
                if (shipIsValid(shipList, shipLocation)) {
                    shipList.placeShip(shipLocation);
                    return true;
                }
            }
            if ((int) startColumn >= 64 + shipSize) {
                ArrayList<String> shipLocation = new ArrayList<>();
                shipLocation.add(start);

                for (int i = 1; i < shipSize; i++) {
                    shipLocation.add(String.valueOf((char) (startColumn - i)) + String.valueOf(startRow));
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
            char startColumn = (char) ((int) (Math.random() * 10) + 65);
            int startRow = (int) (Math.random() * 10) + 1;
            String start = String.valueOf(startColumn) + String.valueOf(startRow);
            if (startRow <= 11 - shipSize) {
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
        } else {
            int[] ships = {5, 4, 3, 3, 2};
            for (int i = 0; i < ships.length; i++) {
                int direction = (int) (Math.random() * 2);

                if (direction == 0) {
                    autoPlaceShipHorizontal(shipList, ships[i]);
                } else {
                    autoPlaceShipVertical(shipList, ships[i]);
                }
            }
            return true;
        }
    }

    private boolean shipIsValid(ShipList shipList, ArrayList<String> shipLocation) {
        return shipLocation.stream().noneMatch(string -> (shipList.getAll().contains(string)));
    }

    private String hitRandomShip(ShipList shipList, ArrayList<String> remaining) {
        if (remaining.isEmpty()) {
            return null;
        }
        while (true) {
            char column = (char) ((int) (Math.random() * 10) + 65);
            int row = (int) (Math.random() * 10) + 1;
            String target = String.valueOf(column) + String.valueOf(row);

            if (remaining.contains(target)) {
                if (shipList.hasShipAt(target)) {
                    shipList.hit(target);
                    return target;
                } else {
                    return target;
                }
            }
        }
    }

    public String hitShip(ShipList shipList, ArrayList<String> remaining) {
        if (firstHit != null) {
            
            String target = testNextGuess(remaining);
            Boolean sunk = shipList.hit(target);
            if (sunk) {
                firstHit = null;
                lastHit = null;
            }
            else if (shipList.hasShipAt(target)) {
                lastHit = target;
            }
            return target;
        }
        
        String target = hitRandomShip(shipList, remaining);
        if (shipList.hasShipAt(target)) {
            firstHit = target;
            lastHit = target;
        }
        return target;
    }

    private String testNextGuess(ArrayList<String> remaining) {
        int lastHitRow = Integer.parseInt(lastHit.substring(1));
        int firstHitRow = Integer.parseInt(firstHit.substring(1));
        
        if (firstHit.equals(lastHit)) {
            String newTarget = String.valueOf((char) (lastHit.charAt(0))) + (String.valueOf((lastHitRow + 1)));
            if (remaining.contains(newTarget)) {
                return newTarget;
            }
            
            newTarget = String.valueOf((char) (lastHit.charAt(0) + 1)) + String.valueOf(lastHitRow);
            if (remaining.contains(newTarget)) {
                return newTarget;
            }
            
            newTarget = String.valueOf((char) (lastHit.charAt(0))) + String.valueOf((lastHitRow - 1));
            System.out.println("newtarget at up :" + newTarget);
            if (remaining.contains(newTarget)) {
                return newTarget;
            }
            
            newTarget = String.valueOf((char) (lastHit.charAt(0) - 1)) + String.valueOf(lastHitRow);
            if (remaining.contains(newTarget)) {
                return newTarget;
            }
        }
        
        else if (lastHitRow == firstHitRow) {
            String newTarget = String.valueOf((char) (lastHit.charAt(0) + 1)) + String.valueOf(lastHitRow);
            if (remaining.contains(newTarget)) {
                return newTarget;
            }
            newTarget = String.valueOf((char) (lastHit.charAt(0) - 1)) + String.valueOf(lastHitRow);
            if (remaining.contains(newTarget)) {
                return newTarget;
            }
            newTarget = String.valueOf((char) (firstHit.charAt(0) - 1)) + String.valueOf(lastHitRow);
            return newTarget;
        }
        
        else if (lastHit.charAt(0) == firstHit.charAt(0)) {
            String newTarget = String.valueOf((char) (lastHit.charAt(0))) + String.valueOf(lastHitRow + 1);
            if (remaining.contains(newTarget)) {
                return newTarget;
            }
            newTarget = String.valueOf((char) (lastHit.charAt(0))) + String.valueOf(lastHitRow - 1);
            if (remaining.contains(newTarget)) {
                return newTarget;
            }
            newTarget = String.valueOf((char) (firstHit.charAt(0))) + String.valueOf(firstHitRow -1);
            return newTarget;
        }
        System.out.println("Something is very wrong");
        return null;
    }
    
}
