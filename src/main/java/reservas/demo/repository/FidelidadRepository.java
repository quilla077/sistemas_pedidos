package reservas.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import reservas.demo.models.entitys.Fidelidad;
import org.springframework.stereotype.Repository;
import reservas.demo.models.dtos.CompradorFielDto;

import java.util.List;

@Repository
public interface FidelidadRepository extends JpaRepository<Fidelidad, Long> {
    @Query(value = "SELECT id_comp AS idComp, COUNT(id_comp) AS numeroVeces " +
            "FROM fidelidad f " +
            "WHERE fecha_res >= DATE_SUB(CURDATE(), INTERVAL 2 MONTH) " +
            "AND f.id_socio = :idSocio " +
            "GROUP BY id_comp,f.cel_comp  " +
            "ORDER BY numeroVeces DESC " +
            "LIMIT 2", nativeQuery = true)
    List<CompradorFielDto> findTop2CompradoresFieles(@Param("idSocio") Integer idSocio);
    @Query(value = """
        SELECT 
            id_comp AS id_comp,
            COUNT(id_comp) AS numero_veces, cel_comp AS numcel
        FROM fidelidad f
        WHERE fecha_res >= DATE_SUB(CURDATE(), INTERVAL :nmeses MONTH)
        AND f.id_socio = :idSocio
        GROUP BY id_comp,f.cel_comp 
        ORDER BY numero_veces DESC
        LIMIT :ncont
        """, nativeQuery = true)
    List<CompradorFielDto> findTopCompradoresFieles(
            @Param("idSocio") Long idSocio,
            @Param("nmeses") int nmeses,
            @Param("ncont") int ncont);
}
