package Utils;

import java.awt.Color;

public class Node {

    private int posX;
    private int posY;
    private Color color;

    public Node(int posX, int posY, Color color){
        this.posX = posX;
        this.posY = posY;
        this.color = color;
    }

    @Override
    public String toString(){
        return "{ pos( "+ this.posX + ", " + this.posY +" ), color(" + this.color.getRed() + ", " + this.color.getGreen() + ", " + this.color.getBlue() + ") }";
    }

    public int getPosX() {
        return this.posX;
    }

    public int getPosY() {
        return this.posY;
    }

    public Color getColor() {
        return this.color;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
