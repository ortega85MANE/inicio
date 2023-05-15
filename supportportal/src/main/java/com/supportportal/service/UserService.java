package com.supportportal.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.supportportal.domain.Usuario;

public interface UserService {

    //User register(String firstName, String lastName, String username, String email) throws UserNotFoundException, UsernameExistException, EmailExistException;
	
	List<Usuario> getUsuarios(); 
	
	Page<Usuario> findAll(Pageable pageable); 
    
    Usuario findByLogin(String login);   
    
    List<Usuario> findByNombre(String nombre);
    
    List<Usuario> findByStatus(String status);
    
    Optional<Usuario> buscarByLogin(String id);
    
    void remove(String id);
    
    Usuario save(Usuario usuario);
    
    Optional<Usuario> update(Usuario user, String id);
    
}
