package co.ufps.edu.dao;

import java.io.ByteArrayInputStream;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.IOException;
import java.sql.Types;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.support.SqlLobValue;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import co.ufps.edu.bd.SpringDbMgr;
import co.ufps.edu.dto.Actividad;
import co.ufps.edu.dto.Visita;
import co.ufps.edu.util.ImagenUtil;

/**
 * Clase que permite acceder a la capa de datos en el entorno de actividades.
 * 
 * @author ufps
 *
 */
@Component
public class UsuarioDao {

  
  public JavaMailSenderImpl javaMailSender;
  private SpringDbMgr springDbMgr;

  public UsuarioDao() {
    springDbMgr = new SpringDbMgr();
    javaMailSender = new JavaMailSenderImpl();
    javaMailSender.setHost("smtp.gmail.com");
    javaMailSender.setPort(587);
    javaMailSender.setProtocol("smtp");
    javaMailSender.setUsername("edgar.yesid.garcia.ortiz@gmail.com");
    javaMailSender.setPassword("94100209440");
    Properties mailProperties = new Properties();
    mailProperties.put("mail.transport.protocol", "smtp");
    mailProperties.put("mail.smtp.auth", "true");
    mailProperties.put("mail.smtp.starttls.enable", "true");
    mailProperties.put("mail.smtp.debug", "true");
    mailProperties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
    mailProperties.put("mail.smtp.starttls.enable", "true");
    mailProperties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
    javaMailSender.setJavaMailProperties(mailProperties);
  }
  



  /**
   * Método que obtiene el numero de actividades guardadas en base de datos.
   * 
   * @return La cantidad de actividades registradas.
   */
  public String enviarCorreo(String correo) {
    int cant = 0;
    // Consulta para realizar en base de datos
    MapSqlParameterSource map = new MapSqlParameterSource();
    map.addValue("correo", correo);
    SqlRowSet sqlRowSet = springDbMgr.executeQuery(
        " SELECT Contraseña pass FROM usuario where correoInstitucional = :correo", map);

    String pass = "";
    if (sqlRowSet.next()) {
      pass = sqlRowSet.getString("pass");
    }

    String mensaje = "";
    if (!pass.equals("")) {

      SimpleMailMessage email = new SimpleMailMessage();
      email.setTo(correo);
      email.setSubject("Recuperación de contraseña ADMIN-UFPS");
      email.setText("<p> Su contraseña es : "+pass+" </p>");

      javaMailSender.send(email);
      mensaje = "Actualizacion";
    }
    return mensaje;
  }
  
  public static void main(String[] args) {
    
    UsuarioDao u = new UsuarioDao();
    
    SimpleMailMessage email = new SimpleMailMessage();
    email.setTo("edgar.yesid.garcia.ortiz@gmail.com");
    email.setSubject("Recuperación de contraseña ADMIN-UFPS");
    email.setText("<p> Su contraseña es : 12121 </p>");

    u.javaMailSender.send(email);
    
  }

}
