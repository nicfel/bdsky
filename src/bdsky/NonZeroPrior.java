package bdsky;

import beast.core.Distribution;
import beast.core.Function;
import beast.core.Input;
import beast.core.State;
import beast.core.parameter.RealParameter;
import beast.math.distributions.ParametricDistribution;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.swing.plaf.synth.SynthSeparatorUI;

/**
 * Implementation of the Log Normal Skygrid Prior for BDSKY from Gill et al., 2013
 * the paper link: https://academic.oup.com/mbe/article/30/3/713/1041171
 * 
 * @author Nicola Felix Müller
 */
public class NonZeroPrior extends Distribution {

    public Input<Function> xInput =
            new Input<>("x", "The x_i values", (Function) null);

    // sigma
//    public Input<RealParameter> sigmaInput =
//            new Input<RealParameter>("sigma", "The standard deviation parameter of the equilibrium distribution", (RealParameter) null);

    public Input<ParametricDistribution> x0PriorInput =
            new Input<>("x0Prior", "The prior to use on x0, or null if none.", (ParametricDistribution) null);

    public double calculateLogP() {


        ParametricDistribution x0Prior = x0PriorInput.get();

        double[] x = xInput.get().getDoubleValues();
        logP=0;
        // calculate the mean
        double mean=0.0;
        int count=0;
        int first_non_zero = -1;
        for (int i = 0; i < x.length; i++){
        	if (x[i]>0){
        		mean+=x[i];
        		count++;
        		
        		if (first_non_zero==-1)
        			first_non_zero = i;
        	}
        }
		logP += Math.log(x0Prior.density(x[first_non_zero]));
	
		return logP;
    }

    @Override
    public List<String> getArguments() {
        return null;
    }

    @Override
    public List<String> getConditions() {
        return null;
    }

    @Override
    public void sample(State state, Random random) {

    }
}
