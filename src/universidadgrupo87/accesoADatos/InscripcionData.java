/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package universidadgrupo87.accesoADatos;

import java.sql.*;

/**
 *
 * @author Agustin Colongne
 */
public class InscripcionData {
    private Connection con = null;
    private AlumnoData aluData;
    private MateriaData matData;

    
    public InscripcionData(){
        con = Conexion.getConexion();
    }
    
    
    
}
