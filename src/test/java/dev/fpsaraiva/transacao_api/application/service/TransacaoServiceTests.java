package dev.fpsaraiva.transacao_api.application.service;

import dev.fpsaraiva.transacao_api.controller.dto.TransacaoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class TransacaoServiceTests {

    @InjectMocks
    private TransacaoService transacaoService;

    private List<TransacaoDTO> transacoesMockadas;

    private List<TransacaoDTO> listaTransacoes;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        listaTransacoes = new ArrayList<>();
        listaTransacoes.add(new TransacaoDTO(100.0, OffsetDateTime.now().minusSeconds(30))); // Dentro do intervalo
        listaTransacoes.add(new TransacaoDTO(200.0, OffsetDateTime.now().minusSeconds(90))); // Fora do intervalo
        listaTransacoes.add(new TransacaoDTO(300.0, OffsetDateTime.now().minusSeconds(59))); // Exatamente no limite

        // Injetando a lista no campo privado via Reflection
        Field field = TransacaoService.class.getDeclaredField("listaTransacoes");
        field.setAccessible(true);
        field.set(transacaoService, listaTransacoes);
    }

    @Test
    void deveRetornarTransacoesDentroDoIntervalo() {
        List<TransacaoDTO> resultado = transacaoService.buscarTransacoes(60);
        assertEquals(2, resultado.size());
        assertTrue(resultado.stream().allMatch(t -> t.dataHora().isAfter(OffsetDateTime.now().minusSeconds(60))));
    }

    @Test
    void deveRetornarListaVaziaQuandoTodasEstaoForaDoIntervalo() {
        List<TransacaoDTO> resultado = transacaoService.buscarTransacoes(10);
        assertTrue(resultado.isEmpty());
    }

    @Test
    void deveRetornarListaVaziaQuandoIntervaloEhZero() {
        List<TransacaoDTO> resultado = transacaoService.buscarTransacoes(0);
        assertTrue(resultado.isEmpty());
    }
}
