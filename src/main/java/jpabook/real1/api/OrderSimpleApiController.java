package jpabook.real1.api;

import jpabook.real1.domain.*;
import jpabook.real1.repository.OrderRepository;
import jpabook.real1.repository.OrderSearch;
import jpabook.real1.repository.order.simpleQuery.OrderSimpleQueryDto;
import jpabook.real1.repository.order.simpleQuery.OrderSimpleQueryRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * xToOne(ManyToOne, OneToOne)
 * Order
 * Order -> Member
 * Order -> Delivery
 */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {
    private final OrderRepository orderRepository;
    private final OrderSimpleQueryRepository orderSimpleQueryRepository;

    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1() {
        List<Order> all = orderRepository.findAllByCriteria(new OrderSearch());
        for (Order order: all) {
            order.getMember().getUsername(); //lazy 강제 초기화
            order.getDelivery().getAddress(); //lazy 강제 초기화
        }
        return all;
    }

    @GetMapping("/api/v2/simple-orders")
    public Result orderV2() {
        List<Order> findOrders = orderRepository.findAllByCriteria(new OrderSearch());
        List<OrderSimpleQueryDto> collect = findOrders.stream()
                .map(o -> new OrderSimpleQueryDto(o))
                .collect(toList());

        return new Result<>(collect);

    }

    @GetMapping("/api/v3/simple-orders")
    public Result orderV3() {
        List<Order> orders = orderRepository.findAllWithMemberDelivery();
        List<OrderSimpleQueryDto> collect = orders.stream()
                .map(o -> new OrderSimpleQueryDto(o))
                .collect(toList());
        return new Result(collect);
    }

    @GetMapping("/api/v4/simple-orders")
    public Result orderV4() {
        return new Result<>(orderSimpleQueryRepository.findOrderDtos());
    }
    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }


}
