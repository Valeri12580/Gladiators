package project.gladiators.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.gladiators.model.entities.Order;
import project.gladiators.model.enums.OrderStatus;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order,String> {
    List<Order> findAllOrdersByCustomer_UsernameOrderByMadeOn(String username);

    Optional<Order> findOrderById(String id);

    List<Order> findAllByOrderStatus(OrderStatus orderStatus);

    void removeAllByOrderStatus(OrderStatus orderStatus);
}
