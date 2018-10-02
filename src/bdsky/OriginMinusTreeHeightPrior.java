package bdsky;

import beast.core.Distribution;
import beast.core.Function;
import beast.core.Input;
import beast.core.Input.Validate;
import beast.core.State;
import beast.core.parameter.RealParameter;
import beast.evolution.tree.Tree;
import beast.math.distributions.ParametricDistribution;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Prior on the origin minus the tree height
 * 
 */
public class OriginMinusTreeHeightPrior extends Distribution {

    public Input<RealParameter> originInput =
    		new Input<>("origin", "The x_i values", Validate.REQUIRED);
    
    public Input<Tree> treeInput =
    		new Input<>("tree", "The x_i values", Validate.OPTIONAL);

    public Input<ParametricDistribution> x0PriorInput =
            new Input<>("prior", "The prior to use on x0, or null if none.", (ParametricDistribution) null);

    public double calculateLogP() {

        ParametricDistribution x0Prior = x0PriorInput.get();

        double diff = originInput.get().getValue();
        if (treeInput.get()!=null)
        	diff -= treeInput.get().getRoot().getHeight();
        
        logP = Math.log(x0Prior.density(diff));      

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
