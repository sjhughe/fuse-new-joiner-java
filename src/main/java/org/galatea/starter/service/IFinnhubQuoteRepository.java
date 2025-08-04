package org.galatea.starter.service;

import org.galatea.starter.domain.FinnhubQuoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IFinnhubQuoteRepository extends JpaRepository<FinnhubQuoteEntity, String> {
}
