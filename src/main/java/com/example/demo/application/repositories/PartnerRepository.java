package com.example.demo.application.repositories;

import java.util.Optional;

import com.example.demo.application.domain.partner.Partner;
import com.example.demo.application.domain.partner.PartnerId;

public interface PartnerRepository {

    Optional<Partner> partnerOfId(PartnerId id);

    Optional<Partner> partnerOfCNPJ(String CPNJ);

    Optional<Partner> partnerOfEmail(String Email);

    Partner create(Partner partner);

    Partner update(Partner partner);
}
