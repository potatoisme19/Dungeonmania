package dungeonmania.entities.logical;

public interface ConductingObserver {
    void onConductingStateChange(boolean isConducting, int tick);
}
