package ru.dmkuranov.springbootrestjpa.account.service;

import org.jetbrains.annotations.NotNull;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.stereotype.Component;
import ru.dmkuranov.springbootrestjpa.account.AccountController;
import ru.dmkuranov.springbootrestjpa.account.dto.AccountDto;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@Component
public class AccountMapper implements ResourceAssembler<AccountDto, Resource<AccountDto>> {
    @NotNull
    public AccountDto toDto(@NotNull Account account) {
        return AccountDto.builder().number(account.getNumber()).amount(account.getAmount()).build();
    }

    @Override
    public Resource<AccountDto> toResource(AccountDto accountDto) {
        Resource<AccountDto> resource = new Resource<>(accountDto,
                ControllerLinkBuilder.linkTo(methodOn(AccountController.class).get(accountDto.getNumber())).withSelfRel());
        resource.add(ControllerLinkBuilder.linkTo(methodOn(AccountController.class).deposit(accountDto.getNumber(), null)).withRel("deposit"));
        resource.add(ControllerLinkBuilder.linkTo(methodOn(AccountController.class).withdraw(accountDto.getNumber(), null)).withRel("withdraw"));
        resource.add(ControllerLinkBuilder.linkTo(methodOn(AccountController.class).transfer(accountDto.getNumber(), null)).withRel("transfer"));
        return resource;
    }
}
