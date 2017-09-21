public class Quine {
    public static void main(String[] args) {
        char comma = 44;
        char tab = 9;
        char q = 34;
        String[] output = {
                "public class Quine {",
                "   public static void main (String[] args) {",
                "       char comma = 44;",
                "       char tab = 9;",
                "       char q = 34;",
                "       String[] output = {",
                "       };",
                "       for (int i = 0; i < 6; i++)",
                "           System.out.println(output[i]);",
                "       for (String out : output) {",
                "           for (int i = 4; i > 0; i--)",
                "               System.out.print(tab);",
                "           System.out.println(q + out + q + comma);",
                "       }",
                "       for (int i = 6; i < output.length; i++)",
                "           System.out.println(output[i]);",
                "   }",
                "}",
        };
        for (int i = 0; i < 6; i++)
            System.out.println(output[i]);
        for (String out : output) {
            for (int i = 4; i > 0; i--)
                System.out.print(tab);
            System.out.println(q + out + q + comma);
        }
        for (int i = 6; i < output.length; i++)
            System.out.println(output[i]);
    }
}
