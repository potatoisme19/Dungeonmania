package dungeonmania.entities.factories;

import org.json.JSONObject;

import dungeonmania.entities.collectables.Sword;
import dungeonmania.util.Position;

public class SwordFactory implements BuildFactory {
    private JSONObject config;
    public static final double DEFAULT_ATTACK = 1;
    public static final int DEFAULT_DURABILITY = 5;

    public SwordFactory(JSONObject config) {
        this.config = config;
    }

    @Override
    public Sword build(Position pos) {
        double attack = config.optDouble("sword_attack", Sword.DEFAULT_ATTACK);
        int durability = config.optInt("sword_durability", Sword.DEFAULT_DURABILITY);
        return new Sword(pos, attack, durability);
    }
}
