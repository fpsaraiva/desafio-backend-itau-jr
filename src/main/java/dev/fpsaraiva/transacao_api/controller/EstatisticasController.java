package dev.fpsaraiva.transacao_api.controller;

import dev.fpsaraiva.transacao_api.application.service.EstatisticasService;
import dev.fpsaraiva.transacao_api.controller.dto.EstatisticasResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/estatistica")
public class EstatisticasController {

    @Autowired
    private EstatisticasService estatisticasService;

    public ResponseEntity<EstatisticasResponseDTO> buscarEstatisticas(
            @RequestParam(value = "intervalorBusca", required = false, defaultValue = "60") Integer intervaloBusca){
        return ResponseEntity.ok(
                estatisticasService.calcularEstatisticasTransacoes(intervaloBusca));
    }
}
