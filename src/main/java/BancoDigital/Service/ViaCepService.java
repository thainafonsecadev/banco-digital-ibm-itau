package BancoDigital.Service;

import BancoDigital.Model.Endereco;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ViaCepService {

    public Endereco buscarEnderecoPorCep(String cep) {
        try {
            String url = "https://viacep.com.br/ws/" + cep + "/json/";
            RestTemplate restTemplate = new RestTemplate();
            Endereco endereco = restTemplate.getForObject(url, Endereco.class);

            if (endereco == null || endereco.getCep() == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "CEP inválido");
            }

            return endereco;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Erro ao consultar CEP");
        }
    }
}
