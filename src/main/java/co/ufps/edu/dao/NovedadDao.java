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

import co.ufps.edu.bd.SpringDbMgr;
import co.ufps.edu.dto.Novedad;
import co.ufps.edu.util.ImagenUtil;

public class NovedadDao {

	private SpringDbMgr springDbMgr;
	private ImagenUtil imagenUtil;
	
	public NovedadDao() {
	  springDbMgr = new SpringDbMgr();
	  imagenUtil = new ImagenUtil();
	}
	
	
	/**
	 * Metodo que consulta en base de datos todas las novedades existentes.
	 * 
	 * @return Una lista con todas las novedades
	 */
	public List<Novedad> getNovedades() {

		// Lista para retornar con los datos
	    List<Novedad> novedades = new LinkedList<>();
	    // Consulta para realizar en base de datos
	    SqlRowSet sqlRowSet = springDbMgr.executeQuery(" SELECT * FROM NOVEDAD ");
	    
	    // Recorre cada registro obtenido de base de datos
	    while (sqlRowSet.next()) {
	      // Objeto en el que sera guardada la informacion del registro
	      Novedad novedad = new Novedad();

	      novedad.setId(sqlRowSet.getLong("id"));
	      novedad.setNombre(sqlRowSet.getString("nombre"));
	      novedad.setFecha(sqlRowSet.getDate("fecha"));
	      // Guarda el registro para ser retornado
	      novedades.add(novedad);
	    }
	    // Retorna todos los contactos desde base de datos
	    return novedades;
	}
	
