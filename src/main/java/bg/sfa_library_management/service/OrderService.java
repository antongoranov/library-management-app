package bg.sfa_library_management.service;

import bg.sfa_library_management.model.dtos.binding.OrderBindingDTO;
import bg.sfa_library_management.model.dtos.view.OrderViewDTO;
import bg.sfa_library_management.model.entity.Order;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface OrderService {
    Optional<OrderViewDTO> getOrderById(long id) throws SQLException;

    List<OrderViewDTO> getAllOrders() throws SQLException;

    OrderViewDTO createOrder(OrderBindingDTO orderBindingDTO, long clientId) throws SQLException;

    List<OrderViewDTO> getAllOrdersForClientId(long id) throws SQLException;

    void deleteOrderById(long id) throws SQLException;
}
