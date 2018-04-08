package dfva_java.client;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
*
* @author luisza
*/
public class SettingsManager {

	private static SettingsManager cm = new SettingsManager();
	private Path path;
	private Properties props;
	private String secretDir = null;
	private FileManager filemanager = null;
	
    public Path get_config_dir() throws IOException {
        // Se asegura que siempre exista el directorio de configuracion
        path = FileSystems.getDefault().getPath(System.getProperty("user.home"), ".dfva_java");
        if (!Files.isDirectory(path)) {
            Files.createDirectories(path);
        
    	}
        return path;
    }
    
    public Path get_path_config_file(String name) throws IOException{
    	if (path == null){
    		path = this.get_config_dir();
    		path = path.getFileSystem().getPath(path.toString(), name);
    	}
        return path;
    }
    
    public String get_config_file(String name) throws IOException{
        return this.get_path_config_file(name).toString();
    }

	public Path getPath() {
		return path;
	}

	public void setPath(Path path) {
		this.path = path;
	}

	public void setPath(String path) {
		this.path =FileSystems.getDefault().getPath(path);
	}

	private SettingsManager() {
		super();
		this.path = null;
		props = new Properties();
		filemanager = new FileManager();
	}
	
    public static SettingsManager getInstance() {
        // retorna la unica instancia
        return cm;

    }
    
    public String getProperty(String key) {
        return props.getProperty(key, "");
    }

    public void setProperty(String key, String value) {
        props.setProperty(key, value);
    }

    
    private String get_config_file() throws IOException {
        //Retorna el archivo de configuracion
    	String dev="";
    	if(this.path == null){
    		dev = this.get_config_file("config.properties");
    	}else{
    		dev = this.path.toString();
    	}
    	return dev;
    }

    public void load_config() {
        // carga las configuraciones desde un archivo de texto
        File configFile;
        try {
            configFile = new File(this.get_config_file());
            InputStream inputStream = new FileInputStream(configFile);
            props.load(inputStream);
            inputStream.close();
        } catch (IOException ex) {
            Logger.getLogger(SettingsManager.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void save_config() {
        // Guarda las configuraciones en un archivo de texto
        File configFile = null;
        FileWriter writer = null;
        //props.setProperty("formato", "json");
        try {
            writer = new FileWriter(this.get_config_file());
            props.store(writer, "dfva settings");

        } catch (IOException ex) {
            Logger.getLogger(SettingsManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(SettingsManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public Settings getSettings(){
    	if(this.secretDir == null){
     	   this.secretDir = FileSystems.getDefault().getPath(
     				System.getProperty("user.home"), ".dfva_java/").toString();
     	}
    	Settings conf = new Settings();
    	this.load_config();
    	   	
    	conf.publicCertificate=filemanager.read_file(this.secretDir+"/dfva_certificate.pem");
    	conf.publicKey=filemanager.read_file(this.secretDir+"/dfva_public_key.pem");
    	conf.privateKey=filemanager.read_file(this.secretDir+"/dfva_private_key.pem");
     	
    	conf.baseUrl = props.getProperty("baseUrl", conf.baseUrl );
    	conf.authenticate = props.getProperty("authenticate", conf.authenticate );
    	conf.sign = props.getProperty("sign", conf.sign);
    	conf.validate_certificate = props.getProperty("validate_certificate", conf.validate_certificate );
    	conf.validate_document = props.getProperty("validate_document", conf.validate_document );
    	conf.suscriptor_conected = props.getProperty("suscriptor_conected", conf.suscriptor_conected );
    	conf.autenticate_show = props.getProperty("autenticate_show", conf.autenticate_show );
    	conf.sign_show = props.getProperty("sign_show", conf.sign_show );	
    	conf.institution = props.getProperty("institution", conf.institution );	
    	conf.notificationURL = props.getProperty("notificationURL", conf.notificationURL );	
    	conf.algorithm=props.getProperty("algorithm", conf.algorithm );		
    	conf.sign_delete=props.getProperty("sign_delete", conf.sign_delete );
    	conf.autenticate_delete = props.getProperty("autenticate_delete", conf.autenticate_delete );
    	return conf;
    }
    
    
    public void setSettings(Settings conf){
    	if(this.secretDir == null){
     	   this.secretDir = FileSystems.getDefault().getPath(
     				System.getProperty("user.home"), ".dfva_java/").toString();
     	}    	
    	
    	filemanager.write_file(this.secretDir+"/dfva_certificate.pem", conf.publicCertificate);

    	filemanager.write_file(this.secretDir+"/dfva_public_key.pem", conf.publicKey);
    	filemanager.write_file(this.secretDir+"/dfva_private_key.pem", conf.privateKey);
  	    	
    	setProperty("baseUrl", conf.baseUrl);
    	setProperty("authenticate", conf.authenticate);
    	setProperty("sign", conf.sign);
    	setProperty("validate_certificate", conf.validate_certificate);
    	setProperty("validate_document", conf.validate_document);
    	setProperty("suscriptor_conected", conf.suscriptor_conected);
    	setProperty("autenticate_show", conf.autenticate_show);
    	setProperty("autenticate_delete", conf.autenticate_delete);
    	setProperty("sign_show", conf.sign_show);
    	setProperty("sign_delete", conf.sign_delete);
    	setProperty("institution", conf.institution);
    	setProperty("notificationURL", conf.notificationURL);
    	setProperty("algorithm", conf.algorithm);
    	save_config();
  
    }
    
    public Settings get_and_create_settings(){
    	Settings dev = new Settings();
		try {
			// primero  pruebo si existe
			if(this.path == null){
				String cpath = get_config_file("config.properties");
			}else{
				if(!Files.exists(this.path)){
					throw new Exception();
				}
			}
			dev = getSettings();
		} catch (Exception e) {
			e.printStackTrace();
			setSettings(dev);
		}
    	return dev;
    }

	public String getSecretDir() {
		return secretDir;
	}

	public void setSecretDir(String secretDir) {
		this.secretDir = secretDir;
	}
}