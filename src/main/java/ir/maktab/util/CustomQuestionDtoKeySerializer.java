package ir.maktab.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import ir.maktab.model.dto.QuestionDto;

import java.io.IOException;

public class CustomQuestionDtoKeySerializer extends JsonSerializer<QuestionDto> {
    private static ObjectMapper mapper = new ObjectMapper();
    @Override
    public void serialize(QuestionDto questionDto, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeFieldName(mapper.writeValueAsString(questionDto));
    }
}
