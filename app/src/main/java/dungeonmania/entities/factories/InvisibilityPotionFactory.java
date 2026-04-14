package dungeonmania.entities.factories;

import org.json.JSONObject;

import dungeonmania.entities.collectables.potions.InvisibilityPotion;
import dungeonmania.util.Position;

public class InvisibilityPotionFactory implements BuildFactory {
    private JSONObject config;
    public static final int DEFAULT_DURATION = 8;

    public InvisibilityPotionFactory(JSONObject config) {
        this.config = config;
    }

    @Override
    public InvisibilityPotion build(Position pos) {
        int duration = config.optInt("invisibility_potion_duration", InvisibilityPotion.DEFAULT_DURATION);
        return new InvisibilityPotion(pos, duration);
    }
}
