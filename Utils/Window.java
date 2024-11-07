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

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class Window {

    private static JPanel panel;
    private static JFrame frame;
    private static JLabel label;
    private static String text = " ";
    static BufferedImage image;

    private JButton openImageButton;
    private JButton saveButton;
    private JButton writeButton;
    private JButton createNewImageButton;
    private JButton showHiddenText;

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
        panel.add(label);
        panel.revalidate();
        panel.repaint();
        frame.revalidate();
        frame.repaint();
    }

    public static void removePreviousImageFromPanel() {
        if (Window.label == null) {
            System.out.println("EdittorFrame.JLabel is null!");
            return;
        }
        JLabel component = Window.label;
        panel.remove(component);
        panel.revalidate();
        panel.repaint();
    }

    public Window() {
        panel = new JPanel();
        openImageButton = new JButton("Open");
        writeButton = new JButton("Open text area");
        saveButton = new JButton("Save");
        createNewImageButton = new JButton("Create image");
        showHiddenText = new JButton("Show hidden text");

        panel.add(openImageButton);
        panel.add(writeButton);
        panel.add(saveButton);
        panel.add(createNewImageButton);
        panel.add(showHiddenText);

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
                // EncodingImage.loadImage(image);
                // EncodingImage.encode(EncodingImage.loadImage(image), TextToBinary.asciiArrayToBinaryArray(TextToBinary.getAsciiArray(text)));
            }
        });

        writeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                text = JOptionPane.showInputDialog("Input the text: ");
                text = text.trim();
                if(text == null || text.equals(" ")){
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

                            label.setText("Image saved successfully!");
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

        createNewImageButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(Window.text == null || Window.text.equals(" ")) return;
                EncodingImage.createNewImage(TextToBinary.asciiArrayToBinaryArray(TextToBinary.getAsciiArray(text)), "new");
            }
            
        });

        showHiddenText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                if(Window.image == null) return;
                String text = BinaryToText.binaryToText(DecodingImage.parseImageToData(Window.image, 20, 20));
                // System.out.println("The hidded text is: " + text);
                JOptionPane.showMessageDialog(null, text);
            }
        });

        frame = new JFrame("Digital Stenography");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);
        frame.setLocationRelativeTo(null);
        frame.add(panel);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
