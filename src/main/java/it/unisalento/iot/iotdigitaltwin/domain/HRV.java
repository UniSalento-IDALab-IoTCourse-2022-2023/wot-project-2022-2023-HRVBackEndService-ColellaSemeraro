package it.unisalento.iot.iotdigitaltwin.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document("HRV")
public class HRV {

    @Id
    String id;

    String usernameAtleta;

    double median_nni;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    LocalDate data;

    int valorePredetto;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsernameAtleta() {
        return usernameAtleta;
    }

    public void setUsernameAtleta(String usernameAtleta) {
        this.usernameAtleta = usernameAtleta;
    }

    public double getMedian_nni() {
        return median_nni;
    }

    public void setMedian_nni(double median_nni) {
        this.median_nni = median_nni;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public int getValorePredetto() {
        return valorePredetto;
    }

    public void setValorePredetto(int valorePredetto) {
        this.valorePredetto = valorePredetto;
    }
}
