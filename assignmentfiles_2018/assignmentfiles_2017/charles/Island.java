package charles;

public class Island extends Population {

    // Special settings Object

    private Initializer initializer;

    public Island() {
        super();
    }

    public Initializer getInitializer() {
        return initializer;
    }

    public void setInitializer(Initializer initializer) {
        this.initializer = initializer;
    }
}
