import java.util.Formatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

class UnitNotFoundException extends Exception {
    UnitNotFoundException() {
    }

    /**
     * @param probablyNotUnit is not recognized unit
     */
    UnitNotFoundException(String probablyNotUnit) {
        super(probablyNotUnit);
    }
}

class UnknownTypeException extends Exception {
    UnknownTypeException() {
    }

    UnknownTypeException(char unknownType) {
        super(Character.toString(unknownType));
    }
}

class NotANumberException extends Exception {
    NotANumberException() {
    }

    NotANumberException(String msg) {
        super(msg);
    }
}

abstract class Units {
    /*
    units contains names of units and maps them to their exponents
    put names must be in lower case
     */
    protected static LinkedHashMap<String, Double> units = new LinkedHashMap<String, Double>();

    private double result = 0.00;

    Units() {
    }

    Units(double quantity, String startingUnit, String endingUnit) {
        double startExponent, endExponent;
        try {
            startExponent = getExponent(startingUnit);
            endExponent = getExponent(endingUnit);
            result = quantity * Math.pow(10, startExponent - endExponent);

        } catch (UnitNotFoundException e) {
            System.err.println("unit: " + e.getMessage() + " is not recognized by application.");
            availableUnits();
            System.exit(1);
        }

    }

    private static double getExponent(String unit) throws UnitNotFoundException {
        if (units.containsKey(unit.toLowerCase()))
            return units.get(unit.toLowerCase());
        else
            throw new UnitNotFoundException(unit);

    }

    double getResult() {
        return result;
    }

    void availableUnits() {
        Iterator<String> iterator = units.keySet().iterator();
        System.out.println("available units: ");
        while (iterator.hasNext())
            System.out.println(" " + iterator.next());
    }
}

class LengthUnits extends Units {

    static {
        units.put("kilometres", 3.0);
        units.put("metres", 0.0);
        units.put("decimetres", -1.0);
        units.put("centimetres", -2.0);
        units.put("micrometres", -3.0);
        units.put("micrometres", -6.0);
    }

    LengthUnits() {
    }

    LengthUnits(double quantity, String startingUnit, String endingUnit) {
        super(quantity, startingUnit, endingUnit);
    }
}

class WeightUnits extends Units {

    static {
        units.put("ton", 3.0);
        units.put("kilogram", 0.0);
        units.put("decagram", -2.0);
        units.put("gram", -3.0);
        units.put("milligram", -6.0);
    }

    WeightUnits() {
    }

    WeightUnits(double quantity, String startingUnit, String endingUnit) {
        super(quantity, startingUnit, endingUnit);
    }

}

class AreaUnits extends Units {

    static {
        units.put("square.kilometres", 6.0);
        units.put("square.metres", 0.0);
        units.put("square.decimetres", -2.0);
        units.put("square.centimetres", -4.0);
        units.put("square.micrometres", -6.0);
        units.put("square.micrometres", -12.0);
    }

    AreaUnits() {
    }

    AreaUnits(double quantity, String startingUnit, String endingUnit) {
        super(quantity, startingUnit, endingUnit);
    }

}

public class UnitConverter {

    private static void usage() {

        System.out.println("usage:");
        System.out.println("    x quantity startingUnit endingUnit");
        System.out.println("    where x is one of the following:");
        System.out.println("        l - length units");
        System.out.println("        a - area units");
        System.out.println("        w - weight units");
        System.out.println("type \"units\" for list of available units");
    }


    private static void availableUnits() {
        Units[] arrayOfUnits = {new LengthUnits(), new AreaUnits(), new WeightUnits()};
        arrayOfUnits[0].availableUnits();
    }

    public static void main(String[] args) {
        try {
            if (args.length == 1 && (args[0].toLowerCase()).equals("units")) {
                availableUnits();
            } else if (args.length < 4) {
                usage();
                System.exit(1);
            } else {
                char type = args[0].charAt(0);
                double quantity;
                try {
                    quantity = Double.parseDouble(args[1]);
                } catch (NumberFormatException e) {
                    throw new NotANumberException(args[1]);
                }
                String startingUnit = args[2];
                String endingUnit = args[3];


                Units u;
                switch (type) {
                    case 'l':
                        u = new LengthUnits(quantity, startingUnit, endingUnit);
                        break;
                    case 'a':
                        u = new AreaUnits(quantity, startingUnit, endingUnit);
                        break;
                    case 'w':
                        u = new WeightUnits(quantity, startingUnit, endingUnit);
                        break;
                    default:
                        throw new UnknownTypeException(type);
                }
                Formatter formater = new Formatter(System.out);
                if (u.getResult() > 10000.0 || u.getResult() < 0.01) {
                    formater.format("%e %s\n", u.getResult(), endingUnit);
                } else {
                    formater.format("%.2f %s\n", u.getResult(), endingUnit);
                }
            }
        } catch (UnknownTypeException e) {
            System.out.println("unknown type: " + e.getMessage());
            usage();
            System.exit(0);
        } catch (NotANumberException e) {
            System.out.println("value: " + e.getMessage() + " is not a number");
            usage();
            System.exit(0);
        }
    }
}
