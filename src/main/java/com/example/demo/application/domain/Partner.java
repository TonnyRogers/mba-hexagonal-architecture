package com.example.demo.application.domain;

import com.example.demo.application.exceptions.ValidationException;

public class Partner {

    private final PartnerId partnerId;
    private Name name;
    private Cnpj cnpj;
    private Email email;

    public Partner(final PartnerId partnerId, final String name, final String cnpj, final String email) {

        if (partnerId == null) {
            throw new ValidationException("Invalid partnerId for Partner");
        }

        this.partnerId = partnerId;
        this.setCnpj(cnpj);
        this.setEmail(email);
        this.setName(name);
    }

    public static Partner newPartner(String name, String cnpj, String email) {
        return new Partner(PartnerId.unique(), name, cnpj, email);
    }

    public PartnerId getPartnerId() {
        return partnerId;
    }

    public Name getName() {
        return name;
    }

    public Cnpj getCnpj() {
        return cnpj;
    }

    public Email getEmail() {
        return email;
    }

    private void setName(final String name) {
        this.name = new Name(name);
    }

    private void setCnpj(final String cnpj) {
        this.cnpj = new Cnpj(cnpj);
    }

    private void setEmail(final String email) {
        this.email = new Email(email);
    }

}
