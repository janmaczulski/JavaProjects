package org.example.api.impl02;

import org.example.api.LogService;

public class LogStdOut implements LogService {
    @Override
    public void execute(String file) {
        System.out.println(file);
    }
}
