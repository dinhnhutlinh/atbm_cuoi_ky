import com.formdev.flatlaf.FlatLightLaf;
import ui.UI;

import java.security.*;

public class App {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        FlatLightLaf.setup();
        UI ui= new UI();
        ui.setVisible(true);
    }
}
