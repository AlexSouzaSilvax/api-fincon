package com.fincon.repository;

import java.util.Date;
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

        @Transactional
        UserDetails findByUsername(String username);

        @Transactional
        @Query(value = "select username from Usuario where email = :pEmail", nativeQuery = true)
        String findUsernameByEmail(@Param("pEmail") String pEmail);

        @Modifying(clearAutomatically = true)
        @Transactional
        @Query(value = "update Usuario set password = :pSenha where email = :pEmail", nativeQuery = true)
        int updateSenhaByEmail(@Param("pSenha") String pSenha, @Param("pEmail") String pEmail);

        @Transactional
        @Query(value = "select u.* from Usuario u left join lancamento l on u.id = l.id_usuario where l.mes_referencia = :pMesReferencia and l.ano_referencia = :pAnoReferencia", nativeQuery = true)
        List<User> findUserLancamentoMesAtual(@Param("pMesReferencia") int pMesReferencia,
                        @Param("pAnoReferencia") int pAnoReferencia);

        @Modifying
        @Transactional
        @Query(value = "update Usuario set nome = :pNome, email = :pEmail, celular = :pCelular, username = :pUsername, password = :pPassword, data_atualizacao = :pDataAtualizacao where id = :pId", nativeQuery = true)
        int userUpdateDTO(@Param("pNome") String nome, @Param("pEmail") String pEmail,
                        @Param("pCelular") String pCelular,
                        @Param("pUsername") String pUsername, @Param("pPassword") String pPassword,
                        @Param("pId") UUID pId, @Param("pDataAtualizacao") Date pDataAtualizacao);

        @Transactional
        @Query(value = "select id from Usuario where LOWER(username) = LOWER(:pUsername)", nativeQuery = true)
        String findIdByUsername(@Param("pUsername") String pUsername);

        @Transactional
        @Query(value = "SELECT EXISTS (SELECT 1 FROM Usuario WHERE email = :pEmail ) AS registro_existe", nativeQuery = true)
        boolean existsUserByEmail(@Param("pEmail") String pEmail);

        @Transactional
        @Query(value = "SELECT EXISTS (SELECT 1 FROM Usuario WHERE LOWER(username) = LOWER(:pUsername) ) AS registro_existe", nativeQuery = true)
        boolean existsUserByUsername(@Param("pUsername") String pUsername);
}