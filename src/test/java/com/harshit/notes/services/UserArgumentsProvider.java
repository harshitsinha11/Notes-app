package com.harshit.notes.services;

import com.harshit.notes.entity.UsersEntity;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.Arrays;
import java.util.stream.Stream;

public class UserArgumentsProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
        return Stream.of(
                Arguments.of(UsersEntity.builder().userName("User1").password("User1").build()),
                Arguments.of(UsersEntity.builder().userName("User2").password("User2").build())
        );
    }
}
