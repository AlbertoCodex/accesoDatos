xquery version "3.1";

(: Ejercicio 1 Mostrar los títulos de los libros con la etiqueta "titulo". :)
for $b in doc("db/practicasXML/libros.xml/")//bookstore/book
return <titulo>{$b/title/text()}</titulo>

(: Ejercicio 2 Mostrar todos los libros cuyo precio sea 30 o inferior. :)
for $b in doc("db/practicasXML/libros.xml/")//bookstore/book
where $b/price <= 30
return $b

(: Ejercicio 3 Mostrar sólo el título sin atributos de los libros cuyo precio sea menor o igual a 30. :)
for $b in doc("db/practicasXML/libros.xml/")//bookstore/book
where $b/price <= 30
return $b/title/text()

(: Ejercicio 4 Mostrar el título y el autor de los libros del año 2005, y etiquetar cada uno de ellos con "lib2005". :)
for $b in doc("db/practicasXML/libros.xml/")//bookstore/book
where $b/year = 2005
return <lib2005>{$b/title,$b/author}</lib2005>

(: Ejercicio 5 Mostrar los años de publicación, primero con "for" y luego con "let" para comprobar la diferencia entre ellos, quedandonos sólo con el texto. Etiquetar la salida con "publicacion". :)
for $b in doc("db/practicasXML/libros.xml/")/bookstore/book/year/text()
let $d := doc("db/practicasXML/libros.xml/")/bookstore/book/year/text()
return <publicacion><for>{$b}</for>, <let>{$d}</let></publicacion>

(: Ejercicio 6 Ordena los libros por titulo. :)
for $b in doc("db/practicasXML/libros.xml/")/bookstore/book
order by $b/title
return $b

(: Ejercicio 7 Ordena los libros por año de publicación. :)
for $b in doc("db/practicasXML/libros.xml/")/bookstore/book
order by $b/year
return $b

(: Ejercicio 8 Mostrar cuántos libros hay, y etiquetarlo con "total". :)
for $b in doc("db/practicasXML/libros.xml/")
let $cn := count($b/bookstore/book)
return $cn

(: Ejercicio 9 Mostrar los títulos de los libros y al final una etiqueta con el número total de libros. :)
let $d := doc("db/practicasXML/libros.xml/")/bookstore/book
let $cn := count($d)
return <books>{<titles>{$d/title}</titles>, <count>{$cn}</count>}</books>

(: Ejercicio 10 Mostrar el título del libro, su precio y su precio con el IVA incluido, cada uno con su propia etiqueta. Ordénalos por precio con IVA. :)
for $b in doc("db/practicasXML/libros.xml/")/bookstore/book
let $iva := $b/price * 1.21
order by $iva
return <books>{$b/title, $b/price, <IVA>{$iva}</IVA>}</books>

(: Ejercicio 11 Mostrar la suma total de los precios de los libros con la etiqueta "total". :)
let $b := doc("db/practicasXML/libros.xml/")/bookstore/book/price
let $total := sum($b)
return <total>{$total}</total>

(: Ejercicio 12 Mostrar cada uno de los precios de los libros, y al final una nueva etiqueta con la suma de los precios. :)
let $b := doc("db/practicasXML/libros.xml/")/bookstore/book/price
let $total := sum($b)
return <prices>{<price>{$b}</price>, <total>{$total}</total>}</prices>

(: Ejercicio 13  Mostrar el título y el número de autores que tiene cada título en etiquetas diferentes. :)
for $b in doc("db/practicasXML/libros.xml/")/bookstore/book
let $total := count($b/author)
return <book>{$b/title, <nAutores>{$total}</nAutores>}</book>

(: Ejercicio 14 Mostrar en la misma etiqueta el título y entre paréntesis el número de autores que tiene ese título. :)

