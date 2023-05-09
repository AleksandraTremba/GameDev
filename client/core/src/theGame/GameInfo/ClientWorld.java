package theGame.GameInfo;

import theGame.ClientConnection;
import theGame.Player;
import theGame.enemy.Raccoon;

import java.util.HashMap;

/**
 * Class in the Client's side, where the local version of the game is created and maintained.
 */
public class ClientWorld {

    private Player player;
    private final HashMap<Integer, Player> worldGameCharactersMap = new HashMap<>();
    private HashMap<Integer, Raccoon> raccoons = new HashMap<>();

    /**
     * Adds the instance of ClientConnection to this class.
     */
    public void registerClient(ClientConnection clientConnection){
    }

    /**
     * This moves the PlayerGameCharacter by changing  x and y coordinates of set character.
     *
     * @param id of the moving character - id is key in worldGameCharactersMap.
     * @param xPosChange the change of x
     * @param yPosChange the change of y
     */
    public void movePlayerGameCharacter(Integer id, float xPosChange, float yPosChange) {
        getGameCharacter(id).moveToNewPos(xPosChange, yPosChange);
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getGameCharacter(Integer id){
        return worldGameCharactersMap.get(id);
    }

    public HashMap<Integer, Player> getWorldGameCharactersMap() {
        return worldGameCharactersMap;
    }

    /**
     * Add a PlayerGameCharacter to the characters map.
     *
     * @param id of the Player
     * @param newCharacter Player
     */
    public void addGameCharacter(Integer id, Player newCharacter) {
        worldGameCharactersMap.put(id, newCharacter);
    }

    public void removeGameCharacter(Integer id) {
        worldGameCharactersMap.remove(id);
    }

    /**
     * This moves the Player by changing  x and y coordinates of set character.
     *
     * @param id of the moving character - id is key in worldGameCharactersMap.
     * @param xPosChange the change of x
     * @param yPosChange the change of y
     */
    public void movePlayerGameCharacter(int id, float xPosChange, float yPosChange) {
        getGameCharacter(id).moveToNewPos(xPosChange, yPosChange);
    }
}
