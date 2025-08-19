package com.leukim.commander.clients;

import com.leukim.commander.application.ports.in.model.CreateProductDto;
import com.leukim.commander.infrastructure.controllers.model.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.util.List;
import java.util.UUID;

@FeignClient(url = "${service.url:http://localhost:${local.server.port}}", name = "productClient")
public interface ProductClient {
    @RequestMapping(method = RequestMethod.GET, value = "/api/products")
    List<ProductDto> getAll();

    @RequestMapping(method = RequestMethod.POST, value = "/api/products")
    ProductDto create(@RequestBody CreateProductDto productDto);

    @RequestMapping(method = RequestMethod.GET, value = "/api/products/{id}")
    ProductDto getById(@PathVariable UUID id);

    @RequestMapping(method = RequestMethod.DELETE, value = "/api/products/{id}")
    void delete(@PathVariable UUID id);
}

