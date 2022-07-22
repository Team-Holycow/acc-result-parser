package de.teamholycow.acc.resultserver;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.teamholycow.acc.resultserver.model.json.JsonResult;
import de.teamholycow.acc.resultserver.model.penalty.LeaguePenalty;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
public class JsonParser {
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    public JsonResult parse(String fileName) throws IOException {
        return parse(fileName, true);
    }

    public JsonResult parse(String fileName, boolean createReadableJson) throws IOException {
        BufferedReader reader = Files.newBufferedReader(Paths.get(fileName), StandardCharsets.UTF_16LE);
        Writer writer = createReadableJson ?
                Files.newBufferedWriter(Paths.get(fileName.replace("json", "aencoded.json"))) :
                StringWriter.nullWriter();

        StringBuilder buffer = new StringBuilder();

        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
            writer.write(line);
        }

        reader.close();
        writer.close();

        JsonResult jsonResult = objectMapper.readValue(buffer.toString(), JsonResult.class);

        log.info("{}", jsonResult);

        return jsonResult;
    }

    public List<LeaguePenalty> parseLeaguePenalties(String filename) throws IOException {
        List<LeaguePenalty> penalties = objectMapper.readValue(new File(filename), new TypeReference<>() {
        });

        log.info("{}", penalties);

        return penalties;
    }
}
