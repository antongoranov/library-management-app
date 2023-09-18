package bg.sfa_library_management.web;

import bg.sfa_library_management.model.dtos.binding.OrderBindingDTO;
import bg.sfa_library_management.model.dtos.view.OrderViewDTO;
import bg.sfa_library_management.model.security.ClientUserDetails;
import bg.sfa_library_management.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create")
    public ResponseEntity<Void> addOrder(@Valid @RequestBody OrderBindingDTO orderBindingDTO,
                                         @AuthenticationPrincipal ClientUserDetails client,
                                         BindingResult bindingResult,
                                         UriComponentsBuilder uriComponentsBuilder) throws SQLException {

        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException(bindingResult
                    .getFieldErrors()
                    .get(0)
                    .getDefaultMessage());
        }

        //no need to check the client, because /create is only accessed when authenticated
        OrderViewDTO createdOrder = this.orderService.createOrder(orderBindingDTO, client.getId());

        return ResponseEntity.created(uriComponentsBuilder
                        .path("/orders/{id}")
                        .build(createdOrder.getId()))
                .build();
    }

    @GetMapping
    public ResponseEntity<List<OrderViewDTO>> getAllOrders() throws SQLException {
        List<OrderViewDTO> allOrders = this.orderService.getAllOrders();
        return ResponseEntity.ok(allOrders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderViewDTO> orderById(@PathVariable("id") long id) throws SQLException {

        Optional<OrderViewDTO> orderByIdDTO = this.orderService.getOrderById(id);

        if (orderByIdDTO.isEmpty()) {
            return ResponseEntity
                    .notFound()
                    .build();
        }

        return ResponseEntity.ok(orderByIdDTO.get());
    }

    @GetMapping("/clients/{id}")
    public ResponseEntity<List<OrderViewDTO>> allOrdersForClient(@PathVariable("id") long id) throws SQLException {

        List<OrderViewDTO> allOrdersForClientId = this.orderService.getAllOrdersForClientId(id);

        return ResponseEntity.ok(allOrdersForClientId);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderById(@PathVariable("id") long id) throws SQLException {

        Optional<OrderViewDTO> optOrder = this.orderService.getOrderById(id);

        if (optOrder.isEmpty()) {
            return ResponseEntity
                    .notFound()
                    .build();
        }

        this.orderService.deleteOrderById(id);

        return ResponseEntity.noContent().build();

    }
}
