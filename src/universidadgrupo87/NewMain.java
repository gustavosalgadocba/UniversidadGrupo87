/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package universidadgrupo87;

import java.sql.Connection;
import java.time.LocalDate;
import universidadgrupo87.accesoADatos.AlumnoData;
import universidadgrupo87.accesoADatos.Conexion;
import universidadgrupo87.accesoADatos.MateriaData;
import universidadgrupo87.entidades.Alumno;
import universidadgrupo87.entidades.Materia;

/**
 *
 * @author Agustin Colongne
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Connection con = Conexion.getConexion();
        Alumno alum = new Alumno(34, "Furini", "Noemi", LocalDate.of(2003, 6, 10), true, 4456789);
        AlumnoData ad = new AlumnoData();
        ad.guardarAlumno(alum);
        Materia asignatura = new Materia(23, "MAtematicas", 1, true);
        MateriaData asignaturaData = new MateriaData();
        asignaturaData.guardarMateria(asignatura);
        System.out.println("Hola gustavo");
    }
    
}
