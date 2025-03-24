package com.ruthuja.krushikmitr;

import java.util.Collections;
import java.util.List;

public class GeminiRequest {
    private List<Content> contents;

    public GeminiRequest(String userInput) {
        this.contents = Collections.singletonList(new Content(userInput));
    }

    static class Content {
        private List<Part> parts;

        Content(String text) {
            this.parts = Collections.singletonList(new Part(text));
        }
    }

    static class Part {
        private String text;

        Part(String text) {
            this.text = text;
        }
    }
}
