package ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle;
import javax.swing.filechooser.FileNameExtensionFilter;

public class UI extends JFrame {
    private String pathFile = "";
    private String pathKey = "";
    private String signature = "";
    private String pathSignature = "";
    public UI() {
        initComponents();
        setDefault();
    }
    private void setDefault(){
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
    }

    public void selectFileToSign() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select file to sign");
        int option = fileChooser.showOpenDialog(null);
        if (option == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            pathFile = file.getAbsolutePath();
        } else {
            pathFile = "";
        }
        fileInput.setText(pathFile);
    }

    public void selectKeyToSign() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select your key");
        int option = fileChooser.showOpenDialog(null);
        if (option == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            pathKey = file.getAbsolutePath();
        } else {
            pathKey = "";
        }
        keyInput.setText(pathKey);
    }
    public void sign() throws NoSuchAlgorithmException, SignatureException, IOException, InvalidKeySpecException, NoSuchProviderException, InvalidKeyException {
        String text1 = fileInput.getText();
        String text2 = keyInput.getText();

        File file = new File(text1);
        File key = new File(text2);
        if (!file.exists() || !file.isFile()) {
            JOptionPane.showMessageDialog(null,"Enter file to sign");
            return;
        }
        if (!key.exists() || !key.isFile()) {
            JOptionPane.showMessageDialog(null,"Enter your key");
            return;
        }

        SignFile signFile = new SignFile();
        signature = signFile.signFile(text1, text2);
        signArea.setText(signature);
        saveBtn.setEnabled(true);
    }

    public void clear() {
        pathFile = "";
        pathKey = "";
        signature = "";
        fileInput.setText(pathFile);
        keyInput.setText(pathKey);
        signArea.setText(signature);
        saveBtn.setEnabled(false);
    }

    public void saveSignature() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify a file to save signature");
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("*.txt", "txt"));
        fileChooser.setAcceptAllFileFilterUsed(false);
        int option = fileChooser.showSaveDialog(null);
        if (option == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            pathSignature = file.getAbsolutePath();
        }

        int index = pathSignature.lastIndexOf(".txt");
        if (index < 0) pathSignature = pathSignature + ".txt";

        SignFile signFile = new SignFile();
        try {
            signFile.saveSignature(pathSignature, signature);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    private void initComponents() {
        label1 = new JLabel();
        label2 = new JLabel();
        fileInput = new JTextField();
        keyInput = new JTextField();
        selectFileBtn = new JButton();
        selectKeyBtn = new JButton();
        signBtn = new JButton();
        clearBtn = new JButton();
        scrollPane1 = new JScrollPane();
        signArea = new JTextArea();
        label3 = new JLabel();
        saveBtn = new JButton();

        //======== this ========
        setTitle("Signature digital tool");
        Container contentPane = getContentPane();

        //---- label1 ----
        label1.setText("Select file to sign");
        label1.setAutoscrolls(true);

        //---- label2 ----
        label2.setText("Select your key");
        label2.setMaximumSize(new Dimension(89, 16));

        //---- fileInput ----
        fileInput.setAlignmentX(0.0F);
        fileInput.setMaximumSize(new Dimension(89, 16));
        fileInput.setEditable(false);
        fileInput.setEnabled(false);
        fileInput.setBackground(Color.white);

        //---- keyInput ----
        keyInput.setMaximumSize(new Dimension(89, 16));
        keyInput.setEditable(false);
        keyInput.setEnabled(false);
        keyInput.setBackground(Color.white);

        //---- selectFileBtn ----
        selectFileBtn.setText("Browser");
        selectFileBtn.setHorizontalAlignment(SwingConstants.LEADING);
        selectFileBtn.setAutoscrolls(true);
        selectFileBtn.setMaximumSize(new Dimension(89, 16));

        //---- selectKeyBtn ----
        selectKeyBtn.setText("Browser");
        selectKeyBtn.setHorizontalAlignment(SwingConstants.LEADING);
        selectKeyBtn.setMaximumSize(new Dimension(89, 16));

        //---- signBtn ----
        signBtn.setText("Sign");
        signBtn.setMaximumSize(new Dimension(89, 16));

        //---- clearBtn ----
        clearBtn.setText("Clear");
        clearBtn.setMaximumSize(new Dimension(89, 16));

        //======== scrollPane1 ========
        {

            //---- signArea ----
            signArea.setEditable(false);
            signArea.setBackground(Color.white);
            signArea.setLineWrap(true);
            scrollPane1.setViewportView(signArea);
        }

        //---- label3 ----
        label3.setText("Signature");

        //---- saveBtn ----
        saveBtn.setText("Save signature");
        saveBtn.setEnabled(false);

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
                contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(contentPaneLayout.createParallelGroup()
                                        .addGroup(contentPaneLayout.createSequentialGroup()
                                                .addGroup(contentPaneLayout.createParallelGroup()
                                                        .addGroup(contentPaneLayout.createSequentialGroup()
                                                                .addGroup(contentPaneLayout.createParallelGroup()
                                                                        .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                                                .addComponent(label1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                .addComponent(label2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                                        .addComponent(signBtn, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addGroup(contentPaneLayout.createParallelGroup()
                                                                        .addGroup(contentPaneLayout.createSequentialGroup()
                                                                                .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                                                        .addComponent(fileInput, GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE)
                                                                                        .addComponent(keyInput, GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE))
                                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                                .addGroup(contentPaneLayout.createParallelGroup()
                                                                                        .addComponent(selectFileBtn, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                                                        .addComponent(selectKeyBtn, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                                                                        .addComponent(clearBtn, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                                                        .addComponent(label3))
                                                .addGap(0, 1, Short.MAX_VALUE))
                                        .addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 396, Short.MAX_VALUE)
                                        .addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
                                                .addGap(0, 324, Short.MAX_VALUE)
                                                .addComponent(saveBtn)))
                                .addContainerGap())
        );
        contentPaneLayout.setVerticalGroup(
                contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(label1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(fileInput, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(selectFileBtn, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(label2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(keyInput, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(selectKeyBtn, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(signBtn, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(clearBtn, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(label3)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 155, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(saveBtn)
                                .addGap(8, 8, 8))
        );
        selectFileBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectFileToSign();
            }
        });
        selectKeyBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectKeyToSign();
            }
        });
        signBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    sign();
                } catch (NoSuchAlgorithmException | SignatureException | IOException | InvalidKeySpecException |
                        NoSuchProviderException | InvalidKeyException ex) {
                    ex.printStackTrace();
                }
            }
        });
        clearBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               clear();
            }
        });
        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               saveSignature();
            }
        });
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - Ph?m Anh Tu?n
    private JLabel label1;
    private JLabel label2;
    private JTextField fileInput;
    private JTextField keyInput;
    private JButton selectFileBtn;
    private JButton selectKeyBtn;
    private JButton signBtn;
    private JButton clearBtn;
    private JScrollPane scrollPane1;
    private JTextArea signArea;
    private JLabel label3;
    private JButton saveBtn;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}

