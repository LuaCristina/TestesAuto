package com.iftm.client.repositories;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.iftm.client.entities.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
	/*
	 * Métodos propostos nas atividades de JUNIT
	 */

	void deleteByCpf(String cpfExistente);

	Optional<Client> findByCpf(String string);
//	Optional<Client> findByName(String string);

	void deleteByIncomeGreaterThan(double salarioI);

	List<Client> findByIncomeGreaterThan(double salarioI);
	List<Client> findByIncomeLessThan(double salarioI);

	List<Client> findByIncomeBetween(double salarioI, double salarioF);

	List<Client> findByCpfLike(String parteCpf);

	@Query("SELECT c FROM Client c WHERE LOWER(c.name) = LOWER(:name)")
	Client findByName(@Param("name") String name);
	@Query("SELECT c FROM Client c WHERE LOWER(c.name) LIKE CONCAT('%', LOWER(:parteName), '%')")
	List<Client> findByNameLike(@Param("parteName") String parteName);

	List<Client> findClientByBirthDateBetween(Instant DataInicio, Instant DataTermino);
	List<Client> findByCpfStartingWith(String parteCpf);
	
	/*
	 * Métodos propostos para trabalhar com novas funcionalidades na atividade de Mockito.
	 * Elas também deveriam ser testadas no ClientRepositorTest
	 * É possível converter uma list em uma Page, mas é bem complexo.
	 */
	Page<Client> findByIncome(Double income, Pageable pageable);
	Page<Client> findByIncomeGreaterThan(double salarioI, Pageable pageable);
	Page<Client> findByCpfLike(String parteCpf, Pageable pageable);
	Page<Client> findByCpfStartingWith(String parteCpf, Pageable pageable);

	/*
	* TESTE EM JPA REPOSITORY
	*
	* */


//	@Query("SELECT c FROM Client c WHERE LOWER(c.nome) = LOWER(:nome)")
//	List<Client> findByNomeIgnoreCase(String nome);

}
