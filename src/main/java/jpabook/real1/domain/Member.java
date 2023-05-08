package jpabook.real1.domain;

import lombok.Getter;
import lombok.Setter;
import org.apache.tomcat.jni.Address;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@Entity
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String username;

    @Embedded
    private Address addressz;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    public Address getAddress() {
        return address;
    }
}
