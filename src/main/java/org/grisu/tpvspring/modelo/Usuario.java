package org.grisu.tpvspring.modelo;


import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = "cuentas")
@EqualsAndHashCode(exclude ="cuentas")
@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 50, unique = true)
    private String nombre;
    private boolean activo;

    @ManyToOne
    @JoinColumn(name = "caja_id")
    private CajaPrincipal cajaPrincipal;

    @ManyToMany(mappedBy = "usuarioList")
    private List<Cuenta> cuentas;

    public Usuario() {
        this.cuentas = new ArrayList<>();
    }
    public Usuario(String nombre) {
        this();
        this.nombre = nombre;
    }
}
