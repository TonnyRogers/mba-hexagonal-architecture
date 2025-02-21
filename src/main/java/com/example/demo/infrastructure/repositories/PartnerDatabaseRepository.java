package com.example.demo.infrastructure.repositories;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.application.domain.partner.Partner;
import com.example.demo.application.domain.partner.PartnerId;
import com.example.demo.application.domain.person.Cnpj;
import com.example.demo.application.domain.person.Email;
import com.example.demo.application.repositories.PartnerRepository;
import com.example.demo.infrastructure.jpa.models.PartnerEntity;
import com.example.demo.infrastructure.jpa.repositories.PartnerJpaRepository;

// interface adapter
@Component
public class PartnerDatabaseRepository implements PartnerRepository {

    private final PartnerJpaRepository partnerJpaRepository;

    public PartnerDatabaseRepository(final PartnerJpaRepository partnerJpaRepository) {
        this.partnerJpaRepository = Objects.requireNonNull(partnerJpaRepository);
    }

    @Override
    public Optional<Partner> partnerOfId(PartnerId id) {
        Objects.requireNonNull(id, "id cannot be null");
        return this.partnerJpaRepository.findById(UUID.fromString(id.value()))
                .map(PartnerEntity::toPartner);
    }

    @Override
    public Optional<Partner> partnerOfCNPJ(Cnpj cnpj) {
        Objects.requireNonNull(cnpj, "cnpj cannot be null");
        return this.partnerJpaRepository.findByCnpj(cnpj.value())
                .map(PartnerEntity::toPartner);
    }

    @Override
    public Optional<Partner> partnerOfEmail(Email email) {
        Objects.requireNonNull(email, "email cannot be null");
        return this.partnerJpaRepository.findByCnpj(email.value())
                .map(PartnerEntity::toPartner);
    }

    @Override
    @Transactional
    public Partner create(Partner partner) {
        return this.partnerJpaRepository.save(PartnerEntity.of(partner))
                .toPartner();
    }

    @Override
    @Transactional
    public Partner update(Partner partner) {
        return this.partnerJpaRepository.save(PartnerEntity.of(partner))
                .toPartner();
    }

}
