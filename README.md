# peruapps

Este proyecto permite a diferente usuarios compartir lugares en común.

Se ha utilizado distintos componentes de firebase, como la authentication,database y storage. 
Entre las actividades que realiza se encuentran: 
- Permite a un usuario acceder mediante un usuario y contraseña.
- Permite crear un nuevo post, tomando una fotografía y colocando una descripción
- Cuando un nuevo usuario crea un post, estas imágenes se almacenan en cloud storage
- Se utilizó el ORM greenDAO para la persistencia de datos (Se creó un módulo)
- Permite visualizar los post de cualquier usuario
- Permite visualizar mis post
- Cada 30 minutos actualiza la posición del dispositivo

Se ha utilizado el patrón de diseño mvp, y se han colocado distintas capas:
- adaptar: Contiene el adapter que listará en el recyclerView todos los post. 
- login: Esta capa permite desarrollar todas las actividades con respecto al login y crear cuenta nueva. Para ello se han creado 4 capas:
   - Interactor 
   - presenter 
   - view
   - repository: Se conecta con firebase y maneja toda la data
- modeldb: Aquí se encuentran todos los modelos de datos, esta carpeta se generó con el greenDao.
- post: Se encuentran las vistas que permitirán realizar los nuevos post.(Aquí se debió realizar las 4 capas(mvp) al igual en la carpeta login, por motivo de tiempo solo se crearon las vistas)
- utilitarios: contiene archivos de configuración, se creó el archivo para almacenara la sesión, y una clase clase GPSTracker para la locación.
- view: Contiene los fragmentos del BottomBar.




