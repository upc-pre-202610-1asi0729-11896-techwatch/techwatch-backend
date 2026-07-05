package com.techwatch.techwatchbackend.profiles.domain.model.aggregates;

import com.techwatch.techwatchbackend.profiles.domain.model.commands.CreateProfileCommand;
import com.techwatch.techwatchbackend.profiles.domain.model.valueobjects.PersonName;
import com.techwatch.techwatchbackend.profiles.domain.model.valueobjects.Preferences;
import com.techwatch.techwatchbackend.profiles.domain.model.valueobjects.UserId;
import com.techwatch.techwatchbackend.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;
import lombok.Setter;

@Getter
public class Profile extends AbstractDomainAggregateRoot<Profile> {

    @Setter
    private Long id;

    @Setter
    private UserId userId;

    @Setter
    private PersonName fullName;

    @Setter
    private String phoneNumber;

    @Setter
    private String profileImageUrl;

    @Setter
    private Preferences preferences;

    public Profile() {
    }

    public Profile(CreateProfileCommand command) {
        this.userId = new UserId(command.userId());
        this.fullName = new PersonName(command.firstName(), command.lastName());
        this.phoneNumber = command.phoneNumber();
        this.profileImageUrl = command.profileImageUrl();
        this.preferences = Preferences.defaults();
    }

    public Profile updateInformation(PersonName fullName, String phoneNumber, String profileImageUrl) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.profileImageUrl = profileImageUrl;
        return this;
    }

    public Profile updatePreferences(Preferences preferences) {
        this.preferences = preferences;
        return this;
    }
}
