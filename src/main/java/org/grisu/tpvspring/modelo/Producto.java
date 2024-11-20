package org.grisu.tpvspring.modelo;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "productos")
public class Producto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private double precio;
    @Lob
    private byte[] imagen;
    @Transient
    private int cantidad;
    @Transient
    private double subTotal;
    @OneToOne
    private LineaVentas lineaVentas;

}
