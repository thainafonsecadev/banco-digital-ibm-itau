package BancoDigital.Controller;

import BancoDigital.Model.Conta;
import BancoDigital.Service.ContaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/contas")

    public class ContaController {

    private final ContaService contaService;

    public ContaController(ContaService contaService) {
        this.contaService = contaService;
    }

    @PostMapping("/abrir/{idCliente}")
    public Conta abrirConta(@PathVariable UUID idCliente, @RequestParam String tipoConta) {
        return contaService.abrirConta(idCliente, tipoConta);
    }

    @GetMapping("/{idConta}")
    public Conta consultarConta(@PathVariable UUID idConta) {
        return contaService.consultarConta(idConta);
    }

    @PutMapping("/{idConta}/status")
    public Conta atualizarStatus(@PathVariable UUID idConta, @RequestParam String status) {
        return contaService.atualizarStatus(idConta, status);
    }

    @DeleteMapping("/{idConta}")
    public void removerConta(@PathVariable UUID idConta) {
        contaService.removerConta(idConta);
    }

    @GetMapping
    public List<Conta> listarContas() {
        return contaService.listarContas();
    }
}
