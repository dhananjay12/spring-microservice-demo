package com.mynotes.spring.cloud.eureka;

public class FileData {

    private int id;

    private String text;

    public FileData(int id, String text) {
        super();
        this.id = id;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

}
