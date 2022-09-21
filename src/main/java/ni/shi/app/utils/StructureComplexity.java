package ni.shi.app.utils;

public enum StructureComplexity {

    SIMPLE("simple"),
    SIMPLE_OBJECT("simple_object"),
    SIMPLE_ARRAY("simple_array"),
    OBJECTS_ARRAY("objects_array"),
    COMPLEX("complex");

    private final String name;

    public String getName() {
        return this.name;
    }

    StructureComplexity(String name) {
        this.name = name;
    }

    public boolean equals(String str) {
        return this.name.equals(str);
    }
}
