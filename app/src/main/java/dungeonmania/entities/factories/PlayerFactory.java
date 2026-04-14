package dungeonmania.entities.factories;

import org.json.JSONObject;

import dungeonmania.entities.Player;
import dungeonmania.util.Position;

public class PlayerFactory implements BuildFactory {

    private JSONObject config;

    public PlayerFactory(JSONObject config) {
        this.config = config;
    }

    @Override
    public Player build(Position pos) {
        double playerHealth = config.optDouble("player_health", Player.DEFAULT_HEALTH);
        double playerAttack = config.optDouble("player_attack", Player.DEFAULT_ATTACK);
        return new Player(pos, playerHealth, playerAttack);
    }

}
