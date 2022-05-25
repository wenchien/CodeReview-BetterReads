package io.jdevelop.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import io.jdevelop.beans.UserBooks;

public interface UserBooksRepository extends CassandraRepository<UserBooks, String>{
    Slice<UserBooks> findAllById(String id, Pageable pageable);
}
