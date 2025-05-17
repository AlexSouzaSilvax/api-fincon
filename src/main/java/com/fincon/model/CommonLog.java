package com.fincon.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fincon.enums.CommonLogEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "common_log")
public class CommonLog {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String usuario;

    @Enumerated(EnumType.STRING)
    private CommonLogEnum etapa;

    private String descricao;

    @Column(length = 10000)
    private String jsonEnvio;

    @Column(length = 10000)
    private String jsonRetorno;

    private String ip;

    private String latitude;

    private String longitude;

    private String urlMaps;

    private LocalDateTime dataHora = LocalDateTime.now();

    public CommonLog() {
    }
}
