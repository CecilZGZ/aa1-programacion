package com.jbes.aa1.controllers;


import com.jbes.aa1.model.Gato;
import com.jbes.aa1.util.Ficheros;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;



import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;


import static com.jbes.aa1.util.Ficheros.GATOS_DAT;

public class GatoController implements Initializable {

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

    private ObservableList<Gato> listaGatos = FXCollections.observableArrayList();

    private FilteredList<Gato> listaGatosFiltrada;



    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if (new File(GATOS_DAT).exists()) {
            ArrayList<Gato> gatoGuardado = Ficheros.cargar(GATOS_DAT, lblBarraEstado);
            if (gatoGuardado == null) {
                lblBarraEstado.setText("Se ha producido un error al cargar los datos.");
            } else {
                listaGatos.addAll(gatoGuardado);
            }
        }

        listaGatosFiltrada = new FilteredList<>(listaGatos, Predicate -> true);
        tableGato.setItems(listaGatosFiltrada);

        cbVacunacion.getItems().addAll("Al día", "Pendiente");

        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colChip.setCellValueFactory(new PropertyValueFactory<>("chip"));
        colPeso.setCellValueFactory(new PropertyValueFactory<>("peso"));
        colVacunacion.setCellValueFactory(new PropertyValueFactory<>("vacunado"));
        colFechaNacimiento.setCellValueFactory(new PropertyValueFactory<>("fechaNacimiento"));

