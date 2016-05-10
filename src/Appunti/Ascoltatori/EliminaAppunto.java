/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Appunti.Ascoltatori;

import Appunti.Vista.ListaAppuntiPanel;
import Application.Controller.Applicazione;
import Application.Vista.Grafica;
import Database.Query.ControlloQuery;
import Database.Query.DeleteQuery;
import Database.Query.ListeQuery;
import Dropbox.Elimina;
import com.dropbox.core.DbxException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author te4o
 */
public class EliminaAppunto implements ActionListener{
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        int showConfirmDialog = JOptionPane.showConfirmDialog(null, "Sei sicuro?", "Conferma", JOptionPane.YES_NO_OPTION);
        
        if(showConfirmDialog == 0 ){
            DeleteQuery.eliminaAppunto();
            
            if(ControlloQuery.controlloAppuntiPreferiti()==false){
                DeleteQuery.eliminaAppuntiPreferiti();
            }
        
            try {
                Elimina elimina = new Elimina();
                elimina.del();
            } catch (DbxException ex) {
                Logger.getLogger(EliminaAppunto.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            JOptionPane.showMessageDialog(null, "Appunto eliminato correttamente.", "Eliminazione Confermata", JOptionPane.INFORMATION_MESSAGE);

            Applicazione.svuotaAppunti();

            ListeQuery.caricaAppunti();

            Applicazione.back.remove(Applicazione.back.size()-1);

            ListaAppuntiPanel appunti = new ListaAppuntiPanel();
            Grafica.container.add(appunti, "appunti");
            Grafica.card.show(Grafica.container, "appunti");
        }
        
        
    }
    
}
