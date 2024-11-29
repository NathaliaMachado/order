package br.com.fastfood.order.repository;

import br.com.fastfood.order.model.Orders;
import br.com.fastfood.order.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface OrderRepository extends JpaRepository<Orders, Long> {
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Orders p set p.status = :status where p.id = :orderId")
    void updateStatus(Status status, Orders order, Long orderId);

    @Query(value = "SELECT p from Orders p LEFT JOIN FETCH p.items where p.id = :id")
    Orders itemsById(Long id);


}
