package org.gameplanner;

public class GameRepositoryFactoryProducer {
    public static AbstractFactory getFactory(boolean useJDBC) {
        if (useJDBC) {
            return new JDBCRepositoryFactory();
        } else {
            return new DummyRepositoryFactory();
        }
    }
}
