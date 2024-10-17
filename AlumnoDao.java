package org.acatlan.datos;

import org.acatlan.dominio.Alumno;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static org.acatlan.conexion.Conexion.getConexion;

public class AlumnoDao {
    public List<Alumno> listar() {
        List<Alumno> alumnos = new ArrayList<>();
        PreparedStatement ps;
        ResultSet rs;
        Connection con = getConexion();
        String sql = "select * from alumno order by id_alumno";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                var alumno = new Alumno();
                alumno.setId_alumno(rs.getInt("id_alumno"));
                alumno.setNombre(rs.getString("nombre"));
                alumno.setApellido(rs.getString("apellido"));
                alumno.setNumCuenta(rs.getString("numero_cuenta"));
                alumno.setCorreo(rs.getString("correo"));

                alumnos.add(alumno);
            }
        } catch (Exception e) {
            System.out.println("Error al listar datos" + e.getMessage());
        }
        //cerrar conexion
        finally {
            try {
                con.close();
            } catch (Exception e) {
                System.out.println("Error al cerrar conexion" + e.getMessage());
            }
        }
        return alumnos;
    }
    //buscarporID

    public boolean buscarAlumnoId(Alumno alumno) {
        PreparedStatement ps;
        ResultSet rs;
        Connection con = getConexion();
        String sql = "select * from alumno where id_alumno = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, alumno.getId_alumno());
            rs = ps.executeQuery();
            if (rs.next()) {
                alumno.setNombre("nombre:");
                alumno.setApellido("apellido:");
                alumno.setNumCuenta("numero_cuenta:");
                alumno.setCorreo("correo:");
                return true;
            }
        } catch (Exception e) {
            System.out.println("Error al buscar el alumno" + e.getMessage());
        }
        //cerrar conexion
        finally {
            try {
                con.close();
            } catch (Exception e) {
                System.out.println("Error al cerrar conexion" + e.getMessage());
            }
        }
        return false;
    }


    //prueba  de listado
    public static void main(String[] args) {
        //prueba listado
        var alumnoDao = new AlumnoDao();
        System.out.println("Lista de Alumnos");
        List<Alumno> alumnos = alumnoDao.listar();
        alumnos.forEach(System.out::println);
        // prueba busqueda ID
        var alumno2 = new Alumno(2);
        var find = alumnoDao.buscarAlumnoId(alumno2);
        if(find){
            System.out.println("Alumno:"+alumno2);
        }
        else{
            System.out.println("Alumno no encontrado");
        }
    }
}

