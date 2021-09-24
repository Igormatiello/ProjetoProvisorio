package com.imatiello.minhasfinancas.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imatiello.minhasfinancas.exception.RegraNegocioException;
import com.imatiello.minhasfinancas.model.entity.Lancamento;
import com.imatiello.minhasfinancas.model.entity.Usuario;
import com.imatiello.minhasfinancas.model.enums.StatusLancamento;
import com.imatiello.minhasfinancas.model.enums.TipoLancamento;
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
		
		validar(lancamento);
		lancamento.setStatus(StatusLancamento.PENDENTE);
		return repository.save(lancamento);
	}
	
	
	
	

	@Override
	@Transactional
	public Lancamento atualizar(Lancamento lancamento) {
	
		Objects.requireNonNull(lancamento.getId());
		
		lancamento.setStatus(StatusLancamento.PENDENTE);
		validar(lancamento);
		return repository.save(lancamento);
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


		if (lancamento.getDescricao()== null || lancamento.getDescricao().trim().equals("")) {
			throw new RegraNegocioException("Informe uma Descrição válida.");
		}
		
		if (lancamento.getMes()==null || lancamento.getMes()<1 ||lancamento.getMes()>12)
		{
			throw new RegraNegocioException("Informe um Mês válido.");
		}
		if (lancamento.getAno()==null || lancamento.getAno().toString().length()!= 4) {
			
			throw new RegraNegocioException("Informe um Ano válido.");
		}
		if (lancamento.getUsuario() == null || lancamento.getId()==null) {
			throw new RegraNegocioException("Informe um Usuáio.");
		}
		if (lancamento.getValor()==null || lancamento.getValor().compareTo(BigDecimal.ZERO) < 1) {
			throw new RegraNegocioException("Informe um Valor Válido.");
		}
		if (lancamento.getTipo() == null) {
			throw new RegraNegocioException("Informe um Tipo de Lançamento.");
		}
	}



	@Override
	public Optional<Lancamento> obterPorId(Long id) {
		return Optional.empty(); //TODO mudança no original
	}



	@Override
	@Transactional(readOnly = true)
	public BigDecimal obterSaldoPorUsuario(Long id) {
	
		BigDecimal receitas = repository.obterSaldoPorTipoLancamentoEUsuario
				(id, TipoLancamento.RECEITA);
		
		BigDecimal despesas = repository.obterSaldoPorTipoLancamentoEUsuario
				(id, TipoLancamento.DESPESA);
		
		if (receitas==null) {
			receitas=BigDecimal.ZERO;
		}
		if (despesas==null) {
			
			despesas=BigDecimal.ZERO;
		}
		
		return receitas.subtract(despesas);
	}
	

}
