package com.machulski.filler.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.awt.*;

@Data
@AllArgsConstructor
public class ImageContent extends Content {
    private Image image;
}
