package com.leukim.commander.infrastructure.controllers;

import com.leukim.commander.application.model.Order;
import com.leukim.commander.application.ports.in.model.CreateOrderDto;
import com.leukim.commander.application.ports.out.OrderPersistencePort;
import com.leukim.commander.infrastructure.controllers.model.OrderDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.UUID;

import static com.leukim.commander.assertions.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderManagementControllerIntegrationTest {
    private static Order ORDER_1 = new Order(null, "TestOrder", Map.of(), false);
    // TODO Add picked order

    private UUID orderId;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private OrderPersistencePort persistencePort;

    @BeforeEach
    void setUp() {
        persistencePort.deleteAll();
        Order save = persistencePort.save(ORDER_1);

        orderId = save.id();
    }

    @Test
    void createOrder_returnsCreatedOrder() {
        CreateOrderDto createOrderDto = new CreateOrderDto("OrderName");
        ResponseEntity<OrderDto> response = restTemplate.postForEntity("/api/orders", createOrderDto, OrderDto.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        assertThat(response.getBody())
            .isNotNull()
            .hasName("OrderName")
            .hasNoItems()
            .isNotPicked();
    }

    @Test
    void getAllOrders_returnsOrderList() {
        OrderDto[] orders = restTemplate.getForEntity("/api/orders", OrderDto[].class).getBody();
        assertThat(orders).isNotEmpty();

        assertThat(orders[0])
            .hasId(orderId)
            .hasName(ORDER_1.name())
            .hasNoItems()
            .isNotPicked();
    }

    @Test
    void getOrderById_returnsOrder() {
        ResponseEntity<OrderDto> response = restTemplate.getForEntity("/api/orders/" + orderId, OrderDto.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        assertThat(response.getBody())
            .isNotNull()
            .hasId(orderId)
            .hasName(ORDER_1.name())
            .hasNoItems()
            .isNotPicked();
    }

    @Test
    void getOrderById_notFound_returns404() {
        UUID randomId = UUID.randomUUID();
        ResponseEntity<String> response = restTemplate.getForEntity("/api/orders/" + randomId, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).contains(randomId.toString());
    }

    @Test
    void deleteOrder_removesOrder() {
        restTemplate.delete("/api/orders/" + orderId);

        ResponseEntity<String> response = restTemplate.getForEntity("/api/orders/" + orderId, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).contains(orderId.toString());
    }
}
