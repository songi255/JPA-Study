package org.example.model.entity.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import org.example.model.entity.Category;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // 부모 클래스로 만듦. 전략은 쉽게 설명하기 위해 단일테이블 설정했다.
@DiscriminatorColumn(name = "DTYPE") // 단일테이블 전략이므로 구분칼럼명을 설정해줬다. (DTYPE은 애초에 기본값이다.)
public abstract class Item { // super class 이므로 abstract 로 설정해줬다.
    @Id @GeneratedValue
    @Column(name = "ITEM_ID")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();
}
