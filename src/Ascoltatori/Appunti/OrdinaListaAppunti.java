/*
* Ascoltatore dedicato all'ordinamento della lista degli appunti.
* Possibile ordinamento in base al nome oppure alla valutazione.
* Una volta ordinato, viene ricreato il pannello con la lista ordinata.
*/
package Ascoltatori.Appunti;

import Grafica.Grafica;
import Grafica.ListaAppuntiPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;

/**
 *
 * @author Te4o
 */
public class OrdinaListaAppunti implements ActionListener{
    
    //dichiarazione oggetti
    private JComboBox ordina;
    
    //dichiarazione variabili
    public static String ordineCorrente = "";
    
    private ListaAppuntiPanel appunti;
    
    public OrdinaListaAppunti(JComboBox ordina) {
        this.ordina = ordina;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if(ordina.getSelectedItem().equals("Valutazione")){
            Utility.Ordina.Appunti();
            ordineCorrente = "Valutazione";
        }
        
        if(ordina.getSelectedItem().equals("Nome")){
            Utility.Ordina.AppuntiAlfabetic();
            ordineCorrente = "Nome";
        }
        
        appunti = new ListaAppuntiPanel();
        Grafica.container.add(appunti, "appunti");
        Grafica.card.show(Grafica.container, "appunti");
    }
    
}
