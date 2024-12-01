package app.Pleiade.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "pessoa")
@Table
public class PessoasEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "idade")
    private int idade;

    @Column(name = "documento", unique = true)
    private String documento;

    @Column(name = "senha")
    private String senha;
}