package org.gameplanner;

import java.sql.Connection;

public abstract class AbstractFactory {

    public abstract GameRepository getGameRepository(String repositoryType, Connection con);
}
