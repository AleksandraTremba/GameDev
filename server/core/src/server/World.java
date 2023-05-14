package server;

import java.util.HashMap;
import java.util.Map;

public class World {

    private final Map<Integer, Player> clients = new HashMap<>();
//    private final Map<Integer, Raccoon> raccoons = new HashMap<>();


    /**
     * Return the character (Player object) that was queried by their id.
     *
     * @param id player's id
     * @return player instance
     */
    public Player getGameCharacter(int id){
        return clients.get(id);
    }
//    public Raccoon getRaccoon(int id) {
//        return raccoons.get(id);
//    }


    public Map<Integer, Player> getAllCharacters() { return clients; }

    /**
     * Add a new character (Player object).
     *
     * @param id the id of the new player
     * @param gameCharacter the Player class instance
     */
    public void addGameCharacter(Integer id, Player gameCharacter){
        clients.put(id, gameCharacter);
    }

    /**
     * Move the PlayerGameCharacter and update PlayerGameCharacter's direction.
     *
     * @param id of the PlayerGameCharacter
     * @param xPosChange how much the x coordinate has changed
     * @param yPosChange how much the y coordinate has changed
     */
    public void movePlayerGameCharacter(int id, float xPosChange, float yPosChange) {
        Player character = getGameCharacter(id);
        if (character != null) {
            character.move(xPosChange, yPosChange);
        }
    }

//    public void moveRaccoon(int id, float xPosChange, float yPosChange) {
//        Raccoon raccoon = getRaccoon(id);
//        if (raccoon != null) {
//            raccoon.move(xPosChange, yPosChange);
//        }
//    }

    public Map<Integer, Player> getClients() {
        return clients;
    }

    public void addPlayer(int id, Player newPlayer) {
        clients.put(id, newPlayer);
    }

    public void removePlayer(int id) {
        clients.remove(id);
    }

}
