/*
* Ascoltatore per la gestione del menù JComboBox in alto a destra
*/
package Ascoltatori.Header;

import Application.Applicazione;
import Vista.Grafica;
import Database.GuestQuery;
import Database.ListeQuery;
import Vista.LoginPanel;
import Vista.PreferitiPanel;
import Vista.AccountPanel;
import Vista.ListaFacoltàPanel;
import utility.Ordina;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

/**
 *
 * @author Te4o
 */
public class Menù implements ActionListener{
    
    private Applicazione applicazione = Applicazione.getInstance();
    
    //dichiarazione oggetti
    private JComboBox menu;
    private String selezione;
    
    private ListaFacoltàPanel facoltà;
    private AccountPanel account; 
    private PreferitiPanel preferiti; 
    
    public Menù(JComboBox menu) {
        this.menu = menu;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        //inizializzazione variabili
        selezione = menu.getSelectedItem().toString();
        
        switch(selezione){
            
            case "Home": home();
            break;
            case "Account": account();
            break;
            case "Preferiti": preferiti();
            break;
            case "Logout": logout();
            break;
        }
        
        resetMenu();
    }
    
    public void home(){
        
        try {
            applicazione.svuotaTutteLeListe();
            
            ListeQuery.caricaFacoltà();
            ListeQuery.caricaRamiFacoltà();
            
            applicazione.back.add("facoltà");
            
            facoltà = new ListaFacoltàPanel();
            Grafica.card.show(Grafica.container, "facoltà");
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Errore durante il caricamento delle facoltà", "Impossibile completare l'operazione", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void account(){
        
        try {
            GuestQuery.ricaricaDatiUtente();
            } catch (SQLException ex) {
            System.out.println("Errore durante la sincronizzazione dei dati utente");;
        }
            
        applicazione.svuotaMieiDati();

        applicazione.back.add("account");

        account = new AccountPanel();
        Grafica.container.add(account,"account");
        Grafica.card.show(Grafica.container, "account");

    }
    
    public void preferiti(){
        
        try {
            ListeQuery.caricaTuttiPreferiti();
            
            Ordina.ListePreferiti();
            
            applicazione.back.add("preferiti");
            
            preferiti = new PreferitiPanel();
            Grafica.container.add(preferiti, "preferiti");
            Grafica.card.show(Grafica.container, "preferiti");
            
        } catch (SQLException ex) {
            System.out.println("Errore durante il caricamento dei preferiti");
        }
    }
    
    public void logout(){
        
        int showConfirmDialog = JOptionPane.showConfirmDialog(null, "Sei sicuro?", "Logout", JOptionPane.YES_NO_OPTION);
        
        if(showConfirmDialog == 0 ){
            
            Grafica.card.show(Grafica.container, "login");
            LoginPanel.clearForm();
            applicazione.logout();
            applicazione.back.clear();
        }
    }
    
    public void resetMenu() {
        
        menu.setEditable(true);
        menu.setSelectedItem("Menù");
        menu.setEditable(false);
    }
}

