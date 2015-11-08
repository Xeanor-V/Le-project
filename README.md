//clonar repositorio	
git clone https://github.com/Xeanor-V/Le-project.git

// entrar a la carpeta del repositorio
cd Le-project/

//modifacar configuracion para poder subir archivos y modificaciones
git add remote add orign https://github.com/Xeanor-V/Le-project.git

//recomendacion cada dia que entren y quieran modificar o ver los avances (actualiza el repositorio)
git pull

// despues de hacer una modificacion o subir un nuevo archivo en la carpeta (para verificar que si se modifico)
git status // debe aparecer el nombre de los archivos modificados que no se han actualizado

git add //agregar el nombre de los archivos que se modificaron o subieron

//Esta linea se tiene que ingrasar cada que se realiza una modifcacion
// el mensaje debe de contener quien realizo la moifiacion y que fue lo que modifico o subio
git commit -m "mensaje "

//Subir todos lo archivos agregados con git add
git push 

//para verificar que todo los cambios se hayan realizado en el repositorio
git status
