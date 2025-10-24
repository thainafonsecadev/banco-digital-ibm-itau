package BancoDigital.Service;

import BancoDigital.Model.*;
import BancoDigital.Repository.ClienteRepository;
import BancoDigital.Repository.ContaRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class ContaService {

    private final ContaRepository contaRepository;
    private final ClienteRepository clienteRepository;

    public ContaService(ContaRepository contaRepository, ClienteRepository clienteRepository) {
        this.contaRepository = contaRepository;
        this.clienteRepository = clienteRepository;
    }

    public Conta abrirConta(UUID idCliente, String tipoConta) {
        Cliente cliente = (Cliente) clienteRepository.findById(idCliente)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado!"));

        if (!tipoConta.equalsIgnoreCase("PF") && !tipoConta.equalsIgnoreCase("PJ")) {
            throw new RuntimeException("Tipo de conta inválido. Use PF ou PJ.");
        }

        Conta conta = new Conta();
        conta.setCliente(cliente);
        conta.setTipoConta(TipoConta.valueOf(tipoConta.toUpperCase()));
        conta.setStatus(StatusConta.TEMPORARIO);
        conta.setSaldo(0.0);
        conta.setDataCriacao(Instant.now());

        return contaRepository.save(conta);
    }

    public Conta consultarConta(UUID idConta) {
        return contaRepository.findById(idConta)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada!"));
    }

    public Conta atualizarStatus(UUID idConta, String novoStatus) {
        Conta conta = contaRepository.findById(idConta)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada!"));

        if (!novoStatus.equalsIgnoreCase("APROVADA") && !novoStatus.equalsIgnoreCase("REPROVADA")) {
            throw new RuntimeException("Status inválido. Use APROVADA ou REPROVADA.");
        }

        if (!conta.getStatus().equals(StatusConta.TEMPORARIO)) {
            throw new RuntimeException("Apenas contas TEMPORARIO podem ser atualizadas.");
        }

        conta.setStatus(StatusConta.valueOf(novoStatus.toUpperCase()));
        conta.setDataAtualizacao(Instant.now());

        return contaRepository.save(conta);
    }

    public void removerConta(UUID idConta) {
        Conta conta = contaRepository.findById(idConta)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada!"));

        if (!conta.getStatus().equals(StatusConta.TEMPORARIO)) {
            throw new RuntimeException("Apenas contas TEMPORARIO podem ser removidas.");
        }

        contaRepository.delete(conta);
    }

    public List<Conta> listarContas() {
        return contaRepository.findAll();
    }
}
