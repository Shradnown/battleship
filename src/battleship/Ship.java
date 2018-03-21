package battleship;

import java.util.ArrayList;

/**
 *
 * @author User
 */
public class Ship {
    
    private ArrayList<String> hits;
    private final ArrayList<String> position;
    private final String name;
    private boolean sunk;
    private boolean placed;

    public Ship(String name) {
        position = new ArrayList<>();
        hits = new ArrayList<>();
        sunk = false;
        placed = false;
        this.name = name;
    }
    
    public boolean placeShip(ArrayList<String> location) {
        position.addAll(location);
        System.out.println(position);
        placed = true;
        //location.forEach((string) -> parts.put(string, false));
        return placed;
    }
    
    public boolean hit(String xy) {
        hits.add(xy);
        if (hits.containsAll(position)) {
            sunk = true;
        }
        return sunk;
        /*
        parts.put(xy, true);
        if (!parts.containsValue(false)) {
            sunk = true;
        }
        return sunk;
        */
    }

    public boolean isSunk() {
        return sunk;
    }

    public boolean isPlaced() {
        return placed;
    }
    
    public ArrayList<String> getPostition() {
        return position;
    }
    
    @Override
    public String toString() {
        return name;
    }
    
}
