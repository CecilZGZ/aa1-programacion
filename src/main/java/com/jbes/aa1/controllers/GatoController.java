package com.jbes.aa1.controllers;

import com.jbes.aa1.model.Gato;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class GatoController {


    private ObservableList<Gato> listaGatos = FXCollections.observableArrayList();

    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtChip;
    @FXML
    private TextField txtPeso;
    @FXML
    private ChoiceBox<String> cbVacunacion;
    @FXML
    private DatePicker dpFechaNacimiento;

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
    private TableView<Gato> tableGato;
    @FXML
    private TableColumn<Gato, String> colNombre;
    @FXML
    private TableColumn<Gato, Integer> colChip;
    @FXML
    private TableColumn<Gato, Float> colPeso;
    @FXML
    private TableColumn<Gato, Boolean> colVacunacion;
    @FXML
    private TableColumn<Gato, LocalDate> colFechaNacimiento;

    @FXML
    private Label lblBarraEstado;



    @FXML
    public void initialize() {
        cbVacunacion.getItems().addAll("Al día", "Pendiente");
        cbVacunacion.setValue("Estado actual");

        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colChip.setCellValueFactory(new PropertyValueFactory<>("chip"));
        colPeso.setCellValueFactory(new PropertyValueFactory<>("peso"));
        colVacunacion.setCellValueFactory(new PropertyValueFactory<>("vacunado"));
        colFechaNacimiento.setCellValueFactory(new PropertyValueFactory<>("fechaNacimiento"));

        tableGato.setItems(listaGatos);

        lblBarraEstado.setText("Sistema iniciado, por favor, haga su búsqueda.");

        colVacunacion.setCellValueFactory(new PropertyValueFactory<>("vacunado"));

        colVacunacion.setCellFactory(col -> new TableCell<Gato, Boolean>() {
            @Override
            protected void updateItem(Boolean vacunado, boolean empty) {
                super.updateItem(vacunado, empty);
                if (empty || vacunado == null) {
                    setText(null);
                } else {
                    setText(vacunado ? "Al día" : "Pendiente");
                }
            }

        });

        dpFechaNacimiento.setConverter(new StringConverter<LocalDate>() {
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

        colFechaNacimiento.setCellFactory(col -> new TableCell<Gato, LocalDate>() {
            @Override
            protected void updateItem(LocalDate fechaNacimiento, boolean empty) {
                super.updateItem(fechaNacimiento, empty);
                if (empty || fechaNacimiento == null) {
                    setText(null);
                } else {
                    setText(fechaNacimiento.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                }
            }
        });
    }

    @FXML
    protected void anadirGato() {
        String nombre = txtNombre.getText();
        int chip = Integer.parseInt(txtChip.getText());
        float peso = Float.parseFloat(txtPeso.getText().replace(",","."));
        String vacunacion = cbVacunacion.getValue();
        boolean vacunado = false;
        if (vacunacion.equals("Al día")) {
            vacunado = true;
        }
        LocalDate fechaNacimiento = dpFechaNacimiento.getValue();

        Gato gato = new Gato(nombre, chip, peso, vacunado, fechaNacimiento);

        listaGatos.add(gato);

        lblBarraEstado.setText("El gato/a " + nombre + " ha sido añadido/a correctamente.");

        limpiarGato();
    }

    @FXML
    protected void limpiarGato() {
        txtNombre.clear();
        txtChip.clear();
        txtPeso.clear();
        cbVacunacion.setValue(null);
        dpFechaNacimiento.setValue(null);
    }



}
