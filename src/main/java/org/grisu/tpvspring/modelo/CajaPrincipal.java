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
@ToString(exclude = "usuarios")
@EqualsAndHashCode(exclude = "usuarios")
@Entity
@Table(name = "caja_principal")
public class CajaPrincipal implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date aperturaDia;
    private Date cierreDia;
    private Double saldoApertura;
    private Double ventaAcumulada;
    private boolean estaAbierta;
    @OneToMany(targetEntity = Usuario.class, mappedBy = "cajaPrincipal",cascade = CascadeType.ALL)
      private List<Usuario> usuarios;

    public CajaPrincipal() {
        usuarios = new ArrayList<>();
        this.estaAbierta = false;
    }



}
