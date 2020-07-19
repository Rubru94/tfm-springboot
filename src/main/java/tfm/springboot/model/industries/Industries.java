package tfm.springboot.model.industries;

public enum Industries {
	
    LOGISTIC("Logistic"),
    SERVICES("Services");

    private String name;

    private Industries(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
}
