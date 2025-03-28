package com.example.demo.infrastructure.jpa.entities;

import java.util.UUID;

import com.example.demo.application.domain.partner.Partner;
import com.example.demo.application.domain.partner.PartnerId;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "partners")
public class PartnerEntity {

    @Id
    private UUID id;

    private String name;

    private String cnpj;

    private String email;

    public PartnerEntity() {
    }

    public PartnerEntity(UUID id, String name, String cnpj, String email) {
        this.id = id;
        this.name = name;
        this.cnpj = cnpj;
        this.email = email;
    }

    public static PartnerEntity of(final Partner partner) {
        return new PartnerEntity(
                UUID.fromString(partner.getPartnerId().value()),
                partner.getName().value(),
                partner.getCnpj().value(),
                partner.getEmail().value()
        );
    }

    public Partner toPartner() {
        return new Partner(PartnerId.with(this.id.toString()), this.name, this.cnpj, this.email);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
