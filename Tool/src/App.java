import com.formdev.flatlaf.FlatLightLaf;
import ui.UI;

import java.security.*;
import java.util.Base64;

public class App {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        FlatLightLaf.setup();
        UI ui= new UI();
        ui.setVisible(true);
    }
}
