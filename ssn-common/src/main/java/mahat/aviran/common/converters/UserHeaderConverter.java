package mahat.aviran.common.converters;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import mahat.aviran.common.entities.dtos.UserDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
@Log4j2
public class UserHeaderConverter implements Converter<String, UserDto> {

    private final ObjectMapper objectMapper;

    @SneakyThrows
    @Override
    public UserDto convert(String headerValue) {
        return objectMapper.convertValue(objectMapper.readTree(headerValue.getBytes(StandardCharsets.UTF_8)), UserDto.class);
    }
}
