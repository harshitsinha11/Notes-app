package com.harshit.notes.repository;

import com.harshit.notes.entity.UsersEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UsersEntity, ObjectId> {
    public UsersEntity findByUserName(String userName);
}
