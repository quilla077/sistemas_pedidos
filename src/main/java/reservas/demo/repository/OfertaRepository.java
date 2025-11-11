package reservas.demo.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reservas.demo.models.entitys.Oferta;
import reservas.demo.models.Enums.TipoOferta;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OfertaRepository extends JpaRepository<Oferta, Long> {
    // Buscar por idSocio
    List<Oferta> findByIdsocio(Long idSocio);

    // Buscar por calidad
    List<Oferta> findByCalidad(String calidad);

    // Buscar por rango de fechas
    @Query(value = "SELECT * FROM oferta WHERE fechao BETWEEN :startDate AND :endDate",
            nativeQuery = true)
    List<Oferta> findByFechaoBetween(@Param("startDate") LocalDateTime startDate,
                                     @Param("endDate") LocalDateTime endDate);
//    List<Oferta> findByFechaOBetween(LocalDateTime startDate, LocalDateTime endDate);

    // Buscar por fecha específica (día completo)
    @Query(value = "SELECT * FROM oferta WHERE DATE(fechao) = DATE(:fecha)",
            nativeQuery = true)
    List<Oferta> findByFecha(@Param("fecha") LocalDateTime fecha);

    // Buscar por idSocio y calidad
    List<Oferta> findByIdsocioAndCalidad(Long idSocio, String calidad);

    // Buscar ofertas con precio mayor a
    List<Oferta> findByPrecioGreaterThan(Double precio);

    @Query(value = "SELECT * FROM oferta o WHERE o.estado = :estado", nativeQuery = true)
    List<Oferta> findByEstadoNative(@Param("estado") String estado);
    @Query("SELECT o FROM Oferta o WHERE o.estado = :estado")
    List<Oferta> findByEstado(@Param("estado") TipoOferta estado);

    // Para MySQL
    @Query("SELECT o FROM Oferta o WHERE o.fechao < :fechaLimite AND o.estado = 'ACTIVO'")
    List<Oferta> findOfertasConMasDeDosDiasActivas(@Param("fechaLimite") LocalDateTime fechaLimite);

    default List<Oferta> findOfertasConMasDeDosDiasActivas() {
        LocalDateTime fechaLimite = LocalDateTime.now().minusDays(2);
        return findOfertasConMasDeDosDiasActivas(fechaLimite);
    }

    @Modifying
    @Transactional
    @Query("UPDATE Oferta o SET o.estado = :estado WHERE o.idoferta = :id")
    void actualizarEstado(@Param("estado") TipoOferta estado, @Param("id") Long id);

}
