package robotData;


public  class ExternalInteraction {
	private String  exportFile , importFile , arduinoPort ;

	public ExternalInteraction(String exportFile, String importFile, String arduinoPort) {
		super();
		this.exportFile = exportFile;
		this.importFile = importFile;
		this.arduinoPort = arduinoPort;
	}
	public ExternalInteraction(String exportFile, String importFile) {
		this.exportFile = exportFile;
		this.importFile = importFile;

	}
	public ExternalInteraction(String exportFile) {
		this.exportFile = exportFile;
		
	}
	public String getExportFile() {
		return exportFile;
	}
	public void setExportFile(String exportFile) {
		this.exportFile = exportFile;
	}
	public String getImportFile() {
		return importFile;
	}
	public void setImportFile(String importFile) {
		this.importFile = importFile;
	}
	public String getArduinoPort() {
		return arduinoPort;
	}
	public void setArduinoPort(String arduinoPort) {
		this.arduinoPort = arduinoPort;
	}
	@Override 
	public String toString() {
		return "	Interaction avec les appareils ou fichiers extérieurs :\n" 
				+ "Avec Arduino :" + arduinoPort + "\n Exporter du fichier :" + exportFile + "\n Importer d fichier" + importFile;
	}
}