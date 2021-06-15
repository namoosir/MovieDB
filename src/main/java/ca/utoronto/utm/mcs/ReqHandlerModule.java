package ca.utoronto.utm.mcs;

import dagger.Module;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;

@Module
public class ReqHandlerModule {
    // TODO Complete This Module

    @Provides
    public Neo4jDAO provideNeo4jDAO(){
        String uriDb = "bolt://localhost:7687";
        Driver driver = GraphDatabase.driver(uriDb, AuthTokens.basic("neo4j","1234"));

        return new Neo4jDAO(driver);
    }
}
