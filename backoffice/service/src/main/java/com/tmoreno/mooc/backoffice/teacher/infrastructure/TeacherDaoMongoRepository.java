package com.tmoreno.mooc.backoffice.teacher.infrastructure;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface TeacherDaoMongoRepository extends MongoRepository<TeacherMongoDto, String> {

    boolean existsByEmail(String email);

}
