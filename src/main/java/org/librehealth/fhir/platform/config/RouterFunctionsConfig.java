package org.librehealth.fhir.platform.config;

import lombok.RequiredArgsConstructor;
import org.librehealth.fhir.platform.handler.PatientHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@EnableWebFlux
@Configuration
@RequiredArgsConstructor
public class RouterFunctionsConfig {

  @Bean
  RouterFunction<ServerResponse> patientRoute(PatientHandler patientHandler) {
    return RouterFunctions
            .route(GET("/Patient"), patientHandler::search)
            .andRoute(GET("/Patient/{id}"), patientHandler::getById)
            .andRoute(POST("/Patient").and(accept(MediaType.APPLICATION_JSON)), patientHandler::save)
            .andRoute(PUT("/Patient/{id}").and(accept(MediaType.APPLICATION_JSON)), patientHandler::update)
            .andRoute(DELETE("/Patient/{id}"), patientHandler::delete);
  }

}
