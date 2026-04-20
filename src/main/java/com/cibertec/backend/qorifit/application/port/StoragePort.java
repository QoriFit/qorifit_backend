package com.cibertec.backend.qorifit.application.port;

public interface StoragePort {

    String getPublicUrl(String keyPath);
    void uploadFile(String fileName, byte[] data, String contentType);
}
