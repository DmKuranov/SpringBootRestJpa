package ru.dmkuranov.springbootrestjpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import ru.dmkuranov.springbootrestjpa.account.service.AccountService;

import java.util.concurrent.atomic.AtomicLong;

public class AbstractTest {
    @Autowired
    protected AccountService accountService;

    protected static final AtomicLong accountNoSequence = new AtomicLong(1000L);

}
