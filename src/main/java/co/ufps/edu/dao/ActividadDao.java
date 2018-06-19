package co.ufps.edu.dao;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Types;
import java.util.LinkedList;
import java.util.List;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.support.SqlLobValue;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import co.ufps.edu.bd.SpringDbMgr;
import co.ufps.edu.dto.Actividad;
import co.ufps.edu.util.ImagenUtil;

/**
 * Clase que permite acceder a la capa de datos en el entorno de actividades.
 * @author ufps
 *
 */
@Component
public class ActividadDao {

  private SpringDbMgr springDbMgr;
  private ImagenUtil imagenUtil;

  public ActividadDao() {
    springDbMgr = new SpringDbMgr();
    imagenUtil = new ImagenUtil();
  }

  /**
   * Método que obtiene el numero de actividades guardadas en base de datos.
   * @return La cantidad de actividades registradas.
   */
  public int getCantidadActividades() {
    int cant = 0;
    // Consulta para realizar en base de datos
    SqlRowSet sqlRowSet =
        springDbMgr.executeQuery(" SELECT COUNT(*) cantidad FROM proximaactividad ");

    if (sqlRowSet.next()) {
      cant = sqlRowSet.getInt("cantidad");
    }
    return cant;
  }

  /**
   * Metodo que consulta en base de datos todas las actividades existentes y las devuelve
   * ordenadamente
   * 
   * @return Una lista con todas las actividades
   */
  public List<Actividad> getActividades() {

    // Lista para retornar con los datos
    List<Actividad> actividades = new LinkedList<>();

    // Consulta para realizar en base de datos
    SqlRowSet sqlRowSet = springDbMgr.executeQuery(" SELECT * FROM proximaactividad ORDER BY id DESC ");

    // Recorre cada registro obtenido de base de datos
    while (sqlRowSet.next()) {
      // Objeto en el que sera guardada la informacion del registro
      Actividad actividad = new Actividad();

      actividad.setId(sqlRowSet.getLong("id"));
      actividad.setNombre(sqlRowSet.getString("nombre"));
      actividad.setLugar(sqlRowSet.getString("lugar"));
      actividad.setFechaInicial(sqlRowSet.getDate("fechaInicial"));
      actividad.setFechaFinal(sqlRowSet.getDate("fechaFinal"));

      // Guarda el registro para ser retornado
      actividades.add(actividad);
    }

    // Retorna todos las actividades desde base de datos
    return actividades;
  }

  @Async
  public String registrarActividad(Actividad actividad) {
    // Agrego los datos del registro (nombreColumna/Valor)

    MapSqlParameterSource map = new MapSqlParameterSource();
    map.addValue("nombre", actividad.getNombre());
    map.addValue("lugar", actividad.getLugar());
    map.addValue("fechaInicial", actividad.getFechaInicial());
    map.addValue("fechaFinal", actividad.getFechaFinal());
    try {
      map.addValue("imagen",
          new SqlLobValue(new ByteArrayInputStream(actividad.getImagen().getBytes()),
              actividad.getImagen().getBytes().length, new DefaultLobHandler()),
          Types.BLOB);
    } catch (IOException e) {
      new Exception();
    }

    // Armar la sentencia de actualización debase de datos
    String query =
        "INSERT INTO proximaactividad(nombre,lugar,fechaInicial,fechaFinal,imagen) VALUES(:nombre,:lugar,:fechaInicial,:fechaFinal,:imagen)";

    // Ejecutar la sentencia
    int result = 0;
    try {
      result = springDbMgr.executeDml(query, map);
    } catch (Exception e) {
      new Exception();
    }
    // Si hubieron filas afectadas es por que si hubo registro, en caso contrario muestra el mensaje
    // de error.
    return (result == 1) ? "Registro exitoso"
        : "Error al registrar la actividad. Contacte al administrador del sistema.";
  }  

