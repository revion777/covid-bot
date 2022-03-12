package com.revion.covidbot;

import com.revion.covidbot.entities.UserEntity;
import com.revion.covidbot.repositories.UserRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

/**
 * @author Maxim Negodyuk created on 12.03.2022
 */
@SpringBootTest
@Disabled
class UserRepositoryTest {

    static UserRepository userRepository = Mockito.mock(UserRepository.class);

    @BeforeAll
    static void initMocks() {
        UserEntity user = new UserEntity();

        Mockito.when(userRepository.findById(Mockito.any()))
                .thenReturn(java.util.Optional.of(user))
                .then(getAnswer());
    }

    private static Answer<Optional<UserEntity>> getAnswer() {
        return invocationOnMock -> {
            Object[] arguments = invocationOnMock.getArguments();
            if (arguments == null || arguments.length == 0) {
                return Optional.empty();
            } else {
                return Optional.of(new UserEntity());
            }
        };
    }

    @Test
    void test() {
        Assertions.assertTrue(userRepository.findById(Mockito.any()).isPresent());
        Assertions.assertNotNull(userRepository.findById(Mockito.any()));
    }

    @AfterAll
    static void verifyTest() {
        //проверяет на количество вызовов
        Mockito.verify(userRepository, Mockito.times(2)).findById(Mockito.any());
        // ожидание вызова теста хотябы один раз
        Mockito.verify(userRepository, Mockito.after(1000).times(2)).findById(Mockito.any());
        //вызван хоть один раз
        Mockito.verify(userRepository, Mockito.atLeast(2)).findById(Mockito.any());
    }

}
