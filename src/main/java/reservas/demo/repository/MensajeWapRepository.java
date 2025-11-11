package reservas.demo.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reservas.demo.models.entitys.MensajeWap;

import java.util.List;

@Repository
public interface MensajeWapRepository extends JpaRepository<MensajeWap,Long> {
    // Metodo específico para ESPERANDO
    List<MensajeWap> findByEstadom(String estadom);
    @Modifying
    @Transactional
    @Query("UPDATE MensajeWap m SET m.estadom = :estado WHERE m.idmensaje = :id")
    void actualizarEstado(@Param("id") Long id, @Param("estado") String estado);
}
