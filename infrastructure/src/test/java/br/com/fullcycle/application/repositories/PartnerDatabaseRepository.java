package br.com.fullcycle.application.repositories;


import br.com.fullcycle.domain.partner.Partner;
import br.com.fullcycle.domain.partner.PartnerId;
import br.com.fullcycle.domain.partner.PartnerRepository;
import br.com.fullcycle.domain.person.Cnpj;
import br.com.fullcycle.domain.person.Email;
import br.com.fullcycle.infrastructure.jpa.entities.PartnerEntity;
import br.com.fullcycle.infrastructure.jpa.repositories.PartnerJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

// Interface Adapter
@Component
public class PartnerDatabaseRepository implements PartnerRepository {

    private final PartnerJpaRepository partnerJpaRepository;

    public PartnerDatabaseRepository(PartnerJpaRepository partnerJpaRepository) {
        this.partnerJpaRepository = Objects.requireNonNull(partnerJpaRepository);
    }


    @Override
    public Optional<Partner> partnerOfId(final PartnerId anId) {
        Objects.requireNonNull(anId, "Id must not be null");
        return this.partnerJpaRepository.findById(UUID.fromString(anId.value()))
        .map(PartnerEntity::toPartner);
    }

    @Override
    public Optional<Partner> partnerOfCNPJ(final Cnpj cnpj) {
        Objects.requireNonNull(cnpj, "Cpf must not be null");
        return this.partnerJpaRepository.findByCnpj(cnpj.value())
                .map(PartnerEntity::toPartner);
    }

    @Override
    public Optional<Partner> partnerOfEmail(final Email email) {
        Objects.requireNonNull(email, "Email must not be null");
        return this.partnerJpaRepository.findByEmail(email.value())
                .map(PartnerEntity::toPartner);
    }

    @Override
    @Transactional
    public Partner create(Partner partner) {
        return this.partnerJpaRepository.save(PartnerEntity.of(partner)).toPartner();
    }

    @Override
    @Transactional
    public Partner update(Partner partner) {
        return this.partnerJpaRepository.save(PartnerEntity.of(partner)).toPartner();
    }

    @Override
    public void deleteAll() {
        this.partnerJpaRepository.deleteAll();
    }
}
