package reservas.demo.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reservas.demo.models.Enums.EstadoGanado;
import reservas.demo.models.entitys.Ganado;

import java.util.List;

@Repository
public interface GanadoRepository extends JpaRepository<Ganado, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE Ganado g SET g.estadog = :estado WHERE g.idganado = :id")
    void actualizarEstado(@Param("id") Long id, @Param("estado") EstadoGanado estado);

    List<Ganado> findByIdsocio(Long idsocio);
    List<Ganado> findByEstadog(EstadoGanado estadog);
    List<Ganado> findByIdsocioAndEstadog(Long idsocio, EstadoGanado estadog);
}
