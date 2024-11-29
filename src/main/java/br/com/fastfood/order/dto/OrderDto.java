package br.com.fastfood.order.dto;

import br.com.fastfood.order.model.Status;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private Long id;
    private LocalDateTime dateTime;
    private Status status;
    private List<OrderItemDto> items = new ArrayList<>();

}
