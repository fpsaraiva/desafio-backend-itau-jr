package dev.fpsaraiva.transacao_api.application.service;

import dev.fpsaraiva.transacao_api.controller.dto.EstatisticasResponseDTO;
import dev.fpsaraiva.transacao_api.controller.dto.TransacaoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EstatisticasServiceTests {

    @Mock
    private TransacaoService transacaoService;

    @InjectMocks
    private EstatisticasService estatisticasService;

    private List<TransacaoDTO> transacoesMockadas;

    @BeforeEach
    void setUp() {
        transacoesMockadas = Arrays.asList(
                new TransacaoDTO(100.0, OffsetDateTime.now().minusMinutes(1)),
                new TransacaoDTO(50.0, OffsetDateTime.now().minusMinutes(2)),
                new TransacaoDTO(200.0, OffsetDateTime.now().minusMinutes(3))
        );
    }

    @Test
    void deveCalcularEstatisticasTransacoesERetornarValoresCorretos() {
        when(transacaoService.buscarTransacoes(60)).thenReturn(transacoesMockadas);

        EstatisticasResponseDTO resultado = estatisticasService.calcularEstatisticasTransacoes(60);

        assertEquals(3, resultado.count());
        assertEquals(350.0, resultado.sum());
        assertEquals(116.66666, resultado.avg(), 0.0001); // Permite pequena variação no double
        assertEquals(50.0, resultado.min());
        assertEquals(200.0, resultado.max());

        verify(transacaoService, times(1)).buscarTransacoes(60);
    }

    @Test
    void deveCalcularEstatisticasTransacoesERetornarValoresZeradosQuandoNaoHaTransacoes() {
        when(transacaoService.buscarTransacoes(60)).thenReturn(Collections.emptyList());

        EstatisticasResponseDTO resultado = estatisticasService.calcularEstatisticasTransacoes(60);

        assertEquals(0, resultado.count());
        assertEquals(0.0, resultado.sum());
        assertEquals(0.0, resultado.avg());
        assertEquals(0.0, resultado.min());
        assertEquals(0.0, resultado.max());

        verify(transacaoService, times(1)).buscarTransacoes(60);
    }
}
