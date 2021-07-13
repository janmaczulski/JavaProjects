package com.machulski.filler.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TextContent extends Content {
    private String text;
}