  /**
   * Metodo que consulta en base de datos la informacion de una actividad dada
   * 
   * @param idActividad Identificador de la actividad.
   * @return La información de una actividad en un objeto.
   */
  public Actividad obtenerActividadPorId(long idActividad) {
    // Lista para retornar con los datos
    Actividad actividad = new Actividad();

    // Consulta para realizar en base de datos
    MapSqlParameterSource map = new MapSqlParameterSource();
    map.addValue("id", idActividad);
    SqlRowSet sqlRowSet = springDbMgr.executeQuery(" SELECT * FROM proximaactividad WHERE id = :id", map);

    // Consulto si la actividad existe
    if (sqlRowSet.next()) {
      // Almaceno los datos de la actividad
      actividad.setId(sqlRowSet.getLong("id"));
      actividad.setNombre(sqlRowSet.getString("nombre"));
      actividad.setLugar(sqlRowSet.getString("lugar"));
      actividad.setFechaInicial(sqlRowSet.getDate("fechaInicial"));
      actividad.setFechaFinal(sqlRowSet.getDate("fechaFinal"));
      
      Object imagenBlob = sqlRowSet.getObject("imagen");
      actividad.setImBase64image(imagenUtil.convertirImagen((byte[]) imagenBlob));      
    }
    // Retorna la actividad desde base de datos
    return actividad;
  }
  
  @Async
  public String editarActividad(Actividad actividad) {

    // Agrego los datos del registro (nombreColumna/Valor)
    MapSqlParameterSource map = new MapSqlParameterSource();
    map.addValue("id", actividad.getId());
    map.addValue("nombre", actividad.getNombre());
    map.addValue("lugar", actividad.getLugar());
    map.addValue("fechaInicial", actividad.getFechaInicial());
    map.addValue("fechaFinal", actividad.getFechaFinal());
    String sqlImagen = "";
    if (actividad.getImagen().getSize() > 0) {
      try {
        map.addValue("imagen",
            new SqlLobValue(new ByteArrayInputStream(actividad.getImagen().getBytes()),
                actividad.getImagen().getBytes().length, new DefaultLobHandler()),
            Types.BLOB);
        sqlImagen = ", imagen = :imagen";
      } catch (IOException e) {
        // TODO Auto-generated catch block
        new Exception();
      }
    }

    // Armar la sentencia de actualización debase de datos
    String query =
        "UPDATE proximaactividad SET nombre = :nombre, lugar = :lugar, fechaInicial = :fechaInicial, fechaFinal = :fechaFinal  "
            + sqlImagen + " WHERE id = :id";

    // Ejecutar la sentencia
    int result = 0;
    try {
      result = springDbMgr.executeDml(query, map);
    } catch (Exception e) {
      new Exception();
    }
    // Si hubieron filas afectadas es por que si hubo registro, en caso contrario muestra el mensaje
    // de error.
    return (result == 1) ? "Actualizacion exitosa"
        : "Error al actualizar la actividad. Contacte al administrador del sistema.";
  }  
  
  public String eliminarActividad(Actividad actividad) {

    // Agrego los datos de la eliminación (nombreColumna/Valor)
    MapSqlParameterSource map = new MapSqlParameterSource();
    map.addValue("id", actividad.getId());

    // Armar la sentencia de actualización debase de datos
    String query = "DELETE FROM proximaactividad WHERE id = :id";

    // Ejecutar la sentencia
    int result = 0;
    try {
      result = springDbMgr.executeDml(query, map);
    } catch (Exception e) {
      new Exception();
    }
    // Si hubieron filas afectadas es por que si hubo registro, en caso contrario muestra el mensaje
    // de error.
    return (result == 1) ? "Eliminacion exitosa"
        : "Error al eliminar la actividad. Contacte al administrador del sistema.";
  }

  public List<Actividad> getUltimasActividades() {

    // Lista para retornar con los datos
    List<Actividad> actividades = new LinkedList<>();

    // Consulta para realizar en base de datos
    SqlRowSet sqlRowSet = springDbMgr.executeQuery(" SELECT * FROM proximaactividad ORDER BY fechaInicial DESC LIMIT 0, 4 ");

    // Recorre cada registro obtenido de base de datos
    while (sqlRowSet.next()) {
      // Objeto en el que sera guardada la informacion del registro
      Actividad actividad = new Actividad();

      actividad.setId(sqlRowSet.getLong("id"));
      actividad.setNombre(sqlRowSet.getString("nombre"));
      actividad.setLugar(sqlRowSet.getString("lugar"));
      actividad.setFechaInicial(sqlRowSet.getDate("fechaInicial"));
      actividad.setFechaFinal(sqlRowSet.getDate("fechaFinal"));
      
      Object imagen1Blob = sqlRowSet.getObject("imagen");
      actividad.setImBase64image(imagenUtil.convertirImagen((byte[]) imagen1Blob));   

      // Guarda el registro para ser retornado
      actividades.add(actividad);
    }

    // Retorna todos las actividades desde base de datos
    return actividades;
 
  }
  
}
