package io.jdevelop.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableCassandraRepositories({"io.jdevelop.repository"})
public class CassandraConfig extends AbstractCassandraConfiguration {

    @Override
    protected String getKeyspaceName() {
        return "main";
    }

    @Override
    public SchemaAction getSchemaAction() {
      return SchemaAction.CREATE_IF_NOT_EXISTS;
    }

    @Override
    public String[] getEntityBasePackages() {
        log.debug("getEntityBasePackages");
		return new String[] {"io.jdevelop.beans"};
	}

}
