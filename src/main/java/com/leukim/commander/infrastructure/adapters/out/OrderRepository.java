package com.leukim.commander.infrastructure.adapters.out;

import com.leukim.commander.infrastructure.adapters.out.model.DbOrder;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<DbOrder, UUID> {
    List<DbOrder> findByDate(LocalDate date);
}
