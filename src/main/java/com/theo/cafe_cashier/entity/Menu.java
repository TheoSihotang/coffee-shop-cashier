package com.theo.cafe_cashier.entity;

import com.theo.cafe_cashier.constant.TableConstant;
import jakarta.persistence.*;
import lombok.*;

@Table(name = TableConstant.TABLE_MENU)
@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "menu_name", nullable = false)
    private String name;

    @Column(name = "menu_price", nullable = false)
    private Long price;

    @Column(name = "status")
    private Boolean readyOrNot;

    @Column(name = "decription")
    private String description;

    @OneToOne
    @JoinColumn(name = "image_id", unique = true)
    private Image image;
}
