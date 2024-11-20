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
@ToString(exclude = {"tiques", "usuarioList"})
@EqualsAndHashCode(exclude = {"tiques", "usuarioList"})
@Entity
@Table(name = "cuentas")
public class Cuenta implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 50, unique = true)
    private String nombre;
    private boolean activo;


    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "cuenta_usuario",
            joinColumns = @JoinColumn(name = "cuenta_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    )
    private List<Usuario> usuarioList;

    @ManyToMany(targetEntity = Tique.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private List<Tique> tiques;


    public Cuenta() {
        tiques = new ArrayList<>();
        usuarioList = new ArrayList<>();
    }


}
