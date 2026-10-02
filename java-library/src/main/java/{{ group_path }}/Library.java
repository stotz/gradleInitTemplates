package {{ group }};

/**
 * {{ project_name }} - library entry point.
 */
public final class Library {

    private Library() {
    }

    /**
     * Returns a greeting for the given name.
     *
     * @param name the name to greet, must not be null
     * @return the greeting
     */
    public static String greet(String name) {
        if (name == null) {
            throw new IllegalArgumentException("name must not be null");
        }
        return "Hello from " + name + "!";
    }
}
