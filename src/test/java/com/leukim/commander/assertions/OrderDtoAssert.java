package com.leukim.commander.assertions;

import static com.leukim.commander.assertions.Assertions.assertThat;

import com.leukim.commander.infrastructure.controllers.model.OrderDto;
import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;
import org.assertj.core.api.AbstractAssert;

public final class OrderDtoAssert extends AbstractAssert<OrderDtoAssert, OrderDto> {

    OrderDtoAssert(OrderDto actual) {
        super(actual, OrderDtoAssert.class);
    }

    public OrderDtoAssert hasId(UUID expectedId) {
        isNotNull();
        assertThat(actual.id()).isEqualTo(expectedId);
        return this;
    }

    public OrderDtoAssert hasName(String expectedName) {
        isNotNull();
        assertThat(actual.name()).isEqualTo(expectedName);
        return this;
    }

    public OrderDtoAssert hasItems(Map<UUID, Double> expectedItems) {
        isNotNull();
        assertThat(actual.items())
            .hasSize(expectedItems.size())
            .containsExactlyInAnyOrderEntriesOf(expectedItems);
        return this;
    }

    public OrderDtoAssert hasNoItems() {
        isNotNull();
        assertThat(actual.items()).isEmpty();
        return this;
    }

    public OrderDtoAssert isPicked() {
        isNotNull();
        assertThat(actual.picked()).isTrue();
        return this;
    }

    public OrderDtoAssert isNotPicked() {
        isNotNull();
        assertThat(actual.picked()).isFalse();
        return this;
    }

    public void hasDate(LocalDate date) {
        isNotNull();
        assertThat(actual.date()).isEqualTo(date);
    }
}
