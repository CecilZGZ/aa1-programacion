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


        colDueno.setCellValueFactory(new PropertyValueFactory<>("dueno"));
        colGatos.setCellValueFactory(new PropertyValueFactory<>("numeroGatos"));
        colValoracion.setCellValueFactory(new PropertyValueFactory<>("valoracion"));
        colHueco.setCellValueFactory(new PropertyValueFactory<>("huecoDisponible"));
        colFechaInscripcion.setCellValueFactory(new PropertyValueFactory<>("fechaInscripcion"));

        colGatos.setCellFactory(col -> new TableCell<Casa, Integer>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null || item == -1) {
                    setText(null);
                } else {
                    setText(String.valueOf(item));
                }
            }
        });

        colValoracion.setCellFactory(col -> new TableCell<Casa, Float>() {
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
        LocalDate buscarFechaInscripcion = dpFechaInscripcion.getValue();

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
                if (buscarHueco.equals("Sí") && !casa.isHuecoDisponible()) {
                    return false;
                }

                if (buscarHueco.equals("No") && casa.isHuecoDisponible()) {
                    return false;
                }
            }

            if (buscarFechaInscripcion != null) {
                if (casa.getFechaInscripcion() == null) {
                    return false;
                }

                if (!casa.getFechaInscripcion().equals(buscarFechaInscripcion)) {
                    return false;
                }
            }
            return true;
        });

        lblBarraEstado.setText("Búsqueda completada.");
    }


    @FXML
    protected void anadirCasa() {
        if(validadorCamposObligatorios()) {
            String dueno = txtDueno.getText();
            int numeroGatos = -1;
            if (!txtGatos.getText().trim().isEmpty()) {
                Integer.parseInt(txtGatos.getText());
            }
            float valoracion = -1.0f;
            if (!txtValoracion.getText().trim().isEmpty()) {
                Float.parseFloat(txtValoracion.getText().replace(",", "."));
            }
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
            txtGatos.setText(casaCargar.getNumeroGatos() == -1 ? "" : String.valueOf(casaCargar.getNumeroGatos()));
            txtValoracion.setText(casaCargar.getValoracion() == -1.0f ? "" : String.valueOf(casaCargar.getValoracion()));
            cbHueco.setValue(casaCargar.isHuecoDisponible() ? "Sí" : "No");
            dpFechaInscripcion.setValue(casaCargar.getFechaInscripcion());
        }

        lblBarraEstado.setText("La casa de " + (casaCargar.getDueno()) + " ha sido seleccionada.");
    }

    @FXML
    protected void eliminarCasa() {
        Casa casaCargar = tableCasa.getSelectionModel().getSelectedItem();

        if (casaCargar != null) {
            listaCasas.remove(casaCargar);
            limpiarCasa();
        }

        lblBarraEstado.setText("La casa de " + (casaCargar.getDueno()) + " ha sido eliminada de la lista.");
    }

    @FXML
    protected void modificarCasa() {
        Casa casaCargar = tableCasa.getSelectionModel().getSelectedItem();

        if(validadorCamposObligatorios()) {
            if (casaCargar != null) {
                int numeroGatos = -1;
                if (!txtGatos.getText().trim().isEmpty()) {
                    numeroGatos = Integer.parseInt(txtGatos.getText());
                }
                float valoracion = -1.0f;
                if (!txtValoracion.getText().trim().isEmpty()) {
                    valoracion = Float.parseFloat(txtValoracion.getText().replace(",", "."));
                }
                casaCargar.setDueno(txtDueno.getText());
                casaCargar.setNumeroGatos((numeroGatos));
                casaCargar.setValoracion(valoracion);
                casaCargar.setHuecoDisponible("Sí".equals(cbHueco.getValue()));
                casaCargar.setFechaInscripcion(dpFechaInscripcion.getValue());

                tableCasa.refresh();
                limpiarCasa();

                lblBarraEstado.setText("Los datos de la casa de " + (casaCargar.getDueno()) + " han sido modificados.");
            }
        }
    }

    @FXML
    protected boolean validadorCamposObligatorios() {
        String mensajeError = "";
        if (txtDueno.getText() == null || txtDueno.getText().trim().isEmpty()) {
            mensajeError += "Es obligatorio indicar el dueño de la casa. ";
        }
        if (cbHueco.getValue() == null) {
            mensajeError += "Hay que seleccionar si hay hueco disponible.";
        }
        if (mensajeError.isEmpty()) {
            return true;
        } else {
            lblBarraEstado.setText(mensajeError);
            return false;
        }
    }




}