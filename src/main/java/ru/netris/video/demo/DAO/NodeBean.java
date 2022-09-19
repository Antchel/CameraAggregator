package ru.netris.video.demo.DAO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties
@Data
public class NodeBean {
    private int id;
    private String sourceDataUrl;
    private String tokenDataUrl;
}
