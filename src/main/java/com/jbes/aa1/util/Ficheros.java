package com.jbes.aa1.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Label;

public class Ficheros {

    public final static String GATOS_DAT = "gatos.dat";
    public final static String CASAS_DAT = "casas.dat";
    public final static String TRAMITES_DAT = "tramites.dat";

    public static <T> void guardar(List<T> listaGuardado, String nombreFichero, Label lblBarraEstado) {
        ObjectOutputStream serializador = null;
        try {
            serializador = new ObjectOutputStream(new FileOutputStream(nombreFichero));
            serializador.writeObject(new ArrayList<>(listaGuardado));
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
            lblBarraEstado.setText("Error: no se ha podido crear el fichero.");
        } catch (IOException ioe) {
            ioe.printStackTrace();
            lblBarraEstado.setText("Error: No se ha podido guardar el fichero.");
        } finally {
            if (serializador != null)
                try {
                    serializador.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                    lblBarraEstado.setText("Error: No se ha podido guardar el fichero.");
                }
        }
    }

    public static <T> ArrayList<T> cargar(String nombreFichero, Label lblBarraEstado) {
        ObjectInputStream deserializador = null;
        try {
            deserializador = new ObjectInputStream(new FileInputStream(nombreFichero));
            ArrayList<T> listaGuardado = (ArrayList<T>) deserializador.readObject();
            deserializador.close();
            return listaGuardado;
        } catch (FileNotFoundException fnfe) {
            lblBarraEstado.setText("Error: El fichero no existe.");
        } catch (ClassNotFoundException cnfe) {
            lblBarraEstado.setText("Error: El fichero tiene formato incorrecto.");
        } catch (IOException ioe) {
            ioe.printStackTrace();
            lblBarraEstado.setText("Error: No se ha podido leer el ficehro.");
        }
        return null;
    }
}
