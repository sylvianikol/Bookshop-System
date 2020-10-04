package com.softuni.springintroex.io;

import com.softuni.springintroex.io.interfaces.OutputWriter;

public class ConsoleWriter implements OutputWriter {
    @Override
    public void writeLine(String text) {
        System.out.println(text);
    }
}
