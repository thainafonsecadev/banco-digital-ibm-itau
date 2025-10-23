package BancoDigital.Repository;

import BancoDigital.Model.Conta;
import BancoDigital.Model.StatusConta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

    public interface ContaRepository extends JpaRepository<Conta, UUID> {

    boolean existsByClienteIdClienteAndStatus(UUID idCliente, StatusConta status);
}






