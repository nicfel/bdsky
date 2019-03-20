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

/**
 * Implementation of the Log Normal Skygrid Prior for BDSKY from Gill et al., 2013
 * the paper link: https://academic.oup.com/mbe/article/30/3/713/1041171
 * 
 * @author Nicola Felix Müller
 */
public class LogNormalPrior extends Distribution {

    public Input<Function> xInput =
            new Input<>("x", "The x_i values should be in log space with mean 0", (Function) null);

    // sigma
    public Input<RealParameter> sigmaInput =
            new Input<RealParameter>("sigma", "The standard deviation parameter of the equilibrium distribution", (RealParameter) null);

    public Input<ParametricDistribution> x0PriorInput =
            new Input<>("x0Prior", "The prior to use on x0, or null if none.", (ParametricDistribution) null);

    public double calculateLogP() {

        double sigma = sigmaInput.get().getValue();
        double sigsq = sigma * sigma;


        double[] x = xInput.get().getDoubleValues();

        
        double const_fact = Math.log(sigma*Math.pow(2*Math.PI, 0.5));
        
        ParametricDistribution x0Prior = x0PriorInput.get();
        logP = 0.0;
        // calculate the mean value
        double[] x_mean = new double[1];
        x_mean[0] = 0;
//        for (int i = 0; i < x.length; i++)
//        	logP += Math.log(x0Prior.density(x[i]));
        
               
		logP = Math.log(x0Prior.density(x[x.length-1]));
//		logP += Math.log(x0Prior.density(x[50]));
		logP += Math.log(x0Prior.density(x[0]));
		
		
        for (int i = 1; i < x.length; i++) {
            double diff = x[i] - x[i-1];
            logP -= const_fact + diff*diff/(2*sigsq);
        }
        
       

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
