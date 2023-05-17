package jpabook.real1.repository.order.simpleQuery;

import jpabook.real1.domain.Address;
import jpabook.real1.domain.Order;
import jpabook.real1.domain.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

    @Data
    public class OrderSimpleQueryDto {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address; //배송지 주소

        //v3에 사용
        public OrderSimpleQueryDto(Order order) {
            orderId = order.getId();
            name = order.getMember().getUsername();
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress();
        }
        //v4
        public OrderSimpleQueryDto(Long orderId, String name,
                                   LocalDateTime orderDate, OrderStatus orderStatus, Address address) {
            this.orderId = orderId;
            this.name = name;
            this.orderDate = orderDate;
            this.orderStatus = orderStatus;
            this.address = address;

    }
}
