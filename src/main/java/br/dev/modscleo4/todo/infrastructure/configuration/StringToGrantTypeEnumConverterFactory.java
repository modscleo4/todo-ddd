package br.dev.modscleo4.todo.infrastructure.configuration;

import br.dev.modscleo4.todo.domain.auth.GrantType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

public final class StringToGrantTypeEnumConverterFactory implements ConverterFactory<String, GrantType> {
    @Override
    public <T extends GrantType> Converter<String, T> getConverter(Class<T> targetType) {
        return new StringToGrantTypeEnum(targetType);
    }

    public record StringToGrantTypeEnum<T extends GrantType>(Class<T> targetType) implements Converter<String, T> {
        @Override
        public T convert(String source) {
            if (source.isEmpty()) {
                return null;
            }

            return (T) GrantType.valueOf(source.toUpperCase());
        }
    }

}
