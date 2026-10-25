package com.mycompany.archivosney;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

public class Main {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory(
                "com.mycompany_archivosney_jar_1.0-SNAPSHOTPU");
        EntityManager em = emf.createEntityManager();

        // 1. Declarar los tres archivos locales
        File archivoCV = new File("C:/Users/lenovo/Documents/CV_Vianey_Mejia.pdf");
        File archivoDatos = new File("C:/Users/lenovo/Documents/datos.pdf");
        File archivoPractica = new File("C:/Users/lenovo/Documents/PRACTICA2.PDF");

        // Validar que TODOS los archivos existan antes de empezar
        File[] archivosAProcesar = {archivoCV, archivoDatos, archivoPractica};
        for (File f : archivosAProcesar) {
            if (!f.exists()) {
                System.out.println("ERROR: No se encuentra el archivo en la ruta: " + f.getAbsolutePath());
                em.close();
                emf.close();
                return;
            }
        }

        try {
            // 2. Guardar los tres archivos en la base de datos y obtener sus IDs dinámicos
            Long idCV = guardarArchivo(em, archivoCV);
            Long idDatos = guardarArchivo(em, archivoDatos);
            Long idPractica = guardarArchivo(em, archivoPractica);

            System.out.println("\n----------------------------------------");
            System.out.println("¡Los 3 archivos se guardaron con éxito!");
            System.out.println("----------------------------------------\n");

            // 3. Recuperar y descargar cada uno para poder visualizarlos en tus Documentos
            recuperarYGuardarArchivo(em, idCV, "C:/Users/lenovo/Documents/CV_Vianey_descargado.pdf");
            recuperarYGuardarArchivo(em, idDatos, "C:/Users/lenovo/Documents/datos_descargado.pdf");
            recuperarYGuardarArchivo(em, idPractica, "C:/Users/lenovo/Documents/PRACTICA2_descargado.pdf");

        } catch (Exception e) {
            System.err.println("Ocurrió un error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (em.isOpen()) em.close();
            if (emf.isOpen()) emf.close();
        }
    }

    /**
     * Guarda un archivo en la base de datos.
     * @return el ID generado del registro insertado
     */
    public static Long guardarArchivo(EntityManager em, File file) throws IOException {
        byte[] bFile = Files.readAllBytes(file.toPath());

        Archivo archivo = new Archivo();
        archivo.setNombre(file.getName());
        archivo.setTipo("application/pdf");
        archivo.setContenido(bFile);

        em.getTransaction().begin();
        try {
            em.persist(archivo);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        }

        System.out.println("-> Archivo '" + file.getName() + "' guardado con ID: " + archivo.getId());
        return archivo.getId();
    }

    /**
     * Recupera un archivo por ID y lo escribe en disco para visualizarlo.
     */
    public static void recuperarYGuardarArchivo(EntityManager em, Long id, String rutaDestino) {
        Archivo archivo = em.find(Archivo.class, id);

        if (archivo == null) {
            System.out.println("No se encontró ningún archivo con el ID: " + id);
            return;
        }

        try (FileOutputStream fos = new FileOutputStream(new File(rutaDestino))) {
            fos.write(archivo.getContenido());
            System.out.println("¡Descargado para visualizar! -> " + rutaDestino);
        } catch (IOException e) {
            System.err.println("Error al escribir el archivo destino: " + e.getMessage());
            e.printStackTrace();
        }
    }
}