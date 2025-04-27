package com.fincon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UsuarioLocalizacaoDTO {

    private String ip;
    private String latitude;
    private String longitude;
    private String urlMaps;

}
