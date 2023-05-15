package com.supportportal.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.supportportal.domain.Usuario;

public interface UserRepository extends JpaRepository<Usuario, Long> {

	
	@Query("select u from Usuario u where u.login=?1")
    Usuario findByLogin(String login);
	
	@Query("select u from Usuario u where u.nombre like %?1%")        
	List<Usuario> findByNombre(String nombre);
	
	@Query("select u from Usuario u where u.status like %?1%")        
	List<Usuario> findByStatus(String status);
	
	@Query("select u from Usuario u where u.login=?1")
    Optional<Usuario> buscarByLogin(String login);
	
	@Modifying
	@Query("delete from Usuario u where u.login=?1")
	void remove(String login);
	
	Iterable<Usuario> findAllByOrderByNombreAsc();
	
	Page<Usuario> findAllByOrderByNombreAsc(Pageable pageable);

    
}
