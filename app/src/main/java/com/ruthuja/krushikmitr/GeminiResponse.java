package com.ruthuja.krushikmitr;

import java.util.List;

public class GeminiResponse {
    private List<Candidate> candidates;

    public String getContent() {
        if (candidates != null && !candidates.isEmpty()) {
            return candidates.get(0).content.parts.get(0).text;
        }
        return "No response received.";
    }

    static class Candidate {
        Content content;
    }

    static class Content {
        List<Part> parts;
    }

    static class Part {
        String text;
    }
}
