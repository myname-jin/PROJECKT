/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package notice;

/**
 *
 * @author adsd3
 */
import java.awt.*;
import java.awt.image.BufferedImage;

public class NotificationService {
    private TrayIcon trayIcon;

    public NotificationService() {
        if (!SystemTray.isSupported()) return;
        try {
            SystemTray tray = SystemTray.getSystemTray();
            BufferedImage img = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
            trayIcon = new TrayIcon(img, "공지 알림");
            trayIcon.setImageAutoSize(true);
            tray.add(trayIcon);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void push(String title, String message) {
        if (trayIcon != null) {
            trayIcon.displayMessage(title, message, TrayIcon.MessageType.INFO);
        }
    }
}
