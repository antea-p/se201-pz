package org.gameplanner;

import java.sql.Connection;

public class JDBCRepositoryFactory extends AbstractFactory {

    @Override
    public GameRepository getGameRepository(String repositoryType, Connection con ) {
        if (repositoryType.equalsIgnoreCase("JDBC")) {
            return new JDBCGameRepository(con);
        }
        return null;
    }
}
