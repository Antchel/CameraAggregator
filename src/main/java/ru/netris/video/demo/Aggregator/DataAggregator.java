package ru.netris.video.demo.Aggregator;

import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.SneakyThrows;
import ru.netris.video.demo.DAO.NodeBean;
import ru.netris.video.demo.DAO.NodeSourceUrl;
import ru.netris.video.demo.DAO.NodeTokenUrl;
import ru.netris.video.demo.TimeTrack.TimeTracker;
import ru.netris.video.demo.WebScrapper.BaseVideoScrapper;
import ru.netris.video.demo.WebScrapper.IVideoWebScrapper;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DataAggregator {
    private final ObjectMapper mapper;
    private final IVideoWebScrapper scrapper;

    private final ExecutorService WORKER_THREAD_POOL
            = Executors.newCachedThreadPool();

    public DataAggregator(ObjectMapper objectMapper) {
        this.mapper = objectMapper;
        this.scrapper = new BaseVideoScrapper();
    }

    @SneakyThrows
    public ArrayNode aggregateData(String url) {
        JsonNode data = mapper.readTree(new URL(url));
        List<NodeBean> jsonElements = mapper.readValue(data.toString(), new TypeReference<>() {
        });
        int size = jsonElements.size() * 2;
        Map<Integer, NodeSourceUrl> src = new ConcurrentHashMap<>(size);
        Map<Integer, NodeTokenUrl> tkn = new ConcurrentHashMap<>(size);
        CountDownLatch latch = new CountDownLatch(size);

        for (var el : jsonElements) {
            WORKER_THREAD_POOL.submit(() -> {
                src.put(el.getId(), scrapper.getSourceDataUrl(el, mapper));
                latch.countDown();
            });
            WORKER_THREAD_POOL.submit(() -> {
                tkn.put(el.getId(), scrapper.getSTokenDataUrl(el, mapper));
                latch.countDown();
            });
        }
        latch.await();

        return collectJson(src, tkn);
    }

    private ArrayNode collectJson(Map<Integer, NodeSourceUrl> src, Map<Integer, NodeTokenUrl> tkn) {
        ArrayNode arrayNode = mapper.createArrayNode();
        for (var el : src.keySet()) {
            ObjectNode jsonNode = mapper.createObjectNode();
            jsonNode.put("id", el);
            if (src.get(el) != null) jsonNode.put("urlType", src.get(el).getUrlType());
            if (src.get(el) != null) jsonNode.put("videoUrl", src.get(el).getVideoUrl());
            if (tkn.get(el) != null) jsonNode.put("value", tkn.get(el).getValue());
            if (tkn.get(el) != null) jsonNode.put("ttl", tkn.get(el).getTtl());
            arrayNode.add(jsonNode);
        }
        return arrayNode;
    }
}
