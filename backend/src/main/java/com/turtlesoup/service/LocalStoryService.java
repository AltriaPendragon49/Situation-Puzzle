package com.turtlesoup.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LocalStoryService {

    private static final String STORY_LIBRARY_PATH = "stories/story-library.json";
    private Map<String, Object> storyLibrary;

    public LocalStoryService() {
        loadStoryLibrary();
    }

    private void loadStoryLibrary() {
        try {
            ClassPathResource resource = new ClassPathResource(STORY_LIBRARY_PATH);
            ObjectMapper mapper = new ObjectMapper();
            storyLibrary = mapper.readValue(resource.getInputStream(), Map.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load story library", e);
        }
    }

    public List<Map<String, Object>> getStoriesByType(String type) {
        List<Map<String, Object>> stories = (List<Map<String, Object>>) storyLibrary.get("stories");
        return stories.stream()
                .filter(story -> story.get("type").equals(type))
                .collect(Collectors.toList());
    }

    public List<Map<String, Object>> getStoriesByDifficulty(String difficulty) {
        List<Map<String, Object>> stories = (List<Map<String, Object>>) storyLibrary.get("stories");
        return stories.stream()
                .filter(story -> story.get("difficulty").equals(difficulty))
                .collect(Collectors.toList());
    }

    public Optional<Map<String, Object>> getStoryById(String id) {
        List<Map<String, Object>> stories = (List<Map<String, Object>>) storyLibrary.get("stories");
        return stories.stream()
                .filter(story -> story.get("id").equals(id))
                .findFirst();
    }

    public Map<String, Object> getMetadata() {
        return (Map<String, Object>) storyLibrary.get("metadata");
    }
}