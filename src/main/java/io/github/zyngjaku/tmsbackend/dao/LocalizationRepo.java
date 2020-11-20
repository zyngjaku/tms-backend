package io.github.zyngjaku.tmsbackend.dao;

import io.github.zyngjaku.tmsbackend.dao.entity.Company;
import io.github.zyngjaku.tmsbackend.dao.entity.Localization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocalizationRepo extends JpaRepository<Localization, Long> {
}
