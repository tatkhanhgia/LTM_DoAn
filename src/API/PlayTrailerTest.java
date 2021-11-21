package API;

import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;

import javax.swing.*;
import java.awt.*;

public class PlayTrailerTest {
    public static void main(String[] args) {
        NativeInterface.open();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame f = new JFrame("Youtube Video");
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                f.getContentPane().add(getBrowser(), BorderLayout.CENTER);
                f.setSize(700, 1000);
                f.setVisible(true);
            }
        });

        NativeInterface.runEventPump();

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                NativeInterface.close();
            }
        }));

    }

    public static JPanel getBrowser() {
        JPanel wbPanel = new JPanel(new BorderLayout());
        JWebBrowser wb = new JWebBrowser();
        wbPanel.add(wb, BorderLayout.CENTER);
        wb.setBarsVisible(false);
        wb.navigate("https://www.youtube.com/watch?v=KlyknsTJk0w");

        return wbPanel;
    }
}
