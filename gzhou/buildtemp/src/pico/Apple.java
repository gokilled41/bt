package pico;

import org.picocontainer.annotations.Inject;

public class Apple {
    @Inject
    private Orange orange;
    @Inject
    private Pear pear;
    @Inject
    private Banana banana;

    public Orange getOrange() {
        return orange;
    }

    public Pear getPear() {
        return pear;
    }

    public Banana getBanana() {
        return banana;
    }

    // methods  
}
