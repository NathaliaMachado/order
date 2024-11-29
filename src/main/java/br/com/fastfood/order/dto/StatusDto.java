package br.com.fastfood.order.dto;

import br.com.fastfood.order.model.Status;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatusDto {
    private Status status;
}
