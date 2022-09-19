package ru.netris.video.demo.Controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.netris.video.demo.Aggregator.DataAggregator;
import ru.netris.video.demo.TimeTrack.TimeTracker;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.net.URL;

@RestController
@Slf4j
@RequestMapping("/cameras")
public class VideoSourceController {

    private final String SOURCE_URL = "http://www.mocky.io/v2/5c51b9dd3400003252129fb5";
    private final ObjectMapper mapper;
    private final DataAggregator aggregator;

    public VideoSourceController() {
        mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        aggregator = new DataAggregator(mapper);
    }


    @SneakyThrows
    @GetMapping("/available")
    @TimeTracker
    ResponseEntity<?> getListAvailableCameras() {
        return new ResponseEntity<>(aggregator.aggregateData(SOURCE_URL), HttpStatus.OK);
    }
}
