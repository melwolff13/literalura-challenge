package br.com.melissa.literalura.service;

import tools.jackson.databind.ObjectMapper;

public class Conversor {
    ObjectMapper mapper = new ObjectMapper();
    public <T> T converte(String json, Class<T> classe) {
        return mapper.readValue(json, classe);
    }
}
