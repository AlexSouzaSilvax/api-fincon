package com.fincon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fincon.Util.RequestUtils;
import com.fincon.component.CommonLogDispatcher;
import com.fincon.dto.UsuarioLocalizacaoDTO;
import com.fincon.model.CommonLog;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class CommonLogService {

    @Autowired
    private UsuarioInfoService usuarioInfoService;

    @Autowired
    private RequestUtils requestUtils;

    @Autowired
    private CommonLogDispatcher commonLogDispatcher;

    public void save(CommonLog commonLog) {

        HttpServletRequest request = requestUtils.getRequestAtual();

        UsuarioLocalizacaoDTO usuarioLocalizacaoDTO = UsuarioInfoService
                .buscarLocalizacao(usuarioInfoService.getIpCliente(request));
        commonLog.setIp(usuarioLocalizacaoDTO.getIp());
        commonLog.setLatitude(usuarioLocalizacaoDTO.getLatitude());
        commonLog.setLongitude(usuarioLocalizacaoDTO.getLongitude());
        commonLog.setUrlMaps(usuarioLocalizacaoDTO.getUrlMaps());

        commonLogDispatcher.dispatch(commonLog);
    }

}