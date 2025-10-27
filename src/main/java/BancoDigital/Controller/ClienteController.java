package BancoDigital.Controller;

import BancoDigital.DTO.ClienteRequest;
import BancoDigital.Model.Cliente;
import BancoDigital.Model.Endereco;
import BancoDigital.Repository.ClienteRepository;
import BancoDigital.Service.ViaCepService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/clientes")
    public class ClienteController {

    private final ClienteRepository clienteRepository;
    private final ViaCepService viaCepService;

    public ClienteController(ClienteRepository clienteRepository, ViaCepService viaCepService) {
        this.clienteRepository = clienteRepository;
        this.viaCepService = viaCepService;
    }

    @GetMapping("/teste")
    public String teste() {
        return "Controller funcionando!";
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente criarCliente(@Valid @RequestBody ClienteRequest clienteRequest) {

        Optional<Cliente> existente = clienteRepository.findByCpfCnpj(clienteRequest.getCpfCnpj());
        if (existente.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CPF/CNPJ já cadastrado");
        }

        Cliente cliente = new Cliente();
        cliente.setNomeCompleto(clienteRequest.getNomeCompleto());
        cliente.setCpfCnpj(clienteRequest.getCpfCnpj());
        cliente.setEmail(clienteRequest.getEmail());
        cliente.setDataNascimento(clienteRequest.getDataNascimento());

        Endereco endereco = viaCepService.buscarEnderecoPorCep(clienteRequest.getEndereco().getCep());
        cliente.setEndereco(endereco);

        return clienteRepository.save(cliente);
    }
}
