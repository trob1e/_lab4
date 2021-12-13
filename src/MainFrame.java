import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.ArrayList;

public class MainFrame extends JFrame {
    private boolean fileLoaded = false;
    private GraphicsDisplay display = new GraphicsDisplay();
    private JCheckBoxMenuItem showAxisMenuItem;
    private JCheckBoxMenuItem showMarkersMenuItem;
    private JMenuItem resetGraphicsMenuItem;
    private JMenuItem saveToTextMenuItem;
    private JFileChooser fileChooser = null;

    public MainFrame() {
        super("Вывод графика функции");
        this.setSize(600, 600);
        Toolkit kit = Toolkit.getDefaultToolkit();
        this.setLocation((kit.getScreenSize().width - 600) / 2, (kit.getScreenSize().height - 600) / 2);
        JMenuBar menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);
        JMenu fileMenu = new JMenu("Файл");
        menuBar.add(fileMenu);

        Action openGraphicsAction = new AbstractAction("Открыть файл") {
            public void actionPerformed(ActionEvent arg0) {
                if (MainFrame.this.fileChooser == null) {
                    MainFrame.this.fileChooser = new JFileChooser();
                    MainFrame.this.fileChooser.setCurrentDirectory(new File("."));
                }

                if (MainFrame.this.fileChooser.showOpenDialog(MainFrame.this) == 0) {
                }

                MainFrame.this.openGraphics(MainFrame.this.fileChooser.getSelectedFile());
            }
        };
        fileMenu.add(openGraphicsAction);

        Action saveToTextAction = new AbstractAction("Сохранить в .txt") {
            public void actionPerformed(ActionEvent arg0) {
                if (MainFrame.this.fileChooser == null) {
                    MainFrame.this.fileChooser = new JFileChooser();
                    MainFrame.this.fileChooser.setCurrentDirectory(new File("."));
                }

                if (MainFrame.this.fileChooser.showSaveDialog(MainFrame.this) == 0) {
                    MainFrame.this.display.saveToTextFile(MainFrame.this.fileChooser.getSelectedFile());
                }

            }
        };
        this.saveToTextMenuItem = fileMenu.add(saveToTextAction);



        JMenu graphicsMenu = new JMenu("График");
        menuBar.add(graphicsMenu);

        Action showAxisAction = new AbstractAction("Показать оси координат") {
            public void actionPerformed(ActionEvent e) {
                MainFrame.this.display.setShowAxis(MainFrame.this.showAxisMenuItem.isSelected());
            }
        };
        this.showAxisMenuItem = new JCheckBoxMenuItem(showAxisAction);
        graphicsMenu.add(this.showAxisMenuItem);
        this.showAxisMenuItem.setSelected(true);
        Action showMarkersAction = new AbstractAction("Показать маркеры точек") {
            public void actionPerformed(ActionEvent e) {
                MainFrame.this.display.setShowMarkers(MainFrame.this.showMarkersMenuItem.isSelected());
            }
        };
        this.showMarkersMenuItem = new JCheckBoxMenuItem(showMarkersAction);
        graphicsMenu.add(this.showMarkersMenuItem);
        this.showMarkersMenuItem.setSelected(true);
        graphicsMenu.addSeparator();
        Action resetGraphicsAction = new AbstractAction("Отменить все изменения") {
            public void actionPerformed(ActionEvent event) {
                MainFrame.this.display.reset();
            }
        };
        this.resetGraphicsMenuItem = new JMenuItem(resetGraphicsAction);
        graphicsMenu.add(this.resetGraphicsMenuItem);
        this.resetGraphicsMenuItem.setEnabled(false);
        graphicsMenu.addMenuListener(new MainFrame.GraphicsMenuListener());
        this.getContentPane().add(this.display, "Center");
    }

    private class GraphicsMenuListener implements MenuListener {
        private GraphicsMenuListener() {
        }

        public void menuCanceled(MenuEvent arg0) {
        }

        public void menuDeselected(MenuEvent arg0) {
        }

        public void menuSelected(MenuEvent arg0) {
            MainFrame.this.showAxisMenuItem.setEnabled(MainFrame.this.fileLoaded);
            MainFrame.this.showMarkersMenuItem.setEnabled(MainFrame.this.fileLoaded);
            MainFrame.this.saveToTextMenuItem.setEnabled(MainFrame.this.fileLoaded);

        }
    }

    protected void openGraphics(File selectedFile) {
        try {
            DataInputStream in = new DataInputStream(new FileInputStream(selectedFile));
            ArrayList graphicsData = new ArrayList(50);

            while(in.available() > 0) {
                Double x = in.readDouble();
                Double y = in.readDouble();
                graphicsData.add(new Double[]{x, y});
            }

            if (graphicsData.size() > 0) {
                this.fileLoaded = true;
                this.resetGraphicsMenuItem.setEnabled(true);
                this.display.showGraphics(graphicsData);
            }
        } catch (FileNotFoundException var6) {
        } catch (IOException var7) {
        }
    }

    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        frame.setDefaultCloseOperation(3);
        frame.setVisible(true);
    }

}