//package com.cibertec.backend.qorifit.application.service;
//
//import com.cibertec.backend.qorifit.infraestructure.web.exception.BusinessException;
//import com.cibertec.backend.qorifit.utils.InternalCodes;
//import jakarta.annotation.PostConstruct;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.stereotype.Component;
//
//import java.security.Permission;
//import java.util.*;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
//
//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class BitMaskInterpreter {
//
//    public static final Map<String, Integer> ACTION_TO_BIT = Map.of(
//            "READ", 1,
//            "CREATE", 2,
//            "EDIT", 4,
//            "DELETE", 8
//    );
//
//    private final PermissionTypeJpaPort permissionTypeJpaPort;
//
//    private volatile Set<Permission> activePermissions;
//
//    @PostConstruct
//    public void init() {
//        this.activePermissions = this.permissionTypeJpaPort.findAllActive()
//                .stream()
//                .map(item -> new Permission(item.getBitValue(), item.getCode()))
//                .collect(Collectors.toSet());
//
//        log.info("Caché de tipos de permisos inicializada con {} registros.", activePermissions.size());
//    }
//
//    public List<SimpleGrantedAuthority> mapToAuthorities(String[] authoritiesFromToken) {
//        if (authoritiesFromToken == null || authoritiesFromToken.length == 0) {
//            return Collections.emptyList();
//        }
//
//        return Arrays.stream(authoritiesFromToken)
//                .map(claim -> claim.split(":"))
//                .filter(parts -> parts.length == 2)
//                .flatMap(parts -> {
//                    String section = parts[0];
//                    try {
//                        int mask = Integer.parseInt(parts[1]);
//
//                        return activePermissions.stream()
//                                .filter(p -> isBitSet(mask, p.getBitValue()))
//                                .map(p -> new SimpleGrantedAuthority(
//                                        section + ":" + p.getPermissionCode().toUpperCase()
////                                                .replace(" ", "_")
//                                ));
//                    } catch (NumberFormatException e) {
//                        log.error("Máscara de bits inválida en el token: {}", parts[1]);
//                        return Stream.empty();
//                    }
//                })
//                .toList();
//    }
//
//
//    private boolean isBitSet(int mask, Long bitValue) {
//        return (mask & bitValue.intValue()) != 0;
//    }
//
//    public void validateDeactivation(int currentMask, int maskToDisable) {
//        if ((currentMask & maskToDisable) != maskToDisable) {
//            throw new BusinessException(InternalCodes.BUSINESS_RULE_VIOLATION,
//                    "Intento de desactivar permisos no asignados (Máscara actual: " + currentMask +
//                            ", Solicitada: " + maskToDisable + ")");
//        }
//    }
//
//}