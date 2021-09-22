package com.imatiello.minhasfinancas.service.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imatiello.minhasfinancas.model.entity.Lancamento;
import com.imatiello.minhasfinancas.model.entity.Usuario;
import com.imatiello.minhasfinancas.model.enums.StatusLancamento;
import com.imatiello.minhasfinancas.model.repository.LancamentoRepository;
import com.imatiello.minhasfinancas.service.LancamentoService;


@Service
public class LancamentoServiceImpl implements LancamentoService{

	private LancamentoRepository repository;
	
	
	public LancamentoServiceImpl(LancamentoRepository repository) {
		
		this.repository= repository;
		
	}
	
	
	
	@Override
	@Transactional
	public Lancamento salvar(Lancamento lancamento) {
		
		
		
		return repository.save(repository);
	}
	
	
	
	

	@Override
	@Transactional
	public Lancamento atualizar(Lancamento lancamento) {
	
		Objects.requireNonNull(lancamento.getId());
		
		return repository.save(repository);
	}

	
	
	
	@Override
	@Transactional
	public void deletar(Lancamento lancamento) {
		
		Objects.requireNonNull(lancamento.getId());
		
		repository.delete((Usuario) repository);
		
	}

	
	
	@Override
	@Transactional(readOnly = true)
	public List<Lancamento> buscar(Lancamento lancamentoFiltro) {
		
		Example example = Example.of(lancamentoFiltro, 
				ExampleMatcher.matching()
				.withIgnoreCase()
				.withStringMatcher(StringMatcher.CONTAINING));
		
			
		
		return repository.findAll(example);
	}

	
	
	
	

	@Override
	public void atulizarStatus(Lancamento lancamento, StatusLancamento status) {
		
		
		lancamento.setStatus(status);
		
		atualizar(lancamento);
	}



	@Override
	public void validar(Lancamento lancamento) {


		//if (lancamento.getDescricao()== null || lancamento.getDescricao().trim());
		
	}

}
