package co.ufps.edu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import co.ufps.edu.dao.ContenidoDao;
import co.ufps.edu.dao.LogoDao;
import co.ufps.edu.model.Logo;
import co.ufps.edu.model.Noticia;

/**
 * Controlador de contenidos. Los contenidos son las paginas que se abren cuando se da click a una
 * informaci�n. Todos los servicios publicados en esta clase seran expuestos para ser consumidos por
 * los archivos .JSP
 * <p>
 * La etiqueta @Controller escanea todos los servicios para publicarlos segun el tipo de metodo
 * HTTP.
 * 
 * @author ufps
 *
 */
@Controller
public class LogoController {

  private LogoDao logoDao;

  /**
   * Constructor de la clase en donde se inicializan las variables
   */
  public LogoController() {
    logoDao = new LogoDao();
  }

  /**
   * M�todo que retorna una pagina con todas los contenidos en el sistema.
   * 
   * @return La p�gina principal de contenidos.
   */
  @GetMapping("/logos") // Base
  public String index(Model model) {
    // Cargamos los contenidos para poder mostrarlas en el cuadro.
    model.addAttribute("logoHorizontal", logoDao.getLogo("LogoHorizontal"));
    model.addAttribute("logoVertical", logoDao.getLogo("LogoVertical"));
    return "Administrador/Logo/logos"; // Nombre del archivo jsp
  }

  /**
   * Modelo con el que se realizara el formulario
   * 
   * @return Un objeto para ser llenado desde el archivo .JSP
   */
  @ModelAttribute("logo")
  public Logo setUpUserForm() {
    return new Logo();
  }
  
  /**
   * Servicio que permite guardar un logo
   * 
   * @param logo Objeto con la informaci�n a guardar
   * @param model Modelo con la informaci�n necesaria para transportar a los archivos .JSP
   * @return La p�gina a donde debe redireccionar despu�s de la acci�n.
   */
  @PostMapping(value = "/guardarLogo")
  public String guardarLogoHorizontal(@ModelAttribute("logo") Logo logo, Model model) {

      if(logo.getId() == 0) {
        System.out.println("No existeeeee");
      }else {
        System.out.println("Ya existeeeee");
      }
      
      model.addAttribute("logoHorizontal", logoDao.getLogo("LogoHorizontal"));
      model.addAttribute("logoVertical", logoDao.getLogo("LogoVertical"));
      return "Administrador/Logo/logos"; // Nombre del archivo jsp
      
      /*String mensaje = logoDao.actualizarLogo(logoHorizontal);
      if (mensaje.equals("Registro exitoso")) {
        model.addAttribute("result", "Noticia registrada con �xito.");
        model.addAttribute("noticias", noticiaDao.getNoticias());
        return "Administrador/Noticia/Noticias"; // Nombre del archivo jsp
      } else {
        model.addAttribute("wrong", mensaje);
        return "Administrador/Noticia/RegistrarNoticia"; // Nombre del archivo jsp
      }*/

  }  

}
