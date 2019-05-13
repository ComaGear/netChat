package com.tomcat.netChat.web.resources;

public class LayoutInitialize {

    private boolean top;
    private boolean left;
    private boolean footer;
    private boolean right;

    public boolean isTop() {
        return top;
    }

    public void setTop(boolean top) {
        this.top = top;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isFooter() {
        return footer;
    }

    public void setFooter(boolean footer) {
        this.footer = footer;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public LayoutInitialize(boolean top, boolean left, boolean footer, boolean right) {
        this.top = top;
        this.left = left;
        this.footer = footer;
        this.right = right;
    }
}
