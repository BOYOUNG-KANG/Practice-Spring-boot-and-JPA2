package jpabook.real1.repository.order.query;

import jpabook.real1.dto.queryDto.OrderFlatDto;
import jpabook.real1.dto.queryDto.OrderItemQueryDto;
import jpabook.real1.dto.queryDto.OrderQueryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {
    private final EntityManager em;


    public List<OrderQueryDto> findQueryDtos() {
        List<OrderQueryDto> result = findOrders();
        result.forEach(o -> {
            List<OrderItemQueryDto> orderItems = findOrderItems(o.getOrderId());
            o.setOrderItems(orderItems);
        });
        return result;
    }

    public List<OrderQueryDto> findAllByDto_optimization() {
        List<OrderQueryDto> result = findOrders();

        Map<Long, List<OrderItemQueryDto>> orderItemMap = findOrderItemMap(findOrderId(result));

        result.forEach(o-> o.setOrderItems(orderItemMap.get(o.getOrderId())));

        return result;
    }

    private Map<Long, List<OrderItemQueryDto>> findOrderItemMap(List<Long> orderIds) {
        List<OrderItemQueryDto> orderItmes = em.createQuery(
                        "select new jpabook.real1.dto.queryDto.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count)" +
                                " from OrderItem oi"+
                                " join oi.item i" +
                                " where oi.order.id in :orderIds", OrderItemQueryDto.class)
                .setParameter("orderIds", orderIds)
                .getResultList();

        // orderid 기준으로 orderitem map으로 수정
        Map<Long, List<OrderItemQueryDto>> orderItemMap = orderItmes.stream()
                .collect(Collectors.groupingBy(orderItemQueryDto -> orderItemQueryDto.getOrderId()));
        return orderItemMap;
    }

    private static List<Long> findOrderId(List<OrderQueryDto> result) {
        List<Long> orderIds = result.stream()
                        .map(o->o.getOrderId())
                        .collect(Collectors.toList());
        return orderIds;
    }

    //컬랙션 부분
    private List<OrderItemQueryDto> findOrderItems(Long orderId) {
        return em.createQuery(
                "select new jpabook.real1.dto.queryDto.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count)" +
                        " from OrderItem oi"+
                        " join oi.item i" +
                        " where oi.order.id = :orderId", OrderItemQueryDto.class)
                .setParameter("orderId", orderId)
                .getResultList();
    }

    //컬랙션 제외 부분
    private List<OrderQueryDto> findOrders() {
        return em.createQuery(
                        "select new jpabook.real1.dto.queryDto.OrderQueryDto(o.id, m.username, o.orderDate, o.status, d.address)" +
                                " from Order o" +
                                " join o.member m" +
                                " join o.delivery d", OrderQueryDto.class)
                .getResultList();
    }

    public List<OrderFlatDto> findAllByDto_flat() {
        return em.createQuery(
                "select new jpabook.real1.dto.queryDto.OrderFlatDto(o.id, m.username, o.orderDate, o.status, d.address, i.name, oi.orderPrice, oi.count)"+
                " from Order o"+
                " join o.member m" +
                " join o.delivery d"+
                " join o.orderItems oi"+
                " join oi.item i", OrderFlatDto.class)
                .getResultList();
    }
}
