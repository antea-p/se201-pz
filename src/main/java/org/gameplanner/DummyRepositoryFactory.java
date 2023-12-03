package org.gameplanner;

import java.sql.Connection;

public class DummyRepositoryFactory extends AbstractFactory {
    @Override
    public GameRepository getGameRepository(String repositoryType, Connection con) {
        if (repositoryType.equalsIgnoreCase("DUMMY")) {
            return new DummyGameRepository();
        };
        return null;
    }
}
