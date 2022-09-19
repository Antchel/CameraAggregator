package ru.netris.video.demo.WebScrapper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import ru.netris.video.demo.DAO.NodeBean;
import ru.netris.video.demo.DAO.NodeSourceUrl;
import ru.netris.video.demo.DAO.NodeTokenUrl;

import java.net.URL;

@Component
public class BaseVideoScrapper implements IVideoWebScrapper {
    @SneakyThrows
    @Override
    public NodeSourceUrl getSourceDataUrl(NodeBean node, ObjectMapper mapper) {
        JsonNode data = mapper.readTree(new URL(node.getSourceDataUrl()));
        return mapper.readValue(data.toString(), new TypeReference<>() {
        });
    }

    @SneakyThrows
    @Override
    public NodeTokenUrl getSTokenDataUrl(NodeBean node, ObjectMapper mapper) {
        JsonNode data = mapper.readTree(new URL(node.getTokenDataUrl()));
        return mapper.readValue(data.toString(), new TypeReference<>() {
        });
    }
}
