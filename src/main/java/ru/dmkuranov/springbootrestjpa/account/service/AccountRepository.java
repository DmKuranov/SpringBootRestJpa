package ru.dmkuranov.springbootrestjpa.account.service;

import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, Long> {

}
