#!/bin/bash
#(c) ARG [2016]
#[RESIZE ALL IMGs DE LA CARPETA AL TAMAÑO INDICADO]

#INFO: http://www.imagemagick.org/Usage/resize/

width=500;
dir='d'"$width"'x'"$width"'/';

mkdir -p "$dir";

# ls | grep -v "^d[0-9].*x[0-9]" | grep -v ".*\.sh"

### REESCALAMOS TODAS LAS IMAGENES A 500x500 px Y LAS GUARDAMOS EN LA CARPETA
for file in $(ls | grep -v "^d[0-9].*x[0-9]" | grep -v ".*\.sh" 2> /dev/null);
do
	#name=${file##*/}
	base=${file%.*}; #Nombre sin extensión
	echo '# Convirtiendo: '"$file";
	convert "$file" -resize "$width"x"$width"\>  "$dir""$base"'.jpg';
	echo '';
done

### LAS IMAGENES DE LA CARPETA (YA REESCALADAS) LAS REDUCIMOS HASTA EL 75% DE CALIDAD
for file in $(ls "$dir" | grep -v "^d[0-9].*x[0-9]" | grep -v ".*\.sh" 2> /dev/null);
do
	#name=${file##*/}
	base=${file%.*}; #Nombre sin extensión
	echo '# Reduciendo: '"$dir""$file";
	convert "$dir""$file" -quality 75% "$dir""$base"'.jpg';
	echo '';
done
