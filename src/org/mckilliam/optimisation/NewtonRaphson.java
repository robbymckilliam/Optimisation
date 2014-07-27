package org.mckilliam.optimisation;

import Jama.Matrix;

/**
 * General purpose implementation of the Newton-Raphson methods.  Attempts to
 * converge to a nearby stationary point of the function f.
 * @author Robby McKilliam
 */
public class NewtonRaphson {

    /** Maximum number of time Newton's method with iterate */
    int max_iterations = 10;
    /** Tolerance to aim for between iterations */
    double tolerance = 1e-10;

    FunctionAndDerivatives f;

    public NewtonRaphson(FunctionAndDerivatives f){
        this.f = f;
    }

    public NewtonRaphson(FunctionAndDerivatives f,
            int max_iterations, double tolerance){
        this.f = f;
        this.max_iterations = max_iterations;
        this.tolerance = tolerance;
    }

    public Matrix maximise(Matrix x) {

        double e = Double.POSITIVE_INFINITY;
        int itr = 0;
        Matrix xprev = x;
        Matrix xnext;
        while(e > tolerance && itr < max_iterations){

            Matrix H = f.hessian(xprev);
            Matrix G = f.gradient(xprev);

            if(H.rank() != H.getColumnDimension())
                throw new RuntimeException("Matrix is not full rank!");

            xnext = xprev.minus(H.inverse().times(G));

            itr++;
            e = xnext.minus(xprev).normF();
            xprev = xnext;
        }

        return xprev;
    }

}
