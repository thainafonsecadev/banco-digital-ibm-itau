package BancoDigital.Controller;

import BancoDigital.Model.Conta;
import BancoDigital.Service.ContaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

    @RestController

    @RequestMapping("/contas")

    public class ContaController {

    private final ContaService contaService;

    public ContaController(ContaService contaService) {
        this.contaService = contaService;
    }

    // ABRIR UM NOVA CONTA

    @PostMapping("/abrir/{idCliente}")
    public Conta abrirConta(@PathVariable Long idCliente) {
        return contaService.abrirConta(idCliente);
    }

    // DEPOSITAR
    @PutMapping("/depositar/{idConta}")
    public Conta depositar(@PathVariable Long idConta, @RequestParam Double valor) {
        return contaService.depositar(idConta, valor);
    }

    // SACAR
    @PutMapping("/sacar/{idConta}")
    public Conta sacar(@PathVariable Long idConta, @RequestParam Double valor) {
        return contaService.sacar(idConta, valor);
    }

    // TRANSFERIR
    @PutMapping("/transferir")
    public void transferir(
            @RequestParam Long idContaOrigem,

            @RequestParam Long idContaDestino,

            @RequestParam Double valor) {

        contaService.transferir(idContaOrigem, idContaDestino, valor);
    }

    // CONSULTAR SALDO
    @GetMapping("/saldo/{idConta}")
    public Double consultarSaldo(@PathVariable Long idConta) {
        return contaService.consultarSaldo(idConta);
    }

    // LISTAR CONTATOS
    @GetMapping
    public List<Conta> listarContas() {
        return contaService.listarContas();
    }
}
