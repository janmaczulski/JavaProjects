package com.machulski.filler;

import com.machulski.filler.controller.Actions;
import lombok.extern.java.Log;

import javax.swing.*;

@Log
public class Filler {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
               UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                Actions actions = new Actions();
                actions.control();
            } catch (Exception e) {
                log.severe(e.getMessage());
            }
        });
    }
}
