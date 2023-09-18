package bg.sfa_library_management.service.impl;

import bg.sfa_library_management.dao.OrderDAO;
import bg.sfa_library_management.model.dtos.binding.OrderBindingDTO;
import bg.sfa_library_management.model.dtos.view.OrderViewDTO;
import bg.sfa_library_management.model.entity.Order;
import bg.sfa_library_management.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderDAO orderDAO;
    private final ModelMapper modelMapper;

    @Autowired
    public OrderServiceImpl(OrderDAO orderDAO, ModelMapper modelMapper) {
        this.orderDAO = orderDAO;
        this.modelMapper = modelMapper;
    }

    @Override
    public Optional<OrderViewDTO> getOrderById(long id) throws SQLException {
        return this.orderDAO.findOrderById(id)
                .map(this::orderToDTOMapper);
    }

    @Override
    public List<OrderViewDTO> getAllOrdersForClientId(long id) throws SQLException {
        return this.orderDAO.findAllByClientId(id)
                .stream()
                .map(this::orderToDTOMapper)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderViewDTO> getAllOrders() throws SQLException {

        return this.orderDAO.findAll()
                .stream()
                .map(this::orderToDTOMapper)
                .collect(Collectors.toList());
    }

    @Override
    public OrderViewDTO createOrder(OrderBindingDTO orderBindingDTO, long clientId) throws SQLException {

        Order savedOrder = this.orderDAO.saveOrder(orderBindingDTO, clientId);

        return orderToDTOMapper(savedOrder);
    }

    @Override
    public void deleteOrderById(long id) throws SQLException {
        this.orderDAO.deleteById(id);
    }

    private OrderViewDTO orderToDTOMapper(Order order) {
        return this.modelMapper.map(order, OrderViewDTO.class);
    }
}
