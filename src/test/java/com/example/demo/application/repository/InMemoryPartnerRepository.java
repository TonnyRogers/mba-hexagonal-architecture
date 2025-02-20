package com.example.demo.application.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import com.example.demo.application.domain.partner.Partner;
import com.example.demo.application.domain.partner.PartnerId;
import com.example.demo.application.repositories.PartnerRepository;

public class InMemoryPartnerRepository implements PartnerRepository {

    private final Map<String, Partner> partners;
    private final Map<String, Partner> partnersByCNPJ;
    private final Map<String, Partner> partnersByEmail;

    public InMemoryPartnerRepository() {
        this.partners = new HashMap<>();
        this.partnersByCNPJ = new HashMap<>();
        this.partnersByEmail = new HashMap<>();
    }

    @Override
    public Optional<Partner> partnerOfId(PartnerId id) {
        return Optional.ofNullable(this.partners.get(Objects.requireNonNull(id).value()));
    }

    @Override
    public Optional<Partner> partnerOfCNPJ(String CNPJ) {
        return Optional.ofNullable(this.partnersByCNPJ.get(Objects.requireNonNull(CNPJ)));
    }

    @Override
    public Optional<Partner> partnerOfEmail(String Email) {
        return Optional.ofNullable(this.partnersByEmail.get(Objects.requireNonNull(Email)));
    }

    @Override
    public Partner create(Partner partner) {
        this.partners.put(partner.getPartnerId().value(), partner);
        this.partnersByCNPJ.put(partner.getCnpj().value(), partner);
        this.partnersByEmail.put(partner.getEmail().value(), partner);
        return partner;
    }

    @Override
    public Partner update(Partner partner) {
        this.partners.put(partner.getPartnerId().value(), partner);
        this.partnersByCNPJ.put(partner.getCnpj().value(), partner);
        this.partnersByEmail.put(partner.getEmail().value(), partner);
        return partner;
    }

}
