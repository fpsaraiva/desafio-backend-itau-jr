package dev.fpsaraiva.transacao_api.controller;

import dev.fpsaraiva.transacao_api.application.service.EstatisticasService;
import dev.fpsaraiva.transacao_api.controller.dto.EstatisticasResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/estatistica")
public class EstatisticaController {

    @Autowired
    private EstatisticasService estatisticasService;

    private final Logger logger = LoggerFactory.getLogger(EstatisticaController.class);

    @Operation(
            summary = "Calcular estatísticas",
            description = "Calcular estatísticas das transações recebidas."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estatísticas calculadas com sucesso.")
    })
    @GetMapping
    public ResponseEntity<EstatisticasResponseDTO> buscarEstatisticas(
            @RequestParam(value = "intervalorBusca", required = false, defaultValue = "60") Integer intervaloBusca){
        logger.info("Estatísticas calculadas com sucesso.");
        return ResponseEntity.ok(
                estatisticasService.calcularEstatisticasTransacoes(intervaloBusca));
    }
}
