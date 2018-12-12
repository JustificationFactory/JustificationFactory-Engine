package fr.axonic.jf.instance.ReproducibleExperiment.strategies;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MetricAnalysis {

    private List metrics;

    public MetricAnalysis(List<Double> metricsList){
        this.metrics = this.copyList(metricsList);
    }

    private List copyList(List list){
        List result = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            result.add(list.get(i));
        }
        return result;
    }


    private boolean isReproducible(List<Double> list){
        Double median = this.findMedian(list);
        Double MAD = this.MedianAbsoluteDeviation(list,median);
        Double standardDeviation = this.StandardDeviation(MAD,1.4826);
        Double deviationThreshold = this.deviationThreshold(standardDeviation,2.5);
        return this.numberOfOutliersAcceptable(list,median,deviationThreshold,0.15);
    }

    private Double findMedian(List<Double> list){
        this.sortList(list);
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

    private List sortList(List list){
        list.sort(Comparator.naturalOrder());
        return list;
    }

    private Double MedianAbsoluteDeviation(List list, Double median){
        List<Double> deviations = new ArrayList<>();
        for (int i = 0; i < list.size(); i++){
            Double deviation = Math.abs((Double) list.get(i) - median);
            deviations.add(deviation);
        }
        return this.findMedian(deviations);
    }

    private Double StandardDeviation(Double MAD, Double k){
        return k * MAD;
    }

    private Double deviationThreshold(Double standardDeviation, Double n){
        return n * standardDeviation;
    }

    private boolean numberOfOutliersAcceptable(List list, Double median, Double deviationThreshold, Double acceptableThreshold){
        List<Double> outliers = this.findOutliers(list,median,deviationThreshold);
        Double outliersProportion = ((double)outliers.size() / list.size());
        if (outliersProportion < acceptableThreshold){
            return true;
        }
        return false;
    }

    private List<Double> findOutliers(List list, Double median, Double deviationThreshold){
        List<Double> deviations = this.computeDeviationsWithMedian(list, median);
        this.printList(deviations);
        List<Double> outliers = new ArrayList();
        for (int i = 0; i < deviations.size(); i++){
            if (deviationThreshold < deviations.get(i) ) {
                outliers.add(deviations.get(i));
            }
        }
        return outliers;
    }

    private List<Double> computeDeviationsWithMedian(List list, Double median){
        List<Double> deviations = new ArrayList();
        for (int i = 0; i < list.size(); i++){
            Double deviation = Math.abs((Double) list.get(i) - median);
            deviations.add(deviation);
        }
        return deviations;
    }

    private void printList(List list){
        for (int j = 0; j < list.size(); j++){
            System.out.println(list.get(j));
        }
    }
}
