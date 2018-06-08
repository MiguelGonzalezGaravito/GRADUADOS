<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!doctype html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7" lang=""> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8" lang=""> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9" lang=""> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js" lang="">
<!--<![endif]-->
<head>
<!-- Assets for editor-->



    <link href='https://fonts.googleapis.com/css?family=Dosis|Candal' rel='stylesheet' type='text/css'>
    <link href='resources/assets/css/editor.css' rel='stylesheet' type='text/css'>
    <link href='http://netdna.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.css' rel='stylesheet' type='text/css'>


<%@ include file="../General/css.jsp"%>



</head>
<body>
	<!-- Left Panel -->

	<%@ include file="../General/LeftPanel.jsp"%>

	<!-- /#left-panel -->

	<!-- Right Panel -->

	<div id="right-panel" class="right-panel">

		<!-- Header-->
		<header id="header" class="header">

			<div class="header-menu">
				<div class="col-sm-7">
					<a id="menuToggle" class="menutoggle pull-left"><i
						class="fa fa fa-tasks"></i></a>
					<div class="page-header float-left">
						<div class="page-title">
							<ol class="breadcrumb text-right">
								<li><a href="${contextPath}/indexAdmin">Panel de
										control</a></li>
								<li><a href="${contextPath}/contenidos">Contenidos / </li>
								<li class="active"><a href="#"> Registrar contenido</li>
							</ol>
						</div>
					</div>
				</div>
				<!-- Area en donde se encuentra la foto del usuario y la barra de opciones -->
				<div class="col-sm-5">
					<div class="user-area dropdown float-right">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown"
							aria-haspopup="true" aria-expanded="false"> <img
							class="user-avatar rounded-circle"
							src="resources/images/admin.jpg" alt="User Avatar">
						</a>
						<div class="user-menu dropdown-menu">
							<a class="nav-link" href="#"><i class="fa fa- user"></i>Mi
								perfil</a> <a class="nav-link" href="#"><i class="fa fa -cog"></i>Configuración
								de la cuenta</a> <a class="nav-link" href="${contextPath}/logout"><i
								class="fa fa-power -off"></i>Salir</a>
						</div>
					</div>
				</div>
			</div>

		</header>
		<!-- /header -->
		<!-- Header-->

		<!-- Contenedor del formulario -->
		<div class="content mt-3">
			<div class="animated fadeIn">
				<div class="row">
					<div class="col-md-12">

						<div class="card">
							<!-- Titulo de la ventana -->
							<div class="card-header">
								<strong class="card-title">Registrar contenido</strong>
							</div>
							<div class="card-body">
							
								
							
									<!-- Si hubo un error en el registro muestra el mensaje-->
									<div id="exito">

                                    </div>
							
									<div id="error">

                                    </div>
				                  
							

								<!-- Formulario -->
								<form:form id="formContenido" action="guardarContenido"	method="post" modelAttribute="contenido">
									
									<form:hidden id="contenido" path="contenido" class="form-control"	placeholder="Médico" aria-invalid="false" required="true" />
									
									<!-- Campo para escoger el tipo de asosiacion -->
									<div class="form-group">
										<label for="text-input" class=" form-control-label">Tipo de asosiación</label>
										<form:select path="tipoAsociacion" id="tipoAsociacion" class="form-control" onchange="cambiarDeTipoDeAsosiacion();">
											<form:option value="0"
												label="Seleccione el tipo de asosiacion" />
											<form:options items="${tiposAsociacion}" />
										</form:select>
									</div>
									<!-- Campo para escoger la asosiacion -->
									<div class="form-group">
										<label for="text-input" class=" form-control-label">Asosiación</label>
										<form:select path="asociacion" id="asosiacion"
											class="form-control">
											<form:option value="0" label="Seleccione la asosiacion" />
										</form:select>
									</div>

									<!-- Campo para digitar el tipo de contenido -->
									<div class="form-group">
										<label for="textarea-input" class=" form-control-label">Tipo de contenido</label>
										<form:select path="tipoContenido.id" id="tipoContenido"
											class="form-control" onchange="cambiarDeTipoDeContenido();">
											<form:options items="${tiposContenido}" />
										</form:select>
									</div>

									<!-- Campo para digitar el nombre -->
									<div id="titulo" class="form-group">
										<label for="text-input" class=" form-control-label">Título o nombre</label>
										<form:input id="nombre" path="nombre" class="form-control"	placeholder="Oficina de la universidad lanza su pagina oficial" aria-invalid="false" required="true" />
									</div>


									<div id="urll" class="form-group" style="display: none;">
										<label for="text-input" class=" form-control-label">URL a direccionar</label> 
										<input id="url" name="url" type="text"
											class="form-control" path="url"
											placeholder="http://gidis.ufps.edu.co:8080/SeGre/"
											aria-required="true" aria-invalid="false">
									</div>
									
									<div style="display: none;">
										<input type="file" id="imagen" name="imagen" onchange="cargarImagen()" accept="images/*">
									</div>

									<div style="display: none;">
										<input type="file" id="video" name="video" onchange="revisarVideo(this);" accept="video/*">
									</div>
																		
									<div style="display: none;">
										<input type="file" id="archivo" name=archivo onchange="revisarArchivo(this);" accept=".xlsx,.xls,image/*,.doc, .docx,.ppt, .pptx,.txt,.pdf">
									</div>	
								

									<div id="pagina" class="form-group" >
										<label for="textarea-input" class=" form-control-label">Contenido</label>
										
									<div class="toolbar">
									  <a href="#" data-command='undo'><i class='fa fa-undo'></i></a>
									  <a href="#" data-command='redo'><i class='fa fa-repeat'></i></a>
									  <div class="fore-wrapper">
									  	<i class='fa fa-font' style='color:#C96;'></i>
									  	
									    <div class="fore-palette">
									    
									    </div>
									  
									  </div>
									  <div class="back-wrapper"><i class='fa fa-font' style='background:#C96;'></i>
									    
									    <div class="back-palette">
									    
									    </div>
									  
									  </div>
									  <a href="#" data-command='bold'><i class='fa fa-bold'></i></a>
									  <a href="#" data-command='italic'><i class='fa fa-italic'></i></a>
									  <a href="#" data-command='underline'><i class='fa fa-underline'></i></a>
									  <a href="#" data-command='strikeThrough'><i class='fa fa-strikethrough'></i></a>
									  <a href="#" data-command='justifyLeft'><i class='fa fa-align-left'></i></a>
									  <a href="#" data-command='justifyCenter'><i class='fa fa-align-center'></i></a>
									  <a href="#" data-command='justifyRight'><i class='fa fa-align-right'></i></a>
									  <a href="#" data-command='justifyFull'><i class='fa fa-align-justify'></i></a>
									  <a href="#" data-command='indent'><i class='fa fa-indent'></i></a>
									  <a href="#" data-command='outdent'><i class='fa fa-outdent'></i></a>
									  <a href="#" data-command='insertUnorderedList'><i class='fa fa-list-ul'></i></a>
									  <a href="#" data-command='insertOrderedList'><i class='fa fa-list-ol'></i></a>
									  <a href="#" data-command='h1'>H1</a>
									  <a href="#" data-command='h2'>H2</a>
									  <a href="#" data-command='createlink'><i class='fa fa-link'></i></a>
									  <a href="#" data-command='unlink'><i class='fa fa-unlink'></i></a>
									  <a href="#" data-command='insertFile'><i class='fa fa-file-archive-o'></i></a>
									  <a href="#" data-command='insertimage'><i class='fa fa-file-image-o'></i></a>
									  <a href="#" data-command='insertVideo'><i class='fa fa-file-video-o'></i></a>
									  <a href="#" data-command='p'>P</a>
									  <a href="#" data-command='subscript'><i class='fa fa-subscript'></i></a>
									  <a href="#" data-command='superscript'><i class='fa fa-superscript'></i></a>
									</div>
									<div id='editor' contenteditable>
										<h1>Pagina contenido UFPS.</h1>
										<p>Aquí podrás agregar el contenido que desees ver en la página</p>
										
										<!-- PQWOEIRUTUMZNXBCVB -->
									</div>									
										
									</div>
									<!-- Boton para registrar los datos -->
									<button type="button" onclick="enviarDatos()"   class="btn btn-success">Registrar</button>
								</form:form>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- .animated -->
		</div>
		<!-- .content -->


	</div>
	<!-- /#right-panel -->

	<!-- Right Panel -->


	<!-- Carga de los archivos Javascript -->
	
	<script src="http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
	<script src="resources/assets/js/editor.js"></script>	
	
	<%@ include file="../General/scripts.jsp"%>

	

