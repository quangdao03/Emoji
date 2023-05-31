package com.example.emojitest.model;

public class Background {
    private String bg;

    public Background(String stickerpath, String s) {
        this.bg = stickerpath;

    }

    public Background(String bg) {
        this.bg = bg;
    }

    public String getBg() {
        return bg;
    }

    public void setBg(String bg) {
        this.bg = bg;
    }
}
