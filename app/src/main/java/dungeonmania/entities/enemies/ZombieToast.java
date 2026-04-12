package dungeonmania.entities.enemies;

import dungeonmania.entities.PotionListener;
import dungeonmania.entities.collectables.potions.InvincibilityPotion;
import dungeonmania.entities.collectables.potions.Potion;
import dungeonmania.util.Position;
import dungeonmania.entities.enemies.movement.*;

public class ZombieToast extends Enemy implements PotionListener {
    public static final double DEFAULT_HEALTH = 5.0;
    public static final double DEFAULT_ATTACK = 6.0;

    public ZombieToast(Position position, double health, double attack) {
        super(position, health, attack);
        this.setMovementStrategy(new RandomMovementStrategy());
    }

    @Override
    public void notifyPotion(Potion potion) {
        if (potion instanceof InvincibilityPotion)
            setMovementStrategy(new FleeMovementStrategy());
    }

    @Override
    public void notifyNoPotion() {
        setMovementStrategy(new RandomMovementStrategy());
    }

}
