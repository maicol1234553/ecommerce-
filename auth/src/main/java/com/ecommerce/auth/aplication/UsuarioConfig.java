package com.ecommerce.auth.aplication;

import com.ecommerce.auth.domain.model.gateway.UsuarioGateway;
import com.ecommerce.auth.domain.usecase.UsuarioUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.ecommerce.auth.domain.model.gateway.EncrypterGateway;
@Configuration
public class UsuarioConfig {
    @Bean
    public UsuarioUseCase usuarioUseCase(UsuarioGateway usuarioGateway,  EncrypterGateway encrypterGateway) {
        return new UsuarioUseCase(usuarioGateway,encrypterGateway);
    }
}