	/**
	   * M�todo que registra una novedad en base de datos
	   * 
	   * @param novedad Objeto con todos los datos de la novedad a registrar.
	   * @return El resultado de la acci�n.
	   */
	public String registrarNovedad(Novedad novedad) {
	    SpringDbMgr springDbMgr = new SpringDbMgr();

	    // Agrego los datos del registro (nombreColumna/Valor)
	    MapSqlParameterSource map = new MapSqlParameterSource();
	    map.addValue("nombre", novedad.getNombre());
	    map.addValue("fecha", novedad.getFecha());
	    try {
	      map.addValue("imagen",
	          new SqlLobValue(new ByteArrayInputStream(novedad.getImagen().getBytes()),
	              novedad.getImagen().getBytes().length, new DefaultLobHandler()),
	          Types.BLOB);
	    } catch (IOException e) {
	      new Exception();
	    }

	    // Armar la sentencia de actualizaci�n de base de datos
	    String query = "INSERT INTO NOVEDAD(nombre, fecha, imagen) VALUES(:nombre, :fecha, :imagen)";
	    
	    // Ejecutar la sentencia
	    int result = 0;
	    try {
	      result = springDbMgr.executeDml(query, map);
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	    // Si hubieron filas afectadas es por que si hubo registro, en caso contrario muestra el mensaje
	    // de error.
	    return (result == 1) ? "Registro exitoso"
	        : "La informaci�n de la novedad ya se encuentra en el sistema.";
	  }

	  
	  /**
	   * M�todo que consulta en base de datos la informacion de una novedad
	   * 
	   * @param idNovedad Identificador de novedad.
	   * @return La informacion de una novedad en un objeto.
	   */
	  public Novedad obtenerNovedadPorId(long idNovedad) {
	    // Lista para retornar con los datos
	    Novedad novedad = new Novedad();

	    // Consulta para realizar en base de datos
	    MapSqlParameterSource map = new MapSqlParameterSource();
	    map.addValue("id", idNovedad);
	    SqlRowSet sqlRowSet = springDbMgr.executeQuery(" SELECT * FROM NOVEDAD WHERE id = :id", map);
	    
	    // Consulto si la novedad existe
	    if (sqlRowSet.next()) {
	      // Almaceno los datos de contacto
	      novedad.setId(sqlRowSet.getLong("id"));
	      novedad.setNombre(sqlRowSet.getString("nombre"));
	      novedad.setFecha(sqlRowSet.getDate("fecha"));
	      
	      Object imagen1Blob = sqlRowSet.getObject("imagen");
	      novedad.setImBase64image(imagenUtil.convertirImagen((byte[]) imagen1Blob));	      
	    }

	    // Retorna contacto desde base de datos
	    return novedad;
	  }

	  
	  /**
	   * M�todo que consulta en base de datos la informacion de  una novedad
	   * 
	   * @param idNovedad Identificador de novedad.
	   * @return La informacion de una novedad en un objeto.
	   */
	  public String editarNovedad(Novedad novedad) {
	    SpringDbMgr springDbMgr = new SpringDbMgr();

	    // Agrego los datos del registro (nombreColumna/Valor)

	    MapSqlParameterSource map = new MapSqlParameterSource();
	    map.addValue("id", novedad.getId());
	    map.addValue("nombre", novedad.getNombre());
	    map.addValue("fecha", novedad.getFecha());
	    String sqlImagen = "";
	    if (novedad.getImagen().getSize() > 0) {
	       try {
	          map.addValue("imagen",
	              new SqlLobValue(new ByteArrayInputStream(novedad.getImagen().getBytes()),
	                  novedad.getImagen().getBytes().length, new DefaultLobHandler()),
	              Types.BLOB);
	          sqlImagen = ", imagen = :imagen";
	        } catch (IOException e) {
	          new Exception();
	        }
	    }
	    
	    // Armar la sentencia de actualizaci�n debase de datos
	    String query = "UPDATE NOVEDAD SET nombre = :nombre, fecha = :fecha "+ sqlImagen + "  WHERE id = :id";

	    // Ejecutar la sentencia
	    int result = 0;
	    try {
	      result = springDbMgr.executeDml(query, map);
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	    // Si hubieron filas afectadas es por que si hubo registro, en caso contrario muestra el mensaje
	    // de error.
	    return (result == 1) ? "Actualizacion exitosa"
	        : "La novedad ya se encuentra en el sistema.";
	  }
	  
	  
	  public String eliminarNovedad(Novedad novedad) {
		    SpringDbMgr springDbMgr = new SpringDbMgr();

		    // Agrego los datos de la eliminaci�n (nombreColumna/Valor)
		    MapSqlParameterSource map = new MapSqlParameterSource();
		    map.addValue("id", novedad.getId());

		    // Armar la sentencia de eliminaci�n debase de datos
		    String query = "DELETE FROM NOVEDAD WHERE id = :id";

		    // Ejecutar la sentencia
		    int result = 0;
		    try {
		      result = springDbMgr.executeDml(query, map);
		    } catch (Exception e) {
		      e.printStackTrace();
		    }
		    // Si hubieron filas afectadas es por que si hubo registro, en caso contrario muestra el mensaje
		    // de error.
		    return (result == 1) ? "Eliminacion exitosa" : "No fue posible eliminar la novedad";
	  }
	  
	  
	  /*
	   *  M�todo que obtiene la cantidad de novedades registradas
	   */
	  public int getCantidadNovedades() {
		  	int cant = 0;
		    // Consulta para realizar en base de datos
		    SqlRowSet sqlRowSet = springDbMgr.executeQuery(" SELECT COUNT(*) cantidad FROM NOVEDAD "); 
		    
		    if (sqlRowSet.next()) {
		    	cant = sqlRowSet.getInt("cantidad");
		    }
		    return cant;
	  }


    public List<Novedad> getUltimasNovedades() {
      // Lista para retornar con los datos
      List<Novedad> novedades = new LinkedList<>();
      // Consulta para realizar en base de datos
      SqlRowSet sqlRowSet = springDbMgr.executeQuery(" SELECT * FROM NOVEDAD ORDER BY FECHA DESC LIMIT 0, 4 ");
      
      // Recorre cada registro obtenido de base de datos
      while (sqlRowSet.next()) {
        // Objeto en el que sera guardada la informacion del registro
        Novedad novedad = new Novedad();

        novedad.setId(sqlRowSet.getLong("id"));
        novedad.setNombre(sqlRowSet.getString("nombre"));
        novedad.setFecha(sqlRowSet.getDate("fecha"));
        Object imagen1Blob = sqlRowSet.getObject("imagen");
        novedad.setImBase64image(imagenUtil.convertirImagen((byte[]) imagen1Blob));   
        
        // Guarda el registro para ser retornado
        novedades.add(novedad);
      }
      // Retorna todos los contactos desde base de datos
      return novedades;
    }
}
