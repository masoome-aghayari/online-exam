package ir.maktab.util;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import ir.maktab.model.entity.Question;

import java.io.IOException;

public class CustomExamDtoDeserializer extends KeyDeserializer {
    private static ObjectMapper mapper = new ObjectMapper();

    @Override
    public Object deserializeKey(String s, DeserializationContext deserializationContext) throws IOException {
         return mapper.readValue(s, Question.class);
    }
}
