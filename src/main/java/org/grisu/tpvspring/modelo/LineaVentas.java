package org.grisu.tpvspring.modelo;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "linea_ventas")
public class LineaVentas implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer unidades;
    private String producto;
    private Double subTotal;
    private Date fecha;
    @ManyToOne(targetEntity = Tique.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Tique tique;

    @OneToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE}, fetch = FetchType.EAGER)
    private Producto productoDetalle;


}
