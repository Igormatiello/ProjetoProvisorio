package com.imatiello.minhasfinancas.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.imatiello.minhasfinancas.exception.RegraNegocioException;
import com.imatiello.minhasfinancas.model.entity.Usuario;
import com.imatiello.minhasfinancas.model.repository.UsuarioRepository;

import net.bytebuddy.dynamic.scaffold.TypeInitializer.None;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class UsuarioServiceTeste {

	
	@Autowired
	UsuarioService service;
	
	@Autowired
	UsuarioRepository repository;
	@Test(expected=Test.None.class)
	public void deveValidarEmail() {
		//cenario
		repository.deleteAll();
		
		
		//ação
		service.validarEmail("email@email.com");
		
	}
	
	@Test(expected = RegraNegocioException.class)
	public void deveLancarErroAoValidarEmailQuandoExistirEmailCadastrado() {
		//cenario
		Usuario usuario = Usuario.builder().nome("usuario").email("email@email.com").build();
		repository.save(usuario);
		
		//ação
		service.validarEmail("email@email.com");
		
		
	}
}
