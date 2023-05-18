package in.reqres.lombok;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Book {

    private Integer id;

    private String name;

    private Integer year;

    private String color;

    @JsonProperty("pantone_value")
    private String pantoneValue;

}