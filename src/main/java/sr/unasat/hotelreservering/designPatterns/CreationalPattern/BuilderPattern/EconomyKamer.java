package sr.unasat.hotelreservering.designPatterns.CreationalPattern.BuilderPattern;

import java.util.ArrayList;
import java.util.Collections;

public class EconomyKamer extends KamerBuilder{
    public void buildComfort(){
        kamer.setComfort("3 Star");
    }

    public void buildFeatures(){
        ArrayList<String> features = new ArrayList<String>();
        Collections.addAll(features, "Bed", "Tv", "Douche", "Toilet", "Balkon", "Airco");
        kamer.setFeatures(features);
    }
}
