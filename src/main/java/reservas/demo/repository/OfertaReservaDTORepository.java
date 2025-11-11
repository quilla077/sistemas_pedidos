package reservas.demo.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reservas.demo.models.dtos.OfertaReservaDTO;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OfertaReservaDTORepository {//extends JpaRepository<Object, Long>

    // Cambiar la interfaz para que no extienda de JpaRepository<OfertaReservaDTO, Long>
//    @Query("SELECT new reservas.demo.models.dtos.OfertaReservaDTO(" +
//            "r.idreserva, r.fechares, r.horares, r.estadores, " +
//            "r.idcomprador, o.precio, o.calidad, o.peso, " +
//            "o.procedencia, o.marca, o.idsocio, o.estado) " +
//            "FROM Reserva r JOIN Oferta o ON r.idoferta = o.idoferta")
//    List<OfertaReservaDTO> findAllDTOs();
//
//    @Query("SELECT new reservas.demo.models.dtos.OfertaReservaDTO(" +
//            "r.idreserva, r.fechares, r.horares, r.estadores, " +
//            "r.idcomprador, o.precio, o.calidad, o.peso, " +
//            "o.procedencia, o.marca, o.idsocio, o.estado) " +
//            "FROM Reserva r JOIN Oferta o ON r.idoferta = o.idoferta " +
//            "WHERE o.idsocio = :idsocio")
//    List<OfertaReservaDTO> findByIdsocio(@Param("idsocio") Long idsocio);
//    // Buscar por idSocio
//    @Query("SELECT new com.tudominio.dto.OfertaReservaDTO(r.idreserva, r.fechares, r.horares, r.estadores, " +
//            "r.idcomprador, o.precio, o.calidad, o.peso, o.procedencia, o.marca, o.idsocio, o.estado) " +
//            "FROM Reserva r JOIN Oferta o ON r.idoferta = o.idoferta " +
//            "WHERE o.idsocio = :idsocio")
//    List<OfertaReservaDTO> findByIdsocio(@Param("idsocio") Long idsocio);
//
//    // Buscar por idComprador
//    @Query("SELECT new com.tudominio.dto.OfertaReservaDTO(r.idreserva, r.fechares, r.horares, r.estadores, " +
//            "r.idcomprador, o.precio, o.calidad, o.peso, o.procedencia, o.marca, o.idsocio, o.estado) " +
//            "FROM Reserva r JOIN Oferta o ON r.idoferta = o.idoferta " +
//            "WHERE r.idcomprador = :idcomprador")
//    List<OfertaReservaDTO> findByIdcomprador(@Param("idcomprador") Long idcomprador);
//
//    // Buscar por fecha de reserva
//    @Query("SELECT new com.tudominio.dto.OfertaReservaDTO(r.idreserva, r.fechares, r.horares, r.estadores, " +
//            "r.idcomprador, o.precio, o.calidad, o.peso, o.procedencia, o.marca, o.idsocio, o.estado) " +
//            "FROM Reserva r JOIN Oferta o ON r.idoferta = o.idoferta " +
//            "WHERE r.fechares = :fechares")
//    List<OfertaReservaDTO> findByFechares(@Param("fechares") LocalDate fechares);
//
//    // Buscar por marca
//    @Query("SELECT new com.tudominio.dto.OfertaReservaDTO(r.idreserva, r.fechares, r.horares, r.estadores, " +
//            "r.idcomprador, o.precio, o.calidad, o.peso, o.procedencia, o.marca, o.idsocio, o.estado) " +
//            "FROM Reserva r JOIN Oferta o ON r.idoferta = o.idoferta " +
//            "WHERE o.marca = :marca")
//    List<OfertaReservaDTO> findByMarca(@Param("marca") String marca);
//
//    // Buscar por fecha y marca
//    @Query("SELECT new com.tudominio.dto.OfertaReservaDTO(r.idreserva, r.fechares, r.horares, r.estadores, " +
//            "r.idcomprador, o.precio, o.calidad, o.peso, o.procedencia, o.marca, o.idsocio, o.estado) " +
//            "FROM Reserva r JOIN Oferta o ON r.idoferta = o.idoferta " +
//            "WHERE r.fechares = :fechares AND o.marca = :marca")
//    List<OfertaReservaDTO> findByFecharesAndMarca(@Param("fechares") LocalDate fechares,
//                                                  @Param("marca") String marca);

}
