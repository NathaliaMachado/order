package br.com.fastfood.order.service;

import br.com.fastfood.order.dto.OrderDto;
import br.com.fastfood.order.dto.StatusDto;
import br.com.fastfood.order.model.Orders;
import br.com.fastfood.order.model.Status;
import br.com.fastfood.order.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private final ModelMapper mapper;

    public List<OrderDto> findAll() {
        return repository.findAll().stream()
                .map(p -> mapper.map(p, OrderDto.class))
                .collect(Collectors.toList());
    }

    public OrderDto findById(Long id) {
        Orders order = repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        return mapper.map(order, OrderDto.class);
    }

    public OrderDto create(OrderDto dto) {
        Orders order = mapper.map(dto, Orders.class);

        order.setDateTime(LocalDateTime.now());
        order.setStatus(Status.COMPLETED);
        order.getItems().forEach(item -> item.setOrder(order));
        Orders saved = repository.save(order);

        return mapper.map(order, OrderDto.class);
    }

    public OrderDto updateStatus(Long id, StatusDto dto) {

        Orders order = repository.itemsById(id);

        if (order == null) {
            throw new EntityNotFoundException();
        }

        order.setStatus(dto.getStatus());
        repository.updateStatus(dto.getStatus(), order, order.getId());
        return mapper.map(order, OrderDto.class);
    }

    public void aproveOrderPayment(Long id) {

        Orders order = repository.itemsById(id);

        if (order == null) {
            throw new EntityNotFoundException();
        }

        order.setStatus(Status.PAID);
        repository.updateStatus(Status.PAID, order, order.getId());
    }
}
