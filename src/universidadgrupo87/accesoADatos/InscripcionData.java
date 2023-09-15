/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package universidadgrupo87.accesoADatos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import universidadgrupo87.entidades.Alumno;
import universidadgrupo87.entidades.Inscripcion;
import universidadgrupo87.entidades.Materia;

/**
 *
 * @author Agustin Colongne
 */
public class InscripcionData {

    private Connection con = null;
    private AlumnoData aluData = new AlumnoData();
    private MateriaData matData = new MateriaData();

    public InscripcionData() {
        con = Conexion.getConexion();
    }

    public void guardarInscripcion(Inscripcion inscripcion) {
        String sql = "INSERT INTO inscripcion(idAlumno, idMateria, nota) VALUES (?,?,?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, inscripcion.getAlumno().getIdAlumno());
            ps.setInt(2, inscripcion.getMateria().getIdMateria());
            ps.setDouble(3, inscripcion.getNota());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                inscripcion.setIdInscripcion(rs.getInt(1));
                JOptionPane.showMessageDialog(null, "Se guardo correctamente");
            }

            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al conectar a la tabla");
        }

    }

    public void guardarNota(int idAlumno, int idMateria, double nota) {
        String sql = "UPDATE inscripcion SET nota=? WHERE idAlumno=? AND idMateria =?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setDouble(1, nota);
            ps.setInt(2, idAlumno);
            ps.setInt(3, idMateria);
            int filas = ps.executeUpdate();
            if (filas > 0) {
                JOptionPane.showMessageDialog(null, "Nota actualizada");

            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("Error al agregar nota" + ex);
        }
    }

    public void borrarInscripcion(int idAlumno, int idMateria) {
        String sql = "DELETE FROM inscripcion WHERE idAlumno=? AND idMateria=?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idAlumno);
            ps.setInt(2, idMateria);

            int filas = ps.executeUpdate();
            if (filas > 0) {
                JOptionPane.showMessageDialog(null, "Se elimino correctamente");
            }
            ps.close();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al eliminar inscripcion");
        }

    }

    public List<Inscripcion> obtenerInscripciones() {
        String sql = "SELECT * FROM inscripcion ";
        ArrayList<Inscripcion> inscripciones = new ArrayList<>();

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Inscripcion ins = new Inscripcion();
                ins.setIdInscripcion(rs.getInt("idInsc"));
                Alumno alum = aluData.buscarAlumno(rs.getInt("idAlumno"));
                Materia mat = matData.buscarMateria(rs.getInt("idMateria"));
                ins.setNota(rs.getDouble("nota"));
                ins.setAlumno(alum);
                ins.setMateria(mat);
                ins.setNota(rs.getDouble("nota"));
                inscripciones.add(ins);
            }
            ps.close();

        } catch (SQLException ex) {
            System.out.println("Error al obtener inscripciones ");
        }
        return inscripciones;
    }

    public List<Inscripcion> obtenerInscripcionesPorAlumno(int idAlumno) {

        ArrayList<Inscripcion> inscripciones = new ArrayList<>();
        String sql = "SELECT * FROM inscripcion WHERE idAlumno = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idAlumno);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Inscripcion ins = new Inscripcion();
                ins.setIdInscripcion(rs.getInt("idInsc"));
                Alumno alum = aluData.buscarAlumno(rs.getInt("idAlumno"));
                Materia mat = matData.buscarMateria(rs.getInt("idMateria"));
                ins.setNota(rs.getDouble("nota"));
                ins.setAlumno(alum);
                ins.setMateria(mat);
                ins.setNota(rs.getDouble("nota"));
                inscripciones.add(ins);
            }
            ps.close();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al obtener inscripciones ");
        }
        return inscripciones;
    }

    public List<Materia> obtenerMateriasCursadas(int idAlumno) {
        ArrayList<Materia> materias = new ArrayList<>();
        String sql = "SELEC inscripcion.idMateria, nombre, anio FROM inscripcion, maeria WHERE inscripcion.materia = materia.idMateria"
                + "AND inscripcion.idAlumno = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idAlumno);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Materia asignatura = new Materia();
                asignatura.setIdMateria(rs.getInt("idMateria"));
                asignatura.setNombre(rs.getString("nombre"));
                asignatura.setAnioMateria(rs.getInt("anio"));
                materias.add(asignatura);

            }

            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al obtener inscripciones ");
        }

        return materias;
    }

    public List<Materia> obetenerMateriasNoCursadas(int idAlumno) {

        ArrayList<Materia> materias = new ArrayList<>();
        String sql = "SELEC * FROM materia WHERE estado = 1 AND idMateria NOT in (SELEC idMateria FROM inscripcion WHERE idAlumno = ?)";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idAlumno);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Materia asignatura = new Materia();
                asignatura.setIdMateria(rs.getInt("idMateria"));
                asignatura.setNombre(rs.getString("nombre"));
                asignatura.setAnioMateria(rs.getInt("anio"));
                materias.add(asignatura);

            }

            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al obtener inscripciones ");
        }

        return materias;

    }

    public List<Alumno> obtenerAlumnosPorMateria(int idMAteria) {
        ArrayList<Alumno> alumnosPorMateria = new ArrayList<>();
        String sql = "SELEC a.idAlumno, dni, nombre, apellido, fechaNacimiento, estado FROM inscripcion i, alumno a "
                + "WHERE i.idAlumno = a.idAlumno AND idMateria = ? AND a.estado = 1";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idMAteria);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Alumno alu = new Alumno();
                alu.setIdAlumno(rs.getInt("idAlumno"));
                alu.setDni(rs.getInt("dni"));
                alu.setNombre(rs.getString("idAlumno"));
                alu.setApellido(rs.getString("idAlumno"));
                alu.setFechaNacimiento(rs.getDate("fechaNacimiento").toLocalDate());
                alu.setActivo(rs.getBoolean("activo"));
                alumnosPorMateria.add(alu);

            }

            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla iscripcion");
        }

        return alumnosPorMateria;

    }

}
