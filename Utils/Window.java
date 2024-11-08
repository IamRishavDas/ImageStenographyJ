package Utils;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Constants.Constants;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class Window {

    private static JPanel panel;
    private static JPanel consolePanel;
    protected static JLabel consoleLabel;

    private static JFrame frame;
    private static JLabel label;

    private static String text = " ";
    static BufferedImage image;

    private JButton openImageButton;
    private JButton saveButton;
    private JButton writeButton;
    private JButton encodeButton;
    private JButton decodeButton;
    private JButton createNewImageButton;
    private JButton showHiddenText;
    private JButton showHiddenTextInNewFile;

    public static void displayImage(BufferedImage img) {
        if (img == null)
            return;

        int panelWidth = panel.getWidth();
        int panelHeight = panel.getHeight();
        int imageWidth = img.getWidth();
        int imageHeight = img.getHeight();

        double scaleFactor = Math.min((double) panelWidth / imageWidth, (double) panelHeight / imageHeight);

        int scaledWidth = (int) (imageWidth * scaleFactor);
        int scaledHeight = (int) (imageHeight * scaleFactor);

        ImageIcon scaledIcon = new ImageIcon(img.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH));

        label = new JLabel(scaledIcon);
        label.setPreferredSize(new Dimension(scaledWidth, scaledHeight));
        panel.add(label/* , BorderLayout.CENTER */);

        panel.revalidate();
        panel.repaint();
        frame.revalidate();
        frame.repaint();
    }

    public static void removePreviousImageFromPanel() {
        if (Window.label == null) {
            // consoleLabel.setText("EdittorFrame.JLabel is null!");
            return;
        }
        JLabel component = Window.label;
        panel.remove(component);
        panel.revalidate();
        panel.repaint();
    }

    public Window() {
        panel = new JPanel();
        consolePanel = new JPanel();
        consoleLabel = new JLabel("First write a text which will be hidded in any image..");
        openImageButton = new JButton("Open");
        writeButton = new JButton("Write Text");
        saveButton = new JButton("Save");
        encodeButton = new JButton("Encode");
        decodeButton = new JButton("Decode");
        createNewImageButton = new JButton("Create image");
        showHiddenText = new JButton("Show hidden text");
        showHiddenTextInNewFile = new JButton("Show hidden text in new file");

        panel.add(openImageButton);
        panel.add(writeButton);
        panel.add(saveButton);
        panel.add(encodeButton);
        panel.add(decodeButton);
        panel.add(createNewImageButton);
        panel.add(showHiddenText);
        panel.add(showHiddenTextInNewFile);

        consolePanel.add(consoleLabel);

        consolePanel.setBackground(Color.GRAY);

        consoleLabel.setFont(new Font("Cascadia Mono", Font.LAYOUT_LEFT_TO_RIGHT, 16));
        consoleLabel.setForeground(Color.BLACK);

        openImageButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Open Image");
                int userSelection = fileChooser.showOpenDialog(null);

                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    try {
                        File fileToOpen = fileChooser.getSelectedFile();
                        image = ImageIO.read(fileToOpen);
                        removePreviousImageFromPanel();
                        displayImage(image);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        writeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                text = JOptionPane.showInputDialog("Input the text: ");
                text = text.trim();
                if (text == null || text.equals(" ")) {
                    JOptionPane.showMessageDialog(null, "Not a valid text");
                }
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Window.image == null || Window.label == null)
                    return;
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Save Image");
                int userSelection = fileChooser.showSaveDialog(null);

                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    JLabel label = Window.label;

                    if (label.getIcon() != null) {
                        try {
                            Image image;
                            if (label.getIcon() instanceof ImageIcon) {
                                image = ((ImageIcon) label.getIcon()).getImage();
                            } else {
                                consoleLabel.setText("Unsupported image format in label");
                                throw new UnsupportedOperationException("Unsupported image format in label");
                            }

                            BufferedImage bufferedImage;
                            if (!(image instanceof BufferedImage)) {
                                bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null),
                                        BufferedImage.TYPE_INT_ARGB);
                                Graphics2D g2 = bufferedImage.createGraphics();
                                g2.drawImage(image, 0, 0, null);
                                g2.dispose();
                            } else {
                                bufferedImage = (BufferedImage) image;
                            }

                            File fileToSave = fileChooser.getSelectedFile();
                            ImageIO.write(bufferedImage, "png", fileToSave);

                            consoleLabel.setText("Image saved successfully!");
                        } catch (IOException ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(null, "Error saving image: " + ex.getMessage(), "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        } catch (UnsupportedOperationException ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(null, "Image format in label not yet supported",
                                    "Unsupported Image", JOptionPane.INFORMATION_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "No image found in the label to save", "No Image",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });

        encodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Window.label == null || text == null) return;
                // EncodingImage.loadImage(image); 
                EncodingImage.encode(EncodingImage.loadImage(image), TextToBinary.asciiArrayToBinaryArray(TextToBinary.getAsciiArray(text)));
            }
        });


        decodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Window.label == null || text == null) return;
                String text = DecodingImage.decodeImage(image);
                // JOptionPane.showMessageDialog(null, text);
            }
        });


        createNewImageButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (Window.text == null || Window.text.equals(" "))
                    return;
                BufferedImage image = EncodingImage.createNewImage(TextToBinary.asciiArrayToBinaryArray(TextToBinary.getAsciiArray(text)),
                        "new");
                Window.image = image; // updating the current Window.image state with new image
                Window.displayImage(image);
            }

        });

        showHiddenText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Window.image == null){
                    Window.consoleLabel.setText("Window.image is null!!");
                    return;
                }
                String text = BinaryToText.binaryToText(
                        DecodingImage.parseImageToData(Window.image, Constants.RECT_WIDTH, Constants.RECT_HEIGHT));
                JOptionPane.showMessageDialog(null, text);
            }
        });

        showHiddenTextInNewFile.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (Window.image == null)
                    return;
                String text = BinaryToText.binaryToText(
                        DecodingImage.parseImageToData(Window.image, Constants.RECT_WIDTH, Constants.RECT_HEIGHT));
                CreateFile.createFile(JOptionPane.showInputDialog("Name the file with extension: "), text);
            }

        });

        frame = new JFrame("Digital Photo Stenography");
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);
        frame.setLocationRelativeTo(null);
        frame.add(panel, BorderLayout.CENTER);
        frame.add(consolePanel, BorderLayout.SOUTH);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
