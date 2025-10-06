package com.harshit.notes.entity;


import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Users")
@Data
public class Users {
    @Id
    private ObjectId id;
    @NonNull
    private String name;
    private String email;
    @NonNull
    private String password;
}
