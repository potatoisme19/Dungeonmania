package dungeonmania.entities.factories;

import org.json.JSONObject;

import dungeonmania.entities.buildables.Bow;
import dungeonmania.util.Position;

public class BowFactory implements BuildFactory {

    private JSONObject config;

    public BowFactory(JSONObject config) {
        this.config = config;
    }

    @Override
    public Bow build(Position pos) {
        int bowDurability = config.optInt("bow_durability");
        return new Bow(bowDurability);
    }
}
