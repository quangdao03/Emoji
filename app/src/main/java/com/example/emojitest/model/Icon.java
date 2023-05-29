package com.example.emojitest.model;

public class Icon {
    public String iconPath;


    public Icon(String stickerpath, String s) {
        this.iconPath = stickerpath;

    }

    public String getStickerpath() {
        return iconPath;
    }

    public void setStickerpath(String stickerpath) {
        this.iconPath = stickerpath;
    }

    @Override
    public String toString() {
        return "Icon{" +
                "stickerpath='" + iconPath + '\'' +
                '}';
    }
}
