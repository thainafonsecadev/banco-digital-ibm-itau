package BancoDigital.Model;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

    public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String NumeroConta;
    private Double saldo = 0.0;
    private Instant DataCriacao = Instant.now();

    @ManyToOne
    @JoinColumn(name = "Cliente_Id")
    private Cliente Cliente;
}
