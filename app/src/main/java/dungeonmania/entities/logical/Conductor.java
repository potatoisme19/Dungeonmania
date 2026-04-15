package dungeonmania.entities.logical;

public interface Conductor {
    boolean isConducting();

    void setConducting(boolean conducting);

    int getActivatedTick();

    void setActivatedTick(int tick);

}
