package com.av.services;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface ObjectFormatter {
    String format(Object object) throws JsonProcessingException;
}
