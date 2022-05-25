package io.jdevelop.keys;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

import lombok.Data;

@Data
@PrimaryKeyClass
public class UserActivitiesPrimaryKey {

    @PrimaryKeyColumn(name = "userId", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private String userId;

    @PrimaryKeyColumn(name = "bookId", ordinal = 1, type = PrimaryKeyType.PARTITIONED)
    private String bookId;
}
