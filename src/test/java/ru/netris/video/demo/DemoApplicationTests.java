package ru.netris.video.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import ru.netris.video.demo.Aggregator.DataAggregator;
import ru.netris.video.demo.Controller.VideoSourceController;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DemoApplicationTests {
    static Logger log = LoggerFactory.getLogger("TEST");

    @Test
    public void whenExceptionThrown_thenAssertionSucceeds() {
        Exception exception = assertThrows(Exception.class, () -> {
            new DataAggregator(new ObjectMapper()).aggregateData("htp://www.mocky.io/v2/5c51b9dd3400003252129fb5");
        });
    }

    @Test
    public void whenExceptionThrown_thenAssertionFailed() {
        assertDoesNotThrow(() -> {
            new DataAggregator(new ObjectMapper()).aggregateData("http://www.mocky.io/v2/5c51b9dd3400003252129fb5"); }
        );
    }

    @Test
    public void whenJsonMatches_theAssertionSucceeds() {
        assertEquals(new DataAggregator(new ObjectMapper()).aggregateData("http://www.mocky.io/v2/5c51b9dd3400003252129fb5").toString(),
                "[{\"id\":1,\"urlType\":\"LIVE\",\"videoUrl\":\"rtsp://127.0.0.1/1\",\"value\":\"fa4b588e-249b-11e9-ab14-d663bd873d93\"," +
                        "\"ttl\":120},{\"id\":2,\"urlType\":\"LIVE\",\"videoUrl\":\"rtsp://127.0.0.1/20\",\"value\":\"fa4b5f64-249b-11e9-ab14-d663bd873d93\"," +
                        "\"ttl\":180},{\"id\":3,\"urlType\":\"ARCHIVE\",\"videoUrl\":\"rtsp://127.0.0.1/3\",\"value\":\"fa4b5d52-249b-11e9-ab14-d663bd873d93\"," +
                        "\"ttl\":120},{\"id\":20,\"urlType\":\"ARCHIVE\",\"videoUrl\":\"rtsp://127.0.0.1/2\",\"value\":\"fa4b5b22-249b-11e9-ab14-d663bd873d93\",\"ttl\":60}]");
    }
}
