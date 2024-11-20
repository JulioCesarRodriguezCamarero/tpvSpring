package org.grisu.tpvspring.modelo;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = {"lineaVentas","cuentasList"})
@EqualsAndHashCode(exclude = {"lineaVentas","cuentasList"})
@Entity
@Table(name = "tiques")
public class Tique implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date fecha;
    private Double total;
    private boolean activo;

    @ManyToMany(targetEntity = Cuenta.class,mappedBy = "tiques",fetch = FetchType.EAGER)
    private List<Cuenta> cuentasList;

    @OneToMany(targetEntity = LineaVentas.class, cascade = {CascadeType.PERSIST,CascadeType.MERGE}, fetch = FetchType.LAZY,mappedBy = "tique")
    private List<LineaVentas> lineaVentas;

    public Tique() {
        lineaVentas = new ArrayList<>();
        cuentasList = new ArrayList<>();
    }

    public Tique(Date fecha, Double total) {
        this();
        this.fecha = fecha;
        this.total = total;
    }


}
