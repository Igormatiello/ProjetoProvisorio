
package com.imatiello.minhasfinancas.model.repository;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.imatiello.minhasfinancas.model.entity.Usuario;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@DataJpaTest //desfaz o teste após finalizar
@AutoConfigureTestDatabase(replace = Replace.NONE)  //não sobreescrever configurações
public class UsuarioRepositoryTest {

	@Autowired
	UsuarioRepository repository;
	
	@Autowired
	TestEntityManager entityManager; //operações dentro do banco de dados
	
	
	@Test
	public void deveverificarAExistenciaDeUmEmail() {
		//cenario
		Usuario usuario = criarUsuario();
		entityManager.persist(usuario);
		
		//ação/execução
		boolean result = repository.existsByEmail("usuario@email.com");
		
		
		
		//verificação
		
		Assertions.assertThat(result).isTrue();
		
	}
	
	@Test
	public void deveRetornarFalsoQuandoNaoHouverUsuarioCadastradoComOEmail() {
		
		//cenario
		
		
		//ação-execução
		
		boolean result = repository.existsByEmail("usuario@email.com");
		
		
		//verificação
		
		Assertions.assertThat(result).isFalse();
		
		
	}
	
	@Test
	
	public void devePersistirUmUsuarioNaBaseDeDados() {
		//cenario
		Usuario usuario = criarUsuario();
				
				
				
		//ação
		
		Usuario usuarioSalvo = repository.save(usuario);
	
		//verificação
		Assertions.assertThat(usuarioSalvo.getId()).isNotNull();
	}
	
	
	@Test
	
	public void deveBuscarUmUsuarioPorEmail() {
		
		//cenario
		Usuario usuario = criarUsuario();
		entityManager.persist(usuario);
		
		//verificação
		Optional<Usuario> result= repository.findByEmail("usuario@email.com");
		
		Assertions.assertThat(result.isPresent()).isTrue();
		
	}
	
	
@Test
	
	public void deveRetornarVazioAoBuscarUsuarioPorEmailQuandoNaoExisteNaBase() {
		
		//cenario
		

		//verificação
		Optional<Usuario> result= repository.findByEmail("usuario@email.com");
		
		Assertions.assertThat(result.isPresent()).isFalse();
		
	}



		public static Usuario criarUsuario() {
			return Usuario 
					.builder()
					.nome("usuario")
					.email("usuario@email.com")
					.senha("senha")
					.build();
			
			
		}
	
	
	
}
