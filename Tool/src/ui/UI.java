
package ui;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
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
    public void signFile() throws NoSuchAlgorithmException, SignatureException, IOException, InvalidKeySpecException, NoSuchProviderException, InvalidKeyException {
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

    public void signText() throws NoSuchAlgorithmException, SignatureException, IOException, InvalidKeySpecException, NoSuchProviderException, InvalidKeyException {
        String text1 = textInput.getText();
        String text2 = keyInput.getText();
        File key = new File(text2);
        if (text1.equals("")) {
            JOptionPane.showMessageDialog(null,"Enter text to sign");
            return;
        }

        if (!key.exists() || !key.isFile()) {
            JOptionPane.showMessageDialog(null,"Enter your key");
            return;
        }

        SignFile signFile = new SignFile();
        signature = signFile.signText(text1, text2);
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
        textInput.setText("");
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
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - unknown
        label2 = new JLabel();
        fileInput = new JTextField();
        selectFileBtn = new JButton();
        label3 = new JLabel();
        scrollPane1 = new JScrollPane();
        textInput = new JTextArea();
        label4 = new JLabel();
        keyInput = new JTextField();
        selectKeyBtn = new JButton();
        scrollPane2 = new JScrollPane();
        signArea = new JTextArea();
        signFileBtn = new JButton();
        clearBtn = new JButton();
        label5 = new JLabel();
        signTextBtn = new JButton();
        saveBtn = new JButton();

        //======== this ========
        setTitle("Signature digital tool");
        var contentPane = getContentPane();

        //---- label2 ----
        label2.setText("Select file to sign:");

        //---- fileInput ----
        fileInput.setEnabled(false);
        fileInput.setEditable(false);
        fileInput.setBackground(Color.white);

        //---- selectFileBtn ----
        selectFileBtn.setText("Browser");

        //---- label3 ----
        label3.setText("Or enter text to sign:");

        //======== scrollPane1 ========
        {
            scrollPane1.setViewportView(textInput);
        }

        //---- label4 ----
        label4.setText("Select your key:");

        //---- keyInput ----
        keyInput.setEditable(false);
        keyInput.setEnabled(false);
        keyInput.setBackground(Color.white);

        //---- selectKeyBtn ----
        selectKeyBtn.setText("Browser");

        //======== scrollPane2 ========
        {

            //---- signArea ----
            signArea.setEditable(false);
            signArea.setLineWrap(true);
            signArea.setBackground(Color.white);
            scrollPane2.setViewportView(signArea);
        }

        //---- signFileBtn ----
        signFileBtn.setText("Sign file");

        //---- clearBtn ----
        clearBtn.setText("Clear");

        //---- label5 ----
        label5.setText("Signature");

        //---- signTextBtn ----
        signTextBtn.setText("Sign text");

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
                                        .addComponent(label5)
                                        .addGroup(contentPaneLayout.createSequentialGroup()
                                                .addComponent(signFileBtn)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(signTextBtn)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(clearBtn))
                                        .addGroup(contentPaneLayout.createSequentialGroup()
                                                .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(label2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(label3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addGap(3, 3, 3)
                                                .addComponent(fileInput, GroupLayout.PREFERRED_SIZE, 283, GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(selectFileBtn))
                                        .addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 487, GroupLayout.PREFERRED_SIZE)
                                        .addGroup(contentPaneLayout.createSequentialGroup()
                                                .addComponent(label4, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
                                                .addGap(3, 3, 3)
                                                .addComponent(keyInput, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(selectKeyBtn))
                                        .addComponent(scrollPane2, GroupLayout.PREFERRED_SIZE, 487, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(saveBtn)
                                .addContainerGap())
        );
        contentPaneLayout.setVerticalGroup(
                contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(label2, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(fileInput, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(selectFileBtn, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(label3, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(contentPaneLayout.createParallelGroup()
                                        .addComponent(label4, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(keyInput, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(selectKeyBtn, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(signFileBtn)
                                        .addComponent(signTextBtn)
                                        .addComponent(clearBtn))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(label5)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(scrollPane2, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(saveBtn)
                                .addContainerGap())
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
        signFileBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    signFile();
                } catch (NoSuchAlgorithmException | SignatureException | IOException | InvalidKeySpecException |
                        NoSuchProviderException | InvalidKeyException ex) {
                    ex.printStackTrace();
                }
            }
        });
        signTextBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    signText();
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
    // Generated using JFormDesigner Evaluation license - unknown
    private JLabel label2;
    private JTextField fileInput;
    private JButton selectFileBtn;
    private JLabel label3;
    private JScrollPane scrollPane1;
    private JTextArea textInput;
    private JLabel label4;
    private JTextField keyInput;
    private JButton selectKeyBtn;
    private JScrollPane scrollPane2;
    private JTextArea signArea;
    private JButton signFileBtn;
    private JButton clearBtn;
    private JLabel label5;
    private JButton signTextBtn;
    private JButton saveBtn;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
