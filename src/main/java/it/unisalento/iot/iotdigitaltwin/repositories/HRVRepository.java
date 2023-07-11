package it.unisalento.iot.iotdigitaltwin.repositories;

import it.unisalento.iot.iotdigitaltwin.domain.HRV;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface HRVRepository extends MongoRepository<HRV, String> {

    List<HRV> findAllByUsernameAtleta(String username);
}
