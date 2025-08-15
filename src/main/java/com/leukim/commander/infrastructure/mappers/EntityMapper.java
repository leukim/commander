package com.leukim.commander.infrastructure.mappers;

import java.util.List;

public interface EntityMapper<T, R, D> {
    T toDto(R model);
    R fromDto(T dto);

    D toDbModel(R model);
    R fromDbModel(D dbModel);

    List<T> toDtoList(List<R> models);
}
