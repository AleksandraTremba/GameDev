package theGame.GameInfo;

import theGame.ClientConnection;
import theGame.Coin;
import theGame.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * Class in the Client's side, where the local version of the game is created and maintained.
 */
public class ClientWorld {

    private Player player;
    private final HashMap<Integer, Player> worldGameCharactersMap = new HashMap<>();
    private List<Coin> coins = new ArrayList<>();

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

    public Player getGameCharacter(Integer id) {
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
    public List<Coin> getCoins() {
        return coins;
    }

    public void addCoin(Integer x, Integer y) {
        Coin coin = new Coin(x, y);
        coins.add(coin);
    }

    /**
     public void removeOtherPlayerCoins(String color) {
     List<Coin> newCoins = new ArrayList<>();
     for (Coin coin : coins) {
     if (!Objects.equals(coin.getColor(), color)) {
     newCoins.add(coin);
     }
     }
     coins = newCoins;
     }
     **/


}
