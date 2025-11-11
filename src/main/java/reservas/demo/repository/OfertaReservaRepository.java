package reservas.demo.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import reservas.demo.models.Enums.TipoOferta;
import reservas.demo.models.ReservaExpirada;
import reservas.demo.models.dtos.CompradorSocioDTO;
import reservas.demo.models.dtos.OfertaReservaDTO;
import reservas.demo.models.informaciones.InfoCompReservas;
import reservas.demo.models.informaciones.InfoSocioOfertas;
import reservas.demo.models.informaciones.InfoSocioReservas;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class OfertaReservaRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public List<OfertaReservaDTO> findAllDTOs() {
        String jpql = "SELECT new reservas.demo.models.dtos.OfertaReservaDTO(" +
                "r.idreserva, r.fechares, r.horares, r.estadores, " +
                "r.idcomprador, o.precio, o.calidad, o.peso, " +
                "o.procedencia, o.marca, o.idsocio, o.estado) " +
                "FROM Reserva r JOIN Oferta o ON r.idoferta = o.idoferta";

        TypedQuery<OfertaReservaDTO> query = entityManager.createQuery(jpql, OfertaReservaDTO.class);
        return query.getResultList();
    }

    public Optional<OfertaReservaDTO> findOfertReservById(Long idres) {
        String jpql = "SELECT new reservas.demo.models.dtos.OfertaReservaDTO(" +
                "r.idreserva, r.fechares, r.horares, r.estadores, " +
                "r.idcomprador, o.precio, o.calidad, o.peso, " +
                "o.procedencia, o.marca, o.idsocio, o.estado) " +
                "FROM Reserva r JOIN Oferta o ON r.idoferta = o.idoferta " +
                "WHERE r.idreserva = :idres";

        TypedQuery<OfertaReservaDTO> query = entityManager.createQuery(jpql, OfertaReservaDTO.class);
        query.setParameter("idres", idres);
        return query.getResultList().stream().findFirst();
    }

    public List<OfertaReservaDTO> findByIdsocio(Long idsocio) {
        String jpql = "SELECT new reservas.demo.models.dtos.OfertaReservaDTO(" +
                "r.idreserva, r.fechares, r.horares, r.estadores, " +
                "r.idcomprador, o.precio, o.calidad, o.peso, " +
                "o.procedencia, o.marca, o.idsocio, o.estado) " +
                "FROM Reserva r JOIN Oferta o ON r.idoferta = o.idoferta " +
                "WHERE o.idsocio = :idsocio";

        TypedQuery<OfertaReservaDTO> query = entityManager.createQuery(jpql, OfertaReservaDTO.class);
        query.setParameter("idsocio", idsocio);
        return query.getResultList();
    }
    public List<OfertaReservaDTO> findByIdcomp(Long idcomp) {
        String jpql = "SELECT new reservas.demo.models.dtos.OfertaReservaDTO(" +
                "r.idreserva, r.fechares, r.horares, r.estadores, " +
                "r.idcomprador, o.precio, o.calidad, o.peso, " +
                "o.procedencia, o.marca, o.idsocio, o.estado) " +
                "FROM Reserva r JOIN Oferta o ON r.idoferta = o.idoferta " +
                "WHERE r.idcomprador = :idcomp";

        TypedQuery<OfertaReservaDTO> query = entityManager.createQuery(jpql, OfertaReservaDTO.class);
        query.setParameter("idcomp", idcomp);
        return query.getResultList();
    }
    public List<OfertaReservaDTO> findByMarca(String marka) {
        String jpql = "SELECT new reservas.demo.models.dtos.OfertaReservaDTO(" +
                "r.idreserva, r.fechares, r.horares, r.estadores, " +
                "r.idcomprador, o.precio, o.calidad, o.peso, " +
                "o.procedencia, o.marca, o.idsocio, o.estado) " +
                "FROM Reserva r JOIN Oferta o ON r.idoferta = o.idoferta " +
                "WHERE o.marca = :marka";

        TypedQuery<OfertaReservaDTO> query = entityManager.createQuery(jpql, OfertaReservaDTO.class);
        query.setParameter("marka", marka);
        return query.getResultList();
    }
    public List<OfertaReservaDTO> findAllDTOsByDateRange(String fechaInicioStr, String fechaFinStr) {
        // Convertir Strings a LocalDate
        LocalDate fechaInicio = LocalDate.parse(fechaInicioStr);
        LocalDate fechaFin = LocalDate.parse(fechaFinStr);
        String jpql = "SELECT new reservas.demo.models.dtos.OfertaReservaDTO(" +
                "r.idreserva, r.fechares, r.horares, r.estadores, " +
                "r.idcomprador, o.precio, o.calidad, o.peso, " +
                "o.procedencia, o.marca, o.idsocio, o.estado) " +
                "FROM Reserva r JOIN Oferta o ON r.idoferta = o.idoferta " +
                "WHERE r.fechares BETWEEN :fechaInicio AND :fechaFin " +
                "ORDER BY r.fechares DESC";
        TypedQuery<OfertaReservaDTO> query = entityManager.createQuery(jpql, OfertaReservaDTO.class);
        query.setParameter("fechaInicio", fechaInicio);
        query.setParameter("fechaFin", fechaFin);
        return query.getResultList();
    }
    public List<OfertaReservaDTO> findOfertaReservaSocioFecha(int tipoBusqueda,Long idBusqueda, int anio, int mes, int quincena) {
        String jpql = "SELECT new reservas.demo.models.dtos.OfertaReservaDTO(" +
                "r.idreserva, r.fechares, r.horares, r.estadores, " +
                "r.idcomprador, o.precio, o.calidad, o.peso, " +
                "o.procedencia, o.marca, o.idsocio, o.estado) " +
                "FROM Reserva r JOIN Oferta o ON r.idoferta = o.idoferta " +
                "WHERE YEAR(r.fechares) = :anio " +
                "AND o.estado = 'PAGADO' " +
                "AND (:mes = 0 OR MONTH(r.fechares) = :mes) " +
                "AND (:quincena = 3 OR CASE WHEN DAY(r.fechares) <= 15 THEN 1 ELSE 2 END = :quincena) "+
                "AND CASE WHEN :tipoBusqueda = 1 THEN r.idcomprador " +
                "         WHEN :tipoBusqueda = 2 THEN o.idsocio END = :idBusqueda";
        TypedQuery<OfertaReservaDTO> query = entityManager.createQuery(jpql, OfertaReservaDTO.class);
        query.setParameter("tipoBusqueda", tipoBusqueda);
        query.setParameter("idBusqueda", idBusqueda);
        query.setParameter("anio", anio);
        query.setParameter("mes", mes);
        query.setParameter("quincena", quincena);
        return query.getResultList();
    }
    public List<OfertaReservaDTO> findOfertaReservaSocioComp(Long idcomp,Long idsocio, int anio, int mes, int quincena) {
        String jpql = "SELECT new reservas.demo.models.dtos.OfertaReservaDTO(" +
                "r.idreserva, r.fechares, r.horares, r.estadores, " +
                "r.idcomprador, o.precio, o.calidad, o.peso, " +
                "o.procedencia, o.marca, o.idsocio, o.estado) " +
                "FROM Reserva r JOIN Oferta o ON r.idoferta = o.idoferta " +
                "WHERE YEAR(r.fechares) = :anio " +
                "AND (:mes = 0 OR MONTH(r.fechares) = :mes) " +
                "AND o.estado = 'PAGADO' " +
                "AND (:quincena = 3 OR CASE WHEN DAY(r.fechares) <= 15 THEN 1 ELSE 2 END = :quincena) "+
                "AND r.idcomprador = :idcomp " +
                "AND o.idsocio = :idsocio";
        TypedQuery<OfertaReservaDTO> query = entityManager.createQuery(jpql, OfertaReservaDTO.class);
        query.setParameter("idcomp", idcomp);
        query.setParameter("idsocio", idsocio);
        query.setParameter("anio", anio);
        query.setParameter("mes", mes);
        query.setParameter("quincena", quincena);
        return query.getResultList();
    }
    public List<CompradorSocioDTO> findCompradoresSocio(Long idSocio) {
        String jpql = "SELECT DISTINCT new reservas.demo.models.dtos.CompradorSocioDTO(" +
                "r.idcomprador, CONCAT(p.nombre, ' ', p.apellido), p.carnet) " +
                "FROM Reserva r " +
                "JOIN Oferta o ON o.idoferta = r.idoferta " +
                "JOIN Persona p ON r.idcomprador = p.id_persona " +
                "WHERE o.idsocio = :idSocio";
        TypedQuery<CompradorSocioDTO> query = entityManager.createQuery(jpql, CompradorSocioDTO.class);
        query.setParameter("idSocio", idSocio);
        return query.getResultList();
    }

    public InfoSocioReservas findResumenSocioReservas(Long idsocio, int anio, int mes) {
        System.out.println("idsocio = " + idsocio + ", anio = " + anio + ", mes = " + mes);
        String jpql = "SELECT NEW reservas.demo.models.informaciones.InfoSocioReservas(" +
                "SUM(o.precio), COUNT(o)) " +
                "FROM Reserva r JOIN Oferta o ON r.idoferta = o.idoferta " +
                "WHERE o.idsocio = :idsocio " +
                "AND YEAR(r.fechares) = :anio " +
                "AND MONTH(r.fechares) = :mes " +
                "AND o.estado = 'RESERVADO'"+
                "AND r.estadores = 'ACTIVA'";
        TypedQuery<InfoSocioReservas> query = entityManager.createQuery(jpql, InfoSocioReservas.class);
        query.setParameter("idsocio", idsocio);
        query.setParameter("anio", anio);
        query.setParameter("mes", mes);
        return query.getSingleResult();
    }
    public InfoSocioOfertas findResumenSocioOfertas(TipoOferta state, Long idsocio, int anio, int mes) {
        System.out.println("state: "+state+" idsocio = " + idsocio + ", anio = " + anio + ", mes = " + mes);
        String jpql = "SELECT NEW reservas.demo.models.informaciones.InfoSocioOfertas(" +
                "SUM(o.precio), COUNT(o)) " +
                "FROM Oferta o " +
                "WHERE o.idsocio = :idsocio " +
                "AND YEAR(o.fechao) = :anio " +
                "AND MONTH(o.fechao) = :mes " +
                "AND o.estado = :state";
        TypedQuery<InfoSocioOfertas> query = entityManager.createQuery(jpql, InfoSocioOfertas.class);
        query.setParameter("idsocio", idsocio);
        query.setParameter("state", state);
        query.setParameter("anio", anio);
        query.setParameter("mes", mes);
        return query.getSingleResult();
    }
    public InfoCompReservas findResumenPagosComprador(Long idcomprador, int anio, int mes) {
        String jpql = "SELECT NEW reservas.demo.models.informaciones.InfoCompReservas(" +
                "SUM(o.precio), COUNT(o)) " +
                "FROM Reserva r JOIN Oferta o ON r.idoferta = o.idoferta " +
                "WHERE r.idcomprador = :idcomprador " +
                "AND r.estadores = 'PAGADA' "+
                "AND o.AND YEAR(r.fechares) = :anio " +
                "AND MONTH(r.fechares) = :mes";

        TypedQuery<InfoCompReservas> query = entityManager.createQuery(jpql, InfoCompReservas.class);
        query.setParameter("idcomprador", idcomprador);
        query.setParameter("anio", anio);
        query.setParameter("mes", mes);

        return query.getSingleResult();
    }
    public InfoSocioReservas findOfertaReservas(Long idsocio, int anio, int mes) {
        System.out.println("idsocio = " + idsocio + ", anio = " + anio + ", mes = " + mes);
        String jpql = "SELECT NEW reservas.demo.models.informaciones.InfoSocioReservas(" +
                "SUM(o.precio), COUNT(o)) " +
                "FROM Reserva r JOIN Oferta o ON r.idoferta = o.idoferta " +
                "WHERE o.idsocio = :idsocio " +
                "AND YEAR(r.fechares) = :anio " +
                "AND MONTH(r.fechares) = :mes ";
        TypedQuery<InfoSocioReservas> query = entityManager.createQuery(jpql, InfoSocioReservas.class);
        query.setParameter("idsocio", idsocio);
        query.setParameter("anio", anio);
        query.setParameter("mes", mes);
        return query.getSingleResult();
    }
    public List<ReservaExpirada> findReservasExpiradas(int anio, int mes) {
        System.out.println(anio+" Lista de reservas activas: "+mes);
        String jpql = "SELECT new reservas.demo.models.ReservaExpirada(" +
                "r.horares, r.idreserva, o.idoferta, r.idcomprador,o.idsocio) " +
                "FROM Reserva r JOIN Oferta o ON r.idoferta = o.idoferta " +
                "WHERE YEAR(o.fechao) = :anio " +
                "AND MONTH(o.fechao) = :mes " +
                "AND r.estadores = 'ACTIVA' " +
                "AND o.estado = 'RESERVADO'";
        TypedQuery<ReservaExpirada> query = entityManager.createQuery(jpql, ReservaExpirada.class);
        query.setParameter("anio", anio);
        query.setParameter("mes", mes);
        return query.getResultList();
    }
    public List<ReservaExpirada> findReservasPagadas(int anio, int mes) {
        System.out.println(anio+" Lista de reservas activas: "+mes);
        String jpql = "SELECT new reservas.demo.models.ReservaExpirada(" +
                "r.horares, r.idreserva, o.idoferta, r.idcomprador,o.idsocio) " +
                "FROM Reserva r JOIN Oferta o ON r.idoferta = o.idoferta " +
                "WHERE YEAR(o.fechao) = :anio " +
                "AND MONTH(o.fechao) = :mes " +
                "AND r.estadores = 'ACTIVA' " +
                "AND o.estado = 'PAGADO'";
        TypedQuery<ReservaExpirada> query = entityManager.createQuery(jpql, ReservaExpirada.class);
        query.setParameter("anio", anio);
        query.setParameter("mes", mes);
        return query.getResultList();
    }
    public Long findOfertaReservaActiva(Long idoferta) {
        String jpql = "SELECT r.idreserva " +
                "FROM Oferta o, Reserva r " +
                "WHERE o.idoferta = r.idoferta " +
                "AND r.idoferta = :idoferta " +
                "AND r.estador = 'ACTIVA'";

        return entityManager.createQuery(jpql, Long.class)
                .setParameter("idoferta", idoferta)
                .getSingleResult();
    }
}
