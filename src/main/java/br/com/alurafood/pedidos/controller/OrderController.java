package br.com.alurafood.pedidos.controller;

import br.com.alurafood.pedidos.dto.OrderDto;
import br.com.alurafood.pedidos.dto.StatusDto;
import br.com.alurafood.pedidos.service.OrderService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

        @Autowired
        private OrderService service;

        @GetMapping()
        public List<OrderDto> listAll() {
            return service.findAll();
        }

        @GetMapping("/{id}")
        public ResponseEntity<OrderDto> listById(@PathVariable @NotNull Long id) {
            OrderDto dto = service.findById(id);

            return  ResponseEntity.ok(dto);
        }

        @PostMapping()
        public ResponseEntity<OrderDto> placeOrder(@RequestBody @Valid OrderDto dto, UriComponentsBuilder uriBuilder) {
            OrderDto orderCompleted = service.create(dto);

            URI uri = uriBuilder.path("/orders/{id}").buildAndExpand(orderCompleted.getId()).toUri();

            return ResponseEntity.created(uri).body(orderCompleted);
        }

        @PutMapping("/{id}/status")
        public ResponseEntity<OrderDto> updateStatus(@PathVariable Long id, @RequestBody StatusDto status){
           OrderDto dto = service.updateStatus(id, status);

            return ResponseEntity.ok(dto);
        }

        @PutMapping("/{id}/paid")
        public ResponseEntity<Void> aprovePayment(@PathVariable @NotNull Long id) {
            service.aproveOrderPayment(id);

            return ResponseEntity.ok().build();
        }
}
