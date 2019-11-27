package sqlgenerator4;

import java.lang.reflect.Field;

public class Main {

    public static void main(String[] args)
            throws IllegalArgumentException {
//		Student student = new Student(1, "Max", "Mustermann");
//		print(student);
//		
//		Professor professor = new Professor(2, "Peter", "Maier", 10000);
//		print(professor);

        generateSql(Student.class);

    }

    public static void generateSql(Class<?> clazz) {

        Field[] fields = clazz.getDeclaredFields();

        Entity entityAnnotation = clazz.getAnnotation(Entity.class);

        String tableName = "";
        if (entityAnnotation.generatedName().equals(Entity.GeneratedName.LOWER)) {
            tableName = clazz.getSimpleName().toLowerCase();
        } else if (entityAnnotation.generatedName().equals(Entity.GeneratedName.CAPITAL)) {
            tableName = clazz.getSimpleName().toUpperCase();
        } else {
            tableName = clazz.getSimpleName();
        }

        String createStatement = "CREATE TABLE " + tableName + "(";
        for (int i = 0; i < fields.length; i++) {

            if (fields[i].getAnnotation(Column.class) != null) {

                Column columnAnnotation = fields[i].getAnnotation(Column.class);

                createStatement = createStatement + columnAnnotation.value();
                if (fields[i].getType().equals(String.class)) {
                    createStatement += " VARCHAR2(255)";
                } else {
                    createStatement += " INTEGER";
                }
                if (fields[i].getAnnotation(NotNull.class) != null) {
                    createStatement += " NOT NULL";
                }
                if (fields[i].getAnnotation(ID.class) != null) {
                    createStatement += " CONSTRAINT PK_" + tableName + " PRIMARY KEY";
                }
                if (fields[i].getAnnotation(Unique.class) != null) {
                    createStatement += " CONSTRAINT AK_" + tableName + " UNIQUE";
                }
                createStatement += ", ";
            }
        }

        createStatement = createStatement.trim().replaceAll(",$", "");
        createStatement += ");";
        System.out.println(createStatement);

    }

}
