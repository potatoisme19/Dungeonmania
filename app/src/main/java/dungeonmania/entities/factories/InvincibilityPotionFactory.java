package dungeonmania.entities.factories;

import org.json.JSONObject;

import dungeonmania.entities.collectables.potions.InvincibilityPotion;
import dungeonmania.util.Position;

public class InvincibilityPotionFactory implements BuildFactory {
    private JSONObject config;
    public static final int DEFAULT_DURATION = 8;

    public InvincibilityPotionFactory(JSONObject config) {
        this.config = config;
    }

    @Override
    public InvincibilityPotion build(Position pos) {
        int duration = config.optInt("invincibility_potion_duration", InvincibilityPotion.DEFAULT_DURATION);
        return new InvincibilityPotion(pos, duration);
    }
}
