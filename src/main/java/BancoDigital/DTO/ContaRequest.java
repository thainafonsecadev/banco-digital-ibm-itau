package BancoDigital.DTO;

import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Getter
@Setter
    public class ContaRequest {
    private UUID clienteId;
    private String tipoConta;

}

