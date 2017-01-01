package hibernate;

import java.lang.reflect.Field;

public class Student {

    private String id_;
    private String name_;

    public String getId() {
        return id_;
    }

    public void setId(String id) {
        id_ = id;
    }

    public String getName() {
        return name_;
    }

    public void setName(String name) {
        name_ = name;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Class c = getClass();
        sb.append("[");
        sb.append(c.getCanonicalName());
        sb.append(": ");

        Field[] fields = c.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            String name = fields[i].getName();
            if (name.endsWith("_")) {
                name = name.substring(0, name.length() - 1);
            }
            sb.append(name);
            sb.append(" = ");
            Object value = "";
            try {
                value = fields[i].get(this);
            } catch (Exception e) {
            }
            sb.append(value == null ? "null" : value.toString());
            if (i < fields.length - 1) {
                sb.append(", ");
            }
        }

        sb.append("]");
        return sb.toString();
    }
}
