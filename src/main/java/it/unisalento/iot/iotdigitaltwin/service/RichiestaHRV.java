package it.unisalento.iot.iotdigitaltwin.service;

import it.unisalento.iot.iotdigitaltwin.domain.HRV;
import it.unisalento.iot.iotdigitaltwin.dto.HRVDTO;

import java.util.List;

public class RichiestaHRV {

    List<HRVDTO> lista_hrv;

    public List<HRVDTO> getLista_hrv() {
        return lista_hrv;
    }

    public void setLista_hrv(List<HRVDTO> lista_hrv) {
        this.lista_hrv = lista_hrv;
    }
}
