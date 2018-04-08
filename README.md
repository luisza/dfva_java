# dfva cliente para java

Este cliente permite comunicarse con [DFVA](https://github.com/luisza/dfva) para proveer servicios de firma digital para Costa Rica a institutiones.

Utiliza Maven para manejar sus dependencias, si se desea usar fuera de maven se debe usar

* apache httpclient  [descargar](https://mvnrepository.com/artifact/org.apache.httpcomponents/httpclient/4.3.6)
* json-simple [descargar](https://mvnrepository.com/artifact/org.glassfish/javax.json/1.0.4)
* bouncycastle  [descargar](https://mvnrepository.com/artifact/org.bouncycastle/bcprov-jdk15on/1.54)

# Modo de uso 

Este cliente permite:

* Autenticar personas y verificar estado de autenticación
* Firmar documento xml, odf, ms office y verificar estado de firma durante el tiempo que el usuario está firmando
* Validar un certificado emitido con la CA nacional de Costa Rica provista por el BCCR
* Validar un documento XML firmado.
* Revisar si un suscriptor está conectado.

##  Ejemplo de uso

```
Settings settings = new Settings();
settings.publicKey = "llave pública dada por dfva"
settings.publicCertificate  = "Certificado dado por dfva"
settings.privateKey = "Llave privada dada por dfva"
settings.institution = "Código de la institución dado por dfva"
settings.notificationURL = "url de notificación"
InstitutionClient client = new InstitutionClient(settings);
```

**Nota:** notificationURL debe estar registrado en dfva o ser N/D en clientes no web

Si se desea autenticar y revisar estado de la autenticación

```
JSONObject authres = client.authenticate("04-0212-0119");
System.out.println(authres.toJSONString());

String code = (String) authres.get("code");
JSONObject authresshow = client.authenticate_check("04-0212-0119", code);
System.out.println(authresshow.toJSONString());
```
Si se desea revisar si un suscriptor está conectado

```
Boolean sucres = client.suscriptor_connected("04-0212-0119");
System.out.println(sucres);
```

Si se desea firmar y revisar estado de la firma.

```
document = new ByteArrayInputStream(
    "DOCU DE EJEMPLO".getBytes("UTF-8"));
JSONObject signres = client.sign(
    "04-0212-0119", 
    document, 
    "xml", 
    "Texto de resumen",
    "sha512");

System.out.println(signres.toJSONString());

/** Sign Show */
String code = (String) signres.get("code");
JSONObject signresshow = client.sign_check("04-0212-0119", code);
System.out.println(signresshow.toJSONString());
			
```

**Nota:** La revisión de estado de la autenticación/firma no es necesaria en servicios web ya que estos son notificados por en la URL de institución proporcionado.

Si se desea validar un certificado

```
document = new ByteArrayInputStream(
    "CERTIFICADO DE EJEMPLO".getBytes("UTF-8"));
JSONObject validateCertres = client.validate_certificate( document);
System.out.println(validateCertres.toJSONString());	
```

Si se desea validar un documento

```
document = new ByteArrayInputStream(
    "DOCUMENTO DE EJEMPLO".getBytes("UTF-8"));
JSONObject validateDocres = client.validate_document(document);
System.out.println(validateDocres.toJSONString());	
```
