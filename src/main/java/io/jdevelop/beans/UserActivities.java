package io.jdevelop.beans;

import java.time.LocalDate;

import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.CassandraType.Name;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import io.jdevelop.keys.UserActivitiesPrimaryKey;
import lombok.Data;

@Data
@Table("user_activity")
public class UserActivities {

    @PrimaryKey
    private UserActivitiesPrimaryKey id;

    @Column("completion_status")
    @CassandraType(type = Name.TEXT)
    private String completionStatus;

    @Column("started_date")
    @CassandraType(type = Name.DATE)
    private LocalDate startedDate;

    @Column("completed_date")
    @CassandraType(type = Name.DATE)
    private LocalDate completedDate;

    @Column("rating")
    @CassandraType(type = Name.INT)
    private int rating;

}
