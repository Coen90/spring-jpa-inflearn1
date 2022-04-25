package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

// @Table(name = "원하는이름") // 안적으면 camel case 필드명을 snake로 변경하는게 default
@Entity
@Getter @Setter
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @NotEmpty
    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>(); // 하이버네이트는 엔티티를 영속화 할 때 컬렉션을 하이버네이트가 제공하는 내장 컬렉션으로 변경.

}
