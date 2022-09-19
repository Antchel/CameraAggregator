package ru.netris.video.demo.WebScrapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.netris.video.demo.DAO.NodeBean;
import ru.netris.video.demo.DAO.NodeSourceUrl;
import ru.netris.video.demo.DAO.NodeTokenUrl;

public interface IVideoWebScrapper {
    NodeSourceUrl getSourceDataUrl(NodeBean node, ObjectMapper mapper);

    NodeTokenUrl getSTokenDataUrl(NodeBean node, ObjectMapper mapper);
}
