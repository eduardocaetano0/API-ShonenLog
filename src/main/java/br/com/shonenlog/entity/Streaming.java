package br.com.shonenlog.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "streaming")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Streaming {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String name;
}
