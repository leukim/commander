package com.leukim.commander.clients;

import com.leukim.commander.application.ports.in.model.AddOrderItemDto;
import com.leukim.commander.application.ports.in.model.CreateOrderDto;
import com.leukim.commander.infrastructure.controllers.model.OrderDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.UUID;

@FeignClient(url = "${service.url:http://localhost:${local.server.port}}", name = "orderClient")
public interface OrderClient {
    @RequestMapping(method = RequestMethod.GET, value = "/api/orders")
    List<OrderDto> getAll();

    @RequestMapping(method = RequestMethod.POST, value = "/api/orders")
    OrderDto create(@RequestBody CreateOrderDto orderDto);

    @RequestMapping(method = RequestMethod.GET, value = "/api/orders/{id}")
    OrderDto getById(@PathVariable UUID id);

    @RequestMapping(method = RequestMethod.DELETE, value = "/api/orders/{id}")
    void delete(@PathVariable UUID id);

    @RequestMapping(method = RequestMethod.POST, value = "/api/orders/{orderId}/items")
    OrderDto addItem(@PathVariable UUID orderId, @RequestBody AddOrderItemDto dto);

    @RequestMapping(method = RequestMethod.DELETE, value = "/api/orders/{orderId}/items/{productId}")
    OrderDto removeItem(@PathVariable UUID orderId, @PathVariable UUID productId);

    @RequestMapping(method = RequestMethod.GET, value = "/api/orders/date/{date}")
    List<OrderDto> getByDate(@PathVariable String date); // Feign only supports String for path variables
}
