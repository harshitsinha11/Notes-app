package com.harshit.notes.repository;

import com.harshit.notes.entity.NotesEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotesRepository extends MongoRepository<NotesEntity, ObjectId> {
}
