package com.fincon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.fincon.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query(value = "select * from usuario u where u.login = :pLogin and u.senha = :pSenha", nativeQuery = true)
    Usuario access(@Param("pLogin") String login, @Param("pSenha") String senha);
}