package battleship;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author User
 */
public class ShipList {
    HashMap<String, Ship> ships; 

    public ShipList() {
        ships = new HashMap<>();
        ships.put("carrier", new Ship("Carrier"));
        ships.put("battleship", new Ship("Battleship"));
        ships.put("cruiser", new Ship("Cruiser"));
        ships.put("submarine", new Ship("Submarine"));
        ships.put("destroyer", new Ship("Destroyer"));
    }
    
    public boolean placeShip(ArrayList<String> location) {
        switch (location.size()) {
            case 2:
                ships.get("destroyer").placeShip(location);
                break;
            case 3:
                if (!ships.get("cruiser").isPlaced()) {
                    ships.get("cruiser").placeShip(location);
                }
                else {
                    ships.get("submarine").placeShip(location);
                }
                break;
            case 4:
                ships.get("battleship").placeShip(location);
                break;
            case 5:
                ships.get("carrier").placeShip(location);
                break;
            default:
                return false;
                
        }
        return true;
    }
    
    public boolean isShipPlaced(String Name) {
        return ships.get(Name).isPlaced();
    }
    
    public boolean isShipPlaced(int size) {
        switch (size) {
            case 2:
                return ships.get("destroyer").isPlaced();
            case 3:
                if (ships.get("cruiser").isPlaced()) {
                    return ships.get("submarine").isPlaced();
                }
                else {
                    return ships.get("cruiser").isPlaced();
                }
            case 4:
                return ships.get("battleship").isPlaced();
            case 5:
                return ships.get("carrier").isPlaced();
            default:
                return false;
        }
    }
    
    public boolean hasShipAt(String coordinate) {
        for (Ship ship : ships.values()) {
            if (ship.getPostition().contains(coordinate)) {
                return true;
            }
        }
        return false;
    }
    
    public Ship getShipAt(String coordinate) {
        for (Ship ship : ships.values()) {
            if (ship.getPostition().contains(coordinate)) {
                return ship;
            }
        }
        return null;
    }
    
    public boolean hit(String coordinate) {
        boolean sunk = false;
        for (Ship ship : ships.values()) {
            if (ship.getPostition().contains(coordinate)) {
                sunk = ship.hit(coordinate);
                return sunk;
            }
        }
        return sunk;
    }
    
    public ArrayList<String> getAll() {
        ArrayList<String> all = new ArrayList<>();
        ships.values().forEach(ship -> all.addAll(ship.getPostition()));
        return all;
    }
    
}
