// dungeonmania/entities/factories/EntityFactory.java
package dungeonmania.entities.factories;

import org.json.JSONObject;
import dungeonmania.entities.Entity;
import dungeonmania.util.Position;

public class EntityFactory {
    private JSONObject config;

    private SpiderFactory spiderFactory;
    private ZombieFactory zombieFactory;
    private MercenaryFactory mercenaryFactory;
    private PlayerFactory playerFactory;
    private BowFactory bowFactory;
    private ShieldFactory shieldFactory;
    private SwordFactory swordFactory;
    private BombFactory bombFactory;
    private InvincibilityPotionFactory invincibilityPotionFactory;
    private InvisibilityPotionFactory invisibilityPotionFactory;
    private StaticEntityFactory staticEntityFactory;

    public EntityFactory(JSONObject config) {
        this.config = config;
        initializeFactories();
    }

    private void initializeFactories() {
        spiderFactory = new SpiderFactory(config);
        zombieFactory = new ZombieFactory(config);
        mercenaryFactory = new MercenaryFactory(config);
        playerFactory = new PlayerFactory(config);
        bowFactory = new BowFactory(config);
        shieldFactory = new ShieldFactory(config);
        swordFactory = new SwordFactory(config);
        bombFactory = new BombFactory(config);
        invincibilityPotionFactory = new InvincibilityPotionFactory(config);
        invisibilityPotionFactory = new InvisibilityPotionFactory(config);
        staticEntityFactory = new StaticEntityFactory(config);
    }

    public SpiderFactory getSpiderFactory() {
        return spiderFactory;
    }

    public ZombieFactory getZombieFactory() {
        return zombieFactory;
    }

    public MercenaryFactory getMercenaryFactory() {
        return mercenaryFactory;
    }

    public PlayerFactory getPlayerFactory() {
        return playerFactory;
    }

    public BowFactory getBowFactory() {
        return bowFactory;
    }

    public ShieldFactory getShieldFactory() {
        return shieldFactory;
    }

    public SwordFactory getSwordFactory() {
        return swordFactory;
    }

    public BombFactory getBombFactory() {
        return bombFactory;
    }

    public InvincibilityPotionFactory getInvincibilityPotionFactory() {
        return invincibilityPotionFactory;
    }

    public InvisibilityPotionFactory getInvisibilityPotionFactory() {
        return invisibilityPotionFactory;
    }

    public StaticEntityFactory getStaticEntityFactory() {
        return staticEntityFactory;
    }

    public Entity createEntity(JSONObject jsonEntity) {
        Position pos = new Position(jsonEntity.getInt("x"), jsonEntity.getInt("y"));

        switch (jsonEntity.getString("type")) {
            case "spider":
                return spiderFactory.build(pos);
            case "zombie_toast":
                return zombieFactory.build(pos);
            case "mercenary":
                return mercenaryFactory.build(pos);
            case "player":
                return playerFactory.build(pos);
            case "wall":
                return staticEntityFactory.createWall(pos);
            case "boulder":
                return staticEntityFactory.createBoulder(pos);
            case "switch":
                return staticEntityFactory.createSwitch(pos);
            case "exit":
                return staticEntityFactory.createExit(pos);
            case "treasure":
                return staticEntityFactory.createTreasure(pos);
            case "wood":
                return staticEntityFactory.createWood(pos);
            case "arrow":
                return staticEntityFactory.createArrow(pos);
            case "key":
                return staticEntityFactory.createKey(pos, jsonEntity.getInt("key"));
            case "door":
                return staticEntityFactory.createDoor(pos, jsonEntity.getInt("key"));
            case "portal":
                return staticEntityFactory.createPortal(pos, jsonEntity.getString("colour"));
            case "bomb":
                return bombFactory.build(pos);
            case "invisibility_potion":
                return invisibilityPotionFactory.build(pos);
            case "invincibility_potion":
                return invincibilityPotionFactory.build(pos);
            case "sword":
                return swordFactory.build(pos);
            case "zombie_toast_spawner":
                return zombieFactory.buildZombieToastSpawner(pos);
            case "light_bulb_off":
                String lightBulbLogic = jsonEntity.optString("logic", "and");
                return staticEntityFactory.createLightBlub(pos, lightBulbLogic);

            case "wire":
                return staticEntityFactory.createWire(pos);

            case "switch_door":
                String switchDoorLogic = jsonEntity.optString("logic", "and");
                return staticEntityFactory.createSwitchDoor(pos, switchDoorLogic);
            default:
                throw new IllegalArgumentException("Unknown entity type: " + jsonEntity.getString("type"));
        }
    }
}
