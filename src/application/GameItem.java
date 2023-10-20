/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package application;

/**
 *
 * @author fiske
 */
public interface GameItem {
    public String getImage();
    public void handleCollision(GameItem other);
}
