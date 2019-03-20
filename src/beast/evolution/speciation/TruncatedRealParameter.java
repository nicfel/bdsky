package beast.evolution.speciation;

import java.util.Arrays;

import beast.core.CalculationNode;
import beast.core.Input;
import beast.core.Input.Validate;
import beast.core.parameter.RealParameter;

public class TruncatedRealParameter extends CalculationNode {
	// those the same as RealParameter, but is truncated in size
    final public Input<Integer> offsetInput = new Input<>("offset", "start value", 0);
    final public Input<RealParameter> parameterInput = new Input<>("parameter", "parameter", Validate.REQUIRED);

    private int dimension;
    private RealParameter param;

    
    public int getDimension() {
        return dimension;
    }
    
    public Double[] getTruncatedValues() {
    	Double[] tmp = param.getValues();    	
        return Arrays.copyOf(tmp, tmp.length - offsetInput.get());
    }
    
    public Double getValue() {
    	return param.getValue();
    }


	@Override
	public void initAndValidate() {
		param = parameterInput.get();		
		dimension = param.getDimension()-offsetInput.get();
	}    




}
