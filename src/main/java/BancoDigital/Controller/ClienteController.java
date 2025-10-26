package BancoDigital.Controller;

import BancoDigital.Exception.ErrorResponse;
import BancoDigital.Model.Cliente;
import BancoDigital.Model.Endereco;
import BancoDigital.Repository.ClienteRepository;
import BancoDigital.Service.ViaCepService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public Cliente criarCliente(@RequestBody Cliente cliente) {
        Optional<Cliente> existente = clienteRepository.findByCpfCnpj(cliente.getCpfCnpj());
        if (existente.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CPF/CNPJ já cadastrado");
        }

        if (cliente.getEndereco() == null || cliente.getEndereco().getCep() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CEP obrigatório");
        }

        Endereco enderecoViaCep = viaCepService.buscarEnderecoPorCep(cliente.getEndereco().getCep());
        cliente.setEndereco(enderecoViaCep);

        return clienteRepository.save(cliente);
    }

    
}
