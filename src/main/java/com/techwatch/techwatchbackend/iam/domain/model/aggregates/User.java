package com.techwatch.techwatchbackend.iam.domain.model.aggregates;

import com.techwatch.techwatchbackend.iam.domain.model.commands.SignUpCommand;
import com.techwatch.techwatchbackend.iam.domain.model.events.UserRegisteredEvent;
import com.techwatch.techwatchbackend.iam.domain.model.valueobjects.EmailAddress;
import com.techwatch.techwatchbackend.iam.domain.model.valueobjects.Role;
import com.techwatch.techwatchbackend.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;
import lombok.Setter;

@Getter
public class User extends AbstractDomainAggregateRoot<User> {

    @Setter
    private Long id;

    @Setter
    private EmailAddress email;

    @Setter
    private String passwordHash;

    @Setter
    private Role role;

    public User() {
    }

    public User(SignUpCommand command, String passwordHash) {
        this.email = new EmailAddress(command.email());
        this.passwordHash = passwordHash;
        this.role = Role.ROLE_USER;
        registerDomainEvent(new UserRegisteredEvent(null, command.email(), command.firstName(), command.lastName()));
    }
}
