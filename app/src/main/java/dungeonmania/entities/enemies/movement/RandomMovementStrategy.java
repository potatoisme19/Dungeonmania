package dungeonmania.entities.enemies.movement;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import dungeonmania.entities.Player;
import dungeonmania.entities.enemies.Enemy;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class RandomMovementStrategy extends MovementStrategy {
    private final Random randGen = new Random();

    @Override
    public Position getNextPosition(Enemy enemy, Player player, GameMap map) {
        List<Position> validPositions = getValidAdjacentPositions(enemy, map);

        if (validPositions.isEmpty()) {
            return enemy.getPosition();
        }
        return validPositions.get(randGen.nextInt(validPositions.size()));
    }

    private List<Position> getValidAdjacentPositions(Enemy enemy, GameMap map) {
        return enemy.getPosition().getCardinallyAdjacentPositions().stream()
                .filter(p -> map.canMoveTo(enemy, p))
                .collect(Collectors.toList());
    }
}
