package com.av.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class ObjectFormatterImpl implements ObjectFormatter {

    @Override
    public String format(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String dats = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        return dats;
    }
}
