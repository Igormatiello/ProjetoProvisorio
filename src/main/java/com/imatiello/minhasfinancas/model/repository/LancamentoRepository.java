package com.imatiello.minhasfinancas.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.imatiello.minhasfinancas.model.entity.Usuario;

public interface LancamentoRepository extends JpaRepository< Usuario, Long>{

}
