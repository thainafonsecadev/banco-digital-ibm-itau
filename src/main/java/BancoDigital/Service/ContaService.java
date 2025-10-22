package BancoDigital.Service;

import BancoDigital.Model.Cliente;
import BancoDigital.Model.Conta;
import BancoDigital.Repository.ClienteRepository;
import BancoDigital.Repository.ContaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

    @Service
    public class ContaService {

    private final ContaRepository contaRepository;
    private final ClienteRepository clienteRepository;

    public ContaService(ContaRepository contaRepository, ClienteRepository clienteRepository) {
        this.contaRepository = contaRepository;
        this.clienteRepository = clienteRepository;
    }

    //  ABERTURA DE CONTA

    public Conta abrirConta(Long idCliente) {
        Optional<Cliente> clienteOpt = clienteRepository.findById(idCliente);
        if (clienteOpt.isEmpty()) {
            throw new RuntimeException("Cliente não encontrado!");
        }

        Conta conta = new Conta();
        conta.setCliente(clienteOpt.get());
        conta.setSaldo(0.0);
        return contaRepository.save(conta);
    }

    // DEPOSITAR

    public Conta depositar(Long idConta, Double valor) {
        Conta conta = contaRepository.findById(idConta).orElseThrow(() -> new RuntimeException("Conta não encontrada!"));
        conta.setSaldo(conta.getSaldo() + valor);
        return contaRepository.save(conta);
    }

    // SACAR

    public Conta sacar(Long idConta, Double valor) {
        Conta conta = contaRepository.findById(idConta)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada!"));

        if (conta.getSaldo() < valor) {
            throw new RuntimeException("Saldo insuficiente!");
        }

        conta.setSaldo(conta.getSaldo() - valor);
        return contaRepository.save(conta);
    }

    // TRANSFERIR

    public void transferir(Long idContaOrigem, Long idContaDestino, Double valor) {
        Conta origem = contaRepository.findById(idContaOrigem)
                .orElseThrow(() -> new RuntimeException("Conta de origem não encontrada!"));

        Conta destino = contaRepository.findById(idContaDestino)
                .orElseThrow(() -> new RuntimeException("Conta de destino não encontrada!"));

        if (origem.getSaldo() < valor) {
            throw new RuntimeException("Saldo insuficiente para transferência!");
        }

        origem.setSaldo(origem.getSaldo() - valor);
        destino.setSaldo(destino.getSaldo() + valor);

        contaRepository.save(origem);
        contaRepository.save(destino);
    }

    //  CONSULTAR SALDO

    public Double consultarSaldo(Long idConta) {
        Conta conta = contaRepository.findById(idConta)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada!"));
        return conta.getSaldo();
    }

    // LISTAR CONTAS

    public List<Conta> listarContas() {
        return contaRepository.findAll();
    }
}
