package com.cibertec.backend.qorifit.infraestructure.storage;

import com.cibertec.backend.qorifit.application.port.StoragePort;
import com.cibertec.backend.qorifit.utils.properties.StorageProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class SupabaseStorageAdapter implements StoragePort {

    private final WebClient webClient;
    private final StorageProperties storageProperties;

    @Override
    public String getPublicUrl(String keyPath) {
        if (keyPath == null || keyPath.isBlank()) return null;

        return String.format("%s/storage/v1/object/public/%s/%s/%s",
                storageProperties.getUrl(),
                storageProperties.getName(),
                storageProperties.getPrefix(),
                keyPath);
    }

    @Override
    public void uploadFile(String fileName, byte[] data, String contentType) {
        String keyPath = UUID.randomUUID() + "_" + sanitizeFileName(fileName);

        String uploadUrl = String.format("%s/storage/v1/object/%s/%s/%s",
                storageProperties.getUrl(),
                storageProperties.getName(),
                storageProperties.getPrefix(),
                keyPath);

        log.info("Iniciando subida de archivo a Supabase: {}", uploadUrl);

        webClient.post()
                .uri(uploadUrl)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + storageProperties)
                .contentType(MediaType.parseMediaType(contentType))
                .bodyValue(data)
                .retrieve()
                .onStatus(HttpStatusCode::isError, response ->
                        response.bodyToMono(String.class)
                                .flatMap(error -> {
                                    log.error("Error de Supabase: {}", error);
                                    return Mono.error(new RuntimeException("Error en Supabase Storage: " + error));
                                })
                )
                .bodyToMono(String.class)
                .block();

        log.info("Archivo subido con éxito. Path generado: {}", keyPath);
        log.info("URL de acceso: {}", this.getPublicUrl(keyPath));
    }

    private String sanitizeFileName(String fileName) {
        return fileName.replaceAll("[^a-zA-Z0-0.]", "_");
    }
}