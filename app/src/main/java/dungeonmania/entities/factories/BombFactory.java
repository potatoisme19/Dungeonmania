package dungeonmania.entities.factories;

import org.json.JSONObject;

import dungeonmania.entities.collectables.Bomb;
import dungeonmania.util.Position;

public class BombFactory implements BuildFactory {
    private JSONObject config;
    public static final int DEFAULT_RADIUS = 1;

    public BombFactory(JSONObject config) {
        this.config = config;
    }

    @Override
    public Bomb build(Position pos) {
        int bombRadius = config.optInt("bomb_radius", Bomb.DEFAULT_RADIUS);
        return new Bomb(pos, bombRadius);
    }

    public Bomb buildLogical(Position pos, String logicType) {
        int bombRadius = config.optInt("bomb_radius", Bomb.DEFAULT_RADIUS);
        return new Bomb(pos, logicType, bombRadius);
    }
}
