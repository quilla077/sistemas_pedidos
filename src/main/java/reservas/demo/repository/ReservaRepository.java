package reservas.demo.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reservas.demo.models.entitys.Reserva;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva,Long> {

    // Consultas nativas personalizadas
    @Query(value = "SELECT * FROM reserva WHERE idcomprador = :idComprador", nativeQuery = true)
    List<Reserva> findByCompradorNative(@Param("idComprador") Long idComprador);

    @Query(value = "SELECT * FROM reserva WHERE fechar = :fecha", nativeQuery = true)
    List<Reserva> findByFechaNative(@Param("fecha") LocalDate fecha);

    @Query(value = "SELECT * FROM reserva WHERE estador = :estado", nativeQuery = true)
    List<Reserva> findByEstadoNative(@Param("estado") String estado);

    @Query(value = "SELECT r.* FROM reserva r " +
            "INNER JOIN oferta o ON r.idoferta = o.idoferta " +
            "INNER JOIN vehiculo v ON o.idvehiculo = v.idvehiculo " +
            "WHERE v.marca = :marca", nativeQuery = true)
    List<Reserva> findByMarcaNative(@Param("marca") String marca);

    @Query(value = "SELECT * FROM reserva WHERE idcomprador = :idComprador AND estador = :estado", nativeQuery = true)
    List<Reserva> findByCompradorAndEstadoNative(@Param("idComprador") Long idComprador, @Param("estado") String estado);

    @Query(value = "SELECT * FROM reserva WHERE fechar BETWEEN :fechaInicio AND :fechaFin", nativeQuery = true)
    List<Reserva> findByRangoFechasNative(@Param("fechaInicio") LocalDate fechaInicio, @Param("fechaFin") LocalDate fechaFin);

    @Modifying
    @Transactional
    @Query("UPDATE Reserva SET estadores = :estado WHERE idreserva = :id")
    void actualizaEstado(@Param("estado") String estado, @Param("id") Long id);

}
