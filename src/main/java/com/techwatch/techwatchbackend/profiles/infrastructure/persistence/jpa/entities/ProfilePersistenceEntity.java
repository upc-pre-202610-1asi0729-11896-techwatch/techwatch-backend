package com.techwatch.techwatchbackend.profiles.infrastructure.persistence.jpa.entities;

import com.techwatch.techwatchbackend.profiles.domain.model.valueobjects.UserId;
import com.techwatch.techwatchbackend.profiles.infrastructure.persistence.jpa.converters.UserIdPersistenceConverter;
import com.techwatch.techwatchbackend.profiles.infrastructure.persistence.jpa.embeddables.PersonNamePersistenceEmbeddable;
import com.techwatch.techwatchbackend.profiles.infrastructure.persistence.jpa.embeddables.PreferencesPersistenceEmbeddable;
import com.techwatch.techwatchbackend.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "profiles")
@Getter
@Setter
@NoArgsConstructor
public class ProfilePersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Convert(converter = UserIdPersistenceConverter.class)
    @Column(name = "user_id", nullable = false, unique = true)
    private UserId userId;

    @Embedded
    private PersonNamePersistenceEmbeddable fullName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "profile_image_url")
    private String profileImageUrl;

    @Embedded
    private PreferencesPersistenceEmbeddable preferences;
}