<script type="text/javascript">
	
	function enviarDatos(){
		
		// Aqui se obtiene el tipo de asociacion		
		var selectBoxTA = document.getElementById("tipoAsociacion");
		var selectedValueTA = selectBoxTA.options[selectBoxTA.selectedIndex].value;		
		
		// Aqui se obtiene la asociacion
		var selectBoxA = document.getElementById("asosiacion");
		var selectedValueA = selectBoxA.options[selectBoxA.selectedIndex].value;		
		
		// Aqui se obtiene el tipo de contenido
		var selectBoxTC = document.getElementById("tipoContenido");
		var selectedValueTC = selectBoxTC.options[selectBoxTC.selectedIndex].value;	
		
		// Aqui se obtiene el nombre o titlo
		var titulo = document.getElementById("nombre").value;
		
		// Variable para guardar el contenido
		var content = "";
		
		// Variable para guardar el tipo de contenido
		var tipoCon = "";
		
		// Aqui consulto si es link o pagina normal
		if(selectedValueTC == 2){
			content = document.getElementById("url").value;	
			titulo = "LINK";
			tipoCon = "LINK";
			
		}else{
			
			// Aqui obtengo el contenido
			var markup = document.documentElement.innerHTML;
			var arr = ((markup.split("contenteditable")[1]).split("<!-- PQWOEIRUTUMZNXBCVB -->")[0]).split("\n");
			arr.splice(0, 1);
			content = arr.join("");			
			tipoCon = "PAGINA NORMAL";
		}	
			
			var url = window.location.protocol + "//" + window.location.host + "/" + (window.location.pathname).split("/")[1];
			var formData = {
					  tipoAsociacion: 	selectedValueTA,
			          asociacion: 		selectedValueA,
			          tipoContenido: 	{
			        	  id:			selectedValueTC,
			        	  nombre:		tipoCon,
			        	  description:	''
			        	  
			          },
			          nombre: 			titulo,
			          contenido:		content,
			          url:				"",
			          id:				'0'
			};
			
			$.ajax({
				type : "POST",
				contentType : "application/json",
				url : url + "/servicios/recibirInformacion",
				data: JSON.stringify(formData),
				success : function(result) {
					
					if(result.trim() == 'REGISTRO EXITOSO'){
						
						$('#formContenido').trigger("reset");
						cambiarDeTipoDeContenido();
						pintarRegistroExitoso();
					}else{
						pintarRegistroNoExitoso();
					}
					
				},
				error : function(e) {
					pintarRegistroNoExitoso();
					console.log("ERROR: ", e);
				}
			});	
								
	}

		function cambiarDeTipoDeAsosiacion() {
			var selectBox = document.getElementById("tipoAsociacion");
			var selectedValue = selectBox.options[selectBox.selectedIndex].value;

			// DO POST
			var url = window.location.protocol + "//" + window.location.host + "/" + (window.location.pathname).split("/")[1];
			$.ajax({
				type : "POST",
				contentType : "application/json",
				url : url + "/servicios/asosiaciones",
				data : selectedValue,
				success : function(result) {

					var resultado = JSON.stringify(result);
					var asosiaciones = resultado.split("\",");
					eliminarOpciones(document.getElementById("asosiacion"));
					agregarOpcion(document.getElementById("asosiacion"), 0,"Seleccione la asosiacion");
					for (var i = 0; i < asosiaciones.length; i++) {

						var id = asosiaciones[i].substring(1,asosiaciones[i].length).split(":")[0].replace('\"', '').replace('\"', '');
						var nombre = asosiaciones[i].substring(1,asosiaciones[i].length).split(":")[1].replace('\"', '').replace('\"', '');
						if (nombre.indexOf("}") != -1) {
							nombre = nombre.replace('}', '');
						}
						agregarOpcion(document.getElementById("asosiacion"),id, nombre);
						
					}
				},
				error : function(e) {
					alert("Error!")
					console.log("ERROR: ", e);
				}
			});

		}

		function cambiarDeTipoDeContenido() {
			
			var selectBox = document.getElementById("tipoContenido");
			var selectedValue = selectBox.options[selectBox.selectedIndex].value;
			if(selectedValue == 2){
				
				document.getElementById("urll").setAttribute("style","");
				document.getElementById("pagina").setAttribute("style","display: none;");
				document.getElementById("titulo").setAttribute("style","display: none;");
			}else{
				document.getElementById("urll").setAttribute("style","display: none;");
				document.getElementById("pagina").setAttribute("style","");
				document.getElementById("titulo").setAttribute("style","");
			}
		}

		function eliminarOpciones(selectbox) {
			var i;
			for (i = selectbox.options.length - 1; i >= 0; i--) {
				selectbox.remove(i);
			}
		}

		function agregarOpcion(selectbox, id, nombre) {
			var opt = document.createElement("option");
			opt.value = id;
			opt.text = nombre;
			selectbox.options.add(opt);
		}

		function pintarRegistroExitoso(){
			var exito = document.getElementById("exito");
			
			var div = document.createElement("DIV");
			div.setAttribute("class","sufee-alert alert with-close alert-success alert-dismissible fade show");
			var texto = document.createTextNode("Registro exitoso");       
			div.appendChild(texto);
			
			var boton = document.createElement("BUTTON");
			boton.setAttribute("type","button");
			boton.setAttribute("class","close");
			boton.setAttribute("data-dismiss","alert");
			boton.setAttribute("aria-label","Close");
			
			var span = document.createElement("span");
			span.setAttribute("aria-hidden","true");
			var textos = document.createTextNode("X");       
			span.appendChild(textos);
			
			boton.appendChild(span);
			div.appendChild(boton);
			exito.appendChild(div);
		}
		
		function pintarRegistroNoExitoso(){
			var error = document.getElementById("error");
			
			var div = document.createElement("DIV");
			div.setAttribute("class","sufee-alert alert with-close alert-danger alert-dismissible fade show");
			var texto = document.createTextNode("Registro no exitoso");       
			div.appendChild(texto);
			
			var boton = document.createElement("BUTTON");
			boton.setAttribute("type","button");
			boton.setAttribute("class","close");
			boton.setAttribute("data-dismiss","alert");
			boton.setAttribute("aria-label","Close");
			
			var span = document.createElement("span");
			span.setAttribute("aria-hidden","true");
			var textos = document.createTextNode("X");       
			span.appendChild(textos);
			
			boton.appendChild(span);
			div.appendChild(boton);
			error.appendChild(div);
		}
		

	</script>   


</body>
</html>
