package org.mckilliam.optimisation;

import Jama.Matrix;
import pubsim.Integration;
import pubsim.VectorFunctions;

/**
 * Class that numerically computes multidimensional integrals.
 * @author Robby McKilliam
 */
public abstract class AutoIntegralFunction implements FunctionAndIntegral {
    
    protected final int INTSTEPS;

    /** Default number of steps for the integral is 1000 */
    public AutoIntegralFunction(){ INTSTEPS = 2000; };

    /** Set the number of steps for the integral */
    public AutoIntegralFunction(int intsteps){
        INTSTEPS = intsteps;
    }
    
    /** Recursively computes a multi-integral */
    @Override
    public double integral(double[] min, double[] max) {
        
        final AutoIntegralFunction savethis = this;
        double intval = 0.0;
        if(min.length > 1){      
            final double[] minl = new double[min.length - 1];
            final double[] maxl = new double[max.length - 1];
            System.arraycopy(min, 1, minl, 0, minl.length);
            System.arraycopy(max, 1, maxl, 0, maxl.length);

            intval = (new Integration() {
                        public double f(double x) {
                            final double xf = x;
                            double val = (new AutoIntegralFunction(INTSTEPS) {
                                public double value(Matrix mat) {
                                    return savethis.valuewithx(xf, mat);
                                }
                            }).integral(minl, maxl);
                            return val;
                        }
                    }).trapezoid(min[0], max[0], INTSTEPS);
        }else{
            intval = (new Integration() {
                        public double f(double x) {
                            Matrix mat = new Matrix(1,1); mat.set(0,0,x);
                            return value(mat);
                        }
                    }).trapezoid(min[0], max[0], INTSTEPS);
        }
        return intval;
    }
    
    protected double valuewithx(double x, Matrix mat){
        return value(VectorFunctions.prependColumnMatrix(mat, x));
    }
    
}
