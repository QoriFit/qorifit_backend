package com.cibertec.backend.qorifit.utils.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "supabase.storage.bucket")
public class StorageProperties {

    private String url;
    private String prefix;
    private String name;
    private String apiKey;
}