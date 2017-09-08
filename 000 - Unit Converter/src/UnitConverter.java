import java.util.Formatter;
import java.util.HashMap;
import java.util.Iterator;

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

abstract class Unit {
    /*
    units contains names of units and maps them to their exponents
    put names must be in lower case
     */
    private static HashMap<String, Double> units;

    abstract double getResult();

    abstract void availableUnits();
}

class LengthUnit extends Unit {

    private double result;

    private static HashMap<String, Double> units = new HashMap<String, Double>();

    static {
        units.put("kilometres", 3.0);
        units.put("metres", 0.0);
        units.put("decimetres", -1.0);
        units.put("centimetres", -2.0);
        units.put("micrometres", -3.0);
        units.put("micrometres", -6.0);
    }

    LengthUnit() {
    }

    LengthUnit(double quantity, String startingUnit, String endingUnit) {
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

    @Override
    public void availableUnits() {
        Iterator<String> iterator = units.keySet().iterator();
        System.out.println("available length units: ");
        while (iterator.hasNext())
            System.out.println(" " + iterator.next());
    }

    @Override
    public double getResult() {
        return result;
    }
}

public class UnitConverter {

    private static void usage() {

        System.out.println("usage:");
        System.out.println("    x quantity startingUnit endingUnit");
        System.out.println("    where x is one of the following:");
        System.out.println("        a - area units");
        System.out.println("        l - length units");
        System.out.println("type \"units\" for list of available units");
    }


    private static void availableUnits() {
        LengthUnit lu = new LengthUnit();
        lu.availableUnits();
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

                Unit u;
                switch (type) {
                    case 'l':
                        u = new LengthUnit(quantity, startingUnit, endingUnit);
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
