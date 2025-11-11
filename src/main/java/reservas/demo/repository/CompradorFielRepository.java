package reservas.demo.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import reservas.demo.models.dtos.CompradorFielDto;
import reservas.demo.models.dtos.OfertaReservaDTO;

import java.time.LocalDate;
import java.util.List;

@Repository
public class CompradorFielRepository {
    @PersistenceContext
    private EntityManager entityManager;

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
    public List<CompradorFielDto[]> findTopFidelidad1(Long idsocio, int nmeses, int ncomp) {
        String sql = "SELECT " +
                "    id_comp AS idcompfiel, " +
                "    COUNT(id_comp) AS numveces " +
                "FROM fidelidad f " +
                "WHERE fecha_res >= DATE_SUB(CURDATE(), INTERVAL :nmeses MONTH) " +
                "AND f.id_socio = :idsocio " +
                "GROUP BY id_comp " +
                "ORDER BY numveces DESC " +
                "LIMIT :ncomp";

        Query query = entityManager.createNativeQuery(sql)
                .setParameter("idsocio", idsocio)
                .setParameter("nmeses", nmeses)
                .setParameter("ncomp", ncomp);

        return query.getResultList();
    }
    public List<CompradorFielDto[]> findTopFidelidad(Long idsocio, int nmeses, int ncomp){
        // Calcular fecha límite (2 meses atrás)
        LocalDate fechaLimite = LocalDate.now().minusMonths(nmeses);
        // Crear consulta JPQL
        TypedQuery<CompradorFielDto[]> query = entityManager.createQuery(
                "SELECT f.idComp, COUNT(f.idComp) " +
                        "FROM Fidelidad f " +
                        "WHERE f.fechaRes >= :fechaLimite " +
                        "AND f.idSocio = :idSocio " +
                        "GROUP BY f.idComp " +
                        "ORDER BY COUNT(f.idComp) DESC", CompradorFielDto[].class);
        query.setParameter("fechaLimite", fechaLimite);
        query.setParameter("idSocio", idsocio);
        query.setMaxResults(ncomp);
        // Ejecutar consulta y obtener resultados
        return query.getResultList();
    }
    public List<CompradorFielDto[]> findTopFidelidad2(Long idsocio, int nmeses, int ncomp) {
        String jpql = "SELECT f.idComp AS idcompfiel, COUNT(f.idComp) AS numveces " +
                "FROM Fidelidad f " +
                "WHERE f.fechaRes >= FUNCTION('DATE_SUB', CURRENT_DATE, nmeses, 'MONTH') " +
                "AND f.idSocio = :idsocio " +
                "GROUP BY f.idComp " +
                "ORDER BY COUNT(f.idComp) DESC";

        TypedQuery<CompradorFielDto[]> query = entityManager.createQuery(jpql, CompradorFielDto[].class)
                .setParameter("idsocio", idsocio)
                .setParameter("nmeses", nmeses)
                .setMaxResults(ncomp);

        return query.getResultList();
    }

}
