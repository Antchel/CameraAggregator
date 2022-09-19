package ru.netris.video.demo.DAO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties
public class NodeTokenUrl {
    private String value;
    private int ttl;
}
