public class UnitConverter {

    private static void usage() {
        System.out.println("quantity startingUnit endingUnit");
    }

    public static void main(String[] args) {
        if (args.length < 3) {
            usage();
            System.exit(1);
        }
    }
}
