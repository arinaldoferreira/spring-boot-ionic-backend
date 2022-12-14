package com.arinaldoferreira.cursojava.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.arinaldoferreira.cursojava.domain.Cidade;
import com.arinaldoferreira.cursojava.domain.Cliente;
import com.arinaldoferreira.cursojava.domain.Endereco;
import com.arinaldoferreira.cursojava.domain.enums.TipoCliente;
import com.arinaldoferreira.cursojava.dto.ClienteDTO;
import com.arinaldoferreira.cursojava.dto.ClienteNewDTO;
import com.arinaldoferreira.cursojava.repositories.ClienteRepository;
import com.arinaldoferreira.cursojava.repositories.EderecoRepository;
import com.arinaldoferreira.cursojava.services.exceptions.DataIntegrityException;
import com.arinaldoferreira.cursojava.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private PasswordEncoder pe;
	
	@Autowired
	private ClienteRepository repo;
	
	@Autowired
	private EderecoRepository enderecoRepository;

	public Cliente find(Integer id) {
		Optional<Cliente> obj = repo.findById(id);

		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}

	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = repo.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj;
	}

	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}

	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}

	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir um cliente que possui pedidos");
		}
	}

	public List<Cliente> findAll() {
		return repo.findAll();
	}

	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);

		return repo.findAll(pageRequest);
	}

	public Cliente fromDto(ClienteDTO objDto) {
		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null, null);
	}

	public Cliente fromDto(ClienteNewDTO objDto) {
		Cliente cli = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(),
				TipoCliente.toEnum(objDto.getTipo()), pe.encode(objDto.getSenha()));

		Cidade cid = new Cidade(objDto.getCidadeId(), null, null);

		Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(),
				objDto.getBairro(), objDto.getCep(), cli, cid);

		cli.getEnderecos().add(end);

		cli.getTelefones().add(objDto.getTelefone1());

		if (objDto.getTelefone2() != null) {
			cli.getTelefones().add(objDto.getTelefone2());
		}

		if (objDto.getTelefone3() != null) {
			cli.getTelefones().add(objDto.getTelefone3());
		}

		return cli;
	}
}
