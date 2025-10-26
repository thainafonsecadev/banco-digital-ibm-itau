package BancoDigital.Model;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
    public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idConta;

    @Enumerated(EnumType.STRING)
    private TipoConta tipoConta;

    @Enumerated(EnumType.STRING)
    private StatusConta status = StatusConta.TEMPORARIO;

    private Double saldo = 0.0;

    private Instant dataCriacao = Instant.now();

    private Instant dataAtualizacao;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    public UUID getClienteId() {
        return cliente != null ? cliente.getId() : null;
    }
}

