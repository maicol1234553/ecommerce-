package com.ecommerce.catalogo.infrastructure.rest_client.auth;

import com.ecommerce.catalogo.domain.model.gateway.AuthGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class AuthRestAdapter implements AuthGateway {

    private final RestTemplate restTemplate;

    @Value("${auth.service.url:http://localhost:8081/api/ecommerce/usuario}")
    private String authServiceUrl;

    @Override
    public BigDecimal consultarSaldo(String usuarioId) {
        String url = authServiceUrl + "/id/" + usuarioId;
        ResponseEntity<UsuarioResponse> response = restTemplate.getForEntity(url, UsuarioResponse.class);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return response.getBody().getSaldo();
        }
        throw new RuntimeException("Error al consultar el usuario en Auth.");
    }

    @Override
    public void solicitarDescuentoSaldo(String usuarioId, BigDecimal montoTotal) {
        String url = authServiceUrl + "/" + usuarioId + "/descontar";

        Map<String, BigDecimal> requestBody = Map.of("monto", montoTotal);

        ResponseEntity<Void> response = restTemplate.postForEntity(url, requestBody, Void.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Fondos insuficientes o error en el descuento de saldo.");
        }
    }

    @Override
    public boolean existeUsuario(String usuarioId) {
        try {
            String url = authServiceUrl + "/id/" + usuarioId;
            ResponseEntity<UsuarioResponse> response = restTemplate.getForEntity(url, UsuarioResponse.class);
            return response.getStatusCode().is2xxSuccessful() && response.getBody() != null;
        } catch (Exception e) {
            return false;
        }
    }
}
