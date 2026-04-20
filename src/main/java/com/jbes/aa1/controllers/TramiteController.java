package com.jbes.aa1.controllers;

import com.jbes.aa1.model.Casa;
import com.jbes.aa1.model.Tramite;
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

public class TramiteController implements Initializable {

    @FXML
    private TextField txtTipo;
    @FXML
    private TextField txtVeterinaria;
    @FXML
    private TextField txtCoste;
    @FXML
    private ChoiceBox<String> cbCitaFijada;
    @FXML
    private DatePicker dpFechaCita;

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
    private TableView<Tramite> tableTramite;
    @FXML
    private TableColumn<Tramite, String> colTipo;
    @FXML
    private TableColumn<Tramite, String> colVeterinaria;
    @FXML
    private TableColumn<Tramite, Float> colCoste;
    @FXML
    private TableColumn<Tramite, Boolean> colCitaFijada;
    @FXML
    private TableColumn<Tramite, LocalDate> colFechaCita;

    @FXML
    private Label lblBarraEstado;

    private ObservableList<Tramite> listaTramites = FXCollections.observableArrayList();

    private FilteredList<Tramite> listaTramitesFiltrada;



    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbCitaFijada.getItems().addAll("Sí", "No");

        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colVeterinaria.setCellValueFactory(new PropertyValueFactory<>("veterinaria"));
        colCoste.setCellValueFactory(new PropertyValueFactory<>("coste"));
        colCitaFijada.setCellValueFactory(new PropertyValueFactory<>("tieneCita"));
        colFechaCita.setCellValueFactory(new PropertyValueFactory<>("fechaCita"));

