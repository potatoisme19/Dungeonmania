// dungeonmania/entities/factories/StaticEntityFactory.java (for walls, doors, etc.)
package dungeonmania.entities.factories;

import org.json.JSONObject;

import dungeonmania.entities.Boulder;
import dungeonmania.entities.ColorCodedType;
import dungeonmania.entities.Door;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Exit;
import dungeonmania.entities.Portal;
import dungeonmania.entities.Switch;
import dungeonmania.entities.Wall;
import dungeonmania.entities.collectables.Arrow;
import dungeonmania.entities.collectables.Key;
import dungeonmania.entities.collectables.Treasure;
import dungeonmania.entities.collectables.Wood;
import dungeonmania.entities.logical.LightBulb;
import dungeonmania.entities.logical.SwitchDoor;
import dungeonmania.entities.logical.Wire;
import dungeonmania.util.Position;

public class StaticEntityFactory implements BuildFactory {
    private JSONObject config;

    public StaticEntityFactory(JSONObject config) {
        this.config = config;
    }

    @Override
    public Entity build(Position pos) {
        // This factory is used for specific types via the main factory
        throw new UnsupportedOperationException("Use specific build methods");
    }

    public Wall createWall(Position pos) {
        return new Wall(pos);
    }

    public Boulder createBoulder(Position pos) {
        return new Boulder(pos);
    }

    public Switch createSwitch(Position pos) {
        return new Switch(pos);
    }

    public Exit createExit(Position pos) {
        return new Exit(pos);
    }

    public Treasure createTreasure(Position pos) {
        return new Treasure(pos);
    }

    public Wood createWood(Position pos) {
        return new Wood(pos);
    }

    public Arrow createArrow(Position pos) {
        return new Arrow(pos);
    }

    public Key createKey(Position pos, int keyNumber) {
        return new Key(pos, keyNumber);
    }

    public Door createDoor(Position pos, int keyNumber) {
        return new Door(pos, keyNumber);
    }

    public Portal createPortal(Position pos, String color) {
        return new Portal(pos, ColorCodedType.valueOf(color));
    }

    public LightBulb createLightBlub(Position pos, String logicType) {
        return new LightBulb(pos);
    }

    public Wire createWire(Position pos) {
        return new Wire(pos);
    }

    public SwitchDoor createSwitchDoor(Position pos, String logicType) {
        return new SwitchDoor(pos);
    }
}
