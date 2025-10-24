package BancoDigital.Controller;

import BancoDigital.Model.Cliente;
import BancoDigital.Model.Endereco;
import BancoDigital.Repository.ClienteRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.List;

@RestController
@RequestMapping("/clientes")

    public class ClienteController {

    private final ClienteRepository clienteRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    public ClienteController(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    // Criar cliente
    @PostMapping
    public Cliente criarCliente(@RequestBody Cliente cliente) {

        // Busca o CEP na API ViaCEP
        if (cliente.getEndereco() != null && cliente.getEndereco().getCep() != null) {
            String cep = cliente.getEndereco().getCep().replace("-", "");
            String url = "https://viacep.com.br/ws/" + cep + "/json/";

            Endereco enderecoViaCep = restTemplate.getForObject(url, Endereco.class);

            if (enderecoViaCep != null) {
                enderecoViaCep.setCep(cliente.getEndereco().getCep());
                cliente.setEndereco(enderecoViaCep);
            }
        }

        return clienteRepository.save(cliente);
    }

    // Listar clientes
    @GetMapping
    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }
}
