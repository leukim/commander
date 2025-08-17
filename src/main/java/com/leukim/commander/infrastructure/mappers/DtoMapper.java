package com.leukim.commander.infrastructure.mappers;

import java.util.List;

public interface DtoMapper<T, R> {
    T toDto(R model);
    R fromDto(T dto);

    List<T> toDtoList(List<R> models);
}
