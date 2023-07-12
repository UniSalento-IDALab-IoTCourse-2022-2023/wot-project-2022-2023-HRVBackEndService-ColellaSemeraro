package it.unisalento.iot.iotdigitaltwin.restcontrollers;

import it.unisalento.iot.iotdigitaltwin.domain.HRV;
import it.unisalento.iot.iotdigitaltwin.dto.HRVDTO;
import it.unisalento.iot.iotdigitaltwin.repositories.HRVRepository;
import it.unisalento.iot.iotdigitaltwin.service.APICalls;
import it.unisalento.iot.iotdigitaltwin.service.RichiestaUsernameAtleti;
import it.unisalento.iot.iotdigitaltwin.service.RoleResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/hrv")
public class HRVRestController {

    @Autowired
    HRVRepository hrvRepository;

    @Autowired
    APICalls apiCalls;

    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public HRVDTO creazione(HttpServletRequest request, @RequestBody HRVDTO hrvdto){

        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String jwt = authorizationHeader.substring(7);
            RoleResponse roleResponse = getRoleResponse(jwt);

            // Verifica se l'autenticazione è valida
            if (roleResponse != null && !Objects.equals(roleResponse.getRole(), "Authentication failed")) {
                // Esegui le operazioni del metodo solo se l'autenticazione è valida

                // Esempio: Controlla se l'utente ha il ruolo necessario per eseguire questa operazione
                if (roleResponse.getRole().equals("AMMINISTRATORE")) {

                    HRV hrv = new HRV();

                    hrv.setUsernameAtleta(hrvdto.getUsernameAtleta());
                    hrv.setMedian_nni(hrvdto.getMedian_nni());
                    hrv.setData(hrvdto.getData());
                    hrv.setValorePredetto(hrvdto.getValorePredetto());

                    hrvRepository.save(hrv);

                    hrv.setId(hrv.getId());

                    return hrvdto;
                }else{
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Allenamento già esistente");
                }
            }else {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Accesso negato");
            }
        }else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Autenticazione fallita");
        }
    }

    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public List<HRVDTO> trovaTutti(HttpServletRequest request, @RequestBody String usernameAtleta){

        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String jwt = authorizationHeader.substring(7);
            RoleResponse roleResponse = getRoleResponse(jwt);

            // Verifica se l'autenticazione è valida
            if (roleResponse != null && !Objects.equals(roleResponse.getRole(), "Authentication failed")) {
                // Esegui le operazioni del metodo solo se l'autenticazione è valida

                // Esempio: Controlla se l'utente ha il ruolo necessario per eseguire questa operazione
                if (roleResponse.getRole().equals("COACH") || roleResponse.getRole().equals("ATLETA")) {

                    List<HRVDTO> hrvList = new ArrayList<>();

                    for (HRV hrv : hrvRepository.findAllByUsernameAtleta(usernameAtleta)){

                        HRVDTO hrvdto = new HRVDTO();

                        hrvdto.setId(hrv.getId());
                        hrvdto.setData(hrv.getData());
                        hrvdto.setUsernameAtleta(hrv.getUsernameAtleta());
                        hrvdto.setMedian_nni(hrv.getMedian_nni());
                        hrvdto.setValorePredetto(hrv.getValorePredetto());

                        hrvList.add(hrvdto);
                    }

                    return hrvList;

                }else {
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Accesso negato");
                }
            }else {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Autenticazione fallita");
            }
        }
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Accesso negato");
    }


    @RequestMapping(value = "/findById/{id}", method = RequestMethod.GET)
    public HRVDTO trovaPerId(HttpServletRequest request, @PathVariable String id){

        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String jwt = authorizationHeader.substring(7);
            RoleResponse roleResponse = getRoleResponse(jwt);

            // Verifica se l'autenticazione è valida
            if (roleResponse != null && !Objects.equals(roleResponse.getRole(), "Authentication failed")) {
                // Esegui le operazioni del metodo solo se l'autenticazione è valida

                // Esempio: Controlla se l'utente ha il ruolo necessario per eseguire questa operazione
                if (roleResponse.getRole().equals("COACH") || roleResponse.getRole().equals("ATLETA")) {

                    HRVDTO hrvdto = new HRVDTO();
                    Optional<HRV> optHrv = hrvRepository.findById(id);

                    if (optHrv.isPresent()){
                        HRV hrv = optHrv.get();

                        hrvdto.setId(hrv.getId());
                        hrvdto.setData(hrv.getData());
                        hrvdto.setUsernameAtleta(hrv.getUsernameAtleta());
                        hrvdto.setMedian_nni(hrv.getMedian_nni());
                        hrvdto.setValorePredetto(hrv.getValorePredetto());
                    }

                    return hrvdto;
                }else {
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Accesso negato");
                }
            }else {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Autenticazione fallita");
            }
        }
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Accesso negato");
    }

    @PostMapping(value = "/findAllByAtleti", consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<HRVDTO> getAllByAtleti(HttpServletRequest request, @RequestBody RichiestaUsernameAtleti richiestaUsernameAtleti) {

        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String jwt = authorizationHeader.substring(7);
            RoleResponse roleResponse = getRoleResponse(jwt);

            // Verifica se l'autenticazione è valida
            if (roleResponse != null && !Objects.equals(roleResponse.getRole(), "Authentication failed")) {
                // Esegui le operazioni del metodo solo se l'autenticazione è valida

                // Esempio: Controlla se l'utente ha il ruolo necessario per eseguire questa operazione
                if (roleResponse.getRole().equals("COACH") || roleResponse.getRole().equals("ATLETA")) {

                    List<HRVDTO> hrvdtos = new ArrayList<>();

                    for(String usernameAtleta: richiestaUsernameAtleti.getUsernameAtleti()) {
                        for(HRV hrv : hrvRepository.findAllByUsernameAtleta(usernameAtleta)) {

                            HRVDTO hrvdto = new HRVDTO();

                            hrvdto.setId(hrv.getId());
                            hrvdto.setData(hrv.getData());
                            hrvdto.setUsernameAtleta(hrv.getUsernameAtleta());
                            hrvdto.setMedian_nni(hrv.getMedian_nni());
                            hrvdto.setValorePredetto(hrv.getValorePredetto());

                            hrvdtos.add(hrvdto);

                        }
                    }

                    return hrvdtos;

                } else {
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Accesso negato");
                }
            }else {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Autenticazione fallita");
            }
        }
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Accesso negato");

    }

    private RoleResponse getRoleResponse(String jwt) {
        return apiCalls.checkRole(jwt);
    }
}
