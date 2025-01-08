/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author user
 */
class Player{
    private String name;

    public String getName() {
        return name;
    }

    public Player(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
class CustomCellView extends ListCell<Player>{
    

    protected void updateItem(Player p, boolean empty) {
        super.updateItem(p, empty); 

        if (empty || p == null) {
            setText(null);
            setGraphic(null);
        }
        else
        {
            setText("hello "+p.getName());
        }
    }
    
}

public class OnlineClientsListController implements Initializable {

    @FXML
    private ImageView homePage;
    @FXML
    private ListView<Player> onlineViewList;
    @FXML
    private Label alertLabel;
    
    ObservableList<Player> onlineList;

    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        onlineList=FXCollections.observableArrayList();
        onlineViewList.setItems(onlineList);
        onlineViewList.setCellFactory(listView->new CustomCellView());
        if(onlineList.isEmpty())
        {
            alertLabel.setVisible(true);
            
           
        }
        
        // TODO
    }    

    @FXML
    private void navigateToHome(MouseEvent event) {
        onlineList.add(new Player("Mustafa"));
        alertLabel.setVisible(false);
    }
    
    
}
