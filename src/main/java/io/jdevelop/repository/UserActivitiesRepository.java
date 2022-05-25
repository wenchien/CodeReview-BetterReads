package io.jdevelop.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import io.jdevelop.beans.UserActivities;
import io.jdevelop.keys.UserActivitiesPrimaryKey;

@Repository
public interface UserActivitiesRepository extends CassandraRepository<UserActivities, UserActivitiesPrimaryKey>{


}
