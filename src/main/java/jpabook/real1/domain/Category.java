package jpabook.real1.domain;

import jpabook.real1.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@Entity
public class Category {
    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    @OneToMany(mappedBy = "category")
    private List<CategoryItem> categoryItems = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();

    //==연관관계 편의 메소드==//
    public void addChildCategory(Category child) { //child를 넣으면 부모와 자식 모두에서 들어와야함
        this.child.add(child); //부모에서 자식이 누군지 확인
        child.setParent(this); //자식에서 부모가 누군지 확인
    }
}
