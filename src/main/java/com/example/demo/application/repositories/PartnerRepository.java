package com.example.demo.application.repositories;

import java.util.Optional;

import com.example.demo.application.domain.partner.Partner;
import com.example.demo.application.domain.partner.PartnerId;
import com.example.demo.application.domain.person.Cnpj;
import com.example.demo.application.domain.person.Email;

public interface PartnerRepository {

    Optional<Partner> partnerOfId(PartnerId id);

    Optional<Partner> partnerOfCNPJ(Cnpj cnpj);

    Optional<Partner> partnerOfEmail(Email email);

    Partner create(Partner partner);

    Partner update(Partner partner);
}