        colChip.setCellFactory(col -> new TableCell<Gato, Integer>() {
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

        colPeso.setCellFactory(col -> new TableCell<Gato, Float>() {
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
    protected void buscarGato() {
        String buscarNombre = txtNombre.getText().toLowerCase();
        String buscarChip = txtChip.getText();
        String buscarPeso = txtPeso.getText();
        String buscarVacunado = cbVacunacion.getValue();
        LocalDate buscarFechaNacimiento = dpFechaNacimiento.getValue();

        listaGatosFiltrada.setPredicate(gato -> {
            if (!buscarNombre.isEmpty() && !gato.getNombre().toLowerCase().contains(buscarNombre)) {
                return false;
            }

            if (!buscarChip.isEmpty() && !String.valueOf(gato.getChip()).contains(buscarChip)) {
                return false;
            }

            if (!buscarPeso.isEmpty() && !String.valueOf(gato.getPeso()).contains(buscarPeso)) {
                return false;
            }

            if (buscarVacunado != null && !buscarVacunado.isEmpty()) {
                if (buscarVacunado.equals("Al día") && !gato.getVacunado()) {
                    return false;
                }

                if (buscarVacunado.equals("Pendiente") && gato.getVacunado()) {
                    return false;
                }
            }

            if (buscarFechaNacimiento != null) {
                if (gato.getFechaNacimiento() == null) {
                    return false;
                }

                if (!gato.getFechaNacimiento().equals(buscarFechaNacimiento)) {
                    return false;
                }
            }
            return true;
        });

        lblBarraEstado.setText("Búsqueda completada.");
    }


    @FXML
    protected void anadirGato() {
        if (validadorCamposObligatorios()) {
            String nombre = txtNombre.getText();

            int chip = -1;
            if (!txtChip.getText().trim().isEmpty()) {
                chip = Integer.parseInt(txtChip.getText());
            }
            float peso = -1.0f;
            if (!txtPeso.getText().trim().isEmpty()) {
                peso = Float.parseFloat(txtPeso.getText().replace(",","."));
            }
            String vacunacion = cbVacunacion.getValue();
            Boolean vacunado = null;
            if (vacunacion != null) {
                vacunado = vacunacion.equals("Al día");
            }
            LocalDate fechaNacimiento = dpFechaNacimiento.getValue();

            Gato gato = new Gato(nombre, chip, peso, vacunado, fechaNacimiento);

            listaGatos.add(gato);

            lblBarraEstado.setText("El gato/a " + nombre + " ha sido añadido/a correctamente.");

            limpiarGato();

            Ficheros.guardar(listaGatos, GATOS_DAT, lblBarraEstado);
        }
    }

    @FXML
    protected void limpiarGato() {
        txtNombre.clear();
        txtChip.clear();
        txtPeso.clear();
        cbVacunacion.setValue(null);
        dpFechaNacimiento.setValue(null);

        tableGato.getSelectionModel().clearSelection();
        btnAnadir.setDisable(false);
        btnBuscar.setDisable(false);
        btnModificar.setDisable(true);
        btnEliminar.setDisable(true);

    }

    @FXML
    protected void cargarGato() {
        Gato gatoCargar = tableGato.getSelectionModel().getSelectedItem();

        if (gatoCargar != null) {
            txtNombre.setText(gatoCargar.getNombre());
            txtChip.setText(gatoCargar.getChip() == -1 ? "" : String.valueOf(gatoCargar.getChip()));
            txtPeso.setText(gatoCargar.getPeso() == -1.0f ? "" : String.valueOf(gatoCargar.getPeso()));
            cbVacunacion.setValue(gatoCargar.getVacunado() ? "Al día" : "Pendiente");
            if (gatoCargar.getVacunado() == null) {
                cbVacunacion.setValue(null);
            } else {
                cbVacunacion.setValue(gatoCargar.getVacunado() ? "Al día" : "Pendiente");
            }
            dpFechaNacimiento.setValue(gatoCargar.getFechaNacimiento());

            btnAnadir.setDisable(true);
            btnBuscar.setDisable(true);
            btnEliminar.setDisable(false);
            btnModificar.setDisable(false);
        }

        lblBarraEstado.setText((gatoCargar.getNombre()) + " ha sido seleccionado/a");
    }

    @FXML
    protected void eliminarGato() {
        Gato gatoCargar = tableGato.getSelectionModel().getSelectedItem();

        if (gatoCargar != null) {
            listaGatos.remove(gatoCargar);
            limpiarGato();
        }

        lblBarraEstado.setText((gatoCargar.getNombre()) + " ha sido eliminado/a de la lista.");

        Ficheros.guardar(listaGatos, GATOS_DAT, lblBarraEstado);
    }

    @FXML
    protected void modificarGato() {
        Gato gatoCargar = tableGato.getSelectionModel().getSelectedItem();

        if( validadorCamposObligatorios()) {
            if (gatoCargar != null) {

                int chip = -1;
                if (!txtChip.getText().trim().isEmpty()) {
                    chip = Integer.parseInt(txtChip.getText());
                }
                float peso = -1.0f;
                if (!txtPeso.getText().trim().isEmpty()) {
                    peso = Float.parseFloat(txtPeso.getText());
                }
                gatoCargar.setNombre(txtNombre.getText());
                gatoCargar.setChip(chip);
                gatoCargar.setPeso(peso);
                gatoCargar.setVacunado(cbVacunacion.getValue() == null ? null : "Al día".equals(cbVacunacion.getValue()));
                gatoCargar.setFechaNacimiento(dpFechaNacimiento.getValue());

                tableGato.refresh();
                limpiarGato();

                lblBarraEstado.setText("Los datos de " + (gatoCargar.getNombre()) + " han sido modificados.");

                Ficheros.guardar(listaGatos, GATOS_DAT, lblBarraEstado);
            }
        }


    }

    @FXML
    protected boolean validadorCamposObligatorios() {
        String mensajeError = "";

        if (txtNombre.getText() == null || txtNombre.getText().trim().isEmpty()) {
            mensajeError += "Es obligatorio introducir un nombre. ";
        }

        try {
            Integer.parseInt(txtChip.getText());
        } catch (NumberFormatException e) {
            mensajeError += "El número de chip debe ser un número entero válido.";
        }

        if (mensajeError.isEmpty()) {
            return true;
        } else {
            lblBarraEstado.setText(mensajeError);
            return false;
        }
    }



}