        colCoste.setCellFactory(col -> new TableCell<Tramite, Float>() {
            @Override
            protected void updateItem(Float item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null || item == -1.0f) {
                    setText(null);
                } else {
                    setText(String.valueOf(item));
                }
            }
        });

        listaTramitesFiltrada = new FilteredList<>(listaTramites, Predicate -> true);
        tableTramite.setItems(listaTramitesFiltrada);

        lblBarraEstado.setText("Sistema iniciado, por favor, haga su búsqueda.");

        colCitaFijada.setCellValueFactory(new PropertyValueFactory<>("tieneCita"));

        colCitaFijada.setCellFactory(col -> new TableCell<Tramite, Boolean>() {
            @Override
            protected void updateItem(Boolean tieneCita, boolean empty) {
                super.updateItem(tieneCita, empty);
                if (empty || tieneCita == null) {
                    setText(null);
                } else {
                    setText(tieneCita ? "Sí" : "No");
                }
            }

        });

        dpFechaCita.setConverter(new StringConverter<LocalDate>() {
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

        colFechaCita.setCellFactory(col -> new TableCell<Tramite, LocalDate>() {
            @Override
            protected void updateItem(LocalDate fechaCita, boolean empty) {
                super.updateItem(fechaCita, empty);
                if (empty || fechaCita == null) {
                    setText(null);
                } else {
                    setText(fechaCita.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                }
            }
        });

    }

    @FXML
    protected void buscarTramite() {
        String buscarTipo = txtTipo.getText().toLowerCase();
        String buscarVeterinaria = txtVeterinaria.getText().toLowerCase();
        String buscarCoste = txtCoste.getText();
        String buscarTieneCita = cbCitaFijada.getValue();
        LocalDate buscarFechaCita = dpFechaCita.getValue();

        listaTramitesFiltrada.setPredicate(tramite -> {
            if (!buscarTipo.isEmpty() && !tramite.getTipo().toLowerCase().contains(buscarTipo)) {
                return false;
            }

            if (!buscarVeterinaria.isEmpty() && !String.valueOf(tramite.getVeterinaria()).contains(buscarVeterinaria)) {
                return false;
            }

            if (!buscarCoste.isEmpty() && !String.valueOf(tramite.getCoste()).contains(buscarCoste)) {
                return false;
            }

            if (buscarTieneCita != null && !buscarTieneCita.isEmpty()) {
                if (buscarTieneCita.equals("Sí") && !tramite.isTieneCita()) {
                    return false;
                }

                if (buscarTieneCita.equals("No") && tramite.isTieneCita()) {
                    return false;
                }
            }

            if (buscarFechaCita != null) {
                if (tramite.getFechaCita() == null) {
                    return false;
                }

                if (!tramite.getFechaCita().equals(buscarFechaCita)) {
                    return false;
                }
            }
            return true;
        });

        lblBarraEstado.setText("Búsqueda completada.");
    }


    @FXML
    protected void anadirTramite() {
        if (validadorCamposObligatorios()) {
            String tipo = txtTipo.getText();
            String veterinaria = txtVeterinaria.getText();
            float coste = -1.0f;
            if (!txtCoste.getText().trim().isEmpty()) {
                Float.parseFloat(txtCoste.getText().replace(",", "."));
            }
            String citaFijada = cbCitaFijada.getValue();
            boolean tieneCita = false;
            if (citaFijada.equals("Sí")) {
                tieneCita = true;
            }
            LocalDate fechaCita = dpFechaCita.getValue();

            Tramite tramite = new Tramite(tipo, veterinaria, coste, tieneCita, fechaCita);

            listaTramites.add(tramite);

            lblBarraEstado.setText("El trámite " + tipo + " ha sido añadido correctamente.");

            limpiarTramite();
        }
    }

    @FXML
    protected void limpiarTramite() {
        txtTipo.clear();
        txtVeterinaria.clear();
        txtCoste.clear();
        cbCitaFijada.setValue(null);
        dpFechaCita.setValue(null);
    }

    @FXML
    protected void cargarTramite() {
        Tramite tramiteCargar = tableTramite.getSelectionModel().getSelectedItem();

        if (tramiteCargar != null) {
            txtTipo.setText(tramiteCargar.getTipo());
            txtVeterinaria.setText(String.valueOf(tramiteCargar.getVeterinaria()));
            txtCoste.setText(tramiteCargar.getCoste() == -1.0f ? "" : String.valueOf(tramiteCargar.getCoste()));
            cbCitaFijada.setValue(tramiteCargar.isTieneCita() ? "Sí" : "No");
            dpFechaCita.setValue(tramiteCargar.getFechaCita());
        }

        lblBarraEstado.setText((tramiteCargar.getTipo()) + " ha sido seleccionado/a");
    }

    @FXML
    protected void eliminarTramite() {
        Tramite tramiteCargar = tableTramite.getSelectionModel().getSelectedItem();

        if (tramiteCargar != null) {
            listaTramites.remove(tramiteCargar);
            limpiarTramite();
        }

        lblBarraEstado.setText((tramiteCargar.getTipo()) + " ha sido eliminado/a de la lista.");
    }

    @FXML
    protected void modificarTramite() {
        Tramite tramiteCargar = tableTramite.getSelectionModel().getSelectedItem();

        if (validadorCamposObligatorios()) {
            if (tramiteCargar != null) {

                float coste = -1.0f;
                if (!txtCoste.getText().trim().isEmpty()) {
                    coste = Float.parseFloat(txtCoste.getText().replace(",", "."));
                }
                tramiteCargar.setTipo(txtTipo.getText());
                tramiteCargar.setVeterinaria(txtVeterinaria.getText());
                tramiteCargar.setCoste(coste);
                tramiteCargar.setTieneCita("Sí".equals(cbCitaFijada.getValue()));
                tramiteCargar.setFechaCita(dpFechaCita.getValue());

                tableTramite.refresh();
                limpiarTramite();

                lblBarraEstado.setText("El trámite " + (tramiteCargar.getTipo()) + " ha sido modificado.");
            }


        }


    }

    @FXML
    protected boolean validadorCamposObligatorios() {
        String mensajeError = "";
        if (txtTipo.getText() == null || txtTipo.getText().trim().isEmpty()) {
            mensajeError += "Es obligatorio introducir un trámite. ";
        }
        if (cbCitaFijada.getValue() == null) {
            mensajeError += "Es obligatorio indicar si existe una cita fijada.";
        }
        if (mensajeError.isEmpty()) {
            return true;
        } else {
            lblBarraEstado.setText(mensajeError);
            return false;
        }
    }



}