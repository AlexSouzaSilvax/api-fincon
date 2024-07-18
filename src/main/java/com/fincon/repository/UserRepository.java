package com.fincon.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fincon.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    UserDetails findByUsername(String username);

    @Query(value = "select username from usuario where email = :pEmail", nativeQuery = true)
    String findUsernameByEmail(@Param("pEmail") String pEmail);

    @Transactional
    @Modifying
    @Query(value = "update usuario set password = :pSenha where email = :pEmail", nativeQuery = true)
    void updateSenhaByEmail(@Param("pEmail") String pEmail, @Param("pSenha") String pSenha);

    @Query(value = "select u.* from usuario u left join lancamento l on u.id = l.id_usuario where l.mes_referencia = :pMesReferencia and l.ano_referencia = :pAnoReferencia", nativeQuery = true)
    List<User> findUserLancamentoMesAtual(@Param("pMesReferencia") int pMesReferencia,
            @Param("pAnoReferencia") int pAnoReferencia);

}