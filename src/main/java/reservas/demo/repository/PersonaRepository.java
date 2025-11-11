package reservas.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reservas.demo.models.dtos.UserAdminDto;
import reservas.demo.models.dtos.UserCompradorDto;
import reservas.demo.models.dtos.UserPorteroDto;
import reservas.demo.models.dtos.UserSocioDto;
import reservas.demo.models.entitys.Admin;
import reservas.demo.models.entitys.Comprador;
import reservas.demo.models.entitys.Persona;
import reservas.demo.models.entitys.Socio;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long> {
    // Buscar por tipo usando el discriminador
    @Query("SELECT s FROM Socio s")
    List<Socio> findAllSocios();
    @Query("SELECT c FROM Comprador c")
    List<Comprador> findAllCompradores();
    @Query("SELECT a FROM Admin a")
    List<Admin> findAllAdmins();

    @Query(value = "SELECT a FROM Admin WHERE id_persona = ?1", nativeQuery = true)
    Persona buscarAdminById(Long x);

    @Query(value = "SELECT * FROM persona WHERE id_persona = ?1" +
            " and tipo_persona like 'ADMIN'", nativeQuery = true)
    Persona findPersonaById(Long x);

    // Consulta JPQL para obtener persona por id_persona
    @Query("SELECT p FROM Persona p WHERE p.id_persona = :id")
    Optional<Persona> findByIdPersona(@Param("id") Long id);

    // Consulta nativa alternativa (opcional)
    @Query(value = "SELECT * FROM persona WHERE id_persona = :id", nativeQuery = true)
    Optional<Persona> findByIdPersonaNative(@Param("id") Long id);

    @Query("SELECT a FROM Admin a WHERE a.id_persona = :id")
    Optional<Admin> findAdminById(@Param("id") Long id);
    @Query("SELECT s FROM Socio s WHERE s.id_persona = :id")
    Socio findSocioById(@Param("id") Long id);
    @Query("SELECT c FROM Comprador c WHERE c.id_persona = :id")
    Comprador findCompradorById(@Param("id") Long id);

    @Query("SELECT a FROM Admin a WHERE a.carnet = :id")
    Optional<Admin> findAdminByCarnet(@Param("id") Long id);
    @Query("SELECT s FROM Socio s WHERE s.carnet = :id")
    Optional<Socio> findSocioByCarnet(@Param("id") Long id);
    @Query("SELECT c FROM Comprador c WHERE c.carnet = :id")
    Optional<Comprador> findCompradorByCarnet(@Param("id") Long id);

    @Query("""
        SELECT NEW reservas.demo.models.dtos.UserSocioDto(
            u.idper,
            u.username,
            u.estado,
            u.email,
            u.password,
            u.role,
            p.nombre,
            p.apellido,
            p.carnet,
            p.numcel,
            p.antiguedad,
            p.marca
        )
        FROM Socio p, Usuario u
        WHERE p.id_persona = u.idper
        AND u.estado = :state
    """)
    List <UserSocioDto> filterUserSocioDto(@Param("state") String state);
    @Query("""
        SELECT NEW reservas.demo.models.dtos.UserAdminDto(
            u.idper,
            u.username,
            u.estado,
            u.email,
            u.password,
            u.role,
            p.nombre,
            p.apellido,
            p.carnet,
            p.numcel,
            p.nivel
        )
        FROM Admin p, Usuario u
        WHERE p.id_persona = u.idper
        AND u.estado = :state
    """)
    List <UserAdminDto> filterUserAdminDto(@Param("state") String state);
    @Query("""
        SELECT NEW reservas.demo.models.dtos.UserCompradorDto(
            u.idper,
            u.username,
            u.estado,
            u.email,
            u.password,
            u.role,
            p.nombre,
            p.apellido,
            p.carnet,
            p.numcel,
            p.fidelidad
        )
        FROM Comprador p, Usuario u
        WHERE p.id_persona = u.idper
        AND u.estado = :state
    """)
    List <UserCompradorDto> filterUserCompDto(@Param("state") String state);
    @Query("""
        SELECT NEW reservas.demo.models.dtos.UserPorteroDto(
            u.idper,
            u.username,
            u.estado,
            u.email,
            u.password,
            u.role,
            p.nombre,
            p.apellido,
            p.carnet,
            p.numcel,
            p.turno
        )
        FROM Portero p, Usuario u
        WHERE p.id_persona = u.idper
        AND u.estado = :state
    """)
    List <UserPorteroDto> filterUserPorteroDto(@Param("state") String state);

    @Query("""
        SELECT NEW reservas.demo.models.dtos.UserAdminDto(
            u.idper,
            u.username,
            u.estado,
            u.email,
            u.password,
            u.role,
            p.nombre,
            p.apellido,
            p.carnet,
            p.numcel,
            p.nivel
        )
        FROM Admin p, Usuario u
        WHERE p.id_persona = u.idper
        AND p.carnet = :num
    """)
    Optional <UserAdminDto> userAdminDtoByCarnet(@Param("num") Long num);
    @Query("""
        SELECT NEW reservas.demo.models.dtos.UserSocioDto(
            u.idper,
            u.username,
            u.estado,
            u.email,
            u.password,
            u.role,
            p.nombre,
            p.apellido,
            p.carnet,
            p.numcel,
            p.antiguedad,
            p.marca
        )
        FROM Socio p, Usuario u
        WHERE p.id_persona = u.idper
        AND p.carnet = :num
    """)
    Optional <UserSocioDto> userSocioDtoByCarnet(@Param("num") Long num);
    @Query("""
        SELECT NEW reservas.demo.models.dtos.UserCompradorDto(
            u.idper,
            u.username,
            u.estado,
            u.email,
            u.password,
            u.role,
            p.nombre,
            p.apellido,
            p.carnet,
            p.numcel,
            p.fidelidad
        )
        FROM Comprador p, Usuario u
        WHERE p.id_persona = u.idper
        AND p.carnet = :num
    """)
    Optional <UserCompradorDto> userCompDtoByCarnet(@Param("num") Long num);
    @Query("""
        SELECT NEW reservas.demo.models.dtos.UserPorteroDto(
            u.idper,
            u.username,
            u.estado,
            u.email,
            u.password,
            u.role,
            p.nombre,
            p.apellido,
            p.carnet,
            p.numcel,
            p.turno
        )
        FROM Portero p, Usuario u
        WHERE p.id_persona = u.idper
        AND p.carnet = :num
    """)
    Optional <UserPorteroDto> userPortDtoByCarnet(@Param("num") Long num);

    @Query("""
        SELECT NEW reservas.demo.models.dtos.UserAdminDto(
            u.idper,
            u.username,
            u.estado,
            u.email,
            u.password,
            u.role,
            p.nombre,
            p.apellido,
            p.carnet,
            p.numcel,
            p.nivel
        )
        FROM Admin p, Usuario u
        WHERE p.id_persona = u.idper
        AND p.id_persona = :num
    """)
    Optional <UserAdminDto> userAdminDtoById(@Param("num") Long num);
    @Query("""
        SELECT NEW reservas.demo.models.dtos.UserSocioDto(
            u.idper,
            u.username,
            u.estado,
            u.email,
            u.password,
            u.role,
            p.nombre,
            p.apellido,
            p.carnet,
            p.numcel,
            p.antiguedad,
            p.marca
        )
        FROM Socio p, Usuario u
        WHERE p.id_persona = u.idper
        AND p.id_persona = :num
    """)
    Optional <UserSocioDto> userSocioDtoById(@Param("num") Long num);
    @Query("""
        SELECT NEW reservas.demo.models.dtos.UserCompradorDto(
            u.idper,
            u.username,
            u.estado,
            u.email,
            u.password,
            u.role,
            p.nombre,
            p.apellido,
            p.carnet,
            p.numcel,
            p.fidelidad
        )
        FROM Comprador p, Usuario u
        WHERE p.id_persona = u.idper
        AND p.id_persona = :num
    """)
    Optional <UserCompradorDto> userCompDtoById(@Param("num") Long num);
    @Query("""
        SELECT NEW reservas.demo.models.dtos.UserPorteroDto(
            u.idper,
            u.username,
            u.estado,
            u.email,
            u.password,
            u.role,
            p.nombre,
            p.apellido,
            p.carnet,
            p.numcel,
            p.turno
        )
        FROM Portero p, Usuario u
        WHERE p.id_persona = u.idper
        AND p.id_persona = :num
    """)
    Optional <UserPorteroDto> userPortDtoById(@Param("num") Long num);

    @Query("""
        SELECT NEW reservas.demo.models.dtos.UserAdminDto(
            u.idper,
            u.username,
            u.estado,
            u.email,
            u.password,
            u.role,
            p.nombre,
            p.apellido,
            p.carnet,
            p.numcel,
            p.nivel
        )
        FROM Admin p, Usuario u
        WHERE p.id_persona = u.idper
        AND u.username = :uname
    """)
    Optional <UserAdminDto> userAdminDtoByUsername(@Param("uname") String uname);
    @Query("""
        SELECT NEW reservas.demo.models.dtos.UserSocioDto(
            u.idper,
            u.username,
            u.estado,
            u.email,
            u.password,
            u.role,
            p.nombre,
            p.apellido,
            p.carnet,
            p.numcel,
            p.antiguedad,
            p.marca
        )
        FROM Socio p, Usuario u
        WHERE p.id_persona = u.idper
        AND u.username = :uname
    """)
    Optional <UserSocioDto> userSocioDtoByUsername(@Param("uname") String uname);
    @Query("""
        SELECT NEW reservas.demo.models.dtos.UserCompradorDto(
            u.idper,
            u.username,
            u.estado,
            u.email,
            u.password,
            u.role,
            p.nombre,
            p.apellido,
            p.carnet,
            p.numcel,
            p.fidelidad
        )
        FROM Comprador p, Usuario u
        WHERE p.id_persona = u.idper
        AND u.username = :uname
    """)
    Optional <UserCompradorDto> userCompDtoByUsername(@Param("uname") String uname);

    // Buscar socios por antigüedad mínima
    List<Socio> findByAntiguedadGreaterThanEqual(Integer antiguedadMinima);

    // Buscar compradores por nivel de fidelidad
    List<Comprador> findByFidelidad(Integer fidelidad);
}
