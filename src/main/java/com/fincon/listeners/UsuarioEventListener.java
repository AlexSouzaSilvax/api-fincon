package com.fincon.listeners;

import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.fincon.model.User;
import com.fincon.service.EmailBemVindoService;

import jakarta.persistence.PostPersist;

@Component
public class UsuarioEventListener {

    @PostPersist
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onPostPersist(User pUsuario) {
        performActionAfterInsert(pUsuario);
    }

    private void performActionAfterInsert(User pUsuario) {
        new EmailBemVindoService().enviar(pUsuario.getEmail(), pUsuario.getNome());
    }
}