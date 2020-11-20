package io.github.zyngjaku.tmsbackend.dao;

import io.github.zyngjaku.tmsbackend.dao.entity.TransshipmentTerminal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransshipmentTerminalRepo extends JpaRepository<TransshipmentTerminal, Long> {
}
