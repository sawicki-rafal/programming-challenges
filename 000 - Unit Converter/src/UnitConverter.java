interface Unit {
    double getResult();
}

class LengthUnit implements Unit {
    private double result;

    LengthUnit(double quantity, String startingUnit, String endingUnit) {
        double startExponent = 0;
        switch (startingUnit.toLowerCase()) {
            case "kilometres":
                startExponent = 3;
                break;
            case "decimetres":
                startExponent = -1;
                break;
            case "centimetres":
                startExponent = -2;
                break;
        }
    }

    @Override
    public double getResult() {
        return 0;
    }
}

public class UnitConverter {

    private static void usage() {

        System.out.println("quantity x startingUnit endingUnit");
        System.out.println("where x is one of the following:");
        System.out.println("     a - area units");
        System.out.println("     l - length units");
    }

    public static void main(String[] args) {
        if (args.length < 3) {
            usage();
            System.exit(1);
        }
    }
}
