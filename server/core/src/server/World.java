package server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class World {

    private final Map<Integer, Player> clients = new HashMap<>();
    private Map<Player, List<Coin>> clientsCoins = new HashMap<>();


    /**
     * Return the character (Player object) that was queried by their id.
     *
     * @param id player's id
     * @return player instance
     */
    public Player getGameCharacter(int id){
        return clients.get(id);
    }


    public Map<Integer, Player> getAllCharacters() { return clients; }

    public Map<Player, List<Coin>> getClientsCoins() {
        return clientsCoins;
    }

    public void addCoins(int id) {
        Player player = getGameCharacter(id);
        List<Coin> coins = new ArrayList<>();
        List<Integer> coinsXCoordinates = new ArrayList<>();
        coinsXCoordinates.add(1500);
        coinsXCoordinates.add(5490);
        coinsXCoordinates.add(7000);
        coinsXCoordinates.add(3000);
        coinsXCoordinates.add(3000);
        coinsXCoordinates.add(5000);
        coinsXCoordinates.add(6000);
        coinsXCoordinates.add(2000);
        coinsXCoordinates.add(3700);
        coinsXCoordinates.add(4600);
        coinsXCoordinates.add(6543);
        coinsXCoordinates.add(3456);
        coinsXCoordinates.add(7500);
        coinsXCoordinates.add(1680);
        coinsXCoordinates.add(2678);
        List<Integer> coinsYCoordinates = new ArrayList<>();
        coinsYCoordinates.add(5000);
        coinsYCoordinates.add(3000);
        coinsYCoordinates.add(4000);
        coinsYCoordinates.add(7456);
        coinsYCoordinates.add(1000);
        coinsYCoordinates.add(500);
        coinsYCoordinates.add(4800);
        coinsYCoordinates.add(3470);
        coinsYCoordinates.add(1000);
        coinsYCoordinates.add(4600);
        coinsYCoordinates.add(3443);
        coinsYCoordinates.add(6456);
        coinsYCoordinates.add(4300);
        coinsYCoordinates.add(1480);
        coinsYCoordinates.add(2678);
        for (int i = 0; i < coinsXCoordinates.size(); i++) {
            Coin coin = new Coin(coinsXCoordinates.get(i), coinsYCoordinates.get(i));
            coins.add(coin);
        }
        player.addCoins(coins);
        clientsCoins.put(player, coins);
    }


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
