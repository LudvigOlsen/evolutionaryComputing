package charles.utils;

public class ScaleToRange {

    // TODO Make generic version
//    public <T> T scaleToRange(T value, T prevMin, T prevMax, T newMin, T newMax) {
//
//        return ((value - prevMin) / (prevMax - prevMin)) * (newMax - newMin) + newMin;
//
//    }

    public static double scaleToRange(double value, double prevMin, double prevMax, double newMin, double newMax) {

        return ((value - prevMin) / (prevMax - prevMin)) * (newMax - newMin) + newMin;

    }

}
