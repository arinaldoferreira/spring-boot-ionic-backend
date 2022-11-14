package com.arinaldoferreira.cursojava.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arinaldoferreira.cursojava.domain.Cliente;

public interface ClienteRepository  extends JpaRepository<Cliente, Integer> {

}
