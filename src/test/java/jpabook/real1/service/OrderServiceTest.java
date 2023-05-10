package jpabook.real1.service;

import jpabook.real1.domain.Address;
import jpabook.real1.domain.Member;
import jpabook.real1.domain.Order;
import jpabook.real1.domain.OrderStatus;
import jpabook.real1.domain.item.Book;
import jpabook.real1.domain.item.Item;
import jpabook.real1.exception.NotEnoughStockException;
import jpabook.real1.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@Transactional
@SpringBootTest
class OrderServiceTest {

    @Autowired EntityManager em;
    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;
    @Test
    public void 상품주문() throws Exception {
        //given
        Member member = createMember("회원1");
        Item book = createBook("토비의 스프링", 10000, 10);

        int orderCount = 2;
        //when
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        //then
        Order getOrder = orderRepository.findOne(orderId);
        assertEquals(OrderStatus.ORDER, getOrder.getStatus());//상품 주문시 상태는 order
        assertEquals(1, getOrder.getOrderItems().size()); //주문한 상품 종류의 수가 정화해야한다
        assertEquals(10000 * orderCount, getOrder.getTotalPrice()); //주문 가격은 가격 * 수량이다
        assertEquals(8, book.getStockQuantity()); //주문 수량만큼 재고가 줄어야 한다

    }


    @Test
    public void 주문취소() throws Exception {
        //given
        Member member = createMember("grace");
        Item book = createBook("토비의 스프링", 20000, 10);

        int orderCount = 9;

        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);
        //when
        assertEquals(1, book.getStockQuantity());
        orderService.cancelOrder(orderId);

        //then
        Order findOrder = orderRepository.findOne(orderId);

        assertEquals(OrderStatus.CANCEL, findOrder.getStatus()); //주문 취소시 상태는 cancel이어야 한다.
        assertEquals(10, book.getStockQuantity()); //주문이 취소된 상품은 그만큰 재고가 증가해야 한다.
    }

    @Test
    public void 상품주문_재고수량초과() throws Exception {
        //given
        Member member = createMember("회원1");
        Item book = createBook("토비의 스프링", 10000, 10);

        int orderCount = 11;

        //when

        //then
        assertThrows(NotEnoughStockException.class, () -> orderService.order(member.getId(), book.getId(), orderCount));

    }

    private Item createBook(String name, int price, int quantity) {
        Item book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(quantity);
        em.persist(book);
        return book;
    }

    private Member createMember(String name) {
        Member member = new Member();
        member.setUsername(name);
        member.setAddress(new Address("수원시", "영통구", "1234"));
        em.persist(member);
        return member;
    }

}