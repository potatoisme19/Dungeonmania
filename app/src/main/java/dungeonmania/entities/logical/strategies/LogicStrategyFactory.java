package dungeonmania.entities.logical.strategies;

public class LogicStrategyFactory {
    public static LogicStrategy createStrategy(String logicType) {
        switch (logicType) {
            case "and":
                return new AndStrategy();
            case "or":
                return new OrStrategy();
            case "xor":
                return new XorStrategy();
            case "co_and":
                return new CoAndStrategy();
            default:
                return new AndStrategy();
        }
    }
}
