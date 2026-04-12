package com.jbes.aa1.controllers;

import com.jbes.aa1.model.Casa;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;


import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class CasaController implements Initializable {

    @FXML
    private TextField txtDueno;
    @FXML
    private TextField txtGatos;
    @FXML
    private TextField txtValoracion;
    @FXML
    private ChoiceBox<String> cbHueco;
    @FXML
    private DatePicker dpFechaInscripcion;

    @FXML
    private Button btnBuscar;
    @FXML
    private Button btnAnadir;
    @FXML
    private Button btnModificar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnLimpiar;


    @FXML
    private TableView<Casa> tableCasa;
    @FXML
    private TableColumn<Casa, String> colDueno;
    @FXML
    private TableColumn<Casa, Integer> colGatos;
    @FXML
    private TableColumn<Casa, Float> colValoracion;
    @FXML
    private TableColumn<Casa, Boolean> colHueco;
    @FXML
    private TableColumn<Casa, LocalDate> colFechaInscripcion;

    @FXML
    private Label lblBarraEstado;

    private ObservableList<Casa> listaCasas = FXCollections.observableArrayList();

    private FilteredList<Casa> listaCasasFiltrada;



    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbHueco.getItems().addAll("Sí", "No");
        cbHueco.setValue("Estado actual");

        colDueno.setCellValueFactory(new PropertyValueFactory<>("dueno"));
        colGatos.setCellValueFactory(new PropertyValueFactory<>("numeroGatos"));
        colValoracion.setCellValueFactory(new PropertyValueFactory<>("valoracion"));
        colHueco.setCellValueFactory(new PropertyValueFactory<>("huecoDisponible"));
        colFechaInscripcion.setCellValueFactory(new PropertyValueFactory<>("fechaIncripcion"));

        listaCasasFiltrada = new FilteredList<>(listaCasas, Predicate -> true);
        tableCasa.setItems(listaCasasFiltrada);

        lblBarraEstado.setText("Sistema iniciado, por favor, haga su búsqueda.");

        colHueco.setCellValueFactory(new PropertyValueFactory<>("huecoDisponible"));

        colHueco.setCellFactory(col -> new TableCell<Casa, Boolean>() {
            @Override
            protected void updateItem(Boolean huecoDisponible, boolean empty) {
                super.updateItem(huecoDisponible, empty);
                if (empty || huecoDisponible == null) {
                    setText(null);
                } else {
                    setText(huecoDisponible ? "Sí" : "No");
                }
            }

        });

        dpFechaInscripcion.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate localDate) {
                if (localDate != null) {
                    return localDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                }
                return null;
            }


            @Override
            public LocalDate fromString(String s) {
                return (LocalDate.parse(s, DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            }
        });

        colFechaInscripcion.setCellFactory(col -> new TableCell<Casa, LocalDate>() {
            @Override
            protected void updateItem(LocalDate fechaInscripcion, boolean empty) {
                super.updateItem(fechaInscripcion, empty);
                if (empty || fechaInscripcion == null) {
                    setText(null);
                } else {
                    setText(fechaInscripcion.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                }
            }
        });

    }

    @FXML
    protected void buscarCasa() {
        String buscarDueno = txtDueno.getText().toLowerCase();
        String buscarGatos = txtGatos.getText();
        String buscarValoracion = txtValoracion.getText();
        String buscarHueco = cbHueco.getValue();
        LocalDate buscarFechaIncripcion = dpFechaInscripcion.getValue();

        listaCasasFiltrada.setPredicate(casa -> {
            if (!buscarDueno.isEmpty() && !casa.getDueno().toLowerCase().contains(buscarDueno)) {
                return false;
            }

            if (!buscarGatos.isEmpty() && !String.valueOf(casa.getNumeroGatos()).contains(buscarGatos)) {
                return false;
            }

            if (!buscarValoracion.isEmpty() && !String.valueOf(casa.getValoracion()).contains(buscarValoracion)) {
                return false;
            }

            if (buscarHueco != null && !buscarHueco.isEmpty()) {
                if (buscarHueco.equals("Al día") && !casa.isHuecoDisponible()) {
                    return false;
                }

                if (buscarHueco.equals("Pendiente") && casa.isHuecoDisponible()) {
                    return false;
                }
            }

            if (buscarFechaIncripcion != null) {
                if (casa.getFechaInscripcion() == null) {
                    return false;
                }

                if (!casa.getFechaInscripcion().equals(buscarFechaIncripcion)) {
                    return false;
                }
            }
            return true;
        });
    }


    @FXML
    protected void anadirCasa() {
        String dueno = txtDueno.getText();
        int numeroGatos = Integer.parseInt(txtGatos.getText());
        float valoracion = Float.parseFloat(txtValoracion.getText().replace(",","."));
        String huecos = cbHueco.getValue();
        boolean huecoDisponible = false;
        if (huecos.equals("Sí")) {
            huecoDisponible = true;
        }
        LocalDate fechaInscripcion = dpFechaInscripcion.getValue();

        Casa casa = new Casa(dueno, numeroGatos, valoracion, huecoDisponible, fechaInscripcion);

        listaCasas.add(casa);

        lblBarraEstado.setText("La casa de " + dueno + " ha sido añadida correctamente.");

        limpiarCasa();
    }

    @FXML
    protected void limpiarCasa() {
        txtDueno.clear();
        txtGatos.clear();
        txtValoracion.clear();
        cbHueco.setValue(null);
        dpFechaInscripcion.setValue(null);
    }

    @FXML
    protected void cargarCasa() {
        Casa casaCargar = tableCasa.getSelectionModel().getSelectedItem();

        if (casaCargar != null) {
            txtDueno.setText(casaCargar.getDueno());
            txtGatos.setText(String.valueOf(casaCargar.getNumeroGatos()));
            txtValoracion.setText(String.valueOf(casaCargar.getValoracion()));
            cbHueco.setValue(casaCargar.isHuecoDisponible() ? "Sí" : "No");
            dpFechaInscripcion.setValue(casaCargar.getFechaInscripcion());
        }
    }

    @FXML
    protected void eliminarCasa() {
        Casa casaCargar = tableCasa.getSelectionModel().getSelectedItem();

        if (casaCargar != null) {
            listaCasas.remove(casaCargar);
            limpiarCasa();
        }
    }

    @FXML
    protected void modificarCasa() {
        Casa casaCargar = tableCasa.getSelectionModel().getSelectedItem();

        if (casaCargar != null) {
            casaCargar.setDueno(txtDueno.getText());
            casaCargar.setNumeroGatos(Integer.parseInt(txtGatos.getText()));
            casaCargar.setValoracion(Float.parseFloat(txtValoracion.getText().replace(",",".")));
            casaCargar.setHuecoDisponible("Al día".equals(cbHueco.getValue()));
            casaCargar.setFechaInscripcion(dpFechaInscripcion.getValue());

            tableCasa.refresh();
            limpiarCasa();
        }
    }



}