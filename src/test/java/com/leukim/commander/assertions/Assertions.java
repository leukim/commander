package com.leukim.commander.assertions;

import com.leukim.commander.infrastructure.controllers.model.OrderDto;
import com.leukim.commander.infrastructure.controllers.model.ProductDto;
import java.util.List;

public class Assertions extends org.assertj.core.api.Assertions {
    public static ProductDtoAssert assertThat(ProductDto actual) {
        return new ProductDtoAssert(actual);
    }

    public static ProductDtoListAssert assertThatProductList(List<ProductDto> actual) {
        return new ProductDtoListAssert(actual);
    }

    public static OrderDtoAssert assertThat(OrderDto actual) {
        return new OrderDtoAssert(actual);
    }
}
