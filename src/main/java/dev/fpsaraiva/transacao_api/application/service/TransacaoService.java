package dev.fpsaraiva.transacao_api.application.service;

import dev.fpsaraiva.transacao_api.controller.dto.TransacaoDTO;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransacaoService {

    public final List<TransacaoDTO> listaTransacoes = new ArrayList<>();

    public void receberTransacao(TransacaoDTO transacaoDTO) {
        listaTransacoes.add(transacaoDTO);
    }

    public void limparTransacoes() {
        listaTransacoes.clear();
    }

    public List<TransacaoDTO> buscarTransacoes(Integer intervaloBusca){
        OffsetDateTime dataHoraIntervalo = OffsetDateTime.now().minusSeconds(intervaloBusca);

        return listaTransacoes.stream()
                .filter(transacao -> transacao.dataHora()
                        .isAfter(dataHoraIntervalo)).toList();
    }
}
