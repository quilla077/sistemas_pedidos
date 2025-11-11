package reservas.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import reservas.demo.models.entitys.Usuario;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
    //Usuario findByUsername(String username);
    Optional<Usuario> findByUsername(String username);
    // Consulta personalizada para obtener el ID máximo
    @Query("SELECT MAX(p.id_persona) FROM Persona p")
    Long findMaxIdPersona();

    List<Usuario> findByRole(String name);
    @Query(value="select * from user u where u.role like ?1",
            nativeQuery = true)
    List<Usuario> buscarTipo(String tipo);

    @Query(value="select * from user u where u.`role` like 'CUST' and u.id_area = ?1",
            nativeQuery = true)
    List<Usuario> buscarCustArea(int area);
    @Query(value="select * from user u where u.`role` like 'PROP' and u.id_area = ?1",
            nativeQuery = true)
    List<Usuario> buscarPropArea(int area);
    @Query(value="select * from user u where u.username like ?1",
            nativeQuery = true)
    Usuario obtenerByUsername(String username);
    @Query(value="select * from user u where u.role like 'SEG' or u.role like 'ADMIN'",
            nativeQuery = true)
    List<Usuario> buscarTipoReg();

    @Query(value = "select u.carnet from user u where u.username like ?1 ",nativeQuery = true)
    Long obtenerId(String username);

}
