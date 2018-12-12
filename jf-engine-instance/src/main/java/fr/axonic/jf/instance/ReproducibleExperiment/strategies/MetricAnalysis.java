package fr.axonic.jf.instance.ReproducibleExperiment.strategies;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MetricAnalysis {

    private List copyList(List list){
        List result = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            result.add(list.get(i));
        }
        return result;
    }


    public static boolean isReproducible(List <Double> list, Double acceptableThreshold){
        Double median = findMedian(list);
        Double MAD = MedianAbsoluteDeviation(list,median);
        Double standardDeviation = StandardDeviation(MAD,1.4826);
        Double deviationThreshold = deviationThreshold(standardDeviation,2.5);
        return numberOfOutliersAcceptable(list,median,deviationThreshold,acceptableThreshold);
    }

    private static Double findMedian(List<Double> list){
        sortList(list);
        double medianPosition = (double)(list.size() - 1) / 2;
        if (list.size() % 2 != 0){
            return list.get((int)medianPosition);
        }
        int indice1 = (int)(Math.floor(medianPosition));
        int indice2 = (int)(Math.ceil(medianPosition));
        Double firstMiddleValue = list.get(indice1);
        Double secondMiddleValue = list.get(indice2);
        Double median = (firstMiddleValue + secondMiddleValue) / 2;
        return median;
    }

    private static List sortList(List list){
        list.sort(Comparator.naturalOrder());
        return list;
    }

    private static Double MedianAbsoluteDeviation(List list, Double median){
        List<Double> deviations = new ArrayList<>();
        for (int i = 0; i < list.size(); i++){
            Double deviation = Math.abs((Double) list.get(i) - median);
            deviations.add(deviation);
        }
        return findMedian(deviations);
    }

    private static Double StandardDeviation(Double MAD, Double k){
        return k * MAD;
    }

    private static Double deviationThreshold(Double standardDeviation, Double n){
        return n * standardDeviation;
    }

    private static boolean numberOfOutliersAcceptable(List list, Double median, Double deviationThreshold, Double acceptableThreshold){
        List<Double> outliers = findOutliers(list,median,deviationThreshold);
        Double outliersProportion = ((double)outliers.size() / list.size());
        if (outliersProportion < acceptableThreshold){
            return true;
        }
        return false;
    }

    private static List<Double> findOutliers(List list, Double median, Double deviationThreshold){
        List<Double> deviations = computeDeviationsWithMedian(list, median);
        //printList(deviations);
        List<Double> outliers = new ArrayList();
        for (int i = 0; i < deviations.size(); i++){
            if (deviationThreshold < deviations.get(i) ) {
                outliers.add(deviations.get(i));
            }
        }
        return outliers;
    }

    private static List<Double> computeDeviationsWithMedian(List list, Double median){
        List<Double> deviations = new ArrayList();
        for (int i = 0; i < list.size(); i++){
            Double deviation = Math.abs((Double) list.get(i) - median);
            deviations.add(deviation);
        }
        return deviations;
    }

    private static void printList(List list){
        for (int j = 0; j < list.size(); j++){
            System.out.println(list.get(j));
        }
    }
}
