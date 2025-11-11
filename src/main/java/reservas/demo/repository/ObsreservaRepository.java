package reservas.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reservas.demo.models.Enums.EstadoObs;
import reservas.demo.models.Enums.TipoObsRes;
import reservas.demo.models.entitys.Obsreserva;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ObsreservaRepository extends JpaRepository<Obsreserva,Long> {
    // Buscar por tipo de observación
    List<Obsreserva> findByTipoobs(TipoObsRes tipoobs);

    @Query("SELECT o FROM Obsreserva o WHERE o.tipoobs = :tipo AND o.idemisor = :id")
    List<Obsreserva> findByTipoobsId(@Param("tipo") TipoObsRes tipoobs,@Param("id") Long id);

    // Buscar por fecha
    List<Obsreserva> findByFechaobs(LocalDate fechaobs);

    // Buscar por rango de fechas
    List<Obsreserva> findByFechaobsBetween(LocalDate fechaInicio, LocalDate fechaFin);

    // Buscar por idemisor
    List<Obsreserva> findByIdemisor(Long idemisor);

    // Buscar por idreceptor
    List<Obsreserva> findByIdreceptor(Long idreceptor);

    // Buscar por idreserva
    List<Obsreserva> findByIdreserva(Long idreserva);

    // Buscar por estado
    List<Obsreserva> findByEstado(EstadoObs estado);

    // Buscar por múltiples criterios
    @Query("SELECT o FROM Obsreserva o WHERE o.tipoobs = :tipo AND o.estado = :estado")
    List<Obsreserva> findByTipoAndEstado(@Param("tipo") TipoObsRes tipo,
                                         @Param("estado") EstadoObs estado);

    // Contar observaciones por tipo
    @Query("SELECT o.tipoobs, COUNT(o) FROM Obsreserva o GROUP BY o.tipoobs")
    List<Object[]> countByTipoobs();
}
