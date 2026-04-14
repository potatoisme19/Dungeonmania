package dungeonmania.entities.logical;

public interface ConductingSubject {
    void addObserver(ConductingObserver observer);

    void removeObserver(ConductingObserver observer);

    void notifyObservers(boolean isConducting, int tick);
}
