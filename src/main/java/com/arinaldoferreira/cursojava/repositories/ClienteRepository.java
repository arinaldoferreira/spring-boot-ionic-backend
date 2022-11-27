package com.arinaldoferreira.cursojava.repositories;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.arinaldoferreira.cursojava.domain.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
	@Transactional(readOnly = true)
	Cliente findByEmail(String email);
}
