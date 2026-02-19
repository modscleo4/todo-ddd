package br.dev.modscleo4.todo;

import jakarta.persistence.GeneratedValue;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.data.repository.CrudRepository;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Random;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestUtils {
    private static final Random random = new Random();

    /**
     * Mocks {@link CrudRepository#save(Object)} method to return the
     * saved entity as it was passed as parameter and add generated ID to it.
     * If ID could not be generated, it will be ignored.
     * If parameter already has and ID, it will be overridden.
     */
    public static <T, V> void mockSave(CrudRepository<T, V> repository) {
        when(repository.save(any())).thenAnswer(i -> {
            var argument = i.getArgument(0);
            Arrays.stream(argument.getClass().getDeclaredFields())
                    .filter(f -> f.getAnnotation(GeneratedValue.class) != null)
                    .forEach(f -> enrichGeneratedValueField(argument, f));
            return argument;
        });
    }

    private static void enrichGeneratedValueField(Object argument, Field field) {
        try {
            if (field.getType().isAssignableFrom(Integer.class)) {
                FieldUtils.writeField(field, argument, Math.abs(random.nextInt()), true);
            } else {
                FieldUtils.writeField(field, argument, mock(field.getType()), true);
            }
        } catch (Exception _) {

        }
    }
}
