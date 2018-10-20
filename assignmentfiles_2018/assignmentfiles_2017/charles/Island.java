package charles;

import charles.initializers.BasicInitializer;

public class Island extends Population {

    // Special settings Object

    private BasicInitializer initializer;

    public Island() {
        super();
    }

    public BasicInitializer getInitializer() {
        return initializer;
    }

    public void setInitializer(BasicInitializer initializer) {
        this.initializer = initializer;
    }
}
