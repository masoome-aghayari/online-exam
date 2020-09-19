package ir.maktab.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import ir.maktab.model.dto.QuestionDto;

import java.io.IOException;

public class CustomExamDtoSerializer extends JsonSerializer<QuestionDto> {
    @Override
    public void serialize(QuestionDto questionDto, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

    }
}
