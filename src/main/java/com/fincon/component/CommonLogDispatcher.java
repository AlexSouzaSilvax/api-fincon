package com.fincon.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.fincon.model.CommonLog;
import com.fincon.repository.CommonLogRepository;

@Component
public class CommonLogDispatcher {

    @Autowired
    private CommonLogRepository commonLogRepository;

    @Async
    public void dispatch(CommonLog commonLog) {
        try {
            commonLogRepository.save(commonLog);
            System.out.println(formatarLogParaConsole(commonLog));
        } catch (Exception e) {
            System.err.println("Erro ao salvar log: " + e.getMessage());
        }
    }

    private String formatarLogParaConsole(CommonLog commonLog) {
        return "[" + commonLog.getDataHora() + "] [" + commonLog.getEtapa() + "] [" + "] - " + commonLog.getDescricao();
    }
}