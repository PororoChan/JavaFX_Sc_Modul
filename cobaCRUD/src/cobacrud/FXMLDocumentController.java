package cobacrud;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author ASUS
 */
public class FXMLDocumentController implements Initializable {
   
    Connection con = Config.koneksi();
    PreparedStatement ps;
    
    @FXML
    private RadioButton laki, perem;
    @FXML
    private TextField dt_nama, dt_npm;
    @FXML
    private Button reset, simpan;
    @FXML
    private TableView<ModelTable> tblView;
    @FXML 
    private TableColumn<ModelTable, String> col_no;
    @FXML 
    private TableColumn<ModelTable, String> col_nama;
    @FXML 
    private TableColumn<ModelTable, String> col_gender;
    @FXML 
    private TableColumn<ModelTable, String> col_npm;
    
    ObservableList<ModelTable> list = FXCollections.observableArrayList();
    
    
    public void btn(ActionEvent event){
       if(simpan.getText().equals("Simpan")){
           simpan();
       } else if(simpan.getText().equals("Update")){
           update();
       }
    }
    
    private void simpan(){
        String nama = dt_nama.getText().toString();
        String npm = dt_npm.getText().toString();
        String gender = "";
        if(laki.isSelected()){
            gender = "Laki-Laki";
        } else if(perem.isSelected()){
            gender = "Perempuan";
        }
        
        try {
            ps = con.prepareStatement("INSERT INTO dt_mhsiswa(`nama_mhsiswa`,`gender`,`npm`) "
                    + "VALUES(?,?,?)");
            ps.setString(1, nama);
            ps.setString(2, gender);
            ps.setString(3, npm);
            ps.execute();
            Alert a = new Alert(AlertType.INFORMATION);
            a.setTitle("Informasi Database");
            a.setHeaderText(null);
            a.setContentText("Data Berhasil Ditambahkan!!");
            a.showAndWait();
            tblView.getItems().clear();
            load_table();
            reset();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    private void load_table(){
        col_no.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_nama.setCellValueFactory(new PropertyValueFactory<>("nama"));
        col_gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        col_npm.setCellValueFactory(new PropertyValueFactory<>("npm"));
        
        try {
            Connection con = Config.koneksi();
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM dt_mhsiswa");
            
            while(rs.next()){
                list.add(new ModelTable(rs.getString("id_mhsiswa"), rs.getString("nama_mhsiswa"), 
                        rs.getString("gender"), rs.getString("npm")));
            }
            tblView.setItems(list);
        } catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    
    public void btnEdit(ActionEvent event){
        try {
            TableView.TableViewSelectionModel<ModelTable> selectionModel = tblView.getSelectionModel();
            selectionModel.setSelectionMode(SelectionMode.SINGLE);

            ObservableList<ModelTable> selectedItems = selectionModel.getSelectedItems();
            ModelTable tb = tblView.getSelectionModel().getSelectedItem();
            
            String nama = tb.getNama();
            String gender = tb.getGender();
            String npm = tb.getNpm();
            
            dt_nama.setText(nama);
            if(gender.equals("Laki-Laki")){
                laki.setSelected(true);
            } else if(gender.equals("Perempuan")){
                perem.setSelected(true);
            }
            dt_npm.setText(npm);
            simpan.setText("Update");
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private void update(){
        String nma = dt_nama.getText().toString();
        String npm = dt_npm.getText().toString();
        String gender = "";
        if(laki.isSelected()){
            gender = "Laki-Laki";
        } else if(perem.isSelected()){
            gender = "Perempuan";
        }
        
        try {
            TableView.TableViewSelectionModel<ModelTable> selectionModel = tblView.getSelectionModel();
            selectionModel.setSelectionMode(SelectionMode.SINGLE);

            ObservableList<ModelTable> selectedItems = selectionModel.getSelectedItems();
            ModelTable tb = tblView.getSelectionModel().getSelectedItem();
            
            String id = tb.getId();
            
            String up = "UPDATE dt_mhsiswa SET nama_mhsiswa='"+nma+"', "
                    + "gender='"+gender+"', npm='"+npm+"' WHERE id_mhsiswa = '"+id+"'";
            
            ps = con.prepareStatement(up);
            ps.execute();
            tblView.getItems().clear();
            load_table();
            
            Alert a = new Alert(AlertType.INFORMATION);
            a.setTitle("Informasi Database");
            a.setHeaderText(null);
            a.setContentText("Data Berhasil di Update!!");
            a.showAndWait();
        } catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    
    public void delete(ActionEvent event){
        try {
            TableView.TableViewSelectionModel<ModelTable> selectionModel = tblView.getSelectionModel();
            selectionModel.setSelectionMode(SelectionMode.SINGLE);

            ObservableList<ModelTable> selectedItems = selectionModel.getSelectedItems();
            ModelTable tb = tblView.getSelectionModel().getSelectedItem();
            
            String id = tb.getId();
            String del = "DELETE FROM dt_mhsiswa WHERE id_mhsiswa='"+id+"'";
            
            ps = con.prepareStatement(del);
            ps.execute();
            Alert a = new Alert(AlertType.INFORMATION);
            a.setTitle("Informasi Database");
            a.setHeaderText(null);
            a.setContentText("Data berhasil dihapus!!");
            a.showAndWait();
            tblView.getItems().clear();
            load_table();
        } catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    
    public void rst(ActionEvent event){
        reset();
    }
    
    public void reset(){
        dt_nama.setText("");
        dt_npm.setText("");
        laki.setSelected(false);
        perem.setSelected(false);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        load_table();
    }    
    
}
