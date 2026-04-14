package dungeonmania.entities.factories;

import org.json.JSONObject;

import dungeonmania.entities.buildables.Shield;
import dungeonmania.util.Position;

public class ShieldFactory implements BuildFactory {

    private JSONObject config;

    public ShieldFactory(JSONObject config) {
        this.config = config;
    }

    @Override
    public Shield build(Position pos) {
        int shieldDurability = config.optInt("shield_durability");
        double shieldDefence = config.optInt("shield_defence");
        return new Shield(shieldDurability, shieldDefence);
    }

}
