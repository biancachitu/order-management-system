package run;

import bll.ClientBll;
import model.Client;
import presentation.MainMenu;

import javax.swing.*;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    protected static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    public static void main(String[] args) throws SQLException {
        MainMenu menu = new MainMenu();
        menu.setLocationRelativeTo(null);
        menu.setVisible(true);
    }
}
