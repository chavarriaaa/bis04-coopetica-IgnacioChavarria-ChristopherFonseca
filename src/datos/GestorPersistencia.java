package datos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import logica.Cooperativa;
import modelo.Asociado;
import modelo.Sucursal;

/**
 * Guarda y carga la información de la cooperativa en archivos de texto.
 * existe una única instancia en todo el programa.
 */
public class GestorPersistencia {

    private static final String SEPARADOR = ";";
    private static final String NOMBRE_COOPERATIVA = "CoopeTica R.L.";
    private static final String ARCHIVO_SUCURSALES = "sucursales.txt";
    private static final String ARCHIVO_ASOCIADOS = "asociados.txt";

    private static GestorPersistencia instancia;

    private File carpeta;

    private GestorPersistencia() {
        this.carpeta = new File("datos");
    }

    /**
     * Obtiene la única instancia del gestor.
     *
     * @return el gestor de persistencia
     */
    public static GestorPersistencia getInstancia() {
        if (instancia == null) {
            instancia = new GestorPersistencia();
        }
        return instancia;
    }

    /**
     * Cambia la carpeta donde se guardan los archivos. Se usa en las pruebas
     * para no tocar los datos reales.
     *
     * @param ruta ruta de la carpeta; no puede estar vacía
     * @throws IllegalArgumentException si la ruta está vacía
     */
    public void usarCarpeta(String ruta) {
        if (ruta == null || ruta.isBlank()) {
            throw new IllegalArgumentException("La ruta es obligatoria.");
        }
        this.carpeta = new File(ruta);
    }

    /**
     * Verifica que existan la carpeta y los archivos de datos, y los crea
     * si faltan.
     *
     * @throws IOException si no se puede crear la carpeta o algún archivo
     */
    public void verificarArchivos() throws IOException {
        if (!carpeta.exists() && !carpeta.mkdirs()) {
            throw new IOException("No se pudo crear la carpeta " + carpeta.getPath());
        }
        crearSiNoExiste(new File(carpeta, ARCHIVO_SUCURSALES));
        crearSiNoExiste(new File(carpeta, ARCHIVO_ASOCIADOS));
    }

    /**
     * Guarda las sucursales y los asociados de la cooperativa, reemplazando
     * el contenido anterior de los archivos.
     *
     * @param c cooperativa a guardar; no puede ser nula
     * @throws IOException si ocurre un error al escribir los archivos
     * @throws IllegalArgumentException si la cooperativa es nula o algún
     *         dato contiene el carácter ';' o saltos de línea
     */
    public void guardarTodo(Cooperativa c) throws IOException {
        if (c == null) {
            throw new IllegalArgumentException("La cooperativa es obligatoria.");
        }
        StringBuilder sucursales = new StringBuilder();
        for (Sucursal s : c.listarSucursales()) {
            sucursales.append(texto(s.getCodigo())).append(SEPARADOR)
                    .append(texto(s.getNombre())).append(System.lineSeparator());
        }
        StringBuilder asociados = new StringBuilder();
        for (Asociado a : c.listarAsociados()) {
            asociados.append(texto(a.getCedula())).append(SEPARADOR)
                    .append(texto(a.getNombre())).append(SEPARADOR)
                    .append(texto(a.getNumeroAsociado())).append(SEPARADOR)
                    .append(texto(a.getSucursal().getCodigo())).append(SEPARADOR)
                    .append(a.getSaldoAportes()).append(System.lineSeparator());
        }
        verificarArchivos();
        escribir(new File(carpeta, ARCHIVO_SUCURSALES), sucursales.toString());
        escribir(new File(carpeta, ARCHIVO_ASOCIADOS), asociados.toString());
    }

    /**
     * Carga la cooperativa desde los archivos de datos. Si los archivos no
     * existen, los crea y devuelve una cooperativa vacía.
     *
     * @return la cooperativa con sus sucursales y asociados
     * @throws IOException si hay un error de lectura o alguna línea es
     *         inválida; el mensaje indica el archivo y el número de línea
     */
    public Cooperativa cargarTodo() throws IOException {
        verificarArchivos();
        Cooperativa c = new Cooperativa(NOMBRE_COOPERATIVA);
        cargarSucursales(c);
        cargarAsociados(c);
        return c;
    }

    private void cargarSucursales(Cooperativa c) throws IOException {
        File archivo = new File(carpeta, ARCHIVO_SUCURSALES);
        try (BufferedReader lector =
                new BufferedReader(new FileReader(archivo, StandardCharsets.UTF_8))) {
            String linea;
            int numero = 0;
            while ((linea = lector.readLine()) != null) {
                numero++;
                if (linea.isBlank()) {
                    continue;
                }
                String[] campos = linea.split(SEPARADOR, -1);
                if (campos.length != 2) {
                    throw errorEnLinea(ARCHIVO_SUCURSALES, numero, "se esperaban 2 campos", null);
                }
                try {
                    c.registrarSucursal(new Sucursal(campos[0], campos[1]));
                } catch (IllegalArgumentException e) {
                    throw errorEnLinea(ARCHIVO_SUCURSALES, numero, e.getMessage(), e);
                }
            }
        }
    }

    private void cargarAsociados(Cooperativa c) throws IOException {
        File archivo = new File(carpeta, ARCHIVO_ASOCIADOS);
        try (BufferedReader lector =
                new BufferedReader(new FileReader(archivo, StandardCharsets.UTF_8))) {
            String linea;
            int numero = 0;
            while ((linea = lector.readLine()) != null) {
                numero++;
                if (linea.isBlank()) {
                    continue;
                }
                String[] campos = linea.split(SEPARADOR, -1);
                if (campos.length != 5) {
                    throw errorEnLinea(ARCHIVO_ASOCIADOS, numero, "se esperaban 5 campos", null);
                }
                Sucursal s = c.buscarSucursal(campos[3]);
                if (s == null) {
                    throw errorEnLinea(ARCHIVO_ASOCIADOS, numero,
                            "la sucursal " + campos[3] + " no existe", null);
                }
                try {
                    double saldo = Double.parseDouble(campos[4]);
                    c.registrarAsociado(new Asociado(campos[0], campos[1], campos[2], s, saldo));
                } catch (NumberFormatException e) {
                    throw errorEnLinea(ARCHIVO_ASOCIADOS, numero, "el saldo no es un número", e);
                } catch (IllegalArgumentException e) {
                    throw errorEnLinea(ARCHIVO_ASOCIADOS, numero, e.getMessage(), e);
                }
            }
        }
    }

    private void escribir(File archivo, String contenido) throws IOException {
        try (BufferedWriter escritor =
                new BufferedWriter(new FileWriter(archivo, StandardCharsets.UTF_8))) {
            escritor.write(contenido);
        }
    }

    private void crearSiNoExiste(File archivo) throws IOException {
        if (!archivo.exists() && !archivo.createNewFile()) {
            throw new IOException("No se pudo crear el archivo " + archivo.getPath());
        }
    }

    private String texto(String valor) {
        if (valor.contains(SEPARADOR) || valor.contains("\n") || valor.contains("\r")) {
            throw new IllegalArgumentException(
                    "Los datos no pueden contener el carácter ';' ni saltos de línea.");
        }
        return valor;
    }

    private IOException errorEnLinea(String archivo, int numero, String detalle, Exception causa) {
        return new IOException("Error en " + archivo + ", línea " + numero + ": " + detalle, causa);
    }
}