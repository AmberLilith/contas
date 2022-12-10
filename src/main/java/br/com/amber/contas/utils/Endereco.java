package br.com.amber.contas.utils;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Endereco {

    private String thoroughfareType;

    private String name;

    private Integer number;

    private String complement;

    private String district;

    private String city;

    private String state;

    private String country;

    @Override
    public String toString() {
        return "{'thoroughfareType':'" + thoroughfareType +
                "', 'name':'" + name +
                "', 'number=" + number +
                "', 'complement':'" + complement +
                "', 'district':'" + district +
                "', 'city':'" + city +
                "', 'state':'" + state +
                "', 'country':'" + country +
                "}";
    }
}
