// dungeonmania/entities/factories/ZombieFactory.java
package dungeonmania.entities.factories;

import java.util.List;
import java.util.Random;
import org.json.JSONObject;
import dungeonmania.Game;
import dungeonmania.entities.Wall;
import dungeonmania.entities.enemies.ZombieToast;
import dungeonmania.entities.enemies.ZombieToastSpawner;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class ZombieFactory implements BuildFactory {
    private JSONObject config;
    private Random randGen = new Random();

    public ZombieFactory(JSONObject config) {
        this.config = config;
    }

    @Override
    public ZombieToast build(Position pos) {
        double zombieHealth = config.optDouble("zombie_health", ZombieToast.DEFAULT_HEALTH);
        double zombieAttack = config.optDouble("zombie_attack", ZombieToast.DEFAULT_ATTACK);
        return new ZombieToast(pos, zombieHealth, zombieAttack);
    }

    public void spawnZombie(Game game, ZombieToastSpawner spawner) {
        GameMap map = game.getMap();
        int tick = game.getTick();
        int spawnInterval = config.optInt("zombie_spawn_interval", ZombieToastSpawner.DEFAULT_SPAWN_INTERVAL);

        if (spawnInterval == 0 || (tick + 1) % spawnInterval != 0)
            return;

        List<Position> pos = spawner.getPosition().getCardinallyAdjacentPositions();
        pos = pos.stream()
                .filter(p -> map.getEntities(p).stream().noneMatch(Wall.class::isInstance))
                .toList();

        if (pos.isEmpty())
            return;

        ZombieToast zt = build(pos.get(randGen.nextInt(pos.size())));
        map.addEntity(zt);
        map.registerPotionListener(zt);
        game.register(() -> zt.move(game), Game.AI_MOVEMENT, zt.getId());
    }

    public ZombieToastSpawner buildZombieToastSpawner(Position pos) {
        int zombieSpawnRate = config.optInt("zombie_spawn_interval", ZombieToastSpawner.DEFAULT_SPAWN_INTERVAL);
        return new ZombieToastSpawner(pos, zombieSpawnRate);
    }
}
