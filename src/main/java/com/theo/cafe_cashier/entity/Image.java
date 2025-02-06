package com.theo.cafe_cashier.entity;

import com.theo.cafe_cashier.constant.TableConstant;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = TableConstant.TABLE_IMAGE)
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "image_name", nullable = false)
    private String name;

    @Column(name = "path", nullable = false)
    private String path;

    @Column(name = "size")
    private Long size;

    @Column(name = "type")
    private String contentType;

    @OneToOne(mappedBy = "image")
    private Menu menu;
}
