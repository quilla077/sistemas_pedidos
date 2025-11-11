package reservas.demo.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reservas.demo.models.dtos.UserAdminDto;
import reservas.demo.models.dtos.UserCompradorDto;
import reservas.demo.models.dtos.UserPorteroDto;
import reservas.demo.models.dtos.UserSocioDto;
import reservas.demo.models.entitys.Admin;
import reservas.demo.models.entitys.Comprador;
import reservas.demo.models.entitys.Persona;
import reservas.demo.models.entitys.Socio;
import reservas.demo.repository.PersonaRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonaServicesImpl {
    private final PersonaRepository personaRepository;

    public List<Persona> listarTodasPersonas(){
        return personaRepository.findAll();
    }
    public Persona guardarPersona(Persona persona) {
        return personaRepository.save(persona);
    }
    public Persona obtenerPersona(Long idper){
        return personaRepository.getReferenceById(idper);
    }


    public List<UserAdminDto> filtrarUserAdminDtos(String state) {
        return personaRepository.filterUserAdminDto(state);
    }
    public List<UserSocioDto> filtrarUserSocioDtos(String state) {
        return personaRepository.filterUserSocioDto(state);
    }
    public List<UserCompradorDto> filtrarUserCompradorDtos(String state) {
        return personaRepository.filterUserCompDto(state);
    }
    public List<UserPorteroDto> filtrarUserPorteroDtos(String state) {
        return personaRepository.filterUserPorteroDto(state);
    }

    public Optional<UserAdminDto> obtenerAdminPor(String criterio,Long num) {
        return switch (criterio.toLowerCase()) {
            case "carnet" -> personaRepository.userAdminDtoByCarnet(num);
            case "id" -> personaRepository.userAdminDtoById(num);
            default -> Optional.empty();
        };
    }
    public Optional<UserSocioDto> obtenerSocioPor(String criterio,Long num) {
        return switch (criterio.toLowerCase()) {
            case "carnet" -> personaRepository.userSocioDtoByCarnet(num);
            case "id" -> personaRepository.userSocioDtoById(num);
            default -> Optional.empty();
        };
    }
    public Optional<UserCompradorDto> obtenerCompradorPor(String criterio,Long num) {
//        return "carnet".equals(criterio)
//                ? personaRepository.findCompradorByCarnet(num)
//                : personaRepository.findCompradorById(num);
        return switch (criterio.toLowerCase()) {
            case "carnet" -> personaRepository.userCompDtoByCarnet(num);
            case "id" -> personaRepository.userCompDtoById(num);
            default -> Optional.empty();
        };
    }
    public Optional<UserPorteroDto> obtenerPorteroPor(String criterio,Long num) {
        return switch (criterio.toLowerCase()) {
            case "carnet" -> personaRepository.userPortDtoByCarnet(num);
            case "id" -> personaRepository.userPortDtoById(num);
            default -> Optional.empty();
        };
    }

    public List<Socio> obtenerTodosSocios() {
        return personaRepository.findAllSocios();
    }
    public List<Comprador> obtenerTodosCompradores() {
        return personaRepository.findAllCompradores();
    }
    public List<Admin> obtenerTodosAdmins() {
        return personaRepository.findAllAdmins();
    }
}
