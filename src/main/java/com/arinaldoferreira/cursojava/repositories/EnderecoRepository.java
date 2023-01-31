package com.arinaldoferreira.cursojava.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arinaldoferreira.cursojava.domain.Endereco;

public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {

}
