<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!doctype html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7" lang=""> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8" lang=""> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9" lang=""> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js" lang=""> <!--<![endif]-->
<head>
	<%@ include file = "../General/css.jsp" %>
</head>
<body>
	<!-- Left Panel -->

		<%@ include file = "../General/LeftPanel.jsp" %>

	<!-- /#left-panel -->

    <!-- Right Panel -->

    <div id="right-panel" class="right-panel">

        <!-- Header-->
        <header id="header" class="header">

            <div class="header-menu">
            	<div class="col-sm-7">
                        <a id="menuToggle" class="menutoggle pull-left"><i class="fa fa fa-tasks"></i></a>
                        <div class="page-header float-left">
                        	<div class="page-title">
                                <ol class="breadcrumb text-right">
                                	<li><a href="${contextPath}/indexAdmin">Panel de control</a></li>
                                    <li><a href="${contextPath}/actividades">Actividades / </li>
                                    <li class="active"><a href="#"> Actualizar Actividad</li>
                            	</ol>
                            </div>
						</div>    
                </div>
               	<!-- Area en donde se encuentra la foto del usuario y la barra de opciones -->
				<div class="col-sm-5">
	            	<div class="user-area dropdown float-right">
	                	<a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
	                    	<img class="user-avatar rounded-circle" src="resources/images/admin.jpg" alt="User Avatar">
						</a>
						<div class="user-menu dropdown-menu">
	                    	<a class="nav-link" href="#"><i class="fa fa- user"></i>Mi perfil</a>                                  
	                        <a class="nav-link" href="#"><i class="fa fa -cog"></i>Configuración de la cuenta</a>
	                        <a class="nav-link" href="${contextPath}/logout"><i class="fa fa-power -off"></i>Salir</a>
						</div>
					</div>                        
				</div>
            </div>

        </header><!-- /header -->
        <!-- Header-->
		
		<!-- Contenedor del formulario -->
        <div class="content mt-3">
            <div class="animated fadeIn">
                <div class="row">
                	<div class="col-md-12">                	
 	
                    	<div class="card">
                    		<!-- Titulo de la ventana -->
                        	<div class="card-header">
                            	<strong class="card-title">Actualizar actividad</strong>
                        	</div>
                        	<div class="card-body">
                        		<!-- Si hubo un error en el registro muestra el mensaje-->						
								<c:if test="${not empty wrong}">		            
		                        	<div class="sufee-alert alert with-close alert-danger alert-dismissible fade show">				                   	
				                    		<c:out value='${wrong}' />
				                    	<button type="button" class="close" data-dismiss="alert" aria-label="Close">
											<span aria-hidden="true">&times;</span>
				                    	</button>
				                   </div>
							    </c:if>
                        	
                        		<!-- Formulario -->
                        		<form:form id="formActividad" action="editarActividad" method="post" modelAttribute="actividad" enctype="multipart/form-data">
                            		
                            		<form:hidden id="id" path="id" class="form-control" aria-invalid="false" required = "true" value="${actividad.id}"/>
                            		<form:hidden id="im1Base64image" path="imBase64image" class="form-control"  aria-invalid="false" value="Lleno"/>
                            		                            		
                            		<!-- Campo para digitar el nombre -->
                                	<div class="form-group">
                                    	<label for="text-input" class=" form-control-label">Nombre</label>
                                		<form:input id="nombre" path="nombre" class="form-control" placeholder="Encuentro de egresados 2019" aria-invalid="false" required = "true" value="${actividad.nombre}"/>
                                	</div> 
                            		<!-- Campo para digitar el lugar -->
                                	<div class="form-group">
                                    	<label for="text-input" class=" form-control-label">Lugar</label>
                                		<form:input id="lugar" path="lugar" class="form-control" placeholder="Hotel tonchala" aria-invalid="false" required = "true" value="${actividad.lugar}"/>
                                	</div> 
									<!-- Campos de las fechas -->
									<div class="row">
										<!-- Fecha inicial -->
										<div class="col-md-6" >
											<div class="form-group"> <!-- Date input -->
									        	<label class="control-label" for="date">Fecha inicial</label>
									        	<form:input type="date" id="fechaInicial" path="fechaInicial" class="form-control" aria-invalid="false" required = "true" value="${actividad.fechaInicial}"/>
									      	</div>
										</div>
										<!--/ Fin de fecha inicial -->
		
										<!-- Fecha final -->
										<div class="col-md-6" >
									      	<div class="form-group"> <!-- Date input -->
									        	<label class="control-label" for="date">Fecha final</label>
									        	<form:input type="date" id="fechaFinal" path="fechaFinal" class="form-control" aria-invalid="false" required = "true" value="${actividad.fechaFinal}"/>
									      	</div>
										</div>
										<!--/ Fin de fecha final -->
		                            </div>	           
			                        </br>                                	
                                	 <!-- Campo para digitar la imagen 1 -->
                                	<div id ="divim1" class="form-group btn btn-primary btn-sm">
                                    	<label for="text-input" class=" form-control-label">Imagen 1</label>
                                		<form:input type="file" path="Imagen" id="Imagen1" name="Imagen1" onchange="revisarArchivos('1')"/>
                                	</div>
                                	</br>
                                	<div id = "divimagen1" class="form-group">
                                		<img id = "img1" src="${actividad.imBase64image}" height="200" alt="Imagen">
                                	</div>
                                	</br>
                                	                                        		                                                               	                                	                              	                                	
                                	<!-- Boton para actualizar los datos -->
                                	<button type="submit" class="btn btn-success">Actualizar</button>                                 
                            	 </form:form>                          
                        	</div>
                    	</div>
                	</div>
                </div>
            </div><!-- .animated -->
        </div><!-- .content -->


    </div><!-- /#right-panel -->

    <!-- Right Panel -->


	<!-- Carga de los archivos Javascript -->
	<%@ include file = "../General/scripts.jsp" %>
	
	<!-- <script src="resources/assets/js/archivos.js"></script>  -->
	<script type="text/javascript">
	function revisarArchivos(id){
		var el = document.getElementById("Imagen"+id).files;
		if(el!=null && el.length==0){
			document.getElementById("divim"+id).setAttribute('class', 'btn btn-danger btn-sm');
			previewFile(id);
		}else{
			document.getElementById("divim"+id).setAttribute('class', 'btn btn-primary btn-sm');
			previewFile(id);
		}
	}
	
	/*
	* Metodo que permite pintar una imagen recien 
	*/
	function previewFile(id) {
		  var preview = document.getElementById('img'+id);
		  var file    = document.getElementById('Imagen'+id).files[0];
		  var reader  = new FileReader();

		  reader.onloadend = function () {
		    preview.src = reader.result;
		    document.getElementById('im'+id+'Base64image').value = "Lleno";
		  }

		  if (file) {
		    reader.readAsDataURL(file);
		  } else {
			  			  
		    preview.src = "";
		    document.getElementById('im'+id+'Base64image').value = "";
		  }
		}	
	</script>

</body>
</html>
